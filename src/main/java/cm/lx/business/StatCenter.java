package cm.lx.business;

import cm.lx.bean.stat.MoneyFlowStat;
import cm.lx.bean.stat.MoneyStat;
import cm.lx.bean.stat.OtherStat;
import cm.lx.bean.stat.PropertyStat;
import cm.lx.common.ContextType;
import cm.lx.bean.entity.*;
import cm.lx.enums.CarPropertyEnum;
import cm.lx.service.*;
import cm.lx.util.TimeUtils;
import cm.lx.util.Utils;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class StatCenter {

    public static MoneyStat statMoneyData(CarSaleSetupService carSaleSetupService,
                                          CarMoneyRecordService carMoneyRecordService,
                                          CarSaleInfoService carSaleInfoService,
                                          List<CarRecord> list, List<MoneyStat> moneyStatList) {
        int allCarNum = list.size();
        //车均毛利润
        double grossProfit = 0;
        //车均溢价
        double vehiclePremium = 0;
        //按揭车数量
        int ajCarNum = 0;
        //按揭车均毛利润
        double ajGrossProfit = 0;
        //按揭车均溢价
        double ajVehiclePremium = 0;
        //全款车数量
        int qkCarNum = 0;
        //全款车均毛利润
        double qkGrossProfit = 0;
        //全款车均溢价
        double qkVehiclePremium = 0;
        //销售中介车辆总数
        double saleAgent = 0;
        //售前整备费
        double preSetup = 0;
        //销售整备费
        double saleSetup = 0;
        //售后整备费
        double afterSetup = 0;
        //采购成本
        double purchaseCost = 0;
        //销售成本
        double saleCost = 0;

        for (CarRecord carRecord : list) {
            double tempGrossProfit = Double.valueOf(carRecord.getGrossProfit());
            CarSaleInfo carSaleInfo = carSaleInfoService.getCarSaleInfoById(carRecord.getSaleId());

            MoneyStat moneyStat = new MoneyStat();
            moneyStat.setCarModel(carRecord.getCarModel());
            moneyStat.setFrameNum(carRecord.getFrameNum());
            moneyStat.setGrossProfit(tempGrossProfit);
            moneyStat.setVehiclePremium(carRecord.getVehiclePremium());
            if (carSaleInfo.getSaleType().equals(ContextType.SALE_TYPE_AJ)) {
                ajCarNum++;
                ajGrossProfit += tempGrossProfit;
                moneyStat.setAjGrossProfit(tempGrossProfit);
                moneyStat.setQkGrossProfit(0.0);

                ajVehiclePremium += carRecord.getVehiclePremium();
                moneyStat.setAjVehiclePremium(carRecord.getVehiclePremium());
                moneyStat.setQkVehiclePremium(0.0);
            } else {
                qkCarNum++;
                qkGrossProfit += tempGrossProfit;
                moneyStat.setAjGrossProfit(0.0);
                moneyStat.setQkGrossProfit(tempGrossProfit);

                qkVehiclePremium += carRecord.getVehiclePremium();
                moneyStat.setAjVehiclePremium(0.0);
                moneyStat.setQkVehiclePremium(carRecord.getVehiclePremium());
            }
            grossProfit += tempGrossProfit;
            vehiclePremium += carRecord.getVehiclePremium();

            //中介率
            moneyStat.setSaleAgent("否");
            if (carSaleInfo.getAgencyFee() != 0) {
                saleAgent++;
                moneyStat.setSaleAgent("是");
            }

            moneyStat.setPreSetup(ToolUtil.getCarSaleSetupTuple(carSaleSetupService, carRecord.getId(), ContextType.PRE_SETUP_TYPE).left());
            moneyStat.setAfterSetup(ToolUtil.getCarSaleSetupTuple(carSaleSetupService, carRecord.getId(), ContextType.AFTER_SETUP_TYPE).left());
            preSetup += moneyStat.getPreSetup();
            afterSetup += moneyStat.getAfterSetup();

            moneyStat.setPurchaseCost(ToolUtil.getCarMoneyRecordTuple(carMoneyRecordService, null, carRecord.getId(),CarPropertyEnum.MONEY_RECORD_COST.getDesc()).left());
            purchaseCost += moneyStat.getPurchaseCost();

            moneyStat.setSaleSetup(ToolUtil.getCarSaleSetupTuple(carSaleSetupService, carRecord.getId(), ContextType.SALE_TYPE).left());
            saleSetup += moneyStat.getSaleSetup();

            Double allSf = ToolUtil.getCarMoneyRecordTuple(carMoneyRecordService, null, carRecord.getId(),CarPropertyEnum.MONEY_RECORD_COST.getDesc()).left();
            moneyStat.setSaleCost(allSf + carSaleInfo.getServiceFee());
            saleCost += moneyStat.getSaleCost();

            moneyStatList.add(moneyStat);
        }
        //统计数据
        MoneyStat moneyStat = new MoneyStat();
        moneyStat.setCarModel("平均");
        moneyStat.setFrameNum("-");
        moneyStat.setGrossProfit(allCarNum == 0 ? 0 : Utils.saveTwoSeat(grossProfit / allCarNum));
        moneyStat.setAjGrossProfit(ajCarNum == 0 ? 0 : Utils.saveTwoSeat(ajGrossProfit / ajCarNum));
        moneyStat.setQkGrossProfit(qkCarNum == 0 ? 0 : Utils.saveTwoSeat(qkGrossProfit / qkCarNum));
        moneyStat.setVehiclePremium(allCarNum == 0 ? 0 : Utils.saveTwoSeat(vehiclePremium / allCarNum));
        moneyStat.setAjVehiclePremium(ajCarNum == 0 ? 0 : Utils.saveTwoSeat(ajVehiclePremium / ajCarNum));
        moneyStat.setQkVehiclePremium(qkCarNum == 0 ? 0 : Utils.saveTwoSeat(qkVehiclePremium / qkCarNum));
        moneyStat.setSaleAgent(String.valueOf(allCarNum == 0 ? 0 : Utils.saveTwoSeat(saleAgent / allCarNum)));
        moneyStat.setPreSetup(allCarNum == 0 ? 0 : Utils.saveTwoSeat(preSetup / allCarNum));
        moneyStat.setSaleSetup(allCarNum == 0 ? 0 : Utils.saveTwoSeat(saleSetup / allCarNum));
        moneyStat.setAfterSetup(allCarNum == 0 ? 0 : Utils.saveTwoSeat(afterSetup / allCarNum));
        moneyStat.setPurchaseCost(allCarNum == 0 ? 0 : Utils.saveTwoSeat(purchaseCost / allCarNum));
        moneyStat.setSaleCost(allCarNum == 0 ? 0 : Utils.saveTwoSeat(saleCost / allCarNum));
        return moneyStat;
    }

    public static void statMoneyFlowData(MortgageRebateService mortgageRebateService,
                                         MoneyManagerService moneyManagerService,
                                         CarRecordService carRecordService,
                                         CarSaleSetupService carSaleSetupService,
                                         CarMoneyRecordService carMoneyRecordService,
                                         CarSaleInfoService carSaleInfoService,
                                         MortgageRecordService mortgageRecordService,
                                         CacheCenter cacheCenter, MoneyFlowStat moneyFlowStat, Long et) {

        moneyFlowStat.setCash(ToolUtil.statDifferentMoneyType(ContextType.MONEY_TYPE_CASH, moneyManagerService));
        moneyFlowStat.setBank(ToolUtil.statDifferentMoneyType(ContextType.MONEY_TYPE_BANK, moneyManagerService));
        moneyFlowStat.setPoss(ToolUtil.statDifferentMoneyType(ContextType.MONEY_TYPE_POSS, moneyManagerService));
        moneyFlowStat.setBasic(ToolUtil.statDifferentMoneyType(ContextType.MONEY_TYPE_BASIC, moneyManagerService));
        moneyFlowStat.setReceivable(ToolUtil.statDifferentMoneyType(ContextType.MONEY_TYPE_RECEIVABLE, moneyManagerService));
        moneyFlowStat.setCooperate(ToolUtil.statDifferentMoneyType(ContextType.MONEY_TYPE_COOPERATE, moneyManagerService));

        //借款特殊处理
        statLoanData(moneyManagerService, moneyFlowStat, et);

        Double house = 0.0;
        List<MoneyManager> list = moneyManagerService.getMoneyManagerListByType(ContextType.MONEY_TYPE_HOUSE);
        for (MoneyManager moneyManager : list) {
            if (moneyManager.getActionDate() <= et && et <= moneyManager.getActionEndDate()) {
                Double durationTime = (double) (moneyManager.getActionEndDate() - et);
                Double allTime = (double) (moneyManager.getActionEndDate() - moneyManager.getActionDate());
                house += Utils.saveTwoSeat(durationTime / allTime * moneyManager.getActionFee());
            }
        }
        moneyFlowStat.setHouse(Utils.saveTwoSeat(house));

        //返点金额
        Double backMoney = 0.0;
        List<MortgageRebate> mortgageRebateList = mortgageRebateService.getMortgageRebateList();
        for (MortgageRebate mortgageRebate : mortgageRebateList) {
            backMoney += mortgageRebate.getBackMoney();
        }
        moneyFlowStat.setBackMoney(Utils.saveTwoSeat(backMoney));

        NumberFormat nf = NumberFormat.getInstance();
        nf.setGroupingUsed(false);
        moneyFlowStat.setLiabilities(nf.format(moneyFlowStat.getCooperate() + moneyFlowStat.getLoan() + moneyFlowStat.getInterest()));

        //计算商品库存
        Double purchaseMoney = 0.0;
        List<CarRecord> carList = carRecordService.getCarRecordByRecordStatus(ContextType.RECORD_STATUS_PURCHASE);
        for (CarRecord carRecord : carList) {
            if (carRecord.getPurchaseType() == 0) {
                continue;
            }
            if ("寄售".equals(cacheCenter.getCarPropertyById(carRecord.getPurchaseType()).getPropertyValue())) {
                continue;
            }
            purchaseMoney += carRecord.getPaidMoney();
        }

        double stockMoney = 0.0;
        Integer dayNum = 0;
        carList = carRecordService.getCarRecordByRecordStatus(ContextType.RECORD_STATUS_STOCK);
        int i = 0;
        for (CarRecord carRecord : carList) {
            dayNum += TimeUtils.dayBetweenTwoTime(carRecord.getPurchaseDate(), et);
            if (carRecord.getPurchaseType() == 0) {
                continue;
            }
            if ("寄售".equals(cacheCenter.getCarPropertyById(carRecord.getPurchaseType()).getPropertyValue()) ||
                    "帮卖".equals(cacheCenter.getCarPropertyById(carRecord.getPurchaseType()).getPropertyValue())) {
                continue;
            }

            Double preAllFee = ToolUtil.getCarSaleSetupTuple(carSaleSetupService, carRecord.getId(), ContextType.PRE_SETUP_TYPE).left();
            Double allCost = ToolUtil.getCarMoneyRecordTuple(carMoneyRecordService, null, carRecord.getId(),CarPropertyEnum.MONEY_RECORD_COST.getDesc()).left();
            stockMoney += carRecord.getPaidMoney() + preAllFee + allCost;
            i++;
        }

        //车均库存
        moneyFlowStat.setStockTime(Utils.saveTwoSeat(carList.size() == 0 ? dayNum.doubleValue() : dayNum.doubleValue() / i));

        Double saleMoney = 0.0;
        carList = carRecordService.getCarRecordByRecordStatus(ContextType.RECORD_STATUS_SALE);
        for (CarRecord carRecord : carList) {
            if (carRecord.getPurchaseType() == 0) {
                continue;
            }
            if ("寄售".equals(cacheCenter.getCarPropertyById(carRecord.getPurchaseType()).getPropertyValue()) ||
                    "帮卖".equals(cacheCenter.getCarPropertyById(carRecord.getPurchaseType()).getPropertyValue())) {
                continue;
            }

            Double preAllFee = ToolUtil.getCarSaleSetupTuple(carSaleSetupService, carRecord.getId(), ContextType.PRE_SETUP_TYPE).left();
            Double allCost = ToolUtil.getCarMoneyRecordTuple(carMoneyRecordService, null, carRecord.getId(),CarPropertyEnum.MONEY_RECORD_COST.getDesc()).left();
            saleMoney += carRecord.getPaidMoney() + preAllFee + allCost;

            Double allSf = ToolUtil.getCarMoneyRecordTuple(carMoneyRecordService, null, carRecord.getId(),CarPropertyEnum.MONEY_RECORD_SALE.getDesc()).left();
            Double saleAllFee = ToolUtil.getCarSaleSetupTuple(carSaleSetupService, carRecord.getId(), ContextType.SALE_TYPE).left();

            CarSaleInfo carSaleInfo = carSaleInfoService.getCarSaleInfoById(carRecord.getSaleId());
            double serviceFee = carSaleInfo != null ? carSaleInfo.getServiceFee() : 0.0;
            saleMoney += allSf + serviceFee + saleAllFee;

            if (carSaleInfo != null) {
                saleMoney -= carSaleInfo.getPaidMoney();
                if (carSaleInfo.getSaleType().equals(ContextType.SALE_TYPE_AJ)) {
                    MortgageRecord mortgageRecord = mortgageRecordService.getMortgageRecordById(carSaleInfo.getMortgageId());
                    saleMoney -= mortgageRecord.getaMortgageMoney();
                }
            }
        }
        moneyFlowStat.setGoods(BigDecimal.valueOf(Utils.saveTwoSeat(purchaseMoney) + stockMoney + saleMoney));
        moneyFlowStat.setAll(BigDecimal.valueOf(Utils.saveTwoSeat(moneyFlowStat.getCash() + moneyFlowStat.getBank() + moneyFlowStat.getPoss() + moneyFlowStat.getBasic() +
                moneyFlowStat.getHouse() + moneyFlowStat.getBackMoney() + moneyFlowStat.getReceivable())).add(moneyFlowStat.getGoods()));
    }

    private static void statLoanData(MoneyManagerService moneyManagerService, MoneyFlowStat moneyFlowStat, long et) {
        Double temp = 0.0;
        double lx = 0.0;

        List<MoneyManager> list = moneyManagerService.getMoneyManagerListByType(ContextType.MONEY_TYPE_LOAN);
        for (MoneyManager moneyManager : list) {
            if (moneyManager.getActionType().equals(ContextType.MONEY_MANAGER_IN)) {
                temp += moneyManager.getActionFee();
                //计算利息
                lx += TimeUtils.dayBetweenTwoTime(moneyManager.getActionDate(), et) * moneyManager.getActionFee() / 3000.0;
            } else {
                temp -= moneyManager.getActionFee();
            }
        }
        moneyFlowStat.setLoan(Utils.saveTwoSeat(temp));
        moneyFlowStat.setInterest(Utils.saveTwoSeat(lx));
    }

    public static void statProperty(CarSaleInfoService carSaleInfoService,
                                    CacheCenter cacheCenter, List<CarRecord> list, PropertyStat propertyStat) {
        Map<Integer, Double> carLevelMap = new HashMap<>(list.size());
        Map<Integer, Double> carTakeTypeMap = new HashMap<>(list.size());
        Map<Integer, Double> purchaseTypeMap = new HashMap<>(list.size());
        Map<Integer, Double> carChannelMap = new HashMap<>(list.size());
        Map<Integer, Double> carLineMap = new HashMap<>(list.size());
        Map<String, Double> carBrandMap = new HashMap<>(list.size());
        Map<Integer, Double> consumerPropertyMap = new HashMap<>(list.size());
        Map<Integer, Double> consumerAgeMap = new HashMap<>(list.size());
        Map<String, Double> consumerSexMap = new HashMap<>(list.size());
        Map<Integer, Double> consumerResourceMap = new HashMap<>(list.size());
        Map<String, Double> saleTypeMap = new HashMap<>(list.size());
        for (CarRecord carRecord : list) {
            //提车方式
            carTakeTypeMap.put(carRecord.getCarTakeType(), removeNull(carTakeTypeMap.get(carRecord.getCarTakeType())) + 1);

            //采购类别
            purchaseTypeMap.put(carRecord.getPurchaseType(), removeNull(purchaseTypeMap.get(carRecord.getPurchaseType())) + 1);

            //车源渠道
            carChannelMap.put(carRecord.getCarChannel(), removeNull(carChannelMap.get(carRecord.getCarChannel())) + 1);

            //品牌
            carBrandMap.put(carRecord.getCarBrand(), removeNull(carBrandMap.get(carRecord.getCarBrand())) + 1);

            CarSaleInfo carSaleInfo = carSaleInfoService.getCarSaleInfoById(carRecord.getSaleId());
            //客户属性
            consumerPropertyMap.put(carSaleInfo.getConsumerProperty(), removeNull(consumerPropertyMap.get(carSaleInfo.getConsumerProperty())) + 1);

            //客户年龄段
            consumerAgeMap.put(carSaleInfo.getConsumerAge(), removeNull(consumerAgeMap.get(carSaleInfo.getConsumerAge())) + 1);

            //客户性别
            String key = carSaleInfo.getConsumerSex() == 1 ? "男" : "女";
            consumerSexMap.put(key, removeNull(consumerSexMap.get(key)) + 1);

            //获客渠道
            consumerResourceMap.put(carSaleInfo.getConsumerResource(), removeNull(consumerResourceMap.get(carSaleInfo.getConsumerResource())) + 1);

            //付款方式
            key = carSaleInfo.getSaleType().equals(ContextType.SALE_TYPE_AJ) ? "按揭" : "全款";
            saleTypeMap.put(key, removeNull(saleTypeMap.get(key)) + 1);
        }

        propertyStat.setCarLevelMap(install(cacheCenter, carLevelMap.entrySet().iterator(), list.size()));
        propertyStat.setCarTakeTypeMap(install(cacheCenter, carTakeTypeMap.entrySet().iterator(), list.size()));
        propertyStat.setPurchaseTypeMap(install(cacheCenter, purchaseTypeMap.entrySet().iterator(), list.size()));
        propertyStat.setCarChannelMap(install(cacheCenter, carChannelMap.entrySet().iterator(), list.size()));
        propertyStat.setCarLineMap(install(cacheCenter, carLineMap.entrySet().iterator(), list.size()));
        propertyStat.setCarBrandMap(installStringKey(carBrandMap.entrySet().iterator(), list.size()));
        propertyStat.setConsumerPropertyMap(install(cacheCenter, consumerPropertyMap.entrySet().iterator(), list.size()));
        propertyStat.setConsumerAgeMap(install(cacheCenter, consumerAgeMap.entrySet().iterator(), list.size()));
        propertyStat.setConsumerSexMap(installStringKey(consumerSexMap.entrySet().iterator(), list.size()));
        propertyStat.setConsumerResourceMap(install(cacheCenter, consumerResourceMap.entrySet().iterator(), list.size()));
        propertyStat.setSaleTypeMap(installStringKey(saleTypeMap.entrySet().iterator(), list.size()));
    }

    public static void statMortgageCompanyData(CarSaleInfoService carSaleInfoService,
                                               MortgageRecordService mortgageRecordService,
                                               List<CarRecord> list, OtherStat otherStat) {
        Map<String, Double> map = new HashMap<>();
        Double allMoney = 0.0;
        for (CarRecord carRecord : list) {
            CarSaleInfo carSaleInfo = carSaleInfoService.getCarSaleInfoById(carRecord.getSaleId());
            if (carSaleInfo.getSaleType().equals(ContextType.SALE_TYPE_AJ)) {
                MortgageRecord mortgageRecord = mortgageRecordService.getMortgageRecordById(carSaleInfo.getMortgageId());
                map.put(mortgageRecord.getMortgageCompany(), removeNull(map.get(mortgageRecord.getMortgageCompany())) + mortgageRecord.getLoanFee());
                allMoney += mortgageRecord.getLoanFee();
            }
        }
        otherStat.setMortgageMap(installStringKeyAndValue(map.entrySet().iterator(), allMoney));
    }

    public static void statInsuranceData(List<Insurance> list, OtherStat otherStat) {
        Double allRenewal = 0.0;
        Map<String, Double> insuranceMap = new HashMap<>(list.size());
        Double allQzxFee = 0.0;
        Map<String, Double> insuranceBusinessQzxMap = new HashMap<>(list.size());
        Double allSyxFee = 0.0;
        Map<String, Double> insuranceBusinessSyxMap = new HashMap<>(list.size());
        for (Insurance insurance : list) {
            String key;
            switch (insurance.getDealRenewal()) {
                case 0:
                    key = "无";
                    break;
                case 1:
                    key = "转收入";
                    break;
                case 2:
                    key = "退还";
                    break;
                case 3:
                    key = "暂存";
                    break;
                default:
                    key = "无";
            }
            insuranceMap.put(key, removeNull(insuranceMap.get(key)) + insurance.getRenewalFee());
            allRenewal += insurance.getRenewalFee();
            for (InsuranceBusiness insuranceBusiness : insurance.getList()) {
                insuranceBusinessQzxMap.put(insuranceBusiness.getQzxCompany(), removeNull(insuranceBusinessQzxMap.get(insuranceBusiness.getQzxCompany())) + insuranceBusiness.getQzxFee());
                allQzxFee += insuranceBusiness.getQzxFee();
                insuranceBusinessSyxMap.put(insuranceBusiness.getSyxCompany(), removeNull(insuranceBusinessSyxMap.get(insuranceBusiness.getSyxCompany())) + insuranceBusiness.getSyxFee());
                allSyxFee += insuranceBusiness.getSyxFee();
            }
        }
        otherStat.setRenewalMap(installStringKeyAndValue(insuranceMap.entrySet().iterator(), allRenewal));
        otherStat.setQzxMap(installStringKeyAndValue(insuranceBusinessQzxMap.entrySet().iterator(), allQzxFee));
        otherStat.setSyxMap(installStringKeyAndValue(insuranceBusinessSyxMap.entrySet().iterator(), allSyxFee));
    }

    private static Double removeNull(Double a) {
        return a == null ? 0.0 : a;
    }


    private static Map<String, Double> install(CacheCenter cacheCenter, Iterator<Map.Entry<Integer, Double>> it, Integer allNum) {
        Map<String, Double> map = new HashMap<>();
        while (it.hasNext()) {
            Map.Entry<Integer, Double> entry = it.next();
            String key = entry.getKey() == 0 ? CarPropertyEnum.CAR_PROPERTY_DEFAULT.getDesc() : cacheCenter.getCarPropertyById(entry.getKey()).getPropertyValue();
            Double value = entry.getValue() == 0 ? 0.0 : Utils.saveTwoSeat(entry.getValue() / allNum);
            map.put(key, value);
        }
        return map;
    }

    private static Map<String, Double> installStringKey(Iterator<Map.Entry<String, Double>> it, Integer allNum) {
        Map<String, Double> map = new HashMap<>();
        while (it.hasNext()) {
            Map.Entry<String, Double> entry = it.next();
            String key = entry.getKey();
            Double value = entry.getValue() == 0 ? 0.0 : Utils.saveTwoSeat(entry.getValue() / allNum);
            map.put(key, value);
        }
        return map;
    }

    private static Map<String, String> installStringKeyAndValue(Iterator<Map.Entry<String, Double>> it, Double num) {
        Map<String, String> map = new HashMap<>();
        while (it.hasNext()) {
            Map.Entry<String, Double> entry = it.next();
            String key = entry.getKey();
            Double value = entry.getValue() == 0 ? 0.0 : Utils.saveTwoSeat(entry.getValue() / num);
            String strValue = entry.getValue() + "/" + value;
            map.put(key, strValue);
        }
        return map;
    }
}
