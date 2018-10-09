package net.sppan.base.quartz.job;

import net.sppan.base.entity.BorrowHistoryModel;
import net.sppan.base.entity.BorrowModel;
import net.sppan.base.entity.UserModel;
import net.sppan.base.service.BorrowHistoryService;
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
public class TransferToHistoryBorrow implements Job {

    @Autowired
    private BorrowService borrowService;
    @Autowired
    private BorrowHistoryService borrowHistoryService;
    /**
     * 0点更新用户是否存在超期书籍
     *
     * @param context
     * @throws JobExecutionException
     */
    public void execute(JobExecutionContext context) throws JobExecutionException {
        List<BorrowModel> borrowModelList=borrowService.findAll();
        System.out.println("开始迁移至历史借阅表");
        for(BorrowModel borrowModel:borrowModelList){
            if(borrowModel.getIsFinish()==1){
                BorrowHistoryModel borrowHistoryModel=new BorrowHistoryModel();
                borrowHistoryModel.setActualBookcaseId(borrowModel.getActualBookcaseId());
                borrowHistoryModel.setActualBoxId(borrowModel.getActualBoxId());
                borrowHistoryModel.setActualTime(borrowModel.getActualTime());
                borrowHistoryModel.setAmount(borrowModel.getAmount());
                borrowHistoryModel.setBookRfid(borrowModel.getBookRfid());
                borrowHistoryModel.setBorrowTime(borrowModel.getBorrowTime());
                borrowHistoryModel.setIsFinish(borrowModel.getIsFinish());
                borrowHistoryModel.setIsPay(borrowModel.getIsPay());
                borrowHistoryModel.setReturnBookcaseId(borrowModel.getReturnBookcaseId());
                borrowHistoryModel.setReturnBoxId(borrowModel.getReturnBoxId());
                borrowHistoryModel.setReturnTime(borrowModel.getReturnTime());
                borrowHistoryModel.setStatus(borrowModel.getStatus());
                borrowHistoryModel.setUserId(borrowModel.getUserId());
                borrowHistoryModel.setActualLocation(borrowModel.getActualLocation());
                borrowHistoryModel.setReturnLocation(borrowModel.getReturnLocation());
                try{
                    borrowHistoryService.saveOrUpdate(borrowHistoryModel);
                    borrowService.delete(borrowModel.getBorrowId());
                }catch(Exception e){
                    System.out.println("迁移"+borrowModel.getBorrowId()+"数据失败");
                }
            }
        }
        System.out.println("迁移结束");
    }
}
