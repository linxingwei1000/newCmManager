package cm.lx.bean;

import cm.lx.bean.entity.*;

import java.util.List;

public class ContextBean {

    CarRecord carRecord;

    Double allCost;
    List<CarMoneyRecord> costList;

    Double preAllFee;
    List<CarSaleSetup> preList;

    Double otherAllFee;
    List<CarSaleSetup> otherList;

    Double allSf;
    List<CarMoneyRecord> sfList;

    Double saleAllFee;
    List<CarSaleSetup> saleList;

    Double afterAllFee;
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

    public Double getAllCost() {
        return allCost;
    }

    public void setAllCost(Double allCost) {
        this.allCost = allCost;
    }

    public List<CarMoneyRecord> getCostList() {
        return costList;
    }

    public void setCostList(List<CarMoneyRecord> costList) {
        this.costList = costList;
    }

    public Double getPreAllFee() {
        return preAllFee;
    }

    public void setPreAllFee(Double preAllFee) {
        this.preAllFee = preAllFee;
    }

    public List<CarSaleSetup> getPreList() {
        return preList;
    }

    public void setPreList(List<CarSaleSetup> preList) {
        this.preList = preList;
    }

    public Double getOtherAllFee() {
        return otherAllFee;
    }

    public void setOtherAllFee(Double otherAllFee) {
        this.otherAllFee = otherAllFee;
    }

    public List<CarSaleSetup> getOtherList() {
        return otherList;
    }

    public void setOtherList(List<CarSaleSetup> otherList) {
        this.otherList = otherList;
    }

    public Double getAllSf() {
        return allSf;
    }

    public void setAllSf(Double allSf) {
        this.allSf = allSf;
    }

    public List<CarMoneyRecord> getSfList() {
        return sfList;
    }

    public void setSfList(List<CarMoneyRecord> sfList) {
        this.sfList = sfList;
    }

    public Double getSaleAllFee() {
        return saleAllFee;
    }

    public void setSaleAllFee(Double saleAllFee) {
        this.saleAllFee = saleAllFee;
    }

    public List<CarSaleSetup> getSaleList() {
        return saleList;
    }

    public void setSaleList(List<CarSaleSetup> saleList) {
        this.saleList = saleList;
    }

    public Double getAfterAllFee() {
        return afterAllFee;
    }

    public void setAfterAllFee(Double afterAllFee) {
        this.afterAllFee = afterAllFee;
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
