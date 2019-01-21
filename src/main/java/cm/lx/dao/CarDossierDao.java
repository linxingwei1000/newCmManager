package cm.lx.dao;

import cm.lx.bean.CarDossier;

import java.util.List;
import java.util.Map;

public interface CarDossierDao {

    Integer getCarDossierAutoId();

    Integer insert(CarDossier carDossier);

    CarDossier getById(Integer id);

    Integer deleteById(Integer id);

    Integer deleteByMap(Map map);

    Integer updateById(CarDossier carDossier);

    List<CarDossier> getListByMap(Map map);
}
