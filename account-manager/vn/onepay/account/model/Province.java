package vn.onepay.account.model;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Document(collection = "Provinces")
@TypeAlias("province")
public class Province implements Serializable{
	private static final long serialVersionUID = -8774007953288820683L;
	
	public final static String PROVINCE_ID_FIELD_NAME="_id";
	public final static String PROVINCE_CODE_FIELD_NAME="code";
	public final static String PROVINCE_NAME_FIELD_NAME="name";
	public final static String PROVINCE_STATUS_FIELD_NAME="status";
	
	@Id
	private String id;
	private String code;
	private String name;
	private int status;
	@DateTimeFormat(iso=ISO.DATE_TIME)
	private java.util.Date created_time;
	@DateTimeFormat(iso=ISO.DATE_TIME)
	private java.util.Date updated_time;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	
}
