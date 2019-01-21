package cm.lx.dao;


import cm.lx.bean.MortgageRecord;

import java.util.List;
import java.util.Map;

public interface MortgageRecordDao {

    Integer getMortgageRecordAutoId();

    Integer insert(MortgageRecord mortgageRecord);

    MortgageRecord getById(Integer id);

    Integer deleteById(Integer id);

    Integer updateById(MortgageRecord mortgageRecord);

    List<MortgageRecord> getMortgageRecordList(Map map);

}
