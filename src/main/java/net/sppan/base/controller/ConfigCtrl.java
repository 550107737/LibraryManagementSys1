package net.sppan.base.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import net.sppan.base.common.JsonResult;
import net.sppan.base.common.utils.CryptoUtil;
import net.sppan.base.common.utils.JsonUtil;
import net.sppan.base.common.utils.RandomUtil;
import net.sppan.base.config.consts.LabConsts;
import net.sppan.base.entity.BookModel;
import net.sppan.base.entity.BookboxModel;
import net.sppan.base.entity.ConfigModel;
import net.sppan.base.entity.ParamModel;
import net.sppan.base.service.BookService;
import net.sppan.base.service.BookboxService;
import net.sppan.base.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/configCtrl")
public class ConfigCtrl extends BaseController {
    @Autowired
    private ConfigService configService;
    @Autowired
    private BookboxService bookboxService;
    @Autowired
    private BookService bookService;
    @Autowired
    private API api;
    @RequestMapping(value = { "/", "/config" })
    public String index(ModelMap map) {

        /*
        String jsonListStr="{\"20000007\":\"00000002\",\"20000008\":\"00000009\",\"20000009\":\"00000005,00000006,00000007\"}";
        ParamModel param=new ParamModel();
        Date date=new Date();
        param.setTime(date.getTime()+"");
        String userId="admin";
        param.setBookcaseSN("10000003");
        String serverAPIKey = CryptoUtil.md5(LabConsts.SECRET_KEY).concat(CryptoUtil.md5(param.getBookcaseSN())).
                concat(param.getTime()).concat(CryptoUtil.md5(userId));
        String serverAPIKey1 = CryptoUtil.md5(serverAPIKey);
        param.setToken(serverAPIKey1);
        System.out.println(serverAPIKey);
        api.borrowBook(param,"admin",jsonListStr);
        */
        /*
        ParamModel paramModel=new ParamModel();
        Date date=new Date();
        paramModel.setTime(date.getTime()+"");
        paramModel.setBookcaseSN("10000003");
        String serverAPIKey = CryptoUtil.md5(LabConsts.SECRET_KEY).concat(CryptoUtil.md5(param.getBookcaseSN())).
                concat(param.getTime()).concat(CryptoUtil.md5(userId));
        String serverAPIKey1 = CryptoUtil.md5(serverAPIKey);
        param.setToken(serverAPIKey1);
        api.doorOverTime(paramModel);
        */
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
        return "config/form";
    }
}
