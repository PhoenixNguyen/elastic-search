package vn.onepay.account.service.impl;

import java.util.ArrayList;
import java.util.List;

import vn.onepay.account.model.Province;
import vn.onepay.account.service.ProvinceManager;

public class ProvinceManagerImpl implements ProvinceManager{
	private List<Province> provinces;
	public void setProvinces(List<Province> provinces_) {
		this.provinces = provinces_;
	}
	
	@Override
	public Province find(String code) {
		for(Province province: this.provinces){
			if(code.equalsIgnoreCase(province.getCode()))
				return province;
		}
		return null;
	}
	
	@Override
	public List<Province> findAllProvince() {
		List<Province> rs = new ArrayList<Province>();
		final int status = 1;
		for(Province province: this.provinces){
			if(status == province.getStatus())
				rs.add(province);
		}
		return rs;
	}

}
