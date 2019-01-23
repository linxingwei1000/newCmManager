package cm.lx.bean.entity;

import cm.lx.bean.export.InsuranceExport;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.List;

@TableName("cm_insurance_business")
public class InsuranceBusiness {

    @TableId(type = IdType.AUTO)
    Integer id;

    @TableField(value = "business_type")
    Integer businessType;

    @TableField(value = "insurance_date")
    Long insuranceDate;

    @TableField(value = "qzx_date")
    Long qzxDate;

    @TableField(value = "qzx_company")
    String qzxCompany;

    @TableField(value = "qzx_fee")
    Double qzxFee;

    @TableField(value = "syx_date")
    Long syxDate;

    @TableField(value = "syx_company")
    String syxCompany;

    @TableField(value = "syx_fee")
    Double syxFee;

    @TableField(value = "syx_discount")
    String syxDiscount;

    @TableField(value = "expense_rebate")
    String expenseRebate;

    @TableField(value = "rebate_fee")
    Double rebateFee;

    @TableField(value = "return_fee")
    Double returnFee;

    @TableField(value = "business_desc")
    String  businessDesc;

    @TableField(value = "insurance_id")
    Integer insuranceId;

    @TableField(value = "display_status")
    Integer displayStatus;

    Long ctime;

    Long utime;

    @TableField(exist = false)
    String strInsuranceDate;

    @TableField(exist = false)
    String strQzxDate;

    @TableField(exist = false)
    String strSyxDate;

    @TableField(exist = false)
    String strBusinessType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBusinessType() {
        return businessType;
    }

    public void setBusinessType(Integer businessType) {
        this.businessType = businessType;
    }

    public Long getInsuranceDate() {
        return insuranceDate;
    }

    public void setInsuranceDate(Long insuranceDate) {
        this.insuranceDate = insuranceDate;
    }

    public Long getQzxDate() {
        return qzxDate;
    }

    public void setQzxDate(Long qzxDate) {
        this.qzxDate = qzxDate;
    }

    public String getQzxCompany() {
        return qzxCompany;
    }

    public void setQzxCompany(String qzxCompany) {
        this.qzxCompany = qzxCompany;
    }

    public Double getQzxFee() {
        return qzxFee;
    }

    public void setQzxFee(Double qzxFee) {
        this.qzxFee = qzxFee;
    }

    public Long getSyxDate() {
        return syxDate;
    }

    public void setSyxDate(Long syxDate) {
        this.syxDate = syxDate;
    }

    public String getSyxCompany() {
        return syxCompany;
    }

    public void setSyxCompany(String syxCompany) {
        this.syxCompany = syxCompany;
    }

    public Double getSyxFee() {
        return syxFee;
    }

    public void setSyxFee(Double syxFee) {
        this.syxFee = syxFee;
    }

    public String getSyxDiscount() {
        return syxDiscount;
    }

    public void setSyxDiscount(String syxDiscount) {
        this.syxDiscount = syxDiscount;
    }

    public String getExpenseRebate() {
        return expenseRebate;
    }

    public void setExpenseRebate(String expenseRebate) {
        this.expenseRebate = expenseRebate;
    }

    public Double getRebateFee() {
        return rebateFee;
    }

    public void setRebateFee(Double rebateFee) {
        this.rebateFee = rebateFee;
    }

    public Double getReturnFee() {
        return returnFee;
    }

    public void setReturnFee(Double returnFee) {
        this.returnFee = returnFee;
    }

    public String getBusinessDesc() {
        return businessDesc;
    }

    public void setBusinessDesc(String businessDesc) {
        this.businessDesc = businessDesc;
    }

    public Integer getInsuranceId() {
        return insuranceId;
    }

    public void setInsuranceId(Integer insuranceId) {
        this.insuranceId = insuranceId;
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

    public String getStrInsuranceDate() {
        return strInsuranceDate;
    }

    public void setStrInsuranceDate(String strInsuranceDate) {
        this.strInsuranceDate = strInsuranceDate;
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

    public String getStrBusinessType() {
        return strBusinessType;
    }

    public void setStrBusinessType(String strBusinessType) {
        this.strBusinessType = strBusinessType;
    }

    public static void installHeaders(List<String> headers) {
        headers.add("投保时间");
        headers.add("强制险到期时间");
        headers.add("强制险保险公司");
        headers.add("强制险保费");
        headers.add("商业险到期时间");
        headers.add("商业险保险公司");
        headers.add("商业险保费");
        headers.add("商业险折扣");
        headers.add("保险返点");
        headers.add("返点金额");
        headers.add("返还客户金额");
        headers.add("备注信息");
    }

    public void install(InsuranceExport insuranceExport) {
        insuranceExport.setInsuranceDate(strInsuranceDate);
        insuranceExport.setQzxDate(strQzxDate);
        insuranceExport.setQzxCompany(qzxCompany);
        insuranceExport.setQzxFee(qzxFee);
        insuranceExport.setSyxDate(strSyxDate);
        insuranceExport.setSyxCompany(syxCompany);
        insuranceExport.setSyxFee(syxFee);
        insuranceExport.setSyxDiscount(syxDiscount);
        insuranceExport.setExpenseRebate(expenseRebate);
        insuranceExport.setRebateFee(rebateFee);
        insuranceExport.setReturnFee(returnFee);
        insuranceExport.setBusinessDesc(businessDesc);
    }
}
