package cm.lx.service;

import cm.lx.bean.entity.CarMoneyRecord;

import java.util.List;

/**
 * @author linxingwei
 * @date 2019/3/1
 */
public interface CarMoneyRecordService {

    Integer createCarMoneyRecord(CarMoneyRecord carMoneyRecord);

    Integer deleteCarMoneyRecord(Integer id);

    Integer deleteByCarRecordId(Integer carRecordId);

    Integer deleteByCarRecordIdAndType(Integer carRecordId, String type);

    Integer updateCarMoneyRecord(CarMoneyRecord carMoneyRecord);

    CarMoneyRecord getCarMoneyRecordById(Integer id);

    List<CarMoneyRecord> getCarMoneyRecordListByCarRecordIdAndType(Integer carRecordId, String type);
}
