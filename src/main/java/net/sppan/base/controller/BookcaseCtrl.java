package net.sppan.base.controller;

import net.sppan.base.common.JsonResult;
import net.sppan.base.entity.BookcaseModel;
import net.sppan.base.service.BookcaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/bookcaseCtrl")
public class BookcaseCtrl extends BaseController {
    @Autowired
    private BookcaseService bookcaseService;

    /**
     * @方法名: /bookcase
     * @功能描述: 返回书柜主页
     * @创建人: 黄梓莘
     * @创建时间： 2018-6-28
     */
    @RequestMapping(value = { "/", "/bookcase" })
    public String index() {
        return "bookcase/bookcase";
    }
    /**
     * @方法名: addOrEditBookcase
     * @功能描述: 新建或编辑书柜
     * @创建人: 黄梓莘
     * @创建时间： 2018-6-28
     */
    @RequestMapping("/addOrEditBookcase")
    @ResponseBody
    public JsonResult addOrEditBookcase(BookcaseModel bookcaseModel){
        try {
            bookcaseService.saveOrUpdate(bookcaseModel);
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.failure(e.getMessage());
        }
        return JsonResult.success();
    }
    /**
     * @方法名: deleteBookcase/{id}
     * @功能描述: 删除书柜
     * @创建人: 黄梓莘
     * @创建时间： 2018-6-28
     */
    @RequestMapping(value = "/deleteBookcase/{id}")
    @ResponseBody
    public JsonResult deleteBookcase(@PathVariable Integer id) {
        try {
            bookcaseService.delete(id);
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.failure(e.getMessage());
        }
        return JsonResult.success();
    }
    /**
     * @方法名: list
     * @功能描述: 查询书柜
     * @创建人: 黄梓莘
     * @创建时间： 2018-6-28
     */
    @RequestMapping("/list")
    @ResponseBody
    public Page<BookcaseModel> list(
            @RequestParam(value="searchText",required=false) String searchText
    ) {
        Page<BookcaseModel> page = bookcaseService.findAllByLike(searchText, getPageRequest());
        return page;
    }
    /**
     * @方法名: add
     * @功能描述: 返回增加书柜form
     * @创建人: 黄梓莘
     * @创建时间： 2018-6-28
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(ModelMap map) {

        return "bookcase/form";
    }

    /**
     * @方法名: edit/{id}
     * @功能描述: 返回编辑书柜form
     * @创建人: 黄梓莘
     * @创建时间： 2018-6-28
     */
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable Integer id, ModelMap map) {
        BookcaseModel bookcaseModel = bookcaseService.find(id);
        map.put("bookcaseModel", bookcaseModel);
        return "bookcase/form";
    }
}
