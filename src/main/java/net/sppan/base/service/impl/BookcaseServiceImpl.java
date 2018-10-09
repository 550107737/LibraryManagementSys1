package net.sppan.base.service.impl;


import net.sppan.base.dao.BookDao;
import net.sppan.base.dao.BookcaseDao;
import net.sppan.base.dao.support.IBaseDao;

import net.sppan.base.entity.BookcaseModel;
import net.sppan.base.service.BookcaseService;
import net.sppan.base.service.support.impl.BaseServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Date;


@Service
public class BookcaseServiceImpl extends BaseServiceImpl<BookcaseModel, Integer> implements BookcaseService {

	@Autowired
	private BookcaseDao bookcaseDao;

	
	@Override
	public IBaseDao<BookcaseModel, Integer> getBaseDao() {
		return this.bookcaseDao;
	}

	@Override
	public BookcaseModel findByBookcaseId(Integer id) {
		return bookcaseDao.findByBookcaseId(id);
	}

	@Override
	public void saveOrUpdate(BookcaseModel bookcaseModel) {
		if(bookcaseModel.getBookcaseId() != null){
			bookcaseModel.setUpdateTime(new Date());
			update(bookcaseModel);
		}else{

			bookcaseModel.setUpdateTime(new Date());

			save(bookcaseModel);
		}
	}
	
	

	@Override
	public void delete(Integer id) {
		super.delete(id);
	}



	@Override
	public Page<BookcaseModel> findAllByLike(String searchText, PageRequest pageRequest) {
		if(StringUtils.isBlank(searchText)){
			searchText = "";
		}
		return bookcaseDao.findAllByLocationContaining(searchText,pageRequest);
	}

	@Override
	public BookcaseModel findByBookcaseRfid(String bookcaseRfid) {
		return bookcaseDao.findByBookcaseRfid(bookcaseRfid);
	}
}
