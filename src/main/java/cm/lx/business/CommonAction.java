package cm.lx.business;

import cm.lx.common.ContextType;
import cm.lx.bean.entity.*;

public class CommonAction {



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

}