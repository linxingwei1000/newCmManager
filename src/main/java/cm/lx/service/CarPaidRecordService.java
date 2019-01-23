package cm.lx.service;

import cm.lx.bean.entity.CarPaidRecord;

import java.util.List;

/**
 * @author linxingwei
 * @date 2019/1/21
 */
public interface CarPaidRecordService {

    Integer create(CarPaidRecord carPaidRecord);

    Integer deleteCarPaidRecordByLinkIdAndType(Integer id, Integer type);

    Integer deleteCarPaidRecordById(Integer id);

    CarPaidRecord getCarPaidRecordById(Integer id);

    List<CarPaidRecord> getCarPaidRecordByLinkIdAndType(Integer id, Integer type);
}
