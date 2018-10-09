package net.sppan.base.controller;

import net.sppan.base.common.JsonResult;
import net.sppan.base.common.utils.RandomUtil;
import net.sppan.base.entity.ConfigModel;
import net.sppan.base.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/configCtrl")
@SessionAttributes("EncryptKey")
public class ConfigCtrl extends BaseController {
    @Autowired
    private ConfigService configService;

    private String key=null;

    @RequestMapping(value = { "/", "/config" })
    public String index(ModelMap map) {
        return "config/config";
    }

    /**
     * @方法名: addOrEditConfig
     * @功能描述: 新建或编辑默认参数
     * @创建人: 黄梓莘
     * @创建时间： 2018-6-28
     */
    @RequestMapping("/addOrEditConfig")
    @ResponseBody
    public JsonResult addOrEditConfig(ConfigModel configModel){
        try {
            configService.saveOrUpdate(configModel);
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
    @RequestMapping(value = "/deleteConfig/{id}")
    @ResponseBody
    public JsonResult deleteConfig(@PathVariable Integer id) {
        try {
            configService.delete(id);
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
    public Page<ConfigModel> list() {
        Page<ConfigModel> page = configService.findAll(getPageRequest());
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
        return "config/form";
    }

    /**
     * @方法名: edit
     * @功能描述: 返回edit表单
     * @创建人: 黄梓莘
     * @创建时间： 2018-6-28
     */
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable Integer id, ModelMap map) {
        ConfigModel configModel = configService.find(id);
        map.put("configModel", configModel);
        key=RandomUtil.generateString(16);
        map.addAttribute("EncryptKey",key);
        return "config/form";
    }
}
