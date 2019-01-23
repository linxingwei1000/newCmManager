package cm.lx.service;

import cm.lx.bean.entity.CarRemark;

import java.util.List;

/**
 * @author linxingwei
 * @date 2019/1/22
 */
public interface CarRemarkService {

    Integer createCarRemark(CarRemark carRemark);

    Integer deleteCarRemarkById(Integer id);

    CarRemark getCarRemarkById(Integer id);

    List<CarRemark> getCarRemarkByLinkIdAndType(Integer linkId, Integer type);
}
