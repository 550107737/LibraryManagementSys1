package net.sppan.base.quartz.event;

import net.sppan.base.service.ScheduleJobService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * author : fengjing
 * createTime : 2016-08-04
 * description : 定时任务初始化
 * version : 1.0
 */
@Component
public class ScheduleJobInit {

    /** 日志对象 */
    private static final Logger LOG = LoggerFactory.getLogger(ScheduleJobInit.class);

    /** 定时任务service */
    @Autowired
    private ScheduleJobService scheduleJobService;

    /**
     * 项目启动时初始化
     */
    @PostConstruct
    public void init() {

        if (LOG.isInfoEnabled()) {
            LOG.info("init");
        }
        try{
            scheduleJobService.initScheduleJob();
            if (LOG.isInfoEnabled()) {
                LOG.info("end");
            }
        }catch(Exception e){
            LOG.info("error",e);
        }



    }

}
