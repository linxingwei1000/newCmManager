package cm.lx.dao;



import cm.lx.bean.NewCarFinance;

import java.util.List;
import java.util.Map;

public interface NewCarFinanceDao {

    Integer getNewCarFinanceAutoId();

    Integer insert(NewCarFinance newCarFinance);

    NewCarFinance getById(Integer id);

    Integer deleteById(Integer id);

    Integer updateById(NewCarFinance newCarFinance);

    List<NewCarFinance> getNewCarFinanceList(Map map);

}
