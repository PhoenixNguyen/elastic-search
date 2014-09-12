package vn.onepay.account.service;

import java.util.List;

import vn.onepay.account.model.Bank;

public interface BankManager {
	String BEAN_NAME = "bankDAO";
	
	Bank find(String code);
	List<Bank> findAllBank();
}
