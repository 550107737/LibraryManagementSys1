package net.sppan.base.service;

import net.sppan.base.entity.RoleModel;
import net.sppan.base.service.support.IBaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;


public interface RoleService extends IBaseService<RoleModel, Integer> {

	/**
	 * 根据id查找权限
	 * @param txt
	 * @return
	 */
	RoleModel findById(Integer txt);

	/**
	 * 增加或者修改权限
	 * @param configModel
	 */
	void saveOrUpdate(RoleModel configModel);


	/**
	 * 根据关键字获取分页
	 * @param searchText
	 * @param pageRequest
	 * @return
	 */
	Page<RoleModel> findAllByLike(String searchText, PageRequest pageRequest);



}
