package cm.lx.bean;

import java.util.List;

/**
 * Created by linxingwei on 2018/2/8.
 */
public class CarSf {

    Integer id;

    Integer carRecordId;

    Double transferFee;

    Double transferSubsidy;

    Double transferCrossingFee;

    Double transferBillingFee;

    Double transferOilFee;

    Double rubbing;

    Double removeCard;

    Double cattleFee;

    Double saleFee;

    Integer isProduce;

    Double serviceFee;

    Long ctime;

    Long utime;

    Double allSf;

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

    public Double getTransferFee() {
        return transferFee;
    }

    public void setTransferFee(Double transferFee) {
        this.transferFee = transferFee;
    }

    public Double getTransferSubsidy() {
        return transferSubsidy;
    }

    public void setTransferSubsidy(Double transferSubsidy) {
        this.transferSubsidy = transferSubsidy;
    }

    public Double getTransferCrossingFee() {
        return transferCrossingFee;
    }

    public void setTransferCrossingFee(Double transferCrossingFee) {
        this.transferCrossingFee = transferCrossingFee;
    }

    public Double getTransferBillingFee() {
        return transferBillingFee;
    }

    public void setTransferBillingFee(Double transferBillingFee) {
        this.transferBillingFee = transferBillingFee;
    }

    public Double getTransferOilFee() {
        return transferOilFee;
    }

    public void setTransferOilFee(Double transferOilFee) {
        this.transferOilFee = transferOilFee;
    }

    public Double getRubbing() {
        return rubbing;
    }

    public void setRubbing(Double rubbing) {
        this.rubbing = rubbing;
    }

    public Double getRemoveCard() {
        return removeCard;
    }

    public void setRemoveCard(Double removeCard) {
        this.removeCard = removeCard;
    }

    public Double getCattleFee() {
        return cattleFee;
    }

    public void setCattleFee(Double cattleFee) {
        this.cattleFee = cattleFee;
    }

    public Double getSaleFee() {
        return saleFee;
    }

    public void setSaleFee(Double saleFee) {
        this.saleFee = saleFee;
    }

    public Integer getIsProduce() {
        return isProduce;
    }

    public void setIsProduce(Integer isProduce) {
        this.isProduce = isProduce;
    }

    public Double getServiceFee() {
        return serviceFee;
    }

    public void setServiceFee(Double serviceFee) {
        this.serviceFee = serviceFee;
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

    public Double getAllSf() {
        return allSf;
    }

    public void setAllSf(Double allSf) {
        this.allSf = allSf;
    }

    public void addAll() {
        allSf = transferFee + transferSubsidy + transferCrossingFee + transferBillingFee + transferOilFee + rubbing + removeCard + cattleFee +
                serviceFee;
    }

    public static void installHeaders(List<String> headers){
        headers.add("过户费");
        headers.add("过户补贴");
        headers.add("过户过路费");
        headers.add("过户开票费");
        headers.add("过户加油费");
        headers.add("拓印");
        headers.add("拆牌");
        headers.add("过户黄牛费");
        headers.add("车辆销售整备费用");
        headers.add("售后服务基金");
    }

    public void install(CarRecordExport carRecordExport){
        carRecordExport.setTransferFee(this.transferFee);
        carRecordExport.setTransferSubsidy(this.transferSubsidy);
        carRecordExport.setTransferCrossingFee(this.transferCrossingFee);
        carRecordExport.setTransferBillingFee(this.transferBillingFee);
        carRecordExport.setTransferOilFee(this.transferOilFee);
        carRecordExport.setRubbing(this.rubbing);
        carRecordExport.setRemoveCard(this.removeCard);
        carRecordExport.setCattleFee(this.cattleFee);
        carRecordExport.setSaleFee(this.saleFee);
        carRecordExport.setServiceFee(this.serviceFee);
    }

}
