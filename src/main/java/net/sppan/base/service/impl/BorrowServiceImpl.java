package net.sppan.base.service.impl;


import net.sppan.base.dao.BorrowDao;
import net.sppan.base.dao.support.IBaseDao;

import net.sppan.base.entity.*;
import net.sppan.base.service.*;
import net.sppan.base.service.support.impl.BaseServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static net.sppan.base.config.consts.LabConsts.BORROWABLE_CHECK_FULLY;
import static net.sppan.base.config.consts.LabConsts.BORROWABLE_CHECK_LOGIN;


@Service
@Transactional(rollbackFor = {Exception.class})
public class BorrowServiceImpl extends BaseServiceImpl<BorrowModel, Integer> implements BorrowService {

	@Autowired
	private BorrowDao borrowDao;

	@Autowired
	private BorrowService borrowService;

	@Autowired
	private UserService userService;

	@Autowired
	private BookService bookService;

	@Autowired
	private ConfigService configService;

	@Autowired
	private BookboxService bookboxService;

	@Autowired
	private BookcaseService bookcaseService;
	
	@Override
	public IBaseDao<BorrowModel, Integer> getBaseDao() {
		return this.borrowDao;
	}

	@Override
	public Long countByUserIdAndStatus(String userId, Integer status) {
		return borrowDao.countByUserIdAndStatus(userId,status);
	}

	@Override
	public BorrowModel findByUserIdAndBookRfidAndStatus(String userid, String bookRfid,Integer status) {
		return borrowDao.findByUserIdAndBookRfidAndStatus(userid,bookRfid,status);
	}

	@Override
	public BorrowModel findByUserId(String username) {
		return borrowDao.findByUserId(username);
	}

	@Override
	public void saveOrUpdate(BorrowModel borrowModel) {
		if(borrowModel.getBorrowId() != null){
			borrowModel.setUpdateTime(new Date());
			update(borrowModel);
		}else{

			borrowModel.setUpdateTime(new Date());

			save(borrowModel);
		}
	}
	
	

	@Override
	public void delete(Integer id) {
		super.delete(id);
	}


	@Override
	public Page<BorrowModel> findAllByLike(String userId,String bookRfid, PageRequest pageRequest) {
		if(StringUtils.isBlank(userId)){
			userId = "";
		}
		if(StringUtils.isBlank(bookRfid)){
			bookRfid = "";
		}
		return borrowDao.findAllByUserIdContainingAndBookRfidContaining(userId,bookRfid,pageRequest);
	}


	@Override
	public void changeBookStatus(BorrowModel borrowModel,int type) throws Exception{//type=0用户借书新增，1管理员编辑，2用户还书
		BookModel bookModel=bookService.findByBookRfid(borrowModel.getBookRfid());
		if(type==0){
			Date shouldReturnTime=getShouldReturnTime(borrowModel);
			bookModel.setRepayTime(shouldReturnTime);//应还时间
			bookModel.setBooksStatus(1);//状态已借出
		}
		if(type==2){
			bookModel.setBooksStatus(0);//状态改为可借
			if(borrowModel.getActualBoxId()!=null){
				bookModel.setBoxId(borrowModel.getActualBoxId());
				bookModel.setBookcaseId(borrowModel.getActualBookcaseId());
			}
			bookModel.setBooksPosition(borrowModel.getActualLocation());
		}
		bookService.saveOrUpdate(bookModel);

	}

	@Override
	public void checkUserBorrowable(BorrowModel borrowModel,int type) throws Exception{
		UserModel userModel=userService.findByUserId(borrowModel.getUserId());
		if(userModel==null){
			throw new Exception("查无此用户");
		}
		Date date=new Date();
		if((type==BORROWABLE_CHECK_FULLY || type== BORROWABLE_CHECK_LOGIN ) && date.getTime()>userModel.getSidTime().getTime()){
			throw new Exception("证件已过有效期，无法登录书柜！");
		}
		if(type ==BORROWABLE_CHECK_FULLY && userModel.getRemainNum()<=0){
			throw new Exception("超过最大可借数量，请先归还后再进行借阅！");
		}
		if((type==BORROWABLE_CHECK_FULLY || type== BORROWABLE_CHECK_LOGIN )  && userModel.getUserStatus()!=0){
			throw new Exception("账户状态异常，无法登录书柜！");
		}
		if(type==BORROWABLE_CHECK_FULLY && userModel.getIsOverdue()==1){
			throw new Exception("存在超期未还的书籍，请先归还后再进行借阅！");
		}
		if(type==BORROWABLE_CHECK_FULLY && userModel.getOverdueTotalAmount()>0){
			throw new Exception("存在未缴款的罚款，请先缴款后再进行借阅！");
		}

	}

	@Override
	public void checkBookBorrowable(BorrowModel borrowModel,int type) throws Exception{
		BookModel bookModel=bookService.findByBookRfid(borrowModel.getBookRfid());
		if(bookModel==null){
			throw new Exception("查无此书！");
		}
		if(type ==0 && bookModel.getBooksStatus()!=0){
			throw new Exception("该书籍当前不可借阅");
		}
		if(type ==2 && bookModel.getBooksStatus()!=1){
			throw new Exception("该书籍当前不可归还");
		}
	}

	@Override
	public void changeUserAction(BorrowModel borrowModel) throws Exception{
		UserModel userModel=userService.findByUserId(borrowModel.getUserId());
		Long borrowNum=borrowDao.countByUserIdAndStatus(borrowModel.getUserId(),0);//根据userid和书本未还状态在borrow中查询一共借了几本书
		userModel.setBorrowNum(borrowNum.intValue());//设置学生已借数量
		ConfigModel configModel=configService.find(1);
		Integer num=userModel.getUserRole()==0 ? configModel.getStuMax():configModel.getTeacherMax();//学生或教师的最大可借数量
		userModel.setRemainNum(num-borrowNum.intValue());
		if(borrowModel.getIsPay()==1 && borrowModel.getAmount()>0){//如果书籍超期，则更新用户的欠款金额
			userModel.setOverdueTotalAmount(userModel.getOverdueTotalAmount()+borrowModel.getAmount());
			//还书时如果欠费，则用户状态里isoverdue一定为1
			//开始判断用户是否还存在其他超期未还书籍，若全都还了则将isoverdue改成0
			List<BorrowModel> borrowModels=borrowService.findAllByUserId(userModel.getUserId());
			boolean flag=true;Date date=new Date();
			for(BorrowModel bm:borrowModels){
				if(bm.getStatus()==0 && date.getTime()>bm.getReturnTime().getTime()){
					flag=false;
					break;
				}
			}
			if(flag){
				userModel.setIsOverdue(0);
			}
		}
		userService.saveOrUpdate(userModel);
	}

	@Override
	public void changeCaseAndBox(BorrowModel borrowModel,int type) throws Exception {//type=0用户借书新增，2用户还书，应该不存在type为1的情况
		int a=0;BookModel bookModel=null;BookboxModel bookboxModel=null;
		if(type==0){
			bookModel=bookService.findByBookRfid(borrowModel.getBookRfid());
			bookboxModel=bookboxService.find(bookModel.getBoxId());
			a=1;
		}
		if(type==2){
			bookboxModel=bookboxService.find(borrowModel.getActualBoxId());
			a=-1;
		}
		if(bookboxModel==null){//若type不为0和2，必定null，不需要修改
			return;
		}
		bookboxModel.setBoxNum(bookboxModel.getBoxNum()-a);//剩余书本数量-1
		bookboxService.saveOrUpdate(bookboxModel);
	}
	@Override
	public Date getShouldReturnTime(BorrowModel borrowModel){
		Date startDate=borrowModel.getBorrowTime();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		String a = sdf.format(startDate);
		try{
			startDate=sdf.parse(a);//将时分秒变为0
		}catch(ParseException e){
		}
		UserModel userModel=userService.findByUserId(borrowModel.getUserId());
		ConfigModel configModel=configService.find(1);
		Integer days=userModel.getUserRole() ==0 ? configModel.getStuDay():configModel.getTeacherDay();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(startDate);
		calendar.add(Calendar.DATE,days);
		return calendar.getTime();
	}

	@Override
	public BorrowModel updateBorrowModelByReturn(BorrowModel borrowModel) throws Exception{
		BorrowModel oldBorrowModel=borrowService.findByUserIdAndBookRfidAndStatus(borrowModel.getUserId(),borrowModel.getBookRfid(),0);
		if(oldBorrowModel==null){
			throw new Exception("未查询到此条借阅记录！");
		}
		Date date=new Date();
		oldBorrowModel.setActualTime(date);//设置实际归还时间
		oldBorrowModel.setActualBoxId(borrowModel.getActualBoxId());
		oldBorrowModel.setActualBookcaseId(borrowModel.getActualBookcaseId());
		oldBorrowModel.setActualLocation(borrowModel.getActualLocation());
		if(oldBorrowModel.getReturnTime().getTime()<date.getTime()){
			Long c=date.getTime()-oldBorrowModel.getReturnTime().getTime();
			Long d = c/1000/60/60/24;//相差的天数
			if(d>0){//超期
				oldBorrowModel.setStatus(2);//设置欠费
				oldBorrowModel.setIsPay(1);
				oldBorrowModel.setIsFinish(0);//设置订单未完成
				oldBorrowModel.setAmount(oldBorrowModel.getAmount()+d*0.1);//设置罚款金额，超期1天1毛
			}else{//超过0点但是不满1天的按照未超期结算
				oldBorrowModel.setStatus(1);//设置已归还
				oldBorrowModel.setAmount(oldBorrowModel.getAmount());//设置罚款金额为0
				oldBorrowModel.setIsFinish(1);//设置订单已完成
			}
		}else{
			oldBorrowModel.setStatus(1);//设置已归还
			oldBorrowModel.setAmount(oldBorrowModel.getAmount());//设置罚款金额为0
			oldBorrowModel.setIsFinish(1);//设置订单未完成
		}
		return oldBorrowModel;
	}

	@Override
	public void checkBoxReturnable(BorrowModel borrowModel) throws Exception{
		ConfigModel configModel=configService.find(1);
		BookboxModel bookboxModel=bookboxService.find(borrowModel.getActualBoxId());
		if(bookboxModel.getBoxNum()+1>configModel.getBoxMax()){
			throw new Exception("超过该书箱最大容纳数量！");
		}
	}

	@Override
	public Page<BorrowModel> findAllByUserId(String searchText, PageRequest pageRequest) {
		return borrowDao.findAllByUserId(searchText,pageRequest);
	}

	@Override
	public void checkEditFormValid(BorrowModel borrowModel)throws Exception {
		if(borrowModel.getStatus()==0){
			if(borrowModel.getActualBoxId()!=null||borrowModel.getActualBookcaseId()!=null||
					borrowModel.getActualTime()!=null){
				throw new Exception("未还书情况下不得设置还书参数！");
			}
		}else if(borrowModel.getActualBoxId()==null||borrowModel.getActualBookcaseId()==null||
				borrowModel.getActualTime()==null){
			throw new Exception("已还书情况下必须设置还书参数！");
		}
	}

	@Override
	public List<BorrowModel> findAllByUserId(String searchText) {
		return borrowDao.findAllByUserId(searchText);
	}

	@Override
	public List<BorrowModel> findAllByBookRfid(String bookRfid) {
		return borrowDao.findAllByBookRfid(bookRfid);
	}

	@Override
	public void addBorrow(BorrowModel borrowModel) throws Exception{
		BookModel bookModel = bookService.findByBookRfid(borrowModel.getBookRfid());
		borrowService.saveOrUpdate(borrowModel);
		borrowService.changeBookStatus(borrowModel,0);//type为0代表新增借阅，service中bookModel.status直接置1
		borrowService.changeUserAction(borrowModel);
		if(bookModel.getInBox()==0){
			borrowService.changeCaseAndBox(borrowModel,0);
		}
		//throw new Exception("11");
	}

	@Override
	public void returnBorrow(BorrowModel borrowModel, int inBox) throws Exception{
		BorrowModel oldBorrowModel=borrowService.updateBorrowModelByReturn(borrowModel);
		borrowService.saveOrUpdate(oldBorrowModel);

		BookModel bookModel=bookService.findByBookRfid(oldBorrowModel.getBookRfid());
		bookModel.setInBox(inBox);
		bookService.saveOrUpdate(bookModel);
		if(inBox==0)
			borrowService.changeCaseAndBox(oldBorrowModel,2);//type为2代表还书
		borrowService.changeBookStatus(oldBorrowModel,2);//type为2代表还书
		borrowService.changeUserAction(oldBorrowModel);//修改用户剩余可借书籍数量
	}
}
