package cm.lx.service;

import cm.lx.bean.entity.CarPayMoneyAssist;

import java.util.List;

/**
 * @author linxingwei
 * @date 2019/1/21
 */
public interface CarPayMoneyAssistService {

    Integer createCarPayMoneyAssist(CarPayMoneyAssist carPayMoneyAssist);

    Integer deleteCarPayMoneyAssistByLinkId(Integer linkId);

    Integer updateCarPayMoneyAssist(CarPayMoneyAssist carPayMoneyAssist);

    List<CarPayMoneyAssist> getCarPayMoneyAssistListByLinkId(Integer linkId);

}
