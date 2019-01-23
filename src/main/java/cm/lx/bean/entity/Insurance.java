package cm.lx.bean.entity;

import cm.lx.bean.export.InsuranceExport;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.List;

@TableName("cm_insurance")
public class Insurance {

    @TableId(type = IdType.AUTO)
    Integer id;

    @TableField(value = "business_person")
    String businessPerson;

    @TableField(value = "car_brand")
    String carBrand;

    @TableField(value = "car_model")
    String carModel;

    @TableField(value = "consumer_name")
    String consumerName;

    @TableField(value = "consumer_sex")
    Integer consumerSex;

    @TableField(value = "consumer_birth")
    String consumerBirth;

    @TableField(value = "consumer_phone")
    String consumerPhone;

    @TableField(value = "travel_register")
    Long travelRegister;

    @TableField(value = "travel_lssuing")
    Long travelLssuing;

    @TableField(value = "consumer_type")
    Integer consumerType;

    @TableField(value = "renewal_fee")
    Double renewalFee;

    @TableField(value = "deal_renewal")
    Integer dealRenewal;

    @TableField(value = "renewal_desc")
    String renewalDesc;

    @TableField(value = "display_status")
    Integer displayStatus;

    Long ctime;

    Long utime;

    @TableField(exist = false)
    List<InsuranceBusiness> list;

    @TableField(exist = false)
    String strTravelRegister;

    @TableField(exist = false)
    String strTravelLssuing;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBusinessPerson() {
        return businessPerson;
    }

    public void setBusinessPerson(String businessPerson) {
        this.businessPerson = businessPerson;
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

    public Integer getConsumerSex() {
        return consumerSex;
    }

    public void setConsumerSex(Integer consumerSex) {
        this.consumerSex = consumerSex;
    }

    public String getConsumerBirth() {
        return consumerBirth;
    }

    public void setConsumerBirth(String consumerBirth) {
        this.consumerBirth = consumerBirth;
    }

    public String getConsumerPhone() {
        return consumerPhone;
    }

    public void setConsumerPhone(String consumerPhone) {
        this.consumerPhone = consumerPhone;
    }

    public Long getTravelRegister() {
        return travelRegister;
    }

    public void setTravelRegister(Long travelRegister) {
        this.travelRegister = travelRegister;
    }

    public Long getTravelLssuing() {
        return travelLssuing;
    }

    public void setTravelLssuing(Long travelLssuing) {
        this.travelLssuing = travelLssuing;
    }

    public Integer getConsumerType() {
        return consumerType;
    }

    public void setConsumerType(Integer consumerType) {
        this.consumerType = consumerType;
    }

    public Double getRenewalFee() {
        return renewalFee;
    }

    public void setRenewalFee(Double renewalFee) {
        this.renewalFee = renewalFee;
    }

    public Integer getDealRenewal() {
        return dealRenewal;
    }

    public void setDealRenewal(Integer dealRenewal) {
        this.dealRenewal = dealRenewal;
    }

    public String getRenewalDesc() {
        return renewalDesc;
    }

    public void setRenewalDesc(String renewalDesc) {
        this.renewalDesc = renewalDesc;
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

    public List<InsuranceBusiness> getList() {
        return list;
    }

    public void setList(List<InsuranceBusiness> list) {
        this.list = list;
    }

    public String getStrTravelRegister() {
        return strTravelRegister;
    }

    public void setStrTravelRegister(String strTravelRegister) {
        this.strTravelRegister = strTravelRegister;
    }

    public String getStrTravelLssuing() {
        return strTravelLssuing;
    }

    public void setStrTravelLssuing(String strTravelLssuing) {
        this.strTravelLssuing = strTravelLssuing;
    }


    public static void installHeaders(List<String> headers) {
        headers.add("业务员");
        headers.add("车辆品牌");
        headers.add("车型");
        headers.add("客户姓名");
        headers.add("客户性别");
        headers.add("出生日期");
        headers.add("电话号码");
        headers.add("行驶证登记日期");
        headers.add("行驶证发证日期");
        headers.add("客户类别");
        headers.add("续保押金");
        headers.add("续保押金处理");
        headers.add("备注");
    }

    public void install(InsuranceExport insuranceExport) {
        insuranceExport.setBusinessPerson(businessPerson);
        insuranceExport.setCarBrand(carBrand);
        insuranceExport.setCarModel(carModel);
        insuranceExport.setConsumerName(consumerName);
        insuranceExport.setConsumerSex(consumerSex==1?"男":"女");
        insuranceExport.setConsumerBirth(consumerBirth);
        insuranceExport.setConsumerPhone(consumerPhone);
        insuranceExport.setTravelRegister(strTravelRegister);
        insuranceExport.setTravelLssuing(strTravelLssuing);
        if(consumerType==1){
            insuranceExport.setConsumerType("全款");
        }else if(consumerType==2){
            insuranceExport.setConsumerType("按揭");
        }else if(consumerType==3){
            insuranceExport.setConsumerType("外拓");
        }
        insuranceExport.setRenewalFee(renewalFee);
        if(dealRenewal == 0){
            insuranceExport.setDealRenewal("无");
        }else if(dealRenewal == 1){
            insuranceExport.setDealRenewal("转收入");
        }else if(dealRenewal == 2){
            insuranceExport.setDealRenewal("退还");
        }else if(dealRenewal == 3){
            insuranceExport.setDealRenewal("暂存");
        }
        insuranceExport.setRenewalDesc(renewalDesc);
    }
}
