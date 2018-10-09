package net.sppan.base.quartz.job;

import net.sppan.base.dao.BookDao;
import net.sppan.base.entity.BookModel;
import net.sppan.base.entity.BooksCheckModel;
import net.sppan.base.service.BookService;
import net.sppan.base.service.BooksCheckService;
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
public class CheckBooks implements Job {

    @Autowired
    private BookService bookService;
    @Autowired
    private BooksCheckService booksCheckService;
    @Autowired
    private BookDao bookDao;

    /**
     * 0点更新用户是否存在超期书籍
     *
     * @param context
     * @throws JobExecutionException
     */
    public void execute(JobExecutionContext context) throws JobExecutionException {

        List<BookModel> bookModels=bookDao.findAllByInBoxAndCheckStatus(0,0);

        if(bookModels==null || bookModels.size()==0){
            System.out.println("无盘点异常书籍，盘点结束");
            return;
        }
        System.out.println("开始盘点书籍");
        Date date=new Date();
        for(BookModel bookModel:bookModels){
            try{
                BooksCheckModel booksCheckModel=new BooksCheckModel();
                booksCheckModel.setBooksId(bookModel.getBooksId());
                booksCheckModel.setBookcaseId(bookModel.getBookcaseId());
                booksCheckModel.setBoxId(bookModel.getBoxId());
                booksCheckModel.setBookLocation(bookModel.getBooksPosition());
                booksCheckModel.setBookRfid(bookModel.getBookRfid());
                booksCheckModel.setCheckStatus(0);
                booksCheckModel.setCheckDate(date);
                booksCheckService.saveOrUpdate(booksCheckModel);
            }catch(Exception e){
                System.out.println("书籍ID："+bookModel.getBooksId()+"盘点失败"); }
        }
        System.out.println("用户逾期状态更新结束");
    }
}
