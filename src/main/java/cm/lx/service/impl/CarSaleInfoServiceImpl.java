package cm.lx.service.impl;

import cm.lx.bean.entity.CarSaleInfo;
import cm.lx.dao.CarSaleInfoMapper;
import cm.lx.service.CarSaleInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author linxingwei
 * @date 2019/1/21
 */
@Service
public class CarSaleInfoServiceImpl implements CarSaleInfoService {

    @Resource
    CarSaleInfoMapper carSaleInfoMapper;

    @Override
    public Integer createCarSaleInfo(CarSaleInfo carSaleInfo) {
        carSaleInfo.setUtime(System.currentTimeMillis());
        carSaleInfo.setCtime(System.currentTimeMillis());
        return carSaleInfoMapper.insert(carSaleInfo);
    }

    @Override
    public Integer deleteCarSaleInfoById(Integer id) {
        return carSaleInfoMapper.deleteById(id);
    }

    @Override
    public Integer updateCarSaleInfo(CarSaleInfo carSaleInfo) {
        carSaleInfo.setUtime(System.currentTimeMillis());
        return carSaleInfoMapper.updateById(carSaleInfo);
    }

    @Override
    public CarSaleInfo getCarSaleInfoById(Integer id) {
        return carSaleInfoMapper.selectById(id);

    }
}
