package cm.lx.service;

import cm.lx.bean.entity.CarDossier;

import java.util.List;

/**
 * @author linxingwei
 * @date 2019/1/21
 */
public interface CarDossierService {

    Integer createCarDossier(CarDossier carDossier);

    Integer deleteCarDossierById(Integer id);

    Integer deleteCarDossierByCid(Integer cid);

    Integer updateCarDossierById(CarDossier carDossier);

    CarDossier getCarDossierById(Integer id);

    List<CarDossier> getCarDossierListByDisplayStatus(Integer displayStatus);
}
