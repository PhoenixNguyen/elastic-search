package vn.onepay.account.model;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Document(collection = "BillingInformation")
@TypeAlias("billingInfo")
public class BillingInformation implements Serializable{
	private static final long serialVersionUID = 3746872595724007539L;
	
	public final static String BILLINGINFO_ID_FIELD_NAME="_id";
	public final static String BILLINGINFO_USERNAME_FIELD_NAME="username";
	public final static String BILLINGINFO_BILLING_TYPE="billing_type";
	public final static String BILLINGINFO_STATUS_FIELD_NAME="status";
	public final static String BILLINGINFO_ACCOUNT_NUMBER_FIELD_NAME="bank_account_number";
	public final static String BILLINGINFO_ACCOUNT_HOLDER_FIELD_NAME="bank_account_holder";
	public final static String BILLINGINFO_BANK_NAME_FIELD_NAME="bank_name";
	public final static String BILLINGINFO_CITY_BANK_FIELD_NAME="bank_city";
	public final static String BILLINGINFO_BRANCH_BANK_FIELD_NAME="bank_branch";
	
	public final static int INIT_STATUS = 0;
	public final static int MERCHANT_VERIFY_STATUS = 2;
	public final static int MERCHANT_REJECT_STATUS = 1;
	public final static int ONEPAY_VERIFY_STATUS = 4;
	public final static int ONEPAY_REJECT_STATUS = 3;
	
	public final static String BILLING_TYPE_WIRE_TRANSFER="wire_transfer";
	public final static String BILLING_TYPE_MONEY_ORDER="money_order";
	
	
	@Id
	private String id;
	@Indexed(unique=true)
	private String username;
	private String billing_type;
	private String bank_account_number;
	private String bank_account_holder;
	private String bank_name;
	private String bank_city;
	private String bank_name_code;
	private String bank_city_code;
	private String bank_branch;
	
	private int status = INIT_STATUS;
	@DateTimeFormat(iso=ISO.DATE_TIME)
	private java.util.Date created_time;
	@DateTimeFormat(iso=ISO.DATE_TIME)
	private java.util.Date updated_time;

	private String verify_by_user;
	
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
	public String getBilling_type() {
		return billing_type;
	}
	public void setBilling_type(String billing_type) {
		this.billing_type = billing_type;
	}
	public String getBank_account_number() {
		return bank_account_number;
	}
	public void setBank_account_number(String bank_account_number) {
		this.bank_account_number = bank_account_number;
	}
	public String getBank_account_holder() {
		return bank_account_holder;
	}
	public void setBank_account_holder(String bank_account_holder) {
		this.bank_account_holder = bank_account_holder;
	}
	public String getBank_name() {
		return bank_name;
	}
	public void setBank_name(String bank_name) {
		this.bank_name = bank_name;
	}
	public String getBank_city() {
		return bank_city;
	}
	public void setBank_city(String bank_city) {
		this.bank_city = bank_city;
	}
	public String getBank_name_code() {
		return bank_name_code;
	}
	public void setBank_name_code(String bank_name_code) {
		this.bank_name_code = bank_name_code;
	}
	public String getBank_city_code() {
		return bank_city_code;
	}
	public void setBank_city_code(String bank_city_code) {
		this.bank_city_code = bank_city_code;
	}
	public String getBank_branch() {
		return bank_branch;
	}
	public void setBank_branch(String bank_branch) {
		this.bank_branch = bank_branch;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public java.util.Date getCreated_time() {
		return created_time;
	}
	public void setCreated_time(java.util.Date created_time) {
		this.created_time = created_time;
	}
	public java.util.Date getUpdated_time() {
		return updated_time;
	}
	public void setUpdated_time(java.util.Date updated_time) {
		this.updated_time = updated_time;
	}
	
	public boolean getVerify() {
		boolean verify= (status == ONEPAY_VERIFY_STATUS)?true:false;
		return verify;
	}
	
	public void setVerify(boolean verify) {
		this.status = verify? ONEPAY_VERIFY_STATUS : INIT_STATUS ;
	}
	
	public String getVerify_by_user() {
		return verify_by_user;
	}
	public void setVerify_by_user(String verify_by_user) {
		this.verify_by_user = verify_by_user;
	}
}
