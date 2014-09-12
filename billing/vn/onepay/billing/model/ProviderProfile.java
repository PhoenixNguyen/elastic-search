package vn.onepay.billing.model;

import java.io.PrintStream;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Document(collection="ProviderProfiles")
@TypeAlias("providerProfile")
public class ProviderProfile
  implements Serializable
{

  @Id
  private String id;
  private String profileCode;
  private String providerCode;
  private String providerName;
  private String providerDesc;
  private String contractNo = "";

  @DateTimeFormat(iso=DateTimeFormat.ISO.DATE_TIME)
  private Date contractDate = new Date();
  private String contractType = "A_B2B";
  private String merchantContracts = null;
  private String paymentPolicy;
  private String tranferDate;
  private int status = 0;
  private int weight = 0;

  @DateTimeFormat(iso=DateTimeFormat.ISO.DATE_TIME)
  private Date enableDate;

  @DateTimeFormat(iso=DateTimeFormat.ISO.DATE_TIME)
  private Date expiredDate;
  private Map<String, Map<String, Map<Long, Double>>> chargingRates;
  private static Pattern pPattern = Pattern.compile("^(\\d+)\\s*?_\\s*n\\s*?_\\s*(\\d+)$", 42);

  private boolean hasVat;
	  
  public boolean isHasVat() {
	  return hasVat;
  }
		
  public void setHasVat(boolean hasVat) {
	this.hasVat = hasVat;
  }
	
  public String getId()
  {
    return this.id;
  }
  public void setId(String id) {
    this.id = id;
  }
  public String getProfileCode() {
    return this.profileCode;
  }
  public void setProfileCode(String profileCode) {
    this.profileCode = profileCode;
  }
  public String getProviderCode() {
    return this.providerCode;
  }
  public void setProviderCode(String providerCode) {
    this.providerCode = providerCode;
  }
  public String getProviderName() {
    return this.providerName;
  }
  public void setProviderName(String providerName) {
    this.providerName = providerName;
  }
  public String getProviderDesc() {
    return this.providerDesc;
  }
  public void setProviderDesc(String providerDesc) {
    this.providerDesc = providerDesc;
  }
  public String getContractNo() {
    return this.contractNo;
  }
  public void setContractNo(String contractNo) {
    this.contractNo = contractNo;
  }
  public Date getContractDate() {
    return this.contractDate;
  }
  public void setContractDate(Date contractDate) {
    this.contractDate = contractDate;
  }
  public String getContractType() {
    return this.contractType;
  }
  public void setContractType(String contractType) {
    this.contractType = contractType;
  }

  public String getMerchantContracts() {
    return this.merchantContracts;
  }
  public void setMerchantContracts(String merchantContracts) {
    this.merchantContracts = merchantContracts;
  }

  public String getPaymentPolicy() {
    return this.paymentPolicy;
  }
  public void setPaymentPolicy(String paymentPolicy) {
    this.paymentPolicy = paymentPolicy;
  }
  public String getTranferDate() {
    return this.tranferDate;
  }
  public void setTranferDate(String tranferDate) {
    this.tranferDate = tranferDate;
  }
  public Map<String, Map<String, Map<Long, Double>>> getChargingRates() {
    return this.chargingRates;
  }
  public void setChargingRates(Map<String, Map<String, Map<Long, Double>>> chargingRates) {
    this.chargingRates = chargingRates;
  }

  public int getStatus() {
    return this.status;
  }
  public void setStatus(int status) {
    this.status = status;
  }

  public int getWeight() {
    return this.weight;
  }
  public void setWeight(int weight) {
    this.weight = weight;
  }

  public Date getEnableDate() {
    return this.enableDate;
  }
  public void setEnableDate(Date enableDate) {
    this.enableDate = enableDate;
  }
  public Date getExpiredDate() {
    return this.expiredDate;
  }
  public void setExpiredDate(Date expiredDate) {
    this.expiredDate = expiredDate;
  }

  private Date getTranferDate(Date date, String sTranferDate) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);

    Matcher matcher = pPattern.matcher(StringUtils.trimToEmpty(sTranferDate));
    if (matcher.find()) {
      int day = Integer.parseInt(matcher.group(1));
      int month = Integer.parseInt(matcher.group(2));
      cal.add(2, month);
      cal.set(5, day);
    }

    return cal.getTime();
  }
  public Date getTranferDate(Date date) {
    Date rs = null;
    for (String sTranferDate : this.tranferDate.split("\\|")) {
      Date temp = getTranferDate(date, sTranferDate);
      if ((!temp.after(date)) || (
        (rs != null) && (!rs.after(temp)))) continue;
      rs = temp;
    }

    return rs;
  }
  public static void main(String[] args) {
    String tranferDate = "17_n_0|4_n_1";
    Matcher matcher = pPattern.matcher(tranferDate);
    if (matcher.find())
      System.out.println(matcher.group(1) + "-" + matcher.group(2));
  }

  public boolean accept(String merchantContract, String merchantPaymentPolicy) {
    try {
      if ((this.paymentPolicy.equalsIgnoreCase(merchantPaymentPolicy)) && (this.merchantContracts.indexOf("|" + merchantContract + "|") != -1))
        return true;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }
}