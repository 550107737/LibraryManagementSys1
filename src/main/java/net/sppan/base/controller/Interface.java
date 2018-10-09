package net.sppan.base.controller;

import net.sppan.base.common.JsonResult;
import net.sppan.base.common.utils.EncryUtil;
import net.sppan.base.common.utils.MD5Utils;
import net.sppan.base.entity.BookModel;
import net.sppan.base.entity.BookboxModel;
import net.sppan.base.entity.BookcaseModel;
import net.sppan.base.entity.BorrowModel;
import net.sppan.base.service.BookService;
import net.sppan.base.service.BookboxService;
import net.sppan.base.service.BookcaseService;
import net.sppan.base.service.BorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/interface")
public class Interface extends BaseController{

    @Autowired
    private BorrowService borrowService;
    @Autowired
    private BookService bookService;
    @Autowired
    private BookboxService bookboxService;
    @Autowired
    private BookcaseService bookcaseService;
    /**
     * @方法名: login
     * @功能描述: 登录
     * @创建人: 黄梓莘
     * @创建时间： 2018-7-29
     */
    @RequestMapping(value = { "/login" })
    public JsonResult login(String APIKey,String userId,Integer bookcaseId,String timestamp) {
        try{
            if(!verifyTimestamp(20,timestamp)){
                throw new Exception("操作超时！");
            }
            List list=new ArrayList();
            list.add(userId);
            list.add(bookcaseId.toString());
            list.add(timestamp);
            if(!APIKey.equals(getParamMD5(list))){
                throw new Exception("参数错误！");
            }
            BorrowModel borrowModel=new BorrowModel();
            borrowModel.setUserId(userId);
            borrowService.checkUserBorrowable(borrowModel,0);
        }catch(Exception e){
            return JsonResult.failure(e.getMessage());
        }
        return JsonResult.success();
    }



    /**
     * @方法名: borrowBook
     * @功能描述: 借书
     * @创建人: 黄梓莘
     * @创建时间： 2018-7-29
     */
    @RequestMapping(value = { "/borrowBook" })
    public JsonResult borrowBook(String param) {
        try{
//            String params[]=checkAndDecryptParam(param,3);
//            String secretKey=params[0];
//            String borrowBookParam=params[1];
//            String timestamp=params[2];
//            if(!secretKey.equals("borrowBook")){
//                throw new Exception("秘钥不匹配！");
//            }
//            if(!verifyTimestamp(20,timestamp)){
//                throw new Exception("操作超时！");
//            }
//            String borrowBookParams[]=borrowBookParam.split("*");
//            String userId=borrowBookParams[0];
//            String bookId=borrowBookParams[1];
//            BorrowModel borrowModel=new BorrowModel();
//            borrowModel.setUserId(userId);
//            //borrowModel.setBookId(Integer.valueOf(bookId));
//            //
//            borrowService.checkBookBorrowable(borrowModel,0);//0代表借书
//            //BookModel bookModel=bookService.find(borrowModel.getBookId());
//            borrowModel.setBorrowTime(new Date());
//            borrowModel.setReturnTime(borrowService.getShouldReturnTime(borrowModel));//应还时间
//            borrowModel.setStatus(0);//新增则默认为书本未归还，编辑则根据网页
//            borrowModel.setAmount(0.0);
//            borrowModel.setIsFinish(0);
//            borrowModel.setIsPay(0);
//            borrowModel.setReturnBoxId(bookModel.getBoxId());
//            borrowModel.setReturnBookcaseId(bookModel.getBookcaseId());
//            BookboxModel bookboxModel=bookboxService.findByBoxId(bookModel.getBoxId());
//            String location=bookboxModel.getLocation()+bookboxModel.getBoxSid().toString()+"层";
//            borrowModel.setReturnLocation(location);
//            borrowService.saveOrUpdate(borrowModel);
//            borrowService.changeBookStatus(borrowModel,0);//type为0代表新增借阅，service中bookModel.status直接置1
//            borrowService.changeUserAction(borrowModel);
//            borrowService.changeCaseAndBox(borrowModel,0);

        }catch(Exception e){
            return JsonResult.failure(e.getMessage());
        }
        return JsonResult.success();
    }
    /**
     * @方法名: returnBook
     * @功能描述: 还书
     * @创建人: 黄梓莘
     * @创建时间： 2018-7-29
     */
    @RequestMapping(value = { "/returnBook" })
    public JsonResult returnBook(String param) {
        try{
//            String params[]=checkAndDecryptParam(param,3);
//            String secretKey=params[0];
//            String returnBookParam=params[1];
//            String timestamp=params[2];
//            if(!secretKey.equals("returnBook")){
//                throw new Exception("秘钥不匹配！");
//            }
//            if(!verifyTimestamp(20,timestamp)){
//                throw new Exception("操作超时！");
//            }
//            String returnBookParams[]=returnBookParam.split("*");
//            BorrowModel borrowModel=new BorrowModel();
//            borrowModel.setUserId(returnBookParams[0]);
//            borrowModel.setBookId(Integer.valueOf(returnBookParams[1]));
//            borrowModel.setActualBookcaseId(Integer.valueOf(returnBookParams[2]));
//            borrowModel.setActualBoxId(Integer.valueOf(returnBookParams[3]));
//            borrowService.checkBookBorrowable(borrowModel,2);//检查书籍状态是否可还
//            borrowService.checkBoxReturnable(borrowModel);//检查书箱是否满书
//            BookboxModel bookboxModel=bookboxService.findByBoxSidAndBookcaseId(borrowModel.getActualBoxId(),borrowModel.getActualBookcaseId());
//            if(bookboxModel==null){
//                throw new Exception("未查询到归还书柜和书箱");
//            }
//            borrowModel.setActualBoxId(bookboxModel.getBoxId());//将actualboxid(实际为boxsid)转换为boxId
//            String location=bookboxModel.getLocation()+bookboxModel.getBoxSid().toString()+"层";
//            borrowModel.setActualLocation(location);
//
//            //设备调用该函数应该只能收到用户id、书籍id、当前书柜书箱id，根据这些信息找到原来的borrowid再进行操作。
//            BorrowModel oldBorrowModel=borrowService.updateBorrowModelByReturn(borrowModel);
//            borrowService.saveOrUpdate(oldBorrowModel);
//            BookModel bookModel=bookService.find(oldBorrowModel.getBookId());
//            bookService.saveOrUpdate(bookModel);
//            borrowService.changeCaseAndBox(oldBorrowModel,2);//type为2代表还书
//            borrowService.changeBookStatus(oldBorrowModel,2);//type为2代表还书
//            borrowService.changeUserAction(oldBorrowModel);//修改用户剩余可借书籍数量
        }catch(Exception e){
            return JsonResult.failure(e.getMessage());
        }
        return JsonResult.success();
    }
    /**
     * @方法名: malfunction
     * @功能描述: 故障
     * @创建人: 黄梓莘
     * @创建时间： 2018-7-29
     */
    @RequestMapping(value = { "/malfunction" })
    public JsonResult malfunction(String param) {
        try{
            String params[]=checkAndDecryptParam(param,3);
            String secretKey=params[0];
            String bookcaseId=params[1];
            String timestamp=params[2];
            if(!secretKey.equals("malfunction")){
                throw new Exception("秘钥不匹配！");
            }
            if(!verifyTimestamp(20,timestamp)){
                throw new Exception("操作超时！");
            }
            //设置书柜故障
            BookcaseModel bookcaseModel=bookcaseService.find(Integer.valueOf(bookcaseId));
            if(bookcaseModel==null){
                throw new Exception("未找到故障书柜");
            }
            bookcaseModel.setBrokenTime(new Date());
            bookcaseModel.setIsBroken(1);
            bookcaseService.saveOrUpdate(bookcaseModel);
        }catch(Exception e){
            return JsonResult.failure(e.getMessage());
        }
        return JsonResult.success();
    }
    /**
     * @方法名: doorOverTime
     * @功能描述: 柜门超过5分钟未关闭
     * @创建人: 黄梓莘
     * @创建时间： 2018-7-29
     */
    @RequestMapping(value = { "/doorOverTime" })
    public JsonResult doorOverTime(String param) {
        try{
            String params[]=checkAndDecryptParam(param,3);
            String secretKey=params[0];
            String bookcaseId=params[1];
            String timestamp=params[2];
            if(!secretKey.equals("doorOverTime")){
                throw new Exception("秘钥不匹配！");
            }
            if(!verifyTimestamp(20,timestamp)){
                throw new Exception("操作超时！");
            }
            BookcaseModel bookcaseModel=bookcaseService.find(Integer.valueOf(bookcaseId));
            if(bookcaseModel==null){
                throw new Exception("未找到未关门书柜");
            }
            //以下等待微信通知接口

        }catch(Exception e){
            return JsonResult.failure(e.getMessage());
        }
        return JsonResult.success();
    }
    /**
     * @方法名: bookCheck
     * @功能描述: 图书盘点
     * @创建人: 黄梓莘
     * @创建时间： 2018-7-29
     */
    @RequestMapping(value = { "/bookCheck" })
    public JsonResult bookCheck(String param) {
        try{
            String params[]=checkAndDecryptParam(param,3);
            String secretKey=params[0];
            String bookRfid=params[1];
            String timestamp=params[2];
            if(!secretKey.equals("bookCheck")){
                throw new Exception("秘钥不匹配！");
            }
            if(!verifyTimestamp(60,timestamp)){
                throw new Exception("操作超时！");
            }
            String bookRfids[]=bookRfid.split("\\*");
            Date date=new Date();
            for(int i=0;i<bookRfids.length;i++){
                BookModel bookModel=bookService.findByBookRfid(bookRfids[i]);
                if(bookModel==null){
                    continue;
                }
                bookModel.setCheckStatus(1);
                bookModel.setCheckDate(date);
                bookService.saveOrUpdate(bookModel);
            }
        }catch(Exception e){
            return JsonResult.failure(e.getMessage());
        }
        return JsonResult.success();
    }

    private boolean verifyTimestamp(int seconds,String timestamp){
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String currentTimeStr = formatter.format(currentTime);
        Integer currentTimestamp=Integer.valueOf(currentTimeStr);
        Integer oldTimestamp=Integer.valueOf(timestamp);
        if(currentTimestamp-oldTimestamp>seconds){
            return false;
        }
        return true;
    }
    private String[] checkAndDecryptParam(String param,int paramNum) throws Exception{
        if(param==null || param.equals("")){
            throw new Exception("未获得正确的参数！");
        }
        String decryptParam=EncryUtil.decrypt(param);
        String params[]=decryptParam.split(",");
        if (params.length!=paramNum){
            throw new Exception("未获得正确的参数！");
        }
        return params;
    }
    private String getParamMD5(List paramList){
        String tempStr="";
        for(int i=0;i< paramList.size();i++){
            tempStr+=paramList.get(i);
        }
        return MD5Utils.md5(tempStr);
    }
}
