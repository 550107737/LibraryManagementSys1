package net.sppan.base.dao;

import net.sppan.base.dao.support.IBaseDao;
import net.sppan.base.entity.BorrowModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BorrowDao extends IBaseDao<BorrowModel, Integer> {

	BorrowModel findByUserId(String username);

	Page<BorrowModel> findAllByUserIdContainingAndBookRfidContaining(String userId,String bookRfid, Pageable pageable);

	Long countByUserIdAndStatus(String userId,Integer status);

	BorrowModel findByUserIdAndBookRfidAndStatus(String userid,String bookRfid,Integer status);

	Page<BorrowModel> findAllByUserId(String searchText, Pageable pageable);

	List<BorrowModel> findAllByUserId(String userId);

	List<BorrowModel> findAllByBookRfid(String bookRfid);

	List<BorrowModel> findAllByUserIdAndIsFinish(String userId,Integer isFinish);
}
