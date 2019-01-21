package cm.lx.controller;

import cm.lx.bean.*;
import cm.lx.business.CommonAction;
import cm.lx.business.DaoCenter;
import cm.lx.common.ContextType;
import cm.lx.util.ExportExcel;
import cm.lx.util.TimeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class MortgageController extends BaseController {

    @Resource
    DaoCenter daoCenter;

    List<MortgageLog> mortgageLogList = new ArrayList<MortgageLog>();

    private List<MortgageLog> dealMortgageList(List<MortgageLog> list) {
        for (MortgageLog mortgageLog : list) {
            if (mortgageLog.getMortgageDate() != null && mortgageLog.getMortgageDate() != 0) {
                mortgageLog.setStrMortgageDate(TimeUtils.transformTimetagToDate(mortgageLog.getMortgageDate(), TimeUtils.FORMAT_ONE));
            }

            Map<String, Integer> map = new HashMap<String, Integer>();
            map.put("linkId", mortgageLog.getId());
            map.put("recordStatus", ContextType.PAY_RECORD_MORTGAGE_LOG);
            mortgageLog.setMortgagePaidList(daoCenter.getCarPaidRecordList(map));

            if (mortgageLog.getAgentPayFee() != 0) {
                mortgageLog.setAgentPayList(daoCenter.getCarSaleSetupByCarCostId(mortgageLog.getId(), ContextType.MORTGAGE_AGENT_PAY));
            }

        }
        return list;
    }

    @RequestMapping(value = "/mortgageSearch", method = RequestMethod.GET)
    public ModelAndView carSearchPurchase(
            @RequestParam(value = "mortgageType", required = true, defaultValue = "") Integer mortgageType,
            @RequestParam(value = "searchKey", required = false, defaultValue = "") String searchKey,
            @RequestParam(value = "searchValue", required = false, defaultValue = "") String searchValue,
            @RequestParam(value = "btime", required = false, defaultValue = "") String btime,
            @RequestParam(value = "etime", required = false, defaultValue = "") String etime) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("export", 1);
        mav.addObject("mortgageType", mortgageType);
        mav.addObject("searchKey", searchKey);
        mav.addObject("searchValue", searchValue);
        mav.addObject("btime", btime);
        mav.addObject("etime", etime);
        Long bt = StringUtils.isEmpty(btime) ? 0L : TimeUtils.transformDateToTimetag(btime, TimeUtils.FORMAT_ONE);
        Long et = StringUtils.isEmpty(etime) ? System.currentTimeMillis() : TimeUtils.transformDateToTimetag(etime, TimeUtils.FORMAT_ONE);
        List<MortgageLog> resultList = new ArrayList<MortgageLog>();

        if (StringUtils.isEmpty(searchKey)) {
            mav.setViewName("redirect:/mortgageView?mortgageType=" + mortgageType);
            mav.addObject(TIP, "搜索条件未选中！");
            return mav;
        } else {
            Map<String, Integer> map = new HashMap<String, Integer>();
            map.put("mortgageType", mortgageType);
            List<MortgageLog> list = dealMortgageList(daoCenter.getMortgageLogList(map));
            for (MortgageLog mortgageLog : list) {
                if (searchKey.equals("consumerName")) {
                    if (mortgageLog.getConsumerName().equals(searchValue)) {
                        resultList.add(mortgageLog);
                    }
                } else if (searchKey.equals("actionPerson")) {
                    if (mortgageLog.getActionPerson().equals(searchValue)) {
                        resultList.add(mortgageLog);
                    }
                } else if (searchKey.equals("mortgageCompany")) {
                    if (mortgageLog.getMortgageCompany().contains(searchValue)) {
                        resultList.add(mortgageLog);
                    }
                } else if (searchKey.equals("mortgageCommissioner")) {
                    if (mortgageLog.getMortgageCommissioner().equals(searchValue)) {
                        resultList.add(mortgageLog);
                    }
                } else if (searchKey.equals("mortgageDate")) {
                    if (bt <= mortgageLog.getMortgageDate() && mortgageLog.getMortgageDate() <= et) {
                        resultList.add(mortgageLog);
                    }
                }
            }
        }
        mav.setViewName("mortgage/list");
        mortgageLogList.addAll(resultList);
        mav.addObject(DATA, resultList);
        return mav;
    }

    @RequestMapping(value = "/mortgageView")
    public ModelAndView mortgageView(
            @RequestParam(value = "mortgageType", required = true, defaultValue = "") Integer mortgageType,
            HttpSession session) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("mortgage/list");
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("displayStatus", ContextType.DISPLAY_STATUS_SHOW);
        map.put("mortgageType", mortgageType);
        mav.addObject(DATA, dealMortgageList(daoCenter.getMortgageLogList(map)));
        mav.addObject("mortgageType", mortgageType);
        mav.addObject(TIP, session.getAttribute("tip"));
        return mav;
    }

    @RequestMapping(value = "/mortgageAction", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView mortgageAction(
            @RequestParam(value = "action", required = true, defaultValue = "") Integer action,
            @RequestParam(value = "over", required = false, defaultValue = "") Integer over,
            @RequestParam(value = "id", required = false, defaultValue = "") Integer id,
            @RequestParam(value = "mortgageType", required = false, defaultValue = "") Integer mortgageType,
            @RequestParam(value = "actionPerson", required = false, defaultValue = "") String actionPerson,
            @RequestParam(value = "consumerName", required = false, defaultValue = "") String consumerName,
            @RequestParam(value = "consumerPhone", required = false, defaultValue = "") String consumerPhone,
            @RequestParam(value = "carBrand", required = false, defaultValue = "") String carBrand,
            @RequestParam(value = "carModel", required = false, defaultValue = "") String carModel,
            @RequestParam(value = "mortgageCommissioner", required = false, defaultValue = "") String mortgageCommissioner,
            @RequestParam(value = "mortgageCompany", required = false, defaultValue = "") String mortgageCompany,
            @RequestParam(value = "loanFee", required = false, defaultValue = "") Double loanFee,
            @RequestParam(value = "interestRate", required = false, defaultValue = "") String interestRate,
            @RequestParam(value = "mortgageRebate", required = false, defaultValue = "") Double mortgageRebate,
            @RequestParam(value = "backFee", required = false, defaultValue = "") Double backFee,
            @RequestParam(value = "payBackFee", required = false, defaultValue = "") Double payBackFee,
            @RequestParam(value = "assessmentFee", required = false, defaultValue = "") Double assessmentFee,
            @RequestParam(value = "renewalFee", required = false, defaultValue = "") Double renewalFee,
            @RequestParam(value = "signBillFee", required = false, defaultValue = "") Double signBillFee,
            @RequestParam(value = "overYearFee", required = false, defaultValue = "") Double overYearFee,
            @RequestParam(value = "riskFee", required = false, defaultValue = "") Double riskFee,
            @RequestParam(value = "padFee", required = false, defaultValue = "") Double padFee,
            @RequestParam(value = "doorFee", required = false, defaultValue = "") Double doorFee,
            @RequestParam(value = "stampDuty", required = false, defaultValue = "") Double stampDuty,
            @RequestParam(value = "otherFee", required = false, defaultValue = "") Double otherFee,
            @RequestParam(value = "expenseCompany", required = false, defaultValue = "") String expenseCompany,
            @RequestParam(value = "businessExpenseFee", required = false, defaultValue = "") Double businessExpenseFee,
            @RequestParam(value = "forceExpenseFee", required = false, defaultValue = "") Double forceExpenseFee,
            @RequestParam(value = "businessExpenseRebate", required = false, defaultValue = "") Double businessExpenseRebate,
            @RequestParam(value = "businessExpenseBack", required = false, defaultValue = "") Double businessExpenseBack,
            HttpSession session) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("mortgage/add");
        mav.addObject("action", action);
        mav.addObject("mortgageType", mortgageType);

        mav.addObject("actionPerson", actionPerson);
        mav.addObject("consumerName", consumerName);
        mav.addObject("consumerPhone", consumerPhone);
        mav.addObject("carBrand", carBrand);
        mav.addObject("carModel", carModel);
        mav.addObject("mortgageCommissioner", mortgageCommissioner);
        mav.addObject("mortgageCompany", mortgageCompany);
        mav.addObject("loanFee", loanFee);
        mav.addObject("interestRate", interestRate);
        mav.addObject("mortgageRebate", mortgageRebate);
        mav.addObject("backFee", backFee);
        mav.addObject("payBackFee", payBackFee);
        mav.addObject("assessmentFee", assessmentFee);
        mav.addObject("renewalFee", renewalFee);
        mav.addObject("signBillFee", signBillFee);
        mav.addObject("overYearFee", overYearFee);
        mav.addObject("riskFee", riskFee);
        mav.addObject("padFee", padFee);
        mav.addObject("doorFee", doorFee);
        mav.addObject("stampDuty", stampDuty);
        mav.addObject("otherFee", otherFee);
        mav.addObject("expenseCompany", expenseCompany);
        mav.addObject("businessExpenseFee", businessExpenseFee);
        mav.addObject("forceExpenseFee", forceExpenseFee);
        mav.addObject("businessExpenseRebate", businessExpenseRebate);
        mav.addObject("businessExpenseBack", businessExpenseBack);

        if (over == null) {
            if (id != null) {
                MortgageLog mortgageLog = daoCenter.getMortgageLogById(id);
                mav.addObject("id", id);
                mav.addObject("actionPerson", mortgageLog.getActionPerson());
                mav.addObject("consumerName", mortgageLog.getConsumerName());
                mav.addObject("consumerPhone", mortgageLog.getConsumerPhone());
                mav.addObject("carBrand", mortgageLog.getCarBrand());
                mav.addObject("carModel", mortgageLog.getCarModel());
                mav.addObject("mortgageCommissioner", mortgageLog.getMortgageCommissioner());
                mav.addObject("mortgageCompany", mortgageLog.getMortgageCompany());
                mav.addObject("loanFee", mortgageLog.getLoanFee());
                mav.addObject("interestRate", mortgageLog.getInterestRate());
                mav.addObject("mortgageRebate", mortgageLog.getMortgageRebate());
                mav.addObject("backFee", mortgageLog.getBackFee());
                mav.addObject("payBackFee", mortgageLog.getPayBackFee());
                mav.addObject("assessmentFee", mortgageLog.getAssessmentFee());
                mav.addObject("renewalFee", mortgageLog.getRenewalFee());
                mav.addObject("signBillFee", mortgageLog.getSignBillFee());
                mav.addObject("overYearFee", mortgageLog.getOverYearFee());
                mav.addObject("riskFee", mortgageLog.getRiskFee());
                mav.addObject("padFee", mortgageLog.getPadFee());
                mav.addObject("doorFee", mortgageLog.getDoorFee());
                mav.addObject("stampDuty", mortgageLog.getStampDuty());
                mav.addObject("otherFee", mortgageLog.getOtherFee());
                mav.addObject("expenseCompany", mortgageLog.getExpenseCompany());
                mav.addObject("businessExpenseFee", mortgageLog.getBusinessExpenseFee());
                mav.addObject("forceExpenseFee", mortgageLog.getForceExpenseFee());
                mav.addObject("businessExpenseRebate", mortgageLog.getBusinessExpenseRebate());
                mav.addObject("businessExpenseBack", mortgageLog.getBusinessExpenseBack());
            }
            return mav;
        } else {
            MortgageLog mortgageLog = new MortgageLog();
            mortgageLog.setActionPerson(actionPerson);
            mortgageLog.setConsumerName(consumerName);
            mortgageLog.setConsumerPhone(consumerPhone);
            mortgageLog.setCarBrand(carBrand);
            mortgageLog.setCarModel(carModel);
            mortgageLog.setMortgageCommissioner(mortgageCommissioner);
            mortgageLog.setMortgageCompany(mortgageCompany);
            mortgageLog.setLoanFee(loanFee == null ? 0.0 : loanFee);
            mortgageLog.setInterestRate(interestRate == null ? "" : interestRate);
            mortgageLog.setMortgageRebate(mortgageRebate == null ? 0.0 : mortgageRebate);
            mortgageLog.setBackFee(backFee == null ? 0.0 : backFee);
            mortgageLog.setAssessmentFee(assessmentFee == null ? 0.0 : assessmentFee);
            mortgageLog.setRenewalFee(renewalFee == null ? 0.0 : renewalFee);
            mortgageLog.setExpenseCompany(expenseCompany);
            mortgageLog.setBusinessExpenseFee(businessExpenseFee == null ? 0.0 : businessExpenseFee);
            mortgageLog.setForceExpenseFee(forceExpenseFee == null ? 0.0 : forceExpenseFee);
            mortgageLog.setBusinessExpenseRebate(businessExpenseRebate == null ? 0.0 : businessExpenseRebate);
            mortgageLog.setBusinessExpenseBack(businessExpenseBack == null ? 0.0 : businessExpenseBack);
            mortgageLog.setMortgageType(mortgageType);

            if (mortgageType.equals(ContextType.MORTGAGE_TYPE_AGENCY)) {
                mortgageLog.setRiskFee(riskFee == null ? 0.0 : riskFee);
                mortgageLog.setPadFee(padFee == null ? 0.0 : padFee);
                mortgageLog.setDoorFee(doorFee == null ? 0.0 : doorFee);
                mortgageLog.setStampDuty(stampDuty == null ? 0.0 : stampDuty);
                mortgageLog.setOtherFee(otherFee == null ? 0.0 : otherFee);
            } else if (mortgageType.equals(ContextType.MORTGAGE_TYPE_OUT)) {
                mortgageLog.setPayBackFee(payBackFee == null ? 0.0 : payBackFee);
                mortgageLog.setSignBillFee(signBillFee == null ? 0.0 : signBillFee);
                mortgageLog.setOverYearFee(overYearFee == null ? 0.0 : overYearFee);
            }
            mav.clear();
            if (action.equals(CREATE_ACTION)) {
                mortgageLog.setMortgageMoney(0.0);
                mortgageLog.setAgentPayFee(0.0);
                mortgageLog.setDisplayStatus(ContextType.DISPLAY_STATUS_SHOW);
                //计算毛利润
                CommonAction.calculateMortgageData(mortgageLog);
                daoCenter.createMortgageLog(mortgageLog);
            } else if (action.equals(MOD_ACTION)) {
                MortgageLog old = daoCenter.getMortgageLogById(id);

                mortgageLog.setId(id);
                mortgageLog.setMortgageMoney(old.getMortgageMoney());
                mortgageLog.setAgentPayFee(old.getAgentPayFee());
                CommonAction.calculateMortgageData(mortgageLog);
                daoCenter.updateMortgageLog(mortgageLog);

            }
            mav.setViewName("redirect:/mortgageView?mortgageType=" + mortgageType);
            return mav;
        }
    }


    @RequestMapping(value = "/mortgageDateAction", method = RequestMethod.GET)
    public ModelAndView mortgageDateAction(
            @RequestParam(value = "soldDate", required = false, defaultValue = "") String soldDate,
            @RequestParam(value = "mortgageType", required = false, defaultValue = "") Integer mortgageType,
            @RequestParam(value = "id", required = false, defaultValue = "") Integer id,
            HttpSession session) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:/mortgageView?mortgageType=" + mortgageType);

        if (StringUtils.isEmpty(soldDate)) {
            session.setAttribute("tip", "日期必选！");
            return mav;
        }
        MortgageLog update = new MortgageLog();
        update.setId(id);
        update.setMortgageDate(TimeUtils.transformDateToTimetag(soldDate, TimeUtils.FORMAT_ONE));
        daoCenter.updateMortgageLog(update);
        session.setAttribute("tip", "ok 日期更新成功！");
        return mav;
    }

    @RequestMapping(value = "/mortgagePaid", method = RequestMethod.GET)
    public ModelAndView mortgagePaid(
            @RequestParam(value = "goonPaid", required = false, defaultValue = "") Double goonPaid,
            @RequestParam(value = "paidReason", required = false, defaultValue = "") String paidReason,
            @RequestParam(value = "mortgageType", required = false, defaultValue = "") Integer mortgageType,
            @RequestParam(value = "id", required = false, defaultValue = "") Integer id,
            HttpSession session) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:/mortgageView?mortgageType=" + mortgageType);

        if (goonPaid == null) {
            session.setAttribute("tip", "放贷金额必填！");
            return mav;
        }

        MortgageLog mortgageLog = daoCenter.getMortgageLogById(id);
        mortgageLog.setMortgageMoney(goonPaid + mortgageLog.getMortgageMoney());

        //计算毛利润
        CommonAction.calculateMortgageData(mortgageLog);
        daoCenter.updateMortgageLog(mortgageLog);
        session.setAttribute("tip", "放款成功");

        //创建放款记录
        CarPaidRecord carPaidRecord = new CarPaidRecord();
        carPaidRecord.setCarRecordId(id);
        carPaidRecord.setRecordStatus(ContextType.PAY_RECORD_MORTGAGE_LOG);
        carPaidRecord.setPaidMoney(goonPaid);
        carPaidRecord.setPaidReason(paidReason);
        daoCenter.createCarPaidRecord(carPaidRecord);
        return mav;
    }

//    @RequestMapping(value = "/mortgagePaid", method = RequestMethod.GET)
//    public ModelAndView mortgagePaid(
//            @RequestParam(value = "goonPaid", required = false, defaultValue = "") Double goonPaid,
//            @RequestParam(value = "id", required = false, defaultValue = "") Integer id,
//            HttpSession session) {
//        if (goonPaid == null) goonPaid = 0.0;
//        ModelAndView mav = new ModelAndView();
//        MortgageRecord mortgageLog = daoCenter.getMortgageRecordById(id);
//        Double paidMoney = mortgageLog.getaMortgageMoney() + goonPaid;
//
//        boolean isSale = false;
//        boolean isMortgage = false;
//        if (mortgageLog.getIsSale() != null && mortgageLog.getIsSale() == 1) {
//            mav.setViewName("redirect:/carSaleView");
//            isSale = true;
//        } else if (mortgageLog.getIsNewCar() != null && mortgageLog.getIsNewCar() == 1) {
//            mav.setViewName("redirect:/newCarView");
//        } else {
//            mav.setViewName("redirect:/mortgageView");
//            isMortgage = true;
//        }
//
//        if (paidMoney > mortgageLog.getMortgageMoney()) {
//            return mav;
//        }
//
//        MortgageRecord update = new MortgageRecord();
//        update.setId(id);
//        update.setaMortgageMoney(paidMoney);
//        if (isMortgage) {
//            if (paidMoney.equals(mortgageLog.getMortgageMoney())) {
//                update.setDisplayStatus(ContextType.DISPLAY_STATUS_HIDE);
//            }
//        }
//        daoCenter.updateMortgageRecord(update);
//
//        //如果是汽车销售，判断是否达到转已售的标准
//        if (isSale) {
//            boolean isMortgageAbe = paidMoney.equals(mortgageLog.getMortgageMoney());
//            if (isMortgageAbe) {
//                CarSaleInfo carSaleInfo = daoCenter.getCarSaleInfoById(mortgageLog.getSaleId());
//                if (carSaleInfo.getPayMoney().equals(carSaleInfo.getPaidMoney())) {
//                    //判断销售成本是否录入
//                    CarRecord carRecord = daoCenter.getCarRecordById(carSaleInfo.getCarRecordId());
//                    if (carRecord.getIsSf() == 0) {
//                        session.setAttribute("tip", "销售成本未填写，不能转已售！");
//                        return mav;
//                    }
//                    if (carRecord.getSoldDate() == 0) {
//                        session.setAttribute("tip", "转已售日期未填写，不能转已售！");
//                        return mav;
//                    }
//                    CommonAction.changeCarStatus(daoCenter, carSaleInfo);
//                }
//            }
//        }
//        return mav;
//    }

    @RequestMapping(value = "/mortgageDelete", method = RequestMethod.GET)
    public ModelAndView mortgageDelete(
            @RequestParam(value = "id", required = false, defaultValue = "") Integer id,
            @RequestParam(value = "mortgageType", required = false, defaultValue = "") Integer mortgageType,
            HttpSession session) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:/mortgageView?mortgageType=" + mortgageType);
        daoCenter.deleteMortgageLog(id);
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("linkId", id);
        //删除付款记录表
        map.put("recordStatus", ContextType.PAY_RECORD_MORTGAGE_LOG);
        daoCenter.deleteCarPaidRecordList(map);
        session.setAttribute("tip", "ok 删除成功！");
        return mav;
    }

    @RequestMapping(value = "/mortgageStatus", method = RequestMethod.GET)
    public ModelAndView mortgageStatus(
            @RequestParam(value = "id", required = false, defaultValue = "") Integer id,
            @RequestParam(value = "mortgageType", required = false, defaultValue = "") Integer mortgageType,
            HttpSession session) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:/mortgageView?mortgageType=" + mortgageType);
        MortgageLog old = daoCenter.getMortgageLogById(id);
        if (old.getMortgageDate() == null || old.getMortgageDate() == 0) {
            session.setAttribute("tip", "放款日期必填！");
            return mav;
        }
        if (old.getMortgageMoney() == 0) {
            session.setAttribute("tip", "放款金额必填！");
            return mav;
        }
        MortgageLog update = new MortgageLog();
        update.setId(id);
        update.setDisplayStatus(ContextType.DISPLAY_STATUS_HIDE);
        daoCenter.updateMortgageLog(update);
        session.setAttribute("tip", "ok 转数据库成功！");
        return mav;
    }

    @RequestMapping(value = "/mortgageExport", method = RequestMethod.GET)
    public ModelAndView mortgageExport(HttpServletRequest request, HttpServletResponse response,
                                       @RequestParam(value = "mortgageType", required = false, defaultValue = "") Integer mortgageType) throws Exception {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:/mortgageView?mortgageType=" + mortgageType);

        //组装头行
        List<String> headers = new ArrayList<String>();
        MortgageLog.installHeaders(headers);

        List<MortgageLogExport> list = new ArrayList<MortgageLogExport>();
        for (MortgageLog mortgageLog : mortgageLogList) {
            MortgageLogExport mortgageLogExport = new MortgageLogExport();
            mortgageLog.installAll(mortgageLogExport);
            list.add(mortgageLogExport);
        }

        response.setContentType("octets/stream");
        String excelName = "按揭信息";
        //转码防止乱码
        response.addHeader("Content-Disposition", "attachment;filename=" + new String(excelName.getBytes("utf-8"), "ISO8859-1") + ".xls");
        ExportExcel<MortgageLogExport> ex = new ExportExcel<MortgageLogExport>();
        OutputStream out = response.getOutputStream();
        ex.exportExcel("按揭信息", headers, list, out);
        out.close();
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        return mav;
    }
}
