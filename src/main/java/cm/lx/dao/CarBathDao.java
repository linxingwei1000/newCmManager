package cm.lx.dao;

import cm.lx.bean.CarBath;
import cm.lx.bean.CarProperty;

import java.util.List;
import java.util.Map;

public interface CarBathDao {

    Integer getCarBathAutoId();

    Integer insert(CarBath carBath);

    CarBath getById(Integer id);

    CarBath getByName(Map map);

    Integer deleteById(Integer id);

    Integer updateById(CarBath carBath);

    List<CarBath> getCarBathListByKey(Map map);
}
