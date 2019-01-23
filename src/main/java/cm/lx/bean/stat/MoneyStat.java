package cm.lx.bean.stat;

public class MoneyStat {
    String carModel;                //车型
    String frameNum;                //车架号
    Double grossProfit;             //车均毛利润
    Double ajGrossProfit;           //按揭车均毛利润
    Double qkGrossProfit;           //全款车均毛利润
    Double vehiclePremium;          //车均溢价
    Double ajVehiclePremium;          //按揭车均溢价
    Double qkVehiclePremium;          //全款车均溢价
    String saleAgent;                  //销售中介率
    Double preSetup;                //售前整备费
    Double saleSetup;               //销售整备费
    Double afterSetup;              //售后整备费
    Double purchaseCost;            //采购成本
    Double saleCost;                //销售成本

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getFrameNum() {
        return frameNum;
    }

    public void setFrameNum(String frameNum) {
        this.frameNum = frameNum;
    }

    public Double getGrossProfit() {
        return grossProfit;
    }

    public void setGrossProfit(Double grossProfit) {
        this.grossProfit = grossProfit;
    }

    public Double getAjGrossProfit() {
        return ajGrossProfit;
    }

    public void setAjGrossProfit(Double ajGrossProfit) {
        this.ajGrossProfit = ajGrossProfit;
    }

    public Double getQkGrossProfit() {
        return qkGrossProfit;
    }

    public void setQkGrossProfit(Double qkGrossProfit) {
        this.qkGrossProfit = qkGrossProfit;
    }

    public Double getVehiclePremium() {
        return vehiclePremium;
    }

    public void setVehiclePremium(Double vehiclePremium) {
        this.vehiclePremium = vehiclePremium;
    }

    public Double getAjVehiclePremium() {
        return ajVehiclePremium;
    }

    public void setAjVehiclePremium(Double ajVehiclePremium) {
        this.ajVehiclePremium = ajVehiclePremium;
    }

    public Double getQkVehiclePremium() {
        return qkVehiclePremium;
    }

    public void setQkVehiclePremium(Double qkVehiclePremium) {
        this.qkVehiclePremium = qkVehiclePremium;
    }

    public String getSaleAgent() {
        return saleAgent;
    }

    public void setSaleAgent(String saleAgent) {
        this.saleAgent = saleAgent;
    }

    public Double getPreSetup() {
        return preSetup;
    }

    public void setPreSetup(Double preSetup) {
        this.preSetup = preSetup;
    }

    public Double getSaleSetup() {
        return saleSetup;
    }

    public void setSaleSetup(Double saleSetup) {
        this.saleSetup = saleSetup;
    }

    public Double getAfterSetup() {
        return afterSetup;
    }

    public void setAfterSetup(Double afterSetup) {
        this.afterSetup = afterSetup;
    }

    public Double getPurchaseCost() {
        return purchaseCost;
    }

    public void setPurchaseCost(Double purchaseCost) {
        this.purchaseCost = purchaseCost;
    }

    public Double getSaleCost() {
        return saleCost;
    }

    public void setSaleCost(Double saleCost) {
        this.saleCost = saleCost;
    }
}
