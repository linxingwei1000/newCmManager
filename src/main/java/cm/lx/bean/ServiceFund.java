package cm.lx.bean;

/**
 * Created by linxingwei on 2018/2/27.
 */
public class ServiceFund {

    Integer id;

    Long useDate;

    String person;

    String project;

    Double fee;

    String remark;

    Long ctime;

    Long utime;

    String strUseDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getUseDate() {
        return useDate;
    }

    public void setUseDate(Long useDate) {
        this.useDate = useDate;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public String getStrUseDate() {
        return strUseDate;
    }

    public void setStrUseDate(String strUseDate) {
        this.strUseDate = strUseDate;
    }
}
