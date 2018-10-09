package net.sppan.base.entity;

import com.alibaba.fastjson.annotation.JSONField;
import net.sppan.base.entity.support.BaseEntity;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "configs")
public class ConfigModel extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 默认参数编号，自增
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "config_id", nullable = false)
	private Integer configId;

	/**
	 * 学生最大借阅数量
	 */
	private Integer stuMax;
	/**
	 * 学生最大借阅天数
	 */
	private Integer stuDay;
	/**
	 * 教师最大借阅数量
	 */
	private Integer teacherMax;
	/**
	 * 教师最大借阅天数
	 */
	private Integer teacherDay;
	/**
	 * 书箱最大存放书本数量
	 */
	private Integer boxMax;


	/**
	 * 更新时间
	 */
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date updateTime;

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}


	public Integer getConfigId() {
		return configId;
	}

	public void setConfigId(Integer configId) {
		this.configId = configId;
	}

	public Integer getStuMax() {
		return stuMax;
	}

	public void setStuMax(Integer stuMax) {
		this.stuMax = stuMax;
	}

	public Integer getStuDay() {
		return stuDay;
	}

	public void setStuDay(Integer stuDay) {
		this.stuDay = stuDay;
	}

	public Integer getTeacherMax() {
		return teacherMax;
	}

	public void setTeacherMax(Integer teacherMax) {
		this.teacherMax = teacherMax;
	}

	public Integer getTeacherDay() {
		return teacherDay;
	}

	public void setTeacherDay(Integer teacherDay) {
		this.teacherDay = teacherDay;
	}

	public Integer getBoxMax() {
		return boxMax;
	}

	public void setBoxMax(Integer boxMax) {
		this.boxMax = boxMax;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}
