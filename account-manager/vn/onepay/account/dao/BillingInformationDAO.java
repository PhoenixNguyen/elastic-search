package vn.onepay.account.dao;

import java.util.List;

import vn.onepay.account.model.Account;
import vn.onepay.account.model.BillingInformation;

public interface BillingInformationDAO {
	String BEAN_NAME = "billingInformationDAO";
	boolean save(Account account, BillingInformation billingInformation);
	void update(String username, String field, Object value);
//	boolean update(Account account, String id, String[] fields, Object[] values);
	
	List<BillingInformation> findAll();
	BillingInformation findById(String id);
	BillingInformation findByMerchant(String username);
}
