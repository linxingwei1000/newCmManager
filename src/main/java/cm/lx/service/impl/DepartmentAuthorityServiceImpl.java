package cm.lx.service.impl;

import cm.lx.dao.DepartmentAuthorityMapper;
import cm.lx.bean.entity.DepartmentAuthority;
import cm.lx.service.DepartmentAuthorityService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author linxingwei
 * @date 2019/1/22
 */
@Service
public class DepartmentAuthorityServiceImpl implements DepartmentAuthorityService {

    @Resource
    DepartmentAuthorityMapper departmentAuthorityMapper;


    @Override
    public Integer create(DepartmentAuthority departmentAuthority) {
        return departmentAuthorityMapper.insert(departmentAuthority);
    }

    @Override
    public Integer deleteDepartmentAuthorityById(Integer id) {
        return departmentAuthorityMapper.deleteById(id);
    }

    @Override
    public Integer updateDepartmentAuthority(DepartmentAuthority departmentAuthority) {
        return departmentAuthorityMapper.updateById(departmentAuthority);
    }

    @Override
    public DepartmentAuthority getDepartmentAuthorityById(Integer id) {
        return departmentAuthorityMapper.selectById(id);
    }

    @Override
    public DepartmentAuthority getDepartmentAuthorityByName(String name) {
        QueryWrapper<DepartmentAuthority> query = new QueryWrapper<>();
        query.eq("department_name", name);
        return departmentAuthorityMapper.selectOne(query);
    }

    @Override
    public List<DepartmentAuthority> getDepartmentAuthorityList() {
        return departmentAuthorityMapper.selectList(null);
    }
}
