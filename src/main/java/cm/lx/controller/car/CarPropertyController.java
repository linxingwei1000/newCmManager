package cm.lx.controller.car;

import cm.lx.bean.entity.CarProperty;
import cm.lx.business.CacheCenter;
import cm.lx.controller.BaseController;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

/**
 * 车辆属性相关
 *
 * @author linxingwei
 * @date 2019/1/21
 */
@Controller
public class CarPropertyController extends BaseController {

    @Resource
    CacheCenter cacheCenter;

    //车辆配置相关操作
    @RequestMapping(value = "/carPropertyView", method = RequestMethod.GET)
    public ModelAndView carPropertyView() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("car/propertyList");
        mav.addObject(DATA, cacheCenter.getCarPropertyByKey(null));
        return mav;
    }

    @RequestMapping(value = "/carPropertyAction", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView carPropertyAction(
            @RequestParam(value = "action", required = true, defaultValue = "") Integer action,
            @RequestParam(value = "over", required = false, defaultValue = "") Integer over,
            @RequestParam(value = "id", required = false, defaultValue = "") Integer id,
            @RequestParam(value = "propertyKey", required = false, defaultValue = "") String propertyKey,
            @RequestParam(value = "propertyValue", required = false, defaultValue = "") String propertyValue) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("car/propertyAdd");
        mav.addObject("action", action);

        mav.addObject("propertyKey", propertyKey);
        mav.addObject("propertyValue", propertyValue);

        if (over == null) {
            if (id != null) {
                CarProperty carProperty = cacheCenter.getCarPropertyById(id);
                mav.addObject("id", id);
                mav.addObject("propertyKey", carProperty.getPropertyKey());
                mav.addObject("propertyValue", carProperty.getPropertyValue());
            }
            return mav;
        } else {
            if (StringUtils.isEmpty(propertyKey)) {
                mav.addObject(TIP, "车辆属性名不能为空！");
                return mav;
            }
            if (StringUtils.isEmpty(propertyValue)) {
                mav.addObject(TIP, "属性值不能为空！");
                return mav;
            }

            if (cacheCenter.isCarPropertyExist(propertyKey, propertyValue)) {
                mav.addObject(TIP, "属性已存在！");
                return mav;
            }
            CarProperty carProperty = new CarProperty();
            carProperty.setPropertyKey(propertyKey);
            carProperty.setPropertyValue(propertyValue);

            mav.clear();
            if (action.equals(CREATE_ACTION)) {
                mav.addObject(TIP, "ok 新属性创建成功！");
            } else if (action.equals(MOD_ACTION)) {
                carProperty.setId(id);
                mav.addObject(TIP, "ok 属性修改成功！");
            }
            cacheCenter.updateCarProperty(carProperty);
            mav.setViewName("redirect:/carPropertyView");
            return mav;
        }
    }

    @RequestMapping(value = "/carPropertyDelete", method = RequestMethod.GET)
    public ModelAndView carPropertyDelete(
            @RequestParam(value = "id", required = false, defaultValue = "") Integer id) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:/carPropertyView");

        cacheCenter.deleteCarProperty(id);
        mav.addObject(TIP, urlEncode("删除车辆属性成功！"));
        return mav;
    }
}
