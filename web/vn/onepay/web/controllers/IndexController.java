package vn.onepay.web.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jasig.cas.client.util.AbstractCasFilter;
import org.jasig.cas.client.validation.Assertion;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import vn.onepay.account.model.Account;
import vn.onepay.common.SharedConstants;

public class IndexController extends AbstractController {

	@Override
	protected ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) throws Exception {
		Account account = (Account) request.getSession().getAttribute(
				SharedConstants.ACCOUNT_LOGINED);
		if (account == null) {
			Assertion assertion = (Assertion) request.getSession()
					.getAttribute(AbstractCasFilter.CONST_CAS_ASSERTION);
			if (assertion != null) {
				String userName = assertion.getPrincipal().getName();
				account = accountDAO.find(userName);
			}
		}
		if (account != null) {
			return new ModelAndView("forward:/protected/home.html");
		} else {
			return new ModelAndView(getWebView(), "model", model);
		}
	}
}
