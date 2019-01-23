package cm.lx.service;

import cm.lx.bean.entity.CarBath;

import java.util.List;

/**
 * @author linxingwei
 * @date 2019/1/21
 */
public interface CarBathService {

    Integer create(CarBath carBath);

    Integer deleteById(Integer id);

    Integer updateCarBathById(CarBath carBath);

    CarBath getCarBathById(Integer id);

    CarBath getByName(String name);

    List<CarBath> getCarBathListByAccount(String account);



    /**
     * carRecord修改批量购车信息
     * @param bathId
     * @param carRecordId
     * @param action
     */
    void modCarRecordId(Integer bathId, Integer carRecordId, int action);
}
