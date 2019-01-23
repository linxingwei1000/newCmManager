package cm.lx.service.impl;

import cm.lx.dao.MortgageRebateMapper;
import cm.lx.bean.entity.MortgageRebate;
import cm.lx.service.MortgageRebateService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author linxingwei
 * @date 2019/1/23
 */
@Service
public class MortgageRebateServiceImpl implements MortgageRebateService {

    @Resource
    MortgageRebateMapper mortgageRebateMapper;

    @Override
    public Integer create(MortgageRebate mortgageRebate) {
        mortgageRebate.setUtime(System.currentTimeMillis());
        mortgageRebate.setCtime(System.currentTimeMillis());
        return mortgageRebateMapper.insert(mortgageRebate);
    }

    @Override
    public Integer deleteMortgageRebateById(Integer id) {
        return mortgageRebateMapper.deleteById(id);
    }

    @Override
    public Integer updateMortgageRebateById(MortgageRebate mortgageRebate) {
        mortgageRebate.setUtime(System.currentTimeMillis());
        return mortgageRebateMapper.updateById(mortgageRebate);
    }

    @Override
    public MortgageRebate getMortgageRebateById(Integer id) {
        return mortgageRebateMapper.selectById(id);
    }

    @Override
    public List<MortgageRebate> getMortgageRebateList() {
        return mortgageRebateMapper.selectList(null);
    }
}
