package net.sppan.base.controller;

import net.sppan.base.common.JsonResult;
import net.sppan.base.entity.ScheduleJob;
import net.sppan.base.service.ScheduleJobService;
import net.sppan.base.vo.ScheduleJobVo;
import org.apache.commons.lang.StringUtils;
import org.quartz.CronScheduleBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * author : fengjing
 * createTime : 2016-08-04
 * description : 定时任务控制器
 * version : 1.0
 */
@Controller
@RequestMapping("/quartzCtrl")
public class ScheduleJobController extends BaseController{

    /** job service */
    @Autowired
    private ScheduleJobService scheduleJobService;

    /**
     * @方法名: addOrEditQuartz
     * @功能描述: 添加或修改定时任务
     * @创建人: 黄梓莘
     * @创建时间： 2018-07-09
     */
    @RequestMapping(value = "addOrEditQuartz")
    @ResponseBody
    public JsonResult saveScheduleJob(ScheduleJobVo scheduleJobVo) {
        try{
            Class.forName(scheduleJobVo.getUrl());//检查url是否输入正确
            //验证cron表达式是否正确
            CronScheduleBuilder scheduleBuilder=null;
            try{
                scheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleJobVo.getCronExpression());
            }catch(Exception e){
                return JsonResult.failure("Cron表达式错误！");
            }finally {
                scheduleBuilder=null;
            }
            scheduleJobVo.setIsSync(true);//貌似没什么用的属性，随便设
            scheduleJobVo.setStatus("1");//由于不可直接修改状态，所以新增直接设置为运行；修改为先删除后增加，所以也直接设置为运行
            if (scheduleJobVo.getScheduleJobId() == null) {
                scheduleJobService.insert(scheduleJobVo);
            } else{
                scheduleJobService.delUpdate(scheduleJobVo);//删了再加，直接更新无效
            }
//            else if (StringUtils.equalsIgnoreCase(scheduleJobVo.getKeywords(),"delUpdate")){
//                //直接拿keywords存一下，就不另外重新弄了
//                scheduleJobService.delUpdate(scheduleJobVo);
//            }else {
//                scheduleJobService.update(scheduleJobVo);
//            }
        }catch(ClassNotFoundException e){
            return JsonResult.failure("URL输入错误！");
        }catch(Exception e){
            e.printStackTrace();
            return JsonResult.failure(e.getMessage());
        }
        return JsonResult.success();
    }

    /**
     * @方法名: deleteQuartz
     * @功能描述: 删除定时任务
     * @创建人: 黄梓莘
     * @创建时间： 2018-07-09
     */
    @RequestMapping(value = "deleteQuartz/{id}")
    @ResponseBody
    public JsonResult deleteScheduleJob(@PathVariable Integer id) {
        try{
            scheduleJobService.delete1(id);
        }catch(Exception e){
            e.printStackTrace();
            return JsonResult.failure(e.getMessage());
        }
        return JsonResult.success();
    }

    /**
     * @方法名: runOnce
     * @功能描述: 运行一次定时任务
     * @创建人: 黄梓莘
     * @创建时间： 2018-07-09
     */
    @RequestMapping(value = "runOnce/{id}")
    @ResponseBody
    public JsonResult runOnceScheduleJob(@PathVariable Integer id) {
        try{
            scheduleJobService.runOnce(id);
        }catch (Exception e){
            e.printStackTrace();
            return JsonResult.failure(e.getMessage());
        }
        return JsonResult.success();
    }

    /**
     * @方法名: pauseJob
     * @功能描述: 暂停定时任务
     * @创建人: 黄梓莘
     * @创建时间： 2018-07-09
     */
    @RequestMapping(value = "pauseJob/{id}")
    @ResponseBody
    public JsonResult pauseScheduleJob(@PathVariable Integer id) {
        try{
            scheduleJobService.pauseJob(id);
        }catch (Exception e){
            e.printStackTrace();
            return JsonResult.failure(e.getMessage());
        }
        return JsonResult.success();
    }

    /**
     * @方法名: resumeJob
     * @功能描述: 恢复定时任务
     * @创建人: 黄梓莘
     * @创建时间： 2018-07-09
     */
    @RequestMapping(value = "resumeJob/{id}")
    @ResponseBody
    public JsonResult resumeScheduleJob(@PathVariable Integer id) {
        try{
            scheduleJobService.resumeJob(id);
        }catch (Exception e){
            e.printStackTrace();
            return JsonResult.failure(e.getMessage());
        }
        return JsonResult.success();
    }





    /**
     * @方法名: index
     * @功能描述: 返回定时任务主页面
     * @创建人: 黄梓莘
     * @创建时间： 2018-07-09
     */
    @RequestMapping(value = { "/", "/index" })
    public String index(ModelMap map) {
        //return "redirect:list-schedule-job";
        return "quartz/quartz";
    }

    /**
     * @方法名: list
     * @功能描述: 查询所有定时任务
     * @创建人: 黄梓莘
     * @创建时间： 2018-07-09
     */
    @RequestMapping("/list")
    @ResponseBody
    public Page<ScheduleJob> list(
            @RequestParam(value="searchText",required=false) String searchText
    ) {
        Page<ScheduleJob> page = scheduleJobService.findAllByLike(searchText, getPageRequest());
        return page;
    }

    /**
     * @方法名: add
     * @功能描述: 返回增加定时任务form
     * @创建人: 黄梓莘
     * @创建时间： 2018-7-9
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(ModelMap map) {
        return "quartz/form";
    }
    /**
     * @方法名: edit
     * @功能描述: 返回编辑定时任务form
     * @创建人: 黄梓莘
     * @创建时间： 2018-7-9
     */
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable Integer id, ModelMap map) {
        ScheduleJob scheduleJob = scheduleJobService.findByLongId(id);
        map.put("scheduleJob", scheduleJob);

        return "quartz/form";
    }
}
