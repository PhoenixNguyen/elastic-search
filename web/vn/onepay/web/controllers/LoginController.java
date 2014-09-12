package vn.onepay.web.controllers;

import java.net.URLEncoder;
import java.rmi.RemoteException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jettison.json.JSONException;
import org.jasig.cas.client.util.AbstractCasFilter;
import org.jasig.cas.client.validation.Assertion;
import org.json.JSONObject;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import vn.onepay.account.model.Account;
import vn.onepay.common.SharedConstants;
import vn.onepay.utils.AES;
import vn.onepay.utils.CookieUtil;
import vn.onepay.utils.Utils;
import vn.onepay.web.models.LoginForm;

@SuppressWarnings("deprecation")
public class LoginController extends AbstractFormController {

	@Override
	protected Object formBackingObject(HttpServletRequest request)
			throws Exception {
		LoginForm form = new LoginForm();
		return form;
	}

	@Override
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		try {
			LoginForm loginForm = (LoginForm) command;
			Account account = accountDAO.find(StringUtils.trimToEmpty(loginForm.getUserName()));
			if(account == null) {
				errors.reject("account.notexist", "Tài khoản không tồn tại.");
				return showForm(request, errors, getFormView());
			}else{
				String hashedPwd=Utils.encryptMD5(StringUtils.trimToEmpty(loginForm.getPassword())+account.getSalt());
				if(!hashedPwd.equals(account.getPassword())){
					errors.reject("account.notexist", "Sai mật khẩu.");
					return showForm(request, errors, getFormView());
				}
				request.getSession().setAttribute(SharedConstants.ACCOUNT_LOGINED,account);
				try{
					String ticketValue = AES.encrypt(SharedConstants.AUTH_TICKET_ENCRIPT_CODE.getBytes(), loginForm.getUserName());
					CookieUtil.setCookieValue(response, SharedConstants.AUTH_COOKIE_TICKET_NAME, ticketValue, 10000);
				}catch(Exception e){
					e.printStackTrace();
				}
				if(account!=null){
					String backUrl = request.getParameter("back_url");
					String ver = request.getParameter("ver");
					if(StringUtils.isEmpty(ver)){
						ver = (StringUtils.isNotEmpty(this.folderLayout) && this.folderLayout.indexOf("english")!=-1)?"en":"vi";
					}
					if("ON".equalsIgnoreCase(SharedConstants.OTP_AUTH_ON_OFF_STATUS)){
						//---
//						if(account.checkRoles(new String[]{Account.ACCOUNT_STAFF_ROLE})){
							Object verifiedOTP = request.getSession().getAttribute(SharedConstants.ACCOUNT_VERIFIED_OTP);
							if(verifiedOTP ==null){
								if(backUrl!=null)
									return new ModelAndView("forward:/otp-auth.html?back_url="+backUrl+"&ver="+ver);
								else
									return new ModelAndView("forward:/otp-auth.html?back_url="+URLEncoder.encode(request.getRequestURL().toString(),"UTF-8")+"&ver="+ver);
							}
//						}
						//---
					}
					if(backUrl!=null)
						return new ModelAndView("redirect:"+backUrl);
					return new ModelAndView("redirect:/");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return showForm(request, errors, getFormView());
	}
	
	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Account account = (Account) request.getSession().getAttribute(SharedConstants.ACCOUNT_LOGINED);
		Assertion assertion = (Assertion) request.getSession().getAttribute(AbstractCasFilter.CONST_CAS_ASSERTION);
		if(account!=null || assertion!=null){
			if(account==null){
				String userName=null;
				if(assertion!=null){
					userName = assertion.getPrincipal().getName();
					try{
						String ticketValue = AES.encrypt(SharedConstants.AUTH_TICKET_ENCRIPT_CODE.getBytes(), userName);
						CookieUtil.setCookieValue(response, SharedConstants.AUTH_COOKIE_TICKET_NAME, ticketValue, 10000);
					}catch(Exception e){
						e.printStackTrace();
					}
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
							try{
								String ticketValue = AES.encrypt(SharedConstants.AUTH_TICKET_ENCRIPT_CODE.getBytes(), userName);
								CookieUtil.setCookieValue(response, SharedConstants.AUTH_COOKIE_TICKET_NAME, ticketValue, 10000);
							}catch(Exception e){
								e.printStackTrace();
							}
						}
					}
				}
			}
		}
		if(account!=null){
			String backUrl = request.getParameter("back_url");
			String ver = request.getParameter("ver");
			if(StringUtils.isEmpty(ver)){
				ver = (StringUtils.isNotEmpty(this.folderLayout) && this.folderLayout.indexOf("english")!=-1)?"en":"vi";
			}
			if("ON".equalsIgnoreCase(SharedConstants.OTP_AUTH_ON_OFF_STATUS)){
				//---
//				if(account.checkRoles(new String[]{Account.ACCOUNT_STAFF_ROLE})){
					Object verifiedOTP = request.getSession().getAttribute(SharedConstants.ACCOUNT_VERIFIED_OTP);
					if(verifiedOTP ==null){
						if(backUrl!=null)
							return new ModelAndView("forward:/otp-auth.html?back_url="+backUrl+"&ver="+ver);
						else
							return new ModelAndView("forward:/otp-auth.html?back_url="+URLEncoder.encode(request.getRequestURL().toString(),"UTF-8")+"&ver="+ver);
					}
//				}
				//---
			}
			if(backUrl!=null)
				return new ModelAndView("redirect:"+backUrl);
			return new ModelAndView("redirect:/");
		}
		return super.handleRequest(request, response);
	}
	
}
