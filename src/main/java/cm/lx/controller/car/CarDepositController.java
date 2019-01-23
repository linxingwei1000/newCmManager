package cm.lx.controller.car;

import cm.lx.bean.entity.CarDeposit;
import cm.lx.common.ContextType;
import cm.lx.controller.BaseController;
import cm.lx.bean.entity.CarPaidRecord;
import cm.lx.service.CarDepositService;
import cm.lx.service.CarPaidRecordService;
import cm.lx.util.TimeUtils;
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
 * 定金寻车
 *
 * @author linxingwei
 * @date 2019/1/21
 */
@Controller
public class CarDepositController extends BaseController {

    @Resource
    CarDepositService carDepositService;

    @Resource
    CarPaidRecordService carPaidRecordService;

    private List<CarDeposit> dealDepositList(List<CarDeposit> list) {
        for (CarDeposit carDeposit : list) {
            carDeposit.setStrDepositDate(TimeUtils.transformTimetagToDate(carDeposit.getDepositDate(), TimeUtils.FORMAT_ONE));
            carDeposit.setStrGiveCarDate(TimeUtils.transformTimetagToDate(carDeposit.getGiveCarDate(), TimeUtils.FORMAT_ONE));
            carDeposit.setDepositPaidList(carPaidRecordService.getCarPaidRecordByLinkIdAndType(carDeposit.getId(), ContextType.PAY_RECORD_DEPOSIT));
        }
        return list;
    }

    @RequestMapping(value = "/carDepositView", method = RequestMethod.GET)
    public ModelAndView carDepositView(HttpSession session) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("car/depositList");
        mav.addObject("list", dealDepositList(carDepositService.getCarDepositList()));
        mav.addObject(TIP, session.getAttribute("tip"));
        return mav;
    }

    @RequestMapping(value = "/carDepositAction", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView carDepositAction(
            @RequestParam(value = "action", required = true, defaultValue = "") Integer action,
            @RequestParam(value = "over", required = false, defaultValue = "") Integer over,
            @RequestParam(value = "id", required = false, defaultValue = "") Integer id,
            @RequestParam(value = "salePerson", required = false, defaultValue = "") String salePerson,
            @RequestParam(value = "depositDate", required = false, defaultValue = "") String depositDate,
            @RequestParam(value = "carModel", required = false, defaultValue = "") String carModel,
            @RequestParam(value = "carColor", required = false, defaultValue = "") String carColor,
            @RequestParam(value = "carYear", required = false, defaultValue = "") String carYear,
            @RequestParam(value = "carConfig", required = false, defaultValue = "") String carConfig,
            @RequestParam(value = "budget", required = false, defaultValue = "") String budget,
            @RequestParam(value = "giveCarDate", required = false, defaultValue = "") String giveCarDate,
            @RequestParam(value = "deposit", required = false, defaultValue = "") Double deposit,
            HttpSession session) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("car/depositAdd");
        mav.addObject("action", action);

        mav.addObject("salePerson", salePerson);
        mav.addObject("depositDate", depositDate);
        mav.addObject("carModel", carModel);
        mav.addObject("carColor", carColor);
        mav.addObject("carYear", carYear);
        mav.addObject("carConfig", carConfig);
        mav.addObject("budget", budget);
        mav.addObject("giveCarDate", giveCarDate);
        mav.addObject("deposit", deposit);
        if (over == null) {
            if (id != null) {
                CarDeposit carDeposit = carDepositService.getCarDepositById(id);
                mav.addObject("id", id);
                mav.addObject("salePerson", carDeposit.getSalePerson());
                mav.addObject("depositDate", TimeUtils.transformTimetagToDate(carDeposit.getDepositDate(), TimeUtils.FORMAT_ONE));
                mav.addObject("carModel", carDeposit.getCarModel());
                mav.addObject("carColor", carDeposit.getCarColor());
                mav.addObject("carYear", carDeposit.getCarYear());
                mav.addObject("carConfig", carDeposit.getCarConfig());
                mav.addObject("budget", carDeposit.getBudget());
                mav.addObject("giveCarDate", TimeUtils.transformTimetagToDate(carDeposit.getGiveCarDate(), TimeUtils.FORMAT_ONE));
                mav.addObject("deposit", carDeposit.getDeposit());
            }
            return mav;
        } else {
            if (StringUtils.isEmpty(salePerson)) {
                mav.addObject(TIP, "销售员必填！");
                return mav;
            }

            if (StringUtils.isEmpty(depositDate)) {
                mav.addObject(TIP, "收订金日期必填！");
                return mav;
            }

            if (StringUtils.isEmpty(giveCarDate)) {
                mav.addObject(TIP, "交车日期必填！");
                return mav;
            }

            CarDeposit carDeposit = new CarDeposit();
            carDeposit.setSalePerson(salePerson);
            carDeposit.setDepositDate(TimeUtils.transformDateToTimetag(depositDate, TimeUtils.FORMAT_ONE));
            carDeposit.setCarModel(carModel);
            carDeposit.setCarColor(carColor);
            carDeposit.setCarYear(carYear);
            carDeposit.setCarConfig(carConfig);
            carDeposit.setBudget(budget);
            carDeposit.setGiveCarDate(TimeUtils.transformDateToTimetag(giveCarDate, TimeUtils.FORMAT_ONE));
            carDeposit.setDeposit(deposit);
            mav.clear();
            if (action.equals(CREATE_ACTION)) {
                carDepositService.create(carDeposit);
                session.setAttribute(TIP, "ok 创建成功！");
            } else if (action.equals(MOD_ACTION)) {
                carDeposit.setId(id);
                carDepositService.updateById(carDeposit);
                session.setAttribute(TIP, "ok 修改成功！");
            }
            mav.setViewName("redirect:/carDepositView");
            return mav;
        }
    }

    @RequestMapping(value = "/carDepositPaid", method = RequestMethod.GET)
    public ModelAndView carDepositPaid(
            @RequestParam(value = "goonPaid", required = false, defaultValue = "") Double goonPaid,
            @RequestParam(value = "paidReason", required = false, defaultValue = "") String paidReason,
            @RequestParam(value = "id", required = false, defaultValue = "") Integer id,
            HttpSession session) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:/carDepositView");
        if (goonPaid == null) {
            session.setAttribute("tip", "付款金额未填！");
            return mav;
        }
        CarDeposit old = carDepositService.getCarDepositById(id);

        CarDeposit update = new CarDeposit();
        update.setId(id);
        update.setDeposit(old.getDeposit() + goonPaid);
        carDepositService.updateById(update);
        session.setAttribute("tip", "ok 付款成功！");

        //创建付款记录
        CarPaidRecord carPaidRecord = new CarPaidRecord();
        carPaidRecord.setCarRecordId(id);
        carPaidRecord.setRecordStatus(ContextType.PAY_RECORD_DEPOSIT);
        carPaidRecord.setPaidMoney(goonPaid);
        carPaidRecord.setPaidReason(paidReason);
        carPaidRecordService.create(carPaidRecord);

        return mav;
    }

    @RequestMapping(value = "/carDepositDelete", method = RequestMethod.GET)
    public ModelAndView carDepositDelete(
            @RequestParam(value = "id", required = false, defaultValue = "") Integer id, HttpSession session) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:/carDepositView");

        //删除付款记录表
        carPaidRecordService.deleteCarPaidRecordByLinkIdAndType(id, ContextType.PAY_RECORD_DEPOSIT);

        carDepositService.deleteById(id);
        session.setAttribute(TIP, "ok 删除成功！");
        return mav;
    }
}
