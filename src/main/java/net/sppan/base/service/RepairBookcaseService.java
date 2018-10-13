package net.sppan.base.service;

import net.sppan.base.entity.ConfigModel;
import net.sppan.base.entity.RepairBookcaseModel;
import net.sppan.base.service.support.IBaseService;


public interface RepairBookcaseService extends IBaseService<RepairBookcaseModel, Integer> {



	/**
	 * 增加或者修改书柜报警记录
	 * @param repairBookcaseModel
	 */
	void saveOrUpdate(RepairBookcaseModel repairBookcaseModel);






}
