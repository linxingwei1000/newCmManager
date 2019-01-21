package cm.lx.dao;



import cm.lx.bean.MoneyManager;

import java.util.List;
import java.util.Map;

public interface MoneyManagerDao {

    Integer getAutoId();

    Integer insert(MoneyManager moneyManager);

    MoneyManager getById(Integer id);

    Integer deleteById(Integer id);

    Integer updateById(MoneyManager moneyManager);

    List<MoneyManager> getMoneyManagerList(Map map);
}
