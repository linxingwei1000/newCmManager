package cm.lx.bean.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.List;

@TableName("cm_new_car_finance")
public class NewCarFinance {

    @TableId(type = IdType.AUTO)
    Integer id;

    @TableField(value = "finance_company")
    String financeCompany;

    @TableField(value = "car_brand")
    String carBrand;

    @TableField(value = "car_model")
    String carModel;

    @TableField(value = "car_config")
    String carConfig;

    @TableField(value = "guidance_price")
    Double guidancePrice;

    @TableField(value = "down_payments")
    Double downPayments;

    @TableField(value = "month_mortgage")
    Double monthMortgage;

    @TableField(value = "service_fee")
    Double serviceFee;

    @TableField(value = "other_fee")
    Double otherFee;

    @TableField(value = "other_cost")
    Double otherCost;

    @TableField(value = "consumer_property")
    Integer consumerProperty;

    @TableField(value = "consumer_resource")
    Integer consumerResource;

    @TableField(value = "purchase_use")
    Integer purchaseUse;

    @TableField(value = "consumer_name")
    String consumerName;

    @TableField(value = "consumer_sex")
    Integer consumerSex;

    @TableField(value = "consumer_age")
    Integer consumerAge;

    @TableField(value = "consumer_address")
    String consumerAddress;

    @TableField(value = "consumer_phone")
    String consumerPhone;

    @TableField(value = "display_status")
    Integer displayStatus;

    Long ctime;

    Long utime;

    @TableField(exist = false)
    String strConsumerProperty;

    @TableField(exist = false)
    String strConsumerResource;

    @TableField(exist = false)
    String strPurchaseUse;

    @TableField(exist = false)
    String strConsumerAge;

    @TableField(exist = false)
    List<CarSaleSetup> otherCostList;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFinanceCompany() {
        return financeCompany;
    }

    public void setFinanceCompany(String financeCompany) {
        this.financeCompany = financeCompany;
    }

    public String getCarBrand() {
        return carBrand;
    }

    public void setCarBrand(String carBrand) {
        this.carBrand = carBrand;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getCarConfig() {
        return carConfig;
    }

    public void setCarConfig(String carConfig) {
        this.carConfig = carConfig;
    }

    public Double getGuidancePrice() {
        return guidancePrice;
    }

    public void setGuidancePrice(Double guidancePrice) {
        this.guidancePrice = guidancePrice;
    }

    public Double getDownPayments() {
        return downPayments;
    }

    public void setDownPayments(Double downPayments) {
        this.downPayments = downPayments;
    }

    public Double getMonthMortgage() {
        return monthMortgage;
    }

    public void setMonthMortgage(Double monthMortgage) {
        this.monthMortgage = monthMortgage;
    }

    public Double getServiceFee() {
        return serviceFee;
    }

    public void setServiceFee(Double serviceFee) {
        this.serviceFee = serviceFee;
    }

    public Double getOtherFee() {
        return otherFee;
    }

    public void setOtherFee(Double otherFee) {
        this.otherFee = otherFee;
    }

    public Double getOtherCost() {
        return otherCost;
    }

    public void setOtherCost(Double otherCost) {
        this.otherCost = otherCost;
    }

    public Integer getConsumerProperty() {
        return consumerProperty;
    }

    public void setConsumerProperty(Integer consumerProperty) {
        this.consumerProperty = consumerProperty;
    }

    public Integer getConsumerResource() {
        return consumerResource;
    }

    public void setConsumerResource(Integer consumerResource) {
        this.consumerResource = consumerResource;
    }

    public Integer getPurchaseUse() {
        return purchaseUse;
    }

    public void setPurchaseUse(Integer purchaseUse) {
        this.purchaseUse = purchaseUse;
    }

    public String getConsumerName() {
        return consumerName;
    }

    public void setConsumerName(String consumerName) {
        this.consumerName = consumerName;
    }

    public Integer getConsumerSex() {
        return consumerSex;
    }

    public void setConsumerSex(Integer consumerSex) {
        this.consumerSex = consumerSex;
    }

    public Integer getConsumerAge() {
        return consumerAge;
    }

    public void setConsumerAge(Integer consumerAge) {
        this.consumerAge = consumerAge;
    }

    public String getConsumerAddress() {
        return consumerAddress;
    }

    public void setConsumerAddress(String consumerAddress) {
        this.consumerAddress = consumerAddress;
    }

    public String getConsumerPhone() {
        return consumerPhone;
    }

    public void setConsumerPhone(String consumerPhone) {
        this.consumerPhone = consumerPhone;
    }

    public Integer getDisplayStatus() {
        return displayStatus;
    }

    public void setDisplayStatus(Integer displayStatus) {
        this.displayStatus = displayStatus;
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

    public String getStrConsumerProperty() {
        return strConsumerProperty;
    }

    public void setStrConsumerProperty(String strConsumerProperty) {
        this.strConsumerProperty = strConsumerProperty;
    }

    public String getStrConsumerAge() {
        return strConsumerAge;
    }

    public void setStrConsumerAge(String strConsumerAge) {
        this.strConsumerAge = strConsumerAge;
    }

    public String getStrPurchaseUse() {
        return strPurchaseUse;
    }

    public void setStrPurchaseUse(String strPurchaseUse) {
        this.strPurchaseUse = strPurchaseUse;
    }

    public String getStrConsumerResource() {
        return strConsumerResource;
    }

    public void setStrConsumerResource(String strConsumerResource) {
        this.strConsumerResource = strConsumerResource;
    }

    public List<CarSaleSetup> getOtherCostList() {
        return otherCostList;
    }

    public void setOtherCostList(List<CarSaleSetup> otherCostList) {
        this.otherCostList = otherCostList;
    }
}
