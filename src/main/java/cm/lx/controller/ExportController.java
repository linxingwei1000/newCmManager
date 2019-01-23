package cm.lx.controller;


import cm.lx.bean.*;
import cm.lx.bean.export.CarRecordExport;
import cm.lx.bean.export.InsuranceExport;
import cm.lx.bean.export.MortgageLogExport;
import cm.lx.bean.export.NewCarExport;
import cm.lx.business.CacheCenter;
import cm.lx.common.ContextType;
import cm.lx.bean.entity.*;
import cm.lx.service.CarBathService;
import cm.lx.util.ExportExcel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author linxingwei
 * @date 2019/1/23
 */
@Controller
public class ExportController extends BaseController {

    @Resource
    CacheCenter cacheCenter;

    @Resource
    CarBathService carBathService;

    /**
     * 车辆业务导出表格
     *
     * @param request
     * @param response
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/carRecordExport", method = RequestMethod.GET)
    public ModelAndView carRecordExport(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:/carSoldView");

        //组装头部
        List<String> headers = new ArrayList<>();
        CarRecord.installHeaders(headers);
        CarBath.installHeaders(headers);
        CarCost.installHeaders(headers);
        CarSaleInfo.installHeaders(headers);
        MortgageRecord.installHeaders(headers, 2);
        CarSf.installHeaders(headers);

        List<CarRecordExport> list = new ArrayList<>();
        for (ContextBean contextBean : cacheCenter.getContextBeanList()) {
            CarRecordExport carRecordExport = new CarRecordExport();

            //组装车辆信息
            if (contextBean.getCarRecord() != null) {
                contextBean.getCarRecord().install(carRecordExport);
            }

            //组装批量操作信息
            if (contextBean.getCarRecord().getIsBath() == 1) {
                CarBath carBath = carBathService.getCarBathById(contextBean.getCarRecord().getBathId());
                if (carBath != null) {
                    carBath.install(carRecordExport);
                }
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

    /**
     *  保险业务导出表格
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/insuranceExport", method = RequestMethod.GET)
    public ModelAndView insuranceExport(HttpServletResponse response) throws Exception {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:/insuranceView");

        //组装头行
        List<String> headers = new ArrayList<>();
        Insurance.installHeaders(headers);
        InsuranceBusiness.installHeaders(headers);

        List<InsuranceExport> list = new ArrayList<>();
        for (Insurance insurance : cacheCenter.getInsuranceList()) {
            if (insurance.getList() != null && insurance.getList().size() != 0) {
                InsuranceExport tempInsuranceExport = new InsuranceExport();
                insurance.install(tempInsuranceExport);
                for (InsuranceBusiness insuranceBusiness : insurance.getList()) {
                    insuranceBusiness.install(tempInsuranceExport);
                    list.add(tempInsuranceExport);
                }
            } else {
                InsuranceExport insuranceExport = new InsuranceExport();
                insurance.install(insuranceExport);
                list.add(insuranceExport);
            }
        }

        response.setContentType("octets/stream");
        String excelName = "保险信息";
        //转码防止乱码
        response.addHeader("Content-Disposition", "attachment;filename=" + new String(excelName.getBytes("utf-8"), "ISO8859-1") + ".xls");
        ExportExcel<InsuranceExport> ex = new ExportExcel<InsuranceExport>();
        OutputStream out = response.getOutputStream();
        ex.exportExcel("保险信息", headers, list, out);
        out.close();
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        return mav;
    }

    /**
     *  代办按揭业务导出表格
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/mortgageExport", method = RequestMethod.GET)
    public ModelAndView mortgageExport(HttpServletResponse response,
                                       @RequestParam(value = "mortgageType", required = false, defaultValue = "") Integer mortgageType) throws Exception {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:/mortgageView?mortgageType=" + mortgageType);

        //组装头行
        List<String> headers = new ArrayList<>();
        MortgageLog.installHeaders(headers);

        List<MortgageLogExport> list = new ArrayList<>();
        for (MortgageLog mortgageLog : cacheCenter.getMortgageLogList()) {
            MortgageLogExport mortgageLogExport = new MortgageLogExport();
            mortgageLog.installAll(mortgageLogExport);
            list.add(mortgageLogExport);
        }

        response.setContentType("octets/stream");
        String excelName = "按揭信息";
        //转码防止乱码
        response.addHeader("Content-Disposition", "attachment;filename=" + new String(excelName.getBytes("utf-8"), "ISO8859-1") + ".xls");
        ExportExcel<MortgageLogExport> ex = new ExportExcel<MortgageLogExport>();
        OutputStream out = response.getOutputStream();
        ex.exportExcel("按揭信息", headers, list, out);
        out.close();
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        return mav;
    }

    /**
     * 新车业务导出
     *
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/newCarExport", method = RequestMethod.GET)
    public ModelAndView newCarExport(HttpServletResponse response) throws Exception {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:/newCarView");

        //组装头行
        List<String> headers = new ArrayList<>();
        NewCar.installHeaders(headers);
        MortgageRecord.installHeaders(headers, 2);

        List<NewCarExport> list = new ArrayList<>();
        for (NewCar newCar : cacheCenter.getNewCarList()) {
            NewCarExport newCarExport = new NewCarExport();
            newCar.install(newCarExport);

            if (newCar.getSaleType().equals(ContextType.SALE_TYPE_AJ)) {
                newCar.getMortgageRecord().installNewCar(newCarExport);
            }
            list.add(newCarExport);
        }

        response.setContentType("octets/stream");
        String excelName = "新车信息";
        //转码防止乱码
        response.addHeader("Content-Disposition", "attachment;filename=" + new String(excelName.getBytes("utf-8"), "ISO8859-1") + ".xls");
        ExportExcel<NewCarExport> ex = new ExportExcel<NewCarExport>();
        OutputStream out = response.getOutputStream();
        ex.exportExcel("新车信息", headers, list, out);
        out.close();
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        return mav;
    }
}
