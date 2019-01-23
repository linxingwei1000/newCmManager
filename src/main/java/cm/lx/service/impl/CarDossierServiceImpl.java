package cm.lx.service.impl;

import cm.lx.dao.CarDossierMapper;
import cm.lx.bean.entity.CarDossier;
import cm.lx.service.CarDossierService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author linxingwei
 * @date 2019/1/21
 */
@Service
public class CarDossierServiceImpl implements CarDossierService {

    @Resource
    CarDossierMapper carDossierMapper;

    @Override
    public Integer createCarDossier(CarDossier carDossier) {
        carDossier.setUtime(System.currentTimeMillis());
        carDossier.setCtime(System.currentTimeMillis());
        return carDossierMapper.insert(carDossier);
    }

    @Override
    public Integer deleteCarDossierById(Integer id) {
        return carDossierMapper.deleteById(id);
    }

    @Override
    public Integer deleteCarDossierByCid(Integer cid) {
        QueryWrapper<CarDossier> query = new QueryWrapper<>();
        query.eq("car_record_id", cid);
        return carDossierMapper.delete(query);
    }

    @Override
    public Integer updateCarDossierById(CarDossier carDossier) {
        carDossier.setUtime(System.currentTimeMillis());
        return carDossierMapper.updateById(carDossier);
    }

    @Override
    public CarDossier getCarDossierById(Integer id) {
        return carDossierMapper.selectById(id);
    }

    @Override
    public List<CarDossier> getCarDossierListByDisplayStatus(Integer displayStatus) {
        QueryWrapper<CarDossier> query = new QueryWrapper<>();
        if(displayStatus!=null){
            query.eq("display_status", displayStatus);
        }
        return carDossierMapper.selectList(query);
    }
}
