package vn.onepay.account.property.dao;

import java.util.List;

import vn.onepay.account.model.AccountProperty;

public interface AccountPropertyDAO {
	String BEAN_NAME = "accountPropertyDAO";
	
	boolean save(AccountProperty accountProfile);
	void remove(AccountProperty accountProfile);
	
	List<AccountProperty> findAllAccountProperties();
	List<AccountProperty> getAccountProperties(String username);
	AccountProperty getAccountProperty(String username, String accountProfileParam);
}
