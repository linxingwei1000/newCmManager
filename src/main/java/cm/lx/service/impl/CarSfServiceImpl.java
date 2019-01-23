package cm.lx.service.impl;

import cm.lx.dao.CarSfMapper;
import cm.lx.bean.entity.CarSf;
import cm.lx.service.CarSfService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author linxingwei
 * @date 2019/1/21
 */
@Service
public class CarSfServiceImpl implements CarSfService {

    @Resource
    CarSfMapper carSfMapper;

    @Override
    public Integer createCarSf(CarSf carSf) {
        carSf.setUtime(System.currentTimeMillis());
        carSf.setCtime(System.currentTimeMillis());
        return carSfMapper.insert(carSf);
    }

    @Override
    public Integer updateCarSf(CarSf carSf) {
        carSf.setUtime(System.currentTimeMillis());
        return carSfMapper.updateById(carSf);
    }

    @Override
    public CarSf getCarSfById(Integer id) {
        return carSfMapper.selectById(id);
    }

    @Override
    public Integer deleteCarSfById(Integer id) {
        return carSfMapper.deleteById(id);
    }

    @Override
    public Double getServiceMoney() {
        return carSfMapper.getServiceMoney();
    }
}
