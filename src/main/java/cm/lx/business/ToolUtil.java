package cm.lx.business;

import cm.lx.bean.TwoTuple;
import cm.lx.bean.entity.CarMoneyRecord;
import cm.lx.bean.entity.CarProperty;
import cm.lx.bean.entity.CarSaleSetup;
import cm.lx.bean.entity.MoneyManager;
import cm.lx.common.ContextType;
import cm.lx.enums.CarPropertyEnum;
import cm.lx.service.CarMoneyRecordService;
import cm.lx.service.CarSaleSetupService;
import cm.lx.service.MoneyManagerService;
import cm.lx.util.Utils;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @author linxingwei
 * @date 2019/1/21
 */
public class ToolUtil {

    /**
     * 页面加载车辆配置信息
     *
     * @param mav
     * @param cacheCenter
     */
    public static void initCarPropertyData(ModelAndView mav, CacheCenter cacheCenter) {
        for (String desc : CarPropertyEnum.returnDescs()) {
            mav.addObject(desc, cacheCenter.getCarPropertyByKey(desc));
        }
    }

    /**
     * 设置返回的view
     *
     * @param mav
     * @param remarkType
     */
    public static void setMavView(ModelAndView mav, Integer remarkType) {
        if (remarkType.equals(ContextType.REMARK_TYPE_PURCHASE)) {
            mav.setViewName("redirect:/carPurchaseView");
        } else if (remarkType.equals(ContextType.REMARK_TYPE_STOCK)) {
            mav.setViewName("redirect:/carStockView");
        } else if (remarkType.equals(ContextType.REMARK_TYPE_SALE)) {
            mav.setViewName("redirect:/carSaleView");
        } else if (remarkType.equals(ContextType.REMARK_TYPE_DOSSIER)) {
            mav.setViewName("redirect:/dossierView");
        }
    }

    /**
     * 设置返回的view
     *
     * @param mav
     * @param carRecordStatus
     */
    public static void setMavViewByCarRecordStatus(ModelAndView mav, Integer carRecordStatus) {
        if (carRecordStatus.equals(ContextType.RECORD_STATUS_PURCHASE)) {
            mav.setViewName("redirect:/carPurchaseView");
        } else if (carRecordStatus.equals(ContextType.RECORD_STATUS_STOCK)) {
            mav.setViewName("redirect:/carStockView");
        } else if (carRecordStatus.equals(ContextType.RECORD_STATUS_SALE)) {
            mav.setViewName("redirect:/carSaleView");
        } else if (carRecordStatus.equals(ContextType.RECORD_STATUS_SOLD)) {
            mav.setViewName("redirect:/carSoldView");
        }
    }


    /**
     * 组装CarMoneyRecord并计算总和
     */
    public static TwoTuple<Double, List<CarMoneyRecord>> getCarMoneyRecordTuple(CarMoneyRecordService carMoneyRecordService, CacheCenter cacheCenter, Integer carRecordId, String desc) {
        Double allFee = 0.0;
        List<CarMoneyRecord> carMoneyRecords = carMoneyRecordService.getCarMoneyRecordListByCarRecordIdAndType(carRecordId, desc);
        for (CarMoneyRecord carMoneyRecord : carMoneyRecords) {
            if (carMoneyRecord.getLinkId() != null && carMoneyRecord.getLinkId() != 0) {
                if (cacheCenter != null) {
                    CarProperty carProperty = cacheCenter.getCarPropertyById(carMoneyRecord.getLinkId());
                    carMoneyRecord.setLinkName(carProperty.getPropertyValue());
                }
                allFee += carMoneyRecord.getMoney();
            }
        }
        return new TwoTuple<>(allFee, carMoneyRecords);
    }

    /**
     * 组装carSetup并计算总和
     */
    public static TwoTuple<Double, List<CarSaleSetup>> getCarSaleSetupTuple(CarSaleSetupService carSaleSetupService, Integer carRecordId, Integer type) {
        Double allFee = 0.0;
        List<CarSaleSetup> list = carSaleSetupService.getCarSaleSetupByIdAndType(carRecordId, type);
        for (CarSaleSetup carSaleSetup : list) {
            allFee += carSaleSetup.getSetupFee();
        }
        return new TwoTuple<>(allFee, list);
    }

    /**
     * 统计资金流水并计算总和
     */
    public static Double statDifferentMoneyType(Integer type, MoneyManagerService moneyManagerService) {
        Double temp = 0.0;
        List<MoneyManager> list = moneyManagerService.getMoneyManagerListByType(type);
        for (MoneyManager moneyManager : list) {
            if (moneyManager.getActionType().equals(ContextType.MONEY_MANAGER_IN)) {
                temp += moneyManager.getActionFee();
            } else {
                temp -= moneyManager.getActionFee();
            }
        }
        return Utils.saveTwoSeat(temp);
    }

}
