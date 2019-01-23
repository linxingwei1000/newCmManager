package cm.lx.service.impl;

import cm.lx.dao.CarRemarkMapper;
import cm.lx.bean.entity.CarRemark;
import cm.lx.service.CarRemarkService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author linxingwei
 * @date 2019/1/22
 */
@Service
public class CarRemarkServiceImpl implements CarRemarkService {

    @Resource
    CarRemarkMapper carRemarkMapper;


    @Override
    public Integer createCarRemark(CarRemark carRemark) {
        carRemark.setUtime(System.currentTimeMillis());
        carRemark.setCtime(System.currentTimeMillis());
        return carRemarkMapper.insert(carRemark);
    }

    @Override
    public Integer deleteCarRemarkById(Integer id) {
        return carRemarkMapper.deleteById(id);
    }

    @Override
    public CarRemark getCarRemarkById(Integer id) {
        return carRemarkMapper.selectById(id);
    }

    @Override
    public List<CarRemark> getCarRemarkByLinkIdAndType(Integer linkId, Integer type) {
        QueryWrapper<CarRemark> query = new QueryWrapper<>();
        query.eq("car_record_id", linkId).eq("record_type", type);
        return carRemarkMapper.selectList(query);
    }
}
