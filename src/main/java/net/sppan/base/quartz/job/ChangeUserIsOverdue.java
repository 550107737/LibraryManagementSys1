package net.sppan.base.quartz.job;

import net.sppan.base.entity.BorrowModel;
import net.sppan.base.entity.UserModel;
import net.sppan.base.service.BorrowService;
import net.sppan.base.service.UserService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * 定时任务
 *
 * @author 黄梓莘
 * @date 2018-07-10
 **/
public class ChangeUserIsOverdue implements Job {

    @Autowired
    private UserService userService;
    @Autowired
    private BorrowService borrowService;
    /**
     * 0点更新用户是否存在超期书籍
     *
     * @param context
     * @throws JobExecutionException
     */
    public void execute(JobExecutionContext context) throws JobExecutionException {
        List<BorrowModel> borrowModelList=borrowService.findAll();
        Date date=new Date();
        System.out.println("开始更新用户逾期状态");
        for(BorrowModel borrowModel:borrowModelList){
            UserModel userModel=userService.findByUserId(borrowModel.getUserId());
            if(userModel.getIsOverdue()==1){
                continue;//已经存在逾期的话就不用更新了
            }
            if(borrowModel.getStatus()==0 && date.getTime()>borrowModel.getReturnTime().getTime()){
                userModel.setIsOverdue(1);
                try{
                    userService.saveOrUpdate(userModel);
                }catch(Exception e){
                    System.out.println("更新用户ID："+userModel.getId()+"数据失败");
                }
            }
        }
        System.out.println("用户逾期状态更新结束");
    }
}
