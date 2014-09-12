package vn.onepay.web.controllers;

import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.jasig.cas.client.util.AbstractCasFilter;
import org.jasig.cas.client.validation.Assertion;
import org.jboss.aerogear.security.otp.Totp;
import org.jboss.aerogear.security.otp.api.Clock;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import vn.onepay.account.model.Account;
import vn.onepay.common.SharedConstants;
import vn.onepay.utils.AES;
import vn.onepay.utils.CookieUtil;
import vn.onepay.web.models.OTPAuthenticationForm;


public class OTPAuthenticationController extends AbstractFormController {
	@Override
	protected Object formBackingObject(HttpServletRequest request)
			throws Exception {
		// TODO Auto-generated method stub
		OTPAuthenticationForm otpAuthenticationForm  = new OTPAuthenticationForm();
		
		Account account = (Account) request.getSession().getAttribute(SharedConstants.ACCOUNT_LOGINED);
		Assertion assertion = (Assertion) request.getSession().getAttribute(AbstractCasFilter.CONST_CAS_ASSERTION);
		if(account!=null || assertion!=null){
			if(account==null){
				String userName=null;
				if(assertion!=null){
					userName = assertion.getPrincipal().getName();
				}else{
					String authTicket = CookieUtil.getCookieValue(request, SharedConstants.AUTH_COOKIE_TICKET_NAME);
					if(authTicket!=null){
						try{
							userName = AES.decrypt(SharedConstants.AUTH_TICKET_ENCRIPT_CODE.getBytes(), authTicket);
						}catch(Exception e){
							userName = null;
						}
					}
				}
				if(userName!=null){
					if(account ==null){
						account = accountDAO.find(userName);
						if(account!=null){
							request.getSession().setAttribute(SharedConstants.ACCOUNT_LOGINED,account);
						}
					}
				}
			}
		}
		if(account!=null){
			Object verifiedOTP = request.getSession().getAttribute(SharedConstants.ACCOUNT_VERIFIED_OTP);
			if(verifiedOTP == null){
				Totp totp = (Totp) request.getSession().getAttribute(SharedConstants.ACCOUNT_OTP_AUTH_OBJECT); 
				if(totp ==null){
					String key = StringUtils.trimToEmpty(account.getSecret().replaceAll("\\d+", ""));
					totp = new Totp(key,new Clock(120));
					request.getSession().setAttribute(SharedConstants.ACCOUNT_OTP_AUTH_OBJECT, totp);
				}
				String otp = totp.now();
				final String OTP_SESSION_KEY = "current_otp_value";
				String currentOTPSession = (String) request.getSession().getAttribute(OTP_SESSION_KEY);
				if(!otp.equalsIgnoreCase(StringUtils.trimToEmpty(currentOTPSession))){
					String mobileNumber = account.getPhone();
					if (StringUtils.isNotEmpty(mobileNumber)) {
						mobileNumber = mobileNumber.replaceAll("\\D+", "")
								.trim();
						if (mobileNumber.startsWith("0"))
							mobileNumber = "84" + mobileNumber.substring(1);
						try{
							System.out.println("Ma xac thuc OTP de dang nhap: "+otp);
						}catch(Exception e){}
						
					}
					//
					request.getSession().setAttribute(OTP_SESSION_KEY, otp);
				}
			}
		}
		//
		otpAuthenticationForm.setBackUrl(StringUtils.trimToEmpty(request.getParameter("back_url")));
		return otpAuthenticationForm;
	}
	@Override
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		// TODO Auto-generated method stub
		try {
			String userName= null;
			Account account = (Account) request.getSession().getAttribute(SharedConstants.ACCOUNT_LOGINED);
			if(account!=null){
				userName = account.getUsername();
			}
			Assertion assertion = (Assertion) request.getSession().getAttribute(AbstractCasFilter.CONST_CAS_ASSERTION);
			if(assertion!=null){
				userName=assertion.getPrincipal().getName();
			}
			if(userName!=null){
				Totp totp = (Totp) request.getSession().getAttribute(SharedConstants.ACCOUNT_OTP_AUTH_OBJECT); 
				if(totp ==null){
					String key = StringUtils.trimToEmpty(account.getSecret().replaceAll("\\d+", ""));
					totp = new Totp(key,new Clock(120));
					request.getSession().setAttribute(SharedConstants.ACCOUNT_OTP_AUTH_OBJECT, totp);
				}
				OTPAuthenticationForm otpAuthenticationForm = (OTPAuthenticationForm) command;
				boolean verified = totp.verify(otpAuthenticationForm.getOtp());
				if(verified){
					request.getSession().setAttribute(SharedConstants.ACCOUNT_VERIFIED_OTP, true);
					String backUrl = otpAuthenticationForm.getBackUrl();
					if(StringUtils.isNotEmpty(backUrl))
						return new ModelAndView("redirect:"+backUrl);
					else	
						return new ModelAndView("redirect:/protected/profile.html");
				}else{
					errors.reject("account.otp.authenticate.failure", "Mã xác thực không chính xác!");
				}
			}
			return showForm(request, errors, getFormView());
		} catch (Exception e) {
			e.printStackTrace();
			errors.reject("account.otp.authenticate.failure", "Mã xác thực không chính xác!");
			return showForm(request, errors, getFormView());
		}
	}
	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		Account account = (Account) request.getSession().getAttribute(SharedConstants.ACCOUNT_LOGINED);
		Assertion assertion = (Assertion) request.getSession().getAttribute(AbstractCasFilter.CONST_CAS_ASSERTION);
		if(account==null && assertion ==null){
			String backUrl = request.getParameter("back_url");
			String ver = request.getParameter("ver");
			if(StringUtils.isEmpty(ver)){
				ver = (StringUtils.isNotEmpty(this.folderLayout) && this.folderLayout.indexOf("english")!=-1)?"en":"vi";
			}
			if(backUrl!=null)
				return new ModelAndView("forward:/login.html?back_url="+URLEncoder.encode(request.getRequestURL().toString(),"UTF-8")+"&ver="+ver);
			else
				return new ModelAndView("forward:/login.html?ver="+ver);
			
		}
		return super.handleRequest(request, response);
	}
}
