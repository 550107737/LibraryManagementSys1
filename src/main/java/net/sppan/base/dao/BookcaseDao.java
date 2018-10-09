package net.sppan.base.dao;

import net.sppan.base.dao.support.IBaseDao;
import net.sppan.base.entity.BookcaseModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface BookcaseDao extends IBaseDao<BookcaseModel, Integer> {

	BookcaseModel findByBookcaseId(Integer id);

	Page<BookcaseModel> findAllByLocationContaining(String searchText, Pageable pageable);

	BookcaseModel findByBookcaseRfid(String bookcaseRfid);
}
