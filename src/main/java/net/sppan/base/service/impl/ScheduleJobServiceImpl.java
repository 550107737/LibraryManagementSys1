package net.sppan.base.service.impl;

import com.dexcoder.commons.bean.BeanConverter;
import com.dexcoder.dal.JdbcDao;
import com.dexcoder.dal.build.Criteria;
import net.sppan.base.dao.ScheduleJobDao;
import net.sppan.base.dao.support.IBaseDao;
import net.sppan.base.entity.ScheduleJob;
import net.sppan.base.service.ScheduleJobService;
import net.sppan.base.common.utils.ScheduleUtils;
import net.sppan.base.service.support.impl.BaseServiceImpl;
import net.sppan.base.vo.ScheduleJobVo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * author : fengjing
 * createTime : 2016-08-04
 * description : 定时任务服务实现
 * version : 1.0
 */
@Service
public class ScheduleJobServiceImpl extends BaseServiceImpl<ScheduleJob, Integer> implements ScheduleJobService {

    /** 调度工厂Bean */
    @Autowired
    private Scheduler scheduler;

    @Autowired
    private ScheduleJobDao scheduleJobDao;


    @Override
    public IBaseDao<ScheduleJob, Integer> getBaseDao() {
        return this.scheduleJobDao;
    }

    public void initScheduleJob() throws Exception{
        List<ScheduleJob> scheduleJobList = scheduleJobDao.findAll();
        if (CollectionUtils.isEmpty(scheduleJobList)) {
            return;
        }
        for (ScheduleJob scheduleJob : scheduleJobList) {

            CronTrigger cronTrigger = ScheduleUtils.getCronTrigger(scheduler, scheduleJob.getJobName(), scheduleJob.getJobGroup());

            //不存在，创建一个
            if (cronTrigger == null) {
                ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
            } else {
                //已存在，那么更新相应的定时设置
                ScheduleUtils.updateScheduleJob(scheduler, scheduleJob);
            }
        }
    }

    public void insert(ScheduleJobVo scheduleJobVo) throws Exception{
        ScheduleJob scheduleJob = scheduleJobVo.getTargetObject(ScheduleJob.class);
        ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
        save(scheduleJob);
    }

    public void update(ScheduleJobVo scheduleJobVo) throws Exception{
        ScheduleJob scheduleJob = scheduleJobVo.getTargetObject(ScheduleJob.class);
        ScheduleUtils.updateScheduleJob(scheduler, scheduleJob);
        update(scheduleJob);
    }

    public void delUpdate(ScheduleJobVo scheduleJobVo) throws Exception{
        ScheduleJob scheduleJob = scheduleJobVo.getTargetObject(ScheduleJob.class);
        //先删除
        ScheduleUtils.deleteScheduleJob(scheduler, scheduleJob.getJobName(), scheduleJob.getJobGroup());
        //再创建
        ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
        //数据库直接更新即可
        update(scheduleJob);
    }

    public void delete1(Integer scheduleJobId)throws Exception {
        ScheduleJob scheduleJob = scheduleJobDao.findByScheduleJobId(scheduleJobId.longValue());
        //删除运行的任务
        ScheduleUtils.deleteScheduleJob(scheduler, scheduleJob.getJobName(), scheduleJob.getJobGroup());
        //删除数据
        scheduleJobDao.deleteByScheduleJobId(scheduleJobId.longValue());
    }

    public void runOnce(Integer scheduleJobId) throws Exception{
        ScheduleJob scheduleJob = scheduleJobDao.findByScheduleJobId(scheduleJobId.longValue());
        ScheduleUtils.runOnce(scheduler, scheduleJob.getJobName(), scheduleJob.getJobGroup());
    }

    public void pauseJob(Integer scheduleJobId) throws Exception{
        ScheduleJob scheduleJob = scheduleJobDao.findByScheduleJobId(scheduleJobId.longValue());
        scheduleJob.setStatus("0");//设置状态为暂停
        ScheduleUtils.pauseJob(scheduler, scheduleJob.getJobName(), scheduleJob.getJobGroup());
        update(scheduleJob);
    }

    public void resumeJob(Integer scheduleJobId)throws Exception {
        ScheduleJob scheduleJob = scheduleJobDao.findByScheduleJobId(scheduleJobId.longValue());
        scheduleJob.setStatus("1");//设置状态为正常
        ScheduleUtils.resumeJob(scheduler, scheduleJob.getJobName(), scheduleJob.getJobGroup());
        update(scheduleJob);
    }

    public ScheduleJobVo get(Integer scheduleJobId) {
        ScheduleJob scheduleJob = scheduleJobDao.findByScheduleJobId(scheduleJobId.longValue());
        return scheduleJob.getTargetObject(ScheduleJobVo.class);
    }

    public List<ScheduleJobVo> queryList(ScheduleJobVo scheduleJobVo) {

        List<ScheduleJob> scheduleJobs = scheduleJobDao.findAll();//存疑

        List<ScheduleJobVo> scheduleJobVoList = BeanConverter.convert(ScheduleJobVo.class, scheduleJobs);
        try {
            for (ScheduleJobVo vo : scheduleJobVoList) {

                JobKey jobKey = ScheduleUtils.getJobKey(vo.getJobName(), vo.getJobGroup());
                List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
                if (CollectionUtils.isEmpty(triggers)) {
                    continue;
                }

                //这里一个任务可以有多个触发器， 但是我们一个任务对应一个触发器，所以只取第一个即可，清晰明了
                Trigger trigger = triggers.iterator().next();
                vo.setJobTrigger(trigger.getKey().getName());

                Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
                vo.setStatus(triggerState.name());

                if (trigger instanceof CronTrigger) {
                    CronTrigger cronTrigger = (CronTrigger) trigger;
                    String cronExpression = cronTrigger.getCronExpression();
                    vo.setCronExpression(cronExpression);
                }
            }
        } catch (SchedulerException e) {
            //演示用，就不处理了
        }
        return scheduleJobVoList;
    }

    /**
     * 获取运行中的job列表
     * @return
     */
    public List<ScheduleJobVo> queryExecutingJobList() {
        try {
            // 存放结果集
            List<ScheduleJobVo> jobList = new ArrayList<ScheduleJobVo>();

            // 获取scheduler中的JobGroupName
            for (String group:scheduler.getJobGroupNames()){
                // 获取JobKey 循环遍历JobKey
                for(JobKey jobKey:scheduler.getJobKeys(GroupMatcher.<JobKey>groupEquals(group))){
                    JobDetail jobDetail = scheduler.getJobDetail(jobKey);
                    JobDataMap jobDataMap = jobDetail.getJobDataMap();
                    ScheduleJob scheduleJob = (ScheduleJob)jobDataMap.get(ScheduleJobVo.JOB_PARAM_KEY);
                    ScheduleJobVo scheduleJobVo = new ScheduleJobVo();
                    BeanConverter.convert(scheduleJobVo,scheduleJob);
                    List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
                    Trigger trigger = triggers.iterator().next();
                    Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
                    scheduleJobVo.setJobTrigger(trigger.getKey().getName());
                    scheduleJobVo.setStatus(triggerState.name());
                    if (trigger instanceof CronTrigger) {
                        CronTrigger cronTrigger = (CronTrigger) trigger;
                        String cronExpression = cronTrigger.getCronExpression();
                        scheduleJobVo.setCronExpression(cronExpression);
                    }
                    // 获取正常运行的任务列表
                    if(triggerState.name().equals("NORMAL")){
                        jobList.add(scheduleJobVo);
                    }
                }
            }

            /** 非集群环境获取正在执行的任务列表 */
            /**
            List<JobExecutionContext> executingJobs = scheduler.getCurrentlyExecutingJobs();
            List<ScheduleJobVo> jobList = new ArrayList<ScheduleJobVo>(executingJobs.size());
            for (JobExecutionContext executingJob : executingJobs) {
                ScheduleJobVo job = new ScheduleJobVo();
                JobDetail jobDetail = executingJob.getJobDetail();
                JobKey jobKey = jobDetail.getKey();
                Trigger trigger = executingJob.getTrigger();
                job.setJobName(jobKey.getName());
                job.setJobGroup(jobKey.getGroup());
                job.setJobTrigger(trigger.getKey().getName());
                Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
                job.setStatus(triggerState.name());
                if (trigger instanceof CronTrigger) {
                    CronTrigger cronTrigger = (CronTrigger) trigger;
                    String cronExpression = cronTrigger.getCronExpression();
                    job.setCronExpression(cronExpression);
                }
                jobList.add(job);
            }*/

            return jobList;
        } catch (SchedulerException e) {
            return null;
        }

    }

    @Override
    public Page<ScheduleJob> findAllByLike(String searchText, PageRequest pageRequest) {
        if(StringUtils.isBlank(searchText)){
            searchText = "";
        }
        return scheduleJobDao.findAllByDescriptionContaining(searchText,pageRequest);
    }

    @Override
    public ScheduleJob findByLongId(Integer id) {
        return scheduleJobDao.findByScheduleJobId(id.longValue());
    }
}
