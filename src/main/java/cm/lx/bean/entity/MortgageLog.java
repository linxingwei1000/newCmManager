package cm.lx.bean.entity;

import cm.lx.bean.export.MortgageLogExport;
import cm.lx.common.ContextType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.List;

@TableName("cm_mortgage_log")
public class MortgageLog {

    @TableId(type = IdType.AUTO)
    Integer id;

    @TableField(value = "action_person")
    String actionPerson;

    @TableField(value = "consumer_name")
    String consumerName;

    @TableField(value = "consumer_phone")
    String consumerPhone;

    @TableField(value = "car_brand")
    String carBrand;

    @TableField(value = "car_model")
    String carModel;

    @TableField(value = "mortgage_commissioner")
    String mortgageCommissioner;

    @TableField(value = "mortgage_company")
    String mortgageCompany;

    @TableField(value = "loan_fee")
    Double loanFee;

    @TableField(value = "interest_rate")
    String interestRate;

    @TableField(value = "mortgage_rebate")
    Double mortgageRebate;

    @TableField(value = "back_fee")
    Double backFee;

    @TableField(value = "pay_back_fee")
    Double payBackFee;

    @TableField(value = "assessment_fee")
    Double assessmentFee;

    @TableField(value = "renewal_fee")
    Double renewalFee;

    @TableField(value = "sign_bill_fee")
    Double signBillFee;

    @TableField(value = "over_year_fee")
    Double overYearFee;

    @TableField(value = "risk_fee")
    Double riskFee;

    @TableField(value = "pad_fee")
    Double padFee;

    @TableField(value = "door_fee")
    Double doorFee;

    @TableField(value = "stamp_duty")
    Double stampDuty;

    @TableField(value = "other_fee")
    Double otherFee;

    @TableField(value = "expense_company")
    String expenseCompany;

    @TableField(value = "business_expense_fee")
    Double businessExpenseFee;

    @TableField(value = "force_expense_fee")
    Double forceExpenseFee;

    @TableField(value = "business_expense_rebate")
    Double businessExpenseRebate;

    @TableField(value = "business_expense_back")
    Double businessExpenseBack;

    @TableField(value = "mortgage_date")
    Long mortgageDate;

    @TableField(value = "mortgage_money")
    Double mortgageMoney;

    @TableField(value = "gross_profit")
    Double grossProfit;

    @TableField(value = "need_pay_consumer")
    Double needPayConsumer;

    @TableField(value = "agent_pay_fee")
    Double agentPayFee;

    @TableField(value = "mortgage_type")
    Integer mortgageType;

    @TableField(value = "display_status")
    Integer displayStatus;

    Long ctime;

    Long utime;

    @TableField(exist = false)
    String strMortgageDate;

    @TableField(exist = false)
    List<CarPaidRecord> mortgagePaidList;

    @TableField(exist = false)
    List<CarSaleSetup> agentPayList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getActionPerson() {
        return actionPerson;
    }

    public void setActionPerson(String actionPerson) {
        this.actionPerson = actionPerson;
    }

    public String getConsumerName() {
        return consumerName;
    }

    public void setConsumerName(String consumerName) {
        this.consumerName = consumerName;
    }

    public String getConsumerPhone() {
        return consumerPhone;
    }

    public void setConsumerPhone(String consumerPhone) {
        this.consumerPhone = consumerPhone;
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

    public String getMortgageCommissioner() {
        return mortgageCommissioner;
    }

    public void setMortgageCommissioner(String mortgageCommissioner) {
        this.mortgageCommissioner = mortgageCommissioner;
    }

    public String getMortgageCompany() {
        return mortgageCompany;
    }

    public void setMortgageCompany(String mortgageCompany) {
        this.mortgageCompany = mortgageCompany;
    }

    public Double getLoanFee() {
        return loanFee;
    }

    public void setLoanFee(Double loanFee) {
        this.loanFee = loanFee;
    }

    public String getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(String interestRate) {
        this.interestRate = interestRate;
    }

    public Double getMortgageRebate() {
        return mortgageRebate;
    }

    public void setMortgageRebate(Double mortgageRebate) {
        this.mortgageRebate = mortgageRebate;
    }

    public Double getBackFee() {
        return backFee;
    }

    public void setBackFee(Double backFee) {
        this.backFee = backFee;
    }

    public Double getPayBackFee() {
        return payBackFee;
    }

    public void setPayBackFee(Double payBackFee) {
        this.payBackFee = payBackFee;
    }

    public Double getAssessmentFee() {
        return assessmentFee;
    }

    public void setAssessmentFee(Double assessmentFee) {
        this.assessmentFee = assessmentFee;
    }

    public Double getRenewalFee() {
        return renewalFee;
    }

    public void setRenewalFee(Double renewalFee) {
        this.renewalFee = renewalFee;
    }

    public Double getSignBillFee() {
        return signBillFee;
    }

    public void setSignBillFee(Double signBillFee) {
        this.signBillFee = signBillFee;
    }

    public Double getOverYearFee() {
        return overYearFee;
    }

    public void setOverYearFee(Double overYearFee) {
        this.overYearFee = overYearFee;
    }

    public Double getRiskFee() {
        return riskFee;
    }

    public void setRiskFee(Double riskFee) {
        this.riskFee = riskFee;
    }

    public Double getPadFee() {
        return padFee;
    }

    public void setPadFee(Double padFee) {
        this.padFee = padFee;
    }

    public Double getDoorFee() {
        return doorFee;
    }

    public void setDoorFee(Double doorFee) {
        this.doorFee = doorFee;
    }

    public Double getStampDuty() {
        return stampDuty;
    }

    public void setStampDuty(Double stampDuty) {
        this.stampDuty = stampDuty;
    }

    public Double getOtherFee() {
        return otherFee;
    }

    public void setOtherFee(Double otherFee) {
        this.otherFee = otherFee;
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

    public Double getBusinessExpenseRebate() {
        return businessExpenseRebate;
    }

    public void setBusinessExpenseRebate(Double businessExpenseRebate) {
        this.businessExpenseRebate = businessExpenseRebate;
    }

    public Double getBusinessExpenseBack() {
        return businessExpenseBack;
    }

    public void setBusinessExpenseBack(Double businessExpenseBack) {
        this.businessExpenseBack = businessExpenseBack;
    }

    public Long getMortgageDate() {
        return mortgageDate;
    }

    public void setMortgageDate(Long mortgageDate) {
        this.mortgageDate = mortgageDate;
    }

    public Double getMortgageMoney() {
        return mortgageMoney;
    }

    public void setMortgageMoney(Double mortgageMoney) {
        this.mortgageMoney = mortgageMoney;
    }

    public Double getGrossProfit() {
        return grossProfit;
    }

    public void setGrossProfit(Double grossProfit) {
        this.grossProfit = grossProfit;
    }

    public Double getNeedPayConsumer() {
        return needPayConsumer;
    }

    public void setNeedPayConsumer(Double needPayConsumer) {
        this.needPayConsumer = needPayConsumer;
    }

    public Double getAgentPayFee() {
        return agentPayFee;
    }

    public void setAgentPayFee(Double agentPayFee) {
        this.agentPayFee = agentPayFee;
    }

    public Integer getMortgageType() {
        return mortgageType;
    }

    public void setMortgageType(Integer mortgageType) {
        this.mortgageType = mortgageType;
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

    public String getStrMortgageDate() {
        return strMortgageDate;
    }

    public void setStrMortgageDate(String strMortgageDate) {
        this.strMortgageDate = strMortgageDate;
    }

    public List<CarPaidRecord> getMortgagePaidList() {
        return mortgagePaidList;
    }

    public void setMortgagePaidList(List<CarPaidRecord> mortgagePaidList) {
        this.mortgagePaidList = mortgagePaidList;
    }

    public List<CarSaleSetup> getAgentPayList() {
        return agentPayList;
    }

    public void setAgentPayList(List<CarSaleSetup> agentPayList) {
        this.agentPayList = agentPayList;
    }

    public static void installHeaders(List<String> headers){
        headers.add("按揭类型");
        headers.add("委托人或业务员");
        headers.add("客户姓名");
        headers.add("客户联系方式");
        headers.add("车辆品牌");
        headers.add("车型");
        headers.add("按揭专员");
        headers.add("按揭公司");
        headers.add("贷款金额");
        headers.add("利率");
        headers.add("按揭返点");
        headers.add("返还金额");
        headers.add("支付委托人返点金额");
        headers.add("评估费");
        headers.add("续保押金");
        headers.add("签单费");
        headers.add("超年限费");
        headers.add("风险金");
        headers.add("垫资费");
        headers.add("上门费");
        headers.add("印花税");
        headers.add("其它费用");
        headers.add("保险公司");
        headers.add("商业保险费");
        headers.add("强制保险费");
        headers.add("商业保险返点");
        headers.add("商业险返点金额");
        headers.add("放款日期");
        headers.add("放款金额");
        headers.add("毛利润");
        headers.add("应支付客户金额");
        headers.add("代付金额");
    }

    public void installAll(MortgageLogExport mortgageLogExport){
        mortgageLogExport.setMortgageType(mortgageType.equals(ContextType.MORTGAGE_TYPE_AGENCY)?"代办":"外拓");
        mortgageLogExport.setActionPerson(actionPerson);
        mortgageLogExport.setConsumerName(consumerName);
        mortgageLogExport.setConsumerPhone(consumerPhone);
        mortgageLogExport.setCarBrand(carBrand);
        mortgageLogExport.setCarModel(carModel);
        mortgageLogExport.setMortgageCommissioner(mortgageCommissioner);
        mortgageLogExport.setMortgageCompany(mortgageCompany);
        mortgageLogExport.setLoanFee(loanFee);
        mortgageLogExport.setInterestRate(interestRate);
        mortgageLogExport.setMortgageRebate(mortgageRebate);
        mortgageLogExport.setBackFee(backFee);
        mortgageLogExport.setPayBackFee(payBackFee);
        mortgageLogExport.setAssessmentFee(assessmentFee);
        mortgageLogExport.setRenewalFee(renewalFee);
        mortgageLogExport.setSignBillFee(signBillFee);
        mortgageLogExport.setOverYearFee(overYearFee);
        mortgageLogExport.setRiskFee(riskFee);
        mortgageLogExport.setPadFee(padFee);
        mortgageLogExport.setDoorFee(doorFee);
        mortgageLogExport.setStampDuty(stampDuty);
        mortgageLogExport.setOtherFee(otherFee);
        mortgageLogExport.setExpenseCompany(expenseCompany);
        mortgageLogExport.setBusinessExpenseFee(businessExpenseFee);
        mortgageLogExport.setForceExpenseFee(forceExpenseFee);
        mortgageLogExport.setBusinessExpenseRebate(businessExpenseRebate);
        mortgageLogExport.setBusinessExpenseBack(businessExpenseBack);
        mortgageLogExport.setMortgageDate(strMortgageDate);
        mortgageLogExport.setMortgageMoney(mortgageMoney);
        mortgageLogExport.setGrossProfit(grossProfit);
        mortgageLogExport.setNeedPayConsumer(needPayConsumer);
        mortgageLogExport.setAgentPayFee(agentPayFee);
    }
}
