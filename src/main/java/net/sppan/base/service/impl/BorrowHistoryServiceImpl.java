package net.sppan.base.service.impl;


import net.sppan.base.dao.BorrowHistoryDao;
import net.sppan.base.dao.support.IBaseDao;
import net.sppan.base.entity.BorrowHistoryModel;
import net.sppan.base.service.BorrowHistoryService;
import net.sppan.base.service.support.impl.BaseServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
public class BorrowHistoryServiceImpl extends BaseServiceImpl<BorrowHistoryModel, Integer> implements BorrowHistoryService {

	@Autowired
	private BorrowHistoryDao borrowHistoryDao;

	
	@Override
	public IBaseDao<BorrowHistoryModel, Integer> getBaseDao() {
		return this.borrowHistoryDao;
	}

	@Override
	public Long countByUserIdAndStatus(String userId, Integer status) {
		return borrowHistoryDao.countByUserIdAndStatus(userId,status);
	}

	@Override
	public BorrowHistoryModel findByUserIdAndBookRfidAndStatus(String userid, String bookRfid,Integer status) {
		return borrowHistoryDao.findByUserIdAndBookRfidAndStatus(userid,bookRfid,status);
	}

	@Override
	public BorrowHistoryModel findByUserId(String username) {
		return borrowHistoryDao.findByUserId(username);
	}

	@Override
	public void saveOrUpdate(BorrowHistoryModel borrowHistoryModel) {
		if(borrowHistoryModel.getBorrowId() != null){
			borrowHistoryModel.setUpdateTime(new Date());
			update(borrowHistoryModel);
		}else{

			borrowHistoryModel.setUpdateTime(new Date());

			save(borrowHistoryModel);
		}
	}
	
	

	@Override
	public void delete(Integer id) {
		super.delete(id);
	}


	@Override
	public Page<BorrowHistoryModel> findAllByLike(String searchText, PageRequest pageRequest) {
		if(StringUtils.isBlank(searchText)){
			searchText = "";
		}
		return borrowHistoryDao.findAllByUserIdContaining(searchText,pageRequest);
	}

	@Override
	public Page<BorrowHistoryModel> findAllByUserId(String searchText, PageRequest pageRequest) {
		return borrowHistoryDao.findAllByUserId(searchText,pageRequest);
	}

	@Override
	public List<BorrowHistoryModel> findAllByUserId(String userId) {
		return borrowHistoryDao.findAllByUserId(userId);
	}

	@Override
	public List<BorrowHistoryModel> findAllByBookRfid(String bookRfid) {
		return borrowHistoryDao.findAllByBookRfid(bookRfid);
	}

	@Override
	public Page<BorrowHistoryModel> findAllByUserIdAndStatus(String userId, Integer status, PageRequest pageRequest) {
		if(StringUtils.isBlank(userId)){
			return borrowHistoryDao.findAllByUserIdContaining("",pageRequest);
		}else {
			return borrowHistoryDao.findAllByUserIdAndStatus(userId,status,pageRequest);
		}
	}
}
