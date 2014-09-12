package vn.onepay.account.model;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Document(collection = "UserMessages")
@TypeAlias("userMessages")
public class AccountMessage implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8144482516692467853L;

	public static final String ACCOUNT_MESSAGE_COLLECTION_NAME="UserMessages";
	
	public static final String ACCOUNT_MESSAGE_ID_FIELD_NAME="_id";
	public static final String ACCOUNT_USER_NAME_FIELD_NAME="username";
	public static final String ACCOUNT_FROM_USER_NAME_FIELD_NAME="from_username";
	public static final String ACCOUNT_MESSAGE_TITLE_FIELD_NAME="title";
	public static final String ACCOUNT_MESSAGE_CONTENT_FIELD_NAME="content";
	public static final String ACCOUNT_MESSAGE_TYPE_FIELD_NAME="type";
	public static final String ACCOUNT_MESSAGE_READ_FIELD_NAME="read";
	public static final String ACCOUNT_MESSAGE_TIME_NAME="created_time";

	public static final String ACCOUNT_MESSAGE_TYPE_ALERT = "alert";
	public static final String ACCOUNT_MESSAGE_TYPE_MESSAGE = "message";
	public static final String ACCOUNT_MESSAGE_TYPE_HISTORY = "history";
	
	@Id
	private String id;
	private String username;
	private String fromUsername;
	private String title;
	private String content;
	private String type;
	private boolean read;
	@DateTimeFormat(iso=ISO.DATE_TIME)
	private java.util.Date created_time;
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
	public String getFromUsername() {
		return fromUsername;
	}
	public void setFromUsername(String fromUsername) {
		this.fromUsername = fromUsername;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public boolean isRead() {
		return read;
	}
	public void setRead(boolean read) {
		this.read = read;
	}
	public java.util.Date getCreated_time() {
		return created_time;
	}
	public void setCreated_time(java.util.Date created_time) {
		this.created_time = created_time;
	}
	
	
}
