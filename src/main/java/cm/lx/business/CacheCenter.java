package cm.lx.business;

import cm.lx.bean.CarProperty;
import cm.lx.common.ContextType;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class CacheCenter implements InitializingBean {

    @Resource
    DaoCenter daoCenter;

    private ConcurrentHashMap<String, List<CarProperty>> carPropertyKeyMap;
    private ConcurrentHashMap<Integer, CarProperty> carPropertyMap;

    @Override
    public void afterPropertiesSet() throws Exception {
        carPropertyKeyMap = new ConcurrentHashMap<>();
        carPropertyMap = new ConcurrentHashMap<>();

        //初始化车辆属性
        initCarProperty();

        //组合carRecord信息
        initCarRecordInfo();
    }

    //获取车辆属性
    public List<CarProperty> getCarPropertyByKey(String key) {
        if (key != null) {
            return carPropertyKeyMap.get(key);
        } else {
            return new ArrayList<CarProperty>(carPropertyMap.values());
        }
    }

    //根据属性获取车辆属性Id
    public Integer getCarPropertyId(String key, String value) {
        List<CarProperty> list = getCarPropertyByKey(key);
        for (CarProperty c : list) {
            if (c.getPropertyValue().equals(value)) {
                return c.getId();
            }
        }
        return 0;
    }


    //获取车辆属性
    public CarProperty getCarPropertyById(Integer id) {
        return carPropertyMap.get(id);
    }

    public boolean isCarPropertyExist(String key, String value) {
        List<CarProperty> list = getCarPropertyByKey(key);
        if (list == null) return false;
        for (CarProperty cp : list) {
            if (cp.getPropertyValue().equals(value)) return true;
        }
        return false;
    }

    //更新车辆配置
    public void updateCarProperty(CarProperty carProperty) {
        Long curTime = System.currentTimeMillis();
        if (carProperty.getId() != null) {
            carProperty.setUtime(curTime);
            daoCenter.updateCarPropertyById(carProperty);
        } else {
            carProperty.setCtime(curTime);
            carProperty.setUtime(curTime);
            daoCenter.createCarProperty(carProperty);
        }
        initCarProperty();
    }

    //删除车辆配置
    public void deleteCarProperty(Integer id) {
        daoCenter.deleteCarPropertyById(id);
        initCarProperty();
    }

    private void initCarProperty() {
        carPropertyKeyMap.clear();
        carPropertyMap.clear();
        //初始化车系
        List<CarProperty> list = daoCenter.getCarPropertyListByKey(ContextType.CAR_LINE);
        carPropertyKeyMap.put(ContextType.CAR_LINE, list);
        for (CarProperty cp : list) {
            carPropertyMap.put(cp.getId(), cp);
        }

        //初始化车级
        list = daoCenter.getCarPropertyListByKey(ContextType.CAR_LEVEL);
        carPropertyKeyMap.put(ContextType.CAR_LEVEL, list);
        for (CarProperty cp : list) {
            carPropertyMap.put(cp.getId(), cp);
        }

        //初始化车来源渠道
        list = daoCenter.getCarPropertyListByKey(ContextType.CAR_CHANNEL);
        carPropertyKeyMap.put(ContextType.CAR_CHANNEL, list);
        for (CarProperty cp : list) {
            carPropertyMap.put(cp.getId(), cp);
        }

        //初始化提车方式
        list = daoCenter.getCarPropertyListByKey(ContextType.CAR_TAKE_TYPE);
        carPropertyKeyMap.put(ContextType.CAR_TAKE_TYPE, list);
        for (CarProperty cp : list) {
            carPropertyMap.put(cp.getId(), cp);
        }

        //初始化车况
        list = daoCenter.getCarPropertyListByKey(ContextType.CAR_STATUS);
        carPropertyKeyMap.put(ContextType.CAR_STATUS, list);
        for (CarProperty cp : list) {
            carPropertyMap.put(cp.getId(), cp);
        }

        //初始化车辆买入方式
        list = daoCenter.getCarPropertyListByKey(ContextType.CAR_PURCHASE_TYPE);
        carPropertyKeyMap.put(ContextType.CAR_PURCHASE_TYPE, list);
        for (CarProperty cp : list) {
            carPropertyMap.put(cp.getId(), cp);
        }

        //初始化客户属性
        list = daoCenter.getCarPropertyListByKey(ContextType.CAR_CONSUMER_PROPERTY);
        carPropertyKeyMap.put(ContextType.CAR_CONSUMER_PROPERTY, list);
        for (CarProperty cp : list) {
            carPropertyMap.put(cp.getId(), cp);
        }

        //初始化获客渠道
        list = daoCenter.getCarPropertyListByKey(ContextType.CAR_CONSUMER_RESOURCE);
        carPropertyKeyMap.put(ContextType.CAR_CONSUMER_RESOURCE, list);
        for (CarProperty cp : list) {
            carPropertyMap.put(cp.getId(), cp);
        }

        //初始化购车用途
        list = daoCenter.getCarPropertyListByKey(ContextType.CAR_PURCHASE_USE);
        carPropertyKeyMap.put(ContextType.CAR_PURCHASE_USE, list);
        for (CarProperty cp : list) {
            carPropertyMap.put(cp.getId(), cp);
        }

        //初始化购车用途
        list = daoCenter.getCarPropertyListByKey(ContextType.CONSUMER_GENERATION);
        carPropertyKeyMap.put(ContextType.CONSUMER_GENERATION, list);
        for (CarProperty cp : list) {
            carPropertyMap.put(cp.getId(), cp);
        }

        //初始化保险类型
        //初始化购车用途
        list = daoCenter.getCarPropertyListByKey(ContextType.BUSINESS_TYPE);
        carPropertyKeyMap.put(ContextType.BUSINESS_TYPE, list);
        for (CarProperty cp : list) {
            carPropertyMap.put(cp.getId(), cp);
        }
    }


    private void initCarRecordInfo(){

    }
}