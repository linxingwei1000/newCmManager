package cm.lx.service;

import cm.lx.bean.entity.MortgageLog;

import java.util.List;

/**
 * @author linxingwei
 * @date 2019/1/21
 */
public interface MortgageLogService {

    Integer create(MortgageLog mortgageLog);

    Integer deleteMortgageLogById(Integer id);

    Integer updateMortgageLog(MortgageLog mortgageLog);

    MortgageLog getMortgageLogById(Integer id);

    List<MortgageLog> getMortgageLogListByTypeAndStatus(Integer type, Integer status);
}
