package cm.lx.dao;

import cm.lx.bean.CarRemark;

import java.util.List;
import java.util.Map;

public interface CarRemarkDao {

    Integer getCarRemarkAutoId();

    Integer insert(CarRemark CarRemark);

    CarRemark getById(Integer id);

    Integer deleteById(Integer id);

    Integer updateById(CarRemark carRemark);

    List<CarRemark> getCarRemarkList(Map map);
}
