package cm.lx.dao;


import cm.lx.bean.ServiceFund;

import java.util.List;
import java.util.Map;

public interface ServiceFundDao {

    Integer getServiceFundAutoId();

    Integer insert(ServiceFund serviceFund);

    ServiceFund getById(Integer id);

    Integer deleteById(Integer id);

    Integer updateById(ServiceFund serviceFund);

    List<ServiceFund> getByMap(Map map);

    Integer getUseServiceMoney();
}
