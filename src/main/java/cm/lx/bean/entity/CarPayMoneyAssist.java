package cm.lx.bean.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 车辆保险信息
 * @author linxingwei
 * @date 2018/12/27
 */
@TableName("cm_car_pay_money_assist")
public class CarPayMoneyAssist {

    @TableId(type = IdType.AUTO)
    Integer id;

    @TableField(value = "car_sale_info_id")
    Integer carSaleInfoId;

    @TableField(value = "old_pay_money")
    Double oldPayMoney;

    @TableField(value = "new_pay_money")
    Double newPayMoney;

    Double difference;

    @TableField(value = "mod_reason")
    String modReason;

    Long ctime;

    Long utime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCarSaleInfoId() {
        return carSaleInfoId;
    }

    public void setCarSaleInfoId(Integer carSaleInfoId) {
        this.carSaleInfoId = carSaleInfoId;
    }

    public Double getOldPayMoney() {
        return oldPayMoney;
    }

    public void setOldPayMoney(Double oldPayMoney) {
        this.oldPayMoney = oldPayMoney;
    }

    public Double getNewPayMoney() {
        return newPayMoney;
    }

    public void setNewPayMoney(Double newPayMoney) {
        this.newPayMoney = newPayMoney;
    }

    public Double getDifference() {
        return difference;
    }

    public void setDifference(Double difference) {
        this.difference = difference;
    }

    public String getModReason() {
        return modReason;
    }

    public void setModReason(String modReason) {
        this.modReason = modReason;
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
