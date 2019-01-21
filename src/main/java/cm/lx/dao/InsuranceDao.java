package cm.lx.dao;


import cm.lx.bean.Insurance;

import java.util.List;
import java.util.Map;

public interface InsuranceDao {

    Integer getInsuranceAutoId();

    Integer insert(Insurance insurance);

    Insurance getById(Integer id);

    Integer deleteById(Integer id);

    Integer updateById(Insurance insurance);

    List<Insurance> getInsuranceByMap(Map map);
}
