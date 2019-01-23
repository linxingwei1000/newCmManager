package cm.lx.service.impl;

import cm.lx.dao.InsuranceBusinessMapper;
import cm.lx.bean.entity.InsuranceBusiness;
import cm.lx.service.InsuranceBusinessService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author linxingwei
 * @date 2019/1/23
 */
@Service
public class InsuranceBusinessServiceImpl implements InsuranceBusinessService {

    @Resource
    InsuranceBusinessMapper insuranceBusinessMapper;


    @Override
    public Integer create(InsuranceBusiness insuranceBusiness) {
        insuranceBusiness.setUtime(System.currentTimeMillis());
        insuranceBusiness.setCtime(System.currentTimeMillis());
        return insuranceBusinessMapper.insert(insuranceBusiness);
    }

    @Override
    public Integer deleteById(Integer id) {
        return insuranceBusinessMapper.deleteById(id);
    }

    @Override
    public Integer deleteInsuranceBusinessByIid(Integer iid) {
        QueryWrapper<InsuranceBusiness> query = new QueryWrapper<>();
        query.eq("insurance_id", iid);
        return insuranceBusinessMapper.delete(query);
    }

    @Override
    public Integer updateInsuranceBusinessById(InsuranceBusiness insuranceBusiness) {
        insuranceBusiness.setUtime(System.currentTimeMillis());
        return insuranceBusinessMapper.updateById(insuranceBusiness);
    }

    @Override
    public InsuranceBusiness getInsuranceBusinessById(Integer id) {
        return insuranceBusinessMapper.selectById(id);
    }

    @Override
    public List<InsuranceBusiness> getInsuranceBusinessListByIidAndStatus(Integer iid, Integer displayStatus) {
        QueryWrapper<InsuranceBusiness> query = new QueryWrapper<>();
        query.eq("insurance_id", iid).eq("display_status", displayStatus);
        return insuranceBusinessMapper.selectList(query);
    }
}
