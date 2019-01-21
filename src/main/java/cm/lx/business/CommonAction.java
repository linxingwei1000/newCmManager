package cm.lx.business;

import cm.lx.bean.*;
import cm.lx.common.ContextType;
import cm.lx.util.TimeUtils;
import cm.lx.util.Utils;
import org.apache.commons.lang3.StringUtils;

import javax.smartcardio.Card;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommonAction {

    public static void changeCarStatus(DaoCenter daoCenter, CarSaleInfo carSaleInfo) {
        CarRecord old = daoCenter.getCarRecordById(carSaleInfo.getCarRecordId());
        CarRecord carRecord = new CarRecord();
        carRecord.setId(carSaleInfo.getCarRecordId());
        carRecord.setRecordStatus(ContextType.RECORD_STATUS_SOLD);

        //更新服务基金费
        CarSf carSf = daoCenter.getCarSfById(old.getSfId());
        carSf.setServiceFee(0.0);
        if (carSf.getIsProduce() == 1) {
            carSf.setServiceFee(ContextType.SERVICE_MONEY);
        }
        daoCenter.updateCarSf(carSf);

        CarCost carCost = daoCenter.getCarCostById(old.getCostId());

        //计算车辆毛利润
        Double ajMoney = 0.0;
        if (carSaleInfo.getSaleType().equals(ContextType.SALE_TYPE_AJ)) {
            MortgageRecord mortgageRecord = daoCenter.getMortgageRecordById(carSaleInfo.getMortgageId());
            ajMoney = mortgageRecord.getBackFee() + mortgageRecord.getAssessmentFee() + mortgageRecord.getRiskFee() + mortgageRecord.getPadFee() +
                    mortgageRecord.getDoorFee() + mortgageRecord.getStampDuty() + mortgageRecord.getOtherFee() - 1500;
        }

        Double grossProfit = ajMoney + carSaleInfo.getSaleMoney() + carCost.getOtherIncomeFee() - carSaleInfo.getAgencyFee() -
                carCost.getMentionFee() - carCost.getMentionSubsidy() - carCost.getCrossingFee() - carCost.getTravelFee() -
                carCost.getPutFee() - carCost.getPutSubsidy() - carCost.getMailFee() - carCost.getFreightFee() - carCost.getBillingFee() -
                carCost.getOilFee() - carCost.getCattleFee() - carCost.getExpenseFee() - carCost.getOtherFee() - carCost.getPreSaleFee() -
                carSf.getTransferFee() - carSf.getTransferSubsidy() - carSf.getTransferCrossingFee() - carSf.getTransferBillingFee() -
                carSf.getTransferOilFee() - carSf.getRubbing() - carSf.getCattleFee() - carSf.getRemoveCard() - carSf.getServiceFee() -
                old.getPurchaseMoney() - old.getAgencyFee() - carSf.getSaleFee();

        carRecord.setGrossProfit(String.valueOf(Utils.saveTwoSeat(grossProfit + carSaleInfo.getAllUnearnedInsurance() - carSaleInfo.getPayCompanyFee())));

        //计算车溢价
        Double vehiclePremium = carSaleInfo.getSaleMoney() + carSaleInfo.getExpenseRebate() * carSaleInfo.getBusinessExpenseFee() -
                carSaleInfo.getAgencyFee() - carSf.getSaleFee();
        if (carSaleInfo.getSaleType().equals(ContextType.SALE_TYPE_AJ)) {
            vehiclePremium = vehiclePremium - old.getaAuthorityMoney() + ajMoney;
        } else {
            vehiclePremium = vehiclePremium - old.getqAuthorityMoney();
        }
        carRecord.setVehiclePremium(Utils.saveTwoSeat(vehiclePremium));

        //计算库存周期
        carRecord.setStockDuration((double) (carSaleInfo.getSaleDate() - old.getPurchaseDate()) / (1000 * 60 * 60 * 24));

        //计算车辆提成
        Double carCommission;
        if (carSaleInfo.getSaleType().equals(ContextType.SALE_TYPE_AJ)) {
            MortgageRecord mortgageRecord = daoCenter.getMortgageRecordById(carSaleInfo.getMortgageId());
            Double temp = (carSaleInfo.getSaleMoney() - old.getaAuthorityMoney() - carSf.getSaleFee() - carSaleInfo.getAgencyFee()
                    + mortgageRecord.getRiskFee() + mortgageRecord.getPadFee()) * 0.25;
            if (temp <= 0) {
                carCommission = 500.0;
            } else {
                carCommission = 500.0 + temp;
            }
        } else {
            Double temp = (carSaleInfo.getSaleMoney() - old.getqAuthorityMoney() - carSf.getSaleFee() - carSaleInfo.getAgencyFee()
                    + carSaleInfo.getExpenseRebate() * carSaleInfo.getBusinessExpenseFee()) * 0.2;
            if (temp <= 0) {
                carCommission = 200.0;
            } else {
                carCommission = 200.0 + temp;
            }
        }

        Account account = daoCenter.getAccountByRealName(old.getPurchasePerson());
        Double purchaseCommission = 0.0;
        if (account != null) {
            purchaseCommission = Utils.saveTwoSeat((Double.valueOf(carRecord.getGrossProfit()) - carCommission) * account.getPurchaseCommission() / 100);
        }

        Double shareDividends = 0.0;
        if (!StringUtils.isEmpty(old.getInsideProportion())) {
            shareDividends = Utils.saveTwoSeat((Double.valueOf(carRecord.getGrossProfit()) - carCommission - purchaseCommission) * (Double.valueOf(old.getInsideProportion())));
        }

        boolean needCreate = false;
        WagesAssist wagesAssist = daoCenter.getWagesAssistByCarRecordId(carRecord.getId());
        if (wagesAssist == null) {
            wagesAssist = new WagesAssist();
            needCreate = true;
        }
        wagesAssist.setSaleDate(carSaleInfo.getSaleDate());
        wagesAssist.setSoldDate(old.getSoldDate());
        wagesAssist.setSalePerson(carSaleInfo.getSalePerson());
        wagesAssist.setPurchasePerson(old.getPurchasePerson());
        wagesAssist.setInsidePerson(old.getInsidePerson() == null ? "" : old.getInsidePerson());
        wagesAssist.setCarCommission(carCommission);
        wagesAssist.setPurchaseCommission(purchaseCommission);
        wagesAssist.setShareDividends(shareDividends);
        wagesAssist.setCarRecordId(carRecord.getId());

        if (needCreate) {
            wagesAssist.setId(daoCenter.getWagesAssistAutoId());
            daoCenter.createWagesAssist(wagesAssist);
        } else {
            daoCenter.updateWagesAssist(wagesAssist);
        }
        carRecord.setExpenseId(wagesAssist.getId());
        daoCenter.updateCarRecord(carRecord);
    }

    public static void calculate(DaoCenter daoCenter, String stime, String etime, String soldDate, Wages wages) {
        Long st = TimeUtils.transformDateToTimetag(stime, TimeUtils.FORMAT_ONE);
        Long et = TimeUtils.transformDateToTimetag(etime, TimeUtils.FORMAT_ONE) + 24 * 60 * 60 * 1000;
        Long soldt = TimeUtils.transformDateToTimetag(soldDate, TimeUtils.FORMAT_ONE);
        Map<String, Long> map = new HashMap<String, Long>();
        map.put("st", st);
        map.put("et", et);
        map.put("soldt", soldt);
        List<WagesAssist> list = daoCenter.getWagesAssistByMap(map);

        Double carCommission = 0.0;
        Double purchaseCommission = 0.0;
        Double shareDividends = 0.0;

        for (WagesAssist wagesAssist : list) {
            if (wagesAssist.getSalePerson().equals(wages.getStaff())) {
                carCommission += wagesAssist.getCarCommission();
            }
            if (wagesAssist.getPurchasePerson().equals(wages.getStaff())) {
                purchaseCommission += wagesAssist.getPurchaseCommission();
            }
            if (wagesAssist.getInsidePerson().equals(wages.getStaff())) {
                shareDividends += wagesAssist.getShareDividends();
            }
        }
        wages.setCarCommission(carCommission);
        wages.setPurchaseCommission(purchaseCommission);
        wages.setShareDividends(shareDividends);
        wages.calculateWages();
    }

    public static Double calculatePayMoney(Double saleMoney, Double tempMoney, MortgageRecord mortgageRecord) {
        Double payMoney = saleMoney + tempMoney;
        if (mortgageRecord != null) {
            payMoney = payMoney - mortgageRecord.getLoanFee() + mortgageRecord.getAssessmentFee() + mortgageRecord.getRiskFee() + mortgageRecord.getRenewalFee() +
                    mortgageRecord.getPadFee() + mortgageRecord.getDoorFee() + mortgageRecord.getStampDuty() + mortgageRecord.getOtherFee();
        }
        return payMoney < 0 ? 0 : payMoney;
    }

    public static void calculateMortgageData(MortgageLog mortgageLog) {
        Double needPayConsumer = 0.0;
        Double grossProfit = 0.0;
        if (mortgageLog.getMortgageType().equals(ContextType.MORTGAGE_TYPE_AGENCY)) {
            //计算因支付金额
            needPayConsumer = mortgageLog.getLoanFee() - mortgageLog.getAssessmentFee() - mortgageLog.getRiskFee() -
                    mortgageLog.getRenewalFee() - mortgageLog.getPadFee() - mortgageLog.getDoorFee() -
                    mortgageLog.getStampDuty() - mortgageLog.getOtherFee() - mortgageLog.getBusinessExpenseFee() -
                    mortgageLog.getForceExpenseFee() - mortgageLog.getAgentPayFee();
            grossProfit = mortgageLog.getMortgageMoney() - needPayConsumer - mortgageLog.getRenewalFee();
        } else if (mortgageLog.getMortgageType().equals(ContextType.MORTGAGE_TYPE_OUT)) {
            needPayConsumer = mortgageLog.getLoanFee() + mortgageLog.getPayBackFee() - mortgageLog.getAssessmentFee() -
                    mortgageLog.getRenewalFee() - mortgageLog.getSignBillFee() - mortgageLog.getOverYearFee() -
                    mortgageLog.getBusinessExpenseFee() * 0.75 - mortgageLog.getForceExpenseFee() - mortgageLog.getAgentPayFee();
            grossProfit = mortgageLog.getMortgageMoney() - needPayConsumer - mortgageLog.getRenewalFee() +
                    mortgageLog.getBusinessExpenseBack() - mortgageLog.getBusinessExpenseFee() * 0.25;
        }
        mortgageLog.setNeedPayConsumer(needPayConsumer);
        mortgageLog.setGrossProfit(grossProfit);
    }

    public static void syncCarRecord(DaoCenter daoCenter) {
        List<CarDossier> list = daoCenter.getCarDossierByMap(null);
        for (CarDossier carDossier : list) {
            CarRecord carRecord = daoCenter.getCarRecordById(carDossier.getCarRecordId());
            //判断车辆销售状态
            if (carRecord.getRecordStatus().equals(ContextType.RECORD_STATUS_SOLD)) {
                if (!carDossier.getDisplayStatus().equals(ContextType.DISPLAY_STATUS_HIDE)) {
                    CarDossier update = new CarDossier();
                    update.setId(carDossier.getId());
                    update.setDisplayStatus(ContextType.DISPLAY_STATUS_HIDE);
                    daoCenter.updateCarDossier(update);
                }
            } else {
                if (carDossier.getDisplayStatus().equals(ContextType.DISPLAY_STATUS_HIDE)) {
                    CarDossier update = new CarDossier();
                    update.setId(carDossier.getId());
                    update.setDisplayStatus(ContextType.DISPLAY_STATUS_SHOW);
                    daoCenter.updateCarDossier(update);
                }
            }
        }
    }
}