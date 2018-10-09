package net.sppan.base.dao;

import net.sppan.base.dao.support.IBaseDao;
import net.sppan.base.entity.BorrowHistoryModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BorrowHistoryDao extends IBaseDao<BorrowHistoryModel, Integer> {

	BorrowHistoryModel findByUserId(String username);

	Page<BorrowHistoryModel> findAllByUserIdContaining(String searchText, Pageable pageable);

	Long countByUserIdAndStatus(String userId, Integer status);

	BorrowHistoryModel findByUserIdAndBookRfidAndStatus(String userid, String bookRfid, Integer status);

	Page<BorrowHistoryModel> findAllByUserId(String searchText, Pageable pageable);

	List<BorrowHistoryModel> findAllByUserId(String userId);

	List<BorrowHistoryModel> findAllByBookRfid(String bookRfid);

	Page<BorrowHistoryModel> findAllByUserIdAndStatus(String userId,Integer status,Pageable pageable);


}
