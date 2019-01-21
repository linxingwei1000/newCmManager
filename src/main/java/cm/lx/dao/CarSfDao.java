package cm.lx.dao;

import cm.lx.bean.CarSf;

public interface CarSfDao {

    Integer getCarSfAutoId();

    Integer insert(CarSf carSf);

    CarSf getById(Integer id);

    Integer deleteById(Integer id);

    Integer updateById(CarSf carSf);

    Integer getServiceMoney();
}
