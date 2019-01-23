package cm.lx.service.impl;

import cm.lx.dao.MoneyManagerMapper;
import cm.lx.bean.entity.MoneyManager;
import cm.lx.service.MoneyManagerService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author linxingwei
 * @date 2019/1/23
 */
@Service
public class MoneyManagerServiceImpl implements MoneyManagerService {

    @Resource
    MoneyManagerMapper moneyManagerMapper;


    @Override
    public Integer create(MoneyManager moneyManager) {
        moneyManager.setUtime(System.currentTimeMillis());
        moneyManager.setCtime(System.currentTimeMillis());
        return moneyManagerMapper.insert(moneyManager);
    }

    @Override
    public Integer deleteMoneyManagerById(Integer id) {
        return moneyManagerMapper.deleteById(id);
    }

    @Override
    public Integer updateMoneyManagerById(MoneyManager moneyManager) {
        moneyManager.setUtime(System.currentTimeMillis());
        return moneyManagerMapper.updateById(moneyManager);
    }

    @Override
    public MoneyManager getMoneyManagerById(Integer id) {
        return moneyManagerMapper.selectById(id);
    }

    @Override
    public List<MoneyManager> getMoneyManagerListByType(Integer type) {
        QueryWrapper<MoneyManager> query = new QueryWrapper<>();
        if (type != null) {
            query.eq("money_type", type);
        }
        return moneyManagerMapper.selectList(query);
    }
}
