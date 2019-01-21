package cm.lx.dao;

import cm.lx.bean.CarDeposit;
import cm.lx.bean.CarProperty;

import java.util.List;
import java.util.Map;

public interface CarDepositDao {

    Integer insert(CarDeposit carDeposit);

    CarDeposit getById(Integer id);

    Integer deleteById(Integer id);

    Integer updateById(CarDeposit carDeposit);

    List<CarDeposit> getCarDepositListByKey(Map map);
}
