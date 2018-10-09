package net.sppan.base.dao;

import net.sppan.base.dao.support.IBaseDao;

import net.sppan.base.entity.RoleModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleDao extends IBaseDao<RoleModel, Integer> {

	RoleModel findById(Integer txt);

	Page<RoleModel> findAllByDescriptionContaining(String searchText, Pageable pageable);

}
