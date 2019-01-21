package cm.lx.dao;


import cm.lx.bean.NewCar;

import java.util.List;
import java.util.Map;

public interface NewCarDao {

    Integer getNewCarAutoId();

    Integer insert(NewCar newCar);

    NewCar getById(Integer id);

    Integer deleteById(Integer id);

    Integer updateById(NewCar newCar);

    List<NewCar> getNewCarList(Map map);

}
