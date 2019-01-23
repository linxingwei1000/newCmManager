package cm.lx.service.impl;

import cm.lx.dao.NewCarFinanceMapper;
import cm.lx.bean.entity.NewCarFinance;
import cm.lx.service.NewCarFinanceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author linxingwei
 * @date 2019/1/21
 */
@Service
public class NewCarFinanceServiceImpl implements NewCarFinanceService {

    @Resource
    NewCarFinanceMapper newCarFinanceMapper;


    @Override
    public Integer create(NewCarFinance newCarFinance) {
        newCarFinance.setUtime(System.currentTimeMillis());
        newCarFinance.setCtime(System.currentTimeMillis());
        return newCarFinanceMapper.insert(newCarFinance);
    }

    @Override
    public Integer deleteById(Integer id) {
        return newCarFinanceMapper.deleteById(id);
    }

    @Override
    public Integer updateNewCarFinance(NewCarFinance newCarFinance) {
        newCarFinance.setUtime(System.currentTimeMillis());
        return newCarFinanceMapper.updateById(newCarFinance);
    }

    @Override
    public NewCarFinance getNewCarFinanceById(Integer id) {
        return newCarFinanceMapper.selectById(id);
    }

    @Override
    public List<NewCarFinance> getNewCarFinanceList() {
        return newCarFinanceMapper.selectList(null);
    }
}
