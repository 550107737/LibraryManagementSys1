package net.sppan.base.service.impl;


import net.sppan.base.dao.ConfigDao;
import net.sppan.base.dao.RepairBookcaseDao;
import net.sppan.base.dao.support.IBaseDao;
import net.sppan.base.entity.ConfigModel;
import net.sppan.base.entity.RepairBookcaseModel;
import net.sppan.base.service.ConfigService;
import net.sppan.base.service.RepairBookcaseService;
import net.sppan.base.service.support.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
public class RepairBookcaseServiceImpl extends BaseServiceImpl<RepairBookcaseModel, Integer> implements RepairBookcaseService {

	@Autowired
	private RepairBookcaseDao repairBookcaseDao;

	
	@Override
	public IBaseDao<RepairBookcaseModel, Integer> getBaseDao() {
		return this.repairBookcaseDao;
	}

	@Override
	public void saveOrUpdate(RepairBookcaseModel repairBookcaseModel) {
		if(repairBookcaseModel.getRepairId() != null){
			repairBookcaseModel.setUpdateTime(new Date());
			update(repairBookcaseModel);
		}else{

			repairBookcaseModel.setUpdateTime(new Date());

			save(repairBookcaseModel);
		}
	}



}
