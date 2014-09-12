package vn.onepay.web.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import vn.onepay.web.models.LoginForm;

public class LoginValidator implements Validator {

	@Override
	public boolean supports(Class<?> _class) {
		return LoginForm.class.equals(_class);
	}

	@Override
	public void validate(Object obj, Errors errs) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errs, "userName", "account.oldpassword.empty", "Chưa nhập tài khoản.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errs, "password", "account.newpassword.empty", "Chưa nhập mật khẩu.");
	}

}
