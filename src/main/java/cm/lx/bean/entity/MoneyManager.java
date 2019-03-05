package cm.lx.bean.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("cm_money_manager")
public class MoneyManager {

    @TableId(type = IdType.AUTO)
    Integer id;

    @TableField(value = "action_date")
    Long actionDate;

    @TableField(value = "action_end_date")
    Long actionEndDate;

    @TableField(value = "action_person")
    String actionPerson;

    @TableField(value = "action_desc")
    String actionDesc;

    @TableField(value = "action_fee")
    Double actionFee;

    @TableField(value = "action_type")
    Integer actionType;

    @TableField(value = "money_type")
    Integer moneyType;

    @TableField(value = "link_id")
    Integer linkId;


    Long ctime;

    Long utime;

    @TableField(exist = false)
    String strActionDate;

    @TableField(exist = false)
    String strActionEndDate;

    @TableField(exist = false)
    String linkDesc;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getActionDate() {
        return actionDate;
    }

    public void setActionDate(Long actionDate) {
        this.actionDate = actionDate;
    }

    public Long getActionEndDate() {
        return actionEndDate;
    }

    public void setActionEndDate(Long actionEndDate) {
        this.actionEndDate = actionEndDate;
    }

    public String getActionPerson() {
        return actionPerson;
    }

    public void setActionPerson(String actionPerson) {
        this.actionPerson = actionPerson;
    }

    public String getActionDesc() {
        return actionDesc;
    }

    public void setActionDesc(String actionDesc) {
        this.actionDesc = actionDesc;
    }

    public Double getActionFee() {
        return actionFee;
    }

    public void setActionFee(Double actionFee) {
        this.actionFee = actionFee;
    }

    public Integer getActionType() {
        return actionType;
    }

    public void setActionType(Integer actionType) {
        this.actionType = actionType;
    }

    public Integer getMoneyType() {
        return moneyType;
    }

    public void setMoneyType(Integer moneyType) {
        this.moneyType = moneyType;
    }

    public Integer getLinkId() {
        return linkId;
    }

    public void setLinkId(Integer linkId) {
        this.linkId = linkId;
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

    public String getStrActionDate() {
        return strActionDate;
    }

    public void setStrActionDate(String strActionDate) {
        this.strActionDate = strActionDate;
    }

    public String getStrActionEndDate() {
        return strActionEndDate;
    }

    public void setStrActionEndDate(String strActionEndDate) {
        this.strActionEndDate = strActionEndDate;
    }

    public String getLinkDesc() {
        return linkDesc;
    }

    public void setLinkDesc(String linkDesc) {
        this.linkDesc = linkDesc;
    }
}
