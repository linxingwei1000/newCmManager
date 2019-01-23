package cm.lx.service.impl;

import cm.lx.dao.InsuranceMapper;
import cm.lx.bean.entity.Insurance;
import cm.lx.service.InsuranceService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author linxingwei
 * @date 2019/1/23
 */
@Service
public class InsuranceServiceImpl implements InsuranceService {

    @Resource
    InsuranceMapper insuranceMapper;

    @Override
    public Integer create(Insurance insurance) {
        insurance.setUtime(System.currentTimeMillis());
        insurance.setCtime(System.currentTimeMillis());
        return insuranceMapper.insert(insurance);
    }

    @Override
    public Integer deleteById(Integer id) {
        return insuranceMapper.deleteById(id);
    }

    @Override
    public Integer updateInsuranceById(Insurance insurance) {
        insurance.setUtime(System.currentTimeMillis());
        return insuranceMapper.updateById(insurance);
    }

    @Override
    public Insurance getInsuranceById(Integer id) {
        return insuranceMapper.selectById(id);
    }

    @Override
    public List<Insurance> getInsuranceListByDisplayStatus(Integer displayStatus) {
        QueryWrapper<Insurance> query = new QueryWrapper<>();
        if(displayStatus !=null){
            query.eq("display_status", displayStatus);
        }
        return insuranceMapper.selectList(query);
    }
}
