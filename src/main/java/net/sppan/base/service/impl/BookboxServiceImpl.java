package net.sppan.base.service.impl;


import net.sppan.base.dao.BookboxDao;
import net.sppan.base.dao.BookcaseDao;
import net.sppan.base.dao.support.IBaseDao;
import net.sppan.base.entity.BookboxModel;
import net.sppan.base.entity.BookcaseModel;
import net.sppan.base.service.BookboxService;
import net.sppan.base.service.BookcaseService;
import net.sppan.base.service.support.impl.BaseServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class BookboxServiceImpl extends BaseServiceImpl<BookboxModel, Integer> implements BookboxService {

	@Autowired
	private BookboxDao bookboxDao;
	@Autowired
	private BookcaseService bookcaseService;

	@Override
	public IBaseDao<BookboxModel, Integer> getBaseDao() {
		return this.bookboxDao;
	}

	@Override
	public BookboxModel findByBoxId(Integer id) {
		return bookboxDao.findByBoxId(id);
	}

	@Override
	public void saveOrUpdate(BookboxModel bookboxModel) {
		BookcaseModel bookcaseModel=bookcaseService.findByBookcaseId(bookboxModel.getBookcaseId());
		if(bookboxModel.getBookcaseId() != null){
			bookboxModel.setLocation(bookcaseModel.getLocation());
			bookboxModel.setUpdateTime(new Date());
			update(bookboxModel);
		}else{
			bookboxModel.setLocation(bookcaseModel.getLocation());
			bookboxModel.setUpdateTime(new Date());
			save(bookboxModel);
		}
	}

	@Override
	public void delete(Integer id) {
		super.delete(id);
	}


	@Override
	public Page<BookboxModel> findAllByLike(String searchText, PageRequest pageRequest) {
		if(StringUtils.isBlank(searchText)){
			searchText = "";
		}
		return bookboxDao.findAllByLocationContaining(searchText,pageRequest);
	}

	@Override
	public BookboxModel findByBoxSidAndBookcaseId(Integer bookSid, Integer bookcaseId) {
		return bookboxDao.findByBoxSidAndBookcaseId(bookSid,bookcaseId);
	}

	@Override
	public BookboxModel findByBoxRfid(String boxRfid) {
		return bookboxDao.findByBoxRfid(boxRfid);
	}
}
