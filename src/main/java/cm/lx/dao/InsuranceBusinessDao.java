package cm.lx.dao;


import cm.lx.bean.InsuranceBusiness;

import java.util.List;
import java.util.Map;

public interface InsuranceBusinessDao {

    Integer getInsuranceBusinessAutoId();

    Integer insert(InsuranceBusiness insuranceBusiness);

    InsuranceBusiness getById(Integer id);

    Integer deleteById(Integer id);

    Integer updateById(InsuranceBusiness insuranceBusiness);

    List<InsuranceBusiness> getInsuranceBusinessByMap(Map map);

    Integer deleteByInsuranceId(Map map);
}
