package cm.lx.controller.car;

import cm.lx.bean.ContextBean;
import cm.lx.business.CacheCenter;
import cm.lx.business.CommonAction;
import cm.lx.business.ToolUtil;
import cm.lx.common.ContextType;
import cm.lx.controller.BaseController;
import cm.lx.bean.entity.*;
import cm.lx.service.*;
import cm.lx.util.TimeUtils;
import cm.lx.util.Utils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author linxingwei
 * @date 2019/1/22
 */
@Controller
public class CarSaleController extends BaseController {

    @Resource
    CacheCenter cacheCenter;

    @Resource
    CarBathService carBathService;

    @Resource
    CarRecordService carRecordService;

    @Resource
    CarSaleInfoService carSaleInfoService;

    @Resource
    CarSaleSetupService carSaleSetupService;

    @Resource
    CarPaidRecordService carPaidRecordService;

    @Resource
    CarSfService carSfService;

    @Resource
    CarCostService carCostService;

    @Resource
    MortgageRecordService mortgageRecordService;

    @Resource
    CarPayMoneyAssistService carPayMoneyAssistService;

    @Resource
    AccountService accountService;

    @Resource
    WagesAssistService wagesAssistService;

    @Resource
    CarDossierService carDossierService;

    //车辆销售操作
    @RequestMapping(value = "/carSaleView", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView carSaleView(HttpSession session) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("car/saleList");
        mav.addObject("list", cacheCenter.getCarRecordCombinationInfo(
                carRecordService.getCarRecordByRecordStatus(ContextType.RECORD_STATUS_SALE)));
        mav.addObject(TIP, session.getAttribute("tip"));
        return mav;
    }

    @RequestMapping(value = "/carSoldView", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView carSoldView(HttpSession session) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("car/soldList");
        List<CarRecord> list = carRecordService.getCarRecordByRecordStatus(ContextType.RECORD_STATUS_SOLD);
        mav.addObject("carNum", list.size());

        if(list.size()>10){
            list = list.subList(0,10);
        }
        mav.addObject("list", cacheCenter.getCarRecordCombinationInfo(list));
        mav.addObject(TIP, session.getAttribute("tip"));
        return mav;
    }

    //车辆销售信息
    @RequestMapping(value = "/carSaleInfoAction", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView carSaleInfoAction(
            @RequestParam(value = "action", required = true, defaultValue = "") Integer action,
            @RequestParam(value = "over", required = false, defaultValue = "") Integer over,
            @RequestParam(value = "id", required = false, defaultValue = "") Integer id,
            @RequestParam(value = "recordId", required = false, defaultValue = "") Integer recordId,
            @RequestParam(value = "salePerson", required = false, defaultValue = "") String salePerson,
            @RequestParam(value = "saleDate", required = false, defaultValue = "") String saleDate,
            @RequestParam(value = "saleMoney", required = false, defaultValue = "") Double saleMoney,
            @RequestParam(value = "agencyFee", required = false, defaultValue = "") Double agencyFee,
            @RequestParam(value = "unearnedInsurance", required = false, defaultValue = "") Double unearnedInsurance,
            @RequestParam(value = "consumerProperty", required = false, defaultValue = "") Integer consumerProperty,
            @RequestParam(value = "consumerResource", required = false, defaultValue = "") Integer consumerResource,
            @RequestParam(value = "purchaseUse", required = false, defaultValue = "") Integer purchaseUse,
            @RequestParam(value = "consumerName", required = false, defaultValue = "") String consumerName,
            @RequestParam(value = "consumerSex", required = false, defaultValue = "") Integer consumerSex,
            @RequestParam(value = "consumerAge", required = false, defaultValue = "") Integer consumerAge,
            @RequestParam(value = "consumerAddress", required = false, defaultValue = "") String consumerAddress,
            @RequestParam(value = "consumerPhone", required = false, defaultValue = "") String consumerPhone,
            @RequestParam(value = "saleType", required = false, defaultValue = "") Integer saleType,
            @RequestParam(value = "mortgageCommissioner", required = false, defaultValue = "") String mortgageCommissioner,
            @RequestParam(value = "mortgageCompany", required = false, defaultValue = "") String mortgageCompany,
            @RequestParam(value = "loanFee", required = false, defaultValue = "") Double loanFee,
            @RequestParam(value = "interestRate", required = false, defaultValue = "") String interestRate,
            @RequestParam(value = "mortgageRebate", required = false, defaultValue = "") Double mortgageRebate,
            @RequestParam(value = "backFee", required = false, defaultValue = "") Double backFee,
            @RequestParam(value = "assessmentFee", required = false, defaultValue = "") Double assessmentFee,
            @RequestParam(value = "riskFee", required = false, defaultValue = "") Double riskFee,
            @RequestParam(value = "renewalFee", required = false, defaultValue = "") Double renewalFee,
            @RequestParam(value = "padFee", required = false, defaultValue = "") Double padFee,
            @RequestParam(value = "doorFee", required = false, defaultValue = "") Double doorFee,
            @RequestParam(value = "stampDuty", required = false, defaultValue = "") Double stampDuty,
            @RequestParam(value = "otherFee", required = false, defaultValue = "") Double otherFee,
            HttpSession session) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("car/saleInfoAdd");
        mav.addObject("action", action);
        mav.addObject("recordId", recordId);
        ToolUtil.initCarPropertyData(mav, cacheCenter);

        mav.addObject("salePerson", salePerson);
        mav.addObject("saleDate", saleDate);
        mav.addObject("saleMoney", saleMoney);
        mav.addObject("agencyFee", agencyFee);
        mav.addObject("unearnedInsurance", unearnedInsurance);
        mav.addObject("consumerProperty", consumerProperty);
        mav.addObject("consumerResource", consumerResource);
        mav.addObject("purchaseUse", purchaseUse);
        mav.addObject("consumerName", consumerName);
        mav.addObject("consumerSex", consumerSex);
        mav.addObject("consumerAge", consumerAge);
        mav.addObject("consumerAddress", consumerAddress);
        mav.addObject("consumerPhone", consumerPhone);
        mav.addObject("saleType", saleType == null ? saleType = 1 : saleType);
        mav.addObject("mortgageCommissioner", mortgageCommissioner);
        mav.addObject("mortgageCompany", mortgageCompany);
        mav.addObject("loanFee", loanFee);
        mav.addObject("interestRate", interestRate);
        mav.addObject("mortgageRebate", mortgageRebate);
        mav.addObject("backFee", backFee);
        mav.addObject("assessmentFee", assessmentFee);
        mav.addObject("riskFee", riskFee);
        mav.addObject("renewalFee", renewalFee);
        mav.addObject("padFee", padFee);
        mav.addObject("doorFee", doorFee);
        mav.addObject("stampDuty", stampDuty);
        mav.addObject("otherFee", otherFee);

        if (over == null) {
            if (id != null) {
                CarSaleInfo carSaleInfo = carSaleInfoService.getCarSaleInfoById(id);
                mav.addObject("id", id);

                mav.addObject("salePerson", carSaleInfo.getSalePerson());
                mav.addObject("saleDate", TimeUtils.transformTimetagToDate(carSaleInfo.getSaleDate(), TimeUtils.FORMAT_ONE));
                mav.addObject("saleMoney", carSaleInfo.getSaleMoney());
                mav.addObject("agencyFee", carSaleInfo.getAgencyFee());
                mav.addObject("unearnedInsurance", carSaleInfo.getUnearnedInsurance());
                mav.addObject("consumerProperty", carSaleInfo.getConsumerProperty());
                mav.addObject("consumerResource", carSaleInfo.getConsumerResource());
                mav.addObject("purchaseUse", carSaleInfo.getPurchaseUse());
                mav.addObject("consumerName", carSaleInfo.getConsumerName());
                mav.addObject("consumerSex", carSaleInfo.getConsumerSex());
                mav.addObject("consumerAge", carSaleInfo.getConsumerAge());
                mav.addObject("consumerAddress", carSaleInfo.getConsumerAddress());
                mav.addObject("consumerPhone", carSaleInfo.getConsumerPhone());
                mav.addObject("saleType", carSaleInfo.getSaleType());

                if (carSaleInfo.getSaleType().equals(ContextType.SALE_TYPE_AJ)) {
                    MortgageRecord mortgageRecord = mortgageRecordService.getMortgageRecordById(carSaleInfo.getMortgageId());
                    mav.addObject("mortgageCommissioner", mortgageRecord.getMortgageCommissioner());
                    mav.addObject("mortgageCompany", mortgageRecord.getMortgageCompany());
                    mav.addObject("loanFee", mortgageRecord.getLoanFee());
                    mav.addObject("interestRate", mortgageRecord.getInterestRate());
                    mav.addObject("mortgageRebate", mortgageRecord.getMortgageRebate());
                    mav.addObject("backFee", mortgageRecord.getBackFee());
                    mav.addObject("assessmentFee", mortgageRecord.getAssessmentFee());
                    mav.addObject("riskFee", mortgageRecord.getRiskFee());
                    mav.addObject("renewalFee", mortgageRecord.getRenewalFee());
                    mav.addObject("padFee", mortgageRecord.getPadFee());
                    mav.addObject("doorFee", mortgageRecord.getDoorFee());
                    mav.addObject("stampDuty", mortgageRecord.getStampDuty());
                    mav.addObject("otherFee", mortgageRecord.getOtherFee());
                }
            }
            return mav;
        } else {
            if (StringUtils.isEmpty(salePerson)) {
                mav.addObject(TIP, "销售人必填！");
                return mav;
            }

            if (StringUtils.isEmpty(saleDate)) {
                mav.addObject(TIP, "销售日期必填！");
                return mav;
            }

            if (saleMoney == 0) {
                mav.addObject(TIP, "销售价格必填！");
                return mav;
            }
            if (unearnedInsurance == null && action.equals(CREATE_ACTION)) {
                mav.addObject(TIP, "预收保险金额必填！");
                return mav;
            }


            if (StringUtils.isEmpty(saleDate)) {
                mav.addObject(TIP, "销售日期必填！");
                return mav;
            }

            if (consumerProperty == 0) {
                mav.addObject(TIP, "客户属性必选！");
                return mav;
            }

            if (consumerResource == 0) {
                mav.addObject(TIP, "获客渠道必选！");
                return mav;
            }

            if (purchaseUse == 0) {
                mav.addObject(TIP, "购车用途必选！");
                return mav;
            }

            if (consumerSex == null) {
                mav.addObject(TIP, "客户性别必选！");
                return mav;
            }

            if (saleType.equals(ContextType.SALE_TYPE_AJ)) {
                if (StringUtils.isEmpty(mortgageCommissioner)) {
                    mav.addObject(TIP, "对接按揭专员必填！");
                    return mav;
                }
                if (StringUtils.isEmpty(mortgageCompany)) {
                    mav.addObject(TIP, "按揭公司必填！");
                    return mav;
                }
                if (StringUtils.isEmpty(interestRate)) {
                    mav.addObject(TIP, "利率必填！");
                    return mav;
                }
            }

            CarSaleInfo carSaleInfo = new CarSaleInfo();
            carSaleInfo.setSalePerson(salePerson);
            carSaleInfo.setSaleDate(TimeUtils.transformDateToTimetag(saleDate, TimeUtils.FORMAT_ONE));
            carSaleInfo.setSaleMoney(saleMoney);
            carSaleInfo.setAgencyFee(agencyFee == null ? 0.0 : agencyFee);
            carSaleInfo.setConsumerProperty(consumerProperty);
            carSaleInfo.setConsumerResource(consumerResource);
            carSaleInfo.setPurchaseUse(purchaseUse);
            carSaleInfo.setConsumerName(consumerName);
            carSaleInfo.setConsumerSex(consumerSex);
            carSaleInfo.setConsumerAge(consumerAge);
            carSaleInfo.setConsumerAddress(consumerAddress);
            carSaleInfo.setConsumerPhone(consumerPhone);
            carSaleInfo.setSaleType(saleType);
            carSaleInfo.setUnearnedInsurance(unearnedInsurance == null ? 0.0 : unearnedInsurance);

            mav.clear();
            if (action.equals(CREATE_ACTION)) {
                MortgageRecord mortgageRecord = null;
                if (saleType.equals(ContextType.SALE_TYPE_AJ)) {
                    mortgageRecord = new MortgageRecord();
                    mortgageRecord.setMortgageCommissioner(mortgageCommissioner);
                    mortgageRecord.setMortgageCompany(mortgageCompany);
                    mortgageRecord.setLoanFee(loanFee == null ? 0.0 : loanFee);
                    mortgageRecord.setInterestRate(interestRate);
                    mortgageRecord.setMortgageRebate(mortgageRebate == null ? 0.0 : mortgageRebate);
                    mortgageRecord.setBackFee(backFee == null ? 0.0 : backFee);
                    mortgageRecord.setAssessmentFee(assessmentFee == null ? 0.0 : assessmentFee);
                    mortgageRecord.setRiskFee(riskFee == null ? 0.0 : riskFee);
                    mortgageRecord.setRenewalFee(renewalFee == null ? 0.0 : renewalFee);
                    mortgageRecord.setPadFee(padFee == null ? 0.0 : padFee);
                    mortgageRecord.setDoorFee(doorFee == null ? 0.0 : doorFee);
                    mortgageRecord.setStampDuty(stampDuty == null ? 0.0 : stampDuty);
                    mortgageRecord.setOtherFee(otherFee == null ? 0.0 : otherFee);
                    mortgageRecord.setMortgageMoney(0.0);
                    mortgageRecord.setaMortgageMoney(0.0);
                    mortgageRecord.setIsMortgage(mortgageRecord.getMortgageMoney() <= 0 ? 0 : 1);
                    mortgageRecordService.createMortgageRecord(mortgageRecord);
                    carSaleInfo.setMortgageId(mortgageRecord.getId());

                }
                carSaleInfo.setPayMoney(CommonAction.calculatePayMoney(carSaleInfo.getSaleMoney(), carSaleInfo.getUnearnedInsurance(), mortgageRecord));

                //查看用户是否交订金
                CarRecord old = carRecordService.getCarRecordById(recordId);
                Double paidMoney = 0.0;
                if (old.getDeposit() != null && old.getDeposit() != 0.0) {
                    paidMoney = old.getDeposit();
                    CarPaidRecord carPaidRecord = new CarPaidRecord();
                    carPaidRecord.setCarRecordId(recordId);
                    carPaidRecord.setRecordStatus(ContextType.PAY_RECORD_SALE);
                    carPaidRecord.setPaidMoney(paidMoney);
                    carPaidRecord.setPaidReason("用户交付的订金");
                    carPaidRecordService.create(carPaidRecord);
                }
                carSaleInfo.setPaidMoney(paidMoney);
                carSaleInfo.setBusinessExpenseFee(0.0);
                carSaleInfo.setForceExpenseFee(0.0);
                carSaleInfo.setExpenseRebate(0.0);
                carSaleInfo.setPayCompanyFee(0.0);
                carSaleInfo.setAllUnearnedInsurance(0.0);
                carSaleInfo.setCarRecordId(recordId);
                carSaleInfoService.createCarSaleInfo(carSaleInfo);

                CarRecord carRecord = new CarRecord();
                carRecord.setId(recordId);
                carRecord.setIsSale(1);
                carRecord.setSaleId(carSaleInfo.getId());
                carRecordService.updateCarRecord(carRecord);

                //按揭更新linkID
                if(mortgageRecord!=null){
                    mortgageRecord.setIsSale(1);
                    mortgageRecord.setSaleId(carSaleInfo.getId());
                    mortgageRecordService.updateMortgageRecord(mortgageRecord);
                }

                //清缓存
                cacheCenter.deleteCarRecordInfo(recordId);

                session.setAttribute("tip", "ok 销售信息录入成功！");

            } else if (action.equals(MOD_ACTION)) {
                CarSaleInfo oldCarSaleInfo = carSaleInfoService.getCarSaleInfoById(id);
                if (oldCarSaleInfo.getSaleType().equals(ContextType.SALE_TYPE_QK) && carSaleInfo.getSaleType().equals(ContextType.SALE_TYPE_QK)) {

                } else if (oldCarSaleInfo.getSaleType().equals(ContextType.SALE_TYPE_AJ) && carSaleInfo.getSaleType().equals(ContextType.SALE_TYPE_QK)) {

                    mortgageRecordService.deleteMortgageRecordById(oldCarSaleInfo.getMortgageId());
                    carSaleInfo.setMortgageId(0);

                } else if (oldCarSaleInfo.getSaleType().equals(ContextType.SALE_TYPE_QK) && carSaleInfo.getSaleType().equals(ContextType.SALE_TYPE_AJ)) {

                    MortgageRecord mortgageRecord = new MortgageRecord();
                    mortgageRecord.setMortgageCommissioner(mortgageCommissioner);
                    mortgageRecord.setMortgageCompany(mortgageCompany);
                    mortgageRecord.setLoanFee(loanFee == null ? 0.0 : loanFee);
                    mortgageRecord.setInterestRate(interestRate);
                    mortgageRecord.setMortgageRebate(mortgageRebate == null ? 0.0 : mortgageRebate);
                    mortgageRecord.setBackFee(backFee == null ? 0.0 : backFee);
                    mortgageRecord.setAssessmentFee(assessmentFee == null ? 0.0 : assessmentFee);
                    mortgageRecord.setRiskFee(riskFee == null ? 0.0 : riskFee);
                    mortgageRecord.setRenewalFee(renewalFee == null ? 0.0 : renewalFee);
                    mortgageRecord.setPadFee(padFee == null ? 0.0 : padFee);
                    mortgageRecord.setDoorFee(doorFee == null ? 0.0 : doorFee);
                    mortgageRecord.setStampDuty(stampDuty == null ? 0.0 : stampDuty);
                    mortgageRecord.setOtherFee(otherFee == null ? 0.0 : otherFee);
                    mortgageRecord.setMortgageMoney(0.0);
                    mortgageRecord.setaMortgageMoney(0.0);
                    mortgageRecord.setIsMortgage(mortgageRecord.getMortgageMoney() <= 0 ? 0 : 1);
                    mortgageRecord.setIsSale(1);
                    mortgageRecord.setSaleId(id);
                    mortgageRecordService.createMortgageRecord(mortgageRecord);
                    carSaleInfo.setMortgageId(mortgageRecord.getId());

                } else if (oldCarSaleInfo.getSaleType().equals(ContextType.SALE_TYPE_AJ) && carSaleInfo.getSaleType().equals(ContextType.SALE_TYPE_AJ)) {

                    MortgageRecord mortgageRecord = mortgageRecordService.getMortgageRecordById(oldCarSaleInfo.getMortgageId());
                    mortgageRecord.setMortgageCommissioner(mortgageCommissioner);
                    mortgageRecord.setMortgageCompany(mortgageCompany);
                    mortgageRecord.setLoanFee(loanFee == null ? 0.0 : loanFee);
                    mortgageRecord.setInterestRate(interestRate);
                    mortgageRecord.setMortgageRebate(mortgageRebate == null ? 0.0 : mortgageRebate);
                    mortgageRecord.setBackFee(backFee == null ? 0.0 : backFee);
                    mortgageRecord.setAssessmentFee(assessmentFee == null ? 0.0 : assessmentFee);
                    mortgageRecord.setRiskFee(riskFee == null ? 0.0 : riskFee);
                    mortgageRecord.setRenewalFee(renewalFee == null ? 0.0 : renewalFee);
                    mortgageRecord.setPadFee(padFee == null ? 0.0 : padFee);
                    mortgageRecord.setDoorFee(doorFee == null ? 0.0 : doorFee);
                    mortgageRecord.setStampDuty(stampDuty == null ? 0.0 : stampDuty);
                    mortgageRecord.setOtherFee(otherFee == null ? 0.0 : otherFee);
                    mortgageRecord.setMortgageMoney(0.0);
                    mortgageRecord.setaMortgageMoney(0.0);
                    mortgageRecord.setIsMortgage(mortgageRecord.getMortgageMoney() <= 0 ? 0 : 1);
                    mortgageRecordService.updateMortgageRecord(mortgageRecord);

                    //按揭到按揭，把按揭表ID插入新carSaleInfo中
                    carSaleInfo.setMortgageId(mortgageRecord.getId());
                }
                carSaleInfo.setId(id);

                carSaleInfo.setPayMoney(CommonAction.calculatePayMoney(carSaleInfo.getSaleMoney(),
                        oldCarSaleInfo.getAllUnearnedInsurance() == 0 ? carSaleInfo.getUnearnedInsurance() : oldCarSaleInfo.getAllUnearnedInsurance(),
                        carSaleInfo.getMortgageId() == null ? null : mortgageRecordService.getMortgageRecordById(carSaleInfo.getMortgageId())));
                if (carSaleInfo.getPayMoney() < oldCarSaleInfo.getPaidMoney()) {
                    carSaleInfo.setPaidMoney(carSaleInfo.getPayMoney());
                }
                if (oldCarSaleInfo.getPaidMoney() < 0) {
                    carSaleInfo.setPaidMoney(0.0);
                }
                carSaleInfoService.updateCarSaleInfo(carSaleInfo);

                //清缓存
                cacheCenter.deleteCarRecordInfo(oldCarSaleInfo.getCarRecordId());

                session.setAttribute("tip", "ok 销售信息修改成功！");
            }
            mav.setViewName("redirect:/carSaleView");
            return mav;
        }
    }

    //车辆保险信息相关操作
    @RequestMapping(value = "/carExpenseAction", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView carExpenseAction(
            @RequestParam(value = "action", required = true, defaultValue = "") Integer action,
            @RequestParam(value = "over", required = false, defaultValue = "") Integer over,
            @RequestParam(value = "id", required = false, defaultValue = "") Integer id,
            @RequestParam(value = "expenseCompany", required = false, defaultValue = "") String expenseCompany,
            @RequestParam(value = "businessExpenseFee", required = false, defaultValue = "") Double businessExpenseFee,
            @RequestParam(value = "forceExpenseFee", required = false, defaultValue = "") Double forceExpenseFee,
            @RequestParam(value = "expenseRebate", required = false, defaultValue = "") Double expenseRebate,
            @RequestParam(value = "allUnearnedInsurance", required = false, defaultValue = "") Double allUnearnedInsurance,
            @RequestParam(value = "payCompanyFee", required = false, defaultValue = "") Double payCompanyFee,
            HttpSession session) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("car/expenseAdd");
        mav.addObject("action", action);

        mav.addObject("expenseCompany", expenseCompany);
        mav.addObject("businessExpenseFee", businessExpenseFee);
        mav.addObject("forceExpenseFee", forceExpenseFee);
        mav.addObject("expenseRebate", expenseRebate);
        mav.addObject("allUnearnedInsurance", allUnearnedInsurance);
        mav.addObject("payCompanyFee", payCompanyFee);

        if (over == null) {
            if (id != null) {
                CarSaleInfo carSaleInfo = carSaleInfoService.getCarSaleInfoById(id);
                mav.addObject("id", id);

                mav.addObject("expenseCompany", carSaleInfo.getExpenseCompany());
                mav.addObject("businessExpenseFee", carSaleInfo.getBusinessExpenseFee());
                mav.addObject("forceExpenseFee", carSaleInfo.getForceExpenseFee());
                mav.addObject("expenseRebate", carSaleInfo.getExpenseRebate());
                mav.addObject("allUnearnedInsurance", carSaleInfo.getAllUnearnedInsurance());
                mav.addObject("payCompanyFee", carSaleInfo.getPayCompanyFee());
            }
            return mav;
        } else {
            if (StringUtils.isEmpty(expenseCompany)) {
                mav.addObject(TIP, "保险公司必填");
                return mav;
            }

            CarSaleInfo carSaleInfo = new CarSaleInfo();
            carSaleInfo.setId(id);
            carSaleInfo.setExpenseCompany(expenseCompany);
            carSaleInfo.setBusinessExpenseFee(businessExpenseFee == null ? 0.0 : businessExpenseFee);
            carSaleInfo.setForceExpenseFee(forceExpenseFee == null ? 0.0 : forceExpenseFee);
            carSaleInfo.setExpenseRebate(expenseRebate == null ? 0.0 : expenseRebate);
            carSaleInfo.setAllUnearnedInsurance(allUnearnedInsurance == null ? 0.0 : allUnearnedInsurance);
            carSaleInfo.setPayCompanyFee(payCompanyFee == null ? 0.0 : payCompanyFee);

            CarSaleInfo old = carSaleInfoService.getCarSaleInfoById(id);

            Double payMoney = CommonAction.calculatePayMoney(old.getSaleMoney(), allUnearnedInsurance,
                    old.getMortgageId() == null ? null : mortgageRecordService.getMortgageRecordById(old.getMortgageId()));
            carSaleInfo.setPayMoney(payMoney);
            //保险处理
            Double d = payMoney - old.getPaidMoney();
            if (d < 0) {
                carSaleInfo.setPaidMoney(payMoney);
            }
            if (old.getPaidMoney() < 0) {
                carSaleInfo.setPaidMoney(0.0);
            }

            //修改总保险金额
            List<CarPayMoneyAssist> list = carPayMoneyAssistService.getCarPayMoneyAssistListByLinkId(id);
            if (list == null || list.size() == 0) {
                CarPayMoneyAssist carPayMoneyAssist = new CarPayMoneyAssist();
                carPayMoneyAssist.setCarSaleInfoId(id);
                carPayMoneyAssist.setOldPayMoney(old.getUnearnedInsurance());
                carPayMoneyAssist.setNewPayMoney(allUnearnedInsurance);
                carPayMoneyAssist.setModReason("");
                carPayMoneyAssist.setDifference(d);
                carPayMoneyAssistService.createCarPayMoneyAssist(carPayMoneyAssist);
            } else {
                if (action.equals(MOD_ACTION)) {
                    CarPayMoneyAssist update = new CarPayMoneyAssist();
                    update.setId(list.get(0).getId());
                    update.setNewPayMoney(allUnearnedInsurance);
                    carPayMoneyAssistService.updateCarPayMoneyAssist(update);
                }
            }

            mav.clear();
            if (action.equals(CREATE_ACTION)) {
                session.setAttribute("tip", "ok 保险信息录入成功！");
            } else if (action.equals(MOD_ACTION)) {
                session.setAttribute("tip", "ok 保险信息修改成功！");
            }
            carSaleInfoService.updateCarSaleInfo(carSaleInfo);

            //清缓存
            cacheCenter.deleteCarRecordInfo(old.getCarRecordId());

            mav.setViewName("redirect:/carSaleView");
            return mav;
        }
    }

    //车辆销售成本录入相关操作
    @RequestMapping(value = "/carSfAction", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView carSfAction(
            @RequestParam(value = "action", required = true, defaultValue = "") Integer action,
            @RequestParam(value = "over", required = false, defaultValue = "") Integer over,
            @RequestParam(value = "id", required = false, defaultValue = "") Integer id,
            @RequestParam(value = "recordId", required = false, defaultValue = "") Integer recordId,
            @RequestParam(value = "transferFee", required = false, defaultValue = "") Double transferFee,
            @RequestParam(value = "transferSubsidy", required = false, defaultValue = "") Double transferSubsidy,
            @RequestParam(value = "transferCrossingFee", required = false, defaultValue = "") Double transferCrossingFee,
            @RequestParam(value = "transferBillingFee", required = false, defaultValue = "") Double transferBillingFee,
            @RequestParam(value = "transferOilFee", required = false, defaultValue = "") Double transferOilFee,
            @RequestParam(value = "rubbing", required = false, defaultValue = "") Double rubbing,
            @RequestParam(value = "removeCard", required = false, defaultValue = "") Double removeCard,
            @RequestParam(value = "cattleFee", required = false, defaultValue = "") Double cattleFee,
            @RequestParam(value = "isProduce", required = false, defaultValue = "") Integer isProduce,
            HttpSession session) {

        ModelAndView mav = new ModelAndView();
        mav.setViewName("car/sfAdd");
        mav.addObject("action", action);
        mav.addObject("recordId", recordId);

        mav.addObject("transferFee", transferFee == null ? 0.0 : transferFee);
        mav.addObject("transferSubsidy", transferSubsidy == null ? 0.0 : transferSubsidy);
        mav.addObject("transferCrossingFee", transferCrossingFee == null ? 0.0 : transferCrossingFee);
        mav.addObject("transferBillingFee", transferBillingFee == null ? 0.0 : transferBillingFee);
        mav.addObject("transferOilFee", transferOilFee == null ? 0.0 : transferOilFee);
        mav.addObject("rubbing", rubbing == null ? 0.0 : rubbing);
        mav.addObject("removeCard", removeCard == null ? 0.0 : removeCard);
        mav.addObject("cattleFee", cattleFee == null ? 0.0 : cattleFee);
        mav.addObject("isProduce", isProduce);

        if (over == null) {
            if (id != null) {
                CarSf carSf = carSfService.getCarSfById(id);
                mav.addObject("id", id);

                mav.addObject("transferFee", carSf.getTransferFee());
                mav.addObject("transferSubsidy", carSf.getTransferSubsidy());
                mav.addObject("transferCrossingFee", carSf.getTransferCrossingFee());
                mav.addObject("transferBillingFee", carSf.getTransferBillingFee());
                mav.addObject("transferOilFee", carSf.getTransferOilFee());
                mav.addObject("rubbing", carSf.getRubbing());
                mav.addObject("removeCard", carSf.getRemoveCard());
                mav.addObject("cattleFee", carSf.getCattleFee());
                mav.addObject("isProduce", carSf.getIsProduce());
            }
            return mav;
        } else {
            CarSf carSf = new CarSf();
            carSf.setTransferFee(transferFee);
            carSf.setTransferSubsidy(transferSubsidy);
            carSf.setTransferCrossingFee(transferCrossingFee);
            carSf.setTransferBillingFee(transferBillingFee);
            carSf.setTransferOilFee(transferOilFee);
            carSf.setRubbing(rubbing);
            carSf.setRemoveCard(removeCard);
            carSf.setCattleFee(cattleFee);
            carSf.setIsProduce(isProduce == null ? 0 : isProduce);

            mav.clear();
            if (action.equals(CREATE_ACTION)) {
                carSf.setCarRecordId(recordId);
                carSf.setSaleFee(0.0);
                carSf.setServiceFee(0.0);
                carSfService.createCarSf(carSf);
                session.setAttribute("tip", "ok 车辆销售成本录入成功！");

                //绑定成本录入ID
                CarRecord carRecord = new CarRecord();
                carRecord.setId(recordId);
                carRecord.setIsSf(1);
                carRecord.setSfId(carSf.getId());
                carRecordService.updateCarRecord(carRecord);
            } else if (action.equals(MOD_ACTION)) {
                carSf.setId(id);
                carSfService.updateCarSf(carSf);

                //刷新缓存
                cacheCenter.deleteCarRecordInfo(recordId);

                session.setAttribute("tip", "ok 车辆销售成本修改成功！");
            }
            mav.setViewName("redirect:/carSaleView");
            return mav;
        }
    }

    //设置转已售时间
    @RequestMapping(value = "/carSoldDate", method = RequestMethod.GET)
    public ModelAndView carSoldDate(
            @RequestParam(value = "soldDate", required = false, defaultValue = "") String soldDate,
            @RequestParam(value = "id", required = false, defaultValue = "") Integer id,
            HttpSession session) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:/carSaleView");

        if (StringUtils.isEmpty(soldDate)) {
            session.setAttribute("tip", "日期必选！");
            return mav;
        }
        CarRecord carRecord = carRecordService.getCarRecordById(id);
        CarRecord update = new CarRecord();
        update.setId(id);
        update.setSoldDate(TimeUtils.transformDateToTimetag(soldDate, TimeUtils.FORMAT_ONE));
        carRecordService.updateCarRecord(update);
        session.setAttribute("tip", "ok 日期更新成功！");

        //判断付款情况
        CarSaleInfo carSaleInfo = carSaleInfoService.getCarSaleInfoById(carRecord.getSaleId());

        //说明销售信息未填
        if (carSaleInfo == null) {
            return mav;
        }
        if (carSaleInfo.getPayMoney().equals(carSaleInfo.getPaidMoney())) {
            //判断销售成本是否录入
            if (carRecord.getIsSf() == 0) {
                session.setAttribute("tip", "销售成本未填写！");
                return mav;
            }

            //改变订单状态
            changeCarStatus(carSaleInfo);
            session.setAttribute("tip", "ok 车辆达到标准，转已售！");
        }
        return mav;
    }

    //在售车辆收帐
    @RequestMapping(value = "/carSalePaid", method = RequestMethod.GET)
    public ModelAndView carSalePaid(
            @RequestParam(value = "goonPaid", required = false, defaultValue = "") Double goonPaid,
            @RequestParam(value = "paidReason", required = false, defaultValue = "") String paidReason,
            @RequestParam(value = "id", required = false, defaultValue = "") Integer id,
            HttpSession session) {
        if (goonPaid == null) {
            goonPaid = 0.0;
        }
        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:/carSaleView");

        CarSaleInfo carSaleInfo = carSaleInfoService.getCarSaleInfoById(id);
        Double paidMoney = carSaleInfo.getPaidMoney() + goonPaid;

        CarSaleInfo update = new CarSaleInfo();
        update.setId(id);
        update.setPaidMoney(paidMoney);

        //判断已付金额是否达标
        if (paidMoney > carSaleInfo.getPayMoney()) {
            session.setAttribute("tip", "付款超限！");
            return mav;
        }

        //创建付款记录
        if (goonPaid != 0.0) {
            CarPaidRecord carPaidRecord = new CarPaidRecord();
            carPaidRecord.setCarRecordId(carSaleInfo.getCarRecordId());
            carPaidRecord.setRecordStatus(ContextType.PAY_RECORD_SALE);
            carPaidRecord.setPaidMoney(goonPaid);
            carPaidRecord.setPaidReason(paidReason);
            carPaidRecordService.create(carPaidRecord);
        }

        update.setUtime(System.currentTimeMillis());
        carSaleInfoService.updateCarSaleInfo(update);

        session.setAttribute("tip", "ok 付款成功！");

        //清缓存
        cacheCenter.deleteCarRecordInfo(carSaleInfo.getCarRecordId());

        //判断转已售条件
        boolean isSaleAble = paidMoney.equals(carSaleInfo.getPayMoney());
        if (isSaleAble) {
            //判断销售成本是否录入
            CarRecord carRecord = carRecordService.getCarRecordById(carSaleInfo.getCarRecordId());
            if (carRecord.getIsSf() == 0) {
                session.setAttribute("tip", "付款成功，销售成本未填写，不能转已售！");
                return mav;
            }
            if (carRecord.getSoldDate() == 0) {
                session.setAttribute("tip", "付款成功，转已售日期未填写，不能转已售！");
                return mav;
            }
            changeCarStatus(carSaleInfo);
            session.setAttribute("tip", "ok 付款成功，车辆达到标准，转已售！");
        }

        return mav;
    }

    //删除收帐记录
    @RequestMapping(value = "/carSaleDelete", method = RequestMethod.GET)
    public ModelAndView carSaleDelete(
            @RequestParam(value = "id", required = false, defaultValue = "") Integer id,
            @RequestParam(value = "carSaleInfoId", required = false, defaultValue = "") Integer carSaleInfoId,
            HttpSession session) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:/carSaleView");
        CarPaidRecord carPaidRecord = carPaidRecordService.getCarPaidRecordById(id);

        CarSaleInfo carSaleInfo = carSaleInfoService.getCarSaleInfoById(carSaleInfoId);

        if (carPaidRecord != null) {
            CarSaleInfo update = new CarSaleInfo();
            update.setId(carSaleInfoId);
            update.setPaidMoney(carSaleInfo.getPaidMoney() - carPaidRecord.getPaidMoney());
            carSaleInfoService.updateCarSaleInfo(update);

            carPaidRecordService.deleteCarPaidRecordById(id);
        }

        //清缓存
        cacheCenter.deleteCarRecordInfo(carSaleInfo.getCarRecordId());

        session.setAttribute("tip", "ok 付款记录删除成功！");
        return mav;
    }

    //添加放款信息
    @RequestMapping(value = "/mortgageCarPaid", method = RequestMethod.GET)
    public ModelAndView mortgageCarPaid(
            @RequestParam(value = "goonPaid", required = false, defaultValue = "") Double goonPaid,
            @RequestParam(value = "paidReason", required = false, defaultValue = "") String paidReason,
            @RequestParam(value = "id", required = false, defaultValue = "") Integer id,
            HttpSession session) {
        if (goonPaid == null) {
            goonPaid = 0.0;
        }
        ModelAndView mav = new ModelAndView();
        MortgageRecord mortgageLog = mortgageRecordService.getMortgageRecordById(id);
        Double paidMoney = mortgageLog.getMortgageMoney() + goonPaid;
        mav.setViewName("redirect:/carSaleView");

        MortgageRecord update = new MortgageRecord();
        update.setId(id);
        update.setMortgageMoney(paidMoney);
        mortgageRecordService.updateMortgageRecord(update);

        session.setAttribute("tip", "放款成功");

        Integer cid = carSaleInfoService.getCarSaleInfoById(mortgageLog.getSaleId()).getCarRecordId();
        //创建放款记录
        CarPaidRecord carPaidRecord = new CarPaidRecord();
        carPaidRecord.setCarRecordId(cid);
        carPaidRecord.setRecordStatus(ContextType.PAY_RECORD_MORTGAGE);
        carPaidRecord.setPaidMoney(goonPaid);
        carPaidRecord.setPaidReason(paidReason);
        carPaidRecordService.create(carPaidRecord);

        //清缓存
        cacheCenter.deleteCarRecordInfo(cid);
        return mav;
    }

    @RequestMapping(value = "/carPurchaseDelete", method = RequestMethod.GET)
    public ModelAndView carPurchaseDelete(
            @RequestParam(value = "id", required = false, defaultValue = "") Integer id,
            @RequestParam(value = "recordStatus", required = false, defaultValue = "") Integer recordStatus,
            HttpSession session) {
        ModelAndView mav = new ModelAndView();
        if (recordStatus.equals(ContextType.RECORD_STATUS_PURCHASE)) {
            mav.setViewName("redirect:/carPurchaseView");
        } else if (recordStatus.equals(ContextType.RECORD_STATUS_STOCK)) {
            mav.setViewName("redirect:/carStockView");
        } else if (recordStatus.equals(ContextType.RECORD_STATUS_SALE)) {
            mav.setViewName("redirect:/carSaleView");
        } else if (recordStatus.equals(ContextType.RECORD_STATUS_SOLD)) {
            mav.setViewName("redirect:/carSoldView");
        }

        CarRecord carRecord = carRecordService.getCarRecordById(id);
        //批量采购操作
        if (carRecord.getIsBath() == 1) {
            carBathService.modCarRecordId(carRecord.getBathId(), carRecord.getId(), 0);
        }

        //删除成本信息
        if (carRecord.getIsCost() == 1) {
            CarCost carCost = carCostService.getCarCostById(carRecord.getCostId());
            if (carCost.getPreSaleFee() != null && carCost.getPreSaleFee() != 0) {
                carSaleSetupService.deleteCarSaleSetupByIdAndType(carCost.getId(), ContextType.PRE_SETUP_TYPE);
            }
            if (carCost.getAfterSaleFee() != null && carCost.getAfterSaleFee() != 0) {
                carSaleSetupService.deleteCarSaleSetupByIdAndType(carCost.getId(), ContextType.AFTER_SETUP_TYPE);
            }
            if (carCost.getOtherIncomeFee() != null && carCost.getOtherIncomeFee() != 0) {
                carSaleSetupService.deleteCarSaleSetupByIdAndType(carCost.getId(), ContextType.OTHER_INCOME);
            }
            carCostService.deleteCarCostById(carRecord.getCostId());
        }

        //删除销售信息
        if (carRecord.getIsSale() == 1) {
            CarSaleInfo carSaleInfo = carSaleInfoService.getCarSaleInfoById(carRecord.getSaleId());
            if (carSaleInfo.getSaleType().equals(ContextType.SALE_TYPE_AJ)) {
                mortgageRecordService.deleteMortgageRecordById(carSaleInfo.getMortgageId());
            }

            carSaleInfoService.deleteCarSaleInfoById(carSaleInfo.getId());

            carPayMoneyAssistService.deleteCarPayMoneyAssistByLinkId(carSaleInfo.getId());
        }

        //删除销售成本信息
        if (carRecord.getIsSf() == 1) {
            CarSf carSf = carSfService.getCarSfById(carRecord.getSfId());
            if (carSf.getSaleFee() != null && carSf.getSaleFee() != 0) {
                carSaleSetupService.deleteCarSaleSetupByIdAndType(carSf.getId(), ContextType.SALE_TYPE);
            }
            carSfService.deleteCarSfById(carRecord.getSfId());
        }

        //删除工资辅助记录表
        wagesAssistService.deleteWagesAssistByCid(carRecord.getId());

        //删除各付款记录表
        carPaidRecordService.deleteCarPaidRecordByLinkIdAndType(carRecord.getId(), ContextType.PAY_RECORD_PURCHASE);
        carPaidRecordService.deleteCarPaidRecordByLinkIdAndType(carRecord.getId(), ContextType.PAY_RECORD_SALE);
        carPaidRecordService.deleteCarPaidRecordByLinkIdAndType(carRecord.getId(), ContextType.PAY_RECORD_MORTGAGE);
        carPaidRecordService.deleteCarPaidRecordByLinkIdAndType(carRecord.getId(), ContextType.PAY_RECORD_BACK);
        carPaidRecordService.deleteCarPaidRecordByLinkIdAndType(carRecord.getId(), ContextType.PAY_RECORD_DEPOSIT);

        //删除档案信息
        carDossierService.deleteCarDossierByCid(carRecord.getId());

        carRecordService.deleteCarRecordById(id);
        session.setAttribute("tip", "ok 采购记录删除成功！");
        return mav;
    }

    @RequestMapping(value = "/carSearchPurchase", method = RequestMethod.GET)
    public ModelAndView carSearchPurchase(
            @RequestParam(value = "recordStatus", required = false, defaultValue = "") Integer recordStatus,
            @RequestParam(value = "searchKey", required = false, defaultValue = "") String searchKey,
            @RequestParam(value = "searchValue", required = false, defaultValue = "") String searchValue,
            @RequestParam(value = "btime", required = false, defaultValue = "") String btime,
            @RequestParam(value = "etime", required = false, defaultValue = "") String etime,
            @RequestParam(value = "zbtime", required = false, defaultValue = "") String zbtime,
            @RequestParam(value = "zetime", required = false, defaultValue = "") String zetime,
            HttpSession session) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("export", 1);
        mav.addObject("recordStatus", recordStatus);
        mav.addObject("searchKey", searchKey);
        mav.addObject("searchValue", searchValue);
        mav.addObject("btime", btime);
        mav.addObject("etime", etime);
        mav.addObject("zbtime", zbtime);
        mav.addObject("zetime", zetime);

        List<CarRecord> list = carRecordService.searchCarRecord(recordStatus, searchKey, searchValue, btime, etime, zbtime, zetime);
        mav.addObject("carNum", list.size());

        if (recordStatus.equals(ContextType.RECORD_STATUS_PURCHASE)) {
            mav.addObject("list", cacheCenter.getCarRecordInfo(list));
            mav.setViewName("car/purchaseList");
        } else if (recordStatus.equals(ContextType.RECORD_STATUS_STOCK)) {
            mav.addObject("list", cacheCenter.getCarRecordCombinationInfo(list));
            mav.setViewName("car/stockList");
        } else if (recordStatus.equals(ContextType.RECORD_STATUS_SALE)) {
            mav.addObject("list", cacheCenter.getCarRecordCombinationInfo(list));
            mav.setViewName("car/saleList");
        } else if (recordStatus.equals(ContextType.RECORD_STATUS_SOLD)) {
            List<ContextBean> contextBeans = cacheCenter.getCarRecordCombinationInfo(list);

            cacheCenter.setContextBeanList(contextBeans);
            mav.addObject("list", contextBeans);
            mav.setViewName("car/soldList");
        }
        return mav;
    }

    @RequestMapping(value = "/carStatusChange", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView carStatusChange(@RequestParam(value = "changeType", required = true, defaultValue = "") Integer changeType,
                                        @RequestParam(value = "deposit", required = false, defaultValue = "") Double deposit,
                                        @RequestParam(value = "id", required = false, defaultValue = "") Integer id,
                                        @RequestParam(value = "backMoney", required = false, defaultValue = "") Double backMoney,
                                        @RequestParam(value = "backDesc", required = false, defaultValue = "") String backDesc,
                                        HttpSession session) {
        ModelAndView mav = new ModelAndView();

        CarRecord old = carRecordService.getCarRecordById(id);
        CarRecord carRecord = new CarRecord();
        carRecord.setId(id);

        if (changeType.equals(ContextType.CAR_STATUS_FROM_STOCK_TO_SALE)) {
            mav.setViewName("redirect:/carStockView");

            if (old.getIsCost() == 0) {
                session.setAttribute("tip", "成本未录入，无法转销售！");
                return mav;
            }
            carRecord.setRecordStatus(ContextType.RECORD_STATUS_SALE);
            Double oldDeposit = old.getDeposit() == null ? 0.0 : old.getDeposit();
            carRecord.setDeposit(deposit == null ? 0.0 : deposit + oldDeposit);
        } else if (changeType.equals(ContextType.CAR_STATUS_FROM_SALE_TO_STOCK)) {
            mav.setViewName("redirect:/carSaleView");
            if (backMoney == null) {
                backMoney = 0.0;
            }
            double d = old.getDeposit() - backMoney;
            if (d < 0) {
                session.setAttribute("tip", "退款金额不能大于订金！");
                return mav;
            } else if (d == 0) {
                //清空在售页面填入的信息
                if (old.getIsSale() == 1) {
                    CarSaleInfo carSaleInfo = carSaleInfoService.getCarSaleInfoById(old.getSaleId());

                    //删除付款记录
                    carPaidRecordService.deleteCarPaidRecordByLinkIdAndType(carSaleInfo.getId(), ContextType.PAY_RECORD_SALE);

                    //删除按揭记录
                    if (carSaleInfo.getSaleType().equals(ContextType.SALE_TYPE_AJ)) {
                        carPaidRecordService.deleteCarPaidRecordByLinkIdAndType(carSaleInfo.getMortgageId(), ContextType.PAY_RECORD_MORTGAGE);
                    }
                    carSaleInfoService.deleteCarSaleInfoById(old.getSaleId());
                    carRecord.setIsSale(0);
                    carRecord.setSaleId(0);
                }

                if (old.getIsSf() == 1) {
                    carSaleSetupService.deleteCarSaleSetupByIdAndType(old.getSfId(), ContextType.SALE_TYPE);
                    carSfService.deleteCarSfById(old.getSfId());

                    carRecord.setIsSf(0);
                    carRecord.setSfId(0);
                }
                carRecord.setSoldDate(0L);
            }
            carRecord.setRecordStatus(ContextType.RECORD_STATUS_STOCK);
            carRecord.setDeposit(d);

            //添加退订记录
            CarPaidRecord carPaidRecord = new CarPaidRecord();
            carPaidRecord.setCarRecordId(id);
            carPaidRecord.setRecordStatus(ContextType.PAY_RECORD_BACK);
            carPaidRecord.setPaidMoney(backMoney);
            carPaidRecord.setPaidReason(backDesc == null ? "" : backDesc);
            carPaidRecordService.create(carPaidRecord);

        } else if (changeType.equals(ContextType.CAR_STATUS_FROM_SOLD_TO_SALE)) {
            mav.setViewName("redirect:/carSoldView");
            carRecord.setRecordStatus(ContextType.RECORD_STATUS_SALE);
        }
        carRecordService.updateCarRecord(carRecord);
        session.setAttribute("tip", "ok 车辆订单状态修改成功！");
        return mav;
    }

    private void changeCarStatus(CarSaleInfo carSaleInfo) {
        CarRecord old = carRecordService.getCarRecordById(carSaleInfo.getCarRecordId());
        CarRecord carRecord = new CarRecord();
        carRecord.setId(carSaleInfo.getCarRecordId());
        carRecord.setRecordStatus(ContextType.RECORD_STATUS_SOLD);

        //更新服务基金费
        CarSf carSf = carSfService.getCarSfById(old.getSfId());
        carSf.setServiceFee(0.0);
        if (carSf.getIsProduce() == 1) {
            carSf.setServiceFee(ContextType.SERVICE_MONEY);
        }
        carSfService.updateCarSf(carSf);

        CarCost carCost = carCostService.getCarCostById(old.getCostId());

        //计算车辆毛利润
        Double ajMoney = 0.0;
        if (carSaleInfo.getSaleType().equals(ContextType.SALE_TYPE_AJ)) {
            MortgageRecord mortgageRecord = mortgageRecordService.getMortgageRecordById(carSaleInfo.getMortgageId());
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
            MortgageRecord mortgageRecord = mortgageRecordService.getMortgageRecordById(carSaleInfo.getMortgageId());
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

        Account account = accountService.getAccountByRealName(old.getPurchasePerson());
        Double purchaseCommission = 0.0;
        if (account != null) {
            purchaseCommission = Utils.saveTwoSeat((Double.valueOf(carRecord.getGrossProfit()) - carCommission) * account.getPurchaseCommission() / 100);
        }

        Double shareDividends = 0.0;
        if (!StringUtils.isEmpty(old.getInsideProportion())) {
            shareDividends = Utils.saveTwoSeat((Double.valueOf(carRecord.getGrossProfit()) - carCommission - purchaseCommission) * (Double.valueOf(old.getInsideProportion())));
        }

        //计算工资提成相关
        boolean needCreate = false;
        WagesAssist wagesAssist = wagesAssistService.getWagesAssistByCid(carRecord.getId());
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
            wagesAssistService.createWagesAssist(wagesAssist);
        } else {
            wagesAssistService.updateWagesAssist(wagesAssist);
        }
        carRecord.setExpenseId(wagesAssist.getId());
        carRecordService.updateCarRecord(carRecord);
    }
}
