package net.sppan.base.entity;

import com.alibaba.fastjson.annotation.JSONField;
import net.sppan.base.entity.support.BaseEntity;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "books_check")
public class BooksCheckModel extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 默认参数编号，自增
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "check_id", nullable = false)
	private Integer checkId;

	/**
	 * 书籍id号
	 */
	private Integer booksId;
	/**
	 * 书籍Rfid
	 */
	private String bookRfid;
	/**
	 * 默认存放的书柜id
	 */
	private Integer boxId;
	/**
	 * 默认存放的书箱id
	 */
	private Integer bookcaseId;
	/**
	 * 书籍位置信息
	 */
	private String bookLocation;
	/**
	 * 盘点状态 0未盘点 1已盘点
	 */
	private Integer checkStatus;

	/**
	 * 盘点日期
	 */
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date checkDate;
	/**
	 * 更新时间
	 */
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date updateTime;

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getBookLocation() {
		return bookLocation;
	}

	public void setBookLocation(String bookLocation) {
		this.bookLocation = bookLocation;
	}

	public Integer getCheckId() {
		return checkId;
	}

	public void setCheckId(Integer checkId) {
		this.checkId = checkId;
	}

	public Integer getBooksId() {
		return booksId;
	}

	public void setBooksId(Integer booksId) {
		this.booksId = booksId;
	}

	public String getBookRfid() {
		return bookRfid;
	}

	public void setBookRfid(String bookRfid) {
		this.bookRfid = bookRfid;
	}

	public Integer getBoxId() {
		return boxId;
	}

	public void setBoxId(Integer boxId) {
		this.boxId = boxId;
	}

	public Integer getBookcaseId() {
		return bookcaseId;
	}

	public void setBookcaseId(Integer bookcaseId) {
		this.bookcaseId = bookcaseId;
	}

	public Integer getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(Integer checkStatus) {
		this.checkStatus = checkStatus;
	}

	public Date getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}
