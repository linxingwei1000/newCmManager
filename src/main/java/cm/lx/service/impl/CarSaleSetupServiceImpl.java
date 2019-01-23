package cm.lx.service.impl;

import cm.lx.dao.CarSaleSetupMapper;
import cm.lx.bean.entity.CarSaleSetup;
import cm.lx.service.CarSaleSetupService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author linxingwei
 * @date 2019/1/21
 */
@Service
public class CarSaleSetupServiceImpl implements CarSaleSetupService {

    @Resource
    CarSaleSetupMapper carSaleSetupMapper;

    @Override
    public Integer createCarSaleSetup(CarSaleSetup carSaleSetup) {
        carSaleSetup.setUtime(System.currentTimeMillis());
        carSaleSetup.setCtime(System.currentTimeMillis());
        return carSaleSetupMapper.insert(carSaleSetup);
    }

    @Override
    public Integer deleteCarSaleSetupByIdAndType(Integer id, Integer type) {
        QueryWrapper<CarSaleSetup> query = new QueryWrapper<>();
        query.eq("car_cost_id", id).eq("setup_type",type);
        return carSaleSetupMapper.delete(query);
    }

    @Override
    public Integer deleteCarSaleSetupById(Integer id) {
        return carSaleSetupMapper.deleteById(id);
    }

    @Override
    public CarSaleSetup getCarSaleSetupById(Integer id) {
        return carSaleSetupMapper.selectById(id);
    }

    @Override
    public List<CarSaleSetup> getCarSaleSetupByIdAndType(Integer id, Integer type) {
        QueryWrapper<CarSaleSetup> query = new QueryWrapper<>();
        query.eq("car_cost_id", id).eq("setup_type",type);
        return carSaleSetupMapper.selectList(query);
    }
}
