package net.sppan.base.service;

import net.sppan.base.entity.BorrowHistoryModel;
import net.sppan.base.service.support.IBaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Date;
import java.util.List;

public interface BorrowHistoryService extends IBaseService<BorrowHistoryModel, Integer> {
	/**
	 * 根据用户证件号、书籍rfid号和未还书状态定位借阅数据
	 * @param
	 */
	BorrowHistoryModel findByUserIdAndBookRfidAndStatus(String userid, String bookRfid, Integer status);
	Long countByUserIdAndStatus(String userId, Integer status);
	/**
	 * 根据用户id查找用户
	 * @param username
	 * @return
	 */
	BorrowHistoryModel findByUserId(String username);

	/**
	 * 增加或者修改借阅记录
	 * @param borrowModel
	 */
	void saveOrUpdate(BorrowHistoryModel borrowModel);

	/**
	 * 根据关键字获取分页
	 * @param searchText
	 * @param pageRequest
	 * @return
	 */
	Page<BorrowHistoryModel> findAllByLike(String searchText, PageRequest pageRequest);
	/**
	 * 根据关键字获取分页
	 * @param searchText
	 * @param pageRequest
	 * @return
	 */
	Page<BorrowHistoryModel> findAllByUserId(String searchText, PageRequest pageRequest);

	List<BorrowHistoryModel> findAllByUserId(String userId);

	List<BorrowHistoryModel> findAllByBookRfid(String bookRfid);

	Page<BorrowHistoryModel> findAllByUserIdAndStatus(String userId,Integer status, PageRequest pageRequest);


}
