package net.sppan.base.dao;

import net.sppan.base.dao.support.IBaseDao;
import net.sppan.base.entity.ConfigModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface ConfigDao extends IBaseDao<ConfigModel, Integer> {

	ConfigModel findByConfigId(Integer txt);


}
