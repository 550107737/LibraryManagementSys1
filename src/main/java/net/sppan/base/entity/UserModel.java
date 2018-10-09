package net.sppan.base.entity;

import com.alibaba.fastjson.annotation.JSONField;
import net.sppan.base.entity.support.BaseEntity;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.Date;
import java.util.Set;


@Entity
@Table(name = "user")
public class UserModel extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 用户id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false)
	private Integer id;

	/**
	 * 用户rfid
	 */
	private String userId;
	/**
	 * 学号/工号
	 */
	private String userSid;
	/**
	 * 身份，0学生1教师
	 */
	private Integer userRole;
	/**
	 * 用户昵称
	 */
	private String userName;
	/**
	 * 用户性别，0男1女
	 */
	private Integer userSex;
	/**
	 * 年级/部门
	 */
	private String userClass;
	/**
	 * 当前借阅数量
	 */
	private Integer borrowNum;
	/**
	 * 剩余可借数量
	 */
	private Integer remainNum;
	/**
	 * 是否存在逾期书本，0否1是
	 */
	private Integer isOverdue;
	/**
	 * 欠款金额
	 */
	private Double overdueTotalAmount;
	/**
	 * 证件有效期
	 */
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date sidTime;
	/**
	 * 用户状态，0正常 1 冻结 2注销
	 */
	private Integer userStatus;
	/**
	 * 密码
	 */
	private String userPassword;

	/**
	 * 用户权限
	 */
	private Integer userSession;
	/**
	 * 更新时间
	 */
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date updateTime;


	public Integer getUserSession() {
		return userSession;
	}

	public void setUserSession(Integer userSession) {
		this.userSession = userSession;
	}


	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserSid() {
		return userSid;
	}

	public void setUserSid(String userSid) {
		this.userSid = userSid;
	}

	public Integer getUserRole() {
		return userRole;
	}

	public void setUserRole(Integer userRole) {
		this.userRole = userRole;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getUserSex() {
		return userSex;
	}

	public void setUserSex(Integer userSex) {
		this.userSex = userSex;
	}

	public String getUserClass() {
		return userClass;
	}

	public void setUserClass(String userClass) {
		this.userClass = userClass;
	}

	public Integer getBorrowNum() {
		return borrowNum;
	}

	public void setBorrowNum(Integer borrowNum) {
		this.borrowNum = borrowNum;
	}

	public Integer getRemainNum() {
		return remainNum;
	}

	public void setRemainNum(Integer remainNum) {
		this.remainNum = remainNum;
	}

	public Integer getIsOverdue() {
		return isOverdue;
	}

	public void setIsOverdue(Integer isOverdue) {
		this.isOverdue = isOverdue;
	}

	public Double getOverdueTotalAmount() {
		return overdueTotalAmount;
	}

	public void setOverdueTotalAmount(Double overdueTotalAmount) {
		this.overdueTotalAmount = overdueTotalAmount;
	}

	public Date getSidTime() {
		return sidTime;
	}

	public void setSidTime(Date sidTime) {
		this.sidTime = sidTime;
	}

	public Integer getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(Integer userStatus) {
		this.userStatus = userStatus;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}
