package net.sppan.base.quartz.job;

import net.sppan.base.entity.*;
import net.sppan.base.service.*;
import org.apache.catalina.User;
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
public class ChangeRfid implements Job {

    @Autowired
    private UserService userService;
    @Autowired
    private BorrowService borrowService;
    @Autowired
    private BorrowHistoryService borrowHistoryService;
    @Autowired
    private BookService bookService;
    @Autowired
    private BookcaseService bookcaseService;
    @Autowired
    private BookboxService bookboxService;
    @Autowired
    private ChangeRfidService changeRfidService;
    /**
     * 0点将损坏RFID书籍更新至对应主表
     *
     * @param context
     * @throws JobExecutionException
     */
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("开始更换rfid");
        List<ChangeRfidModel> changeRfidModels=changeRfidService.findAllByIsFinish(0);
        if(changeRfidModels.size()==0){
            System.out.println("没有需要更换的rfid");
            return;
        }
        for(ChangeRfidModel changeRfidModel:changeRfidModels){
            try{
                switch (changeRfidModel.getType()){
                    case 1:
                        UserModel userModel=userService.findByUserId(changeRfidModel.getOldRfid());
                        if(userModel==null){
                            throw new Exception("RFID编号错误，查询不到旧RFID号");
                        }
                        userModel.setUserId(changeRfidModel.getNewRfid());
                        userService.saveOrUpdate(userModel);

                        List<BorrowModel> borrowModels=borrowService.findAllByUserId(changeRfidModel.getOldRfid());
                        if(borrowModels.size()>0){
                            for(BorrowModel borrowModel:borrowModels){
                                borrowModel.setUserId(changeRfidModel.getNewRfid());
                                borrowService.saveOrUpdate(borrowModel);
                            }
                        }

                        List<BorrowHistoryModel> borrowHistoryModels=borrowHistoryService.findAllByUserId(changeRfidModel.getOldRfid());
                        if(borrowHistoryModels.size()>0){
                            for(BorrowHistoryModel borrowHistoryModel:borrowHistoryModels){
                                borrowHistoryModel.setUserId(changeRfidModel.getNewRfid());
                                borrowHistoryService.saveOrUpdate(borrowHistoryModel);
                            }
                        }

                        break;
                    case 2:
                        BookModel bookModel=bookService.findByBookRfid(changeRfidModel.getOldRfid());
                        if(bookModel==null){
                            throw new Exception("RFID编号错误，查询不到旧RFID号");
                        }


//                        List<BorrowModel> borrowModels1=borrowService.findAllByBookRfid(changeRfidModel.getOldRfid());
//                        if(borrowModels1.size()>0){
//                            for(BorrowModel borrowModel:borrowModels1){
//                                borrowModel.setBookRfid(changeRfidModel.getNewRfid());
//                                borrowService.saveOrUpdate(borrowModel);
//                            }
//                        }
//
//                        List<BorrowHistoryModel> borrowHistoryModels1=borrowHistoryService.findAllByBookRfid(changeRfidModel.getOldRfid());
//                        if(borrowHistoryModels1.size()>0){
//                            for(BorrowHistoryModel borrowHistoryModel:borrowHistoryModels1){
//                                borrowHistoryModel.setBookRfid(changeRfidModel.getNewRfid());
//                                borrowHistoryService.saveOrUpdate(borrowHistoryModel);
//                            }
//                        }

                        break;
                    case 3:
                        BookcaseModel bookcaseModel=bookcaseService.findByBookcaseRfid(changeRfidModel.getOldRfid());
                        if(bookcaseModel==null){
                            throw new Exception("RFID编号错误，查询不到旧RFID号");
                        }
                        bookcaseModel.setBookcaseRfid(changeRfidModel.getNewRfid());
                        bookcaseService.saveOrUpdate(bookcaseModel);
                        break;
                    case 4:
                        BookboxModel bookboxModel=bookboxService.findByBoxRfid(changeRfidModel.getOldRfid());
                        if(bookboxModel==null){
                            throw new Exception("RFID编号错误，查询不到旧RFID号");
                        }
                        bookboxModel.setBoxRfid(changeRfidModel.getNewRfid());
                        bookboxService.saveOrUpdate(bookboxModel);
                        break;
                }
                changeRfidModel.setIsFinish(1);
                changeRfidService.saveOrUpdate(changeRfidModel);
            }catch (Exception e){
                System.out.println("ID:"+changeRfidModel.getChangeId()+"更换rfid失败，原因："+e.toString());
            }

        }
        System.out.println("更换rfid结束");
    }
}
