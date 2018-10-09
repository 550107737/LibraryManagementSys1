package net.sppan.base.entity;

import com.alibaba.fastjson.annotation.JSONField;
import net.sppan.base.entity.support.BaseEntity;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "book")
public class BookModel extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 书本id，自增
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "books_id", nullable = false)
	private Integer booksId;

	/**
	 * 书本rfid标签号
	 */
	private String bookRfid;
	/**
	 * 书本名称
	 */
	private String bookName;
	/**
	 * 书本isbn号
	 */
	private String isbn;
	/**
	 * 书本作者
	 */
	private String authors;
	/**
	 * 书本出版社
	 */
	private String publication;
	/**
	 * 书本出版时间
	 */
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date publicationTime;
	/**
	 * 书本分类
	 */
	private String classification;
	/**
	 * 书本文献号
	 */
	private String document;
	/**
	 * 书本定价
	 */
	private Double amount;
	/**
	 * 书本语言
	 */
	private String language;
	/**
	 * 书本描述
	 */
	private String description;
	/**
	 * 书本当前所属书箱id
	 */
	private Integer boxId;
	/**
	 * 书本当前所属书柜id
	 */
	private Integer bookcaseId;
	/**
	 * 显示位置信息
	 */
	private String booksPosition;
	/**
	 * 书本状态，0 在库，可借阅
	 * 1.已借出 2损坏或丢失下架
	 */
	private Integer booksStatus;
	/**
	 * 书本应还时间
	 */
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date repayTime;
	/**
	 * 书本入库时间
	 */
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date bookTime;
	/**
	 * 书本缩略图url
	 */
	private String imgurl;
	/**
	 * 盘点状态 0未盘点 1已盘点
	 */
	private Integer checkStatus;
	/**
	 * 图书默认位置 0书箱 1图书馆
	 */
	private Integer inBox;
	/**
	 * 更新时间
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

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getAuthors() {
		return authors;
	}

	public void setAuthors(String authors) {
		this.authors = authors;
	}

	public String getPublication() {
		return publication;
	}

	public void setPublication(String publication) {
		this.publication = publication;
	}

	public Date getPublicationTime() {
		return publicationTime;
	}

	public void setPublicationTime(Date publicationTime) {
		this.publicationTime = publicationTime;
	}

	public String getClassification() {
		return classification;
	}

	public void setClassification(String classification) {
		this.classification = classification;
	}

	public String getDocument() {
		return document;
	}

	public void setDocument(String document) {
		this.document = document;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getBooksStatus() {
		return booksStatus;
	}

	public void setBooksStatus(Integer booksStatus) {
		this.booksStatus = booksStatus;
	}

	public Date getRepayTime() {
		return repayTime;
	}

	public void setRepayTime(Date repayTime) {
		this.repayTime = repayTime;
	}

	public Date getBookTime() {
		return bookTime;
	}

	public void setBookTime(Date bookTime) {
		this.bookTime = bookTime;
	}

	public String getImgurl() {
		return imgurl;
	}

	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
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

	public String getBooksPosition() {
		return booksPosition;
	}

	public void setBooksPosition(String booksPosition) {
		this.booksPosition = booksPosition;
	}

	public Integer getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(Integer checkStatus) {
		this.checkStatus = checkStatus;
	}

	public Integer getInBox() {
		return inBox;
	}

	public void setInBox(Integer inBox) {
		this.inBox = inBox;
	}

	public Date getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}
}
