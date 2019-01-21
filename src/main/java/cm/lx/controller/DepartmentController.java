package cm.lx.controller;

import cm.lx.bean.DepartmentAuthority;
import cm.lx.business.DaoCenter;
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
 * Created by linxingwei on 2018/1/11.
 */
@Controller
public class DepartmentController extends BaseController{

    @Resource
    DaoCenter daoCenter;

    @RequestMapping(value = "/userDepartment")
    public ModelAndView listUser(HttpSession session) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("department/list");
        List<DepartmentAuthority> list = daoCenter.getDepartmentAuthorityList();
        mav.addObject(DATA, list);
        return mav;
    }

    @RequestMapping(value = "/userDepartmentAction", method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView addUser(
            @RequestParam(value = "action", required = true, defaultValue = "") Integer action,
            @RequestParam(value = "over", required = false, defaultValue = "") Integer over,
            @RequestParam(value = "id", required = false, defaultValue = "") Integer id,
            @RequestParam(value = "dname", required = false, defaultValue = "") String dname,
            @RequestParam(value = "accountAble", required = false, defaultValue = "") Integer accountAble,
            @RequestParam(value = "carAble", required = false, defaultValue = "") Integer carAble,
            @RequestParam(value = "insuranceAble", required = false, defaultValue = "") Integer insuranceAble,
            @RequestParam(value = "mortgageAble", required = false, defaultValue = "") Integer mortgageAble,
            @RequestParam(value = "newCarAble", required = false, defaultValue = "") Integer newCarAble,
            @RequestParam(value = "moneyAble", required = false, defaultValue = "") Integer moneyAble,
            @RequestParam(value = "statAble", required = false, defaultValue = "") Integer statAble,
            @RequestParam(value = "dossierAble", required = false, defaultValue = "") Integer dossierAble) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("department/add");
        mav.addObject("action", action);

        mav.addObject("dname", dname);
        mav.addObject("accountAble", accountAble);
        mav.addObject("carAble", carAble);
        mav.addObject("insuranceAble", insuranceAble);
        mav.addObject("mortgageAble", mortgageAble);
        mav.addObject("newCarAble", newCarAble);
        mav.addObject("moneyAble", moneyAble);
        mav.addObject("statAble", statAble);
        mav.addObject("dossierAble", dossierAble);

        if(over==null){
            if(id!=null){
                DepartmentAuthority departmentAuthority = daoCenter.getDepartmentAuthorityById(id);
                mav.addObject("id", id);
                mav.addObject("dname", departmentAuthority.getDepartmentName());
                mav.addObject("accountAble", departmentAuthority.getAccountAble());
                mav.addObject("carAble", departmentAuthority.getCarAble());
                mav.addObject("insuranceAble", departmentAuthority.getInsuranceAble());
                mav.addObject("mortgageAble", departmentAuthority.getMortgageAble());
                mav.addObject("newCarAble", departmentAuthority.getNewCarAble());
                mav.addObject("moneyAble", departmentAuthority.getMoneyAble());
                mav.addObject("statAble", departmentAuthority.getStatAble());
                mav.addObject("dossierAble", departmentAuthority.getDossierAble());
            }
            return mav;
        }else{
            if (StringUtils.isBlank(dname)) {
                mav.addObject(TIP, "部门名称不能为空！");
                return mav;
            }
            if (daoCenter.getDepartmentAuthorityByName(dname) != null&&action == CREATE_ACTION) {
                mav.addObject(TIP, "部门【" + dname + "】已存在！");
                return mav;
            }
            if (accountAble == null||carAble==null||insuranceAble==null||mortgageAble==null||newCarAble==null||moneyAble==null||statAble==null||dossierAble==null) {
                mav.addObject(TIP, "页面权限全部必填！");
                return mav;
            }

            DepartmentAuthority departmentAuthority = new DepartmentAuthority();
            departmentAuthority.setDepartmentName(dname);
            departmentAuthority.setAccountAble(accountAble);
            departmentAuthority.setCarAble(carAble);
            departmentAuthority.setInsuranceAble(insuranceAble);
            departmentAuthority.setMortgageAble(mortgageAble);
            departmentAuthority.setNewCarAble(newCarAble);
            departmentAuthority.setMoneyAble(moneyAble);
            departmentAuthority.setStatAble(statAble);
            departmentAuthority.setDossierAble(dossierAble);

            mav.clear();
            if (action.equals(CREATE_ACTION)) {
                daoCenter.createDepartment(departmentAuthority);
                mav.addObject(TIP, "ok 部门【" + departmentAuthority.getDepartmentName() + "】创建成功！");
            } else if (action.equals(MOD_ACTION)) {
                departmentAuthority.setId(id);
                daoCenter.updateDepartment(departmentAuthority);
                mav.addObject(TIP, "ok 部门【" + departmentAuthority.getDepartmentName() + "】更新成功！");
            }
            mav.setViewName("redirect:/userDepartment");
            return mav;
        }
    }

    @RequestMapping(value = "/userDepartmentDelete", method = RequestMethod.GET)
    public ModelAndView deleteUser(
            @RequestParam(value = "id", required = false, defaultValue = "") Integer id) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:/userDepartment");

        DepartmentAuthority departmentAuthority = daoCenter.getDepartmentAuthorityById(id);
        if (departmentAuthority == null) {
            mav.addObject(TIP, urlEncode("要删除的部门不存在！"));
            return mav;
        }

        //总裁不能被删
        if(departmentAuthority.getDepartmentName().equals("总裁")){
            mav.addObject(TIP, urlEncode("总裁不能删除！"));
            return mav;
        }

        //删除部门，删除部门下面所有成员
        daoCenter.deleteDepartment(id);
        daoCenter.deleteAccountByDepartmentId(id);

        mav.addObject(TIP, urlEncode("删除部门【" + departmentAuthority.getDepartmentName() + "】成功！"));
        return mav;
    }


}
