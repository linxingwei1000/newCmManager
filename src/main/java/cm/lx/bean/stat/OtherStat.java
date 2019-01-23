package cm.lx.bean.stat;

import java.util.Map;

public class OtherStat {
    Map<String, String> mortgageMap;
    Map<String, String> qzxMap;
    Map<String, String> syxMap;
    Map<String, String> renewalMap;

    public Map<String, String> getMortgageMap() {
        return mortgageMap;
    }

    public void setMortgageMap(Map<String, String> mortgageMap) {
        this.mortgageMap = mortgageMap;
    }

    public Map<String, String> getQzxMap() {
        return qzxMap;
    }

    public void setQzxMap(Map<String, String> qzxMap) {
        this.qzxMap = qzxMap;
    }

    public Map<String, String> getSyxMap() {
        return syxMap;
    }

    public void setSyxMap(Map<String, String> syxMap) {
        this.syxMap = syxMap;
    }

    public Map<String, String> getRenewalMap() {
        return renewalMap;
    }

    public void setRenewalMap(Map<String, String> renewalMap) {
        this.renewalMap = renewalMap;
    }
}
