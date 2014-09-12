package vn.onepay.account.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Document(collection = "AccountProperties")
@TypeAlias("accountProperty")
public class AccountProperty implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1512463186982566500L;

	public static final String ID_FIELD_NAME="_id";
	public static final String USER_NAME_FIELD_NAME="username";
	public static final String PARAM_FIELD_NAME="param";
	public static final String VALUE_FIELD_NAME="value";
	public static final String CREATED_TIME_FIELD_NAME="created_time";
	
	/*PARAM DEFINED*/
	public static final String ACCOUNT_PROPERTY_PRODUCT_RULES_IS_CONFIRMED_PARAM="product_rules_is_confirmed";
	
	public static final String ACCOUNT_PROPERTY_HAS_CARD_CHARGING_ECONTRACT_PARAM="card_charging_has_econtract";
	public static final String ACCOUNT_PROPERTY_HAS_SMS_CHARGING_ECONTRACT_PARAM="sms_charging_has_econtract";
	public static final String ACCOUNT_PROPERTY_HAS_IAC_CHARGING_ECONTRACT_PARAM="iac_charging_has_econtract";
	public static final String ACCOUNT_PROPERTY_HAS_WAP_CHARGING_ECONTRACT_PARAM="wap_charging_has_econtract";
	
	public static final String ACCOUNT_PROPERTY_HAS_ECONTRACT_VALUE="yes";
//	//Hợp đồng điện tử
//	public static final String HAS_ECONTRACT_ON="ON";
//	public static final String HAS_ECONTRACT_OFF="OFF";
//	public static final String HAS_ECONTRACT="_has_econtract_";
	
//	public static final int USING_ECONTRACT_NO=0;
//	public static final int USING_ECONTRACT_YES=1;
	
	@Id
	private String id;
	@Indexed
	private String username;
	@Indexed
	private String param;
	private String value;
	@DateTimeFormat(iso=ISO.DATE_TIME)
	private java.util.Date created_time = new Date();
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getParam() {
		return param;
	}
	public void setParam(String param) {
		this.param = param;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public java.util.Date getCreated_time() {
		return created_time;
	}
	public void setCreated_time(java.util.Date created_time) {
		this.created_time = created_time;
	}
	
}
