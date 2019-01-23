package cm.lx.service;

import cm.lx.bean.entity.CarProperty;

import java.util.List;

/**
 * @author linxingwei
 * @date 2019/1/22
 */
public interface CarPropertyService {

    Integer create(CarProperty carProperty);

    Integer deleteCarPropertyById(Integer id);

    Integer updateCarPropertyById(CarProperty carProperty);

    List<CarProperty> getCarPropertyListByKey(String key);
}
