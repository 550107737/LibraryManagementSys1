package net.sppan.base.entity;

import com.alibaba.fastjson.annotation.JSONField;
import net.sppan.base.entity.support.BaseEntity;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "bookbox")
public class BookboxModel extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * boxId，自增
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "box_id", nullable = false)
	private Integer boxId;

	/**
	 * 书箱rfid编号
	 */
	private String boxRfid;
	/**
	 * 书箱具体层号
	 */
	private Integer boxSid;
	/**
	 * 所属书柜id
	 */
	private Integer bookcaseId;
	/**
	 * 所属书柜位置
	 */
	private String location;
	/**
	 * 当前书本数量
	 */
	private Integer boxNum;
	/**
	 * 更新时间
	 */
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date updateTime;

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Integer getBoxId() {
		return boxId;
	}

	public void setBoxId(Integer boxId) {
		this.boxId = boxId;
	}

	public String getBoxRfid() {
		return boxRfid;
	}

	public void setBoxRfid(String boxRfid) {
		this.boxRfid = boxRfid;
	}

	public Integer getBoxSid() {
		return boxSid;
	}

	public void setBoxSid(Integer boxSid) {
		this.boxSid = boxSid;
	}

	public Integer getBookcaseId() {
		return bookcaseId;
	}

	public void setBookcaseId(Integer bookcaseId) {
		this.bookcaseId = bookcaseId;
	}


	public Integer getBoxNum() {
		return boxNum;
	}

	public void setBoxNum(Integer boxNum) {
		this.boxNum = boxNum;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}
