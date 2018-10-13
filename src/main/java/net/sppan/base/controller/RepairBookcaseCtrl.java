package net.sppan.base.controller;

import net.sppan.base.common.JsonResult;
import net.sppan.base.entity.RepairBookcaseModel;
import net.sppan.base.service.RepairBookcaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/repairBookcaseCtrl")
public class RepairBookcaseCtrl extends BaseController {
    @Autowired
    private RepairBookcaseService repairBookcaseService;

    @RequestMapping(value = { "/", "/index" })
    public String index(ModelMap map) {
        return "repairbookcase/repairbookcase";
    }

    /**
     * @方法名: addOrEditConfig
     * @功能描述: 新建或编辑默认参数
     * @创建人: 黄梓莘
     * @创建时间： 2018-6-28
     */
    @RequestMapping("/addOrEdit")
    @ResponseBody
    public JsonResult addOrEditConfig(RepairBookcaseModel repairBookcaseModel){
        try {
            repairBookcaseService.saveOrUpdate(repairBookcaseModel);
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.failure(e.getMessage());
        }
        return JsonResult.success();
    }
    /**
     * @方法名: deleteConfig
     * @功能描述: 删除默认参数
     * @创建人: 黄梓莘
     * @创建时间： 2018-6-28
     */
    @RequestMapping(value = "/del/{id}")
    @ResponseBody
    public JsonResult deleteConfig(@PathVariable Integer id) {
        try {
            repairBookcaseService.delete(id);
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
    @RequestMapping("/list")
    @ResponseBody
    public Page<RepairBookcaseModel> list() {
        Page<RepairBookcaseModel> page = repairBookcaseService.findAll(getPageRequest());
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
        return "repairbookcase/form";
    }

    /**
     * @方法名: edit
     * @功能描述: 返回edit表单
     * @创建人: 黄梓莘
     * @创建时间： 2018-6-28
     */
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable Integer id, ModelMap map) {
        RepairBookcaseModel repairBookcaseModel = repairBookcaseService.find(id);
        map.put("repairBookcaseModel", repairBookcaseModel);
        return "repairbookcase/form";
    }
}
