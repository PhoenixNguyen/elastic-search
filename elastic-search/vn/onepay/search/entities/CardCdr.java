package vn.onepay.search.entities;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.format.annotation.DateTimeFormat;

@Document(indexName = "cardcdrs", type = "cardcdr", shards = 1, replicas = 0, refreshInterval = "-1", indexStoreType = "fs")
public class CardCdr{

  @Id
  private String id;
  
  private int amount;
  private String merchant = "1pay";
  private String paymentProvider;
  private String app_code;
  private String pin;
  private String serial;
  private String type;
  private String status;
  private String message;

  @DateTimeFormat(iso=DateTimeFormat.ISO.DATE_TIME)
  private Date timestamp;
  private boolean extractStatus = false;

  public CardCdr(){
	  
  }
  public CardCdr(String id, int amount, String merchant, String paymentProvider,
			String app_code, String pin, String serial, String type, String status,
			String message, Date timestamp, boolean extractStatus) {
		super();
		this.id = id;
		this.amount = amount;
		this.merchant = merchant;
		this.paymentProvider = paymentProvider;
		this.app_code = app_code;
		this.pin = pin;
		this.serial = serial;
		this.type = type;
		this.status = status;
		this.message = message;
		this.timestamp = timestamp;
		this.extractStatus = extractStatus;
	}
  
  public String getId()
  {
    return this.id;
  }
  public void setId(String id) {
    this.id = id;
  }
  public int getAmount() {
    return this.amount;
  }
  public void setAmount(int amount) {
    this.amount = amount;
  }
  public String getMerchant() {
    return this.merchant;
  }
  public void setMerchant(String merchant) {
    this.merchant = merchant;
  }

  public String getPaymentProvider() {
    return this.paymentProvider;
  }
  public void setPaymentProvider(String paymentProvider) {
    this.paymentProvider = paymentProvider;
  }

  public String getApp_code() {
    return this.app_code;
  }
  public void setApp_code(String app_code) {
    this.app_code = app_code;
  }

  public String getPin() {
    return this.pin;
  }
  public void setPin(String pin) {
    this.pin = pin;
  }
  public String getSerial() {
    return this.serial;
  }
  public void setSerial(String serial) {
    this.serial = serial;
  }

  public String getType() {
    return this.type;
  }
  public void setType(String type) {
    this.type = type;
  }
  public String getStatus() {
    return this.status;
  }
  public void setStatus(String status) {
    this.status = status;
  }

  public String getMessage() {
    return this.message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public Date getTimestamp() {
    return this.timestamp;
  }

  public void setTimestamp(Date timestamp) {
    this.timestamp = timestamp;
  }

  public boolean getExtractStatus() {
    return this.extractStatus;
  }
  public void setExtractStatus(boolean extractStatus) {
    this.extractStatus = extractStatus;
  }

  
}