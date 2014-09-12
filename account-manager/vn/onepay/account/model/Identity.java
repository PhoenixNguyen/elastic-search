package vn.onepay.account.model;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Document(collection = "Identity")
@TypeAlias("identity")
public class Identity  implements Serializable{
	private static final long serialVersionUID = -8125015838998699851L;
	public final static String FIELD_NAME_ID="_id";
	public final static String FIELD_NAME_USERNAME="username";
	public final static String FIELD_NAME_IDENTITY="identity";
	public final static String FIELD_NAME_ADDRESS="address";
	public final static String FIELD_NAME_IDENTITY_IMAGE="identityImage";
	public final static String FIELD_NAME_STATUS="status";
	public final static String FIELD_NAME_VERIFY="verify";
	public final static String FIELD_NAME_VERIFY_BY="verify_by";
	
	public final static int INIT_STATUS = 0;
	public final static int MERCHANT_VERIFY_STATUS = 2;
	public final static int MERCHANT_REJECT_STATUS = 1;
	public final static int ONEPAY_VERIFY_STATUS = 4;
	public final static int ONEPAY_REJECT_STATUS = 3;
	
	@Id
	private String id;
	@Indexed(unique=true)
	private String username;
	private String identity;
	private String identityImage;
	private String identityImage2;
	@DateTimeFormat(iso=ISO.DATE_TIME)
	private java.util.Date dateOfIssue;
	private String dateOfIssueFmt;
	private String placeOfIssue;
	
	private int status = INIT_STATUS;
	@DateTimeFormat(iso=ISO.DATE_TIME)
	private java.util.Date created_time;
	@DateTimeFormat(iso=ISO.DATE_TIME)
	private java.util.Date updated_time;
	@DateTimeFormat(iso=ISO.DATE_TIME)
	private java.util.Date verify_time;

	private String verify_by;
	
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
	public String getIdentity() {
		return identity;
	}
	public void setIdentity(String identity) {
		this.identity = identity;
	}
	public String getIdentityImage() {
		return identityImage;
	}
	public void setIdentityImage(String identityImage) {
		this.identityImage = identityImage;
	}
	public String getIdentityImage2() {
		return identityImage2;
	}
	public void setIdentityImage2(String identityImage2) {
		this.identityImage2 = identityImage2;
	}
	public String getPlaceOfIssue() {
		return placeOfIssue;
	}
	public void setPlaceOfIssue(String placeOfIssue) {
		this.placeOfIssue = placeOfIssue;
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
	public java.util.Date getVerify_time() {
		return verify_time;
	}
	public void setVerify_time(java.util.Date verify_time) {
		this.verify_time = verify_time;
	}
	public boolean getVerify() {
		boolean verify= (status == ONEPAY_VERIFY_STATUS)?true:false;
		return verify;
	}
	
	public void setVerify(boolean verify) {
		this.status = verify? ONEPAY_VERIFY_STATUS : INIT_STATUS ;
	}
	public String getVerify_by() {
		return verify_by;
	}
	public void setVerify_by(String verify_by) {
		this.verify_by = verify_by;
	}
	public java.util.Date getDateOfIssue() {
		return dateOfIssue;
	}
	public void setDateOfIssue(java.util.Date dateOfIssue) {
		this.dateOfIssue = dateOfIssue;
	}
	public String getDateOfIssueFmt() {
		if(this.dateOfIssue!=null){
			final DateFormat dtFormat = new SimpleDateFormat("dd/MM/yyyy");
			this.dateOfIssueFmt = dtFormat.format(this.dateOfIssue);
		}
		return dateOfIssueFmt;
	}
}
