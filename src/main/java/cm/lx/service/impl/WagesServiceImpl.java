package cm.lx.service.impl;

import cm.lx.dao.WagesMapper;
import cm.lx.bean.entity.Wages;
import cm.lx.service.WagesService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author linxingwei
 * @date 2019/1/22
 */
@Service
public class WagesServiceImpl implements WagesService {

    @Resource
    WagesMapper wagesMapper;


    @Override
    public Integer create(Wages wages) {
        wages.setUtime(System.currentTimeMillis());
        wages.setCtime(System.currentTimeMillis());
        return wagesMapper.insert(wages);
    }

    @Override
    public Integer deleteById(Integer id) {
        return wagesMapper.deleteById(id);
    }

    @Override
    public Integer updateWages(Wages wages) {
        wages.setUtime(System.currentTimeMillis());
        return wagesMapper.updateById(wages);
    }

    @Override
    public Wages getWagesById(Integer id) {
        return wagesMapper.selectById(id);
    }

    @Override
    public List<Wages> getWagesByStaffAndMonth(String staff, String month) {
        QueryWrapper<Wages> query = new QueryWrapper<>();

        if(!StringUtils.isEmpty(staff)){
            query.eq("staff", staff);
        }

        if(!StringUtils.isEmpty(month)){
            query.eq("pay_month", month);
        }
        return wagesMapper.selectList(query);
    }
}
