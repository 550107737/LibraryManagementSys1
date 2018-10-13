package net.sppan.base.entity;

import com.alibaba.fastjson.annotation.JSONField;
import net.sppan.base.entity.support.BaseEntity;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "repair_bookcase")
public class RepairBookcaseModel extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 默认参数编号，自增
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "repair_id", nullable = false)
	private Integer repairId;

	/**
	 * 书柜rfid
	 */
	private String bookcaseRfid;
	/**
	 * 0是1否
	 */
	private Integer isRepair;
	/**
	 * 损坏时间
	 */
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date brokenTime;
	/**
	 * 损坏原因
	 */
	private String brokenReason;
	/**
	 * 修复时间
	 */
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date repairTime;


	/**
	 * 更新时间
	 */
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date updateTime;

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}


	public Integer getRepairId() {
		return repairId;
	}

	public void setRepairId(Integer repairId) {
		this.repairId = repairId;
	}

	public String getBookcaseRfid() {
		return bookcaseRfid;
	}

	public void setBookcaseRfid(String bookcaseRfid) {
		this.bookcaseRfid = bookcaseRfid;
	}

	public Integer getIsRepair() {
		return isRepair;
	}

	public void setIsRepair(Integer isRepair) {
		this.isRepair = isRepair;
	}

	public Date getBrokenTime() {
		return brokenTime;
	}

	public void setBrokenTime(Date brokenTime) {
		this.brokenTime = brokenTime;
	}

	public String getBrokenReason() {
		return brokenReason;
	}

	public void setBrokenReason(String brokenReason) {
		this.brokenReason = brokenReason;
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
