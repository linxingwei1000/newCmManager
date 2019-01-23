package cm.lx.service.impl;

import cm.lx.dao.CarCostMapper;
import cm.lx.bean.entity.CarCost;
import cm.lx.service.CarCostService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author linxingwei
 * @date 2019/1/21
 */
@Service
public class CarCostServiceImpl implements CarCostService {

    @Resource
    CarCostMapper carCostMapper;


    @Override
    public Integer createCarCost(CarCost carCost) {
        carCost.setUtime(System.currentTimeMillis());
        carCost.setCtime(System.currentTimeMillis());
        return carCostMapper.insert(carCost);
    }

    @Override
    public Integer deleteCarCostById(Integer id) {
        return carCostMapper.deleteById(id);
    }

    @Override
    public Integer updateCarCost(CarCost carCost) {
        carCost.setUtime(System.currentTimeMillis());
        return carCostMapper.updateById(carCost);
    }

    @Override
    public CarCost getCarCostById(Integer id) {
        return carCostMapper.selectById(id);
    }
}
