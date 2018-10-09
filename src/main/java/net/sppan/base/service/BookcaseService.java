package net.sppan.base.service;


import net.sppan.base.entity.BookcaseModel;
import net.sppan.base.service.support.IBaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface BookcaseService extends IBaseService<BookcaseModel, Integer> {

	/**
	 * 根据书柜rfid查找
	 * @return
	 */
	BookcaseModel findByBookcaseId(Integer id);

	/**
	 * 增加或者修改书柜
	 * @param bookModel
	 */
	void saveOrUpdate(BookcaseModel bookModel);


	/**
	 * 根据关键字获取分页
	 * @param searchText
	 * @param pageRequest
	 * @return
	 */
	Page<BookcaseModel> findAllByLike(String searchText, PageRequest pageRequest);

	BookcaseModel findByBookcaseRfid(String bookcaseRfid);

}
