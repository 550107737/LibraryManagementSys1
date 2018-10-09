package net.sppan.base.dao;

import net.sppan.base.dao.support.IBaseDao;

import net.sppan.base.entity.BrokenBookRecordModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface BrokenBookRecordDao extends IBaseDao<BrokenBookRecordModel, Integer> {

	BrokenBookRecordModel findByUserId(String txt);

	Page<BrokenBookRecordModel> findAllByUserIdContaining(String searchText, Pageable pageable);

}
