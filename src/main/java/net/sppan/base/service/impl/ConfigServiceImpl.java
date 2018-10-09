package net.sppan.base.service.impl;


import net.sppan.base.dao.ConfigDao;
import net.sppan.base.dao.support.IBaseDao;

import net.sppan.base.entity.ConfigModel;
import net.sppan.base.service.ConfigService;
import net.sppan.base.service.support.impl.BaseServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
public class ConfigServiceImpl extends BaseServiceImpl<ConfigModel, Integer> implements ConfigService {

	@Autowired
	private ConfigDao configDao;

	
	@Override
	public IBaseDao<ConfigModel, Integer> getBaseDao() {
		return this.configDao;
	}

	@Override
	public ConfigModel findByConfigId(Integer txt) {
		return configDao.findByConfigId(txt);
	}

	@Override
	public void saveOrUpdate(ConfigModel configModel) {
		if(configModel.getConfigId() != null){
			configModel.setUpdateTime(new Date());
			update(configModel);
		}else{

			configModel.setUpdateTime(new Date());

			save(configModel);
		}
	}
	
	

	@Override
	public void delete(Integer id) {
		super.delete(id);
	}



}
