package cm.lx.controller.newcar;

import cm.lx.business.CacheCenter;
import cm.lx.business.ToolUtil;
import cm.lx.common.ContextType;
import cm.lx.controller.BaseController;
import cm.lx.bean.entity.NewCarFinance;
import cm.lx.service.CarSaleSetupService;
import cm.lx.service.NewCarFinanceService;
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
 * @date 2019/1/23
 */
@Controller
public class newCarFinanceController extends BaseController {

    @Resource
    CacheCenter cacheCenter;

    @Resource
    NewCarFinanceService newCarFinanceService;

    @Resource
    CarSaleSetupService carSaleSetupService;

    private List<NewCarFinance> dealNewCarFinanceData(List<NewCarFinance> list) {
        for (NewCarFinance newCarFinance : list) {
            if (newCarFinance.getConsumerProperty() != 0) {
                newCarFinance.setStrConsumerProperty(cacheCenter.getCarPropertyById(newCarFinance.getConsumerProperty()).getPropertyValue());
            }
            if (newCarFinance.getConsumerResource() != 0) {
                newCarFinance.setStrConsumerResource(cacheCenter.getCarPropertyById(newCarFinance.getConsumerResource()).getPropertyValue());
            }

            if (newCarFinance.getPurchaseUse() != 0) {
                newCarFinance.setStrPurchaseUse(cacheCenter.getCarPropertyById(newCarFinance.getPurchaseUse()).getPropertyValue());
            }

            if (newCarFinance.getConsumerAge() != 0) {
                newCarFinance.setStrConsumerAge(cacheCenter.getCarPropertyById(newCarFinance.getConsumerAge()).getPropertyValue());
            }

            if (newCarFinance.getOtherCost() != 0) {
                newCarFinance.setOtherCostList(carSaleSetupService.getCarSaleSetupByIdAndType(newCarFinance.getId(), ContextType.NEW_CAR_FIANCE_COST_TYPE));
            }
        }
        return list;
    }

    @RequestMapping(value = "/newCarFinanceView")
    public ModelAndView newCarFinanceView(HttpSession session) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("newCar/financeList");
        mav.addObject(DATA, dealNewCarFinanceData(newCarFinanceService.getNewCarFinanceList()));
        return mav;
    }

    @RequestMapping(value = "/newCarFinanceAction", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView newCarFinanceAction(
            @RequestParam(value = "action", required = true, defaultValue = "") Integer action,
            @RequestParam(value = "over", required = false, defaultValue = "") Integer over,
            @RequestParam(value = "id", required = false, defaultValue = "") Integer id,
            @RequestParam(value = "financeCompany", required = false, defaultValue = "") String financeCompany,
            @RequestParam(value = "carBrand", required = false, defaultValue = "") String carBrand,
            @RequestParam(value = "carModel", required = false, defaultValue = "") String carModel,
            @RequestParam(value = "carConfig", required = false, defaultValue = "") String carConfig,
            @RequestParam(value = "guidancePrice", required = false, defaultValue = "") Double guidancePrice,
            @RequestParam(value = "downPayments", required = false, defaultValue = "") Double downPayments,
            @RequestParam(value = "monthMortgage", required = false, defaultValue = "") Double monthMortgage,
            @RequestParam(value = "serviceFee", required = false, defaultValue = "") Double serviceFee,
            @RequestParam(value = "otherFee", required = false, defaultValue = "") Double otherFee,
            @RequestParam(value = "consumerProperty", required = false, defaultValue = "") Integer consumerProperty,
            @RequestParam(value = "consumerResource", required = false, defaultValue = "") Integer consumerResource,
            @RequestParam(value = "purchaseUse", required = false, defaultValue = "") Integer purchaseUse,
            @RequestParam(value = "consumerName", required = false, defaultValue = "") String consumerName,
            @RequestParam(value = "consumerSex", required = false, defaultValue = "") Integer consumerSex,
            @RequestParam(value = "consumerAge", required = false, defaultValue = "") Integer consumerAge,
            @RequestParam(value = "consumerAddress", required = false, defaultValue = "") String consumerAddress,
            @RequestParam(value = "consumerPhone", required = false, defaultValue = "") String consumerPhone) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("newCar/financeAdd");
        mav.addObject("action", action);
        ToolUtil.initCarPropertyData(mav, cacheCenter);

        mav.addObject("financeCompany", financeCompany);
        mav.addObject("carBrand", carBrand);
        mav.addObject("carModel", carModel);
        mav.addObject("carConfig", carConfig);
        mav.addObject("guidancePrice", guidancePrice);
        mav.addObject("downPayments", downPayments);
        mav.addObject("monthMortgage", monthMortgage);
        mav.addObject("serviceFee", serviceFee);
        mav.addObject("otherFee", otherFee);
        mav.addObject("consumerProperty", consumerProperty);
        mav.addObject("consumerResource", consumerResource);
        mav.addObject("purchaseUse", purchaseUse);
        mav.addObject("consumerName", consumerName);
        mav.addObject("consumerSex", consumerSex);
        mav.addObject("consumerAge", consumerAge);
        mav.addObject("consumerAddress", consumerAddress);
        mav.addObject("consumerPhone", consumerPhone);

        if (over == null) {
            if (id != null) {
                NewCarFinance newCarFinance = newCarFinanceService.getNewCarFinanceById(id);
                mav.addObject("id", id);
                mav.addObject("financeCompany", newCarFinance.getFinanceCompany());
                mav.addObject("carBrand", newCarFinance.getCarBrand());
                mav.addObject("carModel", newCarFinance.getCarModel());
                mav.addObject("carConfig", newCarFinance.getCarConfig());
                mav.addObject("guidancePrice", newCarFinance.getGuidancePrice());
                mav.addObject("downPayments", newCarFinance.getDownPayments());
                mav.addObject("monthMortgage", newCarFinance.getMonthMortgage());
                mav.addObject("serviceFee", newCarFinance.getServiceFee());
                mav.addObject("otherFee", newCarFinance.getOtherFee());
                mav.addObject("consumerProperty", newCarFinance.getConsumerProperty());
                mav.addObject("consumerResource", newCarFinance.getConsumerResource());
                mav.addObject("purchaseUse", newCarFinance.getPurchaseUse());
                mav.addObject("consumerName", newCarFinance.getConsumerName());
                mav.addObject("consumerSex", newCarFinance.getConsumerSex());
                mav.addObject("consumerAge", newCarFinance.getConsumerAge());
                mav.addObject("consumerAddress", newCarFinance.getConsumerAddress());
                mav.addObject("consumerPhone", newCarFinance.getConsumerPhone());
            }
            return mav;
        } else {
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

            NewCarFinance newCarFinance = new NewCarFinance();
            newCarFinance.setFinanceCompany(financeCompany);
            newCarFinance.setCarBrand(carBrand);
            newCarFinance.setCarModel(carModel);
            newCarFinance.setCarConfig(carConfig);
            newCarFinance.setGuidancePrice(guidancePrice);
            newCarFinance.setDownPayments(downPayments);
            newCarFinance.setMonthMortgage(monthMortgage);
            newCarFinance.setServiceFee(serviceFee);
            newCarFinance.setOtherFee(otherFee);
            newCarFinance.setConsumerProperty(consumerProperty);
            newCarFinance.setConsumerResource(consumerResource);
            newCarFinance.setPurchaseUse(purchaseUse);
            newCarFinance.setConsumerName(consumerName);
            newCarFinance.setConsumerSex(consumerSex);
            newCarFinance.setConsumerAge(consumerAge);
            newCarFinance.setConsumerAddress(consumerAddress);
            newCarFinance.setConsumerPhone(consumerPhone);

            mav.clear();
            if (action.equals(CREATE_ACTION)) {
                newCarFinance.setDisplayStatus(ContextType.DISPLAY_STATUS_SHOW);
                newCarFinance.setOtherCost(0.0);
                newCarFinanceService.create(newCarFinance);
            } else if (action.equals(MOD_ACTION)) {
                newCarFinance.setId(id);
                newCarFinanceService.updateNewCarFinance(newCarFinance);
            }
            mav.setViewName("redirect:/newCarFinanceView");
            return mav;
        }
    }

    @RequestMapping(value = "/newCarFinanceDelete", method = RequestMethod.GET)
    public ModelAndView newCarFinanceDelete(
            @RequestParam(value = "id", required = false, defaultValue = "") Integer id) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:/newCarFinanceView");
        carSaleSetupService.deleteCarSaleSetupByIdAndType(id, ContextType.NEW_CAR_FIANCE_COST_TYPE);
        newCarFinanceService.deleteById(id);
        return mav;
    }
}
