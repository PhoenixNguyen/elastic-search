package vn.onepay.service;


import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class ServiceFinder {
	private static ApplicationContext ctx = null;

	/**
	 * Allows test cases to override where application context obtained from.
	 * 
	 * @param httpRequest
	 *            which can be used to find the <code>ServletContext</code>
	 * 
	 * @return the Spring application context
	 */
	public static ApplicationContext getContext(HttpServletRequest httpRequest) {
		return ctx =  WebApplicationContextUtils
				.getRequiredWebApplicationContext(httpRequest.getSession()
						.getServletContext());
	}

	public static ApplicationContext getContext(ServletContext context) {
		return ctx = WebApplicationContextUtils
				.getRequiredWebApplicationContext(context);
	}

	public static ApplicationContext getContext() {
		System.out.println("ApplicationContext ="+ctx);
		return ctx;
	}
}