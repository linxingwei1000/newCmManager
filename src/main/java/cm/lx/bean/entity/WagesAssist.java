package cm.lx.bean.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("cm_wages_assist")
public class WagesAssist {

    @TableId(type = IdType.AUTO)
    Integer id;

    @TableField(value = "sale_date")
    Long saleDate;

    @TableField(value = "sold_date")
    Long soldDate;

    @TableField(value = "sale_person")
    String salePerson;

    @TableField(value = "purchase_person")
    String purchasePerson;

    @TableField(value = "inside_person")
    String insidePerson;

    @TableField(value = "car_commission")
    Double carCommission;

    @TableField(value = "purchase_commission")
    Double purchaseCommission;

    @TableField(value = "share_dividends")
    Double shareDividends;

    @TableField(value = "car_record_id")
    Integer carRecordId;

    Long ctime;

    Long utime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(Long saleDate) {
        this.saleDate = saleDate;
    }

    public Long getSoldDate() {
        return soldDate;
    }

    public void setSoldDate(Long soldDate) {
        this.soldDate = soldDate;
    }

    public String getSalePerson() {
        return salePerson;
    }

    public void setSalePerson(String salePerson) {
        this.salePerson = salePerson;
    }

    public String getPurchasePerson() {
        return purchasePerson;
    }

    public void setPurchasePerson(String purchasePerson) {
        this.purchasePerson = purchasePerson;
    }

    public String getInsidePerson() {
        return insidePerson;
    }

    public void setInsidePerson(String insidePerson) {
        this.insidePerson = insidePerson;
    }

    public Double getCarCommission() {
        return carCommission;
    }

    public void setCarCommission(Double carCommission) {
        this.carCommission = carCommission;
    }

    public Double getPurchaseCommission() {
        return purchaseCommission;
    }

    public void setPurchaseCommission(Double purchaseCommission) {
        this.purchaseCommission = purchaseCommission;
    }

    public Double getShareDividends() {
        return shareDividends;
    }

    public void setShareDividends(Double shareDividends) {
        this.shareDividends = shareDividends;
    }

    public Integer getCarRecordId() {
        return carRecordId;
    }

    public void setCarRecordId(Integer carRecordId) {
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
}
