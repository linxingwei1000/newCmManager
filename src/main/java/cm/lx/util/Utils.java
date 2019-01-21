package cm.lx.util;

import java.text.DecimalFormat;

public class Utils {

    public static String removeKey(String str, String key){
        if(str.contains(key)){
            int index = str.indexOf(key);
            String strBegin = str.substring(0, index);
            String strEnd = str.substring(index + key.length());
            if(strEnd.length()!=0){
                strEnd = strEnd.substring(1);
            }
            str = strBegin + strEnd;
            if(str.endsWith(",")) str = str.substring(0, str.length() -1);
        }
        return str;
    }

    public static Double saveTwoSeat(Double a){
        DecimalFormat df = new DecimalFormat("0.00");
        return Double.valueOf(df.format(a));
    }
}
