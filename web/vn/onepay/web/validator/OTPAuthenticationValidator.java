package vn.onepay.web.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import vn.onepay.web.models.OTPAuthenticationForm;

public class OTPAuthenticationValidator implements Validator {

	@Override
	public boolean supports(Class<?> _class) {
		return OTPAuthenticationForm.class.equals(_class);
	}

	@Override
	public void validate(Object obj, Errors errs) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errs, "otp", "account.otp.empty", "Chưa nhập mã xác thực!");
	}

}
