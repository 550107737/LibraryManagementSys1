package net.sppan.base.controller;

import net.sppan.base.common.JsonResult;
import net.sppan.base.common.utils.RandomUtil;
import net.sppan.base.entity.BookModel;
import net.sppan.base.entity.BooksCheckModel;
import net.sppan.base.service.BookService;
import net.sppan.base.service.BooksCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Controller
@RequestMapping("/booksCheckCtrl")
public class BooksCheckCtrl extends BaseController {
    @Autowired
    private BooksCheckService booksCheckService;
    @Autowired
    private BookService bookService;


    @RequestMapping(value = { "/", "/booksCheck" })
    public String index(ModelMap map) {
        return "bookscheck/bookscheck";
    }

    /**
     * @方法名: addOrEditConfig
     * @功能描述: 新建或编辑默认参数
     * @创建人: 黄梓莘
     * @创建时间： 2018-8-3
     */
    @RequestMapping("/addOrEdit")
    @ResponseBody
    public JsonResult addOrEdit(BooksCheckModel configModel){
        try {
            booksCheckService.saveOrUpdate(configModel);
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.failure(e.getMessage());
        }
        return JsonResult.success();
    }
    /**
     * @方法名: edit
     * @功能描述: 人工盘点完成
     * @创建人: 黄梓莘
     * @创建时间： 2018-8-4
     */
    @RequestMapping(value = "/edit/{id}")
    @ResponseBody
    public JsonResult edit(@PathVariable Integer id) {
        try {
            BooksCheckModel booksCheckModel=booksCheckService.find(id);
            BookModel bookModel=bookService.find(booksCheckModel.getBooksId());
            bookModel.setCheckStatus(1);
            bookModel.setCheckDate(new Date());
            bookService.saveOrUpdate(bookModel);
            booksCheckModel.setCheckDate(new Date());
            booksCheckModel.setCheckStatus(1);
            booksCheckService.saveOrUpdate(booksCheckModel);
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.failure(e.getMessage());
        }
        return JsonResult.success();
    }

    /**
     * @方法名: list
     * @功能描述: 查询默认参数
     * @创建人: 黄梓莘
     * @创建时间： 2018-8-3
     */
    @RequestMapping("/list")
    @ResponseBody
    public Page<BooksCheckModel> list(
            @RequestParam(value="bookRfid",required=false) String bookRfid
    ) {
        Page<BooksCheckModel> page = booksCheckService.findAllByLike(bookRfid,getPageRequest());
        return page;
    }
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String search( ModelMap map) {
        return "bookscheck/searchForm";
    }


    /**
     * @方法名: edit
     * @功能描述: 返回edit表单
     * @创建人: 黄梓莘
     * @创建时间： 2018-6-28
     */
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable Integer id, ModelMap map) {
        BooksCheckModel booksCheckModel = booksCheckService.find(id);
        map.put("booksCheckModel", booksCheckModel);
        return "bookscheck/form";
    }
}
