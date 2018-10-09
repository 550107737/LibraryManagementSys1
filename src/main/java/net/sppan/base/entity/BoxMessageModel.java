package net.sppan.base.entity;

import com.alibaba.fastjson.annotation.JSONField;
import net.sppan.base.entity.support.BaseEntity;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "box_message")
public class BoxMessageModel extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 默认参数编号，自增
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "message_id", nullable = false)
	private Integer messageId;

	/**
	 * 视频路径
	 */
	private String videoPath;
	/**
	 * 视频路径
	 */
	private String audioPath;
	/**
	 * 音频路径
	 */
	private String picPath;
	/**
	 * 文字消息
	 */
	private String message;
	/**
	 * 是否显示，0否1是
	 */
	private Integer isVisible;
	/**
	 * 启用时间
	 */
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date timeBegin;/**
	 * 停止时间
	 */
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date timeEnd;
	/**
	 * 更新时间
	 */
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date updateTime;

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}


	public Integer getMessageId() {
		return messageId;
	}

	public void setMessageId(Integer messageId) {
		this.messageId = messageId;
	}

	public String getVideoPath() {
		return videoPath;
	}

	public void setVideoPath(String videoPath) {
		this.videoPath = videoPath;
	}

	public String getAudioPath() {
		return audioPath;
	}

	public void setAudioPath(String audioPath) {
		this.audioPath = audioPath;
	}

	public String getPicPath() {
		return picPath;
	}

	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getIsVisible() {
		return isVisible;
	}

	public void setIsVisible(Integer isVisible) {
		this.isVisible = isVisible;
	}

	public Date getTimeBegin() {
		return timeBegin;
	}

	public void setTimeBegin(Date timeBegin) {
		this.timeBegin = timeBegin;
	}

	public Date getTimeEnd() {
		return timeEnd;
	}

	public void setTimeEnd(Date timeEnd) {
		this.timeEnd = timeEnd;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}
