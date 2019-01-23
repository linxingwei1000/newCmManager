package cm.lx.service.impl;

import cm.lx.dao.CarBathMapper;
import cm.lx.bean.entity.CarBath;
import cm.lx.service.CarBathService;
import cm.lx.util.Utils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author linxingwei
 * @date 2019/1/21
 */
@Service
public class CarBathServiceImpl implements CarBathService {

    @Resource
    CarBathMapper carBathMapper;


    @Override
    public Integer create(CarBath carBath) {
        carBath.setUtime(System.currentTimeMillis());
        carBath.setCtime(System.currentTimeMillis());
        return carBathMapper.insert(carBath);
    }

    @Override
    public Integer deleteById(Integer id) {
        return carBathMapper.deleteById(id);
    }

    @Override
    public Integer updateCarBathById(CarBath carBath) {
        carBath.setUtime(System.currentTimeMillis());
        return carBathMapper.updateById(carBath);
    }

    @Override
    public List<CarBath> getCarBathListByAccount(String account) {
        QueryWrapper<CarBath> query = new QueryWrapper<>();
        if (account != null) {
            query.eq("account_num", account);
        }
        return carBathMapper.selectList(query);
    }

    @Override
    public CarBath getCarBathById(Integer id) {
        return carBathMapper.selectById(id);
    }

    @Override
    public CarBath getByName(String name) {
        QueryWrapper<CarBath> query = new QueryWrapper<>();
        query.eq("bath_name", name);
        return carBathMapper.selectOne(query);
    }

    @Override
    public void modCarRecordId(Integer bathId, Integer carRecordId, int action) {
        CarBath carBath = carBathMapper.selectById(bathId);
        String carRecordIds = carBath.getCarRecordId();
        if (action == 1) {
            if (StringUtils.isEmpty(carRecordIds)) {
                carRecordIds = String.valueOf(carRecordId);
            } else {
                carRecordIds = carRecordIds + "," + carRecordId;
            }
        } else {
            carRecordIds = Utils.removeKey(carRecordIds, String.valueOf(carRecordId));
        }
        carBath.setCarRecordId(carRecordIds);
        carBath.setUtime(System.currentTimeMillis());
        carBathMapper.updateById(carBath);
    }
}
