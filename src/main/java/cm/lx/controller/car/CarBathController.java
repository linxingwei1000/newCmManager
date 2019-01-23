package cm.lx.controller.car;

import cm.lx.bean.entity.Account;
import cm.lx.bean.entity.CarBath;
import cm.lx.controller.BaseController;
import cm.lx.service.CarBathService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * 批量采购信息
 *
 * @author linxingwei
 * @date 2019/1/21
 */
@Controller
public class CarBathController extends BaseController {

//    @Resource
//    DaoCenter daoCenter;

    @Resource
    CarBathService carBathService;

    //批量采购相关操作
    @RequestMapping(value = "/carBathView", method = RequestMethod.GET)
    public ModelAndView carBathView() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("car/bathList");
        mav.addObject(DATA, carBathService.getCarBathListByAccount(null));
        return mav;
    }

    @RequestMapping(value = "/carBathAction", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView carBathAction(
            @RequestParam(value = "action", required = true, defaultValue = "") Integer action,
            @RequestParam(value = "over", required = false, defaultValue = "") Integer over,
            @RequestParam(value = "id", required = false, defaultValue = "") Integer id,
            @RequestParam(value = "bathName", required = false, defaultValue = "") String bathName,
            @RequestParam(value = "bathDesc", required = false, defaultValue = "") String bathDesc,
            HttpSession session) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("car/bathAdd");
        mav.addObject("action", action);

        mav.addObject("bathName", bathName);
        mav.addObject("bathDesc", bathDesc);

        if (over == null) {
            if (id != null) {
                CarBath carBath = carBathService.getCarBathById(id);
                mav.addObject("id", id);
                mav.addObject("bathName", carBath.getBathName());
                mav.addObject("bathDesc", carBath.getBathDesc());
            }
            return mav;
        } else {
            if (StringUtils.isEmpty(bathName)) {
                mav.addObject(TIP, "批量采购标题不能为空！");
                return mav;
            }

            if (carBathService.getByName(bathName) != null && action.equals(CREATE_ACTION)) {
                mav.addObject(TIP, "批量采购标题已存在！");
                return mav;
            }
            CarBath carBath = new CarBath();
            carBath.setBathName(bathName);
            carBath.setBathDesc(bathDesc);
            carBath.setCarRecordId("");

            mav.clear();
            if (action.equals(CREATE_ACTION)) {
                Account account = (Account) session.getAttribute("account");
                carBath.setAccountNum(account.getAccountNum());
                carBathService.create(carBath);
                mav.addObject(TIP, "ok 批量采购创建成功！");
            } else if (action.equals(MOD_ACTION)) {
                carBath.setId(id);
                carBathService.updateCarBathById(carBath);
                mav.addObject(TIP, "ok 批量采购修改成功！");
            }
            mav.setViewName("redirect:/carBathView");
            return mav;
        }
    }

    @RequestMapping(value = "/carBathDelete", method = RequestMethod.GET)
    public ModelAndView carBathDelete(
            @RequestParam(value = "id", required = false, defaultValue = "") Integer id) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:/carBathView");

        CarBath carBath = carBathService.getCarBathById(id);
        if (carBath == null) {
            mav.addObject(TIP, urlEncode("要删除的批量采购不存在！"));
            return mav;
        }

        if (!StringUtils.isEmpty(carBath.getCarRecordId())) {
            mav.addObject(TIP, urlEncode("要删除的批量采购有采购车辆在，操作被禁止！"));
            return mav;
        }

        carBathService.deleteById(id);
        mav.addObject(TIP, urlEncode("删除批量采购【" + carBath.getBathName() + "】成功！"));
        return mav;
    }
}
