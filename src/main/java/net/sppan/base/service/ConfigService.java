package net.sppan.base.service;

import net.sppan.base.entity.ConfigModel;
import net.sppan.base.service.support.IBaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Date;


public interface ConfigService extends IBaseService<ConfigModel, Integer> {

	/**
	 * 根据描述查找
	 * @param txt
	 * @return
	 */
	ConfigModel findByConfigId(Integer txt);

	/**
	 * 增加或者修改默认数据
	 * @param configModel
	 */
	void saveOrUpdate(ConfigModel configModel);






}
