package cm.lx.dao;


import cm.lx.bean.Account;

import java.util.List;

public interface AccountDao {

	void insert(Account account);

	Integer deleteByDepartmentId(Integer departmentId);

	Integer deleteById(Integer id);

	Account getByName(String accountNum);

	Account getByRealName(String name);

	Account getById(Integer id);

	Integer updateById(Account account);

	List<Account> getAccountList(String name);
}
