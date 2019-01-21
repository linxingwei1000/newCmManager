package cm.lx.dao;


import cm.lx.bean.Wages;

import java.util.List;
import java.util.Map;

public interface WagesDao {

    Integer getWagesAutoId();

    Integer insert(Wages wages);

    Wages getById(Integer id);

    Integer deleteById(Integer id);

    Integer updateById(Wages wages);

    List<Wages> getListByMap(Map map);
}
