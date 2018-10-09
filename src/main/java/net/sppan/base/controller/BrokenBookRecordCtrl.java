package net.sppan.base.controller;

import net.sppan.base.common.JsonResult;
import net.sppan.base.entity.BookModel;
import net.sppan.base.entity.BrokenBookRecordModel;
import net.sppan.base.service.BookService;
import net.sppan.base.service.BrokenBookRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/brokenBookRecordCtrl")
public class BrokenBookRecordCtrl extends BaseController {
    @Autowired
    private BrokenBookRecordService brokenBookRecordService;
    @Autowired
    private BookService bookService;

    @RequestMapping(value = { "/", "/index" })
    public String index() {
        return "brokenbookrecord/brokenbookrecord";
    }

    /**
     * @方法名: addOrEditBroken
     * @功能描述: 新建或编辑书籍损坏
     * @创建人: 黄梓莘
     * @创建时间： 2018-7-2
     */
    @RequestMapping("/addOrEditBroken")
    @ResponseBody
    public JsonResult addOrEditBroken(BrokenBookRecordModel brokenBookRecordModel){
        try {
            BookModel bookModel=bookService.findByBookRfid(brokenBookRecordModel.getBookRfid());
            if(bookModel==null){
                throw new Exception("无此书籍！");
            }
            if(brokenBookRecordModel.getNeedPay()==1){
                brokenBookRecordService.changeUserFineAndBookStatus(brokenBookRecordModel);
            }
            if(brokenBookRecordModel.getOffShelves()==1){
                bookModel.setBooksStatus(2);
                bookService.saveOrUpdate(bookModel);
            }
            brokenBookRecordService.saveOrUpdate(brokenBookRecordModel);
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.failure(e.getMessage());
        }
        return JsonResult.success();
    }
    /**
     * @方法名: deleteBroken
     * @功能描述: 删除书籍损坏
     * @创建人: 黄梓莘
     * @创建时间： 2018-7-2
     */
    @RequestMapping(value = "/deleteBroken/{id}")
    @ResponseBody
    public JsonResult deleteBroken(@PathVariable Integer id) {
        try {
            brokenBookRecordService.delete(id);
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
     * @创建时间： 2018-7-2
     */
    @RequestMapping("/list")
    @ResponseBody
    public Page<BrokenBookRecordModel> list(
            @RequestParam(value="searchText",required=false) String searchText
    ) {
        Page<BrokenBookRecordModel> page = brokenBookRecordService.findAllByLike(searchText, getPageRequest());
        return page;
    }

    /**
     * @方法名: add
     * @功能描述: 返回add表单
     * @创建人: 黄梓莘
     * @创建时间： 2018-7-2
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(ModelMap map) {
        return "brokenbookrecord/form";
    }

    /**
     * @方法名: edit
     * @功能描述: 返回edit表单
     * @创建人: 黄梓莘
     * @创建时间： 2018-7-2
     */
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable Integer id, ModelMap map) {
        BrokenBookRecordModel brokenBookRecordModel = brokenBookRecordService.find(id);
        map.put("brokenBookRecordModel", brokenBookRecordModel);
        return "brokenbookrecord/form";
    }

}
