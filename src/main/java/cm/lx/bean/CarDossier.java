package cm.lx.bean;

import java.util.List;

public class CarDossier {
    Integer id;
    Integer carRecordId;
    Integer carKeyNum;
    String carOwner;
    String carNum;
    Integer purchaseTimes;
    String carNumResource;
    Long qzxDate;
    String qzxPerson;
    String qzxCompany;
    String qzxAddress;
    Long syxDate;
    String syxPerson;
    String syxCompany;
    String syxAddress;
    Integer billSign;
    Integer register;
    Integer drivingLicense;
    Integer breakRule;
    Integer qzxStick;
    String carDischarge;
    Long annualTrial;
    Double insuranceBudget;
    Integer carStatus;
    String abs;
    Integer displayStatus;
    Long ctime;
    Long utime;

    String strQzxDate;
    String strSyxDate;
    String strAnnualTrial;

    CarRecord carRecord;

    List<CarPaidRecord> budgetList;

    List<CarRemark> remarkList;


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

    public Integer getCarKeyNum() {
        return carKeyNum;
    }

    public void setCarKeyNum(Integer carKeyNum) {
        this.carKeyNum = carKeyNum;
    }

    public String getCarOwner() {
        return carOwner;
    }

    public void setCarOwner(String carOwner) {
        this.carOwner = carOwner;
    }

    public String getCarNum() {
        return carNum;
    }

    public void setCarNum(String carNum) {
        this.carNum = carNum;
    }

    public Integer getPurchaseTimes() {
        return purchaseTimes;
    }

    public void setPurchaseTimes(Integer purchaseTimes) {
        this.purchaseTimes = purchaseTimes;
    }

    public String getCarNumResource() {
        return carNumResource;
    }

    public void setCarNumResource(String carNumResource) {
        this.carNumResource = carNumResource;
    }

    public Long getQzxDate() {
        return qzxDate;
    }

    public void setQzxDate(Long qzxDate) {
        this.qzxDate = qzxDate;
    }

    public String getQzxPerson() {
        return qzxPerson;
    }

    public void setQzxPerson(String qzxPerson) {
        this.qzxPerson = qzxPerson;
    }

    public String getQzxCompany() {
        return qzxCompany;
    }

    public void setQzxCompany(String qzxCompany) {
        this.qzxCompany = qzxCompany;
    }

    public String getQzxAddress() {
        return qzxAddress;
    }

    public void setQzxAddress(String qzxAddress) {
        this.qzxAddress = qzxAddress;
    }

    public Long getSyxDate() {
        return syxDate;
    }

    public void setSyxDate(Long syxDate) {
        this.syxDate = syxDate;
    }

    public String getSyxPerson() {
        return syxPerson;
    }

    public void setSyxPerson(String syxPerson) {
        this.syxPerson = syxPerson;
    }

    public String getSyxCompany() {
        return syxCompany;
    }

    public void setSyxCompany(String syxCompany) {
        this.syxCompany = syxCompany;
    }

    public String getSyxAddress() {
        return syxAddress;
    }

    public void setSyxAddress(String syxAddress) {
        this.syxAddress = syxAddress;
    }

    public Integer getBillSign() {
        return billSign;
    }

    public void setBillSign(Integer billSign) {
        this.billSign = billSign;
    }

    public Integer getRegister() {
        return register;
    }

    public void setRegister(Integer register) {
        this.register = register;
    }

    public Integer getDrivingLicense() {
        return drivingLicense;
    }

    public void setDrivingLicense(Integer drivingLicense) {
        this.drivingLicense = drivingLicense;
    }

    public Integer getBreakRule() {
        return breakRule;
    }

    public void setBreakRule(Integer breakRule) {
        this.breakRule = breakRule;
    }

    public Integer getQzxStick() {
        return qzxStick;
    }

    public void setQzxStick(Integer qzxStick) {
        this.qzxStick = qzxStick;
    }

    public String getCarDischarge() {
        return carDischarge;
    }

    public void setCarDischarge(String carDischarge) {
        this.carDischarge = carDischarge;
    }

    public Long getAnnualTrial() {
        return annualTrial;
    }

    public void setAnnualTrial(Long annualTrial) {
        this.annualTrial = annualTrial;
    }

    public Double getInsuranceBudget() {
        return insuranceBudget;
    }

    public void setInsuranceBudget(Double insuranceBudget) {
        this.insuranceBudget = insuranceBudget;
    }

    public Integer getCarStatus() {
        return carStatus;
    }

    public void setCarStatus(Integer carStatus) {
        this.carStatus = carStatus;
    }

    public String getAbs() {
        return abs;
    }

    public void setAbs(String abs) {
        this.abs = abs;
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

    public String getStrQzxDate() {
        return strQzxDate;
    }

    public void setStrQzxDate(String strQzxDate) {
        this.strQzxDate = strQzxDate;
    }

    public String getStrSyxDate() {
        return strSyxDate;
    }

    public void setStrSyxDate(String strSyxDate) {
        this.strSyxDate = strSyxDate;
    }

    public String getStrAnnualTrial() {
        return strAnnualTrial;
    }

    public void setStrAnnualTrial(String strAnnualTrial) {
        this.strAnnualTrial = strAnnualTrial;
    }

    public CarRecord getCarRecord() {
        return carRecord;
    }

    public void setCarRecord(CarRecord carRecord) {
        this.carRecord = carRecord;
    }

    public List<CarPaidRecord> getBudgetList() {
        return budgetList;
    }

    public void setBudgetList(List<CarPaidRecord> budgetList) {
        this.budgetList = budgetList;
    }

    public List<CarRemark> getRemarkList() {
        return remarkList;
    }

    public void setRemarkList(List<CarRemark> remarkList) {
        this.remarkList = remarkList;
    }
}
