package cm.lx.controller.car;

import cm.lx.bean.ContextBean;
import cm.lx.business.CacheCenter;
import cm.lx.business.ToolUtil;
import cm.lx.common.ContextType;
import cm.lx.controller.BaseController;
import cm.lx.bean.entity.CarCost;
import cm.lx.bean.entity.CarRecord;
import cm.lx.service.CarCostService;
import cm.lx.service.CarRecordService;
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
public class CarStockController extends BaseController {

    @Resource
    CacheCenter cacheCenter;

    @Resource
    CarRecordService carRecordService;

    @Resource
    CarCostService carCostService;

    @RequestMapping(value = "/carStockView", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView carStockView(HttpSession session) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("car/stockList");
        List<ContextBean> list = cacheCenter.getCarRecordCombinationInfo(carRecordService.getCarRecordByRecordStatus(ContextType.RECORD_STATUS_STOCK));
        mav.addObject("carNum", list.size());
        mav.addObject("list", list);
        mav.addObject(TIP, session.getAttribute("tip"));
        return mav;
    }

    @RequestMapping(value = "/carCostAction", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView carCostAction(
            @RequestParam(value = "action", required = true, defaultValue = "") Integer action,
            @RequestParam(value = "over", required = false, defaultValue = "") Integer over,
            @RequestParam(value = "id", required = false, defaultValue = "") Integer id,
            @RequestParam(value = "recordStatus", required = false, defaultValue = "") Integer recordStatus,
            @RequestParam(value = "recordId", required = false, defaultValue = "") Integer recordId,
            @RequestParam(value = "carPickPerson", required = false, defaultValue = "") String carPickPerson,
            @RequestParam(value = "mentionFee", required = false, defaultValue = "") Double mentionFee,
            @RequestParam(value = "mentionSubsidy", required = false, defaultValue = "") Double mentionSubsidy,
            @RequestParam(value = "travelFee", required = false, defaultValue = "") Double travelFee,
            @RequestParam(value = "putFee", required = false, defaultValue = "") Double putFee,
            @RequestParam(value = "putSubsidy", required = false, defaultValue = "") Double putSubsidy,
            @RequestParam(value = "crossingFee", required = false, defaultValue = "") Double crossingFee,
            @RequestParam(value = "mailFee", required = false, defaultValue = "") Double mailFee,
            @RequestParam(value = "freightFee", required = false, defaultValue = "") Double freightFee,
            @RequestParam(value = "billingFee", required = false, defaultValue = "") Double billingFee,
            @RequestParam(value = "oilFee", required = false, defaultValue = "") Double oilFee,
            @RequestParam(value = "cattleFee", required = false, defaultValue = "") Double cattleFee,
            @RequestParam(value = "expenseFee", required = false, defaultValue = "") Double expenseFee,
            @RequestParam(value = "otherFee", required = false, defaultValue = "") Double otherFee,
            HttpSession session) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("car/costAdd");
        mav.addObject("action", action);
        mav.addObject("recordStatus", recordStatus == null ? ContextType.RECORD_STATUS_STOCK : recordStatus);
        mav.addObject("recordId", recordId);

        mav.addObject("carPickPerson", carPickPerson);
        mav.addObject("mentionFee", mentionFee);
        mav.addObject("mentionSubsidy", mentionSubsidy);
        mav.addObject("travelFee", travelFee);
        mav.addObject("putFee", putFee);
        mav.addObject("putSubsidy", putSubsidy);
        mav.addObject("crossingFee", crossingFee);
        mav.addObject("mailFee", mailFee);
        mav.addObject("freightFee", freightFee);
        mav.addObject("billingFee", billingFee);
        mav.addObject("oilFee", oilFee);
        mav.addObject("cattleFee", cattleFee);
        mav.addObject("expenseFee", expenseFee);
        mav.addObject("otherFee", otherFee);

        if (over == null) {
            if (id != null) {
                CarCost carCost = carCostService.getCarCostById(id);
                mav.addObject("id", id);
                mav.addObject("recordId", carCost.getCarRecordId());
                mav.addObject("carPickPerson", carCost.getCarPickPerson());
                mav.addObject("mentionFee", carCost.getMentionFee());
                mav.addObject("mentionSubsidy", carCost.getMentionSubsidy());
                mav.addObject("travelFee", carCost.getTravelFee());
                mav.addObject("putFee", carCost.getPutFee());
                mav.addObject("putSubsidy", carCost.getPutSubsidy());
                mav.addObject("crossingFee", carCost.getCrossingFee());
                mav.addObject("mailFee", carCost.getMailFee());
                mav.addObject("freightFee", carCost.getFreightFee());
                mav.addObject("billingFee", carCost.getBillingFee());
                mav.addObject("oilFee", carCost.getOilFee());
                mav.addObject("cattleFee", carCost.getCattleFee());
                mav.addObject("expenseFee", carCost.getExpenseFee());
                mav.addObject("otherFee", carCost.getOtherFee());
            }
            return mav;
        } else {
            if (StringUtils.isEmpty(carPickPerson)) {
                mav.addObject(TIP, "提车经办人不能为空！");
                return mav;
            }

            CarCost carCost = new CarCost();
            carCost.setCarPickPerson(carPickPerson);
            carCost.setMentionFee(mentionFee == null ? 0 : mentionFee);
            carCost.setMentionSubsidy(mentionSubsidy == null ? 0 : mentionSubsidy);
            carCost.setTravelFee(travelFee == null ? 0 : travelFee);
            carCost.setPutFee(putFee == null ? 0 : putFee);
            carCost.setPutSubsidy(putSubsidy == null ? 0 : putSubsidy);
            carCost.setCrossingFee(crossingFee == null ? 0 : crossingFee);
            carCost.setMailFee(mailFee == null ? 0 : mailFee);
            carCost.setFreightFee(freightFee == null ? 0 : freightFee);
            carCost.setBillingFee(billingFee == null ? 0 : billingFee);
            carCost.setOilFee(oilFee == null ? 0 : oilFee);
            carCost.setCattleFee(cattleFee == null ? 0 : cattleFee);
            carCost.setExpenseFee(expenseFee == null ? 0 : expenseFee);
            carCost.setOtherFee(otherFee == null ? 0 : otherFee);

            mav.clear();
            if (action.equals(CREATE_ACTION)) {
                carCost.setCarRecordId(recordId);
                carCost.setPreSaleFee(0.0);
                carCost.setAfterSaleFee(0.0);
                carCost.setOtherIncomeFee(0.0);
                carCostService.createCarCost(carCost);
                session.setAttribute("tip", "ok 车辆成本录入成功！");

                //绑定成本录入ID
                CarRecord carRecord = new CarRecord();
                carRecord.setId(recordId);
                carRecord.setIsCost(1);
                carRecord.setCostId(carCost.getId());
                carRecordService.updateCarRecord(carRecord);
            } else if (action.equals(MOD_ACTION)) {
                carCost.setId(id);
                carCostService.updateCarCost(carCost);

                //刷新缓存
                cacheCenter.deleteCarRecordInfo(recordId);

                session.setAttribute("tip", "ok 车辆成本修改成功！");
            }

            ToolUtil.setMavView(mav, recordStatus);
            return mav;
        }
    }
}
