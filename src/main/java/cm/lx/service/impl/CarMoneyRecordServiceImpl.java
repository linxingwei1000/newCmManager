package cm.lx.service.impl;

import cm.lx.bean.entity.CarMoneyRecord;
import cm.lx.dao.CarMoneyRecordMapper;
import cm.lx.service.CarMoneyRecordService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author linxingwei
 * @date 2019/3/1
 */
@Service
public class CarMoneyRecordServiceImpl implements CarMoneyRecordService {

    @Resource
    CarMoneyRecordMapper carMoneyRecordMapper;

    @Override
    public Integer createCarMoneyRecord(CarMoneyRecord carMoneyRecord) {
        carMoneyRecord.setUtime(System.currentTimeMillis());
        carMoneyRecord.setCtime(System.currentTimeMillis());
        return carMoneyRecordMapper.insert(carMoneyRecord);
    }

    @Override
    public Integer deleteCarMoneyRecord(Integer id) {
        return carMoneyRecordMapper.deleteById(id);
    }

    @Override
    public Integer deleteByCarRecordId(Integer carRecordId) {
        QueryWrapper<CarMoneyRecord> query = new QueryWrapper<>();
        query.eq("car_record_id", carRecordId);
        return carMoneyRecordMapper.delete(query);
    }

    @Override
    public Integer deleteByCarRecordIdAndType(Integer carRecordId, String type) {
        QueryWrapper<CarMoneyRecord> query = new QueryWrapper<>();
        query.eq("car_record_id", carRecordId).eq("link_type", type);
        return carMoneyRecordMapper.delete(query);
    }

    @Override
    public Integer updateCarMoneyRecord(CarMoneyRecord carMoneyRecord) {
        carMoneyRecord.setUtime(System.currentTimeMillis());
        return carMoneyRecordMapper.updateById(carMoneyRecord);
    }

    @Override
    public CarMoneyRecord getCarMoneyRecordById(Integer id) {
        return carMoneyRecordMapper.selectById(id);
    }

    @Override
    public List<CarMoneyRecord> getCarMoneyRecordListByCarRecordIdAndType(Integer carRecordId, String type) {
        QueryWrapper<CarMoneyRecord> query = new QueryWrapper<>();
        query.eq("car_record_id", carRecordId).eq("link_type", type);
        return carMoneyRecordMapper.selectList(query);
    }
}
