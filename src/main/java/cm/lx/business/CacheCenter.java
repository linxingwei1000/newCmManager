package cm.lx.business;

import cm.lx.bean.*;
import cm.lx.common.ContextType;
import cm.lx.bean.entity.*;
import cm.lx.service.*;
import cm.lx.util.TimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class CacheCenter implements InitializingBean {

    Logger logger = LoggerFactory.getLogger(CacheCenter.class);

    @Resource
    CarRecordService carRecordService;

    @Resource
    CarPropertyService carPropertyService;

    @Resource
    CarPaidRecordService carPaidRecordService;

    @Resource
    CarRemarkService carRemarkService;

    @Resource
    CarCostService carCostService;

    @Resource
    CarSaleSetupService carSaleSetupService;

    @Resource
    CarSaleInfoService carSaleInfoService;

    @Resource
    MortgageRecordService mortgageRecordService;

    @Resource
    CarPayMoneyAssistService carPayMoneyAssistService;

    @Resource
    CarSfService carSfService;

    @Resource
    WagesAssistService wagesAssistService;

    private ConcurrentHashMap<String, List<CarProperty>> carPropertyKeyMap;
    private ConcurrentHashMap<Integer, CarProperty> carPropertyMap;

    private ConcurrentHashMap<Integer, ContextBean> contextBeanMap;

    private ConcurrentHashMap<Integer, List<Integer>> carPropertyRelationCarRecordMap;

    private List<ContextBean> contextBeanList;
    private List<Insurance> insuranceList;
    private List<MortgageLog> mortgageLogList;
    private List<NewCar> newCarList;

    /**
     * 获取车辆查询缓存
     */
    public List<ContextBean> getContextBeanList() {
        return contextBeanList;
    }

    /**
     * 缓存车辆查询结果
     * @param contextBeanList
     */
    public void setContextBeanList(List<ContextBean> contextBeanList) {
        this.contextBeanList = contextBeanList;
    }

    /**
     * 获取保险查询缓存
     */
    public List<Insurance> getInsuranceList() {
        return insuranceList;
    }

    /**
     * 缓存保险查询结果
     * @param insuranceList
     */
    public void setInsuranceList(List<Insurance> insuranceList) {
        this.insuranceList = insuranceList;
    }

    /**
     * 获取代办按揭缓存
     */
    public List<MortgageLog> getMortgageLogList() {
        return mortgageLogList;
    }

    /**
     * 缓存代办按揭查询结果
     * @param mortgageLogList
     */
    public void setMortgageLogList(List<MortgageLog> mortgageLogList) {
        this.mortgageLogList = mortgageLogList;
    }

    /**
     * 获取新车业务缓存
     */
    public List<NewCar> getNewCarList() {
        return newCarList;
    }

    /**
     * 缓存新车业务查询结果
     * @param newCarList
     */
    public void setNewCarList(List<NewCar> newCarList) {
        this.newCarList = newCarList;
    }

    @Override
    public void afterPropertiesSet() {
        carPropertyKeyMap = new ConcurrentHashMap<>();
        carPropertyMap = new ConcurrentHashMap<>();

        //初始化车辆属性
        long sTime = System.currentTimeMillis();
        initCarProperty();
        long duration = System.currentTimeMillis() - sTime;
        logger.info("车辆属性缓存加载完成，耗时：" + duration);

        contextBeanMap = new ConcurrentHashMap<>();
        carPropertyRelationCarRecordMap = new ConcurrentHashMap<>();

        //组合carRecord信息
        sTime = System.currentTimeMillis();
        List<CarRecord> list = carRecordService.getCarRecords();
        for (CarRecord carRecord : list) {
            contextBeanMap.put(carRecord.getId(), initCarRecordInfo(carRecord));
        }
        duration = System.currentTimeMillis() - sTime;
        logger.info("车辆信息缓存加载完成， 耗时：" + duration);
    }

    //获取车辆属性
    public List<CarProperty> getCarPropertyByKey(String key) {
        if (key != null) {
            return carPropertyKeyMap.get(key);
        } else {
            return new ArrayList<>(carPropertyMap.values());
        }
    }

    //获取车辆属性
    public CarProperty getCarPropertyById(Integer id) {
        return carPropertyMap.get(id);
    }

    public boolean isCarPropertyExist(String key, String value) {
        List<CarProperty> list = getCarPropertyByKey(key);
        if (list != null) {
            for (CarProperty cp : list) {
                if (cp.getPropertyValue().equals(value)) {
                    return true;
                }
            }
        }
        return false;
    }

    //更新车辆配置
    public void updateCarProperty(CarProperty carProperty) {
        Long curTime = System.currentTimeMillis();
        if (carProperty.getId() != null) {
            carPropertyService.updateCarPropertyById(carProperty);

            //清配置相关carRecordInfo
            clearRelationCarRecord(carProperty.getId());
        } else {
            carPropertyService.create(carProperty);
        }
        initCarProperty();
    }

    //删除车辆配置
    public void deleteCarProperty(Integer id) {
        carPropertyService.deleteCarPropertyById(id);

        //清配置相关carRecordInfo
        clearRelationCarRecord(id);
        initCarProperty();
    }

    //获取车辆组合信息
    public List<ContextBean> getCarRecordCombinationInfo(List<CarRecord> list) {
        List<ContextBean> resultList = new ArrayList<>();
        if (list != null && list.size() != 0) {
            for (CarRecord carRecord : list) {
                ContextBean contextBean = contextBeanMap.get(carRecord.getId());

                //说明缓存被刷，重新刷
                if (contextBean == null) {
                    contextBean = initCarRecordInfo(carRecord);
                    contextBeanMap.put(carRecord.getId(), initCarRecordInfo(carRecord));
                }
                resultList.add(contextBean);
            }
        }
        return resultList;
    }

    //获取车辆信息
    public List<CarRecord> getCarRecordInfo(List<CarRecord> list) {
        List<CarRecord> resultList = new ArrayList<>();
        if (list != null && list.size() != 0) {
            for (CarRecord carRecord : list) {
                ContextBean contextBean = contextBeanMap.get(carRecord.getId());

                //说明缓存被刷，重新刷
                if (contextBean == null) {
                    contextBean = initCarRecordInfo(carRecord);
                    contextBeanMap.put(carRecord.getId(), initCarRecordInfo(carRecord));
                }
                resultList.add(contextBean.getCarRecord());
            }
        }
        return resultList;
    }

    //有更新删除缓存
    public void deleteCarRecordInfo(Integer cid) {
        contextBeanMap.remove(cid);
    }


    private void clearRelationCarRecord(Integer key) {
        List<Integer> list = carPropertyRelationCarRecordMap.get(key);
        if (list != null && list.size() != 0) {
            for (Integer cid : list) {
                contextBeanMap.remove(cid);
            }
        }
    }

    private void initCarProperty() {
        carPropertyKeyMap.clear();
        carPropertyMap.clear();
        //初始化车系
        List<CarProperty> list = carPropertyService.getCarPropertyListByKey(ContextType.CAR_LINE);
        carPropertyKeyMap.put(ContextType.CAR_LINE, list);
        for (CarProperty cp : list) {
            carPropertyMap.put(cp.getId(), cp);
        }

        //初始化车级
        list = carPropertyService.getCarPropertyListByKey(ContextType.CAR_LEVEL);
        carPropertyKeyMap.put(ContextType.CAR_LEVEL, list);
        for (CarProperty cp : list) {
            carPropertyMap.put(cp.getId(), cp);
        }

        //初始化车来源渠道
        list = carPropertyService.getCarPropertyListByKey(ContextType.CAR_CHANNEL);
        carPropertyKeyMap.put(ContextType.CAR_CHANNEL, list);
        for (CarProperty cp : list) {
            carPropertyMap.put(cp.getId(), cp);
        }

        //初始化提车方式
        list = carPropertyService.getCarPropertyListByKey(ContextType.CAR_TAKE_TYPE);
        carPropertyKeyMap.put(ContextType.CAR_TAKE_TYPE, list);
        for (CarProperty cp : list) {
            carPropertyMap.put(cp.getId(), cp);
        }

        //初始化车况
        list = carPropertyService.getCarPropertyListByKey(ContextType.CAR_STATUS);
        carPropertyKeyMap.put(ContextType.CAR_STATUS, list);
        for (CarProperty cp : list) {
            carPropertyMap.put(cp.getId(), cp);
        }

        //初始化车辆买入方式
        list = carPropertyService.getCarPropertyListByKey(ContextType.CAR_PURCHASE_TYPE);
        carPropertyKeyMap.put(ContextType.CAR_PURCHASE_TYPE, list);
        for (CarProperty cp : list) {
            carPropertyMap.put(cp.getId(), cp);
        }

        //初始化客户属性
        list = carPropertyService.getCarPropertyListByKey(ContextType.CAR_CONSUMER_PROPERTY);
        carPropertyKeyMap.put(ContextType.CAR_CONSUMER_PROPERTY, list);
        for (CarProperty cp : list) {
            carPropertyMap.put(cp.getId(), cp);
        }

        //初始化获客渠道
        list = carPropertyService.getCarPropertyListByKey(ContextType.CAR_CONSUMER_RESOURCE);
        carPropertyKeyMap.put(ContextType.CAR_CONSUMER_RESOURCE, list);
        for (CarProperty cp : list) {
            carPropertyMap.put(cp.getId(), cp);
        }

        //初始化购车用途
        list = carPropertyService.getCarPropertyListByKey(ContextType.CAR_PURCHASE_USE);
        carPropertyKeyMap.put(ContextType.CAR_PURCHASE_USE, list);
        for (CarProperty cp : list) {
            carPropertyMap.put(cp.getId(), cp);
        }

        //初始化购车用途
        list = carPropertyService.getCarPropertyListByKey(ContextType.CONSUMER_GENERATION);
        carPropertyKeyMap.put(ContextType.CONSUMER_GENERATION, list);
        for (CarProperty cp : list) {
            carPropertyMap.put(cp.getId(), cp);
        }

        //初始化保险类型
        //初始化购车用途
        list = carPropertyService.getCarPropertyListByKey(ContextType.BUSINESS_TYPE);
        carPropertyKeyMap.put(ContextType.BUSINESS_TYPE, list);
        for (CarProperty cp : list) {
            carPropertyMap.put(cp.getId(), cp);
        }
    }


    private ContextBean initCarRecordInfo(CarRecord carRecord) {

        carRecord.setStrPurchaseDate(TimeUtils.transformTimetagToDate(carRecord.getPurchaseDate(), TimeUtils.FORMAT_ONE));

        carRecord.setStrCarLine(transform(carRecord.getCarLine(), carRecord.getId()));
        carRecord.setStrCarLevel(transform(carRecord.getCarLevel(), carRecord.getId()));
        carRecord.setStrCarStatus(transform(carRecord.getCarStatus(), carRecord.getId()));
        carRecord.setStrCarTakeType(transform(carRecord.getCarTakeType(), carRecord.getId()));
        carRecord.setStrCarChannel(transform(carRecord.getCarChannel(), carRecord.getId()));
        carRecord.setStrPurchaseType(transform(carRecord.getPurchaseType(), carRecord.getId()));

        if (carRecord.getSoldDate() != 0) {
            carRecord.setStrSoldDate(TimeUtils.transformTimetagToDate(carRecord.getSoldDate(), TimeUtils.FORMAT_ONE));
        }

        carRecord.setPurchasePaidList(carPaidRecordService.getCarPaidRecordByLinkIdAndType(carRecord.getId(), ContextType.PAY_RECORD_PURCHASE));
        carRecord.setPurchaseRemark(carRemarkService.getCarRemarkByLinkIdAndType(carRecord.getId(), ContextType.REMARK_TYPE_PURCHASE));
        carRecord.setStockRemark(carRemarkService.getCarRemarkByLinkIdAndType(carRecord.getId(), ContextType.REMARK_TYPE_STOCK));
        carRecord.setSaleRemark(carRemarkService.getCarRemarkByLinkIdAndType(carRecord.getId(), ContextType.REMARK_TYPE_SALE));

        ContextBean contextBean = new ContextBean();
        CarCost carCost = null;
        if (carRecord.getIsCost() == 1) {
            carCost = carCostService.getCarCostById(carRecord.getCostId());
            carCost.addAll();
        }
        if (carCost == null) {
            carCost = new CarCost();
            carCost.setPreSaleFee(0.0);
            carCost.setOtherIncomeFee(0.0);
            carCost.setAfterSaleFee(0.0);
            carRecord.setIsCost(0);
        }
        contextBean.setCarRecord(carRecord);
        contextBean.setCarCost(carCost);

        //获取售前整备明细
        if (carCost.getPreSaleFee() != 0) {
            contextBean.setPreList(carSaleSetupService.getCarSaleSetupByIdAndType(carRecord.getCostId(), ContextType.PRE_SETUP_TYPE));
        }

        //车辆其他收入明细
        if (carCost.getOtherIncomeFee() != 0) {
            contextBean.setOtherList(carSaleSetupService.getCarSaleSetupByIdAndType(carRecord.getCostId(), ContextType.OTHER_INCOME));
        }

        //获取售后整备明细
        if (carCost.getAfterSaleFee() != 0) {
            contextBean.setAfterList(carSaleSetupService.getCarSaleSetupByIdAndType(carRecord.getCostId(), ContextType.AFTER_SETUP_TYPE));
        }

        //获取销售信息
        if (carRecord.getIsSale() == 1) {
            CarSaleInfo carSaleInfo = carSaleInfoService.getCarSaleInfoById(carRecord.getSaleId());
            if (carSaleInfo.getSaleType().equals(ContextType.SALE_TYPE_AJ)) {
                carSaleInfo.setMortgageRecord(mortgageRecordService.getMortgageRecordById(carSaleInfo.getMortgageId()));
            }
            dealCarSaleInfo(carSaleInfo);
            contextBean.setCarSaleInfo(carSaleInfo);

            contextBean.setMoneyAssistList(carPayMoneyAssistService.getCarPayMoneyAssistListByLinkId(carSaleInfo.getId()));
            //设置页面显示类型
            //mav.addObject("saleType", carSaleInfo.getSaleType());
        }

        //获取销售成本信息
        if (carRecord.getIsSf() == 1) {
            CarSf carSf = carSfService.getCarSfById(carRecord.getSfId());
            carSf.addAll();
            contextBean.setCarSf(carSf);

            if (carSf.getSaleFee() != 0) {
                contextBean.setSaleList(carSaleSetupService.getCarSaleSetupByIdAndType(carRecord.getSfId(), ContextType.SALE_TYPE));
            }

        }
        //获取每辆车售后工资相关信息
        if (carRecord.getExpenseId() != null && carRecord.getExpenseId() != 0) {
            contextBean.setWagesAssist(wagesAssistService.getWagesAssistByCid(carRecord.getExpenseId()));
        }

        //插入各种付款记录
        contextBean.setSalePaidList(carPaidRecordService.getCarPaidRecordByLinkIdAndType(carRecord.getId(), ContextType.PAY_RECORD_SALE));
        contextBean.setMortgagePaidList(carPaidRecordService.getCarPaidRecordByLinkIdAndType(carRecord.getId(), ContextType.PAY_RECORD_MORTGAGE));
        contextBean.setBackList(carPaidRecordService.getCarPaidRecordByLinkIdAndType(carRecord.getId(), ContextType.PAY_RECORD_BACK));

        return contextBean;

    }

    private String transform(Integer key, Integer cid) {
        String result = "";
        if (key != 0) {
            CarProperty carProperty = getCarPropertyById(key);
            if (carProperty != null) {
                result = carProperty.getPropertyValue();
                List<Integer> list = carPropertyRelationCarRecordMap.get(key);
                if (list == null) {
                    list = new ArrayList<>();
                }
                list.add(cid);
                carPropertyRelationCarRecordMap.put(key, list);
            }
        }
        return result;
    }

    private void dealCarSaleInfo(CarSaleInfo carSaleInfo) {
        carSaleInfo.setStrSaleDate(TimeUtils.transformTimetagToDate(carSaleInfo.getSaleDate(), TimeUtils.FORMAT_ONE));
        carSaleInfo.setStrConsumerProperty(getCarPropertyById(carSaleInfo.getConsumerProperty()).getPropertyValue());
        carSaleInfo.setStrConsumerResource(getCarPropertyById(carSaleInfo.getConsumerResource()).getPropertyValue());
        carSaleInfo.setStrPurchaseUse(getCarPropertyById(carSaleInfo.getPurchaseUse()).getPropertyValue());
        if (carSaleInfo.getConsumerAge() != 0) {
            carSaleInfo.setStrConsumerAge(getCarPropertyById(carSaleInfo.getConsumerAge()).getPropertyValue());
        }
    }
}