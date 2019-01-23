package cm.lx.bean.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("cm_car_sale_setup")
public class CarSaleSetup {

    @TableId(type = IdType.AUTO)
    Integer id;

    @TableField(value = "car_cost_id")
    Integer carCostId;

    @TableField(value = "setup_type")
    Integer setupType;

    @TableField(value = "setup_name")
    String setupName;

    @TableField(value = "setup_fee")
    Double setupFee;

    Long ctime;

    Long utime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSetupType() {
        return setupType;
    }

    public void setSetupType(Integer setupType) {
        this.setupType = setupType;
    }

    public Integer getCarCostId() {
        return carCostId;
    }

    public void setCarCostId(Integer carCostId) {
        this.carCostId = carCostId;
    }

    public String getSetupName() {
        return setupName;
    }

    public void setSetupName(String setupName) {
        this.setupName = setupName;
    }

    public Double getSetupFee() {
        return setupFee;
    }

    public void setSetupFee(Double setupFee) {
        this.setupFee = setupFee;
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
