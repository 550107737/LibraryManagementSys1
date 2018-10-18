package net.sppan.base.service;


import net.sppan.base.entity.ChangeRfidModel;
import net.sppan.base.service.support.IBaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;


public interface ChangeRfidService extends IBaseService<ChangeRfidModel, Integer> {

	/**
	 * 根据描述查找
	 * @param txt
	 * @return
	 */
	ChangeRfidModel findByOldRfid(String txt);

	/**
	 * 增加或者修改默认数据
	 * @param configModel
	 */
	void saveOrUpdate(ChangeRfidModel configModel);


	/**
	 * 根据关键字获取分页
	 * @param searchText
	 * @param pageRequest
	 * @return
	 */
	Page<ChangeRfidModel> findAllByLike(String oldRfid,String newRfid, PageRequest pageRequest);

	List<ChangeRfidModel> findAllByIsFinish(Integer isFinish);

	void changeRfid(ChangeRfidModel changeRfidModel) throws Exception;



}
