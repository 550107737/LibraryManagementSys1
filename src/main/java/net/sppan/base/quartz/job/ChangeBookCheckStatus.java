package net.sppan.base.quartz.job;

import net.sppan.base.entity.BookModel;
import net.sppan.base.entity.BorrowModel;
import net.sppan.base.entity.UserModel;
import net.sppan.base.service.BookService;
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
 * @date 2018-08-03
 **/
public class ChangeBookCheckStatus implements Job {

    @Autowired
    private BookService bookService;

    /**
     * 0点先将所有书籍的盘点状态置0
     *
     * @param context
     * @throws JobExecutionException
     */
    public void execute(JobExecutionContext context) throws JobExecutionException {
        List<BookModel> bookModels=bookService.findAll();
        System.out.println("开始置0书籍盘点状态");
        for(BookModel bookModel:bookModels){
            try{
                bookModel.setCheckStatus(0);
                bookService.saveOrUpdate(bookModel);
            }catch(Exception e){
                System.out.println("书籍ID："+bookModel.getBooksId()+"置0盘点状态失败"); }
        }
        System.out.println("用户逾期状态更新结束");
    }
}
