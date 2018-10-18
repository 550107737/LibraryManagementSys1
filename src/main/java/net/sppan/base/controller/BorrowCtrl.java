package net.sppan.base.controller;

import net.sppan.base.common.JsonResult;
import net.sppan.base.entity.*;
import net.sppan.base.service.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.swing.*;
import java.awt.print.Book;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/borrowCtrl")
public class BorrowCtrl extends BaseController{
    @Autowired
    private BorrowService borrowService;

    @Autowired
    private UserService userService;

    @Autowired
    private BookService bookService;

    @Autowired
    private BookcaseService bookcaseService;

    @Autowired
    private BookboxService bookboxService;
    /**
     * @方法名: borrow
     * @功能描述: 返回借阅主页
     * @创建人: 黄梓莘
     * @创建时间： 2018-6-30
     */
    @RequestMapping(value = { "/borrow" })
    public String index() {
        return "borrow/borrow";
    }
    @RequestMapping(value = { "/borrow_student" })
    public String index1() {
        return "borrow/borrow_student";
    }
    /**
     * @方法名: addBorrow
     * @功能描述: 用户借书
     * @创建人: 黄梓莘
     * @创建时间： 2018-6-29
     */
    @RequestMapping("/addBorrow")
    @ResponseBody
    public JsonResult addBorrow(BorrowModel borrowModel){
        try {
            addBorrowForAPI(borrowModel);
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.failure(e.getMessage());
        }
        return JsonResult.success();
    }
    /**
     * @方法名: returnBorrow
     * @功能描述: 用户还书
     * @创建人: 黄梓莘
     * @创建时间： 2018-6-30
     * borrowModel中复用了actualboxid，传入是注意应传入书箱层号，也就是bookboxModel中的boxsid,并不是书箱id
     */
    @RequestMapping("/returnBorrow")
    @ResponseBody
    public JsonResult returnBorrow(BorrowModel borrowModel,Integer inBox,String libraryLocation){//注意这里actualboxid的值是书箱层号，也就是bookboxModel中的boxsid,并不是书箱id
        try {
            returnBorrowForAPI(borrowModel,inBox,libraryLocation);
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.failure(e.getMessage());
        }
        return JsonResult.success();
    }

    /**
     * @方法名: editBorrow
     * @功能描述: 管理员修改借书(忽略检查权限)
     * @创建人: 黄梓莘
     * @创建时间： 2018-6-29
     */
    @RequestMapping("/editBorrow")
    @ResponseBody
    public JsonResult editBorrow(BorrowModel borrowModel){
        try {
            borrowService.checkEditFormValid(borrowModel);
            borrowService.checkUserBorrowable(borrowModel,1);
            borrowService.checkBookBorrowable(borrowModel,1);
            BookboxModel bookboxModel=bookboxService.find(borrowModel.getReturnBoxId());
            String location=null;
            if(bookboxModel!=null){
                location=bookboxModel.getLocation()+bookboxModel.getBoxSid().toString()+"层";
                borrowModel.setReturnLocation(location);
            }
            bookboxModel=bookboxService.find(borrowModel.getActualBoxId());
            if(bookboxModel!=null){
                location=bookboxModel.getLocation()+bookboxModel.getBoxSid().toString()+"层";
                borrowModel.setActualLocation(location);
            }
            borrowService.saveOrUpdate(borrowModel);
            borrowService.changeBookStatus(borrowModel,1);//type=1代表编辑借阅
            borrowService.changeUserAction(borrowModel);//修改用户剩余可借书籍数量
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.failure(e.getMessage());
        }
        return JsonResult.success();
    }



    /**
     * @方法名: deleteBorrow
     * @功能描述: 删除借阅记录
     * @创建人: 黄梓莘
     * @创建时间： 2018-6-29
     */
    @RequestMapping("/deleteBorrow/{id}")
    @ResponseBody
    public JsonResult deleteBorrow(@PathVariable Integer id) {
        try {
            borrowService.delete(id);
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.failure(e.getMessage());
        }
        return JsonResult.success();
    }

    /**
     * @方法名: list
     * @功能描述: 查询借阅记录
     * @创建人: 黄梓莘
     * @创建时间： 2018-6-29
     */
    @RequestMapping("/list")
    @ResponseBody
    public Page<BorrowModel> list(
            @RequestParam(value="userId",required=false) String userId,
            @RequestParam(value="bookRfid",required=false) String bookRfid
    ) {
        Page<BorrowModel> page = borrowService.findAllByLike(userId,bookRfid, getPageRequest());
        return page;
    }
    @RequestMapping("/list_student")
    @ResponseBody
    public Page<BorrowModel> list_student() {
        Subject subject = SecurityUtils.getSubject();
        Object principal = subject.getPrincipal();
        if(principal== null){
            return null;
        }
        UserModel userModel=(UserModel)principal;
        UserModel dbUserModel=userService.findByUserId(userModel.getUserId());
        Page<BorrowModel> page = borrowService.findAllByUserId(dbUserModel.getUserId(),getPageRequest());
        return page;
    }
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String search( ModelMap map) {
        return "borrow/searchForm";
    }

    /**
     * @方法名: add
     * @功能描述: 返回add表单
     * @创建人: 黄梓莘
     * @创建时间： 2018-6-29
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(ModelMap map) {
        return "borrow/borrowAddForm";
    }

    /**
     * @方法名: return1
     * @功能描述: 返回return表单
     * @创建人: 黄梓莘
     * @创建时间： 2018-6-30
     */
    @RequestMapping(value = "/return1", method = RequestMethod.GET)
    public String return1(ModelMap map) {
        List<BookcaseModel> bookcaseList = bookcaseService.findAll();
        map.put("bookcaseList", bookcaseList);
        List<BookboxModel> bookboxList = bookboxService.findAll();
        map.put("bookboxList", bookboxList);
        return "borrow/returnForm";
    }

    /**
     * @方法名: edit
     * @功能描述: 返回编辑借阅表单
     * @创建人: 黄梓莘
     * @创建时间： 2018-7-9
     */
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable Integer id, ModelMap map) {
        BorrowModel borrowModel = borrowService.find(id);
        map.put("borrowModel", borrowModel);
        List<BookcaseModel> bookcaseModels=bookcaseService.findAll();
        map.put("bookcaseModels",bookcaseModels);
        BookboxModel returnBookboxModel=bookboxService.find(borrowModel.getReturnBoxId());
        map.put("returnBookboxModel",returnBookboxModel);
        if(borrowModel.getActualBoxId()!=null){
            BookboxModel actualBookboxModel=bookboxService.find(borrowModel.getActualBoxId());
            map.put("actualBookboxModel",actualBookboxModel);
        }
        return "borrow/borrowEditForm";
    }

    public void returnBorrowForAPI(BorrowModel borrowModel,Integer inBox,String libraryLocation) throws Exception{//注意这里actualboxid的值是书箱层号，也就是bookboxModel中的boxsid,并不是书箱id
            borrowService.checkUserBorrowable(borrowModel,1);//仅为了检查是否存在此用户，还书不需要对用户的各种状态进行校验
            borrowService.checkBookBorrowable(borrowModel,2);//检查书籍状态是否可还
            BorrowModel dbBorrowModel=borrowService.findByUserIdAndBookRfidAndStatus(borrowModel.getUserId(),borrowModel.getBookRfid(),0);
            if(dbBorrowModel==null){
                throw new Exception("未查询到借阅信息，无法归还");
            }
            if(dbBorrowModel.getReturnBookcaseId()==null && inBox==0){//为null说明应还位置在图书馆
                throw new Exception("图书馆书籍不可归还至书柜，请还至图书馆！");
            }
            if(inBox==0){
                BookboxModel bookboxModel=bookboxService.findByBoxSidAndBookcaseId(borrowModel.getActualBoxId(),borrowModel.getActualBookcaseId());
                if(bookboxModel==null){
                    throw new Exception("未查询到归还书柜和书箱");
                }
                borrowModel.setActualBoxId(bookboxModel.getBoxId());//将actualboxid(实际为boxsid)转换为boxId
                borrowService.checkBoxReturnable(borrowModel);//检查书箱是否满书
                if(bookboxModel==null){
                    throw new Exception("未查询到归还书柜和书箱");
                }
                String location=bookboxModel.getLocation()+bookboxModel.getBoxSid().toString()+"层";
                borrowModel.setActualLocation(location);
            }else{
                borrowModel.setActualLocation(libraryLocation);
            }

            //设备调用该函数应该只能收到用户id、书籍id、当前书柜书箱id，根据这些信息找到原来的borrowid再进行操作。
            BorrowModel oldBorrowModel=borrowService.updateBorrowModelByReturn(borrowModel);
            borrowService.saveOrUpdate(oldBorrowModel);

            BookModel bookModel=bookService.findByBookRfid(oldBorrowModel.getBookRfid());
            bookModel.setInBox(inBox);
            bookService.saveOrUpdate(bookModel);
            if(inBox==0)
                borrowService.changeCaseAndBox(oldBorrowModel,2);//type为2代表还书
            borrowService.changeBookStatus(oldBorrowModel,2);//type为2代表还书
            borrowService.changeUserAction(oldBorrowModel);//修改用户剩余可借书籍数量
    }

    public void addBorrowForAPI(BorrowModel borrowModel) throws Exception{
            borrowService.checkUserBorrowable(borrowModel,0);
            borrowService.checkBookBorrowable(borrowModel,0);//0代表借书
            BookModel bookModel=bookService.findByBookRfid(borrowModel.getBookRfid());
            borrowModel.setBorrowTime(new Date());
            borrowModel.setReturnTime(borrowService.getShouldReturnTime(borrowModel));//应还时间
            borrowModel.setStatus(0);//新增则默认为书本未归还，编辑则根据网页
            borrowModel.setAmount(0.0);
            borrowModel.setIsFinish(0);
            borrowModel.setIsPay(0);
            if(bookModel.getInBox()==0){
                borrowModel.setReturnBoxId(bookModel.getBoxId());
                borrowModel.setReturnBookcaseId(bookModel.getBookcaseId());
                BookboxModel bookboxModel=bookboxService.find(bookModel.getBoxId());
                String location=bookboxModel.getLocation()+bookboxModel.getBoxSid().toString()+"层";
                borrowModel.setReturnLocation(location);
            }else{
                borrowModel.setReturnLocation(bookModel.getBooksPosition());
            }

            borrowService.saveOrUpdate(borrowModel);
            borrowService.changeBookStatus(borrowModel,0);//type为0代表新增借阅，service中bookModel.status直接置1
            borrowService.changeUserAction(borrowModel);
            if(bookModel.getInBox()==0){
                borrowService.changeCaseAndBox(borrowModel,0);
            }
    }
}
