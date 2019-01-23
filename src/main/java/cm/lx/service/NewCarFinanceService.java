package cm.lx.service;

import cm.lx.bean.entity.NewCarFinance;

import java.util.List;

/**
 * @author linxingwei
 * @date 2019/1/21
 */
public interface NewCarFinanceService {

    Integer create(NewCarFinance newCarFinance);

    Integer deleteById(Integer id);

    Integer updateNewCarFinance(NewCarFinance newCarFinance);

    NewCarFinance getNewCarFinanceById(Integer id);

    List<NewCarFinance> getNewCarFinanceList();
}
