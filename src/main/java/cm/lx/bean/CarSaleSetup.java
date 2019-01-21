package cm.lx.bean;

public class CarSaleSetup {

    Integer id;

    Integer carCostId;

    Integer setupType;

    String setupName;

    Double setupFee;

    Long ctime;

    Long utime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSetupType() {
        return setupType;
    }

    public void setSetupType(Integer setupType) {
        this.setupType = setupType;
    }

    public Integer getCarCostId() {
        return carCostId;
    }

    public void setCarCostId(Integer carCostId) {
        this.carCostId = carCostId;
    }

    public String getSetupName() {
        return setupName;
    }

    public void setSetupName(String setupName) {
        this.setupName = setupName;
    }

    public Double getSetupFee() {
        return setupFee;
    }

    public void setSetupFee(Double setupFee) {
        this.setupFee = setupFee;
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
