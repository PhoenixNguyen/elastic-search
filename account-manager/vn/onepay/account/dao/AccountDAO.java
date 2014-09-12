package vn.onepay.account.dao;

import java.util.Date;
import java.util.List;

import vn.onepay.account.model.Account;

public interface AccountDAO {
	String BEAN_NAME = "accountDAO";
	boolean insert(Account account);
	boolean update(Account account);
	void update(String username , String field, Object value);
	void delete(Account account);
	//
	Account find(String username);
	Account findByEmail(String email);
	List<Account> findByEmail(String email, int offset, int limit);
	Account findByPhone(String phone);
	List<Account> findByPhone(String phone, int offset, int limit);
	Account find(String username, String password);
	//-----
	List<Account> findByRole(String role);
	List<Account> findByRole(String[] roles);
	List<String> findAllMerchants();
	//------
	long findTotalMerchants(Date fromTime, Date toTime);
	List<String> findMerchants(Date fromTime, Date toTime, int offset, int limit);
	//---
	List<Account> findByOwner(String owner, List<String> roles);
	void findByOwner(List<Account> rs, String owner, List<String> roles, boolean isRecursive, List<String> ownerRolesToRecursive);
	//---
	List<Account> findByTag(String tag);
	Account findOwner(String username);
	
	//
	List<Account> findAllAccount();
	int findTotalAccounts(String userName, String fullName, String referer, List<String> owners, List<String> roles, List<String> tags, Date fromTime, Date toTime);
	List<Account> findAccounts(String userName, String fullName, String referer, List<String> owners, List<String> roles, List<String> tags, Date fromTime, Date toTime, int offset, int limit);
}
