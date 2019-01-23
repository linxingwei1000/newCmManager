package cm.lx.service;

import cm.lx.bean.entity.MortgageRecord;

/**
 * @author linxingwei
 * @date 2019/1/21
 */
public interface MortgageRecordService {

    Integer createMortgageRecord(MortgageRecord mortgageRecord);

    Integer deleteMortgageRecordById(Integer id);

    Integer updateMortgageRecord(MortgageRecord mortgageRecord);

    MortgageRecord getMortgageRecordById(Integer id);
}
