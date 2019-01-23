package cm.lx.service;

import cm.lx.bean.entity.MortgageRebate;

import java.util.List;

/**
 * @author linxingwei
 * @date 2019/1/23
 */
public interface MortgageRebateService {

    Integer create(MortgageRebate mortgageRebate);

    Integer deleteMortgageRebateById(Integer id);

    Integer updateMortgageRebateById(MortgageRebate mortgageRebate);

    MortgageRebate getMortgageRebateById(Integer id);

    List<MortgageRebate> getMortgageRebateList();
}
