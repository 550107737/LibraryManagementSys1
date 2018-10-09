package net.sppan.base.service.impl;


import net.sppan.base.dao.ChangeRfidDao;
import net.sppan.base.dao.ConfigDao;
import net.sppan.base.dao.support.IBaseDao;

import net.sppan.base.entity.*;
import net.sppan.base.service.*;
import net.sppan.base.service.support.impl.BaseServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
public class ChangeRfidServiceImpl extends BaseServiceImpl<ChangeRfidModel, Integer> implements ChangeRfidService {

	@Autowired
	private ChangeRfidDao changeRfidDao;
	@Autowired
	private BookService bookService;
	@Autowired
	private BookcaseService bookcaseService;
	@Autowired
	private BookboxService bookboxService;
	@Autowired
	private UserService userService;
	@Autowired
	private BorrowService borrowService;;
	@Autowired
	private BorrowHistoryService borrowHistoryService;

	
	@Override
	public IBaseDao<ChangeRfidModel, Integer> getBaseDao() {
		return this.changeRfidDao;
	}

	@Override
	public ChangeRfidModel findByOldRfid(String txt) {
		return changeRfidDao.findByOldRfid(txt);
	}

	@Override
	public void saveOrUpdate(ChangeRfidModel changeRfidModel) {
		if(changeRfidModel.getChangeId() != null){
			changeRfidModel.setUpdateTime(new Date());
			update(changeRfidModel);
		}else{

			changeRfidModel.setUpdateTime(new Date());

			save(changeRfidModel);
		}
	}
	
	

	@Override
	public void delete(Integer id) {
		super.delete(id);
	}


	@Override
	public Page<ChangeRfidModel> findAllByLike(String searchText, PageRequest pageRequest) {
		if(StringUtils.isBlank(searchText)){
			searchText = "";
		}
		return changeRfidDao.findAllByOldRfidContaining(searchText,pageRequest);
	}

	@Override
	public List<ChangeRfidModel> findAllByIsFinish(Integer isFinish) {
		return changeRfidDao.findAllByIsFinish(isFinish);
	}

	@Override
	public void changeRfid(ChangeRfidModel changeRfidModel) throws Exception{
		switch (changeRfidModel.getType()){
			case 1:
				UserModel userModel=userService.findByUserId(changeRfidModel.getOldRfid());
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
				bookModel.setBookRfid(changeRfidModel.getNewRfid());
				bookService.saveOrUpdate(bookModel);
				break;
			case 3:
				BookcaseModel bookcaseModel=bookcaseService.findByBookcaseRfid(changeRfidModel.getOldRfid());
				bookcaseModel.setBookcaseRfid(changeRfidModel.getNewRfid());
				bookcaseService.saveOrUpdate(bookcaseModel);
				break;
			case 4:
				BookboxModel bookboxModel=bookboxService.findByBoxRfid(changeRfidModel.getOldRfid());
				bookboxModel.setBoxRfid(changeRfidModel.getNewRfid());
				bookboxService.saveOrUpdate(bookboxModel);
				break;
		}
	}
}
