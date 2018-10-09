package net.sppan.base.service.impl;

import net.sppan.base.common.utils.MD5Utils;
import net.sppan.base.dao.BorrowDao;
import net.sppan.base.dao.UserDao;
import net.sppan.base.dao.support.IBaseDao;
import net.sppan.base.entity.BorrowModel;
import net.sppan.base.entity.ConfigModel;
import net.sppan.base.entity.RoleModel;
import net.sppan.base.entity.UserModel;
import net.sppan.base.service.ConfigService;
import net.sppan.base.service.UserService;
import net.sppan.base.service.support.impl.BaseServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
public class UserServiceImpl extends BaseServiceImpl<UserModel, Integer> implements UserService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private BorrowDao borrowDao;

	@Autowired
	private ConfigService configService;

	@Override
	public IBaseDao<UserModel, Integer> getBaseDao() {
		return this.userDao;
	}

	@Override
	public UserModel findByUserId(String username) {
		return userDao.findByUserId(username);
	}

	@Override
	public void saveOrUpdate(UserModel userModel) throws Exception{
		ConfigModel configModel=configService.find(1);
		Integer num=userModel.getUserRole()==0 ? configModel.getStuMax():configModel.getTeacherMax();//学生或教师的最大可借数量
		if(userModel.getBorrowNum()>num){
			throw new Exception("当前借阅数量超出最大范围！");
		}
		if(userModel.getId() != null){
			UserModel dbUser = find(userModel.getId());
			userModel.setRemainNum(num-userModel.getBorrowNum());
			userModel.setUserPassword(dbUser.getUserPassword());
			userModel.setUserSession(dbUser.getUserSession());
			userModel.setUpdateTime(new Date());
			update(userModel);
		}else{
			userModel.setUserPassword(MD5Utils.md5(userModel.getUserPassword()));
			userModel.setRemainNum(num-userModel.getBorrowNum());
			userModel.setUserSession(3);//新建默认普通用户
			userModel.setUpdateTime(new Date());
			save(userModel);
		}
	}
	@Override
	public void grant(Integer id, String[] roleIds) {
		UserModel userModel = find(id);
		Assert.notNull(userModel, "用户不存在");
		Assert.state(!(1==userModel.getUserSession()),"超级管理员用户不能修改角色");

		userModel.setUserSession(Integer.parseInt(roleIds[0]));
		userModel.setUpdateTime(new Date());
		update(userModel);
	}
	
	

	@Override
	public void delete(Integer id) {
		UserModel userModel = find(id);
		Assert.state(!"admin".equals(userModel.getUserName()),"超级管理员用户不能删除");
		super.delete(id);
	}

	@Override
	public Page<UserModel> findAllByLike(String searchText, PageRequest pageRequest) {
		if(StringUtils.isBlank(searchText)){
			searchText = "";
		}
		return userDao.findAllByUserSidContaining(searchText,pageRequest);
	}

	@Override
	public Page<UserModel> findAllByUserId(String searchText, PageRequest pageRequest) {
		return userDao.findAllByUserId(searchText,pageRequest);
	}

	@Override
	public List<UserModel> findAllByUserId(String userId) {
		return userDao.findAllByUserId(userId);
	}

	@Override
	public void updatePwd(UserModel userModel, String oldPassword, String password1, String password2) {
		Assert.notNull(userModel, "用户不能为空");
		Assert.notNull(oldPassword, "原始密码不能为空");
		Assert.notNull(password1, "新密码不能为空");
		Assert.notNull(password2, "重复密码不能为空");

		UserModel dbUser = userDao.findByUserId(userModel.getUserId());
		Assert.notNull(dbUser, "用户不存在");
		Assert.isTrue(password1.equals(password2), "两次密码不一致");
		Assert.isTrue(userModel.getUserPassword().equals(MD5Utils.md5(oldPassword)), "原始密码不正确");;

		dbUser.setUserPassword(MD5Utils.md5(password1));
		userDao.saveAndFlush(dbUser);
	}

	@Override
	public Page<UserModel> searchAll(String userClass, Integer isOverdue, Integer overdueTotalAmount, PageRequest pageRequest) {
		if(StringUtils.isBlank(userClass)){
			userClass = "";
		}
		if(isOverdue==-1){
			if(overdueTotalAmount==-1){
				return userDao.findAllByUserClassContaining(userClass,pageRequest);
			}else if(overdueTotalAmount==1){
				return userDao.findAllByUserClassContainingAndOverdueTotalAmountGreaterThan(userClass,0.0,pageRequest);
			}else{
				return userDao.findAllByUserClassContainingAndOverdueTotalAmount(userClass,0.0,pageRequest);			}
		}else{
			if(overdueTotalAmount==-1){
				return userDao.findAllByUserClassContainingAndIsOverdue(userClass,isOverdue,pageRequest);
			}else if(overdueTotalAmount==1){
				return  userDao.findAllByUserClassContainingAndIsOverdueAndOverdueTotalAmountGreaterThan(userClass,isOverdue,0.0,pageRequest);
			}else{
				return  userDao.findAllByUserClassContainingAndIsOverdueAndOverdueTotalAmount(userClass,isOverdue,0.0,pageRequest);
			}
		}
	}

	@Override
	public List<BorrowModel> findUnfinishedOrder(String userId) {
		return borrowDao.findAllByUserIdAndIsFinish(userId,0);
	}
}
