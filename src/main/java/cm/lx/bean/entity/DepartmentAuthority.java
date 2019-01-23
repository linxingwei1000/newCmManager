package cm.lx.bean.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * Created by linxingwei on 2018/1/10.
 */
@TableName("cm_department_authority")
public class DepartmentAuthority {

    @TableId(type = IdType.AUTO)
    Integer id;

    @TableField(value = "department_name")
    String departmentName;

    @TableField(value = "account_page")
    Integer accountAble;

    @TableField(value = "car_page")
    Integer carAble;

    @TableField(value = "insurance_page")
    Integer insuranceAble;

    @TableField(value = "mortgage_page")
    Integer mortgageAble;

    @TableField(value = "new_car_page")
    Integer newCarAble;

    @TableField(value = "money_page")
    Integer moneyAble;

    @TableField(value = "stat_page")
    Integer statAble;

    @TableField(value = "dossier_page")
    Integer dossierAble;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public Integer getAccountAble() {
        return accountAble;
    }

    public void setAccountAble(Integer accountAble) {
        this.accountAble = accountAble;
    }

    public Integer getCarAble() {
        return carAble;
    }

    public void setCarAble(Integer carAble) {
        this.carAble = carAble;
    }

    public Integer getInsuranceAble() {
        return insuranceAble;
    }

    public void setInsuranceAble(Integer insuranceAble) {
        this.insuranceAble = insuranceAble;
    }

    public Integer getStatAble() {
        return statAble;
    }

    public void setStatAble(Integer statAble) {
        this.statAble = statAble;
    }

    public Integer getMortgageAble() {
        return mortgageAble;
    }

    public void setMortgageAble(Integer mortgageAble) {
        this.mortgageAble = mortgageAble;
    }

    public Integer getNewCarAble() {
        return newCarAble;
    }

    public void setNewCarAble(Integer newCarAble) {
        this.newCarAble = newCarAble;
    }

    public Integer getMoneyAble() {
        return moneyAble;
    }

    public void setMoneyAble(Integer moneyAble) {
        this.moneyAble = moneyAble;
    }

    public String toAuthStr(){
        return "" + this.accountAble + this.carAble + this.insuranceAble + this.statAble;
    }

    public Integer getDossierAble() {
        return dossierAble;
    }

    public void setDossierAble(Integer dossierAble) {
        this.dossierAble = dossierAble;
    }
}
