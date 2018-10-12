package net.sppan.base.common;

import java.io.Serializable;
import java.util.Date;

/**
 * Json 统一返回消息类
 *
 * @author SPPan
 *
 */
public class JsonResult implements Serializable {
	private static final long serialVersionUID = -1491499610244557029L;
	public static int CODE_SUCCESS = 0;
	public static int CODE_FAILURED = -1;
	public static String[] NOOP = new String[] {};

	private int code; // 处理状态：0: 成功
	private String message;
	private Object data; // 返回数据
	public boolean success=false;

	//4个必须参数
	private String api_flag;
	private int retry_after_seconds;
	private long server_time;
	private boolean result;

	private String token;
	private int status;
	private int borrowStatus;//0还书1借书

	public JsonResult(){}

	private JsonResult(int code, String message, Object data) {
		this.code = code;
		this.message = message;
		this.data = data;
		if(code==CODE_SUCCESS){
			this.success=true;
		}
	}

	/**
	 * 处理成功，并返回数据
	 *
	 * @param data
	 *            数据对象
	 * @return data
	 */
	public static final JsonResult success(Object data) {
		return new JsonResult(CODE_SUCCESS, "操作成功", data);
	}

	/**
	 * 处理成功
	 *
	 * @return data
	 */
	public static final JsonResult success() {
		return new JsonResult(CODE_SUCCESS, "操作成功", NOOP);
	}

	/**
	 * 处理成功
	 *
	 * @param message
	 *            消息
	 * @return data
	 */
	public static final JsonResult success(String message) {
		return new JsonResult(CODE_SUCCESS, message, NOOP);
	}

	/**
	 * 处理成功
	 *
	 * @param message
	 *            消息
	 * @param data
	 *            数据对象
	 * @return data
	 */
	public static final JsonResult success(String message, Object data) {
		return new JsonResult(CODE_SUCCESS, message, data);
	}

	/**
	 * 处理失败，并返回数据（一般为错误信息）
	 *
	 * @param code
	 *            错误代码
	 * @param message
	 *            消息
	 * @return data
	 */
	public static final JsonResult failure(int code, String message) {
		return new JsonResult(code, message, NOOP);
	}

	/**
	 * 处理失败
	 *
	 * @param message
	 *            消息
	 * @return data
	 */
	public static final JsonResult failure(String message) {
		return failure(CODE_FAILURED, message);
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getApi_flag() {
		return api_flag;
	}

	public void setApi_flag(String api_flag) {
		this.api_flag = api_flag;
	}

	public int getRetry_after_seconds() {
		return retry_after_seconds;
	}

	public void setRetry_after_seconds(int retry_after_seconds) {
		this.retry_after_seconds = retry_after_seconds;
	}

	public long getServer_time() {
		return server_time;
	}

	public void setServer_time(long server_time) {
		this.server_time = server_time;
	}

	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getBorrowStatus() {
		return borrowStatus;
	}

	public void setBorrowStatus(int borrowStatus) {
		this.borrowStatus = borrowStatus;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	@Override
	public String toString() {
		return "JsonResult [code=" + code + ", message=" + message + ", data="
				+ data + "]";
	}


}
