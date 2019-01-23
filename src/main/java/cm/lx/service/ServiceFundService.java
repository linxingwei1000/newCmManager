package cm.lx.service;

import cm.lx.bean.entity.ServiceFund;

import java.util.List;

/**
 * @author linxingwei
 * @date 2019/1/22
 */
public interface ServiceFundService {

    Integer createServiceFund(ServiceFund serviceFund);

    Integer deleteById(Integer id);

    Integer updateServiceFund(ServiceFund serviceFund);

    ServiceFund getServiceFundById(Integer id);

    List<ServiceFund> getServiceFundList();

}
