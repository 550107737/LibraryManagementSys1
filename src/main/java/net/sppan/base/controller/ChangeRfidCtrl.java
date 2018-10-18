package net.sppan.base.controller;

import net.sppan.base.common.JsonResult;
import net.sppan.base.entity.*;
import net.sppan.base.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/changeRfidCtrl")
public class ChangeRfidCtrl extends BaseController {
    @Autowired
    private ChangeRfidService changeRfidService;
    @Autowired
    private UserService userService;
    @Autowired
    private BookService bookService;
    @Autowired
    private BookcaseService bookcaseService;
    @Autowired
    private BookboxService bookboxService;

    @RequestMapping(value = { "/", "/index" })
    public String index() {
        return "changerfid/changerfid";
    }

    /**
     * @方法名: addOrEditChangeRfid
     * @功能描述: 新建或编辑默认参数
     * @创建人: 黄梓莘
     * @创建时间： 2018-7-10
     */
    @RequestMapping("/addOrEditChangeRfid")
    @ResponseBody
    public JsonResult addOrEditConfig(ChangeRfidModel changeRfidModel){
        try {
            switch (changeRfidModel.getType()){
                case 1:
                    UserModel userModel=userService.findByUserId(changeRfidModel.getOldRfid());
                    if(userModel==null){
                        throw new Exception("查询不到旧的用户RFID号");
                    }
                    break;
                case 2:
                    BookModel bookModel=bookService.findByBookRfid(changeRfidModel.getOldRfid());
                    if(bookModel==null){
                        throw new Exception("查询不到旧的书籍RFID号");
                    }
                    break;
                case 3:
                    BookcaseModel bookcaseModel=bookcaseService.findByBookcaseRfid(changeRfidModel.getOldRfid());
                    if(bookcaseModel==null){
                        throw new Exception("查询不到旧的书柜RFID号");
                    }
                    break;
                case 4:
                    BookboxModel bookboxModel=bookboxService.findByBoxRfid(changeRfidModel.getOldRfid());
                    if(bookboxModel==null){
                        throw new Exception("查询不到旧的书箱RFID号");
                    }
                    break;
            }
            changeRfidService.changeRfid(changeRfidModel);
            changeRfidService.saveOrUpdate(changeRfidModel);
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.failure(e.getMessage());
        }
        return JsonResult.success();
    }
    /**
     * @方法名: delete
     * @功能描述: 删除默认参数
     * @创建人: 黄梓莘
     * @创建时间： 2018-7-2
     */
    @RequestMapping(value = "/delete/{id}")
    @ResponseBody
    public JsonResult deleteConfig(@PathVariable Integer id) {
        try {
            changeRfidService.delete(id);
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
    public Page<ChangeRfidModel> list(
            @RequestParam(value="oldRfid",required=false) String oldRfid,
            @RequestParam(value="newRfid",required=false) String newRfid
    ) {
        Page<ChangeRfidModel> page = changeRfidService.findAllByLike(oldRfid,newRfid, getPageRequest());
        return page;
    }
    /**
     * @方法名: search
     * @功能描述: 带条件查询
     * @创建人: 黄梓莘
     * @创建时间： 2018-10-18
     */
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String search( ModelMap map) {
        return "changerfid/searchForm";
    }


    /**
     * @方法名: add
     * @功能描述: 返回add表单
     * @创建人: 黄梓莘
     * @创建时间： 2018-7-2
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(ModelMap map) {
        return "changerfid/form";
    }

    /**
     * @方法名: edit
     * @功能描述: 返回edit表单
     * @创建人: 黄梓莘
     * @创建时间： 2018-7-2
     */
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable Integer id, ModelMap map) {
        ChangeRfidModel changeRfidModel = changeRfidService.find(id);
        map.put("changeRfidModel", changeRfidModel);
        return "changerfid/form";
    }

}
