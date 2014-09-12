package vn.onepay.account.service;

import java.util.List;

import vn.onepay.account.model.Province;

public interface ProvinceManager {
	String BEAN_NAME = "provinceDAO";
	
	Province find(String code);
	List<Province> findAllProvince();
}
