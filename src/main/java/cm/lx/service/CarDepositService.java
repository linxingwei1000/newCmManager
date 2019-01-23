package cm.lx.service;

import cm.lx.bean.entity.CarDeposit;

import java.util.List;

/**
 * @author linxingwei
 * @date 2019/1/22
 */
public interface CarDepositService {

    Integer create(CarDeposit carDeposit);

    Integer deleteById(Integer id);

    Integer updateById(CarDeposit carDeposit);

    CarDeposit getCarDepositById(Integer id);

    List<CarDeposit> getCarDepositList();
}
