package net.sppan.base.dao;

import net.sppan.base.dao.support.IBaseDao;

import net.sppan.base.entity.ChangeRfidModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChangeRfidDao extends IBaseDao<ChangeRfidModel, Integer> {

	ChangeRfidModel findByOldRfid(String txt);

	Page<ChangeRfidModel> findAllByOldRfidContaining(String searchText, Pageable pageable);

	List<ChangeRfidModel> findAllByIsFinish(Integer isFinish);

}
