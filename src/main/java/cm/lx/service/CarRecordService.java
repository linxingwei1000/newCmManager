package cm.lx.service;

import cm.lx.bean.entity.CarRecord;

import java.util.List;

/**
 * @author linxingwei
 * @date 2019/1/21
 */
public interface CarRecordService {

    Integer createCarRecord(CarRecord carRecord);

    Integer deleteCarRecordById(Integer id);

    void updateCarRecord(CarRecord carRecord);

    CarRecord getCarRecordById(Integer id);

    List<CarRecord> getCarRecords();

    List<CarRecord> getCarRecordByRecordStatus(Integer recordStatus);

    List<CarRecord> searchCarRecord(Integer recordStatus, String searchKey, String searchValue, String searchDate, String btime, String etime, String zbtime, String zetime);
}
