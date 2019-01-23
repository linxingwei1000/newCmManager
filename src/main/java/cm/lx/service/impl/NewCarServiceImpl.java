package cm.lx.service.impl;

import cm.lx.dao.NewCarMapper;
import cm.lx.bean.entity.NewCar;
import cm.lx.service.NewCarService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author linxingwei
 * @date 2019/1/21
 */
@Service
public class NewCarServiceImpl implements NewCarService {

    @Resource
    NewCarMapper newCarMapper;

    @Override
    public Integer getAutoId() {
        QueryWrapper<NewCar> query = new QueryWrapper<>();
        query.select("select auto_increment from information_schema.TABLES where TABLE_SCHEMA='cm' and TABLE_NAME='cm_new_car'");
        Integer id = newCarMapper.selectCount(query);
        return id;
    }


    @Override
    public Integer create(NewCar newCar) {
        newCar.setUtime(System.currentTimeMillis());
        newCar.setCtime(System.currentTimeMillis());
        return newCarMapper.insert(newCar);
    }

    @Override
    public Integer deleteById(Integer id) {
        return newCarMapper.deleteById(id);
    }

    @Override
    public Integer updateNewCar(NewCar newCar) {
        newCar.setUtime(System.currentTimeMillis());
        return newCarMapper.updateById(newCar);
    }

    @Override
    public NewCar getNewCarById(Integer id) {
        return newCarMapper.selectById(id);
    }

    @Override
    public List<NewCar> getNewCarListByStatus(Integer status) {
        QueryWrapper<NewCar> query = new QueryWrapper<>();
        query.eq("record_status", status);
        return newCarMapper.selectList(query);
    }
}
