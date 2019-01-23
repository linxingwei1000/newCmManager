package cm.lx.controller.stat;

import cm.lx.bean.stat.MoneyFlowStat;
import cm.lx.bean.stat.MoneyStat;
import cm.lx.bean.stat.OtherStat;
import cm.lx.bean.stat.PropertyStat;
import cm.lx.business.CacheCenter;
import cm.lx.business.StatCenter;
import cm.lx.common.ContextType;
import cm.lx.bean.entity.CarRecord;
import cm.lx.bean.entity.CarSaleInfo;
import cm.lx.bean.entity.Insurance;
import cm.lx.bean.entity.InsuranceBusiness;
import cm.lx.controller.BaseController;
import cm.lx.service.*;
import cm.lx.util.TimeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.*;

@Controller
public class StatController extends BaseController {

    @Resource
    CacheCenter cacheCenter;

    @Resource
    CarRecordService carRecordService;

    @Resource
    CarCostService carCostService;

    @Resource
    CarSfService carSfService;

    @Resource
    CarSaleInfoService carSaleInfoService;

    @Resource
    MortgageRecordService mortgageRecordService;

    @Resource
    InsuranceService insuranceService;

    @Resource
    InsuranceBusinessService insuranceBusinessService;

    @Resource
    MoneyManagerService moneyManagerService;

    @Resource
    MortgageRebateService mortgageRebateService;

    @RequestMapping(value = "/statMoneyView")
    public ModelAndView statMoneyView() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("stat/moneyStat");
        return mav;
    }

    @RequestMapping(value = "/statMoneyData", method = {RequestMethod.GET})
    public ModelAndView statMoneyData(
            @RequestParam(value = "searchKey", required = false, defaultValue = "") String searchKey,
            @RequestParam(value = "searchValue", required = false, defaultValue = "") String searchValue,
            @RequestParam(value = "btime", required = false, defaultValue = "") String btime,
            @RequestParam(value = "etime", required = false, defaultValue = "") String etime) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("searchKey", searchKey);
        mav.addObject("searchValue", searchValue);
        mav.addObject("btime", btime);
        mav.addObject("etime", etime);
        Long bt = StringUtils.isEmpty(btime) ? 0L : TimeUtils.transformDateToTimetag(btime, TimeUtils.FORMAT_ONE);
        Long et = StringUtils.isEmpty(etime) ? System.currentTimeMillis() : TimeUtils.transformDateToTimetag(etime, TimeUtils.FORMAT_ONE);

        List<CarRecord> list = carRecordService.getCarRecordByRecordStatus(ContextType.RECORD_STATUS_SOLD);
        List<CarRecord> resultList = new ArrayList<>();
        for (CarRecord carRecord : list) {
            CarSaleInfo carSaleInfo = carSaleInfoService.getCarSaleInfoById(carRecord.getSaleId());
            if (bt <= carSaleInfo.getSaleDate() && carSaleInfo.getSaleDate() <= et) {
                if (searchKey.equals("purchasePerson")) {
                    if (carRecord.getPurchasePerson().equals(searchValue)) {
                        resultList.add(carRecord);
                    }
                } else if (searchKey.equals("salePerson")) {
                    if (carSaleInfo.getSalePerson().equals(searchValue)) {
                        resultList.add(carRecord);
                    }
                } else if (searchKey.equals("purchaseType")) {
                    if (carRecord.getPurchaseType() == 0) continue;
                    if (cacheCenter.getCarPropertyById(carRecord.getPurchaseType()).getPropertyValue().equals(searchValue)) {
                        resultList.add(carRecord);
                    }
                } else if (searchKey.equals("carLine")) {
                    if (carRecord.getCarLine() == 0) continue;
                    if (cacheCenter.getCarPropertyById(carRecord.getCarLine()).getPropertyValue().equals(searchValue)) {
                        resultList.add(carRecord);
                    }
                } else if (searchKey.equals("carLevel")) {
                    if (carRecord.getCarLevel() == 0) continue;
                    if (cacheCenter.getCarPropertyById(carRecord.getCarLevel()).getPropertyValue().equals(searchValue)) {
                        resultList.add(carRecord);
                    }
                } else {
                    resultList.add(carRecord);
                }
            }
        }

        mav.setViewName("stat/moneyStat");
        List<MoneyStat> moneyStatList = new ArrayList<>();
        MoneyStat moneyStat = StatCenter.statMoneyData(carCostService, carSfService, carSaleInfoService, resultList, moneyStatList);
        mav.addObject("moneyStat", moneyStat);
        mav.addObject("moneyStatList", moneyStatList);
        return mav;
    }

    @RequestMapping(value = "/statMoneyFlowView")
    public ModelAndView statMoneyFlowView() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("stat/moneyFlowStat");
        Long et = System.currentTimeMillis() / (1000 * 3600 * 24) * (1000 * 3600 * 24) - TimeZone.getDefault().getRawOffset();
        MoneyFlowStat moneyFlowStat = new MoneyFlowStat();
        StatCenter.statMoneyFlowData(mortgageRebateService, moneyManagerService, carRecordService, carCostService,
                carSfService, carSaleInfoService, mortgageRecordService, cacheCenter, moneyFlowStat, et);
        mav.addObject("moneyFlowStat", moneyFlowStat);
        return mav;
    }

    @RequestMapping(value = "/statPropertyView")
    public ModelAndView statPropertyView() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("stat/propertyStat");
        return mav;
    }


    @RequestMapping(value = "/statPropertyData", method = {RequestMethod.GET})
    public ModelAndView statPropertyData(
            @RequestParam(value = "searchKey", required = false, defaultValue = "") String searchKey,
            @RequestParam(value = "searchValue", required = false, defaultValue = "") String searchValue,
            @RequestParam(value = "btime", required = false, defaultValue = "") String btime,
            @RequestParam(value = "etime", required = false, defaultValue = "") String etime) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("searchKey", searchKey);
        mav.addObject("searchValue", searchValue);
        mav.addObject("btime", btime);
        mav.addObject("etime", etime);
        Long bt = StringUtils.isEmpty(btime) ? 0L : TimeUtils.transformDateToTimetag(btime, TimeUtils.FORMAT_ONE);
        Long et = StringUtils.isEmpty(etime) ? System.currentTimeMillis() : TimeUtils.transformDateToTimetag(etime, TimeUtils.FORMAT_ONE);

        List<CarRecord> list = carRecordService.getCarRecordByRecordStatus(ContextType.RECORD_STATUS_SOLD);
        List<CarRecord> resultList = new ArrayList<>();
        for (CarRecord carRecord : list) {
            CarSaleInfo carSaleInfo = carSaleInfoService.getCarSaleInfoById(carRecord.getSaleId());
            if (bt <= carSaleInfo.getSaleDate() && carSaleInfo.getSaleDate() <= et) {
                resultList.add(carRecord);
            }
        }

        mav.setViewName("stat/propertyStat");
        PropertyStat propertyStat = new PropertyStat();
        StatCenter.statProperty(carSaleInfoService, cacheCenter, resultList, propertyStat);
        mav.addObject("propertyStat", propertyStat);
        return mav;
    }

    @RequestMapping(value = "/statOtherView")
    public ModelAndView statOtherView() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("stat/otherStat");
        return mav;
    }

    @RequestMapping(value = "/statOtherData", method = {RequestMethod.GET})
    public ModelAndView statOtherData(
            @RequestParam(value = "searchKey", required = false, defaultValue = "") String searchKey,
            @RequestParam(value = "searchValue", required = false, defaultValue = "") String searchValue,
            @RequestParam(value = "btime", required = false, defaultValue = "") String btime,
            @RequestParam(value = "etime", required = false, defaultValue = "") String etime) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("searchKey", searchKey);
        mav.addObject("searchValue", searchValue);
        mav.addObject("btime", btime);
        mav.addObject("etime", etime);
        Long bt = StringUtils.isEmpty(btime) ? 0L : TimeUtils.transformDateToTimetag(btime, TimeUtils.FORMAT_ONE);
        Long et = StringUtils.isEmpty(etime) ? System.currentTimeMillis() : TimeUtils.transformDateToTimetag(etime, TimeUtils.FORMAT_ONE);

        List<CarRecord> list = carRecordService.getCarRecordByRecordStatus(ContextType.RECORD_STATUS_SOLD);
        List<CarRecord> resultList = new ArrayList<>();
        for (CarRecord carRecord : list) {
            CarSaleInfo carSaleInfo = carSaleInfoService.getCarSaleInfoById(carRecord.getSaleId());
            if (bt <= carSaleInfo.getSaleDate() && carSaleInfo.getSaleDate() <= et) {
                resultList.add(carRecord);
            }
        }

        mav.setViewName("stat/otherStat");
        OtherStat otherStat = new OtherStat();
        StatCenter.statMortgageCompanyData(carSaleInfoService, mortgageRecordService, resultList, otherStat);

        List<Insurance> insuranceList = insuranceService.getInsuranceListByDisplayStatus(null);
        for (Insurance insurance : insuranceList) {
            List<InsuranceBusiness> bList = insuranceBusinessService.getInsuranceBusinessListByIidAndStatus(insurance.getId(), null);
            List<InsuranceBusiness> temp = new ArrayList<>();
            for (InsuranceBusiness insuranceBusiness : bList) {
                if (bt <= insuranceBusiness.getInsuranceDate() && insuranceBusiness.getInsuranceDate() <= et) {
                    temp.add(insuranceBusiness);
                }
            }
            insurance.setList(temp);
        }

        StatCenter.statInsuranceData(insuranceList, otherStat);
        mav.addObject("otherStat", otherStat);
        return mav;
    }
}
