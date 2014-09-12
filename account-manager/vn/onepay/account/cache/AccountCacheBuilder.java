package vn.onepay.account.cache;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.lang.StringUtils;

import vn.onepay.account.dao.AccountDAO;
import vn.onepay.account.model.Account;
import vn.onepay.common.SharedConstants;
import vn.onepay.service.ServiceFinder;

public class AccountCacheBuilder implements ServletContextListener, Runnable{
	private AccountDAO accountDAO;
	private static boolean running = false;
	private long sleepTime;
	public static long lastModified = System.currentTimeMillis();
	
	public static Map<String,List<String>> MAP_DEPARTMENT_MERCHANTS = new LinkedHashMap<String, List<String>>();
	public static Map<String,List<Account>> MAP_DEPARTMENT_STAFF = new LinkedHashMap<String,List<Account>>();
	public static Map<String,List<Account>> MAP_DEPARTMENT_MERCHANT_MANAGERS = new LinkedHashMap<String,List<Account>>();
	public static Map<String,Account> MAP_DEPARTMENT_CHEF_MANAGERS = new LinkedHashMap<String,Account>();
	
	public void contextDestroyed(ServletContextEvent event) {
		running = false;
	}

	public void contextInitialized(ServletContextEvent event) {
		sleepTime = Integer.parseInt(event.getServletContext().getInitParameter("account-cache-interval"));
		accountDAO = ServiceFinder.getContext(event.getServletContext()).getBean(AccountDAO.BEAN_NAME, AccountDAO.class);
		try {
			running = false;
			new Thread(this).start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void run() {
		while (true) {
			buildCache();
			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	public synchronized void buildCache() {
		if(running)
			return;
		running=true;
		try {
			if(MAP_DEPARTMENT_MERCHANTS == null)
				MAP_DEPARTMENT_MERCHANTS = new LinkedHashMap<String, List<String>>();
			if(MAP_DEPARTMENT_STAFF ==null)
				MAP_DEPARTMENT_STAFF = new LinkedHashMap<String, List<Account>>();
			if(MAP_DEPARTMENT_CHEF_MANAGERS==null)
				MAP_DEPARTMENT_CHEF_MANAGERS = new LinkedHashMap<String, Account>();
			if(MAP_DEPARTMENT_MERCHANT_MANAGERS==null)
				MAP_DEPARTMENT_MERCHANT_MANAGERS = new LinkedHashMap<String, List<Account>>();
			//------
			for(String department: SharedConstants.DEPARTMENTS){
				List<String> merchants = findMerchantsOfDepartment(department);
				if(merchants!=null && merchants.size()>0){
					MAP_DEPARTMENT_MERCHANTS.put(department, merchants);
				}
				List<Account> staffs = findDepartmentStaff(department);
				if(staffs!=null && staffs.size() >0){
					MAP_DEPARTMENT_STAFF.put(department, staffs);
					List<Account> merchantManagers = new ArrayList<Account>();
					for(Account staff:staffs){
						if(staff.checkRole(Account.ACCOUNT_MERCHANT_MANAGER_ROLE)){
							merchantManagers.add(staff);
							if(StringUtils.isEmpty(staff.getOwner()))
								MAP_DEPARTMENT_CHEF_MANAGERS.put(department, staff);
						}
					}
					MAP_DEPARTMENT_MERCHANT_MANAGERS.put(department, merchantManagers);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			running=false;
		}
	}
	//----
	private synchronized List<String> findMerchantsOfDepartment(String department){
    	List<Account> accounts = accountDAO.findByTag(department);
    	if(accounts!=null && accounts.size() > 0){
    		List<Account> rs = new ArrayList<Account>();
    		for(Account acc : accounts){
    			if(acc.checkRole(Account.ACCOUNT_MERCHANT_ROLE))
    				rs.add(acc);
    			if(acc.checkRole(Account.ACCOUNT_MERCHANT_MANAGER_ROLE))
    				accountDAO.findByOwner(rs, acc.getUsername(), Arrays.asList(new String[]{Account.ACCOUNT_MERCHANT_MANAGER_ROLE, Account.ACCOUNT_MERCHANT_ROLE}), true, Arrays.asList(new String[]{Account.ACCOUNT_MERCHANT_MANAGER_ROLE}));
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
    		        List<String> unSortList =new ArrayList<String>(set);
    		        Collections.sort(unSortList);
    		        return unSortList;
    			}
    		}
    	}
    	return null;
    }
	
	private synchronized List<Account> findDepartmentStaff(String department) {
		// TODO Auto-generated method stub
		List<Account> accounts = accountDAO.findByTag(department);
    	if(accounts!=null && accounts.size() > 0){
    		List<Account> rs = new ArrayList<Account>();
    		for(Account acc : accounts){
    			if(acc.isStaff()){
    				rs.add(acc);
    				accountDAO.findByOwner(rs, acc.getUsername(), Arrays.asList(new String[]{Account.ACCOUNT_STAFF_ROLE}), true, Arrays.asList(new String[]{Account.ACCOUNT_STAFF_ROLE}));
    			}
    		}
    		if(rs!=null && rs.size() > 0){
    			List<Account> results = new ArrayList<Account>();
    			Map<String,Boolean> tmpMap = new HashMap<String, Boolean>();
    			for(Account acc: rs){
    				if(acc.isStaff() && !tmpMap.containsKey(acc.getUsername())){
    					results.add(acc);
    					tmpMap.put(acc.getUsername(), true);
    				}
    			}
    			return results;
    		}
    	}
		return null;
	}
	
}
