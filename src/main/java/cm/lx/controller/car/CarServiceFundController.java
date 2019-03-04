package cm.lx.controller.car;

import cm.lx.controller.BaseController;
import cm.lx.bean.entity.ServiceFund;
import cm.lx.service.CarSaleInfoService;
import cm.lx.service.ServiceFundService;
import cm.lx.util.TimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 售后服务基金
 *
 * @author linxingwei
 * @date 2019/1/23
 */
@Controller
public class CarServiceFundController extends BaseController {

    Logger logger = LoggerFactory.getLogger(CarServiceFundController.class);

    @Resource
    CarSaleInfoService carSaleInfoService;

    @Resource
    ServiceFundService serviceFundService;


    @RequestMapping(value = "/carServiceFundView", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView carServiceFundView() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("car/serviceFund");
        List<ServiceFund> list = serviceFundService.getServiceFundList();

        //总服务基金
        Double allServiceFund = carSaleInfoService.getServiceMoney();
        if (allServiceFund == null) {
            allServiceFund = 0.0;
        }

        //使用过的服务基金
        Double useServiceFund = 0.0;
        for (ServiceFund serviceFund : list) {
            serviceFund.setStrUseDate(TimeUtils.transformTimetagToDate(serviceFund.getUseDate(), TimeUtils.FORMAT_ONE));
            useServiceFund += serviceFund.getFee();
        }

        //剩余服务基金
        Double remainServiceFund = allServiceFund - useServiceFund;

        mav.addObject(DATA, list);
        mav.addObject("allServiceFund", allServiceFund);
        mav.addObject("useServiceFund", useServiceFund);
        mav.addObject("remainServiceFund", remainServiceFund);
        return mav;
    }


    @RequestMapping(value = "/carServiceFundAction", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView carServiceFundAction(
            @RequestParam(value = "action", required = true, defaultValue = "") Integer action,
            @RequestParam(value = "over", required = false, defaultValue = "") Integer over,
            @RequestParam(value = "id", required = false, defaultValue = "") Integer id,
            @RequestParam(value = "useDate", required = false, defaultValue = "") String useDate,
            @RequestParam(value = "person", required = false, defaultValue = "") String person,
            @RequestParam(value = "project", required = false, defaultValue = "") String project,
            @RequestParam(value = "fee", required = false, defaultValue = "") Double fee,
            @RequestParam(value = "remark", required = false, defaultValue = "") String remark,
            HttpSession session) {

        ModelAndView mav = new ModelAndView();
        mav.setViewName("car/serviceFundAdd");
        mav.addObject("action", action);

        mav.addObject("useDate", useDate);
        mav.addObject("person", person);
        mav.addObject("project", project);
        mav.addObject("fee", fee);
        mav.addObject("remark", remark);

        if (over == null) {
            if (id != null) {
                ServiceFund serviceFund = serviceFundService.getServiceFundById(id);
                mav.addObject("id", id);
                mav.addObject("useDate", TimeUtils.transformTimetagToDate(serviceFund.getUseDate(), TimeUtils.FORMAT_ONE));
                mav.addObject("person", serviceFund.getPerson());
                mav.addObject("project", serviceFund.getProject());
                mav.addObject("fee", serviceFund.getFee());
                mav.addObject("remark", serviceFund.getRemark());
            }
            return mav;
        } else {
            ServiceFund serviceFund = new ServiceFund();
            serviceFund.setUseDate(TimeUtils.transformDateToTimetag(useDate, TimeUtils.FORMAT_ONE));
            serviceFund.setPerson(person);
            serviceFund.setProject(project);
            serviceFund.setFee(fee == null ? 0 : fee);
            serviceFund.setRemark(remark);

            mav.clear();
            if (action.equals(CREATE_ACTION)) {
                serviceFundService.createServiceFund(serviceFund);
            } else if (action.equals(MOD_ACTION)) {
                serviceFund.setId(id);
                serviceFundService.updateServiceFund(serviceFund);
            }
            mav.setViewName("redirect:/carServiceFundView");
            return mav;
        }
    }

    @RequestMapping(value = "/carServiceFundDelete", method = RequestMethod.GET)
    public ModelAndView carServiceFundDelete(
            @RequestParam(value = "id", required = false, defaultValue = "") Integer id) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:/carServiceFundView");
        serviceFundService.deleteById(id);
        return mav;
    }
}
