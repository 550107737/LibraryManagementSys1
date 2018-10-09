package net.sppan.base.entity;

import com.alibaba.fastjson.annotation.JSONField;
import net.sppan.base.entity.support.BaseEntity;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;


@Entity
@Table(name = "role")
public class RoleModel extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 角色id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false)
	private Integer id;

	/**
	 * 角色key
	 */
	private String roleKey;
	/**
	 * 角色描述
	 */
	private String description;
	/**
	 * 更新时间
	 */
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date updateTime;

	@ManyToMany(cascade = { CascadeType.REFRESH }, fetch = FetchType.LAZY)
	@JoinTable(name = "role_resource", joinColumns = { @JoinColumn(name = "role_id") }, inverseJoinColumns = { @JoinColumn(name = "resource_id") })
	private java.util.Set<Resource> resources;

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRoleKey() {
		return roleKey;
	}

	public void setRoleKey(String roleKey) {
		this.roleKey = roleKey;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Set<Resource> getResources() {
		return resources;
	}

	public void setResources(Set<Resource> resources) {
		this.resources = resources;
	}
}
