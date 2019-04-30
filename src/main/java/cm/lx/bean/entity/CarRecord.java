package cm.lx.bean.entity;

import cm.lx.bean.export.CarRecordExport;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.List;

@TableName("cm_car_record")
public class CarRecord {

    @TableId(type = IdType.AUTO)
    Integer id;

    @TableField(value = "purchase_date")
    Long purchaseDate;

    @TableField(value = "frame_num")
    String frameNum;

    @TableField(value = "key_num")
    String keyNum;

    @TableField(value = "purchase_person")
    String purchasePerson;

    @TableField(value = "outside_person")
    String outsidePerson;

    @TableField(value = "outside_money")
    Double outsideMoney;

    @TableField(value = "car_brand")
    String carBrand;

    @TableField(value = "car_model")
    String carModel;

    @TableField(value = "car_displacement")
    String carDisplacement;

    @TableField(value = "car_channel")
    Integer carChannel;

    @TableField(value = "car_resource")
    String carResource;

    @TableField(value = "car_num_resource")
    String carNumResource;

    @TableField(value = "car_config")
    String carConfig;

    @TableField(value = "car_create_time")
    String carCreateTime;

    @TableField(value = "car_purchase_time")
    String carPurchaseTime;

    @TableField(value = "car_run_num")
    String carRunNum;

    @TableField(value = "car_out_colour")
    String carOutColour;

    @TableField(value = "car_inside_colour")
    String carInsideColour;

    @TableField(value = "car_new_sale")
    Double carNewSale;

    @TableField(value = "car_take_type")
    Integer carTakeType;

    @TableField(value = "car_status")
    Integer carStatus;

    @TableField(value = "car_status_desc")
    String carStatusDesc;

    @TableField(value = "agency_fee")
    Double agencyFee;

    @TableField(value = "purchase_money")
    Double purchaseMoney;

    @TableField(value = "deposit")
    Double deposit;

    @TableField(value = "paid_money")
    Double paidMoney;

    @TableField(value = "hall_money")
    Double hallMoney;

    @TableField(value = "q_authority_money")
    Double qAuthorityMoney;

    @TableField(value = "a_authority_money")
    Double aAuthorityMoney;

    @TableField(value = "purchase_type")
    Integer purchaseType;

    @TableField(value = "sold_date")
    Long soldDate;

    /**
     * 记录状态，1：采购中，2：车辆入库
     */
    @TableField(value = "record_status")
    Integer  recordStatus;

    /**
     * 是否已售
     */
    @TableField(value = "is_sale")
    Integer isSale;

    /**
     * 对应销售表id
     */
    @TableField(value = "sale_id")
    Integer saleId;

    /**
     * 对应保险id
     */
    @TableField(value = "expense_id")
    Integer expenseId;

    @TableField(value = "gross_profit")
    String grossProfit;

    @TableField(value = "vehicle_premium")
    Double vehiclePremium;

    @TableField(value = "stock_duration")
    Double stockDuration;

    Long ctime;

    Long utime;

    //页面显示字段
    @TableField(exist = false)
    String strPurchaseDate;

    @TableField(exist = false)
    String strCarTakeType;

    @TableField(exist = false)
    String strCarStatus;

    @TableField(exist = false)
    String strCarChannel;

    @TableField(exist = false)
    String strPurchaseType;

    @TableField(exist = false)
    String strSoldDate;

    @TableField(exist = false)
    List<CarPaidRecord> purchasePaidList;

    @TableField(exist = false)
    List<CarRemark> purchaseRemark;

    @TableField(exist = false)
    List<CarRemark> stockRemark;

    @TableField(exist = false)
    List<CarRemark> saleRemark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Long purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getFrameNum() {
        return frameNum;
    }

    public void setFrameNum(String frameNum) {
        this.frameNum = frameNum;
    }

    public String getKeyNum() {
        return keyNum;
    }

    public void setKeyNum(String keyNum) {
        this.keyNum = keyNum;
    }

    public String getPurchasePerson() {
        return purchasePerson;
    }

    public void setPurchasePerson(String purchasePerson) {
        this.purchasePerson = purchasePerson;
    }

    public String getOutsidePerson() {
        return outsidePerson;
    }

    public void setOutsidePerson(String outsidePerson) {
        this.outsidePerson = outsidePerson;
    }

    public Double getOutsideMoney() {
        return outsideMoney;
    }

    public void setOutsideMoney(Double outsideMoney) {
        this.outsideMoney = outsideMoney;
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

    public String getCarDisplacement() {
        return carDisplacement;
    }

    public void setCarDisplacement(String carDisplacement) {
        this.carDisplacement = carDisplacement;
    }

    public Integer getCarChannel() {
        return carChannel;
    }

    public void setCarChannel(Integer carChannel) {
        this.carChannel = carChannel;
    }

    public String getCarResource() {
        return carResource;
    }

    public void setCarResource(String carResource) {
        this.carResource = carResource;
    }

    public String getCarNumResource() {
        return carNumResource;
    }

    public void setCarNumResource(String carNumResource) {
        this.carNumResource = carNumResource;
    }

    public String getCarConfig() {
        return carConfig;
    }

    public void setCarConfig(String carConfig) {
        this.carConfig = carConfig;
    }

    public String getCarCreateTime() {
        return carCreateTime;
    }

    public void setCarCreateTime(String carCreateTime) {
        this.carCreateTime = carCreateTime;
    }

    public String getCarPurchaseTime() {
        return carPurchaseTime;
    }

    public void setCarPurchaseTime(String carPurchaseTime) {
        this.carPurchaseTime = carPurchaseTime;
    }

    public String getCarRunNum() {
        return carRunNum;
    }

    public void setCarRunNum(String carRunNum) {
        this.carRunNum = carRunNum;
    }

    public String getCarOutColour() {
        return carOutColour;
    }

    public void setCarOutColour(String carOutColour) {
        this.carOutColour = carOutColour;
    }

    public String getCarInsideColour() {
        return carInsideColour;
    }

    public void setCarInsideColour(String carInsideColour) {
        this.carInsideColour = carInsideColour;
    }

    public Double getCarNewSale() {
        return carNewSale;
    }

    public void setCarNewSale(Double carNewSale) {
        this.carNewSale = carNewSale;
    }

    public Integer getCarTakeType() {
        return carTakeType;
    }

    public void setCarTakeType(Integer carTakeType) {
        this.carTakeType = carTakeType;
    }

    public Integer getCarStatus() {
        return carStatus;
    }

    public void setCarStatus(Integer carStatus) {
        this.carStatus = carStatus;
    }

    public String getCarStatusDesc() {
        return carStatusDesc;
    }

    public void setCarStatusDesc(String carStatusDesc) {
        this.carStatusDesc = carStatusDesc;
    }

    public Double getAgencyFee() {
        return agencyFee;
    }

    public void setAgencyFee(Double agencyFee) {
        this.agencyFee = agencyFee;
    }

    public Double getPurchaseMoney() {
        return purchaseMoney;
    }

    public void setPurchaseMoney(Double purchaseMoney) {
        this.purchaseMoney = purchaseMoney;
    }

    public Double getDeposit() {
        return deposit;
    }

    public void setDeposit(Double deposit) {
        this.deposit = deposit;
    }

    public Double getPaidMoney() {
        return paidMoney;
    }

    public void setPaidMoney(Double paidMoney) {
        this.paidMoney = paidMoney;
    }

    public Double getHallMoney() {
        return hallMoney;
    }

    public void setHallMoney(Double hallMoney) {
        this.hallMoney = hallMoney;
    }

    public Double getqAuthorityMoney() {
        return qAuthorityMoney;
    }

    public void setqAuthorityMoney(Double qAuthorityMoney) {
        this.qAuthorityMoney = qAuthorityMoney;
    }

    public Double getaAuthorityMoney() {
        return aAuthorityMoney;
    }

    public void setaAuthorityMoney(Double aAuthorityMoney) {
        this.aAuthorityMoney = aAuthorityMoney;
    }

    public Integer getPurchaseType() {
        return purchaseType;
    }

    public void setPurchaseType(Integer purchaseType) {
        this.purchaseType = purchaseType;
    }

    public Long getSoldDate() {
        return soldDate;
    }

    public void setSoldDate(Long soldDate) {
        this.soldDate = soldDate;
    }

    public Integer getRecordStatus() {
        return recordStatus;
    }

    public void setRecordStatus(Integer recordStatus) {
        this.recordStatus = recordStatus;
    }

    public Integer getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(Integer expenseId) {
        this.expenseId = expenseId;
    }

    public String getGrossProfit() {
        return grossProfit;
    }

    public void setGrossProfit(String grossProfit) {
        this.grossProfit = grossProfit;
    }

    public Double getVehiclePremium() {
        return vehiclePremium;
    }

    public void setVehiclePremium(Double vehiclePremium) {
        this.vehiclePremium = vehiclePremium;
    }

    public Double getStockDuration() {
        return stockDuration;
    }

    public void setStockDuration(Double stockDuration) {
        this.stockDuration = stockDuration;
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

    public String getStrPurchaseDate() {
        return strPurchaseDate;
    }

    public void setStrPurchaseDate(String strPurchaseDate) {
        this.strPurchaseDate = strPurchaseDate;
    }

    public String getStrCarStatus() {
        return strCarStatus;
    }

    public void setStrCarStatus(String strCarStatus) {
        this.strCarStatus = strCarStatus;
    }

    public String getStrCarTakeType() {
        return strCarTakeType;
    }

    public void setStrCarTakeType(String strCarTakeType) {
        this.strCarTakeType = strCarTakeType;
    }

    public String getStrCarChannel() {
        return strCarChannel;
    }

    public void setStrCarChannel(String strCarChannel) {
        this.strCarChannel = strCarChannel;
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

    public String getStrPurchaseType() {
        return strPurchaseType;
    }

    public void setStrPurchaseType(String strPurchaseType) {
        this.strPurchaseType = strPurchaseType;
    }

    public String getStrSoldDate() {
        return strSoldDate;
    }

    public void setStrSoldDate(String strSoldDate) {
        this.strSoldDate = strSoldDate;
    }

    public List<CarPaidRecord> getPurchasePaidList() {
        return purchasePaidList;
    }

    public void setPurchasePaidList(List<CarPaidRecord> purchasePaidList) {
        this.purchasePaidList = purchasePaidList;
    }

    public List<CarRemark> getPurchaseRemark() {
        return purchaseRemark;
    }

    public void setPurchaseRemark(List<CarRemark> purchaseRemark) {
        this.purchaseRemark = purchaseRemark;
    }

    public List<CarRemark> getStockRemark() {
        return stockRemark;
    }

    public void setStockRemark(List<CarRemark> stockRemark) {
        this.stockRemark = stockRemark;
    }

    public List<CarRemark> getSaleRemark() {
        return saleRemark;
    }

    public void setSaleRemark(List<CarRemark> saleRemark) {
        this.saleRemark = saleRemark;
    }

    public static void installHeaders(List<String> headers){
        headers.add("车辆毛利润");
        headers.add("采购日期");
        headers.add("车架号");
        headers.add("钥匙编号");
        headers.add("采购人");
        headers.add("外部合伙人");
        headers.add("外部合伙金额");
        headers.add("车辆品牌");
        headers.add("车型");
        headers.add("排量");
        headers.add("车源");
        headers.add("车源所在地");
        headers.add("车牌所在地");
        headers.add("车配置");
        headers.add("出厂日期");
        headers.add("入户日期");
        headers.add("公里数（万）");
        headers.add("外观颜色");
        headers.add("内饰颜色");
        headers.add("新车指导价");
        headers.add("车况");
        headers.add("车况描述");
        headers.add("提车方式");
        headers.add("收购价格");
        headers.add("展厅标价");
        headers.add("全款权限底价");
        headers.add("按揭权限底价");
        headers.add("采购中介费");
        headers.add("车辆来源");
    }

    public void install(CarRecordExport carRecordExport){
        carRecordExport.setGrossProfit(grossProfit);
        carRecordExport.setPurchaseDate(this.strPurchaseDate);
        carRecordExport.setFrameNum(this.frameNum);
        carRecordExport.setKeyNum(this.keyNum);
        carRecordExport.setPurchasePerson(this.purchasePerson);
        carRecordExport.setOutsidePerson(this.outsidePerson);
        carRecordExport.setOutsideMoney(this.outsideMoney);
        carRecordExport.setCarBrand(this.carBrand);
        carRecordExport.setCarModel(this.carModel);
        carRecordExport.setCarDisplacement(this.carDisplacement);
        carRecordExport.setCarChannel(this.strCarChannel);
        carRecordExport.setCarResource(this.carResource);
        carRecordExport.setCarNumResource(this.carNumResource);
        carRecordExport.setCarConfig(this.carConfig);
        carRecordExport.setCarCreateTime(this.carCreateTime);
        carRecordExport.setCarPurchaseTime(this.carPurchaseTime);
        carRecordExport.setCarRunNum(this.carRunNum);
        carRecordExport.setCarOutColour(this.carOutColour);
        carRecordExport.setCarInsideColour(this.carInsideColour);
        carRecordExport.setCarNewSale(this.carNewSale);
        carRecordExport.setCarStatus(this.strCarStatus);
        carRecordExport.setCarStatusDesc(this.carStatusDesc);
        carRecordExport.setCarTakeType(this.strCarTakeType);
        carRecordExport.setPurchaseMoney(this.purchaseMoney);
        carRecordExport.setHallMoney(this.hallMoney);
        carRecordExport.setQuanAuthorityMoney(this.qAuthorityMoney);
        carRecordExport.setAnAuthorityMoney(this.aAuthorityMoney);
        carRecordExport.setCarAgencyFee(this.agencyFee);
        carRecordExport.setPurchaseType(this.strPurchaseType);
    }
}
