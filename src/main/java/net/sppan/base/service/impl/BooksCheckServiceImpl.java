package net.sppan.base.service.impl;


import net.sppan.base.dao.BooksCheckDao;
import net.sppan.base.dao.ConfigDao;
import net.sppan.base.dao.support.IBaseDao;
import net.sppan.base.entity.BooksCheckModel;
import net.sppan.base.service.BooksCheckService;
import net.sppan.base.service.ConfigService;
import net.sppan.base.service.support.impl.BaseServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
public class BooksCheckServiceImpl extends BaseServiceImpl<BooksCheckModel, Integer> implements BooksCheckService {

	@Autowired
	private BooksCheckDao booksCheckDao;

	
	@Override
	public IBaseDao<BooksCheckModel, Integer> getBaseDao() {
		return this.booksCheckDao;
	}

	@Override
	public Page<BooksCheckModel> findAllByLike(String bookRfid, PageRequest pageRequest) {
        if(StringUtils.isBlank(bookRfid)){
            bookRfid = "";
        }
		return booksCheckDao.findAllByBookRfidContaining(bookRfid,pageRequest);
	}

	@Override
	public void saveOrUpdate(BooksCheckModel booksCheckModel) {
		if(booksCheckModel.getCheckId() != null){
			booksCheckModel.setUpdateTime(new Date());
			update(booksCheckModel);
		}else{

			booksCheckModel.setUpdateTime(new Date());

			save(booksCheckModel);
		}
	}
	
	

	@Override
	public void delete(Integer id) {
		super.delete(id);
	}



}
