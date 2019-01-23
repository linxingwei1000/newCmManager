package cm.lx.bean.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.List;

/**
 * 定金寻车
 *
 * @author linxingwei
 * @date 2018/12/27
 */
@TableName("cm_car_deposit")
public class CarDeposit {

    @TableId(type = IdType.AUTO)
    Integer id;

    @TableField(value = "sale_person")
    String salePerson;

    @TableField(value = "deposit_date")
    Long depositDate;

    @TableField(value = "car_model")
    String carModel;

    @TableField(value = "car_color")
    String carColor;

    @TableField(value = "car_year")
    String carYear;

    @TableField(value = "car_config")
    String carConfig;

    String budget;

    @TableField(value = "give_car_date")
    Long giveCarDate;

    Double deposit;

    Long ctime;

    Long utime;

    @TableField(exist = false)
    List<CarPaidRecord> depositPaidList;

    @TableField(exist = false)
    String strDepositDate;

    @TableField(exist = false)
    String strGiveCarDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getUtime() {
        return utime;
    }

    public void setUtime(Long utime) {
        this.utime = utime;
    }

    public Long getCtime() {
        return ctime;
    }

    public void setCtime(Long ctime) {
        this.ctime = ctime;
    }

    public String getSalePerson() {
        return salePerson;
    }

    public void setSalePerson(String salePerson) {
        this.salePerson = salePerson;
    }

    public Long getDepositDate() {
        return depositDate;
    }

    public void setDepositDate(Long depositDate) {
        this.depositDate = depositDate;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getCarColor() {
        return carColor;
    }

    public void setCarColor(String carColor) {
        this.carColor = carColor;
    }

    public String getCarYear() {
        return carYear;
    }

    public void setCarYear(String carYear) {
        this.carYear = carYear;
    }

    public String getCarConfig() {
        return carConfig;
    }

    public void setCarConfig(String carConfig) {
        this.carConfig = carConfig;
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    public Long getGiveCarDate() {
        return giveCarDate;
    }

    public void setGiveCarDate(Long giveCarDate) {
        this.giveCarDate = giveCarDate;
    }

    public Double getDeposit() {
        return deposit;
    }

    public void setDeposit(Double deposit) {
        this.deposit = deposit;
    }

    public String getStrDepositDate() {
        return strDepositDate;
    }

    public void setStrDepositDate(String strDepositDate) {
        this.strDepositDate = strDepositDate;
    }

    public String getStrGiveCarDate() {
        return strGiveCarDate;
    }

    public void setStrGiveCarDate(String strGiveCarDate) {
        this.strGiveCarDate = strGiveCarDate;
    }

    public List<CarPaidRecord> getDepositPaidList() {
        return depositPaidList;
    }

    public void setDepositPaidList(List<CarPaidRecord> depositPaidList) {
        this.depositPaidList = depositPaidList;
    }
}
