package cm.lx.dao;



import cm.lx.bean.MortgageLog;

import java.util.List;
import java.util.Map;

public interface MortgageLogDao {

    Integer getAutoId();

    Integer insert(MortgageLog mortgageLog);

    MortgageLog getById(Integer id);

    Integer deleteById(Integer id);

    Integer updateById(MortgageLog mortgageLog);

    List<MortgageLog> getMortgageLogList(Map map);

}
