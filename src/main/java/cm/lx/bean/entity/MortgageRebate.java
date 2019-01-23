package cm.lx.bean.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("cm_mortgage_rebate")
public class MortgageRebate {

    @TableId(type = IdType.AUTO)
    Integer id;

    @TableField(value = "car_model")
    String carModel;

    String number;

    @TableField(value = "loan_date")
    Long loanDate;

    @TableField(value = "bill_person")
    String billPerson;

    Double loan;

    @TableField(value = "interest_rate")
    String interestRate;

    @TableField(value = "back_money")
    Double backMoney;

    String abs;

    Long ctime;

    Long utime;

    @TableField(exist = false)
    String strLoanDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Long getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(Long loanDate) {
        this.loanDate = loanDate;
    }

    public String getBillPerson() {
        return billPerson;
    }

    public void setBillPerson(String billPerson) {
        this.billPerson = billPerson;
    }

    public Double getLoan() {
        return loan;
    }

    public void setLoan(Double loan) {
        this.loan = loan;
    }

    public String getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(String interestRate) {
        this.interestRate = interestRate;
    }

    public Double getBackMoney() {
        return backMoney;
    }

    public void setBackMoney(Double backMoney) {
        this.backMoney = backMoney;
    }

    public String getAbs() {
        return abs;
    }

    public void setAbs(String abs) {
        this.abs = abs;
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

    public String getStrLoanDate() {
        return strLoanDate;
    }

    public void setStrLoanDate(String strLoanDate) {
        this.strLoanDate = strLoanDate;
    }
}