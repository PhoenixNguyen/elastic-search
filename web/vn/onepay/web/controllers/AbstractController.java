package vn.onepay.web.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jasig.cas.client.util.AbstractCasFilter;
import org.jasig.cas.client.validation.Assertion;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import vn.onepay.account.dao.AccountDAO;
import vn.onepay.account.model.Account;
import vn.onepay.common.SharedConstants;
import vn.onepay.utils.AES;
import vn.onepay.utils.CookieUtil;

public abstract class AbstractController implements Controller {
	protected AccountDAO accountDAO;
	protected String folderLayout = "";
	private String webView;
	
	public void setAccountDAO(AccountDAO accountDAO) {
		this.accountDAO = accountDAO;
	}

	public String getWebView() {
		return this.folderLayout + this.webView;
	}
	
	public void setWebView(String webView) {
		this.webView = webView;
	}
	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		ModelMap model = new ModelMap();
		return handleRequest(request, response, model);
	}
	protected abstract ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception;
	
	public boolean checkLogin(HttpServletRequest request, HttpServletResponse response) {
		Account account = (Account) request.getSession().getAttribute(SharedConstants.ACCOUNT_LOGINED);
		if(account!=null)
			return true;
		String userName=null;
		Assertion assertion = (Assertion) request.getSession().getAttribute(AbstractCasFilter.CONST_CAS_ASSERTION);
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
			if(account!=null)
				return true;
		}
		return false;
	}
}
