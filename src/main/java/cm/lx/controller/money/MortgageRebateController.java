package cm.lx.controller.money;

import cm.lx.bean.entity.MortgageRebate;
import cm.lx.controller.BaseController;
import cm.lx.service.MortgageRebateService;
import cm.lx.util.TimeUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author linxingwei
 * @date 2019/1/23
 */
@Controller
public class MortgageRebateController extends BaseController {


    @Resource
    MortgageRebateService mortgageRebateService;

    private List<MortgageRebate> dealMortgageRebate(List<MortgageRebate> list) {
        for (MortgageRebate mortgageRebate : list) {
            mortgageRebate.setStrLoanDate(TimeUtils.transformTimetagToDate(mortgageRebate.getLoanDate(), TimeUtils.FORMAT_ONE));
        }
        return list;
    }

    @RequestMapping(value = "/moneyRebateView")
    public ModelAndView moneyRebateView() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("money/rebateList");
        mav.addObject(DATA, dealMortgageRebate(mortgageRebateService.getMortgageRebateList()));
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
                MortgageRebate mortgageRebate = mortgageRebateService.getMortgageRebateById(id);
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
                mortgageRebateService.create(mortgageRebate);
            } else if (action.equals(MOD_ACTION)) {
                mortgageRebate.setId(id);
                mortgageRebateService.updateMortgageRebateById(mortgageRebate);
            }
            mav.setViewName("redirect:/moneyRebateView");
            return mav;
        }
    }

    @RequestMapping(value = "/moneyRebateDelete", method = RequestMethod.GET)
    public ModelAndView moneyRebateDelete(@RequestParam(value = "id", required = false, defaultValue = "") Integer id) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:/moneyRebateView");
        mortgageRebateService.deleteMortgageRebateById(id);
        return mav;
    }
}
