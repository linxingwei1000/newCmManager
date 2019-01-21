package cm.lx.dao;



import cm.lx.bean.DepartmentAuthority;

import java.util.List;

public interface DepartmentAuthorityDao {
	Integer insert(DepartmentAuthority departmentAuthority);

	Integer updateById(DepartmentAuthority departmentAuthority);

	Integer deleteById(Integer id);

	DepartmentAuthority getById(Integer id);

	DepartmentAuthority getByName(String name);

	List<DepartmentAuthority> getDepartmentAuthorityList();

}
