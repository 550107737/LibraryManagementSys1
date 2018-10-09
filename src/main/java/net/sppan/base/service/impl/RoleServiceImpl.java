package net.sppan.base.service.impl;


import net.sppan.base.dao.RoleDao;
import net.sppan.base.dao.support.IBaseDao;
import net.sppan.base.entity.RoleModel;
import net.sppan.base.service.RoleService;
import net.sppan.base.service.support.impl.BaseServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
public class RoleServiceImpl extends BaseServiceImpl<RoleModel, Integer> implements RoleService {

	@Autowired
	private RoleDao roleDao;

	
	@Override
	public IBaseDao<RoleModel, Integer> getBaseDao() {
		return this.roleDao;
	}

	@Override
	public RoleModel findById(Integer txt) {
		return roleDao.findById(txt);
	}

	@Override
	public void saveOrUpdate(RoleModel roleModel) {
		if(roleModel.getId() != null){
			roleModel.setUpdateTime(new Date());
			update(roleModel);
		}else{

			roleModel.setUpdateTime(new Date());

			save(roleModel);
		}
	}
	
	

	@Override
	public void delete(Integer id) {
		super.delete(id);
	}


	@Override
	public Page<RoleModel> findAllByLike(String searchText, PageRequest pageRequest) {
		if(StringUtils.isBlank(searchText)){
			searchText = "";
		}
		return roleDao.findAllByDescriptionContaining(searchText,pageRequest);
	}

}
