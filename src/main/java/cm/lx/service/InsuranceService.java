package cm.lx.service;

import cm.lx.bean.entity.Insurance;

import java.util.List;

/**
 * @author linxingwei
 * @date 2019/1/23
 */
public interface InsuranceService {

    Integer create(Insurance insurance);

    Integer deleteById(Integer id);

    Integer updateInsuranceById(Insurance insurance);

    Insurance getInsuranceById(Integer id);

    List<Insurance> getInsuranceListByDisplayStatus(Integer displayStatus);
}
