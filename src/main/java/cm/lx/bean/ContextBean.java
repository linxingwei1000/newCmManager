package cm.lx.bean;

import java.util.List;

public class ContextBean {

    CarRecord carRecord;

    CarCost carCost;

    List<CarSaleSetup> preList;

    List<CarSaleSetup> otherList;

    CarSf carSf;

    List<CarSaleSetup> saleList;

    List<CarSaleSetup> afterList;

    CarSaleInfo carSaleInfo;

    List<CarPaidRecord> salePaidList;

    List<CarPaidRecord> backList;

    List<CarPaidRecord> mortgagePaidList;

    List<CarPayMoneyAssist> moneyAssistList;

    WagesAssist wagesAssist;

    public CarRecord getCarRecord() {
        return carRecord;
    }

    public void setCarRecord(CarRecord carRecord) {
        this.carRecord = carRecord;
    }

    public CarCost getCarCost() {
        return carCost;
    }

    public void setCarCost(CarCost carCost) {
        this.carCost = carCost;
    }

    public List<CarSaleSetup> getPreList() {
        return preList;
    }

    public void setPreList(List<CarSaleSetup> preList) {
        this.preList = preList;
    }

    public List<CarSaleSetup> getOtherList() {
        return otherList;
    }

    public void setOtherList(List<CarSaleSetup> otherList) {
        this.otherList = otherList;
    }

    public CarSf getCarSf() {
        return carSf;
    }

    public void setCarSf(CarSf carSf) {
        this.carSf = carSf;
    }

    public List<CarSaleSetup> getSaleList() {
        return saleList;
    }

    public void setSaleList(List<CarSaleSetup> saleList) {
        this.saleList = saleList;
    }

    public List<CarSaleSetup> getAfterList() {
        return afterList;
    }

    public void setAfterList(List<CarSaleSetup> afterList) {
        this.afterList = afterList;
    }

    public CarSaleInfo getCarSaleInfo() {
        return carSaleInfo;
    }

    public void setCarSaleInfo(CarSaleInfo carSaleInfo) {
        this.carSaleInfo = carSaleInfo;
    }

    public List<CarPaidRecord> getSalePaidList() {
        return salePaidList;
    }

    public List<CarPaidRecord> getBackList() {
        return backList;
    }

    public void setBackList(List<CarPaidRecord> backList) {
        this.backList = backList;
    }

    public List<CarPaidRecord> getMortgagePaidList() {
        return mortgagePaidList;
    }

    public void setMortgagePaidList(List<CarPaidRecord> mortgagePaidList) {
        this.mortgagePaidList = mortgagePaidList;
    }

    public void setSalePaidList(List<CarPaidRecord> salePaidList) {
        this.salePaidList = salePaidList;
    }

    public List<CarPayMoneyAssist> getMoneyAssistList() {
        return moneyAssistList;
    }

    public void setMoneyAssistList(List<CarPayMoneyAssist> moneyAssistList) {
        this.moneyAssistList = moneyAssistList;
    }

    public WagesAssist getWagesAssist() {
        return wagesAssist;
    }

    public void setWagesAssist(WagesAssist wagesAssist) {
        this.wagesAssist = wagesAssist;
    }
}
