package vn.onepay.web.validator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import vn.onepay.web.controllers.CaptchaImageGeneratorController;

public class JCaptchaValidator implements Validator{
	public static final String FIELD_MEGAPORTAL_VERIFY_CODE = "verifyCode";
	private static final String JCAPTCHA_ERROR = "account.validate.empty";

	public boolean supports(Class clazz) {
		return String.class.isAssignableFrom(clazz);
	}
	
	@Override
	public void validate(Object target, Errors errors) {
		String verifyCode = (String) target;
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();

		//HttpServletRequest request  = (HttpServletRequest) ActionContext.getContext().get(org.apache.struts2.StrutsStatics.HTTP_REQUEST);
		//Boolean isResponseCorrect = Boolean.FALSE;
		
		//String parm = request.getParameter("j_captcha_response");
		
		try {
			HttpSession session = request.getSession();
			String c= (String)session.getAttribute(CaptchaImageGeneratorController.CAPTCHA_KEY) ;
			if(!verifyCode.equals(c))
				errors.rejectValue(FIELD_MEGAPORTAL_VERIFY_CODE, JCAPTCHA_ERROR, "Mã xác nhận không đúng");
				//errors.rejectValue(FIELD_MEGAPORTAL_VERIFY_CODE, "account.validate.empty", "Hãy nhập chính xác thông tin");
		} catch (Exception e) {
			e.printStackTrace();
			errors.rejectValue(FIELD_MEGAPORTAL_VERIFY_CODE, JCAPTCHA_ERROR);
		}
	}
}
