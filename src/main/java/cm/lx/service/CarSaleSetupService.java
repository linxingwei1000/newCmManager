package cm.lx.service;

import cm.lx.bean.entity.CarSaleSetup;

import java.util.List;

/**
 * @author linxingwei
 * @date 2019/1/21
 */
public interface CarSaleSetupService {

    Integer createCarSaleSetup(CarSaleSetup carSaleSetup);

    Integer deleteCarSaleSetupByIdAndType(Integer id, Integer type);

    Integer deleteCarSaleSetupById(Integer id);

    CarSaleSetup getCarSaleSetupById(Integer id);

    List<CarSaleSetup> getCarSaleSetupByIdAndType(Integer id, Integer type);
}
