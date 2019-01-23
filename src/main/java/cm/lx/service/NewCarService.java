package cm.lx.service;

import cm.lx.bean.entity.NewCar;

import java.util.List;

/**
 * @author linxingwei
 * @date 2019/1/21
 */
public interface NewCarService {

    Integer getAutoId();

    Integer create(NewCar newCar);

    Integer deleteById(Integer id);

    Integer updateNewCar(NewCar newCar);

    NewCar getNewCarById(Integer id);

    List<NewCar> getNewCarListByStatus(Integer status);
}
