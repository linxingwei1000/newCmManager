package cm.lx.controller.car;

import cm.lx.business.CacheCenter;
import cm.lx.business.ToolUtil;
import cm.lx.common.ContextType;
import cm.lx.controller.BaseController;
import cm.lx.bean.entity.CarDossier;
import cm.lx.bean.entity.CarRecord;
import cm.lx.service.CarBathService;
import cm.lx.service.CarDossierService;
import cm.lx.service.CarRecordService;
import cm.lx.util.TimeUtils;
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
public class CarPurchaseController extends BaseController {

    @Resource
    CacheCenter cacheCenter;

    @Resource
    CarRecordService carRecordService;

    @Resource
    CarBathService carBathService;

    @Resource
    CarDossierService carDossierService;

    @RequestMapping(value = "/carPurchaseView", method = RequestMethod.GET)
    public ModelAndView carPurchaseView(HttpSession session) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("car/purchaseList");
        List<CarRecord> list = carRecordService.getCarRecordByRecordStatus(ContextType.RECORD_STATUS_PURCHASE);
        mav.addObject("list", cacheCenter.getCarRecordInfo(list));
        mav.addObject(TIP, session.getAttribute("tip"));
        return mav;
    }

    @RequestMapping(value = "/carPurchaseAction", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView carPurchaseAction(
            @RequestParam(value = "action", required = true, defaultValue = "") Integer action,
            @RequestParam(value = "over", required = false, defaultValue = "") Integer over,
            @RequestParam(value = "id", required = false, defaultValue = "") Integer id,
            @RequestParam(value = "recordStatus", required = false, defaultValue = "") Integer recordStatus,
            @RequestParam(value = "purchaseType", required = false, defaultValue = "") Integer purchaseType,
            @RequestParam(value = "purchaseDate", required = false, defaultValue = "") String purchaseDate,
            @RequestParam(value = "purchasePerson", required = false, defaultValue = "") String purchasePerson,
            @RequestParam(value = "insidePerson", required = false, defaultValue = "") String insidePerson,
            @RequestParam(value = "insideProportion", required = false, defaultValue = "") String insideProportion,
            @RequestParam(value = "outsidePerson", required = false, defaultValue = "") String outsidePerson,
            @RequestParam(value = "outsideProportion", required = false, defaultValue = "") String outsideProportion,
            @RequestParam(value = "carNewSale", required = false, defaultValue = "") Double carNewSale,
            @RequestParam(value = "hallMoney", required = false, defaultValue = "") Double hallMoney,
            @RequestParam(value = "purchaseMoney", required = false, defaultValue = "") Double purchaseMoney,
            @RequestParam(value = "qAuthorityMoney", required = false, defaultValue = "") Double qAuthorityMoney,
            @RequestParam(value = "aAuthorityMoney", required = false, defaultValue = "") Double aAuthorityMoney,
            @RequestParam(value = "agencyFee", required = false, defaultValue = "") Double agencyFee,
            @RequestParam(value = "paidMoney", required = false, defaultValue = "") Double paidMoney,
            @RequestParam(value = "frameNum", required = false, defaultValue = "") String frameNum,
            @RequestParam(value = "keyNum", required = false, defaultValue = "") String keyNum,
            @RequestParam(value = "carBrand", required = false, defaultValue = "") String carBrand,
            @RequestParam(value = "carModel", required = false, defaultValue = "") String carModel,
            @RequestParam(value = "carDisplacement", required = false, defaultValue = "") String carDisplacement,
            @RequestParam(value = "carResource", required = false, defaultValue = "") String carResource,
            @RequestParam(value = "carNumResource", required = false, defaultValue = "") String carNumResource,
            @RequestParam(value = "carCreateTime", required = false, defaultValue = "") String carCreateTime,
            @RequestParam(value = "carPurchaseTime", required = false, defaultValue = "") String carPurchaseTime,
            @RequestParam(value = "carOutColour", required = false, defaultValue = "") String carOutColour,
            @RequestParam(value = "carInsideColour", required = false, defaultValue = "") String carInsideColour,
            @RequestParam(value = "carRunNum", required = false, defaultValue = "") String carRunNum,
            @RequestParam(value = "carLine", required = false, defaultValue = "") Integer carLine,
            @RequestParam(value = "carLevel", required = false, defaultValue = "") Integer carLevel,
            @RequestParam(value = "carChannel", required = false, defaultValue = "") Integer carChannel,
            @RequestParam(value = "carTakeType", required = false, defaultValue = "") Integer carTakeType,
            @RequestParam(value = "carStatus", required = false, defaultValue = "") Integer carStatus,
            @RequestParam(value = "carStatusDesc", required = false, defaultValue = "") String carStatusDesc,
            @RequestParam(value = "carConfig", required = false, defaultValue = "") String carConfig,
            @RequestParam(value = "isBath", required = false, defaultValue = "") Integer isBath,
            @RequestParam(value = "bathId", required = false, defaultValue = "") Integer bathId,
            HttpSession session) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("car/purchaseAdd");
        mav.addObject("action", action);
        mav.addObject("recordStatus", recordStatus);
        ToolUtil.initCarPropertyData(mav, cacheCenter);

        //批量采购加入页面
        mav.addObject(ContextType.CAR_BATH_IDS, carBathService.getCarBathListByAccount(null));

        mav.addObject("purchaseType", purchaseType);
        mav.addObject("purchaseDate", purchaseDate);
        mav.addObject("purchasePerson", purchasePerson);
        mav.addObject("insidePerson", insidePerson);
        mav.addObject("insideProportion", insideProportion);
        mav.addObject("outsidePerson", outsidePerson);
        mav.addObject("outsideProportion", outsideProportion);
        mav.addObject("carNewSale", carNewSale);
        mav.addObject("hallMoney", hallMoney);
        mav.addObject("purchaseMoney", purchaseMoney);
        mav.addObject("qAuthorityMoney", qAuthorityMoney);
        mav.addObject("aAuthorityMoney", aAuthorityMoney);
        mav.addObject("agencyFee", agencyFee);
        mav.addObject("paidMoney", paidMoney);
        mav.addObject("frameNum", frameNum);
        mav.addObject("keyNum", keyNum);
        mav.addObject("carBrand", carBrand);
        mav.addObject("carModel", carModel);
        mav.addObject("carDisplacement", carDisplacement);
        mav.addObject("carResource", carResource);
        mav.addObject("carNumResource", carNumResource);
        mav.addObject("carCreateTime", carCreateTime);
        mav.addObject("carPurchaseTime", carPurchaseTime);
        mav.addObject("carOutColour", carOutColour);
        mav.addObject("carInsideColour", carInsideColour);
        mav.addObject("carRunNum", carRunNum);
        mav.addObject("carLine", carLine);
        mav.addObject("carLevel", carLevel);
        mav.addObject("carChannel", carChannel);
        mav.addObject("carTakeType", carTakeType);
        mav.addObject("carStatus", carStatus);
        mav.addObject("carStatusDesc", carStatusDesc);
        mav.addObject("carConfig", carConfig);
        mav.addObject("isBath", isBath);
        mav.addObject("bathId", bathId);

        if (over == null) {
            if (id != null) {
                CarRecord carRecord = carRecordService.getCarRecordById(id);
                mav.addObject("id", id);
                mav.addObject("purchaseType", carRecord.getPurchaseType());
                mav.addObject("purchaseDate", TimeUtils.transformTimetagToDate(carRecord.getPurchaseDate(), TimeUtils.FORMAT_ONE));
                mav.addObject("purchasePerson", carRecord.getPurchasePerson());
                mav.addObject("insidePerson", carRecord.getInsidePerson());
                mav.addObject("insideProportion", carRecord.getInsideProportion());
                mav.addObject("outsidePerson", carRecord.getOutsidePerson());
                mav.addObject("outsideProportion", carRecord.getOutsideProportion());
                mav.addObject("carNewSale", carRecord.getCarNewSale());
                mav.addObject("hallMoney", carRecord.getHallMoney());
                mav.addObject("purchaseMoney", carRecord.getPurchaseMoney());
                mav.addObject("qAuthorityMoney", carRecord.getqAuthorityMoney());
                mav.addObject("aAuthorityMoney", carRecord.getaAuthorityMoney());
                mav.addObject("agencyFee", carRecord.getAgencyFee());
                mav.addObject("paidMoney", carRecord.getPaidMoney());
                mav.addObject("frameNum", carRecord.getFrameNum());
                mav.addObject("keyNum", carRecord.getKeyNum());
                mav.addObject("carBrand", carRecord.getCarBrand());
                mav.addObject("carModel", carRecord.getCarModel());
                mav.addObject("carDisplacement", carRecord.getCarDisplacement());
                mav.addObject("carResource", carRecord.getCarResource());
                mav.addObject("carNumResource", carRecord.getCarNumResource());
                mav.addObject("carCreateTime", carRecord.getCarCreateTime());
                mav.addObject("carPurchaseTime", carRecord.getCarPurchaseTime());
                mav.addObject("carOutColour", carRecord.getCarOutColour());
                mav.addObject("carInsideColour", carRecord.getCarInsideColour());
                mav.addObject("carRunNum", carRecord.getCarRunNum());
                mav.addObject("carLine", carRecord.getCarLine());
                mav.addObject("carLevel", carRecord.getCarLevel());
                mav.addObject("carChannel", carRecord.getCarChannel());
                mav.addObject("carTakeType", carRecord.getCarTakeType());
                mav.addObject("carStatus", carRecord.getCarStatus());
                mav.addObject("carStatusDesc", carRecord.getCarStatusDesc());
                mav.addObject("carConfig", carRecord.getCarConfig());
                mav.addObject("isBath", carRecord.getIsBath());
                mav.addObject("bathId", carRecord.getBathId());
            }
            return mav;
        } else {
            if (StringUtils.isEmpty(purchaseDate)) {
                mav.addObject(TIP, "采购日期不能为空！");
                return mav;
            }

            if (StringUtils.isEmpty(purchasePerson)) {
                mav.addObject(TIP, "采购人不能为空！");
                return mav;
            }
            if (purchaseMoney == null) {
                mav.addObject(TIP, "采购价格不能为空！");
                return mav;
            }
            if (paidMoney == null) {
                mav.addObject(TIP, "已付金额不能为空！");
                return mav;
            }
            if (StringUtils.isEmpty(carBrand)) {
                mav.addObject(TIP, "品牌不能为空！");
                return mav;
            }
            if (StringUtils.isEmpty(keyNum)) {
                mav.addObject(TIP, "钥匙标号不能为空！");
                return mav;
            }
            if (StringUtils.isEmpty(carModel)) {
                mav.addObject(TIP, "车型不能为空！");
                return mav;
            }
            if (!StringUtils.isEmpty(insideProportion)) {
                try {
                    double a = Double.valueOf(insideProportion);
                } catch (Exception e) {
                    mav.addObject(TIP, "内部比例格式错误！");
                    return mav;
                }
            }
            if (!StringUtils.isEmpty(outsideProportion)) {
                try {
                    double a = Double.valueOf(outsideProportion);
                } catch (Exception e) {
                    mav.addObject(TIP, "外部比例格式错误！");
                    return mav;
                }
            }

            if (isBath == 1 && bathId == 0) {
                mav.addObject(TIP, "批量采购必填批量采购标题参数！");
                return mav;
            }

            CarRecord carRecord = new CarRecord();
            carRecord.setPurchaseDate(TimeUtils.transformDateToTimetag(purchaseDate, TimeUtils.FORMAT_ONE));
            carRecord.setPurchasePerson(purchasePerson);
            carRecord.setFrameNum(frameNum == null ? "" : frameNum);
            carRecord.setKeyNum(keyNum);
            carRecord.setCarBrand(carBrand);
            carRecord.setCarModel(carModel);
            carRecord.setCarDisplacement(carDisplacement == null ? "" : carDisplacement);
            carRecord.setCarResource(carResource == null ? "" : carResource);
            carRecord.setCarNumResource(carNumResource == null ? "" : carNumResource);
            carRecord.setCarCreateTime(carCreateTime);
            carRecord.setCarPurchaseTime(carPurchaseTime);
            carRecord.setCarOutColour(carOutColour);
            carRecord.setCarInsideColour(carInsideColour);
            carRecord.setCarRunNum(carRunNum);
            carRecord.setCarLine(carLine);
            carRecord.setCarLevel(carLevel);
            carRecord.setCarStatus(carStatus);
            carRecord.setCarStatusDesc(carStatusDesc);
            carRecord.setCarConfig(carConfig == null ? "" : carConfig);
            carRecord.setCarChannel(carChannel);
            carRecord.setPurchaseType(purchaseType);
            carRecord.setCarTakeType(carTakeType);
            carRecord.setInsidePerson(insidePerson);
            carRecord.setInsideProportion(insideProportion);
            carRecord.setOutsidePerson(outsidePerson);
            carRecord.setOutsideProportion(outsideProportion);
            carRecord.setPaidMoney(paidMoney);
            carRecord.setCarNewSale(carNewSale == null ? 0 : carNewSale);
            carRecord.setHallMoney(hallMoney == null ? 0 : hallMoney);
            carRecord.setAgencyFee(agencyFee == null ? 0 : agencyFee);
            carRecord.setPurchaseMoney(purchaseMoney);
            carRecord.setqAuthorityMoney(qAuthorityMoney == null ? 0 : qAuthorityMoney);
            carRecord.setaAuthorityMoney(aAuthorityMoney == null ? 0 : aAuthorityMoney);

            mav.clear();
            if (action.equals(CREATE_ACTION)) {
                carRecord.setSoldDate(0L);
                carRecord.setRecordStatus(ContextType.RECORD_STATUS_PURCHASE);
                carRecord.setIsBath(isBath);
                carRecord.setBathId(isBath == 0 ? 0 : bathId);
                carRecord.setIsCost(0);
                carRecord.setCostId(0);
                carRecord.setIsSale(0);
                carRecord.setSaleId(0);
                carRecord.setIsSf(0);
                carRecord.setSfId(0);
                carRecordService.createCarRecord(carRecord);
                session.setAttribute("tip", "ok 采购录入成功！");

                //新建一个档案数据记录
                CarDossier carDossier = new CarDossier();
                carDossier.setCarRecordId(carRecord.getId());
                carDossier.setDisplayStatus(ContextType.DISPLAY_STATUS_SHOW);
                carDossierService.createCarDossier(carDossier);

                //批量采购操作
                if (isBath == 1) {
                    carBathService.modCarRecordId(bathId, carRecord.getId(), 1);
                }
            } else if (action.equals(MOD_ACTION)) {
                //批量采购操作
                CarRecord oldCarRecord = carRecordService.getCarRecordById(id);
                if (oldCarRecord.getIsBath() == 0 && isBath == 0) {
                } else if (oldCarRecord.getIsBath() == 0 && isBath == 1) {
                    carBathService.modCarRecordId(bathId, oldCarRecord.getId(), 1);
                } else if (oldCarRecord.getIsBath() == 1 && isBath == 0) {
                    carBathService.modCarRecordId(oldCarRecord.getBathId(), oldCarRecord.getId(), 0);
                } else if (oldCarRecord.getIsBath() == 1 && isBath == 1) {
                    if (oldCarRecord.getBathId().intValue() != bathId) {
                        carBathService.modCarRecordId(oldCarRecord.getBathId(), oldCarRecord.getId(), 0);
                        carBathService.modCarRecordId(bathId, oldCarRecord.getId(), 1);
                    }
                }

                carRecord.setId(id);
                carRecordService.updateCarRecord(carRecord);
                session.setAttribute(TIP, "ok 采购修改成功！");
            }

            if (recordStatus.equals(ContextType.RECORD_STATUS_PURCHASE)) {
                mav.setViewName("redirect:/carPurchaseView");
            } else if (recordStatus.equals(ContextType.RECORD_STATUS_STOCK)) {
                mav.setViewName("redirect:/carStockView");
            } else if (recordStatus.equals(ContextType.RECORD_STATUS_SALE)) {
                mav.setViewName("redirect:/carSaleView");
            }
            return mav;
        }
    }

}
