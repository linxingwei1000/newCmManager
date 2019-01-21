package cm.lx.dao;

import cm.lx.bean.CarPayMoneyAssist;

import java.util.List;
import java.util.Map;

public interface CarPayMoneyAssistDao {

    Integer insert(CarPayMoneyAssist carPayMoneyAssist);

    CarPayMoneyAssist getById(Integer id);

    Integer deleteById(Integer id);

    Integer deleteByMap(Map map);

    Integer updateById(CarPayMoneyAssist carPayMoneyAssist);

    List<CarPayMoneyAssist> getByCarSaleInfoId(Map map);
}
