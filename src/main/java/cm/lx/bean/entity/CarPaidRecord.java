package cm.lx.bean.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("cm_car_paid_record")
public class CarPaidRecord {

    @TableId(type = IdType.AUTO)
    Integer id;

    @TableField(value = "car_record_id")
    Integer carRecordId;

    @TableField(value = "record_status")
    Integer  recordStatus;

    @TableField(value = "paid_money")
    Double paidMoney;

    @TableField(value = "paid_reason")
    String paidReason;

    Long ctime;

    Long utime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCarRecordId() {
        return carRecordId;
    }

    public void setCarRecordId(Integer carRecordId) {
        this.carRecordId = carRecordId;
    }

    public Integer getRecordStatus() {
        return recordStatus;
    }

    public void setRecordStatus(Integer recordStatus) {
        this.recordStatus = recordStatus;
    }

    public Double getPaidMoney() {
        return paidMoney;
    }

    public void setPaidMoney(Double paidMoney) {
        this.paidMoney = paidMoney;
    }

    public String getPaidReason() {
        return paidReason;
    }

    public void setPaidReason(String paidReason) {
        this.paidReason = paidReason;
    }

    public Long getCtime() {
        return ctime;
    }

    public void setCtime(Long ctime) {
        this.ctime = ctime;
    }

    public Long getUtime() {
        return utime;
    }

    public void setUtime(Long utime) {
        this.utime = utime;
    }
}
