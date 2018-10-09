package net.sppan.base.entity.support;

import com.dexcoder.commons.bean.BeanConverter;
import com.dexcoder.commons.exceptions.CommonsAssistantException;

import java.io.Serializable;
import java.util.Map;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class BaseEntity implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = -250118731239275742L;



	public <T> T getTargetObject(Class<T> clazz) {
		try {
			T t = clazz.newInstance();
			return BeanConverter.convert(t, this);
		} catch (Exception var3) {
			throw new CommonsAssistantException("转换对象失败", var3);
		}
	}
}