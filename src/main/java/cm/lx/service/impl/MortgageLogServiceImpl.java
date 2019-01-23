package cm.lx.service.impl;

import cm.lx.dao.MortgageLogMapper;
import cm.lx.bean.entity.MortgageLog;
import cm.lx.service.MortgageLogService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author linxingwei
 * @date 2019/1/21
 */
@Service
public class MortgageLogServiceImpl implements MortgageLogService {

    @Resource
    MortgageLogMapper mortgageLogMapper;

    @Override
    public Integer create(MortgageLog mortgageLog) {
        mortgageLog.setUtime(System.currentTimeMillis());
        mortgageLog.setCtime(System.currentTimeMillis());
        return mortgageLogMapper.insert(mortgageLog);
    }

    @Override
    public Integer deleteMortgageLogById(Integer id) {
        return mortgageLogMapper.deleteById(id);
    }

    @Override
    public Integer updateMortgageLog(MortgageLog mortgageLog) {
        mortgageLog.setUtime(System.currentTimeMillis());
        return mortgageLogMapper.updateById(mortgageLog);
    }

    @Override
    public MortgageLog getMortgageLogById(Integer id) {
        return mortgageLogMapper.selectById(id);
    }

    @Override
    public List<MortgageLog> getMortgageLogListByTypeAndStatus(Integer type, Integer status) {
        QueryWrapper<MortgageLog> query = new QueryWrapper<>();
        if (type != null) {
            query.eq("mortgage_type", type);
        }

        if (status != null) {
            query.eq("display_status", status);
        }
        return mortgageLogMapper.selectList(query);
    }
}
