package cm.lx.controller.administration;

import cm.lx.controller.BaseController;
import cm.lx.bean.entity.Account;
import cm.lx.bean.entity.DepartmentAuthority;
import cm.lx.service.AccountService;
import cm.lx.service.DepartmentAuthorityService;
import cm.lx.util.MD5Utils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * 员工管理
 *
 * @author linxingwei
 * @date 2018/12/27
 */
@Controller
public class UserController extends BaseController {

    @Value("${zcDepartmentId:1}")
    Integer zcDepartmentId;

    @Value("${initPassword:123456}")
    String initPassword;

    @Resource
    AccountService accountService;

    @Resource
    DepartmentAuthorityService departmentAuthorityService;

    @RequestMapping(value = "/userList")
    public ModelAndView userList() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("user/list");
        List<Account> dataList = accountService.getAccountList();

        List<Account> respList = new ArrayList<>();
        for (Account tempAccount : dataList) {
            DepartmentAuthority departmentAuthority = departmentAuthorityService.getDepartmentAuthorityById(tempAccount.getDepartment());
            if (departmentAuthority != null) {
                tempAccount.setDepartmentName(departmentAuthority.getDepartmentName());
                respList.add(tempAccount);
            }
        }
        mav.addObject(DATA, respList);
        return mav;
    }

    private List<DepartmentAuthority> prepareDepartmentList() {
        List<DepartmentAuthority> list = departmentAuthorityService.getDepartmentAuthorityList();
        for (DepartmentAuthority temp : list) {
            if (temp.getId().equals(zcDepartmentId)) {
                list.remove(temp);
                break;
            }
        }
        return list;
    }

    @RequestMapping(value = "/userAddView", method = RequestMethod.GET)
    public ModelAndView userAddView() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("user/add");
        mav.addObject("list", prepareDepartmentList());
        return mav;
    }

    @RequestMapping(value = "/userAdd", method = RequestMethod.POST)
    public ModelAndView userAdd(
            @RequestParam(value = "accountNum", required = false, defaultValue = "") String accountNum,
            @RequestParam(value = "usertype", required = false, defaultValue = "") Integer usertype,
            @RequestParam(value = "name", required = false, defaultValue = "") String name,
            @RequestParam(value = "phone", required = false, defaultValue = "") String phone,
            @RequestParam(value = "department", required = false, defaultValue = "") Integer department) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("user/add");

        mav.addObject("accountNum", accountNum);
        mav.addObject("usertype", usertype);

        //拉取列表
        List<DepartmentAuthority> list = prepareDepartmentList();
        mav.addObject("list", list);


        DepartmentAuthority departmentAuthority = null;
        for (DepartmentAuthority temp : list) {
            if (temp.getId().equals(department)) {
                departmentAuthority = temp;
                break;
            }
        }

        if (departmentAuthority == null) {
            mav.addObject(TIP, "部门选择错误！");
            return mav;
        }
        mav.addObject("departmentName", departmentAuthority.getDepartmentName());

        if (StringUtils.isBlank(accountNum)) {
            mav.addObject(TIP, "帐号不能为空！");
            return mav;
        }
        if (accountService.getAccountByName(accountNum) != null) {
            mav.addObject(TIP, "帐号已存在！");
            return mav;
        }
        if (usertype == null) {
            mav.addObject(TIP, "用户类型为空！");
            return mav;
        }
        Account account = new Account();
        account.setAccountNum(accountNum);
        account.setPassword(MD5Utils.compute(initPassword));
        account.setDepartment(department);
        account.setUserType(usertype);
        account.setName(name == null ? "" : name);
        account.setPhone(phone == null ? "" : phone);
        account.setActive(1);
        accountService.create(account);
        mav.clear();
        mav.addObject(TIP, "ok 用户" + account.getName() + "创建成功！");
        mav.setViewName("redirect:/userList");
        return mav;

    }

    @RequestMapping(value = "/userUpdateView", method = RequestMethod.GET)
    public ModelAndView userUpdateView(
            @RequestParam(value = "id", required = false, defaultValue = "") Integer id) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("user/update");

        Account account = accountService.getAccountById(id);
        if (account == null) {
            mav.addObject(TIP, "不存在用户！");
            return mav;
        }
        mav.addObject("updateAccount", account);

        List<DepartmentAuthority> list = departmentAuthorityService.getDepartmentAuthorityList();
        for (DepartmentAuthority temp : list) {
            if (temp.getId().equals(account.getDepartment())) {
                account.setDepartmentName(temp.getDepartmentName());
                break;
            }
        }
        mav.addObject("list", list);
        return mav;
    }

    @RequestMapping(value = "/userUpdate", method = RequestMethod.POST)
    public ModelAndView userUpdate(
            @RequestParam(value = "id", required = false, defaultValue = "") Integer id,
            @RequestParam(value = "usertype", required = false, defaultValue = "") Integer usertype,
            @RequestParam(value = "department", required = false, defaultValue = "") Integer department,
            @RequestParam(value = "name", required = false, defaultValue = "") String name,
            @RequestParam(value = "phone", required = false, defaultValue = "") String phone,
            HttpSession session) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("user/update");

        Account account = new Account();
        account.setId(id);
        account.setUserType(usertype);
        account.setDepartment(department);
        if (!StringUtils.isEmpty(name)) {
            account.setName(name);
        }
        if (!StringUtils.isEmpty(phone)) {
            account.setPhone(phone);
        }
        account.setUtime(System.currentTimeMillis());
        accountService.updateAccountById(account);


        Account sAccount = (Account) session.getAttribute("account");
        boolean isMyself = sAccount.getId().equals(id);
        if (isMyself) {
            session.setAttribute("account", accountService.getAccountById(id));
        }

        mav.clear();
        mav.addObject(TIP, urlEncode("ok 员工更新成功！"));
        mav.setViewName("redirect:/userList");
        return mav;
    }


    @RequestMapping(value = "/userPswdUpdateView", method = RequestMethod.GET)
    public ModelAndView userPswdUpdateView(
            @RequestParam(value = "id", required = false, defaultValue = "") String id,
            HttpSession session) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("user/updatePswd");
        mav.addObject("id", id);
        return mav;
    }

    @RequestMapping(value = "/userPswdUpdate", method = RequestMethod.POST)
    public ModelAndView userPswdUpdate(
            HttpSession session,
            @RequestParam(value = "id", required = false, defaultValue = "") Integer id,
            @RequestParam(value = "oldpsw", required = false, defaultValue = "") String oldpsw,
            @RequestParam(value = "newpsw", required = false, defaultValue = "") String newpsw,
            @RequestParam(value = "repsw", required = false, defaultValue = "") String repsw) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("user/updatePswd");
        mav.addObject("id", id);

        Account sAccount = (Account) session.getAttribute("account");
        boolean isMyself = sAccount.getId().equals(id);


        if (isMyself) {
            if (StringUtils.isEmpty(oldpsw)) {
                mav.addObject(TIP, "旧密码不能为空！");
                return mav;
            }

            if (!sAccount.getPassword().equals(MD5Utils.compute(oldpsw))) {
                mav.addObject(TIP, "旧密码错误！");
                return mav;
            }
        }

        if (StringUtils.isEmpty(newpsw)) {
            mav.addObject(TIP, "新密码为空！");
            return mav;
        }
        if (StringUtils.length(newpsw) < 6) {
            mav.addObject(TIP, "新密码长度过短！");
            return mav;
        }
        if (StringUtils.isEmpty(repsw)) {
            mav.addObject(TIP, "重复密码为空！");
            return mav;
        }
        if (!StringUtils.equals(newpsw, repsw)) {
            mav.addObject(TIP, "重复密码不一致！");
            return mav;
        }
        if (oldpsw.equals(newpsw)) {
            mav.addObject(TIP, "新旧密码不能重复！");
            return mav;
        }

        Account account = new Account();
        account.setId(id);
        account.setPassword(MD5Utils.compute(newpsw));
        accountService.updateAccountById(account);
        if (isMyself) {
            mav.clear();
            mav.addObject(TIP, "更新密码成功，重新登录！");
            mav.setViewName("redirect:/logout");
            return mav;
        }
        mav.addObject(TIP, "更新密码成功！");
        mav.setViewName("redirect:/userList");
        return mav;
    }

    @RequestMapping(value = "/userDelete", method = RequestMethod.GET)
    public ModelAndView deleteUser(
            @RequestParam(value = "id", required = false, defaultValue = "") Integer id) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:/userList");

        if (id == null) {
            mav.addObject(TIP, urlEncode("员工索引ID为空！"));
            return mav;
        }

        accountService.deleteAccountById(id);
        mav.addObject(TIP, urlEncode("删除员工成功！"));
        return mav;
    }
}
