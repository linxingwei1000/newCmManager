package cm.lx.service.impl;

import cm.lx.dao.CarDepositMapper;
import cm.lx.bean.entity.CarDeposit;
import cm.lx.service.CarDepositService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author linxingwei
 * @date 2019/1/22
 */
@Service
public class CarDepositServiceImpl implements CarDepositService {

    @Resource
    CarDepositMapper carDepositMapper;

    @Override
    public Integer create(CarDeposit carDeposit) {
        carDeposit.setUtime(System.currentTimeMillis());
        carDeposit.setCtime(System.currentTimeMillis());
        return carDepositMapper.insert(carDeposit);
    }

    @Override
    public Integer deleteById(Integer id) {
        return carDepositMapper.deleteById(id);
    }

    @Override
    public Integer updateById(CarDeposit carDeposit) {
        carDeposit.setUtime(System.currentTimeMillis());
        return carDepositMapper.updateById(carDeposit);
    }

    @Override
    public CarDeposit getCarDepositById(Integer id) {
        return carDepositMapper.selectById(id);
    }

    @Override
    public List<CarDeposit> getCarDepositList() {
        return carDepositMapper.selectList(null);
    }
}
