package net.sppan.base.entity;

import com.alibaba.fastjson.annotation.JSONField;
import net.sppan.base.entity.support.BaseEntity;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "bookcase")
public class BookcaseModel extends BaseEntity {


	/**
	 * boxId，自增
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "bookcase_id", nullable = false)
	private Integer bookcaseId;

	/**
	 * 书箱rfid编号
	 */
	private String bookcaseRfid;
	/**
	 * 书箱具体层号
	 */
	private String location;
	/**
	 * 所属书柜id
	 */
	private Integer isBroken;

	/**
	 * 更新时间
	 */
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date brokenTime;
	/**
	 * 更新时间
	 */
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date repairTime;
	/**
	 * 更新时间
	 */
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date updateTime;

	public Integer getBookcaseId() {
		return bookcaseId;
	}

	public void setBookcaseId(Integer bookcaseId) {
		this.bookcaseId = bookcaseId;
	}

	public String getBookcaseRfid() {
		return bookcaseRfid;
	}

	public void setBookcaseRfid(String bookcaseRfid) {
		this.bookcaseRfid = bookcaseRfid;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Integer getIsBroken() {
		return isBroken;
	}

	public void setIsBroken(Integer isBroken) {
		this.isBroken = isBroken;
	}

	public Date getBrokenTime() {
		return brokenTime;
	}

	public void setBrokenTime(Date brokenTime) {
		this.brokenTime = brokenTime;
	}

	public Date getRepairTime() {
		return repairTime;
	}

	public void setRepairTime(Date repairTime) {
		this.repairTime = repairTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}
