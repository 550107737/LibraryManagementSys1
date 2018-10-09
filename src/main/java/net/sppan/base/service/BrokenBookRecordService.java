package net.sppan.base.service;

import net.sppan.base.entity.BrokenBookRecordModel;
import net.sppan.base.service.support.IBaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;


public interface BrokenBookRecordService extends IBaseService<BrokenBookRecordModel, Integer> {

	/**
	 * 根据描述查找
	 * @param txt
	 * @return
	 */
	BrokenBookRecordModel findByDescription(String txt);

	/**
	 * 增加或者修改默认数据
	 * @param brokenBookRecordModel
	 */
	void saveOrUpdate(BrokenBookRecordModel brokenBookRecordModel);


	/**
	 * 根据关键字获取分页
	 * @param searchText
	 * @param pageRequest
	 * @return
	 */
	Page<BrokenBookRecordModel> findAllByLike(String searchText, PageRequest pageRequest);

	/**
	 * @方法名: changeUserFine
	 * @功能描述: 书籍损坏后更改用户罚款数据
	 * @创建人: 黄梓莘
	 * @创建时间： 2018-7-2
	 */
	void changeUserFineAndBookStatus(BrokenBookRecordModel brokenBookRecordModel) throws Exception;


}
