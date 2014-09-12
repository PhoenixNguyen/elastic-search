package vn.onepay.card.model;

import com.mongodb.DBObject;
import com.mongodb.util.JSON;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONObject;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

@SuppressWarnings("serial")
@Document(collection="CardCharging.CDR")
@TypeAlias("card")
public class CardCdr
  implements Serializable
{

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

  private static DateFormat isoDateTimeFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

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

  public static CardCdr dbObjectToCdr(DBObject obj)
  {
    CardCdr cdr = null;
    try {
      JSONObject jsonObject = new JSONObject(JSON.serialize(obj));
      cdr = new CardCdr();
      cdr.setId(((JSONObject)jsonObject.get("_id")).get("$oid").toString());
      try {
        if (jsonObject.has("amount"))
          cdr.setAmount(jsonObject.getInt("amount")); 
      } catch (Exception localException1) {
      }
      try {
        if (jsonObject.has("merchant"))
          cdr.setMerchant(jsonObject.getString("merchant")); 
      } catch (Exception localException2) {
      }
      try {
        if (jsonObject.has("paymentProvider"))
          cdr.setPaymentProvider(jsonObject.getString("paymentProvider")); 
      } catch (Exception localException3) {
      }
      try {
        if (jsonObject.has("app_code"))
          cdr.setApp_code(jsonObject.getString("app_code")); 
      } catch (Exception localException4) {
      }
      try {
        if (jsonObject.has("pin"))
          cdr.setPin(jsonObject.getString("pin")); 
      } catch (Exception localException5) {
      }
      try {
        if (jsonObject.has("serial"))
          cdr.setSerial(jsonObject.getString("serial")); 
      } catch (Exception localException6) {
      }
      try {
        if (jsonObject.has("type"))
          cdr.setType(jsonObject.getString("type")); 
      } catch (Exception localException7) {
      }
      try {
        if (jsonObject.has("status"))
          cdr.setStatus(jsonObject.getString("status")); 
      } catch (Exception localException8) {
      }
      try {
        if (jsonObject.has("message"))
          cdr.setMessage(jsonObject.get("message").toString()); 
      } catch (Exception localException9) {
      }
      try {
        if (jsonObject.has("timestamp"))
        {
          Date cardTime = isoDateTimeFormat.parse(((JSONObject)jsonObject.get("timestamp")).getString("$date"));
          cdr.setTimestamp(cardTime);
        }
      } catch (Exception localException10) {
      }
      try {
        if (jsonObject.has("extractStatus"))
          cdr.setExtractStatus(jsonObject.getBoolean("extractStatus")); 
      } catch (Exception localException11) {
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return cdr;
  }
}