package cm.lx.bean.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * Created by linxingwei on 2018/2/27.
 */
@TableName("cm_service_fund")
public class ServiceFund {

    @TableId(type = IdType.AUTO)
    Integer id;

    @TableField(value = "use_date")
    Long useDate;

    String person;

    String project;

    Double fee;

    String remark;

    Long ctime;

    Long utime;

    @TableField(exist = false)
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
