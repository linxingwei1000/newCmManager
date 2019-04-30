package cm.lx.controller.car;

import cm.lx.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

/**
 * 自定义公式
 *
 * @author linxingwei
 * @date 2019/4/3
 */
@Controller
public class CarFormulaController extends BaseController {

    @RequestMapping(value = "/carFormulaView", method = RequestMethod.GET)
    public ModelAndView carFormulaView(HttpSession session) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("car/formula");
        return mav;
    }
}
