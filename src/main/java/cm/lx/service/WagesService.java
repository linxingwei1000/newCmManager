package cm.lx.service;

import cm.lx.bean.entity.Wages;

import java.util.List;

/**
 * @author linxingwei
 * @date 2019/1/22
 */
public interface WagesService {

    Integer create(Wages wages);

    Integer deleteById(Integer id);

    Integer updateWages(Wages wages);

    Wages getWagesById(Integer id);

    List<Wages> getWagesByStaffAndMonth(String staff, String month);
}
