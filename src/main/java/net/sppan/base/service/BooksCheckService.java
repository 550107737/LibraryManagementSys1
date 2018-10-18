package net.sppan.base.service;

import net.sppan.base.entity.BooksCheckModel;
import net.sppan.base.entity.ChangeRfidModel;
import net.sppan.base.service.support.IBaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;


public interface BooksCheckService extends IBaseService<BooksCheckModel, Integer> {

	Page<BooksCheckModel> findAllByLike(String bookRfid, PageRequest pageRequest);

	/**
	 * 增加或者修改默认数据
	 * @param booksCheckModel
	 */
	void saveOrUpdate(BooksCheckModel booksCheckModel);






}
