package net.sppan.base.controller;

import net.sppan.base.common.JsonResult;


import net.sppan.base.entity.BorrowHistoryModel;
import net.sppan.base.entity.UserModel;
import net.sppan.base.service.BorrowHistoryService;
import net.sppan.base.service.ConfigService;
import net.sppan.base.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/borrowHistoryCtrl")
public class BorrowHistoryCtrl extends BaseController {
    @Autowired
    private BorrowHistoryService borrowHistoryService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = { "/", "/borrowHistory" })
    public String index() {
        return "borrow/borrowhistory";
    }

    @RequestMapping(value = { "/", "/borrowHistory_student" })
    public String index1() {
        return "borrow/borrowhistory_student";
    }

    /**
     * @方法名: addOrEditBorrowHistory
     * @功能描述: 新建或编辑默认参数
     * @创建人: 黄梓莘
     * @创建时间： 2018-6-28
     */
    @RequestMapping("/addOrEditBorrowHistory")
    @ResponseBody
    public JsonResult addOrEditBorrowHistory(BorrowHistoryModel borrowHistoryModel){
        try {
            borrowHistoryService.saveOrUpdate(borrowHistoryModel);
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
     * @创建时间： 2018-6-28
     */
    @RequestMapping(value = "/delete/{id}")
    @ResponseBody
    public JsonResult deleteConfig(@PathVariable Integer id) {
        try {
            borrowHistoryService.delete(id);
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
     * @创建时间： 2018-6-28
     */
    @RequestMapping("/list123")
    @ResponseBody
    public Page<BorrowHistoryModel> list(
            @RequestParam(value="userId",required=false) String userId
    ) {
        Page<BorrowHistoryModel> page = borrowHistoryService.findAllByUserIdAndStatus(userId,2, getPageRequest());
        return page;
    }

    @RequestMapping("/list_student")
    @ResponseBody
    public Page<BorrowHistoryModel> list_student() {
        Subject subject = SecurityUtils.getSubject();
        Object principal = subject.getPrincipal();
        if(principal== null){
            return null;
        }
        UserModel userModel=(UserModel)principal;
        UserModel dbUserModel=userService.findByUserId(userModel.getUserId());
        Page<BorrowHistoryModel> page = borrowHistoryService.findAllByUserId(dbUserModel.getUserId(),getPageRequest());
        return page;
    }

    /**
     * @方法名: add
     * @功能描述: 返回add表单
     * @创建人: 黄梓莘
     * @创建时间： 2018-6-28
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(ModelMap map) {
        return "borrow/borrowAddForm";
    }

    /**
     * @方法名: edit
     * @功能描述: 返回edit表单
     * @创建人: 黄梓莘
     * @创建时间： 2018-6-28
     */
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable Integer id, ModelMap map) {
        BorrowHistoryModel borrowModel = borrowHistoryService.find(id);
        map.put("borrowModel", borrowModel);
        return "borrow/borrowEditForm";
    }

    @RequestMapping(value = { "overdueList" },method = RequestMethod.GET)
    public String overdueList() {
        return "borrow/overdueListForm";
    }
    @RequestMapping(value = { "overdueList" },method = RequestMethod.POST)
    @ResponseBody
    public Page<BorrowHistoryModel> overdueList1(String userId) {
        Page<BorrowHistoryModel> page = borrowHistoryService.findAllByUserIdAndStatus(userId,2, getPageRequest());
        return page;
    }
}
