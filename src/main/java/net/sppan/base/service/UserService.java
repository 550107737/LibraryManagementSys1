package net.sppan.base.service;

import net.sppan.base.entity.BorrowModel;
import net.sppan.base.entity.UserModel;
import net.sppan.base.service.support.IBaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface UserService extends IBaseService<UserModel, Integer> {

	/**
	 * 根据用户名查找用户
	 * @param username
	 * @return
	 */
	UserModel findByUserId(String username);

	/**
	 * 增加或者修改用户
	 * @param user
	 */
	void saveOrUpdate(UserModel user) throws  Exception;


	/**
	 * 根据关键字获取分页
	 * @param searchText
	 * @param pageRequest
	 * @return
	 */
	Page<UserModel> findAllByLike(String searchText, PageRequest pageRequest);

	/**
	 * 根据关键字获取分页
	 * @param searchText
	 * @param pageRequest
	 * @return
	 */
	Page<UserModel> findAllByUserId(String searchText, PageRequest pageRequest);

	List<UserModel> findAllByUserId(String userId);


	/**
	 * 修改用户密码
	 * @param user
	 * @param oldPassword
	 * @param password1
	 * @param password2
	 */
	void updatePwd(UserModel user, String oldPassword, String password1, String password2);

	/**
	 * 给用户分配角色
	 * @param id 用户ID
	 * @param roleIds 角色Ids
	 */
	void grant(Integer id, String[] roleIds);

	Page<UserModel> searchAll(String userClass,Integer isOverdue,Integer overdueTotalAmount,PageRequest pageRequest);

	List<BorrowModel> findUnfinishedOrder(String userId);
}
