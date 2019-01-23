package cm.lx.bean.entity;

import cm.lx.bean.export.CarRecordExport;
import cm.lx.bean.export.MortgageRecordExport;
import cm.lx.bean.export.NewCarExport;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.List;

@TableName("cm_mortgage_record")
public class MortgageRecord {

    @TableId(type = IdType.AUTO)
    Integer id;

    @TableField(value = "delegate_person")
    String delegatePerson;

    @TableField(value = "car_brand")
    String carBrand;

    @TableField(value = "car_model")
    String carModel;

    @TableField(value = "consumer_name")
    String consumerName;

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

    @TableField(value = "assessment_fee")
    Double assessmentFee;

    @TableField(value = "risk_fee")
    Double riskFee;

    @TableField(value = "renewal_fee")
    Double renewalFee;

    @TableField(value = "pad_fee")
    Double padFee;

    @TableField(value = "door_fee")
    Double doorFee;

    @TableField(value = "stamp_duty")
    Double stampDuty;

    @TableField(value = "other_fee")
    Double otherFee;

    @TableField(value = "expense_is_company")
    Integer expenseIsCompany;

    @TableField(value = "expense_fee")
    Double expenseFee;

    @TableField(value = "expense_rebate")
    String expenseRebate;

    @TableField(value = "money_back_consumer")
    Double moneyBackConsumer;

    @TableField(value = "is_mortgage")
    Integer isMortgage;

    @TableField(value = "mortgage_date")
    Long mortgageDate;

    @TableField(value = "mortgage_money")
    Double mortgageMoney;

    @TableField(value = "a_mortgage_money")
    Double aMortgageMoney;

    @TableField(value = "is_sale")
    Integer isSale;

    @TableField(value = "sale_id")
    Integer saleId;

    @TableField(value = "is_new_car")
    Integer isNewCar;

    @TableField(value = "new_car_id")
    Integer newCarId;

    @TableField(value = "display_status")
    Integer displayStatus;


    Long ctime;

    Long utime;

    @TableField(exist = false)
    String strMortgageDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDelegatePerson() {
        return delegatePerson;
    }

    public void setDelegatePerson(String delegatePerson) {
        this.delegatePerson = delegatePerson;
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

    public String getConsumerName() {
        return consumerName;
    }

    public void setConsumerName(String consumerName) {
        this.consumerName = consumerName;
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

    public Double getAssessmentFee() {
        return assessmentFee;
    }

    public void setAssessmentFee(Double assessmentFee) {
        this.assessmentFee = assessmentFee;
    }

    public Double getRiskFee() {
        return riskFee;
    }

    public void setRiskFee(Double riskFee) {
        this.riskFee = riskFee;
    }

    public Double getRenewalFee() {
        return renewalFee;
    }

    public void setRenewalFee(Double renewalFee) {
        this.renewalFee = renewalFee;
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

    public Integer getExpenseIsCompany() {
        return expenseIsCompany;
    }

    public void setExpenseIsCompany(Integer expenseIsCompany) {
        this.expenseIsCompany = expenseIsCompany;
    }

    public Double getExpenseFee() {
        return expenseFee;
    }

    public void setExpenseFee(Double expenseFee) {
        this.expenseFee = expenseFee;
    }

    public String getExpenseRebate() {
        return expenseRebate;
    }

    public void setExpenseRebate(String expenseRebate) {
        this.expenseRebate = expenseRebate;
    }

    public Double getMoneyBackConsumer() {
        return moneyBackConsumer;
    }

    public void setMoneyBackConsumer(Double moneyBackConsumer) {
        this.moneyBackConsumer = moneyBackConsumer;
    }

    public Integer getIsMortgage() {
        return isMortgage;
    }

    public void setIsMortgage(Integer isMortgage) {
        this.isMortgage = isMortgage;
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

    public Double getaMortgageMoney() {
        return aMortgageMoney;
    }

    public void setaMortgageMoney(Double aMortgageMoney) {
        this.aMortgageMoney = aMortgageMoney;
    }

    public Integer getIsSale() {
        return isSale;
    }

    public void setIsSale(Integer isSale) {
        this.isSale = isSale;
    }

    public Integer getSaleId() {
        return saleId;
    }

    public void setSaleId(Integer saleId) {
        this.saleId = saleId;
    }

    public Integer getIsNewCar() {
        return isNewCar;
    }

    public void setIsNewCar(Integer isNewCar) {
        this.isNewCar = isNewCar;
    }

    public Integer getNewCarId() {
        return newCarId;
    }

    public void setNewCarId(Integer newCarId) {
        this.newCarId = newCarId;
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

    public static void installHeaders(List<String> headers,int type){
        if(type==1){
            headers.add("委托对象");
            headers.add("车辆品牌");
            headers.add("车型");
            headers.add("客户姓名");
        }
        headers.add("对接按揭专员");
        headers.add("按揭公司");
        headers.add("贷款金额");
        headers.add("利率");
        headers.add("按揭返点");
        headers.add("返还金额");
        headers.add("评估费");
        headers.add("风险金");
        headers.add("续保押金");
        headers.add("垫资费");
        headers.add("上门费");
        headers.add("印花税");
        headers.add("其它费用");
        if(type==1){
            headers.add("保费是否公司支出");
            headers.add("保费金额");
            headers.add("保险返点");
            headers.add("返还客户金额");
            headers.add("按揭是否已放款");
            headers.add("放款日期");
        }
        headers.add("放款金额");
//        headers.add("已放款金额");
    }

    public void install(CarRecordExport carRecordExport){
        carRecordExport.setMortgageCommissioner(mortgageCommissioner);
        carRecordExport.setMortgageCompany(mortgageCompany);
        carRecordExport.setLoanFee(loanFee);
        carRecordExport.setInterestRate(interestRate);
        carRecordExport.setMortgageRebate(mortgageRebate);
        carRecordExport.setBackFee(backFee);
        carRecordExport.setAssessmentFee(assessmentFee);
        carRecordExport.setRiskFee(riskFee);
        carRecordExport.setRenewalFee(renewalFee);
        carRecordExport.setPadFee(padFee);
        carRecordExport.setDoorFee(doorFee);
        carRecordExport.setStampDuty(stampDuty);
        carRecordExport.setOtherFee(otherFee);
        carRecordExport.setMortgageMoney(mortgageMoney);
    }

    public void installNewCar(NewCarExport newCarExport){
        newCarExport.setMortgageCommissioner(mortgageCommissioner);
        newCarExport.setMortgageCompany(mortgageCompany);
        newCarExport.setLoanFee(loanFee);
        newCarExport.setInterestRate(interestRate);
        newCarExport.setMortgageRebate(mortgageRebate);
        newCarExport.setBackFee(backFee);
        newCarExport.setAssessmentFee(assessmentFee);
        newCarExport.setRiskFee(riskFee);
        newCarExport.setRenewalFee(renewalFee);
        newCarExport.setPadFee(padFee);
        newCarExport.setDoorFee(doorFee);
        newCarExport.setStampDuty(stampDuty);
        newCarExport.setOtherFee(otherFee);
        newCarExport.setMortgageMoney(mortgageMoney);
    }

    public void installAll(MortgageRecordExport mortgageRecordExport){
        mortgageRecordExport.setDelegatePerson(delegatePerson);
        mortgageRecordExport.setCarBrand(carBrand);
        mortgageRecordExport.setCarModel(carModel);
        mortgageRecordExport.setConsumerName(consumerName);
        mortgageRecordExport.setMortgageCommissioner(mortgageCommissioner);
        mortgageRecordExport.setMortgageCompany(mortgageCompany);
        mortgageRecordExport.setLoanFee(loanFee);
        mortgageRecordExport.setInterestRate(interestRate);
        mortgageRecordExport.setMortgageRebate(mortgageRebate);
        mortgageRecordExport.setBackFee(backFee);
        mortgageRecordExport.setAssessmentFee(assessmentFee);
        mortgageRecordExport.setRiskFee(riskFee);
        mortgageRecordExport.setRenewalFee(renewalFee);
        mortgageRecordExport.setPadFee(padFee);
        mortgageRecordExport.setDoorFee(doorFee);
        mortgageRecordExport.setStampDuty(stampDuty);
        mortgageRecordExport.setOtherFee(otherFee);
        mortgageRecordExport.setExpenseIsCompany(expenseIsCompany==0?"否":"是");
        mortgageRecordExport.setExpenseFee(expenseFee);
        mortgageRecordExport.setExpenseRebate(expenseRebate);
        mortgageRecordExport.setMoneyBackConsumer(moneyBackConsumer);
        mortgageRecordExport.setIsMortgage(isMortgage==0?"否":"是");
        mortgageRecordExport.setMortgageDate(strMortgageDate);
        mortgageRecordExport.setMortgageMoney(mortgageMoney);
    }

}
