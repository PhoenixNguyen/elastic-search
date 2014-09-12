package vn.onepay.web.secure.controllers;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.jasig.cas.client.util.AbstractCasFilter;
import org.jasig.cas.client.validation.Assertion;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import vn.onepay.account.dao.AccountDAO;
import vn.onepay.account.model.Account;
import vn.onepay.common.SharedConstants;
import vn.onepay.utils.AES;
import vn.onepay.utils.CookieUtil;

@SuppressWarnings("deprecation")
public abstract class AbstractProtectedFormController extends SimpleFormController {
	protected AccountDAO accountDAO;
	
	protected String folderLayout ="";
	public AccountDAO getAccountDAO() {
		return accountDAO;
	}
	public void setAccountDAO(AccountDAO accountDAO) {
		this.accountDAO = accountDAO;
	}
	@Override
	protected void initBinder(HttpServletRequest request,
			ServletRequestDataBinder binder) throws Exception {
		// TODO Auto-generated method stub
		//
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
		System.out.println("FORM VIEW: " + this.getFormView());
		super.initBinder(request, binder);
	}
	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		//
		if(!checkLogin(request,response)) {
			String backUrl = request.getRequestURL().toString();
			String ver = request.getParameter("ver");
			if(StringUtils.isEmpty(ver)){
				ver = (StringUtils.isNotEmpty(this.folderLayout) && this.folderLayout.indexOf("english")!=-1)?"en":"vi";
				backUrl+= (backUrl.indexOf("?")!=-1?"&":"?");
				backUrl+=("ver="+ver);
			}
			return new ModelAndView("redirect:/login.html?back_url="+URLEncoder.encode(backUrl,"UTF-8")+"&ver="+URLEncoder.encode(ver,"UTF-8"));
		}
		return super.handleRequest(request, response);
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
	
	protected List<String> findMyOwnMerchants(Account account){
		List<Account> rs = new ArrayList<Account>();
    	if(account.checkRoles(new String[]{Account.ACCOUNT_ADMIN_ROLE, Account.ACCOUNT_OPERATION_MANAGER_ROLE, Account.ACCOUNT_BIZ_SUPPORTER_ROLE, Account.ACCOUNT_REPORTER_ROLE, Account.ACCOUNT_SHARE_HOLDER_ROLE, Account.ACCOUNT_CUSTOMER_CARE_ROLE})){
			rs =  accountDAO.findByRole(Account.ACCOUNT_MERCHANT_ROLE);
		}else{
	    	if(account.checkRole(Account.ACCOUNT_MERCHANT_ROLE))
	    		rs.add(account);
	    	if(account.checkRole(Account.ACCOUNT_MERCHANT_MANAGER_ROLE))
	    		accountDAO.findByOwner(rs, account.getUsername(), Arrays.asList(new String[]{Account.ACCOUNT_MERCHANT_MANAGER_ROLE, Account.ACCOUNT_MERCHANT_ROLE}), true, Arrays.asList(new String[]{Account.ACCOUNT_MERCHANT_MANAGER_ROLE}));
		}
		if(rs!=null && rs.size() > 0){
			List<String> myMerchants = new ArrayList<String>();
			for(Account acc: rs){
				if(acc.checkRole(Account.ACCOUNT_MERCHANT_ROLE))
					myMerchants.add(acc.getUsername());
			}
			if(myMerchants!=null && myMerchants.size() > 0){
				Set<String> set = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
		        set.addAll(myMerchants);
		        return new ArrayList<String>(set);
			}
		}
		return null;
	}
}
