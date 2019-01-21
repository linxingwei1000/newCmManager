package cm.lx.controller;

import cm.lx.bean.MoneyManager;
import cm.lx.bean.MortgageRebate;
import cm.lx.business.DaoCenter;
import cm.lx.business.StatCenter;
import cm.lx.common.ContextType;
import cm.lx.util.TimeUtils;
import cm.lx.util.Utils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class MoneyController extends BaseController {

    @Resource
    DaoCenter daoCenter;

    List<MoneyManager> cacheList = new ArrayList<MoneyManager>();

    private List<MoneyManager> dealMoneyOther(List<MoneyManager> list) {
        for (MoneyManager moneyManager : list) {
            moneyManager.setStrActionDate(TimeUtils.transformTimetagToDate(moneyManager.getActionDate(), TimeUtils.FORMAT_ONE));

            if (moneyManager.getActionEndDate() != null) {
                moneyManager.setStrActionEndDate(TimeUtils.transformTimetagToDate(moneyManager.getActionEndDate(), TimeUtils.FORMAT_ONE));
            }
        }
        return list;
    }

    @RequestMapping(value = "/moneySearch", method = {RequestMethod.GET})
    public ModelAndView moneySearch(
            @RequestParam(value = "searchKey", required = false, defaultValue = "") String searchKey,
            @RequestParam(value = "searchValue1", required = false, defaultValue = "") String searchValue1,
            @RequestParam(value = "searchValue2", required = false, defaultValue = "") String searchValue2,
            @RequestParam(value = "btime", required = false, defaultValue = "") String btime,
            @RequestParam(value = "etime", required = false, defaultValue = "") String etime,
            @RequestParam(value = "moneyType", required = false, defaultValue = "") Integer moneyType,
            HttpSession session) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("money/list");
        mav.addObject("searchKey", searchKey);
        mav.addObject("searchValue1", searchValue1);
        mav.addObject("searchValue2", searchValue2);
        mav.addObject("btime", btime);
        mav.addObject("etime", etime);
        mav.addObject("moneyType", moneyType);

        //判断条件
        if (searchKey.equals("actionPerson")||searchKey.equals("actionDesc")) {
            if (StringUtils.isEmpty(searchValue1)) {
                mav.addObject(TIP, "条件1必填");
                mav.addObject(DATA, cacheList);
                return mav;
            }
        }

        if (searchKey.equals("actionMoney")) {
            if (StringUtils.isEmpty(searchValue1) || StringUtils.isEmpty(searchValue2)) {
                mav.addObject(TIP, "条件1，条件2都必填");
                mav.addObject(DATA, cacheList);
                return mav;
            }

            if (Double.valueOf(searchValue1) > Double.valueOf(searchValue2)) {
                mav.addObject(TIP, "条件2金额必须比条件1金额高");
                mav.addObject(DATA, cacheList);
                return mav;
            }
        }

        Long bt = StringUtils.isEmpty(btime) ? 0L : TimeUtils.transformDateToTimetag(btime, TimeUtils.FORMAT_ONE);
        Long et = StringUtils.isEmpty(etime) ? System.currentTimeMillis() : TimeUtils.transformDateToTimetag(etime, TimeUtils.FORMAT_ONE);

        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("moneyType", moneyType);
        List<MoneyManager> list = dealMoneyOther(daoCenter.getMoneyManagerList(map));

        List<MoneyManager> temp = new ArrayList<MoneyManager>();
        for (MoneyManager moneyManager : list) {
            if (moneyManager.getActionDate() >= bt && moneyManager.getActionDate() <= et) {
                if (searchKey.equals("actionPerson")) {
                    if (!moneyManager.getActionPerson().equals(searchValue1)) continue;
                } else if (searchKey.equals("actionMoney")) {
                    if (moneyManager.getActionFee() < Double.valueOf(searchValue1)
                            || moneyManager.getActionFee() > Double.valueOf(searchValue2)) {
                        continue;
                    }
                } else if(searchKey.equals("actionDesc")){
                    if(!moneyManager.getActionDesc().contains(searchValue1)) continue;
                }
                temp.add(moneyManager);
            }
        }

        mav.addObject(DATA, temp);

        //计算总和
        Double balance = 0.0;
        for (MoneyManager moneyManager : temp) {
            if (moneyManager.getActionType().equals(ContextType.MONEY_MANAGER_IN)) {
                balance += moneyManager.getActionFee();
            } else {
                balance -= moneyManager.getActionFee();
            }
        }
        mav.addObject("balance", Utils.saveTwoSeat(balance));
        return mav;
    }

    @RequestMapping(value = "/moneyView")
    public ModelAndView moneyOtherView(@RequestParam(value = "moneyType", required = false, defaultValue = "") Integer moneyType, HttpSession session) {
        if (moneyType == null) moneyType = ContextType.MONEY_TYPE_CASH;
        ModelAndView mav = new ModelAndView();
        mav.setViewName("money/list");
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("moneyType", moneyType);
        List<MoneyManager> list = dealMoneyOther(daoCenter.getMoneyManagerList(map));
//        if (moneyType.equals(ContextType.MONEY_TYPE_CASH) || moneyType.equals(ContextType.MONEY_TYPE_BANK)) {
//            //截取前20个
//            if (list.size() > 20) {
//                list = list.subList(0, 20);
//            }
//        }

        if (moneyType.equals(ContextType.MONEY_TYPE_CASH) || moneyType.equals(ContextType.MONEY_TYPE_BANK) || moneyType.equals(ContextType.MONEY_TYPE_POSS)) {
            mav.addObject("balance", StatCenter.statDifferentMoneyType(moneyType, daoCenter));
            //截取前20个
            if (list.size() > 20) {
                list = list.subList(0, 20);
            }
        }

        cacheList.clear();
        cacheList = list;
        mav.addObject(DATA, list);
        mav.addObject("moneyType", moneyType);
        mav.addObject(TIP, session.getAttribute("tip"));
        return mav;
    }

    @RequestMapping(value = "/moneyAction", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView moneyOtherAction(
            @RequestParam(value = "action", required = true, defaultValue = "") Integer action,
            @RequestParam(value = "over", required = false, defaultValue = "") Integer over,
            @RequestParam(value = "id", required = false, defaultValue = "") Integer id,
            @RequestParam(value = "moneyType", required = false, defaultValue = "") Integer moneyType,
            @RequestParam(value = "actionDate", required = false, defaultValue = "") String actionDate,
            @RequestParam(value = "actionPerson", required = false, defaultValue = "") String actionPerson,
            @RequestParam(value = "actionDesc", required = false, defaultValue = "") String actionDesc,
            @RequestParam(value = "actionFee", required = false, defaultValue = "") Double actionFee,
            @RequestParam(value = "actionType", required = false, defaultValue = "") Integer actionType) {

        ModelAndView mav = new ModelAndView();
        mav.setViewName("money/add");
        mav.addObject("action", action);
        mav.addObject("moneyType", moneyType);

        mav.addObject("actionDate", actionDate);
        mav.addObject("actionPerson", actionPerson);
        mav.addObject("actionDesc", actionDesc);
        mav.addObject("actionFee", actionFee);
        mav.addObject("actionType", actionType);
        if (over == null) {
            if (id != null) {
                MoneyManager moneyManager = daoCenter.getmoneyManager(id);
                mav.addObject("id", id);
                mav.addObject("actionDate", TimeUtils.transformTimetagToDate(moneyManager.getActionDate(), TimeUtils.FORMAT_ONE));
                mav.addObject("actionPerson", moneyManager.getActionPerson());
                mav.addObject("actionDesc", moneyManager.getActionDesc());
                mav.addObject("actionFee", moneyManager.getActionFee());
                mav.addObject("actionType", moneyManager.getActionType());
            }
            return mav;
        } else {
            if (StringUtils.isBlank(actionDate)) {
                mav.addObject(TIP, "日期必填！");
                return mav;
            }
            if (StringUtils.isBlank(actionPerson)) {
                mav.addObject(TIP, "经办人必填！");
                return mav;
            }
            if (actionFee == 0) {
                mav.addObject(TIP, "金额必填！");
                return mav;
            }
            if (actionType == 0) {
                mav.addObject(TIP, "科目必填！");
                return mav;
            }
            MoneyManager moneyManager = new MoneyManager();
            moneyManager.setActionDate(TimeUtils.transformDateToTimetag(actionDate, TimeUtils.FORMAT_ONE));
            moneyManager.setActionPerson(actionPerson);
            moneyManager.setActionDesc(actionDesc);
            moneyManager.setActionFee(actionFee);
            moneyManager.setActionType(actionType);
            moneyManager.setMoneyType(moneyType);
            mav.clear();
            if (action.equals(CREATE_ACTION)) {
                moneyManager.setActionEndDate(0L);
                daoCenter.createmoneyManager(moneyManager);
            } else if (action.equals(MOD_ACTION)) {
                moneyManager.setId(id);
                daoCenter.updatemoneyManager(moneyManager);
//                mav.addObject(TIP, "ok 部门【" + departmentAuthority.getDepartmentName() + "】更新成功！");
            }
            mav.setViewName("redirect:/moneyView?moneyType=" + moneyType);
            return mav;
        }
    }

    @RequestMapping(value = "/moneyDelete", method = RequestMethod.GET)
    public ModelAndView moneyOtherDelete(@RequestParam(value = "moneyType", required = false, defaultValue = "") Integer moneyType,
                                         @RequestParam(value = "id", required = false, defaultValue = "") Integer id) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:/moneyView?moneyType=" + moneyType);
        daoCenter.deletemoneyManager(id);
//        mav.addObject(TIP, urlEncode("删除部门【" + departmentAuthority.getDepartmentName() + "】成功！"));
        return mav;
    }

    @RequestMapping(value = "/moneyHouseView")
    public ModelAndView moneyHouseView(HttpSession session) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("money/houseList");
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("moneyType", ContextType.MONEY_TYPE_HOUSE);
        List<MoneyManager> list = daoCenter.getMoneyManagerList(map);
        mav.addObject(DATA, dealMoneyOther(list));
        return mav;
    }

    @RequestMapping(value = "/moneyHouseAction", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView moneyHouseAction(
            @RequestParam(value = "action", required = true, defaultValue = "") Integer action,
            @RequestParam(value = "over", required = false, defaultValue = "") Integer over,
            @RequestParam(value = "id", required = false, defaultValue = "") Integer id,
            @RequestParam(value = "actionPerson", required = false, defaultValue = "") String actionPerson,
            @RequestParam(value = "actionDate", required = false, defaultValue = "") String actionDate,
            @RequestParam(value = "actionEndDate", required = false, defaultValue = "") String actionEndDate,
            @RequestParam(value = "actionFee", required = false, defaultValue = "") Double actionFee,
            @RequestParam(value = "actionDesc", required = false, defaultValue = "") String actionDesc) {

        ModelAndView mav = new ModelAndView();
        mav.setViewName("money/houseAdd");
        mav.addObject("action", action);

        mav.addObject("actionPerson", actionPerson);
        mav.addObject("actionDate", actionDate);
        mav.addObject("actionEndDate", actionEndDate);
        mav.addObject("actionFee", actionFee);
        mav.addObject("actionDesc", actionDesc);

        if (over == null) {
            if (id != null) {
                MoneyManager moneyManager = daoCenter.getmoneyManager(id);
                mav.addObject("id", id);
                mav.addObject("actionPerson", moneyManager.getActionPerson());
                mav.addObject("actionDate", TimeUtils.transformTimetagToDate(moneyManager.getActionDate(), TimeUtils.FORMAT_ONE));
                mav.addObject("actionEndDate", TimeUtils.transformTimetagToDate(moneyManager.getActionEndDate(), TimeUtils.FORMAT_ONE));
                mav.addObject("actionDesc", moneyManager.getActionDesc());
                mav.addObject("actionFee", moneyManager.getActionFee());
            }
            return mav;
        } else {
            if (StringUtils.isBlank(actionDate)) {
                mav.addObject(TIP, "起始日期必填！");
                return mav;
            }
            if (StringUtils.isBlank(actionEndDate)) {
                mav.addObject(TIP, "截至日期必填！");
                return mav;
            }
            if (StringUtils.isBlank(actionPerson)) {
                mav.addObject(TIP, "项目必填！");
                return mav;
            }
            if (actionFee == 0) {
                mav.addObject(TIP, "金额必填！");
                return mav;
            }
            MoneyManager moneyManager = new MoneyManager();
            moneyManager.setActionPerson(actionPerson);
            moneyManager.setActionDate(TimeUtils.transformDateToTimetag(actionDate, TimeUtils.FORMAT_ONE));
            moneyManager.setActionEndDate(TimeUtils.transformDateToTimetag(actionEndDate, TimeUtils.FORMAT_ONE));
            moneyManager.setActionDesc(actionDesc);
            moneyManager.setActionFee(actionFee);
            moneyManager.setMoneyType(ContextType.MONEY_TYPE_HOUSE);
            mav.clear();
            if (action.equals(CREATE_ACTION)) {
                daoCenter.createmoneyManager(moneyManager);
            } else if (action.equals(MOD_ACTION)) {
                moneyManager.setId(id);
                daoCenter.updatemoneyManager(moneyManager);
            }
            mav.setViewName("redirect:/moneyHouseView");
            return mav;
        }
    }

    @RequestMapping(value = "/moneyHouseDelete", method = RequestMethod.GET)
    public ModelAndView moneyHouseDelete(@RequestParam(value = "id", required = false, defaultValue = "") Integer id) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:/moneyHouseView");
        daoCenter.deletemoneyManager(id);
        return mav;
    }

    private List<MortgageRebate> dealMortgageRebate(List<MortgageRebate> list) {
        for (MortgageRebate mortgageRebate : list) {
            mortgageRebate.setStrLoanDate(TimeUtils.transformTimetagToDate(mortgageRebate.getLoanDate(), TimeUtils.FORMAT_ONE));
        }
        return list;
    }

    @RequestMapping(value = "/moneyRebateView")
    public ModelAndView moneyRebateView(HttpSession session) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("money/rebateList");
        mav.addObject(DATA, dealMortgageRebate(daoCenter.getMortgageRebateList(null)));
        return mav;
    }

    @RequestMapping(value = "/moneyRebateAction", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView moneyRebateAction(
            @RequestParam(value = "action", required = true, defaultValue = "") Integer action,
            @RequestParam(value = "over", required = false, defaultValue = "") Integer over,
            @RequestParam(value = "id", required = false, defaultValue = "") Integer id,
            @RequestParam(value = "carModel", required = false, defaultValue = "") String carModel,
            @RequestParam(value = "number", required = false, defaultValue = "") String number,
            @RequestParam(value = "loanDate", required = false, defaultValue = "") String loanDate,
            @RequestParam(value = "billPerson", required = false, defaultValue = "") String billPerson,
            @RequestParam(value = "loan", required = false, defaultValue = "") Double loan,
            @RequestParam(value = "interestRate", required = false, defaultValue = "") String interestRate,
            @RequestParam(value = "backMoney", required = false, defaultValue = "") Double backMoney,
            @RequestParam(value = "abs", required = false, defaultValue = "") String abs) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("money/rebateAdd");
        mav.addObject("action", action);

        mav.addObject("carModel", carModel);
        mav.addObject("number", number);
        mav.addObject("loanDate", loanDate);
        mav.addObject("billPerson", billPerson);
        mav.addObject("loan", loan);
        mav.addObject("interestRate", interestRate);
        mav.addObject("backMoney", backMoney);
        mav.addObject("abs", abs);
        if (over == null) {
            if (id != null) {
                MortgageRebate mortgageRebate = daoCenter.getMortgageRebate(id);
                mav.addObject("id", id);
                mav.addObject("carModel", mortgageRebate.getCarModel());
                mav.addObject("number", mortgageRebate.getNumber());
                mav.addObject("loanDate", TimeUtils.transformTimetagToDate(mortgageRebate.getLoanDate(), TimeUtils.FORMAT_ONE));
                mav.addObject("billPerson", mortgageRebate.getBillPerson());
                mav.addObject("loan", mortgageRebate.getLoan());
                mav.addObject("interestRate", mortgageRebate.getInterestRate());
                mav.addObject("backMoney", mortgageRebate.getBackMoney());
                mav.addObject("abs", mortgageRebate.getAbs());
            }
            return mav;
        } else {
            MortgageRebate mortgageRebate = new MortgageRebate();
            mortgageRebate.setCarModel(carModel);
            mortgageRebate.setNumber(number);
            mortgageRebate.setLoanDate(TimeUtils.transformDateToTimetag(loanDate, TimeUtils.FORMAT_ONE));
            mortgageRebate.setBillPerson(billPerson);
            mortgageRebate.setLoan(loan);
            mortgageRebate.setInterestRate(interestRate);
            mortgageRebate.setBackMoney(backMoney);
            mortgageRebate.setAbs(abs);

            mav.clear();
            if (action.equals(CREATE_ACTION)) {
                daoCenter.createMortgageRebate(mortgageRebate);
            } else if (action.equals(MOD_ACTION)) {
                mortgageRebate.setId(id);
                daoCenter.updateMortgageRebate(mortgageRebate);
            }
            mav.setViewName("redirect:/moneyRebateView");
            return mav;
        }
    }

    @RequestMapping(value = "/moneyRebateDelete", method = RequestMethod.GET)
    public ModelAndView moneyRebateDelete(@RequestParam(value = "id", required = false, defaultValue = "") Integer id) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:/moneyRebateView");
        daoCenter.deleteMortgageRebate(id);
        return mav;
    }
}
