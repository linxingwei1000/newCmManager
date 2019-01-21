package cm.lx.dao;

import cm.lx.bean.CarProperty;

import java.util.List;
import java.util.Map;

public interface CarPropertyDao {

    Integer insert(CarProperty carProperty);

    Integer deleteById(Integer id);

    Integer updateById(CarProperty carProperty);

    List<CarProperty> getCarPropertyListByKey(Map map);
}
