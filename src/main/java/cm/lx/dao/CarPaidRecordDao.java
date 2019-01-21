package cm.lx.dao;

import cm.lx.bean.CarPaidRecord;

import java.util.List;
import java.util.Map;

public interface CarPaidRecordDao {

    Integer insert(CarPaidRecord carPaidRecord);

    CarPaidRecord getById(Integer id);

    Integer deleteById(Integer id);

    Integer deleteByMap(Map map);

    Integer updateById(CarPaidRecord carPaidRecord);

    List<CarPaidRecord> getByMap(Map map);
}
