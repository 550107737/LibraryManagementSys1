package net.sppan.base.service;

import net.sppan.base.entity.BooksCheckModel;
import net.sppan.base.service.support.IBaseService;


public interface BooksCheckService extends IBaseService<BooksCheckModel, Integer> {


	/**
	 * 增加或者修改默认数据
	 * @param booksCheckModel
	 */
	void saveOrUpdate(BooksCheckModel booksCheckModel);






}
