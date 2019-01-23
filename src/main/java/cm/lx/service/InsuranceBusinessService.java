package cm.lx.service;

import cm.lx.bean.entity.InsuranceBusiness;

import java.util.List;

/**
 * @author linxingwei
 * @date 2019/1/23
 */
public interface InsuranceBusinessService {

    Integer create(InsuranceBusiness insuranceBusiness);

    Integer deleteById(Integer id);

    Integer deleteInsuranceBusinessByIid(Integer iid);

    Integer updateInsuranceBusinessById(InsuranceBusiness insuranceBusiness);

    InsuranceBusiness getInsuranceBusinessById(Integer id);

    List<InsuranceBusiness> getInsuranceBusinessListByIidAndStatus(Integer iid, Integer displayStatus);
}
