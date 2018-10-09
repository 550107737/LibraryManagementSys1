package net.sppan.base.controller;

import net.sppan.base.common.JsonResult;
import net.sppan.base.common.utils.MD5Utils;
import net.sppan.base.entity.BorrowModel;
import net.sppan.base.entity.ConfigModel;
import net.sppan.base.entity.RoleModel;
import net.sppan.base.entity.UserModel;
import net.sppan.base.service.BorrowService;
import net.sppan.base.service.ConfigService;
import net.sppan.base.service.RoleService;
import net.sppan.base.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/userCtrl")
public class UserCtrl extends BaseController {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private BorrowService borrowService;

    @Autowired
    private ConfigService configService;

    /**
     * @方法名: index
     * @功能描述: 返回用户主页面
     * @创建人: 黄梓莘
     * @创建时间： 2018-6-28
     */
    @RequestMapping(value = { "/", "/index" })
    public String index() {
        return "user/index";
    }

    @RequestMapping(value = { "/index_student" })
    public String index1() {
        return "user/user_student";
    }

    /**
     * @方法名: addOrEditUser
     * @功能描述: 新建或编辑用户
     * @创建人: 黄梓莘
     * @创建时间： 2018-6-28
     */
    @RequestMapping("/addOrEditUser")
    @ResponseBody
    public JsonResult addOrEditUser(UserModel userModel){
        try {
            userService.saveOrUpdate(userModel);
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.failure(e.getMessage());
        }
        return JsonResult.success();
    }

    /**
     * @方法名: list
     * @功能描述: 查询
     * @创建人: 黄梓莘
     * @创建时间： 2018-7-11
     */
    @RequestMapping("/list")
    @ResponseBody
    public Page<UserModel> list(
            @RequestParam(value="userClass",required=false) String userClass,
            @RequestParam(value="isOverdue",required=false) Integer isOverdue,
            @RequestParam(value="overdueTotalAmount",required=false) Integer overdueTotalAmount
    ) {
        if(isOverdue==null) isOverdue=-1;
        if(overdueTotalAmount==null) overdueTotalAmount=-1;
        Page<UserModel> page = userService.searchAll(userClass,isOverdue,overdueTotalAmount, getPageRequest());
        return page;
    }
    @RequestMapping("/list_student")
    @ResponseBody
    public Page<UserModel> list_student(
            @RequestParam(value="searchText",required=false) String searchText
    ) {
        Subject subject = SecurityUtils.getSubject();
        Object principal = subject.getPrincipal();
        if(principal== null){
            return null;
        }
        UserModel userModel=(UserModel)principal;
        UserModel dbUserModel=userService.findByUserId(userModel.getUserId());
        Page<UserModel> page = userService.findAllByUserId(dbUserModel.getUserId(), getPageRequest());
        return page;
    }

    /**
     * @方法名: add
     * @功能描述: 返回添加表单
     * @创建人: 黄梓莘
     * @创建时间： 2018-6-28
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(ModelMap map) {
        return "user/addform";
    }
    /**
     * @方法名: /edit/{id}
     * @功能描述: 返回用户编辑form
     * @创建人: 黄梓莘
     * @创建时间： 2018-6-28
     */
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable Integer id, ModelMap map) {
        UserModel userModel = userService.find(id);
        map.put("user", userModel);
        return "user/editform";
    }
    /**
     * @方法名: /delete/{id}
     * @功能描述: 删除用户
     * @创建人: 黄梓莘
     * @创建时间： 2018-6-28
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult delete(@PathVariable Integer id,ModelMap map) {
        try {
            userService.delete(id);
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.failure(e.getMessage());
        }
        return JsonResult.success();
    }

    /**
     * @方法名: updatePwd，get
     * @功能描述: 返回修改密码表单
     * @创建人: 黄梓莘
     * @创建时间： 2018-6-28
     */
    @RequestMapping(value = "/updatePwd", method = RequestMethod.GET)
    public String updatePwd() {
        return "user/updatePwd";
    }

    /**
     * @方法名: updatePwd，post
     * @功能描述: 修改密码
     * @创建人: 黄梓莘
     * @创建时间： 2018-6-28
     */
    @RequestMapping(value = "/updatePwd", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult updatePwd(String oldPassword, String password1, String password2) {
        try {
            Subject subject = SecurityUtils.getSubject();
            Object principal = subject.getPrincipal();
            if(principal== null){
                return JsonResult.failure("您尚未登录");
            }
            userService.updatePwd((UserModel) principal, oldPassword, password1, password2);
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.failure(e.getMessage());
        }
        return JsonResult.success();
    }

    @RequestMapping(value = "/grant/{id}", method = RequestMethod.GET)
    public String grant(@PathVariable Integer id, ModelMap map) {
        UserModel user = userService.find(id);
        map.put("user", user);

//        Set<RoleModel> set = user.getRoles();
        List<RoleModel> set=roleService.findAll();
        List<Integer> roleIds = new ArrayList<Integer>();
        for (RoleModel role : set) {
            roleIds.add(role.getId());
        }
        map.put("roleIds", roleIds);

        List<RoleModel> roles = roleService.findAll();
        map.put("roles", roles);
        return "user/grant";
    }

    @ResponseBody
    @RequestMapping(value = "/grant/{id}", method = RequestMethod.POST)
    public JsonResult grant(@PathVariable Integer id,String[] roleIds, ModelMap map) {
        try {
            if (roleIds.length==0){
                throw new Exception("必须分配一个角色");
            }
            if (roleIds.length !=1){
                throw new Exception("只能分配一个角色");
            }
            userService.grant(id,roleIds);
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.failure(e.getMessage());
        }
        return JsonResult.success();
    }
    /**
     * @方法名: search
     * @功能描述: 带条件查询用户
     * @创建人: 黄梓莘
     * @创建时间： 2018-07-11
     */
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String search( ModelMap map) {
        return "user/searchForm";
    }

    @ResponseBody
    @RequestMapping(value = "/resetPsw/{id}", method = RequestMethod.POST)
    public JsonResult resetPsw(@PathVariable Integer id, ModelMap map) {
        try {
            Subject subject = SecurityUtils.getSubject();
            Object principal = subject.getPrincipal();
            if(principal== null){
                return JsonResult.failure("您尚未登录");
            }
            UserModel currentUser=(UserModel)principal;
            UserModel targetUser=userService.find(id);
            if(currentUser.getUserSession()>=targetUser.getUserSession()||targetUser.getUserSession()==1){
                throw new Exception("无权重置密码");
            }
            targetUser.setUserPassword(MD5Utils.md5("111111"));
            userService.saveOrUpdate(targetUser);
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.failure(e.getMessage());
        }
        return JsonResult.success();
    }

    @ResponseBody
    @RequestMapping(value = "/repay/{id}", method = RequestMethod.POST)
    public JsonResult repay(@PathVariable Integer id) {
        try {
            UserModel userModel=userService.find(id);
            userModel.setOverdueTotalAmount(0.0);
            userService.saveOrUpdate(userModel);
            List<BorrowModel> borrowModels=userService.findUnfinishedOrder(userModel.getUserId());
            for(BorrowModel borrowModel:borrowModels){
                borrowModel.setIsFinish(1);
                borrowService.saveOrUpdate(borrowModel);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.failure(e.getMessage());
        }
        return JsonResult.success();
    }
    @RequestMapping(value = "/selfrepay/{id}", method = RequestMethod.GET)
    public String selfrepay(@PathVariable Integer id, ModelMap map) {
        UserModel user = userService.find(id);
        map.put("user", user);

        return "user/repay";
    }
}
