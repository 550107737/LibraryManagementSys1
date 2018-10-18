package net.sppan.base.dao;

import net.sppan.base.dao.support.IBaseDao;
import net.sppan.base.entity.BooksCheckModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface BooksCheckDao extends IBaseDao<BooksCheckModel, Integer> {

    Page<BooksCheckModel> findAllByBookRfidContaining(String bookRfid, Pageable pageable);

}
