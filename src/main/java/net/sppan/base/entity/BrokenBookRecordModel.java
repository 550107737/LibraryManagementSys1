package net.sppan.base.entity;

import com.alibaba.fastjson.annotation.JSONField;
import net.sppan.base.entity.support.BaseEntity;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "broken_book_record")
public class BrokenBookRecordModel extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 默认参数编号，自增
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "broken_id", nullable = false)
	private Integer brokenId;

	/**
	 * 损坏书籍rfid
	 */
	private String bookRfid;
	/**
	 * 书籍id
	 */
	private Integer bookId;
	/**
	 * 损坏用户id
	 */
	private String userId;
	/**
	 * 是否需要赔偿，0需要1不需要
	 */
	private Integer needPay;
	/**
	 * 赔偿金额
	 */
	private Double payAmount;
	/**
	 * 损坏原因
	 */
	private String  reason;
	/**
	 * 是否下架 0否1是
	 */
	private Integer offShelves;
	/**
	 * 更新时间
	 */
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date updateTime;

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Integer getBrokenId() {
		return brokenId;
	}

	public void setBrokenId(Integer brokenId) {
		this.brokenId = brokenId;
	}

	public Integer getBookId() {
		return bookId;
	}

	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}

	public Integer getOffShelves() {
		return offShelves;
	}

	public void setOffShelves(Integer offShelves) {
		this.offShelves = offShelves;
	}

	public String getBookRfid() {
		return bookRfid;
	}

	public void setBookRfid(String bookRfid) {
		this.bookRfid = bookRfid;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getNeedPay() {
		return needPay;
	}

	public void setNeedPay(Integer needPay) {
		this.needPay = needPay;
	}

	public Double getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(Double payAmount) {
		this.payAmount = payAmount;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}
