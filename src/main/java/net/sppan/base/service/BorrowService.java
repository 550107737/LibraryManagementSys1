package net.sppan.base.service;

import net.sppan.base.entity.BorrowModel;
import net.sppan.base.service.support.IBaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Date;
import java.util.List;

public interface BorrowService extends IBaseService<BorrowModel, Integer> {
	/**
	 * 根据用户证件号、书籍rfid号和未还书状态定位借阅数据
	 * @param
	 */
	BorrowModel findByUserIdAndBookRfidAndStatus(String userid,String bookRfid,Integer status);
	Long countByUserIdAndStatus(String userId,Integer status);
	/**
	 * 根据用户id查找用户
	 * @param username
	 * @return
	 */
	BorrowModel findByUserId(String username);

	/**
	 * 增加或者修改借阅记录
	 * @param borrowModel
	 */
	void saveOrUpdate(BorrowModel borrowModel);

	/**
	 * 根据关键字获取分页
	 * @param searchText
	 * @param pageRequest
	 * @return
	 */
	Page<BorrowModel> findAllByLike(String userId,String bookRfid, PageRequest pageRequest);

	/**
	 * @方法名: changeBookStatus
	 * @功能描述: 借阅成功后改变书籍状态
	 * @创建人: 黄梓莘
	 * @创建时间： 2018-6-29
	 */
	void changeBookStatus(BorrowModel borrowModel,int type) throws Exception;

	/**
	 * @方法名: checkUserBorrowable
	 * @功能描述: 检查账户状态是否可以借书
	 * @创建人: 黄梓莘
	 * @创建时间： 2018-6-29
	 */
	void checkUserBorrowable(BorrowModel borrowModel,int type) throws Exception;

	/**
	 * @方法名: checkBookBorrowable
	 * @功能描述: 检查书籍状态是否可以借阅
	 * @创建人: 黄梓莘
	 * @创建时间： 2018-6-29
	 */
	void checkBookBorrowable(BorrowModel borrowModel,int type)throws Exception ;
	/**
	 * @方法名: changeUserAction
	 * @功能描述: 借阅成功后改变user剩余借书数量
	 * @创建人: 黄梓莘
	 * @创建时间： 2018-6-29
	 */
	void changeUserAction(BorrowModel borrowModel)throws Exception ;
	/**
	 * @方法名: changeCaseAndBox
	 * @功能描述: 借阅成功后改变书箱剩余书目总量
	 * @创建人: 黄梓莘
	 * @创建时间： 2018-6-29
	 */
	void changeCaseAndBox(BorrowModel borrowModel,int type)throws Exception ;
	/**
	 * @方法名: getShouldReturnTime
	 * @功能描述: 获得应还书时间
	 * @创建人: 黄梓莘
	 * @创建时间： 2018-6-29
	 */
	Date getShouldReturnTime(BorrowModel borrowModel);
	/**
	 * @方法名: updateBorrowModelByReturn
	 * @功能描述: 还书后更新借阅记录
	 * @创建人: 黄梓莘
	 * @创建时间： 2018-6-30
	 */
	BorrowModel updateBorrowModelByReturn(BorrowModel borrowModel) throws Exception;
	/**
	 * @方法名: checkBoxReturnable
	 * @功能描述: 检查箱子是否可以还书
	 * @创建人: 黄梓莘
	 * @创建时间： 2018-7-5
	 */
	void checkBoxReturnable(BorrowModel borrowModel)throws Exception ;
	/**
	 * @方法名: checkEditFormValid
	 * @功能描述: 检查修改表单是否有效
	 * @创建人: 黄梓莘
	 * @创建时间： 2018-7-7
	 */
	void checkEditFormValid(BorrowModel borrowModel)throws Exception ;
	/**
	 * 根据关键字获取分页
	 * @param searchText
	 * @param pageRequest
	 * @return
	 */
	Page<BorrowModel> findAllByUserId(String searchText, PageRequest pageRequest);

	List<BorrowModel> findAllByUserId(String searchText);

	List<BorrowModel> findAllByBookRfid(String bookRfid);

	/*
	多个修改整合一个方法里配合transcational
	 */
	void addBorrow(BorrowModel borrowModel) throws Exception;

	void returnBorrow(BorrowModel borrowModel,int inbox) throws Exception;

}
