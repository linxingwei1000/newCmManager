package cm.lx.dao;

import cm.lx.bean.MortgageRebate;

import java.util.List;
import java.util.Map;

public interface MortgageRebateDao {

    Integer getMortgageRebateAutoId();

    Integer insert(MortgageRebate mortgageRebate);

    MortgageRebate getById(Integer id);

    Integer deleteById(Integer id);

    Integer updateById(MortgageRebate mortgageRebate);

    List<MortgageRebate> getMortgageRebateList(Map map);
}
