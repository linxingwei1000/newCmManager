package cm.lx.controller;

import cm.lx.bean.Account;
import cm.lx.bean.DepartmentAuthority;
import cm.lx.business.DaoCenter;
import cm.lx.util.MD5Utils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by linxingwei on 2018/1/8.
 */
@Controller
public class LoginController extends BaseController {

    @Resource
    DaoCenter daoCenter;

    @RequestMapping(value = "/login")
    public ModelAndView login(@RequestParam(value = "accountNum", required = false, defaultValue = "") String accountNum,
                              @RequestParam(value = "password", required = false, defaultValue = "") String password,
                              @RequestParam(value = "code", required = false, defaultValue = "") String code,
                              HttpSession session,
                              HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("index");

        //初始化界面没有传参数
        if(StringUtils.isEmpty(accountNum)&&StringUtils.isEmpty(password)&&StringUtils.isEmpty(code)) return mav;

        accountNum = StringUtils.trim(accountNum);
        password = StringUtils.trim(password);
        code = StringUtils.trim(code);

        mav.addObject("accountNum", accountNum);
        mav.addObject("password", password);

        if (StringUtils.isBlank(accountNum)) {
            mav.addObject(TIP, "用户不能为空");
            return mav;
        }
        if (StringUtils.isBlank(password)) {
            mav.addObject(TIP, "密码不能为空");
            return mav;
        }

        if (StringUtils.isBlank(code)) {
            mav.addObject(TIP, "验证码不能为空");
            return mav;
        }
        String sessionCode = (String) session.getAttribute("vcode");
        if (!StringUtils.equals(sessionCode, code)) {
            mav.addObject(TIP, "验证码不正确");
            return mav;
        }

        try {
            Account account = daoCenter.getAccountByName(accountNum);
            if (account == null || !account.getPassword().equals(MD5Utils.compute(password))) {
                mav.addObject(TIP, "用户密码不正确");
                return mav;
            }

            DepartmentAuthority departmentAuthority = daoCenter.getDepartmentAuthorityById(account.getDepartment());

            session.setAttribute("account", account);
            session.setAttribute("myDepartmentAuthority", departmentAuthority);

            mav.clear();
            mav.setViewName("main");
            return mav;
        } catch (Exception e) {
            System.out.println("系统错误:" + e.toString());
            return mav;
        }

    }

    @RequestMapping(value = "/main")
    public ModelAndView main() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("main");
        return mav;
    }

    @RequestMapping(value = "/index")
    public ModelAndView index() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("index");
        return mav;
    }

    @RequestMapping(value = "/logout")
    public ModelAndView logout(@RequestParam(value = "tip", required = false, defaultValue = "") String tip, HttpSession session) {
        session.removeAttribute("account");
        session.removeAttribute("myDepartmentAuthority");
        session.invalidate();
        return new ModelAndView("index", TIP, tip);
    }

    @RequestMapping(value = "/nopermission")
    public ModelAndView noPermission(String permission) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("nopermission");
        mav.addObject("permission", permission);
        return mav;
    }
}
