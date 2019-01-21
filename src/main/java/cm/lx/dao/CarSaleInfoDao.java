package cm.lx.dao;


import cm.lx.bean.CarSaleInfo;

import java.util.List;
import java.util.Map;

public interface CarSaleInfoDao {

    Integer getCarSaleInfoAutoId();

    Integer insert(CarSaleInfo carSaleInfo);

    CarSaleInfo getById(Integer id);

    Integer deleteById(Integer id);

    Integer updateById(CarSaleInfo carSaleInfo);

    List<CarSaleInfo> getBySaleType(Map map);
}
