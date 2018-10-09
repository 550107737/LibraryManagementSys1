package net.sppan.base.entity;

import com.alibaba.fastjson.annotation.JSONField;
import net.sppan.base.entity.support.BaseEntity;

import javax.persistence.*;


public class ParamModel extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private String bookcaseSN;
    private String token;
    private String time;

    public String getBookcaseSN() {
        return bookcaseSN;
    }

    public void setBookcaseSN(String bookcaseSN) {
        this.bookcaseSN = bookcaseSN;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


}