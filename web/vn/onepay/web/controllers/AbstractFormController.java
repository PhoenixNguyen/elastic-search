package vn.onepay.web.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.jasig.cas.client.util.AbstractCasFilter;
import org.jasig.cas.client.validation.Assertion;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.mvc.SimpleFormController;

import vn.onepay.account.dao.AccountDAO;
import vn.onepay.account.model.Account;
import vn.onepay.common.SharedConstants;
import vn.onepay.utils.AES;
import vn.onepay.utils.CookieUtil;

@SuppressWarnings("deprecation")
public abstract class AbstractFormController extends SimpleFormController {
	protected AccountDAO accountDAO;
	
	public void setAccountDAO(AccountDAO accountDAO) {
		this.accountDAO = accountDAO;
	}
	protected String folderLayout = "";
	@Override
	protected void initBinder(HttpServletRequest request,
			ServletRequestDataBinder binder) throws Exception {
		// TODO Auto-generated method stub
		if(StringUtils.isNotEmpty(this.folderLayout)){
			String formView = this.getFormView();
			if(StringUtils.isNotEmpty(formView) && !formView.startsWith(this.folderLayout)){
				formView = this.folderLayout + formView;
				this.setFormView(formView);
			}
			String successView = this.getSuccessView();
			if(StringUtils.isNotEmpty(successView) && !successView.startsWith(this.folderLayout)){
				successView = this.folderLayout + successView;
			}
		}else{
			String formView = this.getFormView();
			int index = formView.lastIndexOf("/");
			if(index!=-1){
				formView = formView.substring(index);
				this.setFormView(formView.replace("/", ""));
			}
		}
		System.out.println("FORM VIEW:" + this.getFormView());
		super.initBinder(request, binder);
	}

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