package cm.lx.service.impl;

import cm.lx.dao.MortgageRecordMapper;
import cm.lx.bean.entity.MortgageRecord;
import cm.lx.service.MortgageRecordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author linxingwei
 * @date 2019/1/21
 */
@Service
public class MortgageRecordServiceImpl implements MortgageRecordService {

    @Resource
    MortgageRecordMapper mortgageRecordMapper;

    @Override
    public Integer createMortgageRecord(MortgageRecord mortgageRecord) {
        mortgageRecord.setUtime(System.currentTimeMillis());
        mortgageRecord.setCtime(System.currentTimeMillis());
        return mortgageRecordMapper.insert(mortgageRecord);
    }

    @Override
    public Integer deleteMortgageRecordById(Integer id) {
        return mortgageRecordMapper.deleteById(id);
    }

    @Override
    public Integer updateMortgageRecord(MortgageRecord mortgageRecord) {
        mortgageRecord.setUtime(System.currentTimeMillis());
        return mortgageRecordMapper.updateById(mortgageRecord);
    }

    @Override
    public MortgageRecord getMortgageRecordById(Integer id) {
        return mortgageRecordMapper.selectById(id);
    }
}
