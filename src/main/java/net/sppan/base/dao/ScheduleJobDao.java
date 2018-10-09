package net.sppan.base.dao;

import net.sppan.base.dao.support.IBaseDao;
import net.sppan.base.entity.ScheduleJob;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleJobDao extends IBaseDao<ScheduleJob, Integer> {

	ScheduleJob findByScheduleJobId(Long txt);

	Page<ScheduleJob> findAllByDescriptionContaining(String searchText, Pageable pageable);

	void deleteByScheduleJobId(Long id);

}
