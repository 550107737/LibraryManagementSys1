package net.sppan.base.service;


import net.sppan.base.entity.BookboxModel;
import net.sppan.base.service.support.IBaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface BookboxService extends IBaseService<BookboxModel, Integer> {

	/**
	 * 根据书柜id查找列表
	 * @param id
	 * @return
	 */
	List<BookboxModel> findByBookcaseId(Integer id);

	/**
	 * 增加或者修改书箱
	 * @param bookModel
	 */
	void saveOrUpdate(BookboxModel bookModel);

	/**
	 * 根据关键字获取分页
	 * @param searchText
	 * @param pageRequest
	 * @return
	 */
	Page<BookboxModel> findAllByLike(String searchText, PageRequest pageRequest);
	/**
	 * 根据bookSid和BookcaseId获取记录
	 * @param bookSid
	 * @param bookcaseId
	 * @return
	 */
	BookboxModel findByBoxSidAndBookcaseId(Integer bookSid,Integer bookcaseId);

	BookboxModel findByBoxRfid(String boxRfid);
}
