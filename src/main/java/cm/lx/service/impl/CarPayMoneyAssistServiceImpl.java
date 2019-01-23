package cm.lx.service.impl;

import cm.lx.dao.CarPayMoneyAssistMapper;
import cm.lx.bean.entity.CarPayMoneyAssist;
import cm.lx.service.CarPayMoneyAssistService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author linxingwei
 * @date 2019/1/21
 */
@Service
public class CarPayMoneyAssistServiceImpl implements CarPayMoneyAssistService {

    @Resource
    CarPayMoneyAssistMapper carPayMoneyAssistMapper;


    @Override
    public Integer createCarPayMoneyAssist(CarPayMoneyAssist carPayMoneyAssist) {
        carPayMoneyAssist.setUtime(System.currentTimeMillis());
        carPayMoneyAssist.setCtime(System.currentTimeMillis());
        return carPayMoneyAssistMapper.insert(carPayMoneyAssist);
    }

    @Override
    public Integer deleteCarPayMoneyAssistByLinkId(Integer linkId) {
        QueryWrapper<CarPayMoneyAssist> query = new QueryWrapper<>();
        query.eq("car_sale_info_id", linkId);
        return carPayMoneyAssistMapper.delete(query);
    }

    @Override
    public Integer updateCarPayMoneyAssist(CarPayMoneyAssist carPayMoneyAssist) {
        carPayMoneyAssist.setUtime(System.currentTimeMillis());
        return carPayMoneyAssistMapper.updateById(carPayMoneyAssist);
    }

    @Override
    public List<CarPayMoneyAssist> getCarPayMoneyAssistListByLinkId(Integer linkId) {
        QueryWrapper<CarPayMoneyAssist> query = new QueryWrapper<>();
        query.eq("car_sale_info_id", linkId);
        return carPayMoneyAssistMapper.selectList(query);
    }
}
