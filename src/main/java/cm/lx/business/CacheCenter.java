package cm.lx.business;

import cm.lx.bean.*;
import cm.lx.common.ContextType;
import cm.lx.bean.entity.*;
import cm.lx.enums.CarPropertyEnum;
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
    CarSaleSetupService carSaleSetupService;

    @Resource
    CarSaleInfoService carSaleInfoService;

    @Resource
    MortgageRecordService mortgageRecordService;

    @Resource
    CarPayMoneyAssistService carPayMoneyAssistService;

    @Resource
    WagesAssistService wagesAssistService;

    @Resource
    CarMoneyRecordService carMoneyRecordService;

    private ConcurrentHashMap<String, List<CarProperty>> carPropertyKeyMap;
    private ConcurrentHashMap<Integer, CarProperty> carPropertyMap;

    private ConcurrentHashMap<Integer, ContextBean> contextBeanMap;

    private ConcurrentHashMap<Integer, List<Integer>> carPropertyRelationCarRecordMap;

    private ConcurrentHashMap<String, List<Integer>> userSearchResult;
    private List<Insurance> insuranceList;
    private List<MortgageLog> mortgageLogList;
    private List<NewCar> newCarList;

    /**
     * 获取查询缓存
     */
    public List<Integer> getUserSearchResult(String key) {
        return userSearchResult.get(key);
    }

    /**
     * 缓存查询结果
     */
    public void setUserSearchResult(String key, List<Integer> list) {
        userSearchResult.put(key, list);
    }

    /**
     * 清除查询结果
     */
    public void delUserSearchResult(String key) {
        userSearchResult.remove(key);
    }

    /**
     * 获取保险查询缓存
     */
    public List<Insurance> getInsuranceList() {
        return insuranceList;
    }

    /**
     * 缓存保险查询结果
     *
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
     *
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
     *
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

        userSearchResult = new ConcurrentHashMap<>();
    }

    public List<CarProperty> getCarPropertyByKey(String key) {
        if (key != null) {
            return carPropertyKeyMap.get(key);
        } else {
            return new ArrayList<>(carPropertyMap.values());
        }
    }

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

    public void updateCarProperty(CarProperty carProperty) {
        if (carProperty.getId() != null) {
            carPropertyService.updateCarPropertyById(carProperty);

            //清配置相关carRecordInfo
            clearRelationCarRecord(carProperty.getId());
        } else {
            carPropertyService.create(carProperty);
        }
        initCarProperty();
    }

    public void deleteCarProperty(Integer id) {
        carPropertyService.deleteCarPropertyById(id);

        //清配置相关carRecordInfo
        clearRelationCarRecord(id);
        initCarProperty();
    }

    /**
     * 获取车辆组合
     */
    public List<ContextBean> getCarRecordCombinationInfo(List<CarRecord> list) {
        List<ContextBean> resultList = new ArrayList<>();
        if (list != null && list.size() != 0) {
            for (CarRecord carRecord : list) {
                resultList.add(returnContextBeanAndFixCache(carRecord));
            }
        }
        return resultList;
    }

    /**
     * 获取车辆组合
     */
    public List<ContextBean> getCarRecordCombinationInfoByIds(List<Integer> list) {
        List<ContextBean> resultList = new ArrayList<>();
        if (list != null && list.size() != 0) {
            for (Integer id : list) {
                CarRecord carRecord = carRecordService.getCarRecordById(id);
                resultList.add(returnContextBeanAndFixCache(carRecord));
            }
        }
        return resultList;
    }

    /**
     * 获取车辆信息
     */
    public List<CarRecord> getCarRecordInfo(List<CarRecord> list) {
        List<CarRecord> resultList = new ArrayList<>();
        if (list != null && list.size() != 0) {
            for (CarRecord carRecord : list) {
                resultList.add(returnContextBeanAndFixCache(carRecord).getCarRecord());
            }
        }
        return resultList;
    }

    private ContextBean returnContextBeanAndFixCache(CarRecord carRecord){
        ContextBean contextBean = contextBeanMap.get(carRecord.getId());
        //说明缓存被刷，重新刷
        if (contextBean == null) {
            contextBean = initCarRecordInfo(carRecord);
            contextBeanMap.put(carRecord.getId(), initCarRecordInfo(carRecord));
        }
        return contextBean;
    }

    /**
     * 删除车辆缓存
     */
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

        //初始化属性缓存
        for (String desc : CarPropertyEnum.returnDescs()) {
            List<CarProperty> tmp = carPropertyService.getCarPropertyListByKey(desc);
            carPropertyKeyMap.put(desc, tmp);
            for (CarProperty cp : tmp) {
                carPropertyMap.put(cp.getId(), cp);
            }
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
        contextBean.setCarRecord(carRecord);

        //成本录入细节
        TwoTuple<Double, List<CarMoneyRecord>> twoTuple = ToolUtil.getCarMoneyRecordTuple(carMoneyRecordService, this, carRecord.getId(), CarPropertyEnum.MONEY_RECORD_COST.getDesc());
        contextBean.setAllCost(twoTuple.left());
        contextBean.setCostList(twoTuple.right());

        //获取售前整备明细
        TwoTuple<Double, List<CarSaleSetup>> setupTuple = ToolUtil.getCarSaleSetupTuple(carSaleSetupService, carRecord.getId(), ContextType.PRE_SETUP_TYPE);
        contextBean.setPreAllFee(setupTuple.left());
        contextBean.setPreList(setupTuple.right());

        //车辆其他收入明细
        setupTuple = ToolUtil.getCarSaleSetupTuple(carSaleSetupService, carRecord.getId(), ContextType.OTHER_INCOME);
        contextBean.setOtherAllFee(setupTuple.left());
        contextBean.setOtherList(setupTuple.right());

        //获取售后整备明细
        setupTuple = ToolUtil.getCarSaleSetupTuple(carSaleSetupService, carRecord.getId(), ContextType.AFTER_SETUP_TYPE);
        contextBean.setAfterAllFee(setupTuple.left());
        contextBean.setAfterList(setupTuple.right());

        //获取销售信息
        CarSaleInfo carSaleInfo = null;
        if (carRecord.getIsSale() == 1) {
            carSaleInfo = carSaleInfoService.getCarSaleInfoById(carRecord.getSaleId());
            if (carSaleInfo.getSaleType().equals(ContextType.SALE_TYPE_AJ)) {
                carSaleInfo.setMortgageRecord(mortgageRecordService.getMortgageRecordById(carSaleInfo.getMortgageId()));
            }
            dealCarSaleInfo(carSaleInfo);
            contextBean.setCarSaleInfo(carSaleInfo);
            contextBean.setMoneyAssistList(carPayMoneyAssistService.getCarPayMoneyAssistListByLinkId(carSaleInfo.getId()));
        }

        //获取销售成本信息
        twoTuple = ToolUtil.getCarMoneyRecordTuple(carMoneyRecordService, this, carRecord.getId(), CarPropertyEnum.MONEY_RECORD_SALE.getDesc());
        Double allSf = twoTuple.left();
        if (carSaleInfo != null) {
            allSf += carSaleInfo.getServiceFee();
        }
        contextBean.setAllSf(twoTuple.left() + allSf);
        contextBean.setSfList(twoTuple.right());

        //车辆销售整备费用
        setupTuple = ToolUtil.getCarSaleSetupTuple(carSaleSetupService, carRecord.getId(), ContextType.SALE_TYPE);
        contextBean.setSaleAllFee(setupTuple.left());
        contextBean.setSaleList(setupTuple.right());

        //获取每辆车售后工资相关信息
        if (carRecord.getExpenseId() != null && carRecord.getExpenseId() != 0) {
            contextBean.setWagesAssist(wagesAssistService.getWagesAssistById(carRecord.getExpenseId()));
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
        if (carSaleInfo.getConsumerAge() != 0) {
            carSaleInfo.setStrConsumerAge(getCarPropertyById(carSaleInfo.getConsumerAge()).getPropertyValue());
        }
    }
}