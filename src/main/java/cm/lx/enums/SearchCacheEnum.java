package cm.lx.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * @author linxingwei
 * @date 2019/3/4
 */
public enum SearchCacheEnum {

    /**
     * 车辆库存缓存
     */
    STOCK_SEARCH_CACHE(1, "stock_search_cache"),

    /**
     * 车辆已售缓存
     */
    SOLD_SEARCH_CACHE(2, "sold_search_cache"),

    /**
     * 现金流水缓存
     */
    CASH_SEARCH_CACHE(3, "sold_search_cache"),

    /**
     * 银行流水缓存
     */
    BANK_SEARCH_CACHE(4, "sold_search_cache"),

    /**
     * poss机流水缓存
     */
    POSS_SEARCH_CACHE(5, "sold_search_cache"),
    ;

    private Integer code;
    private String desc;

    SearchCacheEnum(Integer code, String desc) {
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

    public static SearchCacheEnum findEnum(Integer code) {
        if (code == null) return null;
        for (SearchCacheEnum value : values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return null;
    }

    public static List<String> returnDescs() {
        List<String> list = new ArrayList<>();
        for (SearchCacheEnum value : values()) {
            list.add(value.desc);
        }
        return list;
    }
}
