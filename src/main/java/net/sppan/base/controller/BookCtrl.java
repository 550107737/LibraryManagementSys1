package net.sppan.base.controller;

import net.sppan.base.common.JsonResult;
import net.sppan.base.entity.BookModel;
import net.sppan.base.entity.BookboxModel;
import net.sppan.base.entity.BookcaseModel;
import net.sppan.base.service.BookService;
import net.sppan.base.service.BookboxService;
import net.sppan.base.service.BookcaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/bookCtrl")
public class BookCtrl extends BaseController {
    @Autowired
    private BookService bookService;
    @Autowired
    private BookboxService bookboxService;
    @Autowired
    private BookcaseService bookcaseService;
    /**
     * @方法名: books
     * @功能描述: 返回book主页
     * @创建人: 黄梓莘
     * @创建时间： 2018-6-30
     */
    @RequestMapping(value = { "/", "/books" })
    public String index() {
        return "books/books";
    }
    /**
     * @方法名: addBook
     * @功能描述: 增加书籍
     * @创建人: 黄梓莘
     * @创建时间： 2018-6-30
     */
    @RequestMapping("/addBook")
    @ResponseBody
    public JsonResult addBook(BookModel bookModel,String libraryLocation){
        //注意这里boxId的值是书箱层号，也就是bookboxmodel中的boxsid，并不是书箱id
        try {
            bookModel.setCheckStatus(0);//新增书籍默认未盘点
            if(bookModel.getInBox()==0){
                BookboxModel bookboxModel=bookboxService.findByBoxSidAndBookcaseId(bookModel.getBoxId(),bookModel.getBookcaseId());
                if(bookboxModel==null){
                    throw new Exception("未查询到书柜和书箱");
                }
                bookModel.setBoxId(bookboxModel.getBoxId());
                bookService.checkBoxAddable(bookModel.getBoxId());
                String location="移动书柜:"+bookboxModel.getLocation()+bookboxModel.getBoxSid().toString()+"层";
                bookModel.setBooksPosition(location);
                bookService.saveOrUpdate(bookModel);
                bookService.changeBoxNum(bookModel.getBoxId());
            }else{
                bookModel.setBooksPosition("图书馆:"+libraryLocation);
                bookService.saveOrUpdate(bookModel);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.failure(e.getMessage());
        }
        return JsonResult.success();
    }
    /**
     * @方法名: editBook
     * @功能描述: 编辑书本
     * @创建人: 黄梓莘
     * @创建时间： 2018-6-30
     */
    @RequestMapping("/editBook")
    @ResponseBody
    public JsonResult editBook(BookModel bookModel,String libraryLocation){//注意这里belongBookbox的值是书箱层号，也就是bookboxmodel中的boxsid，并不是书箱id
        try {
            BookModel dbBookModel=bookService.find(bookModel.getBooksId());
            bookModel.setCheckStatus(dbBookModel.getCheckStatus());
            if(dbBookModel.getCheckDate()!=null){
                bookModel.setCheckDate(dbBookModel.getCheckDate());
            }
            if(bookModel.getInBox()==0){
                BookboxModel bookboxModel=bookboxService.findByBoxSidAndBookcaseId(bookModel.getBoxId(),bookModel.getBookcaseId());
                if(bookboxModel==null){
                    throw new Exception("未查询到书柜和书箱");
                }
                bookModel.setBoxId(bookboxModel.getBoxId());
                String location="移动书柜:"+bookboxModel.getLocation()+bookboxModel.getBoxSid().toString()+"层";
                bookModel.setBooksPosition(location);
            }else{
                bookModel.setBooksPosition(libraryLocation);
            }

            bookService.saveOrUpdate(bookModel);
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.failure(e.getMessage());
        }
        return JsonResult.success();
    }
    /**
     * @方法名: deleteBook
     * @功能描述: 删除用户
     * @创建人: 黄梓莘
     * @创建时间： 2018-6-27
     */
    @RequestMapping(value = "/deleteBook/{id}")
    @ResponseBody
    public JsonResult deleteBook(@PathVariable Integer id) {
        try {
            bookService.delete(id);
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.failure(e.getMessage());
        }
        return JsonResult.success();
    }
    /**
     * @方法名: list
     * @功能描述: 查询书籍
     * @创建人: 黄梓莘
     * @创建时间： 2018-7-11
     */
    @RequestMapping("/list")
    @ResponseBody
    public Page<BookModel> list(
            @RequestParam(value="authors",required=false) String authors,
            @RequestParam(value="bookName",required=false) String bookName,
            @RequestParam(value="publication",required=false) String publication,
            @RequestParam(value="classification",required=false) String classification,
            @RequestParam(value="booksStatus",required=false) Integer booksStatus,
            @RequestParam(value="overdue",required=false) Integer overdue//检索逾期未还标志
    ) {
        if(booksStatus==null) booksStatus=-1;
        Page<BookModel> page = bookService.searchAll(authors,bookName, publication,classification,booksStatus,overdue,getPageRequest());
        return page;
    }
    /**
     * @方法名: add
     * @功能描述: 返回增加书籍form
     * @创建人: 黄梓莘
     * @创建时间： 2018-6-30
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(ModelMap map) {
        List<BookcaseModel> bookcaseModels=bookcaseService.findAll();
        map.put("bookcaseModels",bookcaseModels);
        return "books/addForm";
    }

    /**
     * @方法名: edit
     * @功能描述: 返回编辑书籍form
     * @创建人: 黄梓莘
     * @创建时间： 2018-6-30
     */
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable Integer id, ModelMap map) {
        BookModel bookModel = bookService.find(id);
        map.put("bookModel", bookModel);
        List<BookcaseModel> bookcaseModels=bookcaseService.findAll();
        map.put("bookcaseModels",bookcaseModels);
        BookboxModel bookboxModel=bookboxService.findByBoxId(bookModel.getBoxId());
        map.put("bookboxModel",bookboxModel);
        return "books/editForm";
    }
    /**
     * @方法名: search
     * @功能描述: 带条件查询书籍
     * @创建人: 黄梓莘
     * @创建时间： 2018-07-03
     */
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String search( ModelMap map) {
        return "books/searchForm";
    }

    @RequestMapping(value = "/transfer/{id}", method = RequestMethod.GET)
    public String transfer(@PathVariable Integer id, ModelMap map) {
        BookModel bookModel = bookService.find(id);
        map.put("bookModel", bookModel);
        List<BookcaseModel> bookcaseModels=bookcaseService.findAll();
        map.put("bookcaseModels",bookcaseModels);
        BookboxModel bookboxModel=bookboxService.findByBoxId(bookModel.getBoxId());
        map.put("bookboxModel",bookboxModel);
        return "books/transferForm";
    }
    @RequestMapping(value = "/transfer", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult transfer1(BookModel bookModel) {
        try {
            BookboxModel bookboxModel=bookboxService.findByBoxSidAndBookcaseId(bookModel.getBoxId(),bookModel.getBookcaseId());
            if(bookboxModel==null){
                throw new Exception("未查询到书柜和书箱");
            }
            BookModel dbBookModel=bookService.find(bookModel.getBooksId());
            if(dbBookModel.getBooksStatus()==1){
                throw new Exception("外借中的书籍不可导入书柜！");
            }
            dbBookModel.setBoxId(bookboxModel.getBoxId());
            dbBookModel.setInBox(0);
            dbBookModel.setBookcaseId(bookModel.getBookcaseId());
            bookService.checkBoxAddable(dbBookModel.getBoxId());
            String location="移动书柜:"+bookboxModel.getLocation()+bookboxModel.getBoxSid().toString()+"层";
            dbBookModel.setBooksPosition(location);
            bookService.saveOrUpdate(dbBookModel);
            bookService.changeBoxNum(dbBookModel.getBoxId());
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.failure(e.getMessage());
        }
        return JsonResult.success();
    }
    @RequestMapping(value = "/getData1", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult getData1() {
        List list=new ArrayList();
        try {
            list=bookService.getData();
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.failure(e.getMessage());
        }
        return JsonResult.success(list);
    }
    @RequestMapping(value = "/getData2", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult getData2() {
        List list=new ArrayList();
        try {
            list=bookService.getData2();
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.failure(e.getMessage());
        }
        return JsonResult.success(list);
    }
    @RequestMapping(value = "/getChartData", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult getChartData() {
        List list=new ArrayList();
        try {
            list=bookService.getChartData();
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.failure(e.getMessage());
        }
        return JsonResult.success(list);
    }
}
