package cm.lx.service.impl;

import cm.lx.dao.CarPaidRecordMapper;
import cm.lx.bean.entity.CarPaidRecord;
import cm.lx.service.CarPaidRecordService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author linxingwei
 * @date 2019/1/21
 */
@Service
public class CarPaidRecordServiceImpl implements CarPaidRecordService {

    @Resource
    CarPaidRecordMapper carPaidRecordMapper;

    @Override
    public Integer create(CarPaidRecord carPaidRecord) {
        carPaidRecord.setUtime(System.currentTimeMillis());
        carPaidRecord.setCtime(System.currentTimeMillis());
        return carPaidRecordMapper.insert(carPaidRecord);
    }

    @Override
    public Integer deleteCarPaidRecordByLinkIdAndType(Integer id, Integer type) {
        QueryWrapper<CarPaidRecord> query = new QueryWrapper<>();
        query.eq("car_record_id", id).eq("record_status", type);
        return carPaidRecordMapper.delete(query);
    }

    @Override
    public Integer deleteCarPaidRecordById(Integer id) {
        return carPaidRecordMapper.deleteById(id);
    }

    @Override
    public CarPaidRecord getCarPaidRecordById(Integer id) {
        return carPaidRecordMapper.selectById(id);
    }

    @Override
    public List<CarPaidRecord> getCarPaidRecordByLinkIdAndType(Integer id, Integer type) {
        QueryWrapper<CarPaidRecord> query = new QueryWrapper<>();
        query.eq("car_record_id", id).eq("record_status", type);
        return carPaidRecordMapper.selectList(query);
    }
}
