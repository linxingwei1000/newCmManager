package cm.lx.service;

import cm.lx.bean.entity.CarSaleInfo;

/**
 * @author linxingwei
 * @date 2019/1/21
 */
public interface CarSaleInfoService {

    Integer createCarSaleInfo(CarSaleInfo carSaleInfo);

    Integer deleteCarSaleInfoById(Integer id);

    Integer updateCarSaleInfo(CarSaleInfo carSaleInfo);

    CarSaleInfo getCarSaleInfoById(Integer id);




}
