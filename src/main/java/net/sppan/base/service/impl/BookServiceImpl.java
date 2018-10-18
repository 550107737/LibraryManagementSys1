package net.sppan.base.service.impl;


import net.sppan.base.dao.BookDao;
import net.sppan.base.dao.support.IBaseDao;
import net.sppan.base.entity.*;
import net.sppan.base.service.*;
import net.sppan.base.service.support.impl.BaseServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


@Service
public class BookServiceImpl extends BaseServiceImpl<BookModel, Integer> implements BookService {

	@Autowired
	private BookDao bookDao;
	@Autowired
	private BookboxService bookboxService;
	@Autowired
	private ConfigService configService;
	@Autowired
	private BorrowService borrowService;
	@Autowired
	private BorrowHistoryService borrowHistoryService;
    @Autowired
    private UserService userService;
	@Autowired
	private BookService bookService;
	@Autowired
	private BookcaseService bookcaseService;

	@Override
	public IBaseDao<BookModel, Integer> getBaseDao() {
		return this.bookDao;
	}

	@Override
	public BookModel findByBookRfid(String username) {
		return bookDao.findByBookRfid(username);
	}

	@Override
	public void saveOrUpdate(BookModel bookModel) {
		Date date=new Date();
		if(bookModel.getBooksId() != null){
			bookModel.setUpdateTime(date);
			update(bookModel);
		}else{
			bookModel.setBookTime(date);
			bookModel.setRepayTime(date);
			bookModel.setUpdateTime(date);

			save(bookModel);
		}
	}

	@Override
	public void delete(Integer id) {
		super.delete(id);
	}


	@Override
	public Page<BookModel> findAllByLike(String searchText, PageRequest pageRequest) {
		if(StringUtils.isBlank(searchText)){
			searchText = "";
		}
		return bookDao.findAllByBookRfidContaining(searchText,pageRequest);
	}


	@Override
	public void changeBoxNum(Integer boxId) {
		BookboxModel bookboxModel=bookboxService.find(boxId);
		bookboxModel.setBoxNum(bookboxModel.getBoxNum()+1);
		bookboxService.saveOrUpdate(bookboxModel);
	}

	@Override
	public void checkBoxAddable(Integer boxId) throws Exception{
		BookboxModel bookboxModel=bookboxService.find(boxId);
		ConfigModel configModel=configService.findByConfigId(1);
		if(bookboxModel.getBoxNum()>=configModel.getBoxMax()){
			throw new Exception("该书箱已满！");
		}
	}

	@Override
	public Page<BookModel> searchAll(String authors, String bookName, String publication, String classification, Integer booksStatus,
									 Integer overdue,PageRequest pageRequest) {
		if(StringUtils.isBlank(authors)){
			authors = "";
		}
		if(StringUtils.isBlank(bookName)){
			bookName = "";
		}
		if(StringUtils.isBlank(publication)){
			publication = "";
		}
		if(StringUtils.isBlank(classification)){
			classification = "";
		}
		if(overdue==null){
			overdue=0;
		}
		if(overdue==0){
			if (booksStatus==-1){
				return bookDao.findAllByAuthorsContainingAndBookNameContainingAndPublicationContainingAndClassificationContaining(
						authors,bookName,publication,classification,pageRequest);
			}else{
				return bookDao.findAllByAuthorsContainingAndBookNameContainingAndPublicationContainingAndClassificationContainingAndBooksStatus(
						authors,bookName,publication,classification,booksStatus,pageRequest);
			}
		}else {
			if (booksStatus==-1){
				return bookDao.findAllByAuthorsContainingAndBookNameContainingAndPublicationContainingAndClassificationContainingAndRepayTimeBefore(
						authors,bookName,publication,classification,new Date(),pageRequest);
			}else{
				return bookDao.findAllByAuthorsContainingAndBookNameContainingAndPublicationContainingAndClassificationContainingAndBooksStatusAndRepayTimeBefore(
						authors,bookName,publication,classification,booksStatus,new Date(),pageRequest);
			}
		}

	}

	@Override
	public List<BookModel> findAllByBookRfid(String bookRfid) {
		return bookDao.findAllByBookRfid(bookRfid);
	}

	@Override
	public Page<BookModel> searchAllOverdueBook(Date date,PageRequest pageRequest) {
		return bookDao.findAllByRepayTimeBefore(date,pageRequest);
	}

	@Override
	public List getData() {//借阅数量，，借阅人数，书籍总量，活跃前10名
		List list=new ArrayList();
		Long totalBorrow=borrowService.count()+borrowHistoryService.count();
		list.add(totalBorrow);
		Long totalBook=bookDao.count();
		list.add(totalBook);
		Integer totalPeople=countDistinctByUserId();
		list.add(totalPeople);
		List userTop10=getUserTop10();
		for(int i=0;i<userTop10.size();i++){
			String rfid=(String)userTop10.get(i);
			UserModel userModel=userService.findByUserId(rfid);
			String name=userModel.getUserName();
			List list2=new ArrayList();
			list2.add(name);
			list2.add(userTop10.get(i));
			list.add(list2);
		}
		return list;
	}
	private Integer countDistinctByUserId() {
		return bookDao.countUserIdDistinctFromBorrowHistory();
	}
	private List getUserTop10() {
		return bookDao.userTop10();
	}

	@Override
	public List getData2() {
		List list1=new ArrayList();
		List popularBookTop20=getPopularBookTop20();
		for(int i=0;i<popularBookTop20.size();i++){
			String rfid=(String)popularBookTop20.get(i);//返回short型
			BookModel bookModel=bookService.findByBookRfid(rfid);
			String name=bookModel.getBookName();
			list1.add(name);
		}
		List list2=new ArrayList();
		List popularBookcaseTop20=getPopularBookcaseTop20();
		for(int i=0;i<popularBookcaseTop20.size();i++){
			if(popularBookcaseTop20.get(i)==null){
				continue;
			}
			short id=(short)popularBookcaseTop20.get(i);//返回short型
			Integer id2=new Integer(id);
			BookcaseModel bookcaseModel=bookcaseService.find(id2);
			String location=bookcaseModel.getLocation();
			list2.add(location);
		}
		List list=new ArrayList();
		list.add(list1);
		list.add(list2);
		return list;
	}
	private List getPopularBookTop20() {
		return bookDao.popularBookTop20();
	}
	private List getPopularBookcaseTop20() {
		return bookDao.popularBookcaseTop20();
	}

	@Override
	public List getChartData() {
		List list=new ArrayList();
		Calendar ca = Calendar.getInstance();// 得到一个Calendar的实例
		ca.setTime(new Date()); // 设置时间为当前时间
		ca.add(Calendar.MONTH, -7);// 月份减1
		Date startDate = ca.getTime(); // 结果
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM");
		String startdatestr=sdf.format(startDate);
		String enddatestr=sdf.format(new Date());
		List sixMonthChart=bookDao.sixMonthChart(startdatestr,enddatestr);
		list.add(sixMonthChart);
		List popularBookClassificationTop10=bookDao.popularBookClassificationTop10();
		List tempList=new ArrayList();
		for(int i=0;i<popularBookClassificationTop10.size();i++){
			Object[] objList=(Object[]) popularBookClassificationTop10.get(i);
			String bookRfid= (String)objList[0];
			BigInteger count=(BigInteger) objList[1];
			BookModel bookModel=bookService.findByBookRfid(bookRfid);
			String classification=bookModel.getClassification();
			List list2=new ArrayList();
			list2.add(classification);
			list2.add(count);
			tempList.add(list2);
		}
		list.add(tempList);
		return list;
	}
}
