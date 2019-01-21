package cm.lx.dao;


import cm.lx.bean.CarSaleSetup;

import java.util.List;
import java.util.Map;

public interface CarSaleSetupDao {

    Integer getCarSaleSetupAutoId();

    Integer insert(CarSaleSetup carSaleSetup);

    CarSaleSetup getById(Integer id);

    Integer deleteById(Integer id);

    Integer deleteByMap(Map map);

    Integer updateById(CarSaleSetup carSaleSetup);

    List<CarSaleSetup> getByCarCostId(Map map);
}
