package cm.lx.controller;

import cm.lx.bean.*;
import cm.lx.business.CacheCenter;
import cm.lx.business.CommonAction;
import cm.lx.business.DaoCenter;
import cm.lx.common.ContextType;
import cm.lx.util.ExportExcel;
import cm.lx.util.TimeUtils;
import cm.lx.util.Utils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.*;

@Controller
public class CarController extends BaseController {

    @Resource
    DaoCenter daoCenter;

    @Resource
    CacheCenter cacheCenter;

    List<ContextBean> contextBeanList = new ArrayList<>();

    //车辆采购相关操作
    private List<CarRecord> dealList(List<CarRecord> list) {
        //准备显示的数据
        for (CarRecord carRecord : list) {
            carRecord.setStrPurchaseDate(TimeUtils.transformTimetagToDate(carRecord.getPurchaseDate(), TimeUtils.FORMAT_ONE));

            if (carRecord.getCarLine() != 0) {
                carRecord.setStrCarLine(cacheCenter.getCarPropertyById(carRecord.getCarLine()).getPropertyValue());
            }

            if (carRecord.getCarLevel() != 0) {
                carRecord.setStrCarLevel(cacheCenter.getCarPropertyById(carRecord.getCarLevel()).getPropertyValue());
            }

            if (carRecord.getCarStatus() != 0) {
                carRecord.setStrCarStatus(cacheCenter.getCarPropertyById(carRecord.getCarStatus()).getPropertyValue());
            }

            if (carRecord.getCarTakeType() != 0) {
                carRecord.setStrCarTakeType(cacheCenter.getCarPropertyById(carRecord.getCarTakeType()).getPropertyValue());
            }

            if (carRecord.getCarChannel() != 0) {
                carRecord.setStrCarChannel(cacheCenter.getCarPropertyById(carRecord.getCarChannel()).getPropertyValue());
            }

            if (carRecord.getPurchaseType() != 0) {
                carRecord.setStrPurchaseType(cacheCenter.getCarPropertyById(carRecord.getPurchaseType()).getPropertyValue());
            }
            if (carRecord.getSoldDate() != 0) {
                carRecord.setStrSoldDate(TimeUtils.transformTimetagToDate(carRecord.getSoldDate(), TimeUtils.FORMAT_ONE));
            }

            Map<String, Integer> map = new HashMap<>();
            map.put("linkId", carRecord.getId());
            map.put("recordStatus", ContextType.PAY_RECORD_PURCHASE);
            carRecord.setPurchasePaidList(daoCenter.getCarPaidRecordList(map));

            map.clear();
            map.put("linkId", carRecord.getId());
            map.put("remarkType", ContextType.REMARK_TYPE_PURCHASE);
            carRecord.setPurchaseRemark(daoCenter.getCarRemarkList(map));

            map.put("remarkType", ContextType.REMARK_TYPE_STOCK);
            List<CarRemark> temp = daoCenter.getCarRemarkList(map);
            temp.addAll(carRecord.getPurchaseRemark());
            carRecord.setStockRemark(temp);

            map.put("remarkType", ContextType.REMARK_TYPE_SALE);
            temp = daoCenter.getCarRemarkList(map);
            temp.addAll(carRecord.getStockRemark());
            carRecord.setSaleRemark(temp);

        }
        return list;
    }

    private List<ContextBean> installContextBean(List<CarRecord> list, ModelAndView mav) {
        List<ContextBean> contextList = new ArrayList<ContextBean>();
        for (CarRecord carRecord : list) {
            ContextBean contextBean = new ContextBean();
            CarCost carCost = null;
            if (carRecord.getIsCost() == 1) {
                carCost = daoCenter.getCarCostById(carRecord.getCostId());
                carCost.addAll();
            }
            if (carCost == null) {
                carCost = new CarCost();
                carCost.setPreSaleFee(0.0);
                carCost.setOtherIncomeFee(0.0);
                carCost.setAfterSaleFee(0.0);
                carRecord.setIsCost(0);
            }
            contextBean.setCarRecord(carRecord);
            contextBean.setCarCost(carCost);

            //获取售前整备明细
            if (carCost.getPreSaleFee() != 0) {
                contextBean.setPreList(daoCenter.getCarSaleSetupByCarCostId(carRecord.getCostId(), ContextType.PRE_SETUP_TYPE));
            }

            //车辆其他收入明细
            if (carCost.getOtherIncomeFee() != 0) {
                contextBean.setOtherList(daoCenter.getCarSaleSetupByCarCostId(carRecord.getCostId(), ContextType.OTHER_INCOME));
            }

            //获取售后整备明细
            if (carCost.getAfterSaleFee() != 0) {
                contextBean.setAfterList(daoCenter.getCarSaleSetupByCarCostId(carRecord.getCostId(), ContextType.AFTER_SETUP_TYPE));
            }

            //获取销售信息
            if (carRecord.getIsSale() == 1) {
                CarSaleInfo carSaleInfo = daoCenter.getCarSaleInfoById(carRecord.getSaleId());
                if (carSaleInfo.getSaleType().equals(ContextType.SALE_TYPE_AJ)) {
                    carSaleInfo.setMortgageRecord(daoCenter.getMortgageRecordById(carSaleInfo.getMortgageId()));
                }
                dealCarSaleInfo(carSaleInfo);
                contextBean.setCarSaleInfo(carSaleInfo);

                Map<String, Integer> map = new HashMap<String, Integer>();
                map.put("linkId", carSaleInfo.getId());
                contextBean.setMoneyAssistList(daoCenter.getCarPayMoneyAssistList(map));

                //设置页面显示类型
                mav.addObject("saleType", carSaleInfo.getSaleType());
            }

            //获取销售成本信息
            if (carRecord.getIsSf() == 1) {
                CarSf carSf = daoCenter.getCarSfById(carRecord.getSfId());
                carSf.addAll();
                contextBean.setCarSf(carSf);

                if (carSf.getSaleFee() != 0) {
                    contextBean.setSaleList(daoCenter.getCarSaleSetupByCarCostId(carRecord.getSfId(), ContextType.SALE_TYPE));
                }

            }
            //获取每辆车售后工资相关信息
            if (carRecord.getExpenseId() != null && carRecord.getExpenseId() != 0) {
                contextBean.setWagesAssist(daoCenter.getWagesAssist(carRecord.getExpenseId()));
            }

            //插入付款记录
            Map<String, Integer> map = new HashMap<String, Integer>();
            map.put("linkId", carRecord.getId());
            map.put("recordStatus", ContextType.PAY_RECORD_SALE);
            contextBean.setSalePaidList(daoCenter.getCarPaidRecordList(map));

            map.put("recordStatus", ContextType.PAY_RECORD_MORTGAGE);
            contextBean.setMortgagePaidList(daoCenter.getCarPaidRecordList(map));

            map.put("recordStatus", ContextType.PAY_RECORD_BACK);
            contextBean.setBackList(daoCenter.getCarPaidRecordList(map));

            contextList.add(contextBean);
        }
        //数据加入缓存
        if (contextBeanList.size() != 0) contextBeanList.clear();
        contextBeanList.addAll(contextList);

        mav.addObject("list", contextList);
        return contextList;
    }

    @RequestMapping(value = "/carStatusChange", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView carStatusChange(@RequestParam(value = "changeType", required = true, defaultValue = "") Integer changeType,
                                        @RequestParam(value = "deposit", required = false, defaultValue = "") Double deposit,
                                        @RequestParam(value = "id", required = false, defaultValue = "") Integer id,
                                        @RequestParam(value = "backMoney", required = false, defaultValue = "") Double backMoney,
                                        @RequestParam(value = "backDesc", required = false, defaultValue = "") String backDesc,
                                        HttpSession session) {
        ModelAndView mav = new ModelAndView();
        CarRecord old = daoCenter.getCarRecordById(id);
        CarRecord carRecord = new CarRecord();
        carRecord.setId(id);

        if (changeType.equals(ContextType.CAR_STATUS_FROM_STOCK_TO_SALE)) {
            mav.setViewName("redirect:/carStockView");

            if (old.getIsCost() == 0) {
                session.setAttribute("tip", "成本未录入，无法转销售！");
                return mav;
            }
            carRecord.setRecordStatus(ContextType.RECORD_STATUS_SALE);
            Double oldDeposit = old.getDeposit() == null ? 0.0 : old.getDeposit();
            carRecord.setDeposit(deposit == null ? 0.0 : deposit + oldDeposit);
        } else if (changeType.equals(ContextType.CAR_STATUS_FROM_SALE_TO_STOCK)) {
            mav.setViewName("redirect:/carSaleView");
            if (backMoney == null) backMoney = 0.0;
            Double d = old.getDeposit() - backMoney;
            if (d < 0) {
                session.setAttribute("tip", "退款金额不能大于订金！");
                return mav;
            } else if (d == 0) {
                //清空在售页面填入的信息
                if (old.getIsSale() == 1) {
                    CarSaleInfo carSaleInfo = daoCenter.getCarSaleInfoById(old.getSaleId());
                    daoCenter.deleteCarSaleInfo(old.getSaleId());
                    carRecord.setIsSale(0);
                    carRecord.setSaleId(0);

                    //删除付款记录表
                    Map<String, Integer> map = new HashMap<String, Integer>();
                    map.put("linkId", carSaleInfo.getId());
                    map.put("recordStatus", ContextType.PAY_RECORD_SALE);
                    daoCenter.deleteCarPaidRecordList(map);

                    if (carSaleInfo.getSaleType().equals(ContextType.SALE_TYPE_AJ)) {
                        map.put("linkId", carSaleInfo.getMortgageId());
                        map.put("recordStatus", ContextType.PAY_RECORD_MORTGAGE);
                        daoCenter.deleteCarPaidRecordList(map);
                    }
                }

                if (old.getIsSf() == 1) {
                    daoCenter.deleteCarSf(old.getSfId());
                    Map<String, Integer> map = new HashMap<String, Integer>();
                    map.put("linkId", old.getSfId());
                    map.put("type", ContextType.SALE_TYPE);
                    daoCenter.deleteCarSaleSetupByMap(map);
                    carRecord.setIsSf(0);
                    carRecord.setSfId(0);
                }
                carRecord.setSoldDate(0L);
            }
            carRecord.setRecordStatus(ContextType.RECORD_STATUS_STOCK);
            carRecord.setDeposit(d);

            //添加退订记录
            CarPaidRecord carPaidRecord = new CarPaidRecord();
            carPaidRecord.setCarRecordId(id);
            carPaidRecord.setRecordStatus(ContextType.PAY_RECORD_BACK);
            carPaidRecord.setPaidMoney(backMoney);
            carPaidRecord.setPaidReason(backDesc == null ? "" : backDesc);
            daoCenter.createCarPaidRecord(carPaidRecord);

        } else if (changeType.equals(ContextType.CAR_STATUS_FROM_SOLD_TO_SALE)) {
            mav.setViewName("redirect:/carSoldView");
            carRecord.setRecordStatus(ContextType.RECORD_STATUS_SALE);

//            //删除车辆工资提成表信息
//            Map<String, Integer> map = new HashMap<String, Integer>();
//            map.put("linkId", carRecord.getId());
//            daoCenter.deleteWagesAssistByMap(map);
        }
        daoCenter.updateCarRecord(carRecord);
        session.setAttribute("tip", "ok 车辆订单状态修改成功！");
        return mav;
    }


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


    //批量采购相关操作
    @RequestMapping(value = "/carBathView", method = RequestMethod.GET)
    public ModelAndView carBathView() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("car/bathList");
        mav.addObject(DATA, daoCenter.getCarBathListByKey(null));
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
                CarBath carBath = daoCenter.getCarBathById(id);
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

            if (daoCenter.getCarBathByName(bathName) != null && action.equals(CREATE_ACTION)) {
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
                daoCenter.createCarBath(carBath);
                mav.addObject(TIP, "ok 批量采购创建成功！");
            } else if (action.equals(MOD_ACTION)) {
                carBath.setId(id);
                daoCenter.updateCarBath(carBath);
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

        CarBath carBath = daoCenter.getCarBathById(id);
        if (carBath == null) {
            mav.addObject(TIP, urlEncode("要删除的批量采购不存在！"));
            return mav;
        }

        if (!StringUtils.isEmpty(carBath.getCarRecordId())) {
            mav.addObject(TIP, urlEncode("要删除的批量采购有采购车辆在，操作被禁止！"));
            return mav;
        }

        daoCenter.delCarBathById(id);
        mav.addObject(TIP, urlEncode("删除批量采购【" + carBath.getBathName() + "】成功！"));
        return mav;
    }

    private List<CarDeposit> dealDepositList(List<CarDeposit> list) {
        for (CarDeposit carDeposit : list) {
            carDeposit.setStrDepositDate(TimeUtils.transformTimetagToDate(carDeposit.getDepositDate(), TimeUtils.FORMAT_ONE));
            carDeposit.setStrGiveCarDate(TimeUtils.transformTimetagToDate(carDeposit.getGiveCarDate(), TimeUtils.FORMAT_ONE));

            Map<String, Integer> map = new HashMap<String, Integer>();
            map.put("linkId", carDeposit.getId());
            map.put("recordStatus", ContextType.PAY_RECORD_DEPOSIT);
            carDeposit.setDepositPaidList(daoCenter.getCarPaidRecordList(map));
        }
        return list;
    }

    @RequestMapping(value = "/carDepositView", method = RequestMethod.GET)
    public ModelAndView carDepositView(HttpSession session) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("car/depositList");
        mav.addObject("list", dealDepositList(daoCenter.getCarDepositList(null)));
        mav.addObject(TIP, session.getAttribute("tip"));
        return mav;
    }

    @RequestMapping(value = "/carDepositAction", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView carDepositAction(
            @RequestParam(value = "action", required = true, defaultValue = "") Integer action,
            @RequestParam(value = "over", required = false, defaultValue = "") Integer over,
            @RequestParam(value = "id", required = false, defaultValue = "") Integer id,
            @RequestParam(value = "salePerson", required = false, defaultValue = "") String salePerson,
            @RequestParam(value = "depositDate", required = false, defaultValue = "") String depositDate,
            @RequestParam(value = "carModel", required = false, defaultValue = "") String carModel,
            @RequestParam(value = "carColor", required = false, defaultValue = "") String carColor,
            @RequestParam(value = "carYear", required = false, defaultValue = "") String carYear,
            @RequestParam(value = "carConfig", required = false, defaultValue = "") String carConfig,
            @RequestParam(value = "budget", required = false, defaultValue = "") String budget,
            @RequestParam(value = "giveCarDate", required = false, defaultValue = "") String giveCarDate,
            @RequestParam(value = "deposit", required = false, defaultValue = "") Double deposit,
            HttpSession session) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("car/depositAdd");
        mav.addObject("action", action);

        mav.addObject("salePerson", salePerson);
        mav.addObject("depositDate", depositDate);
        mav.addObject("carModel", carModel);
        mav.addObject("carColor", carColor);
        mav.addObject("carYear", carYear);
        mav.addObject("carConfig", carConfig);
        mav.addObject("budget", budget);
        mav.addObject("giveCarDate", giveCarDate);
        mav.addObject("deposit", deposit);
        if (over == null) {
            if (id != null) {
                CarDeposit carDeposit = daoCenter.getCarDeposit(id);
                mav.addObject("id", id);
                mav.addObject("salePerson", carDeposit.getSalePerson());
                mav.addObject("depositDate", TimeUtils.transformTimetagToDate(carDeposit.getDepositDate(), TimeUtils.FORMAT_ONE));
                mav.addObject("carModel", carDeposit.getCarModel());
                mav.addObject("carColor", carDeposit.getCarColor());
                mav.addObject("carYear", carDeposit.getCarYear());
                mav.addObject("carConfig", carDeposit.getCarConfig());
                mav.addObject("budget", carDeposit.getBudget());
                mav.addObject("giveCarDate", TimeUtils.transformTimetagToDate(carDeposit.getGiveCarDate(), TimeUtils.FORMAT_ONE));
                mav.addObject("deposit", carDeposit.getDeposit());
            }
            return mav;
        } else {
            if (StringUtils.isEmpty(salePerson)) {
                mav.addObject(TIP, "销售员必填！");
                return mav;
            }

            if (StringUtils.isEmpty(depositDate)) {
                mav.addObject(TIP, "收订金日期必填！");
                return mav;
            }

            if (StringUtils.isEmpty(giveCarDate)) {
                mav.addObject(TIP, "交车日期必填！");
                return mav;
            }

            CarDeposit carDeposit = new CarDeposit();
            carDeposit.setSalePerson(salePerson);
            carDeposit.setDepositDate(TimeUtils.transformDateToTimetag(depositDate, TimeUtils.FORMAT_ONE));
            carDeposit.setCarModel(carModel);
            carDeposit.setCarColor(carColor);
            carDeposit.setCarYear(carYear);
            carDeposit.setCarConfig(carConfig);
            carDeposit.setBudget(budget);
            carDeposit.setGiveCarDate(TimeUtils.transformDateToTimetag(giveCarDate, TimeUtils.FORMAT_ONE));
            carDeposit.setDeposit(deposit);
            mav.clear();
            if (action.equals(CREATE_ACTION)) {
                daoCenter.createCarDeposit(carDeposit);
                session.setAttribute(TIP, "ok 创建成功！");
            } else if (action.equals(MOD_ACTION)) {
                carDeposit.setId(id);
                daoCenter.updateCarDeposit(carDeposit);
                session.setAttribute(TIP, "ok 修改成功！");
            }
            mav.setViewName("redirect:/carDepositView");
            return mav;
        }
    }

    @RequestMapping(value = "/carDepositPaid", method = RequestMethod.GET)
    public ModelAndView carDepositPaid(
            @RequestParam(value = "goonPaid", required = false, defaultValue = "") Double goonPaid,
            @RequestParam(value = "paidReason", required = false, defaultValue = "") String paidReason,
            @RequestParam(value = "id", required = false, defaultValue = "") Integer id,
            HttpSession session) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:/carDepositView");
        if (goonPaid == null) {
            session.setAttribute("tip", "付款金额未填！");
            return mav;
        }
        CarDeposit old = daoCenter.getCarDeposit(id);

        CarDeposit update = new CarDeposit();
        update.setId(id);
        update.setDeposit(old.getDeposit() + goonPaid);
        daoCenter.updateCarDeposit(update);
        session.setAttribute("tip", "ok 付款成功！");

        //创建付款记录
        CarPaidRecord carPaidRecord = new CarPaidRecord();
        carPaidRecord.setCarRecordId(id);
        carPaidRecord.setRecordStatus(ContextType.PAY_RECORD_DEPOSIT);
        carPaidRecord.setPaidMoney(goonPaid);
        carPaidRecord.setPaidReason(paidReason);
        daoCenter.createCarPaidRecord(carPaidRecord);

        return mav;
    }

    @RequestMapping(value = "/carDepositDelete", method = RequestMethod.GET)
    public ModelAndView carDepositDelete(
            @RequestParam(value = "id", required = false, defaultValue = "") Integer id, HttpSession session) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:/carDepositView");
        daoCenter.deleteCarDeposit(id);

        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("linkId", id);
        //删除付款记录表
        daoCenter.deleteCarPaidRecordList(map);
        session.setAttribute(TIP, "ok 删除成功！");
        return mav;
    }


    @RequestMapping(value = "/carPurchaseView", method = RequestMethod.GET)
    public ModelAndView carPurchaseView(HttpSession session) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("car/purchaseList");
        mav.addObject("list", dealList(daoCenter.getCarRecordListByRecordStatus(ContextType.RECORD_STATUS_PURCHASE)));
        mav.addObject(TIP, session.getAttribute("tip"));
        return mav;
    }

    @RequestMapping(value = "/carSearchPurchase", method = RequestMethod.GET)
    public ModelAndView carSearchPurchase(
            @RequestParam(value = "recordStatus", required = false, defaultValue = "") Integer recordStatus,
            @RequestParam(value = "searchKey", required = false, defaultValue = "") String searchKey,
            @RequestParam(value = "searchValue", required = false, defaultValue = "") String searchValue,
            @RequestParam(value = "btime", required = false, defaultValue = "") String btime,
            @RequestParam(value = "etime", required = false, defaultValue = "") String etime,
            @RequestParam(value = "zbtime", required = false, defaultValue = "") String zbtime,
            @RequestParam(value = "zetime", required = false, defaultValue = "") String zetime,
            HttpSession session) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("export", 1);
        mav.addObject("recordStatus", recordStatus);
        mav.addObject("searchKey", searchKey);
        mav.addObject("searchValue", searchValue);
        mav.addObject("btime", btime);
        mav.addObject("etime", etime);
        mav.addObject("zbtime", zbtime);
        mav.addObject("zetime", zetime);

        Long bt = StringUtils.isEmpty(btime) ? 0L : TimeUtils.transformDateToTimetag(btime, TimeUtils.FORMAT_ONE);
        Long et = StringUtils.isEmpty(etime) ? System.currentTimeMillis() : TimeUtils.transformDateToTimetag(etime, TimeUtils.FORMAT_ONE);
        Long zbt = StringUtils.isEmpty(zbtime) ? 0L : TimeUtils.transformDateToTimetag(zbtime, TimeUtils.FORMAT_ONE);
        Long zet = StringUtils.isEmpty(zetime) ? System.currentTimeMillis() : TimeUtils.transformDateToTimetag(zetime, TimeUtils.FORMAT_ONE);

        QueryWrapper<CarRecord> query = new QueryWrapper<>();
        query.eq("record_status", recordStatus);
        if (recordStatus.equals(ContextType.RECORD_STATUS_SOLD)) {
            query.between("sold_date", zbt, zet);
        } else if (recordStatus.equals(ContextType.RECORD_STATUS_STOCK)) {
            query.between("purchase_date", zbt, zet);
        }

        List<CarRecord> list;
        if (searchKey.equals("sale_date")) {
            query.eq("is_sale", 1);
            list = daoCenter.getCarRecordByQuery(query);

            List<CarRecord> resultList = new ArrayList<>();
            for(CarRecord carRecord:list){
                query.between("purchase_date", zbt, zet);
                CarSaleInfo carSaleInfo = daoCenter.getCarSaleInfoById(carRecord.getSaleId());
                if (bt <= carSaleInfo.getSaleDate() && carSaleInfo.getSaleDate() <= et) {
                    resultList.add(carRecord);
                }
            }
            list = resultList;
        } else{
            query.like(searchKey, searchValue);
            list = daoCenter.getCarRecordByQuery(query);
        }
//        List<CarRecord> list = daoCenter.getCarRecordListByRecordStatus(recordStatus);
//        List<CarRecord> resultList = new ArrayList<CarRecord>();
//        for (CarRecord carRecord : list) {
//            if (recordStatus.equals(ContextType.RECORD_STATUS_SOLD)) {
//                if (carRecord.getSoldDate() <= zbt || zet <= carRecord.getSoldDate()) continue;
//            } else if (recordStatus.equals(ContextType.RECORD_STATUS_STOCK)) {
//                if (carRecord.getPurchaseDate() <= zbt || zet <= carRecord.getPurchaseDate()) continue;
//            }
//
//            if (searchKey.equals("frameNum")) {
//                if (carRecord.getFrameNum().contains(searchValue)) {
//                    resultList.add(carRecord);
//                }
//            } else if (searchKey.equals("keyNum")) {
//                if (carRecord.getKeyNum().contains(searchValue)) {
//                    resultList.add(carRecord);
//                }
//            } else if (searchKey.equals("carBrand")) {
//                if (carRecord.getCarBrand().contains(searchValue)) {
//                    resultList.add(carRecord);
//                }
//            } else if (searchKey.equals("carModel")) {
//                if (carRecord.getCarModel().contains(searchValue)) {
//                    resultList.add(carRecord);
//                }
//            } else if (searchKey.equals("purchasePerson")) {
//                if (carRecord.getPurchasePerson().equals(searchValue)) {
//                    resultList.add(carRecord);
//                }
//            } else if (searchKey.equals("insidePerson")) {
//                if (carRecord.getInsidePerson().equals(searchValue)) {
//                    resultList.add(carRecord);
//                }
//            } else if (searchKey.equals("purchaseDate")) {
//                if (bt <= carRecord.getPurchaseDate() && carRecord.getPurchaseDate() <= et) {
//                    resultList.add(carRecord);
//                }
//            } else if (searchKey.equals("saleDate")) {
//                if (carRecord.getIsSale() == 0) continue;
//                CarSaleInfo carSaleInfo = daoCenter.getCarSaleInfoById(carRecord.getSaleId());
//                if (bt <= carSaleInfo.getSaleDate() && carSaleInfo.getSaleDate() <= et) {
//                    resultList.add(carRecord);
//                }
//            } else {
//                resultList.add(carRecord);
//            }
//        }

        List<CarRecord> doneList = dealList(list);
        mav.addObject("carNum", doneList.size());
        if (recordStatus.equals(ContextType.RECORD_STATUS_PURCHASE)) {
            mav.addObject("list", doneList);
            mav.setViewName("car/purchaseList");
        } else if (recordStatus.equals(ContextType.RECORD_STATUS_STOCK)) {
            installContextBean(doneList, mav);
            mav.setViewName("car/stockList");
        } else if (recordStatus.equals(ContextType.RECORD_STATUS_SALE)) {
            installContextBean(doneList, mav);
            mav.setViewName("car/saleList");
        } else if (recordStatus.equals(ContextType.RECORD_STATUS_SOLD)) {
            installContextBean(doneList, mav);
            mav.setViewName("car/soldList");
        }
        //}
        return mav;
    }

    private void initCarPropertyData(ModelAndView mav) {
        mav.addObject(ContextType.CAR_LINE, cacheCenter.getCarPropertyByKey(ContextType.CAR_LINE));
        mav.addObject(ContextType.CAR_LEVEL, cacheCenter.getCarPropertyByKey(ContextType.CAR_LEVEL));
        mav.addObject(ContextType.CAR_CHANNEL, cacheCenter.getCarPropertyByKey(ContextType.CAR_CHANNEL));
        mav.addObject(ContextType.CAR_TAKE_TYPE, cacheCenter.getCarPropertyByKey(ContextType.CAR_TAKE_TYPE));
        mav.addObject(ContextType.CAR_STATUS, cacheCenter.getCarPropertyByKey(ContextType.CAR_STATUS));
        mav.addObject(ContextType.CAR_PURCHASE_TYPE, cacheCenter.getCarPropertyByKey(ContextType.CAR_PURCHASE_TYPE));
        mav.addObject(ContextType.CAR_CONSUMER_PROPERTY, cacheCenter.getCarPropertyByKey(ContextType.CAR_CONSUMER_PROPERTY));
        mav.addObject(ContextType.CAR_CONSUMER_RESOURCE, cacheCenter.getCarPropertyByKey(ContextType.CAR_CONSUMER_RESOURCE));
        mav.addObject(ContextType.CAR_PURCHASE_USE, cacheCenter.getCarPropertyByKey(ContextType.CAR_PURCHASE_USE));
        mav.addObject(ContextType.CONSUMER_GENERATION, cacheCenter.getCarPropertyByKey(ContextType.CONSUMER_GENERATION));
    }

    private void initCarBathData(ModelAndView mav) {
        mav.addObject(ContextType.CAR_BATH_IDS, daoCenter.getCarBathListByKey(null));
    }

    @RequestMapping(value = "/carPurchaseAction", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView carPurchaseAdd(
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
        initCarPropertyData(mav);
        initCarBathData(mav);

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
                CarRecord carRecord = daoCenter.getCarRecordById(id);
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
                carRecord.setId(daoCenter.getCarRecordAutoId());
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
                daoCenter.createCarRecord(carRecord);
                session.setAttribute("tip", "ok 采购录入成功！");

                //新建一个档案数据记录
                CarDossier carDossier = new CarDossier();
                carDossier.setCarRecordId(carRecord.getId());
                carDossier.setDisplayStatus(ContextType.DISPLAY_STATUS_SHOW);
                daoCenter.createCarDossier(carDossier);

                //批量采购操作
                if (isBath == 1) {
                    modCarRecordId(bathId, carRecord.getId(), 1);
                }
            } else if (action.equals(MOD_ACTION)) {
                //批量采购操作
                CarRecord oldCarRecord = daoCenter.getCarRecordById(id);
                if (oldCarRecord.getIsBath() == 0 && isBath == 0) {
                } else if (oldCarRecord.getIsBath() == 0 && isBath == 1) {
                    modCarRecordId(bathId, oldCarRecord.getId(), 1);
                } else if (oldCarRecord.getIsBath() == 1 && isBath == 0) {
                    modCarRecordId(oldCarRecord.getBathId(), oldCarRecord.getId(), 0);
                } else if (oldCarRecord.getIsBath() == 1 && isBath == 1) {
                    if (oldCarRecord.getBathId().intValue() != bathId) {
                        modCarRecordId(oldCarRecord.getBathId(), oldCarRecord.getId(), 0);
                        modCarRecordId(bathId, oldCarRecord.getId(), 1);
                    }
                }
                carRecord.setId(id);
                daoCenter.updateCarRecord(carRecord);
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

    private void modCarRecordId(Integer bathId, Integer carRecordId, int action) {
        CarBath carBath = daoCenter.getCarBathById(bathId);
        String carRecordIds = carBath.getCarRecordId();
        if (action == 1) {
            if (StringUtils.isEmpty(carRecordIds)) {
                carRecordIds = String.valueOf(carRecordId);
            } else {
                carRecordIds = carRecordIds + "," + carRecordId;
            }
        } else {
            carRecordIds = Utils.removeKey(carRecordIds, String.valueOf(carRecordId));
        }
        carBath.setCarRecordId(carRecordIds);
        daoCenter.updateCarBath(carBath);
    }

    @RequestMapping(value = "/carPurchasePaid", method = RequestMethod.GET)
    public ModelAndView carPurchasePaid(
            @RequestParam(value = "goonPaid", required = false, defaultValue = "") Double goonPaid,
            @RequestParam(value = "paidReason", required = false, defaultValue = "") String paidReason,
            @RequestParam(value = "id", required = false, defaultValue = "") Integer id,
            HttpSession session) {
        if (goonPaid == null) goonPaid = 0.0;
        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:/carPurchaseView");
        CarRecord carRecord = daoCenter.getCarRecordById(id);
        Double paidMoney = carRecord.getPaidMoney() + goonPaid;

        CarRecord update = new CarRecord();
        update.setId(id);
        update.setPaidMoney(paidMoney);

        if (paidMoney > carRecord.getPurchaseMoney()) {
            session.setAttribute("tip", "付款超限！");
            return mav;
        } else if (paidMoney.equals(carRecord.getPurchaseMoney())) {
            update.setRecordStatus(ContextType.RECORD_STATUS_STOCK);
        }
        daoCenter.updateCarRecord(update);
        session.setAttribute("tip", "ok 付款成功！");

        //创建付款记录
        CarPaidRecord carPaidRecord = new CarPaidRecord();
        carPaidRecord.setCarRecordId(id);
        carPaidRecord.setRecordStatus(ContextType.PAY_RECORD_PURCHASE);
        carPaidRecord.setPaidMoney(goonPaid);
        carPaidRecord.setPaidReason(paidReason);
        daoCenter.createCarPaidRecord(carPaidRecord);

        return mav;
    }

    @RequestMapping(value = "/carPurchaseDelete", method = RequestMethod.GET)
    public ModelAndView carPurchaseDelete(
            @RequestParam(value = "id", required = false, defaultValue = "") Integer id,
            @RequestParam(value = "recordStatus", required = false, defaultValue = "") Integer recordStatus,
            HttpSession session) {
        ModelAndView mav = new ModelAndView();
        if (recordStatus.equals(ContextType.RECORD_STATUS_PURCHASE)) {
            mav.setViewName("redirect:/carPurchaseView");
        } else if (recordStatus.equals(ContextType.RECORD_STATUS_STOCK)) {
            mav.setViewName("redirect:/carStockView");
        } else if (recordStatus.equals(ContextType.RECORD_STATUS_SALE)) {
            mav.setViewName("redirect:/carSaleView");
        } else if (recordStatus.equals(ContextType.RECORD_STATUS_SOLD)) {
            mav.setViewName("redirect:/carSoldView");
        }

        CarRecord carRecord = daoCenter.getCarRecordById(id);
        //批量采购操作
        if (carRecord.getIsBath() == 1) {
            modCarRecordId(carRecord.getBathId(), carRecord.getId(), 0);
        }

        //删除成本信息
        if (carRecord.getIsCost() == 1) {
            CarCost carCost = daoCenter.getCarCostById(carRecord.getCostId());
            Map<String, Integer> map = new HashMap<String, Integer>();
            map.put("linkId", carCost.getId());
            if (carCost.getPreSaleFee() != null && carCost.getPreSaleFee() != 0) {
                map.put("type", ContextType.PRE_SETUP_TYPE);
                daoCenter.deleteCarSaleSetupByMap(map);
            }
            if (carCost.getAfterSaleFee() != null && carCost.getAfterSaleFee() != 0) {
                map.put("type", ContextType.AFTER_SETUP_TYPE);
                daoCenter.deleteCarSaleSetupByMap(map);
            }
            if (carCost.getOtherIncomeFee() != null && carCost.getOtherIncomeFee() != 0) {
                map.put("type", ContextType.OTHER_INCOME);
                daoCenter.deleteCarSaleSetupByMap(map);
            }
            daoCenter.deleteCarCost(carRecord.getCostId());
        }

        //删除销售信息
        if (carRecord.getIsSale() == 1) {
            CarSaleInfo carSaleInfo = daoCenter.getCarSaleInfoById(carRecord.getSaleId());
            if (carSaleInfo.getSaleType().equals(ContextType.SALE_TYPE_AJ)) {
                daoCenter.deleteMortgageRecord(carSaleInfo.getMortgageId());
            }
            daoCenter.deleteCarSaleInfo(carSaleInfo.getId());
            Map<String, Integer> map = new HashMap<String, Integer>();
            map.put("linkId", carSaleInfo.getId());
            daoCenter.deleteCarPayMoneyAssistList(map);
        }

        //删除销售成本信息
        if (carRecord.getIsSf() == 1) {
            CarSf carSf = daoCenter.getCarSfById(carRecord.getSfId());
            Map<String, Integer> map = new HashMap<String, Integer>();
            map.put("linkId", carSf.getId());
            if (carSf.getSaleFee() != null && carSf.getSaleFee() != 0) {
                map.put("type", ContextType.SALE_TYPE);
                daoCenter.deleteCarSaleSetupByMap(map);
            }
            daoCenter.deleteCarSf(carRecord.getSfId());
        }

        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("linkId", carRecord.getId());
        //删除工资辅助记录表
        daoCenter.deleteWagesAssistByMap(map);
        //删除付款记录表
        map.put("recordStatus", ContextType.PAY_RECORD_PURCHASE);
        daoCenter.deleteCarPaidRecordList(map);

        map.put("recordStatus", ContextType.PAY_RECORD_SALE);
        daoCenter.deleteCarPaidRecordList(map);

        map.put("recordStatus", ContextType.PAY_RECORD_MORTGAGE);
        daoCenter.deleteCarPaidRecordList(map);

        map.put("recordStatus", ContextType.PAY_RECORD_BACK);
        daoCenter.deleteCarPaidRecordList(map);

        map.put("recordStatus", ContextType.PAY_RECORD_DEPOSIT);
        daoCenter.deleteCarPaidRecordList(map);

        //删除档案信息
        daoCenter.deleteCarDossierByMap(map);

        daoCenter.deleteCarRecord(id);
        session.setAttribute("tip", "ok 采购记录删除成功！");
        return mav;
    }

    @RequestMapping(value = "/carStockView", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView carStockView(HttpSession session) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("car/stockList");
        List<CarRecord> list = dealList(daoCenter.getCarRecordListByRecordStatus(ContextType.RECORD_STATUS_STOCK));
        installContextBean(list, mav);
        mav.addObject("carNum", list.size());
        mav.addObject(TIP, session.getAttribute("tip"));
        return mav;
    }

    //车辆销售成本录入相关操作
    @RequestMapping(value = "/carSfAction", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView carSfAction(
            @RequestParam(value = "action", required = true, defaultValue = "") Integer action,
            @RequestParam(value = "over", required = false, defaultValue = "") Integer over,
            @RequestParam(value = "id", required = false, defaultValue = "") Integer id,
            @RequestParam(value = "recordId", required = false, defaultValue = "") Integer recordId,
            @RequestParam(value = "transferFee", required = false, defaultValue = "") Double transferFee,
            @RequestParam(value = "transferSubsidy", required = false, defaultValue = "") Double transferSubsidy,
            @RequestParam(value = "transferCrossingFee", required = false, defaultValue = "") Double transferCrossingFee,
            @RequestParam(value = "transferBillingFee", required = false, defaultValue = "") Double transferBillingFee,
            @RequestParam(value = "transferOilFee", required = false, defaultValue = "") Double transferOilFee,
            @RequestParam(value = "rubbing", required = false, defaultValue = "") Double rubbing,
            @RequestParam(value = "removeCard", required = false, defaultValue = "") Double removeCard,
            @RequestParam(value = "cattleFee", required = false, defaultValue = "") Double cattleFee,
            @RequestParam(value = "isProduce", required = false, defaultValue = "") Integer isProduce,
            HttpSession session) {

        ModelAndView mav = new ModelAndView();
        mav.setViewName("car/sfAdd");
        mav.addObject("action", action);
        mav.addObject("recordId", recordId);

        mav.addObject("transferFee", transferFee == null ? 0.0 : transferFee);
        mav.addObject("transferSubsidy", transferSubsidy == null ? 0.0 : transferSubsidy);
        mav.addObject("transferCrossingFee", transferCrossingFee == null ? 0.0 : transferCrossingFee);
        mav.addObject("transferBillingFee", transferBillingFee == null ? 0.0 : transferBillingFee);
        mav.addObject("transferOilFee", transferOilFee == null ? 0.0 : transferOilFee);
        mav.addObject("rubbing", rubbing == null ? 0.0 : rubbing);
        mav.addObject("removeCard", removeCard == null ? 0.0 : removeCard);
        mav.addObject("cattleFee", cattleFee == null ? 0.0 : cattleFee);
        mav.addObject("isProduce", isProduce);

        if (over == null) {
            if (id != null) {
                CarSf carSf = daoCenter.getCarSfById(id);
                mav.addObject("id", id);

                mav.addObject("transferFee", carSf.getTransferFee());
                mav.addObject("transferSubsidy", carSf.getTransferSubsidy());
                mav.addObject("transferCrossingFee", carSf.getTransferCrossingFee());
                mav.addObject("transferBillingFee", carSf.getTransferBillingFee());
                mav.addObject("transferOilFee", carSf.getTransferOilFee());
                mav.addObject("rubbing", carSf.getRubbing());
                mav.addObject("removeCard", carSf.getRemoveCard());
                mav.addObject("cattleFee", carSf.getCattleFee());
                mav.addObject("isProduce", carSf.getIsProduce());
            }
            return mav;
        } else {
            CarSf carSf = new CarSf();
            carSf.setTransferFee(transferFee);
            carSf.setTransferSubsidy(transferSubsidy);
            carSf.setTransferCrossingFee(transferCrossingFee);
            carSf.setTransferBillingFee(transferBillingFee);
            carSf.setTransferOilFee(transferOilFee);
            carSf.setRubbing(rubbing);
            carSf.setRemoveCard(removeCard);
            carSf.setCattleFee(cattleFee);
            carSf.setIsProduce(isProduce == null ? 0 : isProduce);

            mav.clear();
            if (action.equals(CREATE_ACTION)) {
                carSf.setCarRecordId(recordId);
                carSf.setId(daoCenter.getCarSfAutoId());
                carSf.setSaleFee(0.0);
                carSf.setServiceFee(0.0);
                daoCenter.createCarSf(carSf);
                session.setAttribute("tip", "ok 车辆销售成本录入成功！");

                //绑定成本录入ID
                CarRecord carRecord = new CarRecord();
                carRecord.setId(recordId);
                carRecord.setIsSf(1);
                carRecord.setSfId(carSf.getId());
                daoCenter.updateCarRecord(carRecord);
            } else if (action.equals(MOD_ACTION)) {
                carSf.setId(id);
                daoCenter.updateCarSf(carSf);
                session.setAttribute("tip", "ok 车辆销售成本修改成功！");
            }
            mav.setViewName("redirect:/carSaleView");
            return mav;
        }
    }

    @RequestMapping(value = "/carCostAction", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView carCostAction(
            @RequestParam(value = "action", required = true, defaultValue = "") Integer action,
            @RequestParam(value = "over", required = false, defaultValue = "") Integer over,
            @RequestParam(value = "id", required = false, defaultValue = "") Integer id,
            @RequestParam(value = "recordStatus", required = false, defaultValue = "") Integer recordStatus,
            @RequestParam(value = "recordId", required = false, defaultValue = "") Integer recordId,
            @RequestParam(value = "carPickPerson", required = false, defaultValue = "") String carPickPerson,
            @RequestParam(value = "mentionFee", required = false, defaultValue = "") Double mentionFee,
            @RequestParam(value = "mentionSubsidy", required = false, defaultValue = "") Double mentionSubsidy,
            @RequestParam(value = "travelFee", required = false, defaultValue = "") Double travelFee,
            @RequestParam(value = "putFee", required = false, defaultValue = "") Double putFee,
            @RequestParam(value = "putSubsidy", required = false, defaultValue = "") Double putSubsidy,
            @RequestParam(value = "crossingFee", required = false, defaultValue = "") Double crossingFee,
            @RequestParam(value = "mailFee", required = false, defaultValue = "") Double mailFee,
            @RequestParam(value = "freightFee", required = false, defaultValue = "") Double freightFee,
            @RequestParam(value = "billingFee", required = false, defaultValue = "") Double billingFee,
            @RequestParam(value = "oilFee", required = false, defaultValue = "") Double oilFee,
            @RequestParam(value = "cattleFee", required = false, defaultValue = "") Double cattleFee,
            @RequestParam(value = "expenseFee", required = false, defaultValue = "") Double expenseFee,
            @RequestParam(value = "otherFee", required = false, defaultValue = "") Double otherFee,
            HttpSession session) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("car/costAdd");
        mav.addObject("action", action);
        mav.addObject("recordStatus", recordStatus == null ? ContextType.RECORD_STATUS_STOCK : recordStatus);
        mav.addObject("recordId", recordId);

        mav.addObject("carPickPerson", carPickPerson);
        mav.addObject("mentionFee", mentionFee);
        mav.addObject("mentionSubsidy", mentionSubsidy);
        mav.addObject("travelFee", travelFee);
        mav.addObject("putFee", putFee);
        mav.addObject("putSubsidy", putSubsidy);
        mav.addObject("crossingFee", crossingFee);
        mav.addObject("mailFee", mailFee);
        mav.addObject("freightFee", freightFee);
        mav.addObject("billingFee", billingFee);
        mav.addObject("oilFee", oilFee);
        mav.addObject("cattleFee", cattleFee);
        mav.addObject("expenseFee", expenseFee);
        mav.addObject("otherFee", otherFee);

        if (over == null) {
            if (id != null) {
                CarCost carCost = daoCenter.getCarCostById(id);
                mav.addObject("id", id);
                mav.addObject("recordId", carCost.getCarRecordId());
                mav.addObject("carPickPerson", carCost.getCarPickPerson());
                mav.addObject("mentionFee", carCost.getMentionFee());
                mav.addObject("mentionSubsidy", carCost.getMentionSubsidy());
                mav.addObject("travelFee", carCost.getTravelFee());
                mav.addObject("putFee", carCost.getPutFee());
                mav.addObject("putSubsidy", carCost.getPutSubsidy());
                mav.addObject("crossingFee", carCost.getCrossingFee());
                mav.addObject("mailFee", carCost.getMailFee());
                mav.addObject("freightFee", carCost.getFreightFee());
                mav.addObject("billingFee", carCost.getBillingFee());
                mav.addObject("oilFee", carCost.getOilFee());
                mav.addObject("cattleFee", carCost.getCattleFee());
                mav.addObject("expenseFee", carCost.getExpenseFee());
                mav.addObject("otherFee", carCost.getOtherFee());
            }
            return mav;
        } else {
            if (StringUtils.isEmpty(carPickPerson)) {
                mav.addObject(TIP, "提车经办人不能为空！");
                return mav;
            }

            CarCost carCost = new CarCost();
            carCost.setCarPickPerson(carPickPerson);
            carCost.setMentionFee(mentionFee == null ? 0 : mentionFee);
            carCost.setMentionSubsidy(mentionSubsidy == null ? 0 : mentionSubsidy);
            carCost.setTravelFee(travelFee == null ? 0 : travelFee);
            carCost.setPutFee(putFee == null ? 0 : putFee);
            carCost.setPutSubsidy(putSubsidy == null ? 0 : putSubsidy);
            carCost.setCrossingFee(crossingFee == null ? 0 : crossingFee);
            carCost.setMailFee(mailFee == null ? 0 : mailFee);
            carCost.setFreightFee(freightFee == null ? 0 : freightFee);
            carCost.setBillingFee(billingFee == null ? 0 : billingFee);
            carCost.setOilFee(oilFee == null ? 0 : oilFee);
            carCost.setCattleFee(cattleFee == null ? 0 : cattleFee);
            carCost.setExpenseFee(expenseFee == null ? 0 : expenseFee);
            carCost.setOtherFee(otherFee == null ? 0 : otherFee);

            mav.clear();
            if (action.equals(CREATE_ACTION)) {
                carCost.setCarRecordId(recordId);
                carCost.setId(daoCenter.getCarCostAutoId());
                carCost.setPreSaleFee(0.0);
                carCost.setAfterSaleFee(0.0);
                carCost.setOtherIncomeFee(0.0);
                daoCenter.createCarCost(carCost);
                session.setAttribute("tip", "ok 车辆成本录入成功！");

                //绑定成本录入ID
                CarRecord carRecord = new CarRecord();
                carRecord.setId(recordId);
                carRecord.setIsCost(1);
                carRecord.setCostId(carCost.getId());
                daoCenter.updateCarRecord(carRecord);
            } else if (action.equals(MOD_ACTION)) {
                carCost.setId(id);
                daoCenter.updateCarCost(carCost);
                session.setAttribute("tip", "ok 车辆成本修改成功！");
            }
            mav.setViewName("redirect:/carStockView");
            return mav;
        }
    }

    //车辆售前售后整备相关操作
    @RequestMapping(value = "/carSaleSetupAction", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView carSaleSetupAction(HttpServletRequest request, HttpServletResponse response,
                                           @RequestParam(value = "action", required = true, defaultValue = "") Integer action,
                                           @RequestParam(value = "over", required = false, defaultValue = "") Integer over,
                                           @RequestParam(value = "carCostId", required = false, defaultValue = "") Integer carCostId,
                                           @RequestParam(value = "setupType", required = false, defaultValue = "") Integer setupType,
                                           @RequestParam(value = "recordStatus", required = false, defaultValue = "") Integer recordStatus,
                                           HttpSession session) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("car/saleSetupAdd");
        mav.addObject("action", action);
        mav.addObject("carCostId", carCostId);
        mav.addObject("setupType", setupType);
        mav.addObject("recordStatus", recordStatus);

        if (setupType.equals(ContextType.MORTGAGE_AGENT_PAY)) {
            MortgageLog mortgageLog = daoCenter.getMortgageLogById(carCostId);
            mav.addObject("mortgageType", mortgageLog.getMortgageType());
        }

        if (over == null) return mav;

        // 得到所有的请求参数名称
        Enumeration<String> parameterNames = request.getParameterNames();
        // 用来装所有的参数名称的后缀(即下划线后面的)
        List<String> parameterNamesSuffix = new ArrayList<String>();
        List<String> newParameterNamesSuffix = new ArrayList<String>();
        // 用来装所有的参数名称和参数值
        Map<String, String> parameterNamesAndValues = new HashMap<String, String>();
        while (parameterNames.hasMoreElements()) {
            // 得到请求参数的名称
            String parameterName = parameterNames.nextElement();
            if (ContextType.SETUP_SKIP_PARAM.contains(parameterName)) continue;
            // 根据下划线拆分
            String[] myParameterName = parameterName.split("_");
            // 得到下划线后面的部分
            parameterNamesSuffix.add(myParameterName[myParameterName.length - 1]);
            // 保存参数名称和参数值
            parameterNamesAndValues.put(parameterName, request.getParameter(parameterName));
        }

        // 去重
        Set<String> set = new HashSet<String>();
        for (String parameterNameSuffix : parameterNamesSuffix) {
            if (set.add(parameterNameSuffix)) {
                newParameterNamesSuffix.add(parameterNameSuffix);
            }
        }

        Set<Map.Entry<String, String>> entrySetParameterNamesAndValues = parameterNamesAndValues.entrySet();

        Iterator<Map.Entry<String, String>> it = entrySetParameterNamesAndValues.iterator();
        String[] fieldNames = new String[parameterNamesAndValues.size()];
        String[] fieldValues = new String[parameterNamesAndValues.size()];
        int elementIndex = 0;
        while (it.hasNext()) {
            Map.Entry<String, String> entry = it.next();
            fieldNames[elementIndex] = entry.getKey();
            fieldValues[elementIndex] = entry.getValue();
            elementIndex++;
        }

        Double d = 0.0;
        Integer successNum = 0;
        for (int i = 0; i < newParameterNamesSuffix.size(); i++) {
            CarSaleSetup carSaleSetup = new CarSaleSetup();
            for (int index = 0; index < fieldNames.length; index++) {
                try {
                    String[] field = fieldNames[index].split("_");
                    if (newParameterNamesSuffix.get(i).equals(field[field.length - 1])) {
                        if ("setupName".equals(field[0])) {
                            carSaleSetup.setSetupName(fieldValues[index]);
                        } else if ("setupFee".equals(field[0])) {
                            carSaleSetup.setSetupFee(Double.valueOf(fieldValues[index]));
                        }
                    }
                } catch (Exception e) {
                    System.out.println("e" + e.getMessage());
                }
            }
            d += carSaleSetup.getSetupFee();
            carSaleSetup.setCarCostId(carCostId);
            carSaleSetup.setSetupType(setupType);
            daoCenter.createCarSaleSetup(carSaleSetup);
            ++successNum;
        }
        //将改变金额同步到成本录入表中
        if (d != 0) {
            if (setupType.equals(ContextType.PRE_SETUP_TYPE)
                    || setupType.equals(ContextType.AFTER_SETUP_TYPE)
                    || setupType.equals(ContextType.OTHER_INCOME)) {
                CarCost carCost = daoCenter.getCarCostById(carCostId);
                CarCost update = new CarCost();
                update.setId(carCostId);
                if (setupType.equals(ContextType.PRE_SETUP_TYPE)) {
                    update.setPreSaleFee(carCost.getPreSaleFee() + d);
                    if (recordStatus != null && recordStatus.equals(ContextType.RECORD_STATUS_SALE)) {
                        mav.setViewName("redirect:/carSaleView");
                    } else {
                        mav.setViewName("redirect:/carStockView");
                    }
                } else if (setupType.equals(ContextType.AFTER_SETUP_TYPE)) {
                    update.setAfterSaleFee(carCost.getAfterSaleFee() + d);
                    mav.setViewName("redirect:/carSoldView");
                } else if (setupType.equals(ContextType.OTHER_INCOME)) {
                    update.setOtherIncomeFee(carCost.getOtherIncomeFee() + d);
                    mav.setViewName("redirect:/carStockView");
                }
                daoCenter.updateCarCost(update);
            } else if (setupType.equals(ContextType.SALE_TYPE)) {
                CarSf carSf = daoCenter.getCarSfById(carCostId);
                CarSf sfUpdate = new CarSf();
                sfUpdate.setId(carCostId);
                sfUpdate.setSaleFee(carSf.getSaleFee() + d);
                daoCenter.updateCarSf(sfUpdate);
                mav.setViewName("redirect:/carSaleView");
            } else if (setupType.equals(ContextType.NEW_CAR_COST_TYPE)
                    || setupType.equals(ContextType.NEW_CAR_INCOME_TYPE)) {
                NewCar newCar = daoCenter.getNewCar(carCostId);
                NewCar ncUpdate = new NewCar();
                ncUpdate.setId(carCostId);
                if (setupType.equals(ContextType.NEW_CAR_COST_TYPE)) {
                    ncUpdate.setOtherCost(newCar.getOtherCost() + d);
                } else if (setupType.equals(ContextType.NEW_CAR_INCOME_TYPE)) {
                    ncUpdate.setOtherIncome(newCar.getOtherIncome() + d);
                }
                daoCenter.updateNewCar(ncUpdate);
                mav.setViewName("redirect:/newCarView");
            } else if (setupType.equals(ContextType.NEW_CAR_FIANCE_COST_TYPE)) {
                NewCarFinance newCarFinance = daoCenter.getNewCarFinance(carCostId);
                NewCarFinance ncUpdate = new NewCarFinance();
                ncUpdate.setId(carCostId);
                ncUpdate.setOtherCost(newCarFinance.getOtherCost() + d);
                daoCenter.updateNewCarFinance(ncUpdate);
                mav.setViewName("redirect:/newCarFinanceView");
            } else if (setupType.equals(ContextType.MORTGAGE_AGENT_PAY)) {
                MortgageLog mortgageLog = daoCenter.getMortgageLogById(carCostId);
                mortgageLog.setAgentPayFee(mortgageLog.getAgentPayFee() + d);
                CommonAction.calculateMortgageData(mortgageLog);
                daoCenter.updateMortgageLog(mortgageLog);
                mav.setViewName("redirect:/mortgageView?mortgageType=" + mortgageLog.getMortgageType());
            }
        }
        session.setAttribute("tip", "ok 成功插入" + successNum + "条数据！");
        return mav;
    }

//    //车辆售前售后整备相关操作
//    @RequestMapping(value = "/carSaleSetupAction", method = {RequestMethod.GET, RequestMethod.POST})
//    public ModelAndView carSaleSetupAction(
//            @RequestParam(value = "action", required = true, defaultValue = "") Integer action,
//            @RequestParam(value = "over", required = false, defaultValue = "") Integer over,
//            @RequestParam(value = "id", required = false, defaultValue = "") Integer id,
//            @RequestParam(value = "carCostId", required = false, defaultValue = "") Integer carCostId,
//            @RequestParam(value = "setupType", required = false, defaultValue = "") Integer setupType,
//            @RequestParam(value = "setupName", required = false, defaultValue = "") String setupName,
//            @RequestParam(value = "setupFee", required = false, defaultValue = "") Double setupFee,
//            HttpSession session) {
//        ModelAndView mav = new ModelAndView();
//        mav.setViewName("car/saleSetupAdd");
//        mav.addObject("action", action);
//        mav.addObject("carCostId", carCostId);
//        mav.addObject("setupType", setupType);
//        mav.addObject("setupName", setupName);
//        mav.addObject("setupFee", setupFee);
//        if (setupType.equals(ContextType.MORTGAGE_AGENT_PAY)) {
//            MortgageLog mortgageLog = daoCenter.getMortgageLogById(carCostId);
//            mav.addObject("mortgageType", mortgageLog.getMortgageType());
//        }
//
//        if (over == null) {
//            if (id != null) {
//                CarSaleSetup carSaleSetup = daoCenter.getCarSaleSetupById(id);
//                mav.addObject("id", id);
//                mav.addObject("carCostId", carSaleSetup.getCarCostId());
//                mav.addObject("setupType", carSaleSetup.getSetupType());
//                mav.addObject("setupName", carSaleSetup.getSetupName());
//                mav.addObject("setupFee", carSaleSetup.getSetupFee());
//            }
//            return mav;
//        } else {
//            if (StringUtils.isEmpty(setupName)) {
//                mav.addObject(TIP, "项目必填！");
//                return mav;
//            }
//            if (setupFee == 0) {
//                mav.addObject(TIP, "金额不能为空！");
//                return mav;
//            }
//
//            CarSaleSetup carSaleSetup = new CarSaleSetup();
//            carSaleSetup.setSetupName(setupName);
//            carSaleSetup.setSetupFee(setupFee);
//
//            mav.clear();
//            Double d = 0.0;
//            if (action.equals(CREATE_ACTION)) {
//                //新建整备信息差值为整备费用
//                d = setupFee;
//
//                carSaleSetup.setCarCostId(carCostId);
//                carSaleSetup.setSetupType(setupType);
//                daoCenter.createCarSaleSetup(carSaleSetup);
//                session.setAttribute("tip", "ok 新建成功！");
//            } else if (action.equals(MOD_ACTION)) {
//                CarSaleSetup old = daoCenter.getCarSaleSetupById(id);
//                d = setupFee - old.getSetupFee();
//
//                carSaleSetup.setId(id);
//                daoCenter.updateCarSaleSetup(carSaleSetup);
//                session.setAttribute("tip", "ok 修改成功！");
//            }
//
//            //将改变金额同步到成本录入表中
//            if (d != 0) {
//                if (setupType.equals(ContextType.PRE_SETUP_TYPE)
//                        || setupType.equals(ContextType.AFTER_SETUP_TYPE)
//                        || setupType.equals(ContextType.OTHER_INCOME)) {
//                    CarCost carCost = daoCenter.getCarCostById(carCostId);
//                    CarCost update = new CarCost();
//                    update.setId(carCostId);
//                    if (setupType.equals(ContextType.PRE_SETUP_TYPE)) {
//                        update.setPreSaleFee(carCost.getPreSaleFee() + d);
//                        mav.setViewName("redirect:/carStockView");
//                    } else if (setupType.equals(ContextType.AFTER_SETUP_TYPE)) {
//                        update.setAfterSaleFee(carCost.getAfterSaleFee() + d);
//                        mav.setViewName("redirect:/carSoldView");
//                    } else if (setupType.equals(ContextType.OTHER_INCOME)) {
//                        update.setOtherIncomeFee(carCost.getOtherIncomeFee() + d);
//                        mav.setViewName("redirect:/carStockView");
//                    }
//                    daoCenter.updateCarCost(update);
//                } else if (setupType.equals(ContextType.SALE_TYPE)) {
//                    CarSf carSf = daoCenter.getCarSfById(carCostId);
//                    CarSf sfUpdate = new CarSf();
//                    sfUpdate.setId(carCostId);
//                    sfUpdate.setSaleFee(carSf.getSaleFee() + d);
//                    daoCenter.updateCarSf(sfUpdate);
//                    mav.setViewName("redirect:/carSaleView");
//                } else if (setupType.equals(ContextType.NEW_CAR_COST_TYPE)
//                        || setupType.equals(ContextType.NEW_CAR_INCOME_TYPE)) {
//                    NewCar newCar = daoCenter.getNewCar(carCostId);
//                    NewCar ncUpdate = new NewCar();
//                    ncUpdate.setId(carCostId);
//                    if (setupType.equals(ContextType.NEW_CAR_COST_TYPE)) {
//                        ncUpdate.setOtherCost(newCar.getOtherCost() + d);
//                    } else if (setupType.equals(ContextType.NEW_CAR_INCOME_TYPE)) {
//                        ncUpdate.setOtherIncome(newCar.getOtherIncome() + d);
//                    }
//                    daoCenter.updateNewCar(ncUpdate);
//                    mav.setViewName("redirect:/newCarView");
//                } else if (setupType.equals(ContextType.NEW_CAR_FIANCE_COST_TYPE)) {
//                    NewCarFinance newCarFinance = daoCenter.getNewCarFinance(carCostId);
//                    NewCarFinance ncUpdate = new NewCarFinance();
//                    ncUpdate.setId(carCostId);
//                    ncUpdate.setOtherCost(newCarFinance.getOtherCost() + d);
//                    daoCenter.updateNewCarFinance(ncUpdate);
//                    mav.setViewName("redirect:/newCarFinanceView");
//                } else if (setupType.equals(ContextType.MORTGAGE_AGENT_PAY)) {
//                    MortgageLog mortgageLog = daoCenter.getMortgageLogById(carCostId);
//                    mortgageLog.setAgentPayFee(mortgageLog.getAgentPayFee() + d);
//                    CommonAction.calculateMortgageData(mortgageLog);
//                    daoCenter.updateMortgageLog(mortgageLog);
//                    mav.setViewName("redirect:/mortgageView?mortgageType=" + mortgageLog.getMortgageType());
//                }
//            }
//            return mav;
//        }
//    }


    @RequestMapping(value = "/carSaleSetupDelete", method = RequestMethod.GET)
    public ModelAndView carSaleSetupDelete(
            @RequestParam(value = "id", required = false, defaultValue = "") Integer id,
            @RequestParam(value = "carCostId", required = false, defaultValue = "") Integer carCostId,
            @RequestParam(value = "setupType", required = false, defaultValue = "") Integer setupType,
            @RequestParam(value = "recordStatus", required = false, defaultValue = "") Integer recordStatus,
            HttpSession session) {
        ModelAndView mav = new ModelAndView();

        CarSaleSetup carSaleSetup = daoCenter.getCarSaleSetupById(id);
        daoCenter.deleteCarSaleSetup(id);
        if (setupType.equals(ContextType.PRE_SETUP_TYPE)
                || setupType.equals(ContextType.AFTER_SETUP_TYPE)
                || setupType.equals(ContextType.OTHER_INCOME)) {
            CarCost carCost = daoCenter.getCarCostById(carCostId);
            CarCost update = new CarCost();
            update.setId(carCostId);
            if (setupType.equals(ContextType.PRE_SETUP_TYPE)) {
                if (recordStatus != null && recordStatus.equals(ContextType.RECORD_STATUS_SALE)) {
                    mav.setViewName("redirect:/carSaleView");
                } else {
                    mav.setViewName("redirect:/carStockView");
                }
                update.setPreSaleFee(carCost.getPreSaleFee() - carSaleSetup.getSetupFee());
            } else if (setupType.equals(ContextType.AFTER_SETUP_TYPE)) {
                mav.setViewName("redirect:/carSoldView");
                update.setAfterSaleFee(carCost.getAfterSaleFee() - carSaleSetup.getSetupFee());
            } else if (setupType.equals(ContextType.OTHER_INCOME)) {
                mav.setViewName("redirect:/carStockView");
                update.setOtherIncomeFee(carCost.getOtherIncomeFee() - carSaleSetup.getSetupFee());
            }
            daoCenter.updateCarCost(update);
        } else if (setupType.equals(ContextType.SALE_TYPE)) {
            CarSf carSf = daoCenter.getCarSfById(carCostId);
            CarSf sfUpdate = new CarSf();
            sfUpdate.setId(carCostId);
            sfUpdate.setSaleFee(carSf.getSaleFee() - carSaleSetup.getSetupFee());
            daoCenter.updateCarSf(sfUpdate);
            mav.setViewName("redirect:/carSaleView");
        } else if (setupType.equals(ContextType.NEW_CAR_COST_TYPE)
                || setupType.equals(ContextType.NEW_CAR_INCOME_TYPE)) {
            NewCar newCar = daoCenter.getNewCar(carCostId);
            NewCar ncUpdate = new NewCar();
            ncUpdate.setId(carCostId);
            if (setupType.equals(ContextType.NEW_CAR_COST_TYPE)) {
                ncUpdate.setOtherCost(newCar.getOtherCost() - carSaleSetup.getSetupFee());
            } else if (setupType.equals(ContextType.NEW_CAR_INCOME_TYPE)) {
                ncUpdate.setOtherIncome(newCar.getOtherIncome() - carSaleSetup.getSetupFee());
            }
            daoCenter.updateNewCar(ncUpdate);
            mav.setViewName("redirect:/newCarView");
        } else if (setupType.equals(ContextType.NEW_CAR_FIANCE_COST_TYPE)) {
            NewCarFinance newCarFinance = daoCenter.getNewCarFinance(carCostId);
            NewCarFinance ncUpdate = new NewCarFinance();
            ncUpdate.setId(carCostId);
            ncUpdate.setOtherCost(newCarFinance.getOtherCost() - carSaleSetup.getSetupFee());
            daoCenter.updateNewCarFinance(ncUpdate);
            mav.setViewName("redirect:/newCarFinanceView");
        } else if (setupType.equals(ContextType.MORTGAGE_AGENT_PAY)) {
            MortgageLog mortgageLog = daoCenter.getMortgageLogById(carCostId);
            mortgageLog.setAgentPayFee(mortgageLog.getAgentPayFee() - carSaleSetup.getSetupFee());
            CommonAction.calculateMortgageData(mortgageLog);
            daoCenter.updateMortgageLog(mortgageLog);
            mav.setViewName("redirect:/mortgageView?mortgageType=" + mortgageLog.getMortgageType());
        }
        session.setAttribute("tip", "ok 删除成功！");
        return mav;
    }

    private void dealCarSaleInfo(CarSaleInfo carSaleInfo) {
        carSaleInfo.setStrSaleDate(TimeUtils.transformTimetagToDate(carSaleInfo.getSaleDate(), TimeUtils.FORMAT_ONE));
        carSaleInfo.setStrConsumerProperty(cacheCenter.getCarPropertyById(carSaleInfo.getConsumerProperty()).getPropertyValue());
        carSaleInfo.setStrConsumerResource(cacheCenter.getCarPropertyById(carSaleInfo.getConsumerResource()).getPropertyValue());
        carSaleInfo.setStrPurchaseUse(cacheCenter.getCarPropertyById(carSaleInfo.getPurchaseUse()).getPropertyValue());
        if (carSaleInfo.getConsumerAge() != 0) {
            carSaleInfo.setStrConsumerAge(cacheCenter.getCarPropertyById(carSaleInfo.getConsumerAge()).getPropertyValue());
        }
    }

    //车辆销售操作
    @RequestMapping(value = "/carSaleView", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView carSaleView(HttpSession session) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("car/saleList");
        installContextBean(dealList(daoCenter.getCarRecordListByRecordStatus(ContextType.RECORD_STATUS_SALE)), mav);
        mav.addObject(TIP, session.getAttribute("tip"));
        return mav;
    }


    //车辆售前售后整备相关操作
    @RequestMapping(value = "/carSaleInfoAction", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView carSaleInfoAction(
            @RequestParam(value = "action", required = true, defaultValue = "") Integer action,
            @RequestParam(value = "over", required = false, defaultValue = "") Integer over,
            @RequestParam(value = "id", required = false, defaultValue = "") Integer id,
            @RequestParam(value = "recordId", required = false, defaultValue = "") Integer recordId,
            @RequestParam(value = "salePerson", required = false, defaultValue = "") String salePerson,
            @RequestParam(value = "saleDate", required = false, defaultValue = "") String saleDate,
            @RequestParam(value = "saleMoney", required = false, defaultValue = "") Double saleMoney,
            @RequestParam(value = "agencyFee", required = false, defaultValue = "") Double agencyFee,
            @RequestParam(value = "unearnedInsurance", required = false, defaultValue = "") Double unearnedInsurance,
            @RequestParam(value = "consumerProperty", required = false, defaultValue = "") Integer consumerProperty,
            @RequestParam(value = "consumerResource", required = false, defaultValue = "") Integer consumerResource,
            @RequestParam(value = "purchaseUse", required = false, defaultValue = "") Integer purchaseUse,
            @RequestParam(value = "consumerName", required = false, defaultValue = "") String consumerName,
            @RequestParam(value = "consumerSex", required = false, defaultValue = "") Integer consumerSex,
            @RequestParam(value = "consumerAge", required = false, defaultValue = "") Integer consumerAge,
            @RequestParam(value = "consumerAddress", required = false, defaultValue = "") String consumerAddress,
            @RequestParam(value = "consumerPhone", required = false, defaultValue = "") String consumerPhone,
            @RequestParam(value = "saleType", required = false, defaultValue = "") Integer saleType,
            @RequestParam(value = "mortgageCommissioner", required = false, defaultValue = "") String mortgageCommissioner,
            @RequestParam(value = "mortgageCompany", required = false, defaultValue = "") String mortgageCompany,
            @RequestParam(value = "loanFee", required = false, defaultValue = "") Double loanFee,
            @RequestParam(value = "interestRate", required = false, defaultValue = "") String interestRate,
            @RequestParam(value = "mortgageRebate", required = false, defaultValue = "") Double mortgageRebate,
            @RequestParam(value = "backFee", required = false, defaultValue = "") Double backFee,
            @RequestParam(value = "assessmentFee", required = false, defaultValue = "") Double assessmentFee,
            @RequestParam(value = "riskFee", required = false, defaultValue = "") Double riskFee,
            @RequestParam(value = "renewalFee", required = false, defaultValue = "") Double renewalFee,
            @RequestParam(value = "padFee", required = false, defaultValue = "") Double padFee,
            @RequestParam(value = "doorFee", required = false, defaultValue = "") Double doorFee,
            @RequestParam(value = "stampDuty", required = false, defaultValue = "") Double stampDuty,
            @RequestParam(value = "otherFee", required = false, defaultValue = "") Double otherFee,
            HttpSession session) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("car/saleInfoAdd");
        mav.addObject("action", action);
        mav.addObject("recordId", recordId);
        initCarPropertyData(mav);

        mav.addObject("salePerson", salePerson);
        mav.addObject("saleDate", saleDate);
        mav.addObject("saleMoney", saleMoney);
        mav.addObject("agencyFee", agencyFee);
        mav.addObject("unearnedInsurance", unearnedInsurance);
        mav.addObject("consumerProperty", consumerProperty);
        mav.addObject("consumerResource", consumerResource);
        mav.addObject("purchaseUse", purchaseUse);
        mav.addObject("consumerName", consumerName);
        mav.addObject("consumerSex", consumerSex);
        mav.addObject("consumerAge", consumerAge);
        mav.addObject("consumerAddress", consumerAddress);
        mav.addObject("consumerPhone", consumerPhone);
        mav.addObject("saleType", saleType == null ? saleType = 1 : saleType);
        mav.addObject("mortgageCommissioner", mortgageCommissioner);
        mav.addObject("mortgageCompany", mortgageCompany);
        mav.addObject("loanFee", loanFee);
        mav.addObject("interestRate", interestRate);
        mav.addObject("mortgageRebate", mortgageRebate);
        mav.addObject("backFee", backFee);
        mav.addObject("assessmentFee", assessmentFee);
        mav.addObject("riskFee", riskFee);
        mav.addObject("renewalFee", renewalFee);
        mav.addObject("padFee", padFee);
        mav.addObject("doorFee", doorFee);
        mav.addObject("stampDuty", stampDuty);
        mav.addObject("otherFee", otherFee);

        if (over == null) {
            if (id != null) {
                CarSaleInfo carSaleInfo = daoCenter.getCarSaleInfoById(id);
                mav.addObject("id", id);

                mav.addObject("salePerson", carSaleInfo.getSalePerson());
                mav.addObject("saleDate", TimeUtils.transformTimetagToDate(carSaleInfo.getSaleDate(), TimeUtils.FORMAT_ONE));
                mav.addObject("saleMoney", carSaleInfo.getSaleMoney());
                mav.addObject("agencyFee", carSaleInfo.getAgencyFee());
                mav.addObject("unearnedInsurance", carSaleInfo.getUnearnedInsurance());
                mav.addObject("consumerProperty", carSaleInfo.getConsumerProperty());
                mav.addObject("consumerResource", carSaleInfo.getConsumerResource());
                mav.addObject("purchaseUse", carSaleInfo.getPurchaseUse());
                mav.addObject("consumerName", carSaleInfo.getConsumerName());
                mav.addObject("consumerSex", carSaleInfo.getConsumerSex());
                mav.addObject("consumerAge", carSaleInfo.getConsumerAge());
                mav.addObject("consumerAddress", carSaleInfo.getConsumerAddress());
                mav.addObject("consumerPhone", carSaleInfo.getConsumerPhone());
                mav.addObject("saleType", carSaleInfo.getSaleType());

                if (carSaleInfo.getSaleType().equals(ContextType.SALE_TYPE_AJ)) {
                    MortgageRecord mortgageRecord = daoCenter.getMortgageRecordById(carSaleInfo.getMortgageId());
                    mav.addObject("mortgageCommissioner", mortgageRecord.getMortgageCommissioner());
                    mav.addObject("mortgageCompany", mortgageRecord.getMortgageCompany());
                    mav.addObject("loanFee", mortgageRecord.getLoanFee());
                    mav.addObject("interestRate", mortgageRecord.getInterestRate());
                    mav.addObject("mortgageRebate", mortgageRecord.getMortgageRebate());
                    mav.addObject("backFee", mortgageRecord.getBackFee());
                    mav.addObject("assessmentFee", mortgageRecord.getAssessmentFee());
                    mav.addObject("riskFee", mortgageRecord.getRiskFee());
                    mav.addObject("renewalFee", mortgageRecord.getRenewalFee());
                    mav.addObject("padFee", mortgageRecord.getPadFee());
                    mav.addObject("doorFee", mortgageRecord.getDoorFee());
                    mav.addObject("stampDuty", mortgageRecord.getStampDuty());
                    mav.addObject("otherFee", mortgageRecord.getOtherFee());
                }
            }
            return mav;
        } else {
            if (StringUtils.isEmpty(salePerson)) {
                mav.addObject(TIP, "销售人必填！");
                return mav;
            }

            if (StringUtils.isEmpty(saleDate)) {
                mav.addObject(TIP, "销售日期必填！");
                return mav;
            }

            if (saleMoney == 0) {
                mav.addObject(TIP, "销售价格必填！");
                return mav;
            }
            if (unearnedInsurance == null && action.equals(CREATE_ACTION)) {
                mav.addObject(TIP, "预收保险金额必填！");
                return mav;
            }


            if (StringUtils.isEmpty(saleDate)) {
                mav.addObject(TIP, "销售日期必填！");
                return mav;
            }

            if (consumerProperty == 0) {
                mav.addObject(TIP, "客户属性必选！");
                return mav;
            }

            if (consumerResource == 0) {
                mav.addObject(TIP, "获客渠道必选！");
                return mav;
            }

            if (purchaseUse == 0) {
                mav.addObject(TIP, "购车用途必选！");
                return mav;
            }

            if (consumerSex == null) {
                mav.addObject(TIP, "客户性别必选！");
                return mav;
            }

            if (saleType.equals(ContextType.SALE_TYPE_AJ)) {
                if (StringUtils.isEmpty(mortgageCommissioner)) {
                    mav.addObject(TIP, "对接按揭专员必填！");
                    return mav;
                }
                if (StringUtils.isEmpty(mortgageCompany)) {
                    mav.addObject(TIP, "按揭公司必填！");
                    return mav;
                }
                if (StringUtils.isEmpty(interestRate)) {
                    mav.addObject(TIP, "利率必填！");
                    return mav;
                }
            }

            CarSaleInfo carSaleInfo = new CarSaleInfo();
            carSaleInfo.setSalePerson(salePerson);
            carSaleInfo.setSaleDate(TimeUtils.transformDateToTimetag(saleDate, TimeUtils.FORMAT_ONE));
            carSaleInfo.setSaleMoney(saleMoney);
            carSaleInfo.setAgencyFee(agencyFee == null ? 0.0 : agencyFee);
            carSaleInfo.setConsumerProperty(consumerProperty);
            carSaleInfo.setConsumerResource(consumerResource);
            carSaleInfo.setPurchaseUse(purchaseUse);
            carSaleInfo.setConsumerName(consumerName);
            carSaleInfo.setConsumerSex(consumerSex);
            carSaleInfo.setConsumerAge(consumerAge);
            carSaleInfo.setConsumerAddress(consumerAddress);
            carSaleInfo.setConsumerPhone(consumerPhone);
            carSaleInfo.setSaleType(saleType);
            carSaleInfo.setUnearnedInsurance(unearnedInsurance == null ? 0.0 : unearnedInsurance);

            mav.clear();
            if (action.equals(CREATE_ACTION)) {
                carSaleInfo.setId(daoCenter.getCarSaleInfoAutoId());
                MortgageRecord mortgageRecord = null;
                if (saleType.equals(ContextType.SALE_TYPE_AJ)) {
                    mortgageRecord = new MortgageRecord();
                    mortgageRecord.setId(daoCenter.getMortgageRecordAutoId());
                    mortgageRecord.setMortgageCommissioner(mortgageCommissioner);
                    mortgageRecord.setMortgageCompany(mortgageCompany);
                    mortgageRecord.setLoanFee(loanFee == null ? 0.0 : loanFee);
                    mortgageRecord.setInterestRate(interestRate);
                    mortgageRecord.setMortgageRebate(mortgageRebate == null ? 0.0 : mortgageRebate);
                    mortgageRecord.setBackFee(backFee == null ? 0.0 : backFee);
                    mortgageRecord.setAssessmentFee(assessmentFee == null ? 0.0 : assessmentFee);
                    mortgageRecord.setRiskFee(riskFee == null ? 0.0 : riskFee);
                    mortgageRecord.setRenewalFee(renewalFee == null ? 0.0 : renewalFee);
                    mortgageRecord.setPadFee(padFee == null ? 0.0 : padFee);
                    mortgageRecord.setDoorFee(doorFee == null ? 0.0 : doorFee);
                    mortgageRecord.setStampDuty(stampDuty == null ? 0.0 : stampDuty);
                    mortgageRecord.setOtherFee(otherFee == null ? 0.0 : otherFee);
                    mortgageRecord.setMortgageMoney(0.0);
                    mortgageRecord.setaMortgageMoney(0.0);
                    mortgageRecord.setIsMortgage(mortgageRecord.getMortgageMoney() <= 0 ? 0 : 1);
                    mortgageRecord.setIsSale(1);
                    mortgageRecord.setSaleId(carSaleInfo.getId());
                    daoCenter.createMortgageRecord(mortgageRecord);
                    carSaleInfo.setMortgageId(mortgageRecord.getId());

                }
                carSaleInfo.setPayMoney(CommonAction.calculatePayMoney(carSaleInfo.getSaleMoney(), carSaleInfo.getUnearnedInsurance(), mortgageRecord));

                //查看用户是否交订金
                CarRecord old = daoCenter.getCarRecordById(recordId);
                Double paidMoney = 0.0;
                if (old.getDeposit() != null && old.getDeposit() != 0.0) {
                    paidMoney = old.getDeposit();
                    CarPaidRecord carPaidRecord = new CarPaidRecord();
                    carPaidRecord.setCarRecordId(recordId);
                    carPaidRecord.setRecordStatus(ContextType.PAY_RECORD_SALE);
                    carPaidRecord.setPaidMoney(paidMoney);
                    carPaidRecord.setPaidReason("用户交付的订金");
                    daoCenter.createCarPaidRecord(carPaidRecord);
                }
                carSaleInfo.setPaidMoney(paidMoney);
                carSaleInfo.setBusinessExpenseFee(0.0);
                carSaleInfo.setForceExpenseFee(0.0);
                carSaleInfo.setExpenseRebate(0.0);
                carSaleInfo.setPayCompanyFee(0.0);
                carSaleInfo.setAllUnearnedInsurance(0.0);
                carSaleInfo.setCarRecordId(recordId);
                daoCenter.createCarSaleInfo(carSaleInfo);

                CarRecord carRecord = new CarRecord();
                carRecord.setId(recordId);
                carRecord.setIsSale(1);
                carRecord.setSaleId(carSaleInfo.getId());
                daoCenter.updateCarRecord(carRecord);
                session.setAttribute("tip", "ok 销售信息录入成功！");
            } else if (action.equals(MOD_ACTION)) {
                CarSaleInfo oldCarSaleInfo = daoCenter.getCarSaleInfoById(id);
                if (oldCarSaleInfo.getSaleType().equals(ContextType.SALE_TYPE_QK) && carSaleInfo.getSaleType().equals(ContextType.SALE_TYPE_QK)) {
                } else if (oldCarSaleInfo.getSaleType().equals(ContextType.SALE_TYPE_AJ)
                        && carSaleInfo.getSaleType().equals(ContextType.SALE_TYPE_QK)) {
                    daoCenter.deleteMortgageRecord(oldCarSaleInfo.getMortgageId());
                    carSaleInfo.setMortgageId(0);
                } else if (oldCarSaleInfo.getSaleType().equals(ContextType.SALE_TYPE_QK)
                        && carSaleInfo.getSaleType().equals(ContextType.SALE_TYPE_AJ)) {
                    MortgageRecord mortgageRecord = new MortgageRecord();
                    mortgageRecord.setId(daoCenter.getMortgageRecordAutoId());
                    mortgageRecord.setMortgageCommissioner(mortgageCommissioner);
                    mortgageRecord.setMortgageCompany(mortgageCompany);
                    mortgageRecord.setLoanFee(loanFee == null ? 0.0 : loanFee);
                    mortgageRecord.setInterestRate(interestRate);
                    mortgageRecord.setMortgageRebate(mortgageRebate == null ? 0.0 : mortgageRebate);
                    mortgageRecord.setBackFee(backFee == null ? 0.0 : backFee);
                    mortgageRecord.setAssessmentFee(assessmentFee == null ? 0.0 : assessmentFee);
                    mortgageRecord.setRiskFee(riskFee == null ? 0.0 : riskFee);
                    mortgageRecord.setRenewalFee(renewalFee == null ? 0.0 : renewalFee);
                    mortgageRecord.setPadFee(padFee == null ? 0.0 : padFee);
                    mortgageRecord.setDoorFee(doorFee == null ? 0.0 : doorFee);
                    mortgageRecord.setStampDuty(stampDuty == null ? 0.0 : stampDuty);
                    mortgageRecord.setOtherFee(otherFee == null ? 0.0 : otherFee);
                    mortgageRecord.setMortgageMoney(0.0);
                    mortgageRecord.setaMortgageMoney(0.0);
                    mortgageRecord.setIsMortgage(mortgageRecord.getMortgageMoney() <= 0 ? 0 : 1);
                    mortgageRecord.setIsSale(1);
                    mortgageRecord.setSaleId(id);
                    daoCenter.createMortgageRecord(mortgageRecord);
                    carSaleInfo.setMortgageId(mortgageRecord.getId());
                } else if (oldCarSaleInfo.getSaleType().equals(ContextType.SALE_TYPE_AJ)
                        && carSaleInfo.getSaleType().equals(ContextType.SALE_TYPE_AJ)) {
                    MortgageRecord mortgageRecord = daoCenter.getMortgageRecordById(oldCarSaleInfo.getMortgageId());
                    mortgageRecord.setMortgageCommissioner(mortgageCommissioner);
                    mortgageRecord.setMortgageCompany(mortgageCompany);
                    mortgageRecord.setLoanFee(loanFee == null ? 0.0 : loanFee);
                    mortgageRecord.setInterestRate(interestRate);
                    mortgageRecord.setMortgageRebate(mortgageRebate == null ? 0.0 : mortgageRebate);
                    mortgageRecord.setBackFee(backFee == null ? 0.0 : backFee);
                    mortgageRecord.setAssessmentFee(assessmentFee == null ? 0.0 : assessmentFee);
                    mortgageRecord.setRiskFee(riskFee == null ? 0.0 : riskFee);
                    mortgageRecord.setRenewalFee(renewalFee == null ? 0.0 : renewalFee);
                    mortgageRecord.setPadFee(padFee == null ? 0.0 : padFee);
                    mortgageRecord.setDoorFee(doorFee == null ? 0.0 : doorFee);
                    mortgageRecord.setStampDuty(stampDuty == null ? 0.0 : stampDuty);
                    mortgageRecord.setOtherFee(otherFee == null ? 0.0 : otherFee);
                    mortgageRecord.setMortgageMoney(0.0);
                    mortgageRecord.setaMortgageMoney(0.0);
                    mortgageRecord.setIsMortgage(mortgageRecord.getMortgageMoney() <= 0 ? 0 : 1);
                    daoCenter.updateMortgageRecord(mortgageRecord);

                    //按揭到按揭，把按揭表ID插入新carSaleInfo中
                    carSaleInfo.setMortgageId(mortgageRecord.getId());
                }
                carSaleInfo.setId(id);

                carSaleInfo.setPayMoney(CommonAction.calculatePayMoney(carSaleInfo.getSaleMoney(),
                        oldCarSaleInfo.getAllUnearnedInsurance() == 0 ? carSaleInfo.getUnearnedInsurance() : oldCarSaleInfo.getAllUnearnedInsurance(),
                        carSaleInfo.getMortgageId() == null ? null : daoCenter.getMortgageRecordById(carSaleInfo.getMortgageId())));
                if (carSaleInfo.getPayMoney() < oldCarSaleInfo.getPaidMoney()) {
                    carSaleInfo.setPaidMoney(carSaleInfo.getPayMoney());
                }
                if (oldCarSaleInfo.getPaidMoney() < 0) {
                    carSaleInfo.setPaidMoney(0.0);
                }
                daoCenter.updateCarSaleInfo(carSaleInfo);
                session.setAttribute("tip", "ok 销售信息修改成功！");
            }
            mav.setViewName("redirect:/carSaleView");
            return mav;
        }
    }

    //车辆保险信息相关操作
    @RequestMapping(value = "/carExpenseAction", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView carExpenseAction(
            @RequestParam(value = "action", required = true, defaultValue = "") Integer action,
            @RequestParam(value = "over", required = false, defaultValue = "") Integer over,
            @RequestParam(value = "id", required = false, defaultValue = "") Integer id,
            @RequestParam(value = "expenseCompany", required = false, defaultValue = "") String expenseCompany,
            @RequestParam(value = "businessExpenseFee", required = false, defaultValue = "") Double businessExpenseFee,
            @RequestParam(value = "forceExpenseFee", required = false, defaultValue = "") Double forceExpenseFee,
            @RequestParam(value = "expenseRebate", required = false, defaultValue = "") Double expenseRebate,
            @RequestParam(value = "allUnearnedInsurance", required = false, defaultValue = "") Double allUnearnedInsurance,
            @RequestParam(value = "payCompanyFee", required = false, defaultValue = "") Double payCompanyFee,
            HttpSession session) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("car/expenseAdd");
        mav.addObject("action", action);

        mav.addObject("expenseCompany", expenseCompany);
        mav.addObject("businessExpenseFee", businessExpenseFee);
        mav.addObject("forceExpenseFee", forceExpenseFee);
        mav.addObject("expenseRebate", expenseRebate);
        mav.addObject("allUnearnedInsurance", allUnearnedInsurance);
        mav.addObject("payCompanyFee", payCompanyFee);

        if (over == null) {
            if (id != null) {
                CarSaleInfo carSaleInfo = daoCenter.getCarSaleInfoById(id);
                mav.addObject("id", id);

                mav.addObject("expenseCompany", carSaleInfo.getExpenseCompany());
                mav.addObject("businessExpenseFee", carSaleInfo.getBusinessExpenseFee());
                mav.addObject("forceExpenseFee", carSaleInfo.getForceExpenseFee());
                mav.addObject("expenseRebate", carSaleInfo.getExpenseRebate());
                mav.addObject("allUnearnedInsurance", carSaleInfo.getAllUnearnedInsurance());
                mav.addObject("payCompanyFee", carSaleInfo.getPayCompanyFee());
            }
            return mav;
        } else {
            if (StringUtils.isEmpty(expenseCompany)) {
                mav.addObject(TIP, "保险公司必填");
                return mav;
            }

            CarSaleInfo carSaleInfo = new CarSaleInfo();
            carSaleInfo.setId(id);
            carSaleInfo.setExpenseCompany(expenseCompany);
            carSaleInfo.setBusinessExpenseFee(businessExpenseFee == null ? 0.0 : businessExpenseFee);
            carSaleInfo.setForceExpenseFee(forceExpenseFee == null ? 0.0 : forceExpenseFee);
            carSaleInfo.setExpenseRebate(expenseRebate == null ? 0.0 : expenseRebate);
            carSaleInfo.setAllUnearnedInsurance(allUnearnedInsurance == null ? 0.0 : allUnearnedInsurance);
            carSaleInfo.setPayCompanyFee(payCompanyFee == null ? 0.0 : payCompanyFee);

            //if (allUnearnedInsurance != null && allUnearnedInsurance != 0.0) {
            CarSaleInfo old = daoCenter.getCarSaleInfoById(id);
            Map<String, Integer> map = new HashMap<String, Integer>();
            map.put("linkId", id);

            Double payMoney = CommonAction.calculatePayMoney(old.getSaleMoney(), allUnearnedInsurance, old.getMortgageId() == null ? null : daoCenter.getMortgageRecordById(old.getMortgageId()));
            carSaleInfo.setPayMoney(payMoney);
            //保险处理
            Double d = payMoney - old.getPaidMoney();
            if (d < 0) {
                carSaleInfo.setPaidMoney(payMoney);
            }
            if (old.getPaidMoney() < 0) {
                carSaleInfo.setPaidMoney(0.0);
            }

            //修改总保险金额
            List<CarPayMoneyAssist> list = daoCenter.getCarPayMoneyAssistList(map);
            if (list.size() == 0) {
                CarPayMoneyAssist carPayMoneyAssist = new CarPayMoneyAssist();
                carPayMoneyAssist.setCarSaleInfoId(id);
                carPayMoneyAssist.setOldPayMoney(old.getUnearnedInsurance());
                carPayMoneyAssist.setNewPayMoney(allUnearnedInsurance);
                carPayMoneyAssist.setModReason("");
                carPayMoneyAssist.setDifference(d);
                daoCenter.createCarPayMoneyAssist(carPayMoneyAssist);
            } else {
                CarPayMoneyAssist carPayMoneyAssist = list.get(0);
                if (action.equals(MOD_ACTION)) {
                    CarPayMoneyAssist update = new CarPayMoneyAssist();
                    update.setId(carPayMoneyAssist.getId());
                    update.setNewPayMoney(allUnearnedInsurance);
                    daoCenter.updateCarPayMoneyAssist(update);
                }
            }
            //}

            mav.clear();
            if (action.equals(CREATE_ACTION)) {
                session.setAttribute("tip", "ok 保险信息录入成功！");
            } else if (action.equals(MOD_ACTION)) {

                session.setAttribute("tip", "ok 保险信息修改成功！");
            }
            daoCenter.updateCarSaleInfo(carSaleInfo);
            mav.setViewName("redirect:/carSaleView");
            return mav;
        }
    }

    @RequestMapping(value = "/carSoldDate", method = RequestMethod.GET)
    public ModelAndView carSoldDate(
            @RequestParam(value = "soldDate", required = false, defaultValue = "") String soldDate,
            @RequestParam(value = "id", required = false, defaultValue = "") Integer id,
            HttpSession session) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:/carSaleView");

        if (StringUtils.isEmpty(soldDate)) {
            session.setAttribute("tip", "日期必选！");
            return mav;
        }
        CarRecord carRecord = daoCenter.getCarRecordById(id);
        CarRecord update = new CarRecord();
        update.setId(id);
        update.setSoldDate(TimeUtils.transformDateToTimetag(soldDate, TimeUtils.FORMAT_ONE));
        daoCenter.updateCarRecord(update);
        session.setAttribute("tip", "ok 日期更新成功！");

        //判断付款情况
        CarSaleInfo carSaleInfo = daoCenter.getCarSaleInfoById(carRecord.getSaleId());

        //说明销售信息未填
        if (carSaleInfo == null) {
            return mav;
        }
        if (carSaleInfo.getPayMoney().equals(carSaleInfo.getPaidMoney())) {
            //判断销售成本是否录入
            if (carRecord.getIsSf() == 0) {
                session.setAttribute("tip", "销售成本未填写！");
                return mav;
            }
            CommonAction.changeCarStatus(daoCenter, carSaleInfo);
            session.setAttribute("tip", "ok 车辆达到标准，转已售！");
        }
        return mav;
    }

    @RequestMapping(value = "/mortgageCarPaid", method = RequestMethod.GET)
    public ModelAndView mortgageCarPaid(
            @RequestParam(value = "goonPaid", required = false, defaultValue = "") Double goonPaid,
            @RequestParam(value = "paidReason", required = false, defaultValue = "") String paidReason,
            @RequestParam(value = "id", required = false, defaultValue = "") Integer id,
            HttpSession session) {
        if (goonPaid == null) goonPaid = 0.0;
        ModelAndView mav = new ModelAndView();
        MortgageRecord mortgageLog = daoCenter.getMortgageRecordById(id);
        Double paidMoney = mortgageLog.getMortgageMoney() + goonPaid;
        mav.setViewName("redirect:/carSaleView");

        MortgageRecord update = new MortgageRecord();
        update.setId(id);
        update.setMortgageMoney(paidMoney);
        daoCenter.updateMortgageRecord(update);
        session.setAttribute("tip", "放款成功");

        //创建放款记录
        CarPaidRecord carPaidRecord = new CarPaidRecord();
        carPaidRecord.setCarRecordId(daoCenter.getCarSaleInfoById(mortgageLog.getSaleId()).getCarRecordId());
        carPaidRecord.setRecordStatus(ContextType.PAY_RECORD_MORTGAGE);
        carPaidRecord.setPaidMoney(goonPaid);
        carPaidRecord.setPaidReason(paidReason);
        daoCenter.createCarPaidRecord(carPaidRecord);
        return mav;
    }

    @RequestMapping(value = "/carSalePaid", method = RequestMethod.GET)
    public ModelAndView carSalePaid(
            @RequestParam(value = "goonPaid", required = false, defaultValue = "") Double goonPaid,
            @RequestParam(value = "paidReason", required = false, defaultValue = "") String paidReason,
            @RequestParam(value = "id", required = false, defaultValue = "") Integer id,
            HttpSession session) {
        if (goonPaid == null) goonPaid = 0.0;
        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:/carSaleView");
        CarSaleInfo carSaleInfo = daoCenter.getCarSaleInfoById(id);
        Double paidMoney = carSaleInfo.getPaidMoney() + goonPaid;

        CarSaleInfo update = new CarSaleInfo();
        update.setId(id);
        update.setPaidMoney(paidMoney);

        //判断已付金额是否达标
        if (paidMoney > carSaleInfo.getPayMoney()) {
            session.setAttribute("tip", "付款超限！");
            return mav;
        }

        update.setUtime(System.currentTimeMillis());
        daoCenter.updateCarSaleInfo(update);
        session.setAttribute("tip", "ok 付款成功！");

        //创建付款记录
        if (!(goonPaid == 0.0)) {
            CarPaidRecord carPaidRecord = new CarPaidRecord();
            carPaidRecord.setCarRecordId(carSaleInfo.getCarRecordId());
            carPaidRecord.setRecordStatus(ContextType.PAY_RECORD_SALE);
            carPaidRecord.setPaidMoney(goonPaid);
            carPaidRecord.setPaidReason(paidReason);
            daoCenter.createCarPaidRecord(carPaidRecord);
        }

        //判断转已售条件
        boolean isSaleAble = paidMoney.equals(carSaleInfo.getPayMoney());
        if (isSaleAble) {
            //判断销售成本是否录入
            CarRecord carRecord = daoCenter.getCarRecordById(carSaleInfo.getCarRecordId());
            if (carRecord.getIsSf() == 0) {
                session.setAttribute("tip", "销售成本未填写，不能转已售！");
                return mav;
            }
            if (carRecord.getSoldDate() == 0) {
                session.setAttribute("tip", "转已售日期未填写，不能转已售！");
                return mav;
            }
            CommonAction.changeCarStatus(daoCenter, carSaleInfo);
            session.setAttribute("tip", "ok 车辆达到标准，转已售！");
        }
        return mav;
    }

    @RequestMapping(value = "/carSaleDelete", method = RequestMethod.GET)
    public ModelAndView carSaleDelete(
            @RequestParam(value = "id", required = false, defaultValue = "") Integer id,
            @RequestParam(value = "carSaleInfoId", required = false, defaultValue = "") Integer carSaleInfoId,
            HttpSession session) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:/carSaleView");
        CarPaidRecord carPaidRecord = daoCenter.getCarPaidRecord(id);
        CarSaleInfo carSaleInfo = daoCenter.getCarSaleInfoById(carSaleInfoId);

        if (carPaidRecord != null) {
            CarSaleInfo update = new CarSaleInfo();
            update.setId(carSaleInfoId);
            update.setPaidMoney(carSaleInfo.getPaidMoney() - carPaidRecord.getPaidMoney());

            update.setUtime(System.currentTimeMillis());
            daoCenter.updateCarSaleInfo(update);

            daoCenter.deleteCarPaidRecord(id);
        }

        session.setAttribute("tip", "ok 付款记录删除成功！");
        return mav;
    }

    @RequestMapping(value = "/carSoldView", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView carSoldView(HttpSession session) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("car/soldList");

        QueryWrapper<CarRecord> query = new QueryWrapper<>();
        query.eq("record_status", ContextType.RECORD_STATUS_SOLD);
        mav.addObject("carNum", daoCenter.getCarRecordByQuery(query).size());

        query.orderByDesc("sold_date");
        query.last("limit 10");
        installContextBean(dealList(daoCenter.getCarRecordByQuery(query)), mav);

        mav.addObject(TIP, session.getAttribute("tip"));
        return mav;
    }

    //车辆服务基金使用相关
    private void dealServiceFundData(ModelAndView mav, List<ServiceFund> list) {
        Integer allServiceFund = daoCenter.getServiceMoney();
        if (allServiceFund == null) allServiceFund = 0;

        Integer useServiceFund = daoCenter.getUseServiceMoney();
        if (useServiceFund == null) useServiceFund = 0;

        Integer remainServiceFund = allServiceFund - useServiceFund;

        for (ServiceFund serviceFund : list) {
            serviceFund.setStrUseDate(TimeUtils.transformTimetagToDate(serviceFund.getUseDate(), TimeUtils.FORMAT_ONE));
        }
        mav.addObject(DATA, list);
        mav.addObject("allServiceFund", allServiceFund);
        mav.addObject("useServiceFund", useServiceFund);
        mav.addObject("remainServiceFund", remainServiceFund);
    }


    @RequestMapping(value = "/carServiceFundView", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView carServiceFundView(HttpSession session) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("car/serviceFund");
        List<ServiceFund> list = daoCenter.getServiceFundByMap(null);
        dealServiceFundData(mav, list);
        return mav;
    }


    @RequestMapping(value = "/carServiceFundAction", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView carServiceFundAction(
            @RequestParam(value = "action", required = true, defaultValue = "") Integer action,
            @RequestParam(value = "over", required = false, defaultValue = "") Integer over,
            @RequestParam(value = "id", required = false, defaultValue = "") Integer id,
            @RequestParam(value = "useDate", required = false, defaultValue = "") String useDate,
            @RequestParam(value = "person", required = false, defaultValue = "") String person,
            @RequestParam(value = "project", required = false, defaultValue = "") String project,
            @RequestParam(value = "fee", required = false, defaultValue = "") Double fee,
            @RequestParam(value = "remark", required = false, defaultValue = "") String remark,
            HttpSession session) {

        ModelAndView mav = new ModelAndView();
        mav.setViewName("car/serviceFundAdd");
        mav.addObject("action", action);

        mav.addObject("useDate", useDate);
        mav.addObject("person", person);
        mav.addObject("project", project);
        mav.addObject("fee", fee);
        mav.addObject("remark", remark);

        if (over == null) {
            if (id != null) {
                ServiceFund serviceFund = daoCenter.getServiceFundById(id);
                mav.addObject("id", id);
                mav.addObject("useDate", TimeUtils.transformTimetagToDate(serviceFund.getUseDate(), TimeUtils.FORMAT_ONE));
                mav.addObject("person", serviceFund.getPerson());
                mav.addObject("project", serviceFund.getProject());
                mav.addObject("fee", serviceFund.getFee());
                mav.addObject("remark", serviceFund.getRemark());
            }
            return mav;
        } else {
            ServiceFund serviceFund = new ServiceFund();
            serviceFund.setUseDate(TimeUtils.transformDateToTimetag(useDate, TimeUtils.FORMAT_ONE));
            serviceFund.setPerson(person);
            serviceFund.setProject(project);
            serviceFund.setFee(fee == null ? 0 : fee);
            serviceFund.setRemark(remark);

            mav.clear();
            if (action.equals(CREATE_ACTION)) {
                daoCenter.createServiceFund(serviceFund);
            } else if (action.equals(MOD_ACTION)) {
                serviceFund.setId(id);
                daoCenter.updateServiceFund(serviceFund);
            }
            mav.setViewName("redirect:/carServiceFundView");
            return mav;
        }
    }

    @RequestMapping(value = "/carServiceFundDelete", method = RequestMethod.GET)
    public ModelAndView carServiceFundDelete(
            @RequestParam(value = "id", required = false, defaultValue = "") Integer id,
            HttpSession session) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:/carServiceFundView");
        daoCenter.deleteServiceFund(id);
        return mav;
    }

    @RequestMapping(value = "/carRemarkAdd", method = RequestMethod.GET)
    public ModelAndView carRemarkAdd(
            @RequestParam(value = "carRecordId", required = false, defaultValue = "") Integer carRecordId,
            @RequestParam(value = "remarkType", required = false, defaultValue = "") Integer remarkType,
            @RequestParam(value = "remarkDate", required = false, defaultValue = "") String remarkDate,
            @RequestParam(value = "remark", required = false, defaultValue = "") String remark,
            HttpSession session) {
        ModelAndView mav = new ModelAndView();

        CarRemark carRemark = new CarRemark();
        carRemark.setCarRecordId(carRecordId);
        carRemark.setRemarkType(remarkType);
        carRemark.setRemarkDate(remarkDate);
        carRemark.setRemark(remark);
        daoCenter.createCarRemark(carRemark);

        if (remarkType.equals(ContextType.REMARK_TYPE_PURCHASE)) {
            mav.setViewName("redirect:/carPurchaseView");
        } else if (remarkType.equals(ContextType.REMARK_TYPE_STOCK)) {
            mav.setViewName("redirect:/carStockView");
        } else if (remarkType.equals(ContextType.REMARK_TYPE_SALE)) {
            mav.setViewName("redirect:/carSaleView");
        } else if (remarkType.equals(ContextType.REMARK_TYPE_DOSSIER)) {
            mav.setViewName("redirect:/dossierView");
        }
        return mav;
    }

    @RequestMapping(value = "/carRemarkDel", method = RequestMethod.GET)
    public ModelAndView carRemarkDel(
            @RequestParam(value = "id", required = false, defaultValue = "") Integer id,
            @RequestParam(value = "remarkType", required = false, defaultValue = "") Integer remarkType,
            HttpSession session) {
        ModelAndView mav = new ModelAndView();
        daoCenter.deleteCarRemark(id);
        if (remarkType.equals(ContextType.REMARK_TYPE_PURCHASE)) {
            mav.setViewName("redirect:/carPurchaseView");
        } else if (remarkType.equals(ContextType.REMARK_TYPE_STOCK)) {
            mav.setViewName("redirect:/carStockView");
        } else if (remarkType.equals(ContextType.REMARK_TYPE_SALE)) {
            mav.setViewName("redirect:/carSaleView");
        } else if (remarkType.equals(ContextType.REMARK_TYPE_DOSSIER)) {
            mav.setViewName("redirect:/dossierView");
        }
        return mav;
    }

    @RequestMapping(value = "/carRecordExport", method = RequestMethod.GET)
    public ModelAndView carRecordExport(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:/carSoldView");

        //组装头部
        List<String> headers = new ArrayList<String>();
        CarRecord.installHeaders(headers);
        CarBath.installHeaders(headers);
        CarCost.installHeaders(headers);
        CarSaleInfo.installHeaders(headers);
        MortgageRecord.installHeaders(headers, 2);
        CarSf.installHeaders(headers);

        List<CarRecordExport> list = new ArrayList<CarRecordExport>();
        for (ContextBean contextBean : contextBeanList) {
            CarRecordExport carRecordExport = new CarRecordExport();

            //组装车辆信息
            if (contextBean.getCarRecord() != null) {
                contextBean.getCarRecord().install(carRecordExport);
            }

            //组装批量操作信息
            if (contextBean.getCarRecord().getIsBath() == 1) {
                CarBath carBath = daoCenter.getCarBathById(contextBean.getCarRecord().getBathId());
                if (carBath != null) carBath.install(carRecordExport);
            }

            //组装成本录入
            if (contextBean.getCarCost() != null) {
                contextBean.getCarCost().install(carRecordExport);
            }

            //组装销售信息
            if (contextBean.getCarSaleInfo() != null) {
                contextBean.getCarSaleInfo().install(carRecordExport);
            }

            //组装销售按揭
            if (contextBean.getCarSaleInfo() != null) {
                contextBean.getCarSaleInfo().install(carRecordExport);
                if (contextBean.getCarSaleInfo().getSaleType().equals(ContextType.SALE_TYPE_AJ)) {
                    contextBean.getCarSaleInfo().getMortgageRecord().install(carRecordExport);
                }
            }

            //组装销售成本
            if (contextBean.getCarSf() != null) {
                contextBean.getCarSf().install(carRecordExport);
            }

            list.add(carRecordExport);
        }

        response.setContentType("octets/stream");
        String excelName = "车辆信息";
        //转码防止乱码
        response.addHeader("Content-Disposition", "attachment;filename=" + new String(excelName.getBytes("utf-8"), "ISO8859-1") + ".xls");
        ExportExcel<CarRecordExport> ex = new ExportExcel<CarRecordExport>();
        OutputStream out = response.getOutputStream();
        ex.exportExcel("车辆信息", headers, list, out);
        out.close();
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        return mav;
    }


}
