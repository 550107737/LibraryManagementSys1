package net.sppan.base.dao;

import net.sppan.base.dao.support.IBaseDao;
import net.sppan.base.entity.BookboxModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookboxDao extends IBaseDao<BookboxModel, Integer> {

	List<BookboxModel> findByBookcaseId(Integer id);

	Page<BookboxModel> findAllByLocationContaining(String searchText, Pageable pageable);

	BookboxModel findByBoxSidAndBookcaseId(Integer bookSid,Integer bookcaseId);

	BookboxModel findByBoxRfid(String boxRfid);
}
