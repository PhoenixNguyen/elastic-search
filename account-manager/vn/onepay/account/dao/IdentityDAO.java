package vn.onepay.account.dao;

import vn.onepay.account.model.Account;
import vn.onepay.account.model.Identity;

public interface IdentityDAO {
	String BEAN_NAME = "identityDAO";
	boolean save(Account account, Identity identity);
	//boolean update(Account account, String id, String field, Object value);
	//boolean update(Account account, String id, String[] fields, Object[] values);
	
	Identity findById(String id);
	Identity findByMerchant(String username);
}
