package net.sppan.base.quartz.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 定时任务
 *
 * @author Jackie
 * @date
 **/
public class HelloCucumber implements Job {

    /**
     * 定期获取申请试用用户并发送邮件
     *
     * @param context
     * @throws JobExecutionException
     */
    public void execute(JobExecutionContext context) throws JobExecutionException {

        System.out.println("hello");
    }
}
