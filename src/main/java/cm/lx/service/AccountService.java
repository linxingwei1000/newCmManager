package cm.lx.service;

import cm.lx.bean.entity.Account;

import java.util.List;

/**
 * @author linxingwei
 * @date 2019/1/22
 */
public interface AccountService {

    Integer create(Account account);

    Integer deleteAccountById(Integer id);

    Integer deleteAccountByDepartmentId(Integer departmentId);

    Integer updateAccountById(Account account);

    Account getAccountById(Integer id);

    Account getAccountByName(String account);

    Account getAccountByRealName(String name);

    List<Account> getAccountList();
}
