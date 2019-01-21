package cm.lx.bean;

import java.util.List;

public class NewCar {

    Integer id;
    String carBrand;
    String carModel;
    String carConfig;
    Double guidancePrice;
    String purchasePerson;
    Double purchaseMoney;
    String salePerson;
    Long saleDate;
    Double saleMoney;
    String expenseCompany;
    Double businessExpenseFee;
    Double forceExpenseFee;
    String expenseRebate;
    Double agencyFee;
    Double payMoney;
    Double paidMoney;
    Integer consumerProperty;
    Integer consumerResource;
    Integer purchaseUse;
    String consumerName;
    Integer consumerSex;
    Integer consumerAge;
    String consumerAddress;
    String consumerPhone;
    Integer saleType;
    Double otherCost;
    Double otherIncome;
    Integer mortgageId;
    Integer recordStatus;                  //记录状态，1：卖出中，2：已售
    Long ctime;
    Long utime;


    String strSaleDate;
    String strConsumerProperty;
    String strConsumerResource;
    String strPurchaseUse;
    String strConsumerAge;

    MortgageRecord mortgageRecord;

    List<CarSaleSetup> otherCostList;
    List<CarSaleSetup> otherIncomeList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getPurchasePerson() {
        return purchasePerson;
    }

    public void setPurchasePerson(String purchasePerson) {
        this.purchasePerson = purchasePerson;
    }

    public Double getPurchaseMoney() {
        return purchaseMoney;
    }

    public void setPurchaseMoney(Double purchaseMoney) {
        this.purchaseMoney = purchaseMoney;
    }

    public String getSalePerson() {
        return salePerson;
    }

    public void setSalePerson(String salePerson) {
        this.salePerson = salePerson;
    }

    public Long getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(Long saleDate) {
        this.saleDate = saleDate;
    }

    public Double getSaleMoney() {
        return saleMoney;
    }

    public void setSaleMoney(Double saleMoney) {
        this.saleMoney = saleMoney;
    }

    public String getExpenseCompany() {
        return expenseCompany;
    }

    public void setExpenseCompany(String expenseCompany) {
        this.expenseCompany = expenseCompany;
    }

    public Double getBusinessExpenseFee() {
        return businessExpenseFee;
    }

    public void setBusinessExpenseFee(Double businessExpenseFee) {
        this.businessExpenseFee = businessExpenseFee;
    }

    public Double getForceExpenseFee() {
        return forceExpenseFee;
    }

    public void setForceExpenseFee(Double forceExpenseFee) {
        this.forceExpenseFee = forceExpenseFee;
    }

    public String getExpenseRebate() {
        return expenseRebate;
    }

    public void setExpenseRebate(String expenseRebate) {
        this.expenseRebate = expenseRebate;
    }

    public Double getAgencyFee() {
        return agencyFee;
    }

    public void setAgencyFee(Double agencyFee) {
        this.agencyFee = agencyFee;
    }

    public Double getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(Double payMoney) {
        this.payMoney = payMoney;
    }

    public Double getPaidMoney() {
        return paidMoney;
    }

    public void setPaidMoney(Double paidMoney) {
        this.paidMoney = paidMoney;
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

    public Integer getSaleType() {
        return saleType;
    }

    public void setSaleType(Integer saleType) {
        this.saleType = saleType;
    }

    public Double getOtherCost() {
        return otherCost;
    }

    public void setOtherCost(Double otherCost) {
        this.otherCost = otherCost;
    }

    public Double getOtherIncome() {
        return otherIncome;
    }

    public void setOtherIncome(Double otherIncome) {
        this.otherIncome = otherIncome;
    }

    public Integer getMortgageId() {
        return mortgageId;
    }

    public void setMortgageId(Integer mortgageId) {
        this.mortgageId = mortgageId;
    }

    public Integer getRecordStatus() {
        return recordStatus;
    }

    public void setRecordStatus(Integer recordStatus) {
        this.recordStatus = recordStatus;
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

    public String getStrSaleDate() {
        return strSaleDate;
    }

    public void setStrSaleDate(String strSaleDate) {
        this.strSaleDate = strSaleDate;
    }

    public String getStrConsumerProperty() {
        return strConsumerProperty;
    }

    public void setStrConsumerProperty(String strConsumerProperty) {
        this.strConsumerProperty = strConsumerProperty;
    }

    public String getStrConsumerResource() {
        return strConsumerResource;
    }

    public void setStrConsumerResource(String strConsumerResource) {
        this.strConsumerResource = strConsumerResource;
    }

    public String getStrPurchaseUse() {
        return strPurchaseUse;
    }

    public void setStrPurchaseUse(String strPurchaseUse) {
        this.strPurchaseUse = strPurchaseUse;
    }

    public String getStrConsumerAge() {
        return strConsumerAge;
    }

    public void setStrConsumerAge(String strConsumerAge) {
        this.strConsumerAge = strConsumerAge;
    }

    public MortgageRecord getMortgageRecord() {
        return mortgageRecord;
    }

    public void setMortgageRecord(MortgageRecord mortgageRecord) {
        this.mortgageRecord = mortgageRecord;
    }

    public List<CarSaleSetup> getOtherCostList() {
        return otherCostList;
    }

    public void setOtherCostList(List<CarSaleSetup> otherCostList) {
        this.otherCostList = otherCostList;
    }

    public List<CarSaleSetup> getOtherIncomeList() {
        return otherIncomeList;
    }

    public void setOtherIncomeList(List<CarSaleSetup> otherIncomeList) {
        this.otherIncomeList = otherIncomeList;
    }

    public static void installHeaders(List<String> headers) {
        headers.add("车辆品牌");
        headers.add("车型");
        headers.add("车配置");
        headers.add("厂家指导价");
        headers.add("采购员");
        headers.add("采购价格");
        headers.add("销售员");
        headers.add("销售日期");
        headers.add("销售价格");
        headers.add("保险公司");
        headers.add("商业保险费");
        headers.add("强制保险费");
        headers.add("保险返点");
        headers.add("中介费");
        headers.add("应付金额");
        headers.add("客户属性");
        headers.add("获客渠道");
        headers.add("购车用途");
        headers.add("客户姓名");
        headers.add("客户性别");
        headers.add("客户年龄段");
        headers.add("客户居住地");
        headers.add("电话号码");
        headers.add("付款方式");
        headers.add("其他成本录入");
        headers.add("其他收入录入");
    }

    public void install(NewCarExport newCarExport) {
        newCarExport.setCarBrand(carBrand);
        newCarExport.setCarModel(carModel);
        newCarExport.setCarConfig(carConfig);
        newCarExport.setGuidancePrice(guidancePrice);
        newCarExport.setPurchasePerson(purchasePerson);
        newCarExport.setPurchaseMoney(purchaseMoney);
        newCarExport.setSalePerson(salePerson);
        newCarExport.setSaleDate(strSaleDate);
        newCarExport.setSaleMoney(saleMoney);
        newCarExport.setExpenseCompany(expenseCompany);
        newCarExport.setBusinessExpenseFee(businessExpenseFee);
        newCarExport.setForceExpenseFee(forceExpenseFee);
        newCarExport.setExpenseRebate(expenseRebate);
        newCarExport.setAgencyFee(agencyFee);
        newCarExport.setPayMoney(payMoney);
        newCarExport.setConsumerProperty(strConsumerProperty);
        newCarExport.setConsumerResource(strConsumerResource);
        newCarExport.setPurchaseUse(strPurchaseUse);
        newCarExport.setConsumerName(consumerName);
        newCarExport.setConsumerSex(consumerSex == 1 ? "男" : "女");
        newCarExport.setConsumerAge(strConsumerAge);
        newCarExport.setConsumerAddress(consumerAddress);
        newCarExport.setConsumerPhone(consumerPhone);
        newCarExport.setSaleType(saleType == 1 ? "全款" : "按揭");
        newCarExport.setOtherCost(otherCost);
        newCarExport.setOtherIncome(otherIncome);
    }


}
