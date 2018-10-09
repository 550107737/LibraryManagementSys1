package net.sppan.base.controller;

import net.sppan.base.common.JsonResult;


import net.sppan.base.entity.RoleModel;
import net.sppan.base.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/roleCtrl")
public class RoleCtrl extends BaseController {
    @Autowired
    private RoleService roleService;
    /**
     * @方法名: role
     * @功能描述: 返回用户权限主页面
     * @创建人: 黄梓莘
     * @创建时间： 2018-6-28
     */
    @RequestMapping(value = { "/", "/role" })
    public String index() {
        return "admin/role/index";
    }

    /**
     * @方法名: addOrEditRole
     * @功能描述: 新建或编辑用户权限
     * @创建人: 黄梓莘
     * @创建时间： 2018-6-28
     */
    @RequestMapping("/addOrEditRole")
    @ResponseBody
    public JsonResult addOrEditRole(RoleModel configModel){
        try {
            roleService.saveOrUpdate(configModel);
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.failure(e.getMessage());
        }
        return JsonResult.success();
    }
    /**
     * @方法名: deleteRole
     * @功能描述: 删除用户
     * @创建人: 黄梓莘
     * @创建时间： 2018-6-28
     */
    @RequestMapping(value = "/deleteRole/{id}")
    @ResponseBody
    public JsonResult deleteConfig(@PathVariable Integer id) {
        try {
            roleService.delete(id);
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.failure(e.getMessage());
        }
        return JsonResult.success();
    }
    /**
     * @方法名: list
     * @功能描述: 查询权限
     * @创建人: 黄梓莘
     * @创建时间： 2018-6-28
     */
    @RequestMapping("/list")
    @ResponseBody
    public Page<RoleModel> list(
            @RequestParam(value="searchText",required=false) String searchText
    ) {
        Page<RoleModel> page = roleService.findAllByLike(searchText, getPageRequest());
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
        return "admin/role/form";
    }

    /**
     * @方法名: edit
     * @功能描述: 返回edit表单
     * @创建人: 黄梓莘
     * @创建时间： 2018-6-28
     */
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable Integer id, ModelMap map) {
        RoleModel roleModel = roleService.find(id);
        map.put("role", roleModel);
        return "admin/role/form";
    }

}
