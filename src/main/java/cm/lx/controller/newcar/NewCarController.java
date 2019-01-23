package cm.lx.controller.newcar;

import cm.lx.business.CacheCenter;
import cm.lx.business.ToolUtil;
import cm.lx.common.ContextType;
import cm.lx.controller.BaseController;
import cm.lx.bean.entity.CarPaidRecord;
import cm.lx.bean.entity.MortgageRecord;
import cm.lx.bean.entity.NewCar;
import cm.lx.service.CarPaidRecordService;
import cm.lx.service.CarSaleSetupService;
import cm.lx.service.MortgageRecordService;
import cm.lx.service.NewCarService;
import cm.lx.util.TimeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by linxingwei on 2018/1/11.
 */
@Controller
public class NewCarController extends BaseController {

    @Resource
    CacheCenter cacheCenter;

    @Resource
    NewCarService newCarService;

    @Resource
    CarSaleSetupService carSaleSetupService;

    @Resource
    MortgageRecordService mortgageRecordService;

    @Resource
    CarPaidRecordService carPaidRecordService;

    private List<NewCar> dealList(List<NewCar> list) {
        for (NewCar newCar : list) {
            newCar.setStrSaleDate(TimeUtils.transformTimetagToDate(newCar.getSaleDate(), TimeUtils.FORMAT_ONE));

            if (newCar.getConsumerProperty() != 0) {
                newCar.setStrConsumerProperty(cacheCenter.getCarPropertyById(newCar.getConsumerProperty()).getPropertyValue());
            }
            if (newCar.getConsumerResource() != 0) {
                newCar.setStrConsumerResource(cacheCenter.getCarPropertyById(newCar.getConsumerResource()).getPropertyValue());
            }

            if (newCar.getPurchaseUse() != 0) {
                newCar.setStrPurchaseUse(cacheCenter.getCarPropertyById(newCar.getPurchaseUse()).getPropertyValue());
            }

            if (newCar.getConsumerAge() != 0) {
                newCar.setStrConsumerAge(cacheCenter.getCarPropertyById(newCar.getConsumerAge()).getPropertyValue());
            }

            if (newCar.getSaleType().equals(ContextType.SALE_TYPE_AJ)) {
                newCar.setMortgageRecord(mortgageRecordService.getMortgageRecordById(newCar.getMortgageId()));
            }

            if (newCar.getOtherCost() != 0) {
                newCar.setOtherCostList(carSaleSetupService.getCarSaleSetupByIdAndType(newCar.getId(), ContextType.NEW_CAR_COST_TYPE));
            }

            if (newCar.getOtherIncome() != 0) {
                newCar.setOtherIncomeList(carSaleSetupService.getCarSaleSetupByIdAndType(newCar.getId(), ContextType.NEW_CAR_INCOME_TYPE));
            }
        }
        return list;
    }

    @RequestMapping(value = "/newCarView")
    public ModelAndView newCarView() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("newCar/list");
        mav.addObject(DATA, dealList(newCarService.getNewCarListByStatus(ContextType.NEW_CAR_SALE)));
        return mav;
    }

    @RequestMapping(value = "/newCarAction", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView newCarAction(
            @RequestParam(value = "action", required = true, defaultValue = "") Integer action,
            @RequestParam(value = "over", required = false, defaultValue = "") Integer over,
            @RequestParam(value = "id", required = false, defaultValue = "") Integer id,
            @RequestParam(value = "carBrand", required = false, defaultValue = "") String carBrand,
            @RequestParam(value = "carModel", required = false, defaultValue = "") String carModel,
            @RequestParam(value = "carConfig", required = false, defaultValue = "") String carConfig,
            @RequestParam(value = "guidancePrice", required = false, defaultValue = "") Double guidancePrice,
            @RequestParam(value = "purchasePerson", required = false, defaultValue = "") String purchasePerson,
            @RequestParam(value = "purchaseMoney", required = false, defaultValue = "") Double purchaseMoney,
            @RequestParam(value = "salePerson", required = false, defaultValue = "") String salePerson,
            @RequestParam(value = "saleDate", required = false, defaultValue = "") String saleDate,
            @RequestParam(value = "saleMoney", required = false, defaultValue = "") Double saleMoney,
            @RequestParam(value = "expenseCompany", required = false, defaultValue = "") String expenseCompany,
            @RequestParam(value = "businessExpenseFee", required = false, defaultValue = "") Double businessExpenseFee,
            @RequestParam(value = "forceExpenseFee", required = false, defaultValue = "") Double forceExpenseFee,
            @RequestParam(value = "expenseRebate", required = false, defaultValue = "") String expenseRebate,
            @RequestParam(value = "agencyFee", required = false, defaultValue = "") Double agencyFee,
            @RequestParam(value = "payMoney", required = false, defaultValue = "") Double payMoney,
            @RequestParam(value = "paidMoney", required = false, defaultValue = "") Double paidMoney,
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
            @RequestParam(value = "mortgageMoney", required = false, defaultValue = "") Double mortgageMoney,
            @RequestParam(value = "aMortgageMoney", required = false, defaultValue = "") Double aMortgageMoney) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("newCar/add");
        mav.addObject("action", action);
        ToolUtil.initCarPropertyData(mav, cacheCenter);

        mav.addObject("carBrand", carBrand);
        mav.addObject("carModel", carModel);
        mav.addObject("carConfig", carConfig);
        mav.addObject("guidancePrice", guidancePrice);
        mav.addObject("purchasePerson", purchasePerson);
        mav.addObject("purchaseMoney", purchaseMoney);

        mav.addObject("salePerson", salePerson);
        mav.addObject("saleDate", saleDate);
        mav.addObject("saleMoney", saleMoney);
        mav.addObject("salePerson", salePerson);
        mav.addObject("salePerson", salePerson);
        mav.addObject("expenseCompany", expenseCompany);
        mav.addObject("businessExpenseFee", businessExpenseFee);
        mav.addObject("forceExpenseFee", forceExpenseFee);
        mav.addObject("expenseRebate", expenseRebate);
        mav.addObject("agencyFee", agencyFee);
        mav.addObject("payMoney", payMoney);
        mav.addObject("paidMoney", paidMoney);
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
        mav.addObject("mortgageMoney", mortgageMoney);
        mav.addObject("aMortgageMoney", aMortgageMoney);

        if (over == null) {
            if (id != null) {
                NewCar newCar = newCarService.getNewCarById(id);
                mav.addObject("id", id);
                mav.addObject("carBrand", newCar.getCarBrand());
                mav.addObject("carModel", newCar.getCarModel());
                mav.addObject("carConfig", newCar.getCarConfig());
                mav.addObject("guidancePrice", newCar.getGuidancePrice());
                mav.addObject("purchasePerson", newCar.getPurchasePerson());
                mav.addObject("purchaseMoney", newCar.getPurchaseMoney());
                mav.addObject("salePerson", newCar.getSalePerson());
                mav.addObject("saleDate", TimeUtils.transformTimetagToDate(newCar.getSaleDate(), TimeUtils.FORMAT_ONE));
                mav.addObject("saleMoney", newCar.getSaleMoney());
                mav.addObject("expenseCompany", newCar.getExpenseCompany());
                mav.addObject("businessExpenseFee", newCar.getBusinessExpenseFee());
                mav.addObject("forceExpenseFee", newCar.getForceExpenseFee());
                mav.addObject("expenseRebate", newCar.getExpenseRebate());
                mav.addObject("agencyFee", newCar.getAgencyFee());
                mav.addObject("payMoney", newCar.getPayMoney());
                mav.addObject("paidMoney", newCar.getPaidMoney());
                mav.addObject("consumerProperty", newCar.getConsumerProperty());
                mav.addObject("consumerResource", newCar.getConsumerResource());
                mav.addObject("purchaseUse", newCar.getPurchaseUse());
                mav.addObject("consumerName", newCar.getConsumerName());
                mav.addObject("consumerSex", newCar.getConsumerSex());
                mav.addObject("consumerAge", newCar.getConsumerAge());
                mav.addObject("consumerAddress", newCar.getConsumerAddress());
                mav.addObject("consumerPhone", newCar.getConsumerPhone());
                mav.addObject("saleType", newCar.getSaleType());

                if (newCar.getSaleType().equals(ContextType.SALE_TYPE_AJ)) {
                    MortgageRecord mortgageRecord = mortgageRecordService.getMortgageRecordById(newCar.getMortgageId());
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
                    mav.addObject("mortgageMoney", mortgageRecord.getMortgageMoney());
                    mav.addObject("aMortgageMoney", mortgageRecord.getaMortgageMoney());
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
            NewCar newCar = new NewCar();
            newCar.setCarBrand(carBrand);
            newCar.setCarModel(carModel);
            newCar.setCarConfig(carConfig);
            newCar.setGuidancePrice(guidancePrice == null ? 0 : guidancePrice);
            newCar.setPurchasePerson(purchasePerson);
            newCar.setPurchaseMoney(purchaseMoney == null ? 0 : purchaseMoney);
            newCar.setSalePerson(salePerson);
            newCar.setSaleDate(TimeUtils.transformDateToTimetag(saleDate, TimeUtils.FORMAT_ONE));
            newCar.setSaleMoney(saleMoney);
            newCar.setExpenseCompany(expenseCompany);
            newCar.setBusinessExpenseFee(businessExpenseFee == null ? 0 : businessExpenseFee);
            newCar.setForceExpenseFee(forceExpenseFee == null ? 0 : forceExpenseFee);
            newCar.setExpenseRebate(expenseRebate);
            newCar.setAgencyFee(agencyFee == null ? 0 : agencyFee);
            newCar.setPayMoney(payMoney == null ? 0 : payMoney);
            newCar.setPaidMoney(paidMoney == null ? 0 : paidMoney);
            newCar.setConsumerProperty(consumerProperty);
            newCar.setConsumerResource(consumerResource);
            newCar.setPurchaseUse(purchaseUse);
            newCar.setConsumerName(consumerName);
            newCar.setConsumerSex(consumerSex);
            newCar.setConsumerAge(consumerAge);
            newCar.setConsumerAddress(consumerAddress);
            newCar.setConsumerPhone(consumerPhone);
            newCar.setSaleType(saleType);

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
                    mortgageRecord.setMortgageMoney(mortgageMoney == null ? 0.0 : mortgageMoney);
                    mortgageRecord.setaMortgageMoney(aMortgageMoney == null ? 0.0 : aMortgageMoney);
                    mortgageRecord.setIsMortgage(mortgageRecord.getMortgageMoney() <= 0 ? 0 : 1);
                    mortgageRecordService.createMortgageRecord(mortgageRecord);

                    newCar.setMortgageId(mortgageRecord.getId());
                }

                newCar.setRecordStatus(ContextType.NEW_CAR_SALE);
                newCar.setOtherCost(0.0);
                newCar.setOtherIncome(0.0);
                newCarService.create(newCar);

                //按揭更新linkID
                if(mortgageRecord!=null){
                    mortgageRecord.setIsNewCar(1);
                    mortgageRecord.setNewCarId(newCar.getId());
                    mortgageRecordService.updateMortgageRecord(mortgageRecord);
                }
            } else if (action.equals(MOD_ACTION)) {
                NewCar oldNewCar = newCarService.getNewCarById(id);

                if (oldNewCar.getSaleType().equals(ContextType.SALE_TYPE_QK) && newCar.getSaleType().equals(ContextType.SALE_TYPE_QK)) {
                } else if (oldNewCar.getSaleType().equals(ContextType.SALE_TYPE_AJ)
                        && newCar.getSaleType().equals(ContextType.SALE_TYPE_QK)) {
                    mortgageRecordService.deleteMortgageRecordById(oldNewCar.getMortgageId());
                    newCar.setMortgageId(0);
                } else if (oldNewCar.getSaleType().equals(ContextType.SALE_TYPE_QK)
                        && newCar.getSaleType().equals(ContextType.SALE_TYPE_AJ)) {
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
                    mortgageRecord.setMortgageMoney(mortgageMoney == null ? 0.0 : mortgageMoney);
                    mortgageRecord.setaMortgageMoney(aMortgageMoney == null ? 0.0 : aMortgageMoney);
                    mortgageRecord.setIsMortgage(mortgageRecord.getMortgageMoney() <= 0 ? 0 : 1);
                    mortgageRecord.setIsNewCar(1);
                    mortgageRecord.setNewCarId(oldNewCar.getId());
                    mortgageRecordService.createMortgageRecord(mortgageRecord);

                    newCar.setMortgageId(mortgageRecord.getId());
                } else if (oldNewCar.getSaleType().equals(ContextType.SALE_TYPE_AJ)
                        && newCar.getSaleType().equals(ContextType.SALE_TYPE_AJ)) {
                    MortgageRecord mortgageRecord = mortgageRecordService.getMortgageRecordById(oldNewCar.getMortgageId());
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
                    mortgageRecord.setMortgageMoney(mortgageMoney == null ? 0.0 : mortgageMoney);
                    mortgageRecord.setaMortgageMoney(aMortgageMoney == null ? 0.0 : aMortgageMoney);
                    mortgageRecord.setIsMortgage(mortgageRecord.getMortgageMoney() <= 0 ? 0 : 1);
                    mortgageRecordService.updateMortgageRecord(mortgageRecord);
                }
                newCar.setId(id);
                newCarService.updateNewCar(newCar);
            }
            mav.setViewName("redirect:/newCarView");
            return mav;
        }
    }

    @RequestMapping(value = "/newCarPaid", method = RequestMethod.GET)
    public ModelAndView newCarPaid(
            @RequestParam(value = "goonPaid", required = false, defaultValue = "") Double goonPaid,
            @RequestParam(value = "id", required = false, defaultValue = "") Integer id) {
        if (goonPaid == null) {
            goonPaid = 0.0;
        }
        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:/newCarView");

        NewCar newCar = newCarService.getNewCarById(id);
        Double paidMoney = newCar.getPaidMoney() + goonPaid;

        NewCar update = new NewCar();
        update.setId(id);
        update.setPaidMoney(paidMoney);

        if (paidMoney > newCar.getPayMoney()) {
            return mav;
        }
        newCarService.updateNewCar(update);
        return mav;
    }

    /**
     * 新车放款记录
     *
     * @param goonPaid
     * @param paidReason
     * @param id
     * @param session
     * @return
     */
    @RequestMapping(value = "/mortgageNewCarPaid", method = RequestMethod.GET)
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
        mav.setViewName("redirect:/newCarView");

        MortgageRecord update = new MortgageRecord();
        update.setId(id);
        update.setMortgageMoney(paidMoney);
        mortgageRecordService.updateMortgageRecord(update);

        session.setAttribute("tip", "放款成功");

        //创建放款记录
        CarPaidRecord carPaidRecord = new CarPaidRecord();
        carPaidRecord.setCarRecordId(mortgageLog.getNewCarId());
        carPaidRecord.setRecordStatus(ContextType.PAY_RECORD_NEW_CAR_MORTGAGE);
        carPaidRecord.setPaidMoney(goonPaid);
        carPaidRecord.setPaidReason(paidReason);
        carPaidRecordService.create(carPaidRecord);

        return mav;
    }

    @RequestMapping(value = "/newCarDelete", method = RequestMethod.GET)
    public ModelAndView newCarDelete(
            @RequestParam(value = "id", required = false, defaultValue = "") Integer id) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:/newCarView");

        NewCar newCar = newCarService.getNewCarById(id);

        mortgageRecordService.deleteMortgageRecordById(newCar.getMortgageId());

        carSaleSetupService.deleteCarSaleSetupByIdAndType(id, ContextType.NEW_CAR_COST_TYPE);
        carSaleSetupService.deleteCarSaleSetupByIdAndType(id, ContextType.NEW_CAR_INCOME_TYPE);
        carPaidRecordService.deleteCarPaidRecordByLinkIdAndType(id, ContextType.PAY_RECORD_NEW_CAR_MORTGAGE);

        newCarService.deleteById(id);
        return mav;
    }

    @RequestMapping(value = "/newCarSearch", method = RequestMethod.GET)
    public ModelAndView newCarSearch(
            @RequestParam(value = "searchKey", required = false, defaultValue = "") String searchKey,
            @RequestParam(value = "searchValue", required = false, defaultValue = "") String searchValue,
            @RequestParam(value = "btime", required = false, defaultValue = "") String btime,
            @RequestParam(value = "etime", required = false, defaultValue = "") String etime) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("export", 1);
        mav.addObject("searchKey", searchKey);
        mav.addObject("searchValue", searchValue);
        mav.addObject("btime", btime);
        mav.addObject("etime", etime);
        Long bt = StringUtils.isEmpty(btime) ? 0L : TimeUtils.transformDateToTimetag(btime, TimeUtils.FORMAT_ONE);
        Long et = StringUtils.isEmpty(etime) ? System.currentTimeMillis() : TimeUtils.transformDateToTimetag(etime, TimeUtils.FORMAT_ONE);
        List<NewCar> resultList = new ArrayList<>();

        if (StringUtils.isEmpty(searchKey)) {
            mav.setViewName("redirect:/newCarView");
            mav.addObject(TIP, "搜索条件未选中！");
            return mav;
        } else {
            List<NewCar> list = dealList(newCarService.getNewCarListByStatus(ContextType.NEW_CAR_SALE));
            for (NewCar newCar : list) {
                if (searchKey.equals("salePerson")) {
                    if (newCar.getSalePerson().equals(searchValue)) {
                        resultList.add(newCar);
                    }
                } else if (searchKey.equals("carModel")) {
                    if (newCar.getCarModel().equals(searchValue)) {
                        resultList.add(newCar);
                    }
                } else if (searchKey.equals("saleDate")) {
                    if (bt <= newCar.getSaleDate() && newCar.getSaleDate() <= et) {
                        resultList.add(newCar);
                    }
                }
            }
        }
        mav.setViewName("newCar/list");

        cacheCenter.setNewCarList(resultList);
        mav.addObject(DATA, resultList);
        return mav;
    }
}
