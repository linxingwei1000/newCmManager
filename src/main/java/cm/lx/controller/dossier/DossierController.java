package cm.lx.controller.dossier;

import cm.lx.bean.entity.CarDossier;
import cm.lx.bean.entity.CarPaidRecord;
import cm.lx.bean.entity.CarRecord;
import cm.lx.common.ContextType;
import cm.lx.controller.BaseController;
import cm.lx.service.CarDossierService;
import cm.lx.service.CarPaidRecordService;
import cm.lx.service.CarRecordService;
import cm.lx.service.CarRemarkService;
import cm.lx.util.TimeUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class DossierController extends BaseController {

    @Resource
    CarRecordService carRecordService;

    @Resource
    CarDossierService carDossierService;

    @Resource
    CarPaidRecordService carPaidRecordService;

    @Resource
    CarRemarkService carRemarkService;

    private List<CarDossier> prepareData(List<CarDossier> list) {
        for (CarDossier carDossier : list) {
            CarRecord carRecord = carRecordService.getCarRecordById(carDossier.getCarRecordId());
            carRecord.setStrPurchaseDate(TimeUtils.transformTimetagToDate(carRecord.getPurchaseDate(), TimeUtils.FORMAT_ONE));
            carDossier.setCarRecord(carRecord);

            if (carDossier.getQzxDate() != null && carDossier.getQzxDate() != 0) {
                carDossier.setStrQzxDate(TimeUtils.transformTimetagToDate(carDossier.getQzxDate(), TimeUtils.FORMAT_ONE));
            }

            if (carDossier.getSyxDate() != null && carDossier.getSyxDate() != 0) {
                carDossier.setStrSyxDate(TimeUtils.transformTimetagToDate(carDossier.getSyxDate(), TimeUtils.FORMAT_ONE));
            }

            if (carDossier.getAnnualTrial() != null && carDossier.getAnnualTrial() != 0) {
                carDossier.setStrAnnualTrial(TimeUtils.transformTimetagToDate(carDossier.getAnnualTrial(), TimeUtils.FORMAT_ONE));
            }

            carDossier.setBudgetList(carPaidRecordService.getCarPaidRecordByLinkIdAndType(carDossier.getId(), ContextType.PAY_RECORD_BUDGET));
            carDossier.setRemarkList(carRemarkService.getCarRemarkByLinkIdAndType(carDossier.getId(), ContextType.REMARK_TYPE_DOSSIER));
        }
        return list;
    }

    @RequestMapping(value = "/dossierView")
    public ModelAndView dossierView(HttpSession session) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("dossier/list");
        mav.addObject(DATA, prepareData(carDossierService.getCarDossierListByDisplayStatus(ContextType.DISPLAY_STATUS_SHOW)));
        mav.addObject(TIP, session.getAttribute("tip"));
        mav.addObject("displayStatus", ContextType.DISPLAY_STATUS_SHOW);
        return mav;
    }

    @RequestMapping(value = "/dossierDoneView")
    public ModelAndView dossierDoneView(HttpSession session) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("dossier/list");
        mav.addObject(DATA, prepareData(carDossierService.getCarDossierListByDisplayStatus(ContextType.DISPLAY_STATUS_DONE)));
        mav.addObject(TIP, session.getAttribute("tip"));
        mav.addObject("displayStatus", ContextType.DISPLAY_STATUS_DONE);
        return mav;
    }

    @RequestMapping(value = "/dossierAction", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView dossierAction(
            @RequestParam(value = "action", required = true, defaultValue = "") Integer action,
            @RequestParam(value = "over", required = false, defaultValue = "") Integer over,
            @RequestParam(value = "carRecordId", required = false, defaultValue = "") Integer carRecordId,
            @RequestParam(value = "id", required = false, defaultValue = "") Integer id,
            @RequestParam(value = "carCreateTime", required = false, defaultValue = "") String carCreateTime,
            @RequestParam(value = "carPurchaseTime", required = false, defaultValue = "") String carPurchaseTime,
            @RequestParam(value = "carKeyNum", required = false, defaultValue = "") Integer carKeyNum,
            @RequestParam(value = "carOwner", required = false, defaultValue = "") String carOwner,
            @RequestParam(value = "carNum", required = false, defaultValue = "") String carNum,
            @RequestParam(value = "purchaseTimes", required = false, defaultValue = "") Integer purchaseTimes,
            @RequestParam(value = "carNumResource", required = false, defaultValue = "") String carNumResource,
            @RequestParam(value = "qzxDate", required = false, defaultValue = "") String qzxDate,
            @RequestParam(value = "qzxPerson", required = false, defaultValue = "") String qzxPerson,
            @RequestParam(value = "qzxCompany", required = false, defaultValue = "") String qzxCompany,
            @RequestParam(value = "qzxAddress", required = false, defaultValue = "") String qzxAddress,
            @RequestParam(value = "syxDate", required = false, defaultValue = "") String syxDate,
            @RequestParam(value = "syxPerson", required = false, defaultValue = "") String syxPerson,
            @RequestParam(value = "syxCompany", required = false, defaultValue = "") String syxCompany,
            @RequestParam(value = "syxAddress", required = false, defaultValue = "") String syxAddress,
            @RequestParam(value = "billSign", required = false, defaultValue = "") Integer billSign,
            @RequestParam(value = "register", required = false, defaultValue = "") Integer register,
            @RequestParam(value = "drivingLicense", required = false, defaultValue = "") Integer drivingLicense,
            @RequestParam(value = "breakRule", required = false, defaultValue = "") Integer breakRule,
            @RequestParam(value = "qzxStick", required = false, defaultValue = "") Integer qzxStick,
            @RequestParam(value = "carDischarge", required = false, defaultValue = "") String carDischarge,
            @RequestParam(value = "annualTrial", required = false, defaultValue = "") String annualTrial,
            @RequestParam(value = "abs", required = false, defaultValue = "") String abs) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("dossier/add");
        mav.addObject("action", action);
        mav.addObject("carRecordId", carRecordId);

        mav.addObject("carCreateTime", carCreateTime);
        mav.addObject("carPurchaseTime", carPurchaseTime);
        mav.addObject("carKeyNum", carKeyNum);
        mav.addObject("carOwner", carOwner);
        mav.addObject("carNum", carNum);
        mav.addObject("purchaseTimes", purchaseTimes);
        mav.addObject("carNumResource", carNumResource);
        mav.addObject("qzxDate", qzxDate);
        mav.addObject("qzxPerson", qzxPerson);
        mav.addObject("qzxCompany", qzxCompany);
        mav.addObject("qzxAddress", qzxAddress);
        mav.addObject("syxDate", syxDate);
        mav.addObject("syxPerson", syxPerson);
        mav.addObject("syxCompany", syxCompany);
        mav.addObject("syxAddress", syxAddress);
        mav.addObject("billSign", billSign);
        mav.addObject("register", register);
        mav.addObject("drivingLicense", drivingLicense);
        mav.addObject("breakRule", breakRule);
        mav.addObject("qzxStick", qzxStick);
        mav.addObject("carDischarge", carDischarge);
        mav.addObject("annualTrial", annualTrial);
        mav.addObject("abs", abs);

        if (over == null) {
            if (id != null) {
                CarDossier carDossier = carDossierService.getCarDossierById(id);
                CarRecord carRecord = carRecordService.getCarRecordById(carDossier.getCarRecordId());
                mav.addObject("id", id);
                mav.addObject("carCreateTime", carRecord.getCarCreateTime());
                mav.addObject("carPurchaseTime", carRecord.getCarPurchaseTime());
                mav.addObject("carKeyNum", carDossier.getCarKeyNum());
                mav.addObject("carOwner", carDossier.getCarOwner());
                mav.addObject("carNum", carDossier.getCarNum());
                mav.addObject("purchaseTimes", carDossier.getPurchaseTimes());
                mav.addObject("carNumResource", carDossier.getCarNumResource());
                mav.addObject("qzxDate", TimeUtils.transformTimetagToDate(carDossier.getQzxDate(), TimeUtils.FORMAT_ONE));
                mav.addObject("qzxPerson", carDossier.getQzxPerson());
                mav.addObject("qzxCompany", carDossier.getQzxCompany());
                mav.addObject("qzxAddress", carDossier.getQzxAddress());
                mav.addObject("syxDate", TimeUtils.transformTimetagToDate(carDossier.getSyxDate(), TimeUtils.FORMAT_ONE));
                mav.addObject("syxPerson", carDossier.getSyxPerson());
                mav.addObject("syxCompany", carDossier.getSyxCompany());
                mav.addObject("syxAddress", carDossier.getSyxAddress());
                mav.addObject("billSign", carDossier.getBillSign());
                mav.addObject("register", carDossier.getRegister());
                mav.addObject("drivingLicense", carDossier.getDrivingLicense());
                mav.addObject("breakRule", carDossier.getBreakRule());
                mav.addObject("qzxStick", carDossier.getQzxStick());
                mav.addObject("carDischarge", carDossier.getCarDischarge());
                mav.addObject("annualTrial", TimeUtils.transformTimetagToDate(carDossier.getAnnualTrial(), TimeUtils.FORMAT_ONE));
                mav.addObject("abs", carDossier.getAbs());
            }
            return mav;
        } else {
            CarDossier carDossier = new CarDossier();
            carDossier.setCarKeyNum(carKeyNum);
            carDossier.setCarOwner(carOwner);
            carDossier.setCarNum(carNum);
            carDossier.setPurchaseTimes(purchaseTimes);
            carDossier.setCarNumResource(carNumResource);
            carDossier.setQzxDate(TimeUtils.transformDateToTimetag(qzxDate, TimeUtils.FORMAT_ONE));
            carDossier.setQzxPerson(qzxPerson);
            carDossier.setQzxCompany(qzxCompany);
            carDossier.setQzxAddress(qzxAddress);
            carDossier.setSyxDate(TimeUtils.transformDateToTimetag(syxDate, TimeUtils.FORMAT_ONE));
            carDossier.setSyxPerson(syxPerson);
            carDossier.setSyxCompany(syxCompany);
            carDossier.setSyxAddress(syxAddress);
            carDossier.setBillSign(billSign);
            carDossier.setRegister(register);
            carDossier.setQzxStick(qzxStick);
            carDossier.setDrivingLicense(drivingLicense);
            carDossier.setBreakRule(breakRule);
            carDossier.setCarDischarge(carDischarge);
            carDossier.setAnnualTrial(TimeUtils.transformDateToTimetag(annualTrial, TimeUtils.FORMAT_ONE));
            carDossier.setAbs(abs);

            CarRecord carRecord = new CarRecord();
            carRecord.setId(carRecordId);
            carRecord.setCarCreateTime(carCreateTime);
            carRecord.setCarPurchaseTime(carPurchaseTime);
            carRecordService.updateCarRecord(carRecord);

            mav.clear();
            if (action.equals(CREATE_ACTION)) {
                carDossier.setDisplayStatus(ContextType.DISPLAY_STATUS_SHOW);
                carDossierService.createCarDossier(carDossier);
            } else if (action.equals(MOD_ACTION)) {
                carDossier.setId(id);
                carDossierService.updateCarDossierById(carDossier);
            }
            mav.setViewName("redirect:/dossierView");
            return mav;
        }
    }

    @RequestMapping(value = "/dossierDelete", method = RequestMethod.GET)
    public ModelAndView dossierDelete(
            @RequestParam(value = "id", required = false, defaultValue = "") Integer id) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:/dossierView");
        carDossierService.deleteCarDossierById(id);
        return mav;
    }

    @RequestMapping(value = "/dossierStatus", method = RequestMethod.GET)
    public ModelAndView dossierStatus(
            @RequestParam(value = "id", required = false, defaultValue = "") Integer id,
            @RequestParam(value = "displayStatus", required = false, defaultValue = "") Integer displayStatus,
            HttpSession session) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:/dossierView");
        CarDossier update = new CarDossier();
        update.setId(id);
        update.setDisplayStatus(displayStatus);
        carDossierService.updateCarDossierById(update);
        session.setAttribute("tip", "ok 档案状态更改成功！");
        return mav;
    }

    @RequestMapping(value = "/dossierBudget", method = RequestMethod.GET)
    public ModelAndView dossierBudget(
            @RequestParam(value = "goonPaid", required = false, defaultValue = "") Double goonPaid,
            @RequestParam(value = "paidReason", required = false, defaultValue = "") String paidReason,
            @RequestParam(value = "id", required = false, defaultValue = "") Integer id,
            HttpSession session) {
        if (goonPaid == null) {
            goonPaid = 0.0;
        }
        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:/dossierView");

        CarDossier carDossier = carDossierService.getCarDossierById(id);
        Double old = carDossier.getInsuranceBudget() == null ? 0 : carDossier.getInsuranceBudget();
        Double insuranceBudget = old + goonPaid;

        CarDossier update = new CarDossier();
        update.setId(id);
        update.setInsuranceBudget(insuranceBudget);
        carDossierService.updateCarDossierById(update);
        session.setAttribute("tip", "ok 预算添加成功！");

        //创建付款记录
        CarPaidRecord carPaidRecord = new CarPaidRecord();
        carPaidRecord.setCarRecordId(id);
        carPaidRecord.setRecordStatus(ContextType.PAY_RECORD_BUDGET);
        carPaidRecord.setPaidMoney(goonPaid);
        carPaidRecord.setPaidReason(paidReason);
        carPaidRecordService.create(carPaidRecord);

        return mav;
    }

    @RequestMapping(value = "/dossierBudgetDel", method = RequestMethod.GET)
    public ModelAndView dossierBudgetDel(
            @RequestParam(value = "id", required = false, defaultValue = "") Integer id,
            HttpSession session) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:/dossierView");
        carPaidRecordService.deleteCarPaidRecordById(id);
        session.setAttribute("tip", "ok 删除成功！");
        return mav;
    }

    @RequestMapping(value = "/dossierCarStatus", method = RequestMethod.GET)
    public ModelAndView dossierCarStatus(
            @RequestParam(value = "id", required = false, defaultValue = "") Integer id,
            @RequestParam(value = "carStatus", required = false, defaultValue = "") Integer carStatus,
            HttpSession session) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:/dossierView");
        CarDossier update = new CarDossier();
        update.setId(id);
        update.setCarStatus(carStatus);
        carDossierService.updateCarDossierById(update);
        session.setAttribute("tip", "ok 车辆状态修改成功！");
        return mav;
    }
}
