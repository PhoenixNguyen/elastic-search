package vn.onepay.web.secure.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

public class HomeController extends AbstractProtectedController {

	@Override
	protected ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) throws Exception {
		return new ModelAndView(getWebView(), "model", model);
	}
}
