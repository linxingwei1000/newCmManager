package cm.lx.bean.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("cm_wages")
public class Wages {

    @TableId(type = IdType.AUTO)
    Integer id;

    String staff;

    @TableField(value = "pay_month")
    String payMonth;

    @TableField(value = "base_pay")
    Double basePay;

    @TableField(value = "compassionate_leave")
    Double compassionateLeave;

    Double late;

    @TableField(value = "un_hit_card")
    Double unHitCard;

    @TableField(value = "quality_commission")
    Double qualityCommission;

    @TableField(value = "car_commission")
    Double carCommission;

    @TableField(value = "auth_commission")
    Double authCommission;

    @TableField(value = "deposit_commission")
    Double depositCommission;

    @TableField(value = "bill_commission")
    Double billCommission;

    @TableField(value = "insurance_commission")
    Double insuranceCommission;

    @TableField(value = "purchase_commission")
    Double purchaseCommission;

    @TableField(value = "share_dividends")
    Double shareDividends;

    @TableField(value = "new_car_commission")
    Double newCarCommission;

    @TableField(value = "meal_supplement")
    Double mealSupplement;

    @TableField(value = "other_supplement")
    Double otherSupplement;


    Double wages;

    String stime;

    String etime;

    @TableField(value = "sold_time")
    String soldTime;

    Long ctime;

    Long utime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStaff() {
        return staff;
    }

    public void setStaff(String staff) {
        this.staff = staff;
    }

    public String getPayMonth() {
        return payMonth;
    }

    public void setPayMonth(String payMonth) {
        this.payMonth = payMonth;
    }

    public Double getBasePay() {
        return basePay;
    }

    public void setBasePay(Double basePay) {
        this.basePay = basePay;
    }

    public Double getCompassionateLeave() {
        return compassionateLeave;
    }

    public void setCompassionateLeave(Double compassionateLeave) {
        this.compassionateLeave = compassionateLeave;
    }

    public Double getLate() {
        return late;
    }

    public void setLate(Double late) {
        this.late = late;
    }

    public Double getUnHitCard() {
        return unHitCard;
    }

    public void setUnHitCard(Double unHitCard) {
        this.unHitCard = unHitCard;
    }

    public Double getQualityCommission() {
        return qualityCommission;
    }

    public void setQualityCommission(Double qualityCommission) {
        this.qualityCommission = qualityCommission;
    }

    public Double getCarCommission() {
        return carCommission;
    }

    public void setCarCommission(Double carCommission) {
        this.carCommission = carCommission;
    }

    public Double getAuthCommission() {
        return authCommission;
    }

    public void setAuthCommission(Double authCommission) {
        this.authCommission = authCommission;
    }

    public Double getDepositCommission() {
        return depositCommission;
    }

    public void setDepositCommission(Double depositCommission) {
        this.depositCommission = depositCommission;
    }

    public Double getBillCommission() {
        return billCommission;
    }

    public void setBillCommission(Double billCommission) {
        this.billCommission = billCommission;
    }

    public Double getInsuranceCommission() {
        return insuranceCommission;
    }

    public void setInsuranceCommission(Double insuranceCommission) {
        this.insuranceCommission = insuranceCommission;
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

    public Double getNewCarCommission() {
        return newCarCommission;
    }

    public void setNewCarCommission(Double newCarCommission) {
        this.newCarCommission = newCarCommission;
    }

    public Double getMealSupplement() {
        return mealSupplement;
    }

    public void setMealSupplement(Double mealSupplement) {
        this.mealSupplement = mealSupplement;
    }

    public Double getOtherSupplement() {
        return otherSupplement;
    }

    public void setOtherSupplement(Double otherSupplement) {
        this.otherSupplement = otherSupplement;
    }

    public Double getWages() {
        return wages;
    }

    public void setWages(Double wages) {
        this.wages = wages;
    }

    public String getStime() {
        return stime;
    }

    public void setStime(String stime) {
        this.stime = stime;
    }

    public String getEtime() {
        return etime;
    }

    public void setEtime(String etime) {
        this.etime = etime;
    }

    public String getSoldTime() {
        return soldTime;
    }

    public void setSoldTime(String soldTime) {
        this.soldTime = soldTime;
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

    public void calculateWages() {
        wages = basePay - compassionateLeave - late - unHitCard + qualityCommission + carCommission + authCommission +
                depositCommission + billCommission + insuranceCommission + purchaseCommission + shareDividends +
                newCarCommission + mealSupplement + otherSupplement;
    }
}
