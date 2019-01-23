package cm.lx.bean.entity;

import cm.lx.bean.export.CarRecordExport;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.List;

/**
 * Created by linxingwei on 2018/2/8.
 */
@TableName("cm_car_cost")
public class CarCost {

    @TableId(type = IdType.AUTO)
    Integer id;

    @TableField(value = "car_record_id")
    Integer carRecordId;

    @TableField(value = "car_pick_person")
    String carPickPerson;

    @TableField(value = "mention_fee")
    Double mentionFee;

    @TableField(value = "mention_subsidy")
    Double mentionSubsidy;

    @TableField(value = "travel_fee")
    Double travelFee;

    @TableField(value = "put_fee")
    Double putFee;

    @TableField(value = "put_subsidy")
    Double putSubsidy;

    @TableField(value = "crossing_fee")
    Double crossingFee;

    @TableField(value = "mail_fee")
    Double mailFee;

    @TableField(value = "freight_fee")
    Double freightFee;

    @TableField(value = "billing_fee")
    Double billingFee;

    @TableField(value = "oil_fee")
    Double oilFee;

    @TableField(value = "cattle_fee")
    Double cattleFee;

    @TableField(value = "expense_fee")
    Double expenseFee;

    @TableField(value = "other_fee")
    Double otherFee;

    @TableField(value = "pre_sale_fee")
    Double preSaleFee;

    @TableField(value = "after_sale_fee")
    Double afterSaleFee;

    @TableField(value = "other_income_fee")
    Double otherIncomeFee;


    Long ctime;

    Long utime;

    @TableField(exist = false)
    Double allCost;

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

    public String getCarPickPerson() {
        return carPickPerson;
    }

    public void setCarPickPerson(String carPickPerson) {
        this.carPickPerson = carPickPerson;
    }

    public Double getMentionFee() {
        return mentionFee;
    }

    public void setMentionFee(Double mentionFee) {
        this.mentionFee = mentionFee;
    }

    public Double getMentionSubsidy() {
        return mentionSubsidy;
    }

    public void setMentionSubsidy(Double mentionSubsidy) {
        this.mentionSubsidy = mentionSubsidy;
    }

    public Double getTravelFee() {
        return travelFee;
    }

    public void setTravelFee(Double travelFee) {
        this.travelFee = travelFee;
    }

    public Double getPutFee() {
        return putFee;
    }

    public void setPutFee(Double putFee) {
        this.putFee = putFee;
    }

    public Double getPutSubsidy() {
        return putSubsidy;
    }

    public void setPutSubsidy(Double putSubsidy) {
        this.putSubsidy = putSubsidy;
    }

    public Double getCrossingFee() {
        return crossingFee;
    }

    public void setCrossingFee(Double crossingFee) {
        this.crossingFee = crossingFee;
    }

    public Double getMailFee() {
        return mailFee;
    }

    public void setMailFee(Double mailFee) {
        this.mailFee = mailFee;
    }

    public Double getFreightFee() {
        return freightFee;
    }

    public void setFreightFee(Double freightFee) {
        this.freightFee = freightFee;
    }

    public Double getBillingFee() {
        return billingFee;
    }

    public void setBillingFee(Double billingFee) {
        this.billingFee = billingFee;
    }

    public Double getOilFee() {
        return oilFee;
    }

    public void setOilFee(Double oilFee) {
        this.oilFee = oilFee;
    }

    public Double getCattleFee() {
        return cattleFee;
    }

    public void setCattleFee(Double cattleFee) {
        this.cattleFee = cattleFee;
    }

    public Double getExpenseFee() {
        return expenseFee;
    }

    public void setExpenseFee(Double expenseFee) {
        this.expenseFee = expenseFee;
    }

    public Double getOtherFee() {
        return otherFee;
    }

    public void setOtherFee(Double otherFee) {
        this.otherFee = otherFee;
    }

    public Double getPreSaleFee() {
        return preSaleFee;
    }

    public void setPreSaleFee(Double preSaleFee) {
        this.preSaleFee = preSaleFee;
    }

    public Double getAfterSaleFee() {
        return afterSaleFee;
    }

    public void setAfterSaleFee(Double afterSaleFee) {
        this.afterSaleFee = afterSaleFee;
    }

    public Double getOtherIncomeFee() {
        return otherIncomeFee;
    }

    public void setOtherIncomeFee(Double otherIncomeFee) {
        this.otherIncomeFee = otherIncomeFee;
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

    public Double getAllCost() {
        return allCost;
    }

    public void setAllCost(Double allCost) {
        this.allCost = allCost;
    }

    public void addAll() {
        allCost = mentionFee + mentionSubsidy + travelFee + putFee + putSubsidy + crossingFee + mailFee + freightFee +
        billingFee + oilFee + cattleFee + expenseFee + otherFee + preSaleFee;// + afterSaleFee + otherIncomeFee;
    }

    public static void installHeaders(List<String> headers) {
        headers.add("提车经办人");
        headers.add("提档费");
        headers.add("提档补贴");
        headers.add("差旅费");
        headers.add("入档费");
        headers.add("入档补贴");
        headers.add("提车过路费");
        headers.add("邮递费");
        headers.add("运费");
        headers.add("提车开票费");
        headers.add("提车加油费");
        headers.add("提车黄牛费");
        headers.add("保险费");
        headers.add("其他费用");
        headers.add("车辆售前整备费用");
        headers.add("车辆售后整备费用");
        headers.add("车辆其他收入");
    }

    public void install(CarRecordExport carRecordExport) {
        carRecordExport.setCarPickPerson(this.carPickPerson);
        carRecordExport.setMentionFee(this.mentionFee);
        carRecordExport.setMentionSubsidy(this.mentionSubsidy);
        carRecordExport.setTravelFee(this.travelFee);
        carRecordExport.setPutFee(this.putFee);
        carRecordExport.setPutSubsidy(this.putSubsidy);
        carRecordExport.setCrossingFee(this.crossingFee);
        carRecordExport.setMailFee(this.mailFee);
        carRecordExport.setFreightFee(this.freightFee);
        carRecordExport.setBillingFee(this.billingFee);
        carRecordExport.setCostOilFee(this.oilFee);
        carRecordExport.setCostCattleFee(this.cattleFee);
        carRecordExport.setExpenseFee(this.expenseFee);
        carRecordExport.setCostOtherFee(this.otherFee);
        carRecordExport.setPreSaleFee(this.preSaleFee);
        carRecordExport.setAfterSaleFee(this.afterSaleFee);
        carRecordExport.setOtherIncomeFee(this.otherIncomeFee);
    }
}
