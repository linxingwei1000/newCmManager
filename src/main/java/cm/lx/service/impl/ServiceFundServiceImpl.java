package cm.lx.service.impl;

import cm.lx.dao.ServiceFundMapper;
import cm.lx.bean.entity.ServiceFund;
import cm.lx.service.ServiceFundService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author linxingwei
 * @date 2019/1/22
 */
@Service
public class ServiceFundServiceImpl implements ServiceFundService {

    @Resource
    ServiceFundMapper serviceFundMapper;


    @Override
    public Integer createServiceFund(ServiceFund serviceFund) {
        serviceFund.setUtime(System.currentTimeMillis());
        serviceFund.setCtime(System.currentTimeMillis());
        return serviceFundMapper.insert(serviceFund);
    }

    @Override
    public Integer deleteById(Integer id) {
        return serviceFundMapper.deleteById(id);
    }

    @Override
    public Integer updateServiceFund(ServiceFund serviceFund) {
        serviceFund.setUtime(System.currentTimeMillis());
        return serviceFundMapper.updateById(serviceFund);
    }

    @Override
    public ServiceFund getServiceFundById(Integer id) {
        return serviceFundMapper.selectById(id);
    }

    @Override
    public List<ServiceFund> getServiceFundList() {
        return serviceFundMapper.selectList(null);
    }

}
