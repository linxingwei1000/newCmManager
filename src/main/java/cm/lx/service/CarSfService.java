package cm.lx.service;

import cm.lx.bean.entity.CarSf;

/**
 * @author linxingwei
 * @date 2019/1/21
 */
public interface CarSfService {

    Integer createCarSf(CarSf carSf);

    Integer updateCarSf(CarSf carSf);

    CarSf getCarSfById(Integer id);

    Integer deleteCarSfById(Integer id);

    /**
     * 统计总服务基金
     *
     * @return
     */
    Double getServiceMoney();
}
