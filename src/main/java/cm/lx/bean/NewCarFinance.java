package cm.lx.bean;

import java.util.List;

public class NewCarFinance {

    Integer id;
    String financeCompany;
    String carBrand;
    String carModel;
    String carConfig;
    Double guidancePrice;
    Double downPayments;
    Double monthMortgage;
    Double serviceFee;
    Double otherFee;
    Double otherCost;
    Integer consumerProperty;
    Integer consumerResource;
    Integer purchaseUse;
    String consumerName;
    Integer consumerSex;
    Integer consumerAge;
    String consumerAddress;
    String consumerPhone;
    Integer displayStatus;
    Long ctime;
    Long utime;

    String strConsumerProperty;
    String strConsumerResource;
    String strPurchaseUse;
    String strConsumerAge;

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
