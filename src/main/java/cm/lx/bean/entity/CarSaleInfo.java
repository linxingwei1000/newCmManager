package cm.lx.bean.entity;

import cm.lx.bean.export.CarRecordExport;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.List;

@TableName("cm_car_sale_info")
public class CarSaleInfo {

    @TableId(type = IdType.AUTO)
    Integer id;

    @TableField(value = "sale_person")
    String salePerson;

    @TableField(value = "sale_date")
    Long saleDate;

    @TableField(value = "sale_money")
    Double saleMoney;

    @TableField(value = "expense_company")
    String expenseCompany;

    @TableField(value = "business_expense_fee")
    Double businessExpenseFee;

    @TableField(value = "force_expense_fee")
    Double forceExpenseFee;

    @TableField(value = "expense_rebate")
    Double expenseRebate;

    @TableField(value = "all_unearned_insurance")
    Double allUnearnedInsurance;

    @TableField(value = "pay_company_fee")
    Double payCompanyFee;

    @TableField(value = "agency_fee")
    Double agencyFee;

    @TableField(value = "unearned_insurance")
    Double unearnedInsurance;

    @TableField(value = "pay_money")
    Double payMoney;

    @TableField(value = "paid_money")
    Double paidMoney;

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

    @TableField(value = "car_record_id")
    Integer carRecordId;

    @TableField(value = "sale_type")
    Integer saleType;

    @TableField(value = "mortgage_id")
    Integer mortgageId;

    Long ctime;

    Long utime;

    @TableField(exist = false)
    String strSaleDate;

    @TableField(exist = false)
    String strConsumerProperty;

    @TableField(exist = false)
    String strConsumerResource;

    @TableField(exist = false)
    String strPurchaseUse;

    @TableField(exist = false)
    String strConsumerAge;

    @TableField(exist = false)
    MortgageRecord mortgageRecord;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Double getExpenseRebate() {
        return expenseRebate;
    }

    public void setExpenseRebate(Double expenseRebate) {
        this.expenseRebate = expenseRebate;
    }

    public Double getAllUnearnedInsurance() {
        return allUnearnedInsurance;
    }

    public void setAllUnearnedInsurance(Double allUnearnedInsurance) {
        this.allUnearnedInsurance = allUnearnedInsurance;
    }

    public Double getPayCompanyFee() {
        return payCompanyFee;
    }

    public void setPayCompanyFee(Double payCompanyFee) {
        this.payCompanyFee = payCompanyFee;
    }

    public Double getAgencyFee() {
        return agencyFee;
    }

    public void setAgencyFee(Double agencyFee) {
        this.agencyFee = agencyFee;
    }

    public Double getUnearnedInsurance() {
        return unearnedInsurance;
    }

    public void setUnearnedInsurance(Double unearnedInsurance) {
        this.unearnedInsurance = unearnedInsurance;
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

    public Integer getCarRecordId() {
        return carRecordId;
    }

    public void setCarRecordId(Integer carRecordId) {
        this.carRecordId = carRecordId;
    }

    public Integer getSaleType() {
        return saleType;
    }

    public void setSaleType(Integer saleType) {
        this.saleType = saleType;
    }

    public Integer getMortgageId() {
        return mortgageId;
    }

    public void setMortgageId(Integer mortgageId) {
        this.mortgageId = mortgageId;
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

    public MortgageRecord getMortgageRecord() {
        return mortgageRecord;
    }

    public void setMortgageRecord(MortgageRecord mortgageRecord) {
        this.mortgageRecord = mortgageRecord;
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

    public String getStrConsumerAge() {
        return strConsumerAge;
    }

    public void setStrConsumerAge(String strConsumerAge) {
        this.strConsumerAge = strConsumerAge;
    }

    public static void installHeaders(List<String> headers){
        headers.add("销售员");
        headers.add("销售日期");
        headers.add("销售价格");
        headers.add("保险公司");
        headers.add("商业保险费");
        headers.add("强制保险费");
        headers.add("保险返点");
        headers.add("销售中介费");
        headers.add("应付金额");
        headers.add("客户属性");
        headers.add("获客渠道");
        headers.add("购车用途");
        headers.add("客户姓名");
        headers.add("客户性别");
        headers.add("客户年龄段");
        headers.add("客户居住地");
        headers.add("客户手机号");
        headers.add("付款方式");
    }

    public void install(CarRecordExport carRecordExport){
        carRecordExport.setSalePerson(salePerson);
        carRecordExport.setSaleDate(strSaleDate);
        carRecordExport.setSaleMoney(saleMoney);
        carRecordExport.setExpenseCompany(expenseCompany);
        carRecordExport.setBusinessExpenseFee(businessExpenseFee);
        carRecordExport.setForceExpenseFee(forceExpenseFee);
        carRecordExport.setExpenseRebate(expenseRebate);
        carRecordExport.setAgencyFee(agencyFee);
        carRecordExport.setPayMoney(payMoney);
        carRecordExport.setConsumerProperty(strConsumerProperty);
        carRecordExport.setConsumerResource(strConsumerResource);
        carRecordExport.setPurchaseUse(strPurchaseUse);
        carRecordExport.setConsumerName(consumerName);
        carRecordExport.setConsumerSex(consumerSex==1?"男":"女");
        carRecordExport.setConsumerAge(strConsumerAge);
        carRecordExport.setConsumerAddress(consumerAddress);
        carRecordExport.setConsumerPhone(consumerPhone);
        carRecordExport.setSaleType(saleType==1?"全款":"按揭");
    }
}
