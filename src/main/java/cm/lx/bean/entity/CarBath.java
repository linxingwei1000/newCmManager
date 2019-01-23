package cm.lx.bean.entity;

import cm.lx.bean.export.CarRecordExport;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.List;

@TableName("cm_car_bath")
public class CarBath {

    @TableId(type = IdType.AUTO)
    Integer id;

    @TableField(value = "bath_name")
    String bathName;

    @TableField(value = "bath_desc")
    String bathDesc;

    @TableField(value = "account_num")
    String accountNum;

    @TableField(value = "car_record_id")
    String carRecordId;

    Long ctime;

    Long utime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBathName() {
        return bathName;
    }

    public void setBathName(String bathName) {
        this.bathName = bathName;
    }

    public String getBathDesc() {
        return bathDesc;
    }

    public void setBathDesc(String bathDesc) {
        this.bathDesc = bathDesc;
    }

    public String getAccountNum() {
        return accountNum;
    }

    public void setAccountNum(String accountNum) {
        this.accountNum = accountNum;
    }

    public String getCarRecordId() {
        return carRecordId;
    }

    public void setCarRecordId(String carRecordId) {
        this.carRecordId = carRecordId;
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

    public static void installHeaders(List<String> headers) {
        headers.add("批量采购名称");
        headers.add("描述");
    }

    public void install(CarRecordExport carRecordExport) {
        carRecordExport.setBathName(bathName);
        carRecordExport.setBathDesc(bathDesc);
    }
}
