package cm.lx.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * @author linxingwei
 * @date 2019/3/1
 */
public enum CarPropertyEnum {


    /**
     * 车辆属性缺省值
     */
    CAR_PROPERTY_DEFAULT(0, "未填选"),

    /**
     * 车系
     */
    CAR_LINE(1, "CAR_LINE"),

    /**
     * 车级
     */
    CAR_LEVEL(2, "CAR_LEVEL"),

    /**
     * 来源渠道
     */
    CAR_CHANNEL(3, "CAR_CHANNEL"),

    /**
     * 提车方式
     */
    CAR_TAKE_TYPE(4, "CAR_TAKE_TYPE"),

    /**
     * 车况
     */
    CAR_STATUS(5, "CAR_STATUS"),

    /**
     * 车辆买入方式
     */
    CAR_PURCHASE_TYPE(6, "CAR_PURCHASE_TYPE"),

    /**
     * 客户属性
     */
    CAR_CONSUMER_PROPERTY(7, "CAR_CONSUMER_PROPERTY"),

    /**
     * 获客渠道
     */
    CAR_CONSUMER_RESOURCE(8, "CAR_CONSUMER_RESOURCE"),

    /**
     * 购车用途
     */
    CAR_PURCHASE_USE(9, "CAR_PURCHASE_USE"),

    /**
     * 客户年龄段
     */
    CONSUMER_GENERATION(10, "CONSUMER_GENERATION"),

    /**
     * 保险业务类型
     */
    BUSINESS_TYPE(11, "BUSINESS_TYPE"),

    /**
     * 成本录入
     */
    MONEY_RECORD_COST(12, "MONEY_RECORD_COST"),

    /**
     * 销售录入
     */
    MONEY_RECORD_SALE(13, "MONEY_RECORD_SALE"),
    ;

    private Integer code;
    private String desc;

    CarPropertyEnum(Integer code, String desc){
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static CarPropertyEnum findEnum(Integer code){
        if(code == null) return null;
        for(CarPropertyEnum value:values()){
            if(value.getCode().equals(code)){
                return value;
            }
        }
        return null;
    }

    public static List<String> returnDescs(){
        List<String> list = new ArrayList<>();
        for(CarPropertyEnum value:values()){
            list.add(value.desc);
        }
        return list;
    }
}
