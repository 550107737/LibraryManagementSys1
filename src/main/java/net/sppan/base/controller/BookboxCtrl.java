package net.sppan.base.controller;

import net.sppan.base.common.JsonResult;
import net.sppan.base.entity.BookboxModel;

import net.sppan.base.entity.BookcaseModel;
import net.sppan.base.entity.ConfigModel;
import net.sppan.base.service.BookboxService;
import net.sppan.base.service.BookcaseService;
import net.sppan.base.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/bookboxCtrl")
public class BookboxCtrl extends BaseController {
    @Autowired
    private BookboxService bookboxService;
    @Autowired
    private BookcaseService bookcaseService;
    @Autowired
    private ConfigService configService;

    /**
     * @方法名: index
     * @功能描述: 返回后台书箱主页面
     * @创建人: 黄梓莘
     * @创建时间： 2018-6-28
     */
    @RequestMapping(value = { "/", "/bookbox" })
    public String index(ModelMap map) {
        List<BookcaseModel> list = bookcaseService.findAll();
        map.put("list", list);
        return "bookbox/bookbox";
    }
    /**
     * @方法名: addOrEditBookbox
     * @功能描述: 新建或编辑书柜
     * @创建人: 黄梓莘
     * @创建时间： 2018-6-28
     */
    @RequestMapping("/addOrEditBookbox")
    @ResponseBody
    public JsonResult addOrEditBookbox(BookboxModel bookboxModel){
        try {
            ConfigModel configModel=configService.findByConfigId(1);
            if(bookboxModel.getBoxNum()>configModel.getBoxMax()){
                throw new Exception("超过该箱最大容纳数量！");
            }
            bookboxService.saveOrUpdate(bookboxModel);
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.failure(e.getMessage());
        }
        return JsonResult.success();
    }
    /**
     * @方法名: deleteBookbox
     * @功能描述: 删除书箱
     * @创建人: 黄梓莘
     * @创建时间： 2018-6-28
     */
    @RequestMapping(value = "/deleteBookbox/{id}")
    @ResponseBody
    public JsonResult deleteBookcase(@PathVariable Integer id) {
        try {
            bookboxService.delete(id);
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.failure(e.getMessage());
        }
        return JsonResult.success();
    }
    /**
     * @方法名: list
     * @功能描述: 查询书箱
     * @创建人: 黄梓莘
     * @创建时间： 2018-6-28
     */
    @RequestMapping("/list")
    @ResponseBody
    public Page<BookboxModel> list(
            @RequestParam(value="searchText",required=false) String searchText
    ) {
        Page<BookboxModel> page = bookboxService.findAllByLike(searchText, getPageRequest());
        return page;
    }
    /**
     * @方法名: add
     * @功能描述: 返回书箱增加form
     * @创建人: 黄梓莘
     * @创建时间： 2018-6-28
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(ModelMap map) {
        List<BookcaseModel> list = bookcaseService.findAll();
        map.put("list", list);
        return "bookbox/form";
    }

    /**
     * @方法名: edit/{id}
     * @功能描述: 返回书箱编辑form
     * @创建人: 黄梓莘
     * @创建时间： 2018-6-28
     */
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable Integer id, ModelMap map) {
        BookboxModel bookboxModel = bookboxService.find(id);
        map.put("bookboxModel", bookboxModel);
        List<BookcaseModel> list = bookcaseService.findAll();
        map.put("list", list);
        return "bookbox/form";
    }
}
