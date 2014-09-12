package vn.onepay.account.service.impl;

import java.util.ArrayList;
import java.util.List;

import vn.onepay.account.model.Bank;
import vn.onepay.account.service.BankManager;

public class BankManagerImpl implements BankManager{
	private static List<Bank> banks;
	
	@Override
	public Bank find(String code) {
		for(Bank bank: this.banks){
			if(code.equalsIgnoreCase(bank.getCode()))
				return bank;
		}
		return null;
	}
	
	@Override
	public List<Bank> findAllBank() {
		List<Bank> rs = new ArrayList<Bank>();
		final int status = 1;
		for(Bank bank: this.banks){
			if(status == bank.getStatus())
				rs.add(bank);
		}
		return rs;
	}

	public void setBanks(List<Bank> banks_) {
		this.banks = banks_;
	}
}
