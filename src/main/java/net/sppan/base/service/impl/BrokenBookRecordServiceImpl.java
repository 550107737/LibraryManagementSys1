package net.sppan.base.service.impl;


import net.sppan.base.dao.BrokenBookRecordDao;
import net.sppan.base.dao.ConfigDao;
import net.sppan.base.dao.support.IBaseDao;
import net.sppan.base.entity.BookModel;
import net.sppan.base.entity.BrokenBookRecordModel;


import net.sppan.base.entity.UserModel;
import net.sppan.base.service.BookService;
import net.sppan.base.service.BrokenBookRecordService;
import net.sppan.base.service.UserService;
import net.sppan.base.service.support.impl.BaseServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
public class BrokenBookRecordServiceImpl extends BaseServiceImpl<BrokenBookRecordModel, Integer> implements BrokenBookRecordService {

	@Autowired
	private BrokenBookRecordDao brokenBookRecordDao;

	@Autowired
	private UserService userService;

	@Autowired
	BookService bookService;
	
	@Override
	public IBaseDao<BrokenBookRecordModel, Integer> getBaseDao() {
		return this.brokenBookRecordDao;
	}

	@Override
	public BrokenBookRecordModel findByDescription(String txt) {
		return brokenBookRecordDao.findByUserId(txt);
	}

	@Override
	public void saveOrUpdate(BrokenBookRecordModel brokenBookRecordModel) {
		if(brokenBookRecordModel.getBrokenId() != null){
			brokenBookRecordModel.setUpdateTime(new Date());
			update(brokenBookRecordModel);
		}else{

			brokenBookRecordModel.setUpdateTime(new Date());

			save(brokenBookRecordModel);
		}
	}

	@Override
	public void delete(Integer id) {
		super.delete(id);
	}

	@Override
	public Page<BrokenBookRecordModel> findAllByLike(String searchText, PageRequest pageRequest) {
		if(StringUtils.isBlank(searchText)){
			searchText = "";
		}
		return brokenBookRecordDao.findAllByUserIdContaining(searchText,pageRequest);
	}

	@Override
	public void changeUserFineAndBookStatus(BrokenBookRecordModel brokenBookRecordModel) throws Exception{
		UserModel userModel=userService.findByUserId(brokenBookRecordModel.getUserId());
		if(userModel==null){
			throw new Exception("无此用户！");
		}
		userModel.setOverdueTotalAmount(brokenBookRecordModel.getPayAmount());
		userService.saveOrUpdate(userModel);
	}

}
