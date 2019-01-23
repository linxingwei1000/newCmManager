package cm.lx.controller;

import cm.lx.business.CacheCenter;
import cm.lx.business.CommonAction;
import cm.lx.common.ContextType;
import cm.lx.bean.entity.*;
import cm.lx.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * 车辆管理tag下辅助controller
 *
 * @author linxingwei
 * @date 2019/1/22
 */
@Controller
public class CarAssistController extends BaseController {

    @Resource
    CacheCenter cacheCenter;

    @Resource
    CarRecordService carRecordService;

    @Resource
    CarCostService carCostService;

    @Resource
    CarRemarkService carRemarkService;

    @Resource
    CarPaidRecordService carPaidRecordService;

    @Resource
    MortgageLogService mortgageLogService;

    @Resource
    CarSaleSetupService carSaleSetupService;

    @Resource
    CarSfService carSfService;

    @Resource
    NewCarService newCarService;

    @Resource
    NewCarFinanceService newCarFinanceService;

    @RequestMapping(value = "/carRemarkAdd", method = RequestMethod.GET)
    public ModelAndView carRemarkAdd(
            @RequestParam(value = "carRecordId", required = false, defaultValue = "") Integer carRecordId,
            @RequestParam(value = "remarkType", required = false, defaultValue = "") Integer remarkType,
            @RequestParam(value = "remarkDate", required = false, defaultValue = "") String remarkDate,
            @RequestParam(value = "remark", required = false, defaultValue = "") String remark) {
        ModelAndView mav = new ModelAndView();

        CarRemark carRemark = new CarRemark();
        carRemark.setCarRecordId(carRecordId);
        carRemark.setRemarkType(remarkType);
        carRemark.setRemarkDate(remarkDate);
        carRemark.setRemark(remark);
        carRemarkService.createCarRemark(carRemark);

        //清缓存
        cacheCenter.deleteCarRecordInfo(carRecordId);
        setMavView(mav, remarkType);
        return mav;
    }

    @RequestMapping(value = "/carRemarkDel", method = RequestMethod.GET)
    public ModelAndView carRemarkDel(
            @RequestParam(value = "id", required = false, defaultValue = "") Integer id,
            @RequestParam(value = "remarkType", required = false, defaultValue = "") Integer remarkType) {
        ModelAndView mav = new ModelAndView();

        CarRemark carRemark = carRemarkService.getCarRemarkById(id);
        if(carRemark!=null){
            //清缓存
            cacheCenter.deleteCarRecordInfo(carRemark.getCarRecordId());
        }
        carRemarkService.deleteCarRemarkById(id);

        setMavView(mav, remarkType);
        return mav;
    }

    private void setMavView(ModelAndView mav, Integer remarkType){


        if (remarkType.equals(ContextType.REMARK_TYPE_PURCHASE)) {
            mav.setViewName("redirect:/carPurchaseView");
        } else if (remarkType.equals(ContextType.REMARK_TYPE_STOCK)) {
            mav.setViewName("redirect:/carStockView");
        } else if (remarkType.equals(ContextType.REMARK_TYPE_SALE)) {
            mav.setViewName("redirect:/carSaleView");
        } else if (remarkType.equals(ContextType.REMARK_TYPE_DOSSIER)) {
            mav.setViewName("redirect:/dossierView");
        }
    }

    @RequestMapping(value = "/carPurchasePaid", method = RequestMethod.GET)
    public ModelAndView carPurchasePaid(
            @RequestParam(value = "goonPaid", required = false, defaultValue = "") Double goonPaid,
            @RequestParam(value = "paidReason", required = false, defaultValue = "") String paidReason,
            @RequestParam(value = "id", required = false, defaultValue = "") Integer id,
            HttpSession session) {

        if (goonPaid == null) {
            goonPaid = 0.0;
        }
        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:/carPurchaseView");
        CarRecord carRecord = carRecordService.getCarRecordById(id);
        Double paidMoney = carRecord.getPaidMoney() + goonPaid;

        CarRecord update = new CarRecord();
        update.setId(id);
        update.setPaidMoney(paidMoney);

        if (paidMoney > carRecord.getPurchaseMoney()) {
            session.setAttribute("tip", "付款超限！");
            return mav;
        } else if (paidMoney.equals(carRecord.getPurchaseMoney())) {
            update.setRecordStatus(ContextType.RECORD_STATUS_STOCK);
        }

        //创建付款记录
        CarPaidRecord carPaidRecord = new CarPaidRecord();
        carPaidRecord.setCarRecordId(id);
        carPaidRecord.setRecordStatus(ContextType.PAY_RECORD_PURCHASE);
        carPaidRecord.setPaidMoney(goonPaid);
        carPaidRecord.setPaidReason(paidReason);
        carPaidRecordService.create(carPaidRecord);

        carRecordService.updateCarRecord(update);
        session.setAttribute("tip", "ok 付款成功！");

        return mav;
    }

    //车辆售前售后整备相关操作
    @RequestMapping(value = "/carSaleSetupAction", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView carSaleSetupAction(HttpServletRequest request, HttpServletResponse response,
                                           @RequestParam(value = "action", required = true, defaultValue = "") Integer action,
                                           @RequestParam(value = "over", required = false, defaultValue = "") Integer over,
                                           @RequestParam(value = "carCostId", required = false, defaultValue = "") Integer carCostId,
                                           @RequestParam(value = "setupType", required = false, defaultValue = "") Integer setupType,
                                           @RequestParam(value = "recordStatus", required = false, defaultValue = "") Integer recordStatus,
                                           HttpSession session) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("car/saleSetupAdd");
        mav.addObject("action", action);
        mav.addObject("carCostId", carCostId);
        mav.addObject("setupType", setupType);
        mav.addObject("recordStatus", recordStatus);

        if (setupType.equals(ContextType.MORTGAGE_AGENT_PAY)) {
            MortgageLog mortgageLog = mortgageLogService.getMortgageLogById(carCostId);
            mav.addObject("mortgageType", mortgageLog.getMortgageType());
        }

        if (over == null) {
            return mav;
        }

        // 得到所有的请求参数名称
        Enumeration parameterNames = request.getParameterNames();
        // 用来装所有的参数名称的后缀(即下划线后面的)
        List<String> parameterNamesSuffix = new ArrayList<>();
        List<String> newParameterNamesSuffix = new ArrayList<>();
        // 用来装所有的参数名称和参数值
        Map<String, String> parameterNamesAndValues = new HashMap<>();
        while (parameterNames.hasMoreElements()) {
            // 得到请求参数的名称
            String parameterName = (String) parameterNames.nextElement();
            if (ContextType.SETUP_SKIP_PARAM.contains(parameterName)) {
                continue;
            }
            // 根据下划线拆分
            String[] myParameterName = parameterName.split("_");
            // 得到下划线后面的部分
            parameterNamesSuffix.add(myParameterName[myParameterName.length - 1]);
            // 保存参数名称和参数值
            parameterNamesAndValues.put(parameterName, request.getParameter(parameterName));
        }

        // 去重
        Set<String> set = new HashSet<>();
        for (String parameterNameSuffix : parameterNamesSuffix) {
            if (set.add(parameterNameSuffix)) {
                newParameterNamesSuffix.add(parameterNameSuffix);
            }
        }

        Set<Map.Entry<String, String>> entrySetParameterNamesAndValues = parameterNamesAndValues.entrySet();

        Iterator<Map.Entry<String, String>> it = entrySetParameterNamesAndValues.iterator();
        String[] fieldNames = new String[parameterNamesAndValues.size()];
        String[] fieldValues = new String[parameterNamesAndValues.size()];
        int elementIndex = 0;
        while (it.hasNext()) {
            Map.Entry<String, String> entry = it.next();
            fieldNames[elementIndex] = entry.getKey();
            fieldValues[elementIndex] = entry.getValue();
            elementIndex++;
        }

        Double d = 0.0;
        Integer successNum = 0;
        for (int i = 0; i < newParameterNamesSuffix.size(); i++) {
            CarSaleSetup carSaleSetup = new CarSaleSetup();
            for (int index = 0; index < fieldNames.length; index++) {
                try {
                    String[] field = fieldNames[index].split("_");
                    if (newParameterNamesSuffix.get(i).equals(field[field.length - 1])) {
                        if ("setupName".equals(field[0])) {
                            carSaleSetup.setSetupName(fieldValues[index]);
                        } else if ("setupFee".equals(field[0])) {
                            carSaleSetup.setSetupFee(Double.valueOf(fieldValues[index]));
                        }
                    }
                } catch (Exception e) {
                    System.out.println("e" + e.getMessage());
                }
            }
            d += carSaleSetup.getSetupFee();
            carSaleSetup.setCarCostId(carCostId);
            carSaleSetup.setSetupType(setupType);
            carSaleSetupService.createCarSaleSetup(carSaleSetup);
            ++successNum;
        }
        //将改变金额同步到成本录入表中
        if (d != 0) {
            if (setupType.equals(ContextType.PRE_SETUP_TYPE)
                    || setupType.equals(ContextType.AFTER_SETUP_TYPE)
                    || setupType.equals(ContextType.OTHER_INCOME)) {
                CarCost carCost = carCostService.getCarCostById(carCostId);
                CarCost update = new CarCost();
                update.setId(carCostId);
                if (setupType.equals(ContextType.PRE_SETUP_TYPE)) {
                    update.setPreSaleFee(carCost.getPreSaleFee() + d);
                    if (recordStatus != null && recordStatus.equals(ContextType.RECORD_STATUS_SALE)) {
                        mav.setViewName("redirect:/carSaleView");
                    } else {
                        mav.setViewName("redirect:/carStockView");
                    }
                } else if (setupType.equals(ContextType.AFTER_SETUP_TYPE)) {
                    update.setAfterSaleFee(carCost.getAfterSaleFee() + d);
                    mav.setViewName("redirect:/carSoldView");
                } else if (setupType.equals(ContextType.OTHER_INCOME)) {
                    update.setOtherIncomeFee(carCost.getOtherIncomeFee() + d);
                    mav.setViewName("redirect:/carStockView");
                }
                carCostService.updateCarCost(update);

                //清缓存
                cacheCenter.deleteCarRecordInfo(carCost.getCarRecordId());
            } else if (setupType.equals(ContextType.SALE_TYPE)) {
                CarSf carSf = carSfService.getCarSfById(carCostId);
                CarSf sfUpdate = new CarSf();
                sfUpdate.setId(carCostId);
                sfUpdate.setSaleFee(carSf.getSaleFee() + d);
                carSfService.updateCarSf(sfUpdate);
                mav.setViewName("redirect:/carSaleView");

                //清缓存
                cacheCenter.deleteCarRecordInfo(carSf.getCarRecordId());
            } else if (setupType.equals(ContextType.NEW_CAR_COST_TYPE)
                    || setupType.equals(ContextType.NEW_CAR_INCOME_TYPE)) {
                NewCar newCar = newCarService.getNewCarById(carCostId);
                NewCar ncUpdate = new NewCar();
                ncUpdate.setId(carCostId);
                if (setupType.equals(ContextType.NEW_CAR_COST_TYPE)) {
                    ncUpdate.setOtherCost(newCar.getOtherCost() + d);
                } else if (setupType.equals(ContextType.NEW_CAR_INCOME_TYPE)) {
                    ncUpdate.setOtherIncome(newCar.getOtherIncome() + d);
                }
                newCarService.updateNewCar(ncUpdate);
                mav.setViewName("redirect:/newCarView");
            } else if (setupType.equals(ContextType.NEW_CAR_FIANCE_COST_TYPE)) {
                NewCarFinance newCarFinance = newCarFinanceService.getNewCarFinanceById(carCostId);
                NewCarFinance ncUpdate = new NewCarFinance();
                ncUpdate.setId(carCostId);
                ncUpdate.setOtherCost(newCarFinance.getOtherCost() + d);
                newCarFinanceService.updateNewCarFinance(ncUpdate);
                mav.setViewName("redirect:/newCarFinanceView");
            } else if (setupType.equals(ContextType.MORTGAGE_AGENT_PAY)) {
                MortgageLog mortgageLog = mortgageLogService.getMortgageLogById(carCostId);
                mortgageLog.setAgentPayFee(mortgageLog.getAgentPayFee() + d);
                CommonAction.calculateMortgageData(mortgageLog);
                mortgageLogService.updateMortgageLog(mortgageLog);
                mav.setViewName("redirect:/mortgageView?mortgageType=" + mortgageLog.getMortgageType());
            }
        }
        session.setAttribute("tip", "ok 成功插入" + successNum + "条数据！");
        return mav;
    }


    @RequestMapping(value = "/carSaleSetupDelete", method = RequestMethod.GET)
    public ModelAndView carSaleSetupDelete(
            @RequestParam(value = "id", required = false, defaultValue = "") Integer id,
            @RequestParam(value = "carCostId", required = false, defaultValue = "") Integer carCostId,
            @RequestParam(value = "setupType", required = false, defaultValue = "") Integer setupType,
            @RequestParam(value = "recordStatus", required = false, defaultValue = "") Integer recordStatus,
            HttpSession session) {
        ModelAndView mav = new ModelAndView();

        CarSaleSetup carSaleSetup = carSaleSetupService.getCarSaleSetupById(id);
        if (setupType.equals(ContextType.PRE_SETUP_TYPE)
                || setupType.equals(ContextType.AFTER_SETUP_TYPE)
                || setupType.equals(ContextType.OTHER_INCOME)) {
            CarCost carCost = carCostService.getCarCostById(carCostId);
            CarCost update = new CarCost();
            update.setId(carCostId);
            if (setupType.equals(ContextType.PRE_SETUP_TYPE)) {
                if (recordStatus != null && recordStatus.equals(ContextType.RECORD_STATUS_SALE)) {
                    mav.setViewName("redirect:/carSaleView");
                } else {
                    mav.setViewName("redirect:/carStockView");
                }
                update.setPreSaleFee(carCost.getPreSaleFee() - carSaleSetup.getSetupFee());
            } else if (setupType.equals(ContextType.AFTER_SETUP_TYPE)) {
                mav.setViewName("redirect:/carSoldView");
                update.setAfterSaleFee(carCost.getAfterSaleFee() - carSaleSetup.getSetupFee());
            } else if (setupType.equals(ContextType.OTHER_INCOME)) {
                mav.setViewName("redirect:/carStockView");
                update.setOtherIncomeFee(carCost.getOtherIncomeFee() - carSaleSetup.getSetupFee());
            }
            carCostService.updateCarCost(update);

            //清缓存
            cacheCenter.deleteCarRecordInfo(carCost.getCarRecordId());

        } else if (setupType.equals(ContextType.SALE_TYPE)) {
            CarSf carSf = carSfService.getCarSfById(carCostId);
            CarSf sfUpdate = new CarSf();
            sfUpdate.setId(carCostId);
            sfUpdate.setSaleFee(carSf.getSaleFee() - carSaleSetup.getSetupFee());
            carSfService.updateCarSf(sfUpdate);
            mav.setViewName("redirect:/carSaleView");

            //清缓存
            cacheCenter.deleteCarRecordInfo(carSf.getCarRecordId());

        } else if (setupType.equals(ContextType.NEW_CAR_COST_TYPE)
                || setupType.equals(ContextType.NEW_CAR_INCOME_TYPE)) {
            NewCar newCar = newCarService.getNewCarById(carCostId);
            NewCar ncUpdate = new NewCar();
            ncUpdate.setId(carCostId);
            if (setupType.equals(ContextType.NEW_CAR_COST_TYPE)) {
                ncUpdate.setOtherCost(newCar.getOtherCost() - carSaleSetup.getSetupFee());
            } else if (setupType.equals(ContextType.NEW_CAR_INCOME_TYPE)) {
                ncUpdate.setOtherIncome(newCar.getOtherIncome() - carSaleSetup.getSetupFee());
            }
            newCarService.updateNewCar(ncUpdate);
            mav.setViewName("redirect:/newCarView");
        } else if (setupType.equals(ContextType.NEW_CAR_FIANCE_COST_TYPE)) {
            NewCarFinance newCarFinance = newCarFinanceService.getNewCarFinanceById(carCostId);
            NewCarFinance ncUpdate = new NewCarFinance();
            ncUpdate.setId(carCostId);
            ncUpdate.setOtherCost(newCarFinance.getOtherCost() - carSaleSetup.getSetupFee());
            newCarFinanceService.updateNewCarFinance(ncUpdate);
            mav.setViewName("redirect:/newCarFinanceView");
        } else if (setupType.equals(ContextType.MORTGAGE_AGENT_PAY)) {
            MortgageLog mortgageLog = mortgageLogService.getMortgageLogById(carCostId);
            mortgageLog.setAgentPayFee(mortgageLog.getAgentPayFee() - carSaleSetup.getSetupFee());
            CommonAction.calculateMortgageData(mortgageLog);
            mortgageLogService.updateMortgageLog(mortgageLog);
            mav.setViewName("redirect:/mortgageView?mortgageType=" + mortgageLog.getMortgageType());
        }

        carSaleSetupService.deleteCarSaleSetupById(id);
        session.setAttribute("tip", "ok 删除成功！");
        return mav;
    }
}
