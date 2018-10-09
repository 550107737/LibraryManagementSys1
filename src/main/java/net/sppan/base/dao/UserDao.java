package net.sppan.base.dao;

import net.sppan.base.dao.support.IBaseDao;
import net.sppan.base.entity.UserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao extends IBaseDao<UserModel, Integer> {

	UserModel findByUserId(String username);

	Page<UserModel> findAllByUserSidContaining(String searchText, Pageable pageable);

	Page<UserModel> findAllByUserId(String searchText, Pageable pageable);

	List<UserModel> findAllByUserId(String userId);

	Page<UserModel> findAllByUserClassContaining(String userClass, Pageable pageable);

	Page<UserModel> findAllByUserClassContainingAndIsOverdue(String userClass,Integer isOverdue, Pageable pageable);

	Page<UserModel> findAllByUserClassContainingAndOverdueTotalAmountGreaterThan(String userClass,Double overdueTotalAmount, Pageable pageable);

	Page<UserModel> findAllByUserClassContainingAndOverdueTotalAmount(String userClass,Double overdueTotalAmount, Pageable pageable);

	Page<UserModel> findAllByUserClassContainingAndIsOverdueAndOverdueTotalAmountGreaterThan(String userClassInteger ,Integer isOverdue,Double overdueTotalAmount, Pageable pageable);

	Page<UserModel> findAllByUserClassContainingAndIsOverdueAndOverdueTotalAmount(String userClassInteger ,Integer isOverdue,Double overdueTotalAmount, Pageable pageable);


}
