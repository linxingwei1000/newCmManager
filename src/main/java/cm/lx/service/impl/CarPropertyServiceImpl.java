package cm.lx.service.impl;

import cm.lx.dao.CarPropertyMapper;
import cm.lx.bean.entity.CarProperty;
import cm.lx.service.CarPropertyService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author linxingwei
 * @date 2019/1/22
 */
@Service
public class CarPropertyServiceImpl implements CarPropertyService {

    @Resource
    CarPropertyMapper carPropertyMapper;


    @Override
    public Integer create(CarProperty carProperty) {
        carProperty.setUtime(System.currentTimeMillis());
        carProperty.setCtime(System.currentTimeMillis());
        return carPropertyMapper.insert(carProperty);
    }

    @Override
    public Integer deleteCarPropertyById(Integer id) {
        return carPropertyMapper.deleteById(id);
    }

    @Override
    public Integer updateCarPropertyById(CarProperty carProperty) {
        carProperty.setUtime(System.currentTimeMillis());
        return carPropertyMapper.updateById(carProperty);
    }

    @Override
    public List<CarProperty> getCarPropertyListByKey(String key) {
        QueryWrapper<CarProperty> query = new QueryWrapper<>();
        query.eq("property_key", key);
        return carPropertyMapper.selectList(query);
    }
}
