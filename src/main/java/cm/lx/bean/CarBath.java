package cm.lx.bean;

import java.util.List;

public class CarBath {
    Integer id;

    String bathName;

    String bathDesc;

    String accountNum;

    String carRecordId;

    Long ctime;

    Long utime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBathName() {
        return bathName;
    }

    public void setBathName(String bathName) {
        this.bathName = bathName;
    }

    public String getBathDesc() {
        return bathDesc;
    }

    public void setBathDesc(String bathDesc) {
        this.bathDesc = bathDesc;
    }

    public String getAccountNum() {
        return accountNum;
    }

    public void setAccountNum(String accountNum) {
        this.accountNum = accountNum;
    }

    public String getCarRecordId() {
        return carRecordId;
    }

    public void setCarRecordId(String carRecordId) {
        this.carRecordId = carRecordId;
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

    public static void installHeaders(List<String> headers) {
        headers.add("批量采购名称");
        headers.add("描述");
    }

    public void install(CarRecordExport carRecordExport) {
        carRecordExport.setBathName(bathName);
        carRecordExport.setBathDesc(bathDesc);
    }
}
