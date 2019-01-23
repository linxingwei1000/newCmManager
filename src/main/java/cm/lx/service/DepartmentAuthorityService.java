package cm.lx.service;

import cm.lx.bean.entity.DepartmentAuthority;

import java.util.List;

/**
 * @author linxingwei
 * @date 2019/1/22
 */
public interface DepartmentAuthorityService {

    Integer create(DepartmentAuthority departmentAuthority);

    Integer deleteDepartmentAuthorityById(Integer id);

    Integer updateDepartmentAuthority(DepartmentAuthority departmentAuthority);

    DepartmentAuthority getDepartmentAuthorityById(Integer id);

    DepartmentAuthority getDepartmentAuthorityByName(String name);

    List<DepartmentAuthority> getDepartmentAuthorityList();
}
