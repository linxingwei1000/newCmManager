package cm.lx.service.impl;

import cm.lx.business.CacheCenter;
import cm.lx.common.ContextType;
import cm.lx.dao.CarSaleInfoMapper;
import cm.lx.bean.entity.CarRecord;
import cm.lx.dao.CarRecordMapper;
import cm.lx.bean.entity.CarSaleInfo;
import cm.lx.service.CarRecordService;
import cm.lx.util.TimeUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author linxingwei
 * @date 2019/1/21
 */
@Service
public class CarRecordServiceImpl implements CarRecordService {

    @Resource
    CarRecordMapper carRecordMapper;

    @Resource
    CarSaleInfoMapper carSaleInfoMapper;

    @Resource
    CacheCenter cacheCenter;

    @Override
    public Integer createCarRecord(CarRecord carRecord) {
        carRecord.setUtime(System.currentTimeMillis());
        carRecord.setCtime(System.currentTimeMillis());
        return carRecordMapper.insert(carRecord);
    }

    @Override
    public Integer deleteCarRecordById(Integer id) {
        int result = carRecordMapper.deleteById(id);
        //删除缓存
        cacheCenter.deleteCarRecordInfo(id);

        return result;
    }

    @Override
    public void updateCarRecord(CarRecord carRecord) {
        carRecord.setUtime(System.currentTimeMillis());
        carRecordMapper.updateById(carRecord);

        //删除缓存
        cacheCenter.deleteCarRecordInfo(carRecord.getId());
    }

    @Override
    public CarRecord getCarRecordById(Integer id) {
        return carRecordMapper.selectById(id);
    }

    @Override
    public List<CarRecord> getCarRecords() {
        QueryWrapper<CarRecord> query = new QueryWrapper<>();
        query.orderByDesc("purchase_date");
        return carRecordMapper.selectList(query);
    }

    @Override
    public List<CarRecord> getCarRecordByRecordStatus(Integer recordStatus) {
        QueryWrapper<CarRecord> query = new QueryWrapper<>();
        query.eq("record_status", recordStatus);
        if(recordStatus.equals(ContextType.RECORD_STATUS_SOLD)){
            query.orderByDesc("sold_date");
        }else{
            query.orderByDesc( "ctime");
        }
        return carRecordMapper.selectList(query);
    }

    @Override
    public List<CarRecord> searchCarRecord(Integer recordStatus, String searchKey, String searchValue, String btime, String etime, String zbtime, String zetime) {

        Long bt = StringUtils.isEmpty(btime) ? 0L : TimeUtils.transformDateToTimetag(btime, TimeUtils.FORMAT_ONE);
        Long et = StringUtils.isEmpty(etime) ? System.currentTimeMillis() : TimeUtils.transformDateToTimetag(etime, TimeUtils.FORMAT_ONE);
        Long zbt = StringUtils.isEmpty(zbtime) ? 0L : TimeUtils.transformDateToTimetag(zbtime, TimeUtils.FORMAT_ONE);
        Long zet = StringUtils.isEmpty(zetime) ? System.currentTimeMillis() : TimeUtils.transformDateToTimetag(zetime, TimeUtils.FORMAT_ONE);

        QueryWrapper<CarRecord> query = new QueryWrapper<>();
        query.eq("record_status", recordStatus);
        if (recordStatus.equals(ContextType.RECORD_STATUS_SOLD)) {
            query.between("sold_date", zbt, zet);
        } else if (recordStatus.equals(ContextType.RECORD_STATUS_STOCK)) {
            query.between("purchase_date", zbt, zet);
        }

        List<CarRecord> list;
        if (searchKey.equals("sale_date")) {
            query.eq("is_sale", 1);
            list = carRecordMapper.selectList(query);

            Map<Integer, CarRecord> map = new HashMap<>(list.size());
            for (CarRecord carRecord : list) {
                map.put(carRecord.getSaleId(), carRecord);
            }

            List<CarRecord> resultList = new ArrayList<>();

            QueryWrapper<CarSaleInfo> saleInfoQuery = new QueryWrapper<>();
            saleInfoQuery.between("sale_date", bt, et);
            List<CarSaleInfo> carSaleInfos = carSaleInfoMapper.selectList(saleInfoQuery);
            for (CarSaleInfo carSaleInfo : carSaleInfos) {
                CarRecord carRecord = map.get(carSaleInfo.getId());
                if (carRecord != null) {
                    resultList.add(carRecord);
                }
            }

            list = resultList;
        } else {
            if(!StringUtils.isEmpty(searchKey)){
                query.like(searchKey, searchValue);
            }
            list = carRecordMapper.selectList(query);
        }

        //按已售倒叙
        list.sort((o1, o2) -> {
            if(o2.getSoldDate() >= o1.getSoldDate()){
                return 1;
            }else{
               return -1;
            }
        });
        return list;
    }
}
