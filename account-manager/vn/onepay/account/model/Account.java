package vn.onepay.account.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import vn.onepay.utils.Utils;

@Document(collection = "Users")
@TypeAlias("user")
public class Account implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1979795537855633428L;
	public final static String ACCOUNT_ID_FIELD_NAME="_id";
	public final static String ACCOUNT_USER_NAME_FIELD_NAME="username";
	public final static String ACCOUNT_OWNER_FIELD_NAME="owner";
	public final static String ACCOUNT_EMAIL_FIELD_NAME="email";
	public final static String ACCOUNT_PHONE_FIELD_NAME="phone";
	public final static String ACCOUNT_ALTERNATIVE_PHONE_FIELD_NAME="alternative_phone";
	public final static String ACCOUNT_FULL_NAME_FIELD_NAME="full_name";
	public final static String ACCOUNT_PASSWORD_FIELD_NAME="password";
	public final static String ACCOUNT_RAW_PASSWORD_FIELD_NAME="raw_password";
	public final static String ACCOUNT_ROLES_FIELD_NAME="roles";
	public final static String ACCOUNT_TAGS_FIELD_NAME="tags";
	
	public final static String ACCOUNT_ACCESS_KEY_FIELD_NAME="access_key";
	public final static String ACCOUNT_SALT_FIELD_NAME="salt";
	public final static String ACCOUNT_SECRET_FIELD_NAME="secret";
	public final static String ACCOUNT_STATUS_FIELD_NAME="status";
	public final static String ACCOUNT_REFERER_FIELD_NAME = "referer";
	public final static String ACCOUNT_CREATED_TIME_FIELD_NAME="created_time";
	public final static String ACCOUNT_UPDATED_TIME_NAME="updated_time";
	
	//----------
	public final static String ACCOUNT_MERCHANT_ROLE="merchant";
	public final static String ACCOUNT_STAFF_ROLE="staff";
	public final static String ACCOUNT_STAFF_MANAGER_ROLE="staff_manager";
	public final static String ACCOUNT_MERCHANT_MANAGER_ROLE="merchant_manager";
	public final static String ACCOUNT_BOOK_KEEPER_ROLE="book_keeper";
	public final static String ACCOUNT_BILLING_ROLE="billing";
	public final static String ACCOUNT_REPORTER_ROLE="reporter";
	
	public final static String ACCOUNT_CUSTOMER_CARE_ROLE="customer_care";
	public final static String ACCOUNT_MARKETING_MANAGER_ROLE="marketing_manager";
	
	public final static String ACCOUNT_BIZ_SUPPORTER_ROLE ="biz_supporter";
	
	public final static String ACCOUNT_SHARE_HOLDER_ROLE ="share_holder";
	public final static String ACCOUNT_OPERATION_MANAGER_ROLE="operation_manager";
	public final static String ACCOUNT_ADMIN_ROLE="admin";
	
	public final static String ACCOUNT_VERIFY_PHONE="verify_phone";
	public final static String ACCOUNT_VERIFY_EMAIL="verify_email";
	
	/**
	 * Nhân viên 1Pay roles : staff
	 * Trưởng bộ phận: staff && staff_manager
	 * Trưởng bộ phận kinh doanh: staff && staff_manager && merchant_manager
	 * Trưởng bộ phận support: staff && staff_manager && biz_supporter
	 * Nhân viên kinh doanh: staff && merchant_manager
	 * Nhân viên support: staff && biz_supporter
	 */
	//-----------
	@Id
	private String id;
	@Indexed(unique=true)
	private String username;
	private String owner;
	private String email;
	private String phone;
	private String alternative_phone;
	private String full_name;
	private String password;
	private String raw_password;
	@Indexed
	private String[] roles;
	private String access_key;
	private String salt;
	private String secret;
	@Indexed
	private String status;
	private String referer;
	private String[] tags;
	@DateTimeFormat(iso=ISO.DATE_TIME)
	private java.util.Date created_time;
	@DateTimeFormat(iso=ISO.DATE_TIME)
	private java.util.Date updated_time;
	
	private boolean verify_phone=false;
	private boolean verify_email=false;
	
	@DBRef
	@Indexed
	BillingInformation billingInformation;
	
	@DBRef
	@Indexed
	Identity identity;
	
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
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getAlternative_phone() {
		return alternative_phone;
	}
	public void setAlternative_phone(String alternative_phone) {
		this.alternative_phone = alternative_phone;
	}
	
	public String getFull_name() {
		return full_name;
	}
	public void setFull_name(String full_name) {
		this.full_name = full_name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRaw_password() {
		return raw_password;
	}
	public void setRaw_password(String rawPassword) {
		raw_password = rawPassword;
	}
	public String[] getRoles() {
		return roles;
	}
	public void setRoles(String[] roles) {
		this.roles = roles;
	}
	public String getAccess_key() {
		return access_key;
	}
	public void setAccess_key(String accessKey) {
		access_key = accessKey;
	}
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
	public String getSecret() {
		return secret;
	}
	public void setSecret(String secret) {
		this.secret = secret;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getReferer() {
		return referer;
	}
	public void setReferer(String referer) {
		this.referer = referer;
	}
	public String[] getTags() {
		return tags;
	}
	public void setTags(String[] tags) {
		this.tags = tags;
	}
	public java.util.Date getCreated_time() {
		return created_time;
	}
	public void setCreated_time(java.util.Date createdTime) {
		created_time = createdTime;
	}
	public java.util.Date getUpdated_time() {
		return updated_time;
	}
	public void setUpdated_time(java.util.Date updatedTime) {
		updated_time = updatedTime;
	}
	public boolean getVerify_phone() {
		return verify_phone;
	}
	public void setVerify_phone(boolean verify_phone) {
		this.verify_phone = verify_phone;
	}
	public boolean getVerify_email() {
		return verify_email;
	}
	public void setVerify_email(boolean verify_email) {
		this.verify_email = verify_email;
	}
	public BillingInformation getBillingInformation() {
		return billingInformation;
	}
	public void setBillingInformation(BillingInformation billingInformation) {
		this.billingInformation = billingInformation;
	}
	public Identity getIdentity() {
		return identity;
	}
	public void setIdentity(Identity identity) {
		this.identity = identity;
	}
	public String getHashedPassword(){
//		 hashed_password = md5(raw_password + salt)
		String hashedPassword= Utils.encryptMD5(this.password+ this.salt);
		return hashedPassword;
	}	
	
//	public boolean checkRole(String role){
//		if(this.roles!=null && this.roles.length > 0){
//			return Arrays.asList(this.roles).contains(role);
//		}
//		return false;
//	}
	public boolean checkRoles(String[] roleArr){
		return checkRole(roleArr);
	}
	
	public boolean checkRole(String ... roleArr) {
		if(this.roles==null || this.roles.length == 0
				|| roleArr == null || roleArr.length == 0)
			return false;
		
		List<String> roleList = Arrays.asList(this.roles);
		for(String role : roleArr)
			if(roleList.contains(role))
				return true;
		
		return false;
	}
	
	public void addRole(String role){
		List<String> list = new ArrayList<String>();
		if(this.roles!=null)
			list = Arrays.asList(this.roles);
		if(!list.contains(role)){
			list.add(role);
			this.roles = list.toArray(new String[list.size()]);
		}
	}
	
	/*Merchant & dealer block*/
	public boolean isMerchant(){
		return this.checkRole(Account.ACCOUNT_MERCHANT_ROLE);
	}
	public boolean isMerchantManager(){
		return this.checkRole(Account.ACCOUNT_MERCHANT_MANAGER_ROLE);
	}
	/*Merchant & dealer block*/
	
	public boolean isAdmin() {
		return this.checkRole(Account.ACCOUNT_STAFF_ROLE) 
				&& this.checkRoles(new String[]{Account.ACCOUNT_ADMIN_ROLE, Account.ACCOUNT_OPERATION_MANAGER_ROLE, Account.ACCOUNT_REPORTER_ROLE, Account.ACCOUNT_SHARE_HOLDER_ROLE});
	}
	
	public boolean isStaff() {
		return this.checkRole(Account.ACCOUNT_STAFF_ROLE);
	}
	public boolean isBizStaff() {
		return this.checkRole(Account.ACCOUNT_STAFF_ROLE) && this.checkRole(Account.ACCOUNT_MERCHANT_MANAGER_ROLE);
	}
	public boolean isBizManager(){
		return this.checkRole(Account.ACCOUNT_STAFF_ROLE) && this.checkRole(ACCOUNT_STAFF_MANAGER_ROLE) && this.checkRole(Account.ACCOUNT_MERCHANT_MANAGER_ROLE);
	}
	public boolean isBizSupporter(){
		return this.checkRole(Account.ACCOUNT_STAFF_ROLE) && this.checkRole(ACCOUNT_BIZ_SUPPORTER_ROLE);
	}
	public boolean isBizSupportManager(){
		return this.checkRole(Account.ACCOUNT_STAFF_ROLE) && this.checkRole(ACCOUNT_STAFF_MANAGER_ROLE) && this.checkRole(ACCOUNT_BIZ_SUPPORTER_ROLE);
	}
	public boolean isCustomerCare(){
		return this.checkRole(Account.ACCOUNT_STAFF_ROLE) && this.checkRole(ACCOUNT_CUSTOMER_CARE_ROLE);
	}
	public boolean isCustomerCareManager(){
		return this.checkRole(Account.ACCOUNT_STAFF_ROLE) && this.checkRole(ACCOUNT_STAFF_MANAGER_ROLE) && this.checkRole(ACCOUNT_CUSTOMER_CARE_ROLE);
	}
	public boolean isOperator() {
		return this.checkRole(Account.ACCOUNT_STAFF_ROLE) && this.checkRoles(new String[]{Account.ACCOUNT_ADMIN_ROLE, Account.ACCOUNT_OPERATION_MANAGER_ROLE });
	}
	public boolean isReporter(){
		return this.checkRole(Account.ACCOUNT_STAFF_ROLE) && this.checkRole(Account.ACCOUNT_REPORTER_ROLE); 
	}
	public boolean isShareHolder(){
		return this.checkRole(Account.ACCOUNT_STAFF_ROLE) && this.checkRole(Account.ACCOUNT_SHARE_HOLDER_ROLE); 
	}
	//Đối soát
	public boolean isBookKeeper(){
		return this.checkRole(Account.ACCOUNT_STAFF_ROLE) && this.checkRole(ACCOUNT_BOOK_KEEPER_ROLE);
	}
	public boolean isBilling(){
		return this.checkRole(Account.ACCOUNT_STAFF_ROLE) && this.checkRole(ACCOUNT_BILLING_ROLE);
	}
}
