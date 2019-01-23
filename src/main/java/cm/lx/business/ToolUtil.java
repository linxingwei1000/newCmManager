package cm.lx.business;

import cm.lx.common.ContextType;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author linxingwei
 * @date 2019/1/21
 */
public class ToolUtil {

    /**
     * 页面加载车辆配置信息
     * @param mav
     * @param cacheCenter
     */
    public static void initCarPropertyData(ModelAndView mav, CacheCenter cacheCenter){
        mav.addObject(ContextType.CAR_LINE, cacheCenter.getCarPropertyByKey(ContextType.CAR_LINE));
        mav.addObject(ContextType.CAR_LEVEL, cacheCenter.getCarPropertyByKey(ContextType.CAR_LEVEL));
        mav.addObject(ContextType.CAR_CHANNEL, cacheCenter.getCarPropertyByKey(ContextType.CAR_CHANNEL));
        mav.addObject(ContextType.CAR_TAKE_TYPE, cacheCenter.getCarPropertyByKey(ContextType.CAR_TAKE_TYPE));
        mav.addObject(ContextType.CAR_STATUS, cacheCenter.getCarPropertyByKey(ContextType.CAR_STATUS));
        mav.addObject(ContextType.CAR_PURCHASE_TYPE, cacheCenter.getCarPropertyByKey(ContextType.CAR_PURCHASE_TYPE));
        mav.addObject(ContextType.CAR_CONSUMER_PROPERTY, cacheCenter.getCarPropertyByKey(ContextType.CAR_CONSUMER_PROPERTY));
        mav.addObject(ContextType.CAR_CONSUMER_RESOURCE, cacheCenter.getCarPropertyByKey(ContextType.CAR_CONSUMER_RESOURCE));
        mav.addObject(ContextType.CAR_PURCHASE_USE, cacheCenter.getCarPropertyByKey(ContextType.CAR_PURCHASE_USE));
        mav.addObject(ContextType.CONSUMER_GENERATION, cacheCenter.getCarPropertyByKey(ContextType.CONSUMER_GENERATION));
    }

}
