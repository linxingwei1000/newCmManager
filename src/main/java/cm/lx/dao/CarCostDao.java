package cm.lx.dao;

import cm.lx.bean.CarCost;

public interface CarCostDao {

    Integer getCarCostAutoId();

    Integer insert(CarCost carCost);

    CarCost getById(Integer id);

    Integer deleteById(Integer id);

    Integer updateById(CarCost carCost);
}
