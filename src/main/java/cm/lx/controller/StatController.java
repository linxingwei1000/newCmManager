package cm.lx.controller;

import cm.lx.bean.*;
import cm.lx.business.CacheCenter;
import cm.lx.business.DaoCenter;
import cm.lx.business.StatCenter;
import cm.lx.common.ContextType;
import cm.lx.util.TimeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
public class StatController extends BaseController {

    @Resource
    DaoCenter daoCenter;

    @Resource
    CacheCenter cacheCenter;

    @RequestMapping(value = "/statMoneyView")
    public ModelAndView statMoneyView(HttpSession session) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("stat/moneyStat");
        return mav;
    }

    @RequestMapping(value = "/statMoneyData", method = {RequestMethod.GET})
    public ModelAndView statMoneyData(
            @RequestParam(value = "searchKey", required = false, defaultValue = "") String searchKey,
            @RequestParam(value = "searchValue", required = false, defaultValue = "") String searchValue,
            @RequestParam(value = "btime", required = false, defaultValue = "") String btime,
            @RequestParam(value = "etime", required = false, defaultValue = "") String etime,
            HttpSession session) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("searchKey", searchKey);
        mav.addObject("searchValue", searchValue);
        mav.addObject("btime", btime);
        mav.addObject("etime", etime);
        Long bt = StringUtils.isEmpty(btime) ? 0L : TimeUtils.transformDateToTimetag(btime, TimeUtils.FORMAT_ONE);
        Long et = StringUtils.isEmpty(etime) ? System.currentTimeMillis() : TimeUtils.transformDateToTimetag(etime, TimeUtils.FORMAT_ONE);

        List<CarRecord> list = daoCenter.getCarRecordListByRecordStatus(ContextType.RECORD_STATUS_SOLD);
        List<CarRecord> resultList = new ArrayList<CarRecord>();
        for (CarRecord carRecord : list) {
            CarSaleInfo carSaleInfo = daoCenter.getCarSaleInfoById(carRecord.getSaleId());
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
        List<MoneyStat> moneyStatList = new ArrayList<MoneyStat>();
        MoneyStat moneyStat = StatCenter.statMoneyData(daoCenter, resultList, moneyStatList);
        mav.addObject("moneyStat", moneyStat);
        mav.addObject("moneyStatList", moneyStatList);
        return mav;
    }

    @RequestMapping(value = "/statMoneyFlowView")
    public ModelAndView statMoneyFlowView(HttpSession session) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("stat/moneyFlowStat");
        Long et = System.currentTimeMillis() / (1000 * 3600 * 24) * (1000 * 3600 * 24) - TimeZone.getDefault().getRawOffset();
        MoneyFlowStat moneyFlowStat = new MoneyFlowStat();
        StatCenter.statMoneyFlowData(daoCenter, cacheCenter, moneyFlowStat, et);
        mav.addObject("moneyFlowStat", moneyFlowStat);
        return mav;
    }

    @RequestMapping(value = "/statPropertyView")
    public ModelAndView statPropertyView(HttpSession session) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("stat/propertyStat");
        return mav;
    }


    @RequestMapping(value = "/statPropertyData", method = {RequestMethod.GET})
    public ModelAndView statPropertyData(
            @RequestParam(value = "searchKey", required = false, defaultValue = "") String searchKey,
            @RequestParam(value = "searchValue", required = false, defaultValue = "") String searchValue,
            @RequestParam(value = "btime", required = false, defaultValue = "") String btime,
            @RequestParam(value = "etime", required = false, defaultValue = "") String etime,
            HttpSession session) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("searchKey", searchKey);
        mav.addObject("searchValue", searchValue);
        mav.addObject("btime", btime);
        mav.addObject("etime", etime);
        Long bt = StringUtils.isEmpty(btime) ? 0L : TimeUtils.transformDateToTimetag(btime, TimeUtils.FORMAT_ONE);
        Long et = StringUtils.isEmpty(etime) ? System.currentTimeMillis() : TimeUtils.transformDateToTimetag(etime, TimeUtils.FORMAT_ONE);

        List<CarRecord> list = daoCenter.getCarRecordListByRecordStatus(ContextType.RECORD_STATUS_SOLD);
        List<CarRecord> resultList = new ArrayList<CarRecord>();
        for (CarRecord carRecord : list) {
            CarSaleInfo carSaleInfo = daoCenter.getCarSaleInfoById(carRecord.getSaleId());
            if (bt <= carSaleInfo.getSaleDate() && carSaleInfo.getSaleDate() <= et) {
                resultList.add(carRecord);
            }
        }

        mav.setViewName("stat/propertyStat");
        PropertyStat propertyStat = new PropertyStat();
        StatCenter.statProperty(cacheCenter, daoCenter, resultList, propertyStat);
        mav.addObject("propertyStat", propertyStat);
        return mav;
    }

    @RequestMapping(value = "/statOtherView")
    public ModelAndView statOtherView(HttpSession session) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("stat/otherStat");
        return mav;
    }

    @RequestMapping(value = "/statOtherData", method = {RequestMethod.GET})
    public ModelAndView statOtherData(
            @RequestParam(value = "searchKey", required = false, defaultValue = "") String searchKey,
            @RequestParam(value = "searchValue", required = false, defaultValue = "") String searchValue,
            @RequestParam(value = "btime", required = false, defaultValue = "") String btime,
            @RequestParam(value = "etime", required = false, defaultValue = "") String etime,
            HttpSession session) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("searchKey", searchKey);
        mav.addObject("searchValue", searchValue);
        mav.addObject("btime", btime);
        mav.addObject("etime", etime);
        Long bt = StringUtils.isEmpty(btime) ? 0L : TimeUtils.transformDateToTimetag(btime, TimeUtils.FORMAT_ONE);
        Long et = StringUtils.isEmpty(etime) ? System.currentTimeMillis() : TimeUtils.transformDateToTimetag(etime, TimeUtils.FORMAT_ONE);

        List<CarRecord> list = daoCenter.getCarRecordListByRecordStatus(ContextType.RECORD_STATUS_SOLD);
        List<CarRecord> resultList = new ArrayList<CarRecord>();
        for (CarRecord carRecord : list) {
            CarSaleInfo carSaleInfo = daoCenter.getCarSaleInfoById(carRecord.getSaleId());
            if (bt <= carSaleInfo.getSaleDate() && carSaleInfo.getSaleDate() <= et) {
                resultList.add(carRecord);
            }
        }

        mav.setViewName("stat/otherStat");
        OtherStat otherStat = new OtherStat();
        StatCenter.statMortgageCompanyData(daoCenter, resultList, otherStat);

        List<Insurance> insuranceList = daoCenter.getInsuranceList(null);
        for (Insurance insurance : insuranceList) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("insuranceId", String.valueOf(insurance.getId()));
            List<InsuranceBusiness> bList = daoCenter.getInsuranceBusinessList(map);
            List<InsuranceBusiness> temp = new ArrayList<InsuranceBusiness>();
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
