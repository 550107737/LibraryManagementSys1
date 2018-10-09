package net.sppan.base.service;

import net.sppan.base.entity.BookModel;
import net.sppan.base.service.support.IBaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Date;
import java.util.List;

public interface BookService extends IBaseService<BookModel, Integer> {

	/**
	 * 根据书本id查找用户
	 * @param username
	 * @return
	 */
	BookModel findByBookRfid(String username);

	/**
	 * 增加或者修改书籍
	 * @param bookModel
	 */
	void saveOrUpdate(BookModel bookModel);


	/**
	 * 根据关键字获取分页
	 * @param searchText
	 * @param pageRequest
	 * @return
	 */
	Page<BookModel> findAllByLike(String searchText, PageRequest pageRequest);

	/**
	 * @方法名: changeBoxNum
	 * @功能描述: 新增书籍时，归属书箱数量+1
	 * @创建人: 黄梓莘
	 * @创建时间： 2018-6-29
	 */
	void changeBoxNum(Integer boxId);
	/**
	 * @方法名: checkBoxAddable
	 * @功能描述: 新增书籍时，检查书箱是否可增加
	 * @创建人: 黄梓莘
	 * @创建时间： 2018-7-7
	 */
	void checkBoxAddable(Integer boxId) throws  Exception;
	/**
	 * @方法名: searchAll
	 * @功能描述: 多条件查询
	 * @创建人: 黄梓莘
	 * @创建时间： 2018-7-11
	 */
	Page<BookModel> searchAll(String authors,String bookName,String publication,String classification,Integer booksStatus, Integer overdue,PageRequest pageRequest);

	List<BookModel> findAllByBookRfid(String bookRfid);

	Page<BookModel> searchAllOverdueBook(Date date, PageRequest pageRequest);

	/**
	 * @方法名: getData
	 * @功能描述: 获取主页部分数据
	 * @创建人: 黄梓莘
	 * @创建时间： 2018-8-3
	 */
	List getData();
	/**
	 * @方法名: getData2
	 * @功能描述: 获取主页部分数据
	 * @创建人: 黄梓莘
	 * @创建时间： 2018-8-4
	 */
	List getData2();
	/**
	 * @方法名: getData
	 * @功能描述: 获取主页图表数据
	 * @创建人: 黄梓莘
	 * @创建时间： 2018-8-4
	 */
	List getChartData();

}
