package net.sppan.base.entity;

import com.alibaba.fastjson.annotation.JSONField;
import net.sppan.base.entity.support.BaseEntity;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "change_rfid")
public class ChangeRfidModel extends BaseEntity {

    private static final long serialVersionUID = 1L;
    /**
     * 默认参数编号，自增
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "change_id", nullable = false)
    private Integer changeId;

    /**
     * 旧RFID号
     */
    private String oldRfid;
    /**
     * 新RFID号
     */
    private String newRfid;
    /**
     * 是否更新完成，0否1是
     */
    private Integer isFinish;
    /**
     * 更换类型，1用户rfid，2书籍rfid
     * 3书柜rfid，4书箱rfid
     */
    private Integer type;

    /**
     * 更新时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getIsFinish() {
        return isFinish;
    }

    public void setIsFinish(Integer isFinish) {
        this.isFinish = isFinish;
    }

    public Integer getChangeId() {
        return changeId;
    }

    public void setChangeId(Integer changeId) {
        this.changeId = changeId;
    }

    public String getOldRfid() {
        return oldRfid;
    }

    public void setOldRfid(String oldRfid) {
        this.oldRfid = oldRfid;
    }

    public String getNewRfid() {
        return newRfid;
    }

    public void setNewRfid(String newRfid) {
        this.newRfid = newRfid;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

}
