package cm.lx.service;

import cm.lx.bean.entity.MoneyManager;

import java.util.List;

/**
 * @author linxingwei
 * @date 2019/1/23
 */
public interface MoneyManagerService {

    Integer create(MoneyManager moneyManager);

    Integer deleteMoneyManagerById(Integer id);

    Integer updateMoneyManagerById(MoneyManager moneyManager);

    MoneyManager getMoneyManagerById(Integer id);

    List<MoneyManager> getMoneyManagerByIds(List<Integer> ids);

    List<MoneyManager> getMoneyManagerListByType(Integer type);
}
