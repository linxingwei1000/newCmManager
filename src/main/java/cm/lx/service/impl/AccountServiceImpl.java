package cm.lx.service.impl;

import cm.lx.dao.AccountMapper;
import cm.lx.bean.entity.Account;
import cm.lx.service.AccountService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author linxingwei
 * @date 2019/1/22
 */
@Service
public class AccountServiceImpl implements AccountService {

    @Resource
    AccountMapper accountMapper;


    @Override
    public Integer create(Account account) {
        account.setUtime(System.currentTimeMillis());
        account.setCtime(System.currentTimeMillis());
        return accountMapper.insert(account);
    }

    @Override
    public Integer deleteAccountById(Integer id) {
        return accountMapper.deleteById(id);
    }

    @Override
    public Integer deleteAccountByDepartmentId(Integer departmentId) {
        QueryWrapper<Account> query = new QueryWrapper<>();
        query.eq("department_id", departmentId);
        return accountMapper.delete(query);
    }

    @Override
    public Integer updateAccountById(Account account) {
        account.setUtime(System.currentTimeMillis());
        return accountMapper.updateById(account);
    }

    @Override
    public Account getAccountById(Integer id) {
        return accountMapper.selectById(id);
    }

    @Override
    public Account getAccountByName(String account) {
        QueryWrapper<Account> query = new QueryWrapper<>();
        query.eq("account_num", account).eq("active", 1);
        return accountMapper.selectOne(query);
    }

    @Override
    public Account getAccountByRealName(String name) {
        QueryWrapper<Account> query = new QueryWrapper<>();
        query.eq("name", name).eq("active", 1);
        return accountMapper.selectOne(query);
    }

    @Override
    public List<Account> getAccountList() {
        QueryWrapper<Account> query = new QueryWrapper<>();
        query.eq("active", 1);
        return accountMapper.selectList(query);
    }
}
