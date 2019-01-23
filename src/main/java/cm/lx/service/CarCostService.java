package cm.lx.service;

import cm.lx.bean.entity.CarCost;

/**
 * @author linxingwei
 * @date 2019/1/21
 */
public interface CarCostService {

    Integer createCarCost(CarCost carCost);

    Integer deleteCarCostById(Integer id);

    Integer updateCarCost(CarCost carCost);

    CarCost getCarCostById(Integer id);


}
