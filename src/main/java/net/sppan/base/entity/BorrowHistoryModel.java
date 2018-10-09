package net.sppan.base.entity;

import com.alibaba.fastjson.annotation.JSONField;
import net.sppan.base.entity.support.BaseEntity;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "borrow_history")
public class BorrowHistoryModel extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 借阅id，自增
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "borrow_id", nullable = false)
	private Integer borrowId;

	/**
	 * 借阅人员id
	 */
	private String userId;
	/**
	 * 借阅书籍rfid
	 */
	private String bookRfid;
	/**
	 * 借阅时间
	 */
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date borrowTime;
	/**
	 * 应还时间
	 */
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date returnTime;
	/**
	 * 应还书柜id
	 */
	private Integer returnBookcaseId;
	/**
	 * 应还书箱id
	 */
	private Integer returnBoxId;
	/**
	 * 应还位置
	 */
	private String returnLocation;
	/**
	 * 借阅状态，0：未归还；1已归还 2 逾期
	 */
	private Integer status;
	/**
	 * 实际归还时间
	 */
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date actualTime;
	/**
	 * 实际归还书柜id
	 */
	private Integer actualBookcaseId;
	/**
	 * 实际归还书箱id
	 */
	private Integer actualBoxId;
	/**
	 * 实际归还位置
	 */
	private String actualLocation;
	/**
	 * 是否欠费，0否1是
	 */
	private Integer isPay;
	/**
	 * 欠费金额
	 */
	private Double amount;
	/**
	 * 订单是否完成，0未完成1已完成
	 */
	private Integer isFinish;
	/**
	 * 更新时间
	 */
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date updateTime;

	public String getReturnLocation() {
		return returnLocation;
	}

	public void setReturnLocation(String returnLocation) {
		this.returnLocation = returnLocation;
	}

	public String getActualLocation() {
		return actualLocation;
	}

	public void setActualLocation(String actualLocation) {
		this.actualLocation = actualLocation;
	}

	public String getBookRfid() {
		return bookRfid;
	}

	public void setBookRfid(String bookRfid) {
		this.bookRfid = bookRfid;
	}

	public Integer getActualBoxId() {
		return actualBoxId;
	}

	public void setActualBoxId(Integer actualBoxId) {
		this.actualBoxId = actualBoxId;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Integer getBorrowId() {
		return borrowId;
	}

	public void setBorrowId(Integer borrowId) {
		this.borrowId = borrowId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Date getBorrowTime() {
		return borrowTime;
	}

	public void setBorrowTime(Date borrowTime) {
		this.borrowTime = borrowTime;
	}

	public Date getReturnTime() {
		return returnTime;
	}

	public void setReturnTime(Date returnTime) {
		this.returnTime = returnTime;
	}

	public Integer getReturnBookcaseId() {
		return returnBookcaseId;
	}

	public void setReturnBookcaseId(Integer returnBookcaseId) {
		this.returnBookcaseId = returnBookcaseId;
	}

	public Integer getReturnBoxId() {
		return returnBoxId;
	}

	public void setReturnBoxId(Integer returnBoxId) {
		this.returnBoxId = returnBoxId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getActualTime() {
		return actualTime;
	}

	public void setActualTime(Date actualTime) {
		this.actualTime = actualTime;
	}

	public Integer getActualBookcaseId() {
		return actualBookcaseId;
	}

	public void setActualBookcaseId(Integer actualBookcaseId) {
		this.actualBookcaseId = actualBookcaseId;
	}

	public Integer getIsPay() {
		return isPay;
	}

	public void setIsPay(Integer isPay) {
		this.isPay = isPay;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Integer getIsFinish() {
		return isFinish;
	}

	public void setIsFinish(Integer isFinish) {
		this.isFinish = isFinish;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}
