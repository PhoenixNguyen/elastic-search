package vn.onepay.web.models;

import java.io.Serializable;

@SuppressWarnings("serial")
public class OTPAuthenticationForm implements Serializable {
	private String otp;
	private String backUrl;
	
	public void setOtp(String otp) {
		this.otp = otp;
	}
	public String getOtp() {
		return otp;
	}
	public String getBackUrl() {
		return backUrl;
	}
	public void setBackUrl(String backUrl) {
		this.backUrl = backUrl;
	}
}
