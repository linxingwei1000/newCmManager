package cm.lx.controller.insurance;

import cm.lx.bean.entity.Insurance;
import cm.lx.bean.entity.InsuranceBusiness;
import cm.lx.business.CacheCenter;
import cm.lx.common.ContextType;
import cm.lx.controller.BaseController;
import cm.lx.service.InsuranceBusinessService;
import cm.lx.service.InsuranceService;
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
 * 保险基本业务
 *
 * @author linxingwei
 * @date 2019/1/21
 */
@Controller
public class InsuranceController extends BaseController {

    @Resource
    CacheCenter cacheCenter;

    @Resource
    InsuranceService insuranceService;

    @Resource
    InsuranceBusinessService insuranceBusinessService;

    private void initPropertyData(ModelAndView mav) {
        mav.addObject(ContextType.BUSINESS_TYPE, cacheCenter.getCarPropertyByKey(ContextType.BUSINESS_TYPE));
    }

    private List<Insurance> prepareData(List<Insurance> list) {
        for (Insurance insurance : list) {
            List<InsuranceBusiness> bList = insuranceBusinessService.getInsuranceBusinessListByIidAndStatus(insurance.getId(), ContextType.DISPLAY_STATUS_SHOW);
            for (InsuranceBusiness insuranceBusiness : bList) {
                insuranceBusiness.setStrInsuranceDate(TimeUtils.transformTimetagToDate(insuranceBusiness.getInsuranceDate(), TimeUtils.FORMAT_ONE));
                insuranceBusiness.setStrQzxDate(TimeUtils.transformTimetagToDate(insuranceBusiness.getQzxDate(), TimeUtils.FORMAT_ONE));
                insuranceBusiness.setStrSyxDate(TimeUtils.transformTimetagToDate(insuranceBusiness.getSyxDate(), TimeUtils.FORMAT_ONE));

                if (insuranceBusiness.getBusinessType() != 0) {
                    insuranceBusiness.setStrBusinessType(cacheCenter.getCarPropertyById(insuranceBusiness.getBusinessType()).getPropertyValue());
                }

            }
            insurance.setList(bList);
            insurance.setStrTravelRegister(TimeUtils.transformTimetagToDate(insurance.getTravelRegister(), TimeUtils.FORMAT_ONE));
            insurance.setStrTravelLssuing(TimeUtils.transformTimetagToDate(insurance.getTravelLssuing(), TimeUtils.FORMAT_ONE));
        }
        return list;
    }

    @RequestMapping(value = "/insuranceView")
    public ModelAndView insuranceView(HttpSession session) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("insurance/list");
        mav.addObject(DATA, prepareData(insuranceService.getInsuranceListByDisplayStatus(ContextType.DISPLAY_STATUS_SHOW)));
        mav.addObject(TIP, session.getAttribute("tip"));
        return mav;
    }

    @RequestMapping(value = "/insuranceAction", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView insuranceAction(
            @RequestParam(value = "action", required = true, defaultValue = "") Integer action,
            @RequestParam(value = "over", required = false, defaultValue = "") Integer over,
            @RequestParam(value = "id", required = false, defaultValue = "") Integer id,
            @RequestParam(value = "businessPerson", required = false, defaultValue = "") String businessPerson,
            @RequestParam(value = "carBrand", required = false, defaultValue = "") String carBrand,
            @RequestParam(value = "carModel", required = false, defaultValue = "") String carModel,
            @RequestParam(value = "consumerName", required = false, defaultValue = "") String consumerName,
            @RequestParam(value = "consumerSex", required = false, defaultValue = "") Integer consumerSex,
            @RequestParam(value = "consumerBirth", required = false, defaultValue = "") String consumerBirth,
            @RequestParam(value = "consumerPhone", required = false, defaultValue = "") String consumerPhone,
            @RequestParam(value = "travelRegister", required = false, defaultValue = "") String travelRegister,
            @RequestParam(value = "travelLssuing", required = false, defaultValue = "") String travelLssuing,
            @RequestParam(value = "consumerType", required = false, defaultValue = "") Integer consumerType,
            @RequestParam(value = "renewalFee", required = false, defaultValue = "") Double renewalFee,
            @RequestParam(value = "dealRenewal", required = false, defaultValue = "") Integer dealRenewal,
            @RequestParam(value = "renewalDesc", required = false, defaultValue = "") String renewalDesc) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("insurance/add");
        mav.addObject("action", action);

        mav.addObject("businessPerson", businessPerson);
        mav.addObject("carBrand", carBrand);
        mav.addObject("carModel", carModel);
        mav.addObject("consumerName", consumerName);
        mav.addObject("consumerSex", consumerSex);
        mav.addObject("consumerBirth", consumerBirth);
        mav.addObject("consumerPhone", consumerPhone);
        mav.addObject("travelRegister", travelRegister);
        mav.addObject("travelLssuing", travelLssuing);
        mav.addObject("consumerType", consumerType);
        mav.addObject("renewalFee", renewalFee);
        mav.addObject("dealRenewal", dealRenewal);
        mav.addObject("renewalDesc", renewalDesc);

        if (over == null) {
            if (id != null) {
                Insurance insurance = insuranceService.getInsuranceById(id);
                mav.addObject("id", id);
                mav.addObject("businessPerson", insurance.getBusinessPerson());
                mav.addObject("carBrand", insurance.getCarBrand());
                mav.addObject("carModel", insurance.getCarModel());
                mav.addObject("consumerName", insurance.getConsumerName());
                mav.addObject("consumerSex", insurance.getConsumerSex());
                mav.addObject("consumerBirth", insurance.getConsumerBirth());
                mav.addObject("consumerPhone", insurance.getConsumerPhone());
                mav.addObject("travelRegister", TimeUtils.transformTimetagToDate(insurance.getTravelRegister(), TimeUtils.FORMAT_ONE));
                mav.addObject("travelLssuing", TimeUtils.transformTimetagToDate(insurance.getTravelLssuing(), TimeUtils.FORMAT_ONE));
                mav.addObject("consumerType", insurance.getConsumerType());
                mav.addObject("renewalFee", insurance.getRenewalFee());
                mav.addObject("dealRenewal", insurance.getDealRenewal());
                mav.addObject("renewalDesc", insurance.getRenewalDesc());
            }
            return mav;
        } else {
            Insurance insurance = new Insurance();
            insurance.setBusinessPerson(businessPerson);
            insurance.setCarBrand(carBrand);
            insurance.setCarModel(carModel);
            insurance.setConsumerName(consumerName);
            insurance.setConsumerSex(consumerSex);
            insurance.setConsumerBirth(consumerBirth);
            insurance.setConsumerPhone(consumerPhone);
            insurance.setTravelRegister(TimeUtils.transformDateToTimetag(travelRegister, TimeUtils.FORMAT_ONE));
            insurance.setTravelLssuing(TimeUtils.transformDateToTimetag(travelLssuing, TimeUtils.FORMAT_ONE));
            insurance.setConsumerType(consumerType);
            insurance.setRenewalFee(renewalFee == null ? 0.0 : renewalFee);
            insurance.setDealRenewal(dealRenewal);
            insurance.setRenewalDesc(renewalDesc);

            mav.clear();
            if (action.equals(CREATE_ACTION)) {
                insurance.setDisplayStatus(ContextType.DISPLAY_STATUS_SHOW);
                insuranceService.create(insurance);
            } else if (action.equals(MOD_ACTION)) {
                insurance.setId(id);
                insuranceService.updateInsuranceById(insurance);
            }
            mav.setViewName("redirect:/insuranceView");
            return mav;
        }
    }

    @RequestMapping(value = "/insuranceStatus", method = RequestMethod.GET)
    public ModelAndView insuranceStatus(
            @RequestParam(value = "id", required = false, defaultValue = "") Integer id,
            HttpSession session) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:/insuranceView");
        Insurance insurance = new Insurance();
        insurance.setId(id);
        insurance.setDisplayStatus(ContextType.DISPLAY_STATUS_HIDE);
        insuranceService.updateInsuranceById(insurance);
        session.setAttribute("tip", "ok 转数据库成功！");
        return mav;
    }

    @RequestMapping(value = "/insuranceDelete", method = RequestMethod.GET)
    public ModelAndView insuranceDelete(
            @RequestParam(value = "id", required = false, defaultValue = "") Integer id) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:/insuranceView");
        //删除业务相关数据
        insuranceBusinessService.deleteInsuranceBusinessByIid(id);

        insuranceService.deleteById(id);
        return mav;
    }

    @RequestMapping(value = "/insuranceBusinessAction", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView insuranceBusinessAction(
            @RequestParam(value = "action", required = true, defaultValue = "") Integer action,
            @RequestParam(value = "over", required = false, defaultValue = "") Integer over,
            @RequestParam(value = "id", required = false, defaultValue = "") Integer id,
            @RequestParam(value = "insuranceId", required = false, defaultValue = "") Integer insuranceId,
            @RequestParam(value = "businessType", required = false, defaultValue = "") Integer businessType,
            @RequestParam(value = "insuranceDate", required = false, defaultValue = "") String insuranceDate,
            @RequestParam(value = "qzxDate", required = false, defaultValue = "") String qzxDate,
            @RequestParam(value = "qzxCompany", required = false, defaultValue = "") String qzxCompany,
            @RequestParam(value = "qzxFee", required = false, defaultValue = "") Double qzxFee,
            @RequestParam(value = "syxDate", required = false, defaultValue = "") String syxDate,
            @RequestParam(value = "syxCompany", required = false, defaultValue = "") String syxCompany,
            @RequestParam(value = "syxFee", required = false, defaultValue = "") Double syxFee,
            @RequestParam(value = "syxDiscount", required = false, defaultValue = "") String syxDiscount,
            @RequestParam(value = "expenseRebate", required = false, defaultValue = "") String expenseRebate,
            @RequestParam(value = "rebateFee", required = false, defaultValue = "") Double rebateFee,
            @RequestParam(value = "returnFee", required = false, defaultValue = "") Double returnFee,
            @RequestParam(value = "businessDesc", required = false, defaultValue = "") String businessDesc) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("insurance/businessAdd");
        mav.addObject("action", action);
        mav.addObject("insuranceId", insuranceId);
        initPropertyData(mav);

        mav.addObject("businessType", businessType);
        mav.addObject("insuranceDate", insuranceDate);
        mav.addObject("qzxDate", qzxDate);
        mav.addObject("qzxCompany", qzxCompany);
        mav.addObject("qzxFee", qzxFee);
        mav.addObject("syxDate", syxDate);
        mav.addObject("syxCompany", syxCompany);
        mav.addObject("syxFee", syxFee);
        mav.addObject("syxDiscount", syxDiscount);
        mav.addObject("expenseRebate", expenseRebate);
        mav.addObject("rebateFee", rebateFee);
        mav.addObject("returnFee", returnFee);
        mav.addObject("businessDesc", businessDesc);

        if (over == null) {
            if (id != null) {
                InsuranceBusiness insuranceBusiness = insuranceBusinessService.getInsuranceBusinessById(id);
                mav.addObject("id", id);
                mav.addObject("businessType", insuranceBusiness.getBusinessType());
                mav.addObject("insuranceDate", TimeUtils.transformTimetagToDate(insuranceBusiness.getInsuranceDate(), TimeUtils.FORMAT_ONE));
                mav.addObject("qzxDate", TimeUtils.transformTimetagToDate(insuranceBusiness.getQzxDate(), TimeUtils.FORMAT_ONE));
                mav.addObject("qzxCompany", insuranceBusiness.getQzxCompany());
                mav.addObject("qzxFee", insuranceBusiness.getQzxFee());
                mav.addObject("syxDate", TimeUtils.transformTimetagToDate(insuranceBusiness.getSyxDate(), TimeUtils.FORMAT_ONE));
                mav.addObject("syxCompany", insuranceBusiness.getSyxCompany());
                mav.addObject("syxFee", insuranceBusiness.getSyxFee());
                mav.addObject("syxDiscount", insuranceBusiness.getSyxDiscount());
                mav.addObject("expenseRebate", insuranceBusiness.getExpenseRebate());
                mav.addObject("rebateFee", insuranceBusiness.getRebateFee());
                mav.addObject("returnFee", insuranceBusiness.getReturnFee());
                mav.addObject("businessDesc", insuranceBusiness.getBusinessDesc());
            }
            return mav;
        } else {
            InsuranceBusiness insuranceBusiness = new InsuranceBusiness();
            insuranceBusiness.setBusinessType(businessType);
            insuranceBusiness.setInsuranceDate(TimeUtils.transformDateToTimetag(insuranceDate, TimeUtils.FORMAT_ONE));
            insuranceBusiness.setQzxDate(TimeUtils.transformDateToTimetag(qzxDate, TimeUtils.FORMAT_ONE));
            insuranceBusiness.setQzxCompany(qzxCompany);
            insuranceBusiness.setQzxFee(qzxFee == null ? 0 : qzxFee);
            insuranceBusiness.setSyxDate(TimeUtils.transformDateToTimetag(syxDate, TimeUtils.FORMAT_ONE));
            insuranceBusiness.setSyxCompany(syxCompany);
            insuranceBusiness.setSyxFee(syxFee == null ? 0 : syxFee);
            insuranceBusiness.setSyxDiscount(syxDiscount);
            insuranceBusiness.setExpenseRebate(expenseRebate);
            insuranceBusiness.setRebateFee(rebateFee == null ? 0 : rebateFee);
            insuranceBusiness.setReturnFee(returnFee == null ? 0 : returnFee);
            insuranceBusiness.setBusinessDesc(businessDesc);

            mav.clear();
            if (action.equals(CREATE_ACTION)) {
                insuranceBusiness.setInsuranceId(insuranceId);
                insuranceBusiness.setDisplayStatus(ContextType.DISPLAY_STATUS_SHOW);
                insuranceBusinessService.create(insuranceBusiness);
            } else if (action.equals(MOD_ACTION)) {
                insuranceBusiness.setId(id);
                insuranceBusinessService.updateInsuranceBusinessById(insuranceBusiness);
            }
            mav.setViewName("redirect:/insuranceView");
            return mav;
        }
    }

    @RequestMapping(value = "/insuranceBusinessDelete", method = RequestMethod.GET)
    public ModelAndView insuranceBusinessDelete(
            @RequestParam(value = "id", required = false, defaultValue = "") Integer id) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:/insuranceView");
        insuranceBusinessService.deleteById(id);
        return mav;
    }

    @RequestMapping(value = "/insuranceSearch", method = RequestMethod.GET)
    public ModelAndView insuranceSearch(
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
        List<Insurance> resultList = new ArrayList<>();

        if (StringUtils.isEmpty(searchKey)) {
            mav.setViewName("redirect:/insuranceView");
            mav.addObject(TIP, "搜索条件未选中！");
            return mav;
        } else {
            List<Insurance> list = prepareData(insuranceService.getInsuranceListByDisplayStatus(null));
            for (Insurance insurance : list) {
                if (searchKey.equals("consumerName")) {
                    if (insurance.getConsumerName().contains(searchValue)) {
                        resultList.add(insurance);
                    }
                } else if (searchKey.equals("insuranceDate")) {
                    if (insurance.getList().size() == 0) {
                        continue;
                    }
                    List<InsuranceBusiness> temp = new ArrayList<>();
                    for (InsuranceBusiness insuranceBusiness : insurance.getList()) {
                        if (bt <= insuranceBusiness.getInsuranceDate() && insuranceBusiness.getInsuranceDate() <= et) {
                            temp.add(insuranceBusiness);
                        }
                    }
                    if (temp.size() != 0) {
                        insurance.setList(temp);
                        resultList.add(insurance);
                    }
                } else if (searchKey.equals("qzxDate")) {
                    if (insurance.getList().size() == 0) {
                        continue;
                    }
                    List<InsuranceBusiness> temp = new ArrayList<>();
                    for (InsuranceBusiness insuranceBusiness : insurance.getList()) {
                        if (bt <= insuranceBusiness.getQzxDate() && insuranceBusiness.getQzxDate() <= et) {
                            temp.add(insuranceBusiness);
                        }
                    }
                    if (temp.size() != 0) {
                        insurance.setList(temp);
                        resultList.add(insurance);
                    }
                } else if (searchKey.equals("syxDate")) {
                    if (insurance.getList().size() == 0){
                        continue;
                    }
                    List<InsuranceBusiness> temp = new ArrayList<>();
                    for (InsuranceBusiness insuranceBusiness : insurance.getList()) {
                        if (bt <= insuranceBusiness.getSyxDate() && insuranceBusiness.getSyxDate() <= et) {
                            temp.add(insuranceBusiness);
                        }
                    }
                    if (temp.size() != 0) {
                        insurance.setList(temp);
                        resultList.add(insurance);
                    }
                }else if(searchKey.equals("businessType")){
                    if (insurance.getList().size() == 0){
                        continue;
                    }
                    List<InsuranceBusiness> temp = new ArrayList<>();
                    for (InsuranceBusiness insuranceBusiness : insurance.getList()) {
                        if(insuranceBusiness.getBusinessType()==0) continue;
                        if(cacheCenter.getCarPropertyById(insuranceBusiness.getBusinessType()).getPropertyValue().equals(searchValue)){
                            temp.add(insuranceBusiness);
                        }
                    }
                    if (temp.size() != 0) {
                        insurance.setList(temp);
                        resultList.add(insurance);
                    }
                }
            }
        }
        cacheCenter.setInsuranceList(resultList);
        mav.setViewName("insurance/list");
        mav.addObject(DATA, resultList);
        return mav;
    }


}
