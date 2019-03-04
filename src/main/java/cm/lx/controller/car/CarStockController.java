package cm.lx.controller.car;

import cm.lx.bean.ContextBean;
import cm.lx.bean.entity.Account;
import cm.lx.business.CacheCenter;
import cm.lx.business.ToolUtil;
import cm.lx.common.ContextType;
import cm.lx.controller.BaseController;
import cm.lx.bean.entity.CarRecord;
import cm.lx.enums.SearchCacheEnum;
import cm.lx.service.CarRecordService;
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
 * @author linxingwei
 * @date 2019/1/22
 */
@Controller
public class CarStockController extends BaseController {

    @Resource
    CacheCenter cacheCenter;

    @Resource
    CarRecordService carRecordService;

    @RequestMapping(value = "/carStockView", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView carStockView(HttpSession session) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("car/stockList");

        Account account = (Account) session.getAttribute("account");
        String key = SearchCacheEnum.STOCK_SEARCH_CACHE.getCode() + "-" + account.getId();
        List<Integer> ids = cacheCenter.getUserSearchResult(key);

        List<ContextBean> list;
        if(ids!=null){
            list = cacheCenter.getCarRecordCombinationInfoByIds(ids);
        }else{
            list = cacheCenter.getCarRecordCombinationInfo(carRecordService.getCarRecordByRecordStatus(ContextType.RECORD_STATUS_STOCK));
        }

        mav.addObject("carNum", list.size());
        mav.addObject("list", list);
        mav.addObject(TIP, session.getAttribute("tip"));

        ToolUtil.initCarPropertyData(mav, cacheCenter);
        return mav;
    }

    @RequestMapping(value = "/carStockReset", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView carStockReset(HttpSession session) {
        ModelAndView mav = new ModelAndView();

        Account account = (Account) session.getAttribute("account");
        String key = SearchCacheEnum.STOCK_SEARCH_CACHE.getCode() + "-" + account.getId();
        cacheCenter.delUserSearchResult(key);
        mav.setViewName("redirect:/carStockView");
        return mav;
    }
}
