package cm.lx.dao;



import cm.lx.bean.CarRecord;
import java.util.List;
import java.util.Map;

public interface CarRecordDao {

	Integer getCarBathAutoId();

	Integer insert(CarRecord carRecord);

	Integer updateById(CarRecord carRecord);

	Integer deleteById(Integer id);

	CarRecord getById(Integer id);
//
//	DepartmentAuthority getByName(String name);

	List<CarRecord> getCarRecordListByRecordStatus(Map map);

}
