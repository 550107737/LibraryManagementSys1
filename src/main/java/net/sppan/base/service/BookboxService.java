package net.sppan.base.service;


import net.sppan.base.entity.BookboxModel;
import net.sppan.base.service.support.IBaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface BookboxService extends IBaseService<BookboxModel, Integer> {

	/**
	 * 根据id查找
	 * @param id
	 * @return
	 */
	BookboxModel findByBoxId(Integer id);

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
