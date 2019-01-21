package cm.lx.bean;

public class CarPayMoneyAssist {
    Integer id;
    Integer carSaleInfoId;
    Double oldPayMoney;
    Double newPayMoney;
    Double difference;
    String modReason;
    Long ctime;
    Long utime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCarSaleInfoId() {
        return carSaleInfoId;
    }

    public void setCarSaleInfoId(Integer carSaleInfoId) {
        this.carSaleInfoId = carSaleInfoId;
    }

    public Double getOldPayMoney() {
        return oldPayMoney;
    }

    public void setOldPayMoney(Double oldPayMoney) {
        this.oldPayMoney = oldPayMoney;
    }

    public Double getNewPayMoney() {
        return newPayMoney;
    }

    public void setNewPayMoney(Double newPayMoney) {
        this.newPayMoney = newPayMoney;
    }

    public Double getDifference() {
        return difference;
    }

    public void setDifference(Double difference) {
        this.difference = difference;
    }

    public String getModReason() {
        return modReason;
    }

    public void setModReason(String modReason) {
        this.modReason = modReason;
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
}
