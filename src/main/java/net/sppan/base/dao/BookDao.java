package net.sppan.base.dao;

import net.sppan.base.dao.support.IBaseDao;
import net.sppan.base.entity.BookModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface BookDao extends IBaseDao<BookModel, Integer> {

	BookModel findByBookRfid(String username);

	Page<BookModel> findAllByBookRfidContaining(String searchText, Pageable pageable);

	Page<BookModel> findAllByAuthorsContainingAndBookNameContainingAndPublicationContainingAndClassificationContaining(
			String authors,String bookName, String publication,String classification,Pageable pageable);
	Page<BookModel> findAllByAuthorsContainingAndBookNameContainingAndPublicationContainingAndClassificationContainingAndBooksStatus(
			String authors,String bookName, String publication,String classification,Integer bookStatus,Pageable pageable);

	Page<BookModel> findAllByAuthorsContainingAndBookNameContainingAndPublicationContainingAndClassificationContainingAndRepayTimeBefore(
			String authors,String bookName, String publication,String classification,Date date,Pageable pageable);

	Page<BookModel> findAllByAuthorsContainingAndBookNameContainingAndPublicationContainingAndClassificationContainingAndBooksStatusAndRepayTimeBefore(
			String authors,String bookName, String publication,String classification,Integer bookStatus,Date date,Pageable pageable);

	List<BookModel> findAllByBookRfid(String bookRfid);

	List<BookModel> findAllByInBoxAndCheckStatus(Integer inBox,Integer Status);

	Page<BookModel> findAllByRepayTimeBefore(Date date, Pageable pageable);

	List<BookModel> findAllByBookcaseIdAndBooksStatus(Integer bookcaseId,Integer booksStatus);

	@Query(nativeQuery = true,value ="SELECT COUNT(DISTINCT user_id) FROM borrow")
	Integer countUserIdDistinctFromBorrow();

	@Query(nativeQuery = true,value ="SELECT COUNT(DISTINCT user_id) FROM borrow_history")
	Integer countUserIdDistinctFromBorrowHistory();

	@Query(nativeQuery = true,value ="SELECT user_id FROM borrow_history GROUP BY user_id order by count(*) desc limit 10")
	List userTop10();

	@Query(nativeQuery = true,value ="SELECT book_rfid FROM borrow_history GROUP BY book_rfid order by count(*) desc limit 20")
	List popularBookTop20();

	@Query(nativeQuery = true,value ="SELECT actual_bookcase_id FROM borrow_history GROUP BY actual_bookcase_id order by count(*) desc limit 20")
	List popularBookcaseTop20();

	@Query(nativeQuery = true,value ="SELECT DATE_FORMAT(borrow_time, '%Y-%m') AS time," +
			"count(*) FROM borrow_history WHERE borrow_time >=:startDate AND borrow_time<:endDate GROUP BY time ORDER BY TIME ASC")
	List sixMonthChart(@Param("startDate") String startDate,@Param("endDate") String endDate);

	@Query(nativeQuery = true,value ="SELECT book_rfid,count(*) FROM borrow_history GROUP BY book_rfid ORDER BY count(*) DESC LIMIT 10")
	List popularBookClassificationTop10();
}
