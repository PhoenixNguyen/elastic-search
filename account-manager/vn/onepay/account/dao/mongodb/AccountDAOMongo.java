package vn.onepay.account.dao.mongodb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import vn.onepay.account.dao.AccountDAO;
import vn.onepay.account.model.Account;
import vn.onepay.cache.dynacache.PassiveDynaCache;
import vn.onepay.cache.dynacache.PassiveDynaHashTableCache;
import vn.onepay.cache.dynacache.common.CacheConstants;
import vn.onepay.utils.Utils;

public class AccountDAOMongo implements AccountDAO{
	MongoTemplate mongoTemplate;
	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}
	private static PassiveDynaCache passiveDynaCache = new PassiveDynaHashTableCache();
	private final static String ALL_MERCHANT_CACHE_KEY = "all_merchant_cache_key";
	//---------------
	
	@Override
	public boolean insert(Account account) {
		// TODO Auto-generated method stub
		try{
			if(account.checkRole(Account.ACCOUNT_MERCHANT_ROLE))
				passiveDynaCache.removeCachedItem(ALL_MERCHANT_CACHE_KEY);
		}catch(Exception e){}
		//Neu ton tai username thi ko insert
		Query query = new Query();
		query.addCriteria(Criteria.where(Account.ACCOUNT_USER_NAME_FIELD_NAME).is(account.getUsername()));
		if(mongoTemplate.findOne(query, Account.class)!=null)
			return false;
		if(account.getCreated_time()!=null)
			account.setCreated_time(Utils.convertToMongoDBTime(account.getCreated_time()));
		if(account.getUpdated_time()!=null)
			account.setUpdated_time(Utils.convertToMongoDBTime(account.getUpdated_time()));
		mongoTemplate.save(account);
		return true;
	}
	
	@Override
	public boolean update(Account account) {
		// TODO Auto-generated method stub
		try{
			if(account.checkRole(Account.ACCOUNT_MERCHANT_ROLE))
				passiveDynaCache.removeCachedItem(ALL_MERCHANT_CACHE_KEY);
		}catch(Exception e){}
		//
		//Neu ton tai account co id = account.id thi update
		Query query = new Query();
		query.addCriteria(Criteria.where(Account.ACCOUNT_ID_FIELD_NAME).is(account.getId()));
		if(mongoTemplate.findOne(query, Account.class)!=null){
			if(account.getCreated_time()!=null)
				account.setCreated_time(Utils.convertToMongoDBTime(account.getCreated_time()));
			if(account.getUpdated_time()!=null)
				account.setUpdated_time(Utils.convertToMongoDBTime(account.getUpdated_time()));
			mongoTemplate.save(account);
			return true;
		}
		return false;
	}
	@Override
	public void update(String username, String field, Object value) {
		// TODO Auto-generated method stub
		try{
			passiveDynaCache.removeCachedItem(ALL_MERCHANT_CACHE_KEY);
		}catch(Exception e){}
		//
		try {
			Query query = new Query();
			query.addCriteria(Criteria.where(Account.ACCOUNT_USER_NAME_FIELD_NAME).is(StringUtils.trimToEmpty(username)));
			Update update =  new Update();
			update.set(field, value);
			mongoTemplate.updateFirst(query, update, Account.class);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	@Override
	public void delete(Account account) {
		// TODO Auto-generated method stub
		try{
			if(account.checkRole(Account.ACCOUNT_MERCHANT_ROLE))
				passiveDynaCache.removeCachedItem(ALL_MERCHANT_CACHE_KEY);
		}catch(Exception e){}
		//
		mongoTemplate.remove(account);
	}
	
	//-------
	@Override
	public Account find(String username) {
		// TODO Auto-generated method stub
		Query query = new Query();
		query.addCriteria(Criteria.where(Account.ACCOUNT_USER_NAME_FIELD_NAME).is(StringUtils.trimToEmpty(username)));
		Account account = mongoTemplate.findOne(query, Account.class);
		if(account!=null){
			if(account.getCreated_time()!=null)
				account.setCreated_time(Utils.mongoDbTimeToDisplayTime(account.getCreated_time()));
			if(account.getUpdated_time()!=null)
				account.setUpdated_time(Utils.mongoDbTimeToDisplayTime(account.getUpdated_time()));
		}
		return account;
	}
	
	@Override
	public Account findByEmail(String email) {
		// TODO Auto-generated method stub
		Query query = new Query();
		query.addCriteria(Criteria.where(Account.ACCOUNT_EMAIL_FIELD_NAME).is(StringUtils.trimToEmpty(email)));
		Account account = mongoTemplate.findOne(query, Account.class);
		if(account!=null){
			if(account.getCreated_time()!=null)
				account.setCreated_time(Utils.mongoDbTimeToDisplayTime(account.getCreated_time()));
			if(account.getUpdated_time()!=null)
				account.setUpdated_time(Utils.mongoDbTimeToDisplayTime(account.getUpdated_time()));
		}
		return account;
	}
	
	@Override
	public List<Account> findByEmail(String email, int offset, int limit) {
		// TODO Auto-generated method stub
		Query query = new Query();
		query.addCriteria(Criteria.where(Account.ACCOUNT_EMAIL_FIELD_NAME).is(StringUtils.trimToEmpty(email)));
		query.with(new Sort(Sort.Direction.DESC, Account.ACCOUNT_CREATED_TIME_FIELD_NAME));
		if(offset>0)
			query.skip(offset);
		if(limit>0)
			query.limit(limit);
		List<Account> accounts = mongoTemplate.find(query, Account.class);
		if(accounts!=null && accounts.size()>0){
			for(Account account:accounts){
				if(account.getCreated_time()!=null)
					account.setCreated_time(Utils.mongoDbTimeToDisplayTime(account.getCreated_time()));
				if(account.getUpdated_time()!=null)
					account.setUpdated_time(Utils.mongoDbTimeToDisplayTime(account.getUpdated_time()));
			}
		}
		return accounts;
	}
	@Override
	public Account findByPhone(String phone) {
		// TODO Auto-generated method stub
		phone = Utils.getFormatedMsisdn(phone);
		Query query = new Query();
		query.addCriteria(new Criteria().orOperator(Criteria.where(Account.ACCOUNT_PHONE_FIELD_NAME).is(phone), Criteria.where(Account.ACCOUNT_ALTERNATIVE_PHONE_FIELD_NAME).is(phone)));
		Account account = mongoTemplate.findOne(query, Account.class);
		if(account!=null){
			if(account.getCreated_time()!=null)
				account.setCreated_time(Utils.mongoDbTimeToDisplayTime(account.getCreated_time()));
			if(account.getUpdated_time()!=null)
				account.setUpdated_time(Utils.mongoDbTimeToDisplayTime(account.getUpdated_time()));
		}
		return account;
	}
	@Override
	public List<Account> findByPhone(String phone, int offset, int limit){
		// TODO Auto-generated method stub
		phone = Utils.getFormatedMsisdn(phone);
		Query query = new Query();
		query.addCriteria(new Criteria().orOperator(Criteria.where(Account.ACCOUNT_PHONE_FIELD_NAME).is(phone), Criteria.where(Account.ACCOUNT_ALTERNATIVE_PHONE_FIELD_NAME).is(phone)));
		query.with(new Sort(Sort.Direction.DESC, Account.ACCOUNT_CREATED_TIME_FIELD_NAME));
		if(offset>0)
			query.skip(offset);
		if(limit>0)
			query.limit(limit);
		List<Account> accounts = mongoTemplate.find(query, Account.class);
		if(accounts!=null && accounts.size()>0){
			for(Account account:accounts){
				if(account.getCreated_time()!=null)
					account.setCreated_time(Utils.mongoDbTimeToDisplayTime(account.getCreated_time()));
				if(account.getUpdated_time()!=null)
					account.setUpdated_time(Utils.mongoDbTimeToDisplayTime(account.getUpdated_time()));
			}
		}
		return accounts;
	}
	@Override
	public Account find(String username, String password) {
		// TODO Auto-generated method stub
		Query query = new Query();
		query.addCriteria(Criteria.where(Account.ACCOUNT_USER_NAME_FIELD_NAME).is(StringUtils.trimToEmpty(username)));
		query.addCriteria(Criteria.where(Account.ACCOUNT_PASSWORD_FIELD_NAME).is(StringUtils.trimToEmpty(password)));
		Account account = mongoTemplate.findOne(query, Account.class);
		if(account!=null){
			if(account.getCreated_time()!=null)
				account.setCreated_time(Utils.mongoDbTimeToDisplayTime(account.getCreated_time()));
			if(account.getUpdated_time()!=null)
				account.setUpdated_time(Utils.mongoDbTimeToDisplayTime(account.getUpdated_time()));
		}
		return account;
	}
	@Override
	public List<Account> findByRole(String aRole) {
		// TODO Auto-generated method stub
		try{
			Query query = new Query();
			query.addCriteria(Criteria.where(Account.ACCOUNT_ROLES_FIELD_NAME).is(StringUtils.trimToEmpty(aRole)));
			query.with(new Sort(Sort.Direction.ASC, Account.ACCOUNT_USER_NAME_FIELD_NAME));
			List<Account> accounts = mongoTemplate.find(query, Account.class);
			for(Account account:accounts){
				if(account.getCreated_time()!=null)
					account.setCreated_time(Utils.mongoDbTimeToDisplayTime(account.getCreated_time()));
				if(account.getUpdated_time()!=null)
					account.setUpdated_time(Utils.mongoDbTimeToDisplayTime(account.getUpdated_time()));
			}
			return accounts;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Account> findByRole(String[] roles) {
		// TODO Auto-generated method stub
		try{
			Query query = new Query();
			query.addCriteria(Criteria.where(Account.ACCOUNT_ROLES_FIELD_NAME).in(Arrays.asList(roles)));
			query.with(new Sort(Sort.Direction.ASC, Account.ACCOUNT_USER_NAME_FIELD_NAME));
			List<Account> accounts = mongoTemplate.find(query, Account.class);
			if(accounts!=null && accounts.size() > 0){
				for(Account account:accounts){
					if(account.getCreated_time()!=null)
						account.setCreated_time(Utils.mongoDbTimeToDisplayTime(account.getCreated_time()));
					if(account.getUpdated_time()!=null)
						account.setUpdated_time(Utils.mongoDbTimeToDisplayTime(account.getUpdated_time()));
				}
			}
			return accounts;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public List<String> findAllMerchants() {
		// TODO Auto-generated method stub
		List<String> rs = null;
		try{
			if(passiveDynaCache.containsKey(ALL_MERCHANT_CACHE_KEY)){
				rs = (List<String>) passiveDynaCache.getCachedItem(ALL_MERCHANT_CACHE_KEY);
				if(rs!=null)
					return rs;
			}
			rs = findMerchants(null, null, 0, 0);
			if(rs!=null)
				passiveDynaCache.setCachedItem(ALL_MERCHANT_CACHE_KEY, rs, CacheConstants.MEMCACHED_TIMEOUT_10_MINS);
		}catch(Exception e){
			e.printStackTrace();
		}
		return rs;
	}
	//---
	@Override
	public long findTotalMerchants(Date fromTime, Date toTime){
		// TODO Auto-generated method stub
		try{
			String userName = null;
			String fullName = null;
			String referer = null;
			List<String> owners = null;
			List<String> roles = Arrays.asList(new String[]{Account.ACCOUNT_MERCHANT_ROLE});
			List<String> tags = null;
			return findTotalAccounts(userName, fullName, referer, owners, roles, tags, fromTime, toTime);
		}catch(Exception e){
			e.printStackTrace();
		}
		return 0;
	}
	@Override
	public List<String> findMerchants(Date fromTime, Date toTime, int offset, int limit) {
		// TODO Auto-generated method stub
		try{
			String userName = null;
			String fullName = null;
			String referer = null;
			List<String> owners = null;
			List<String> roles = Arrays.asList(new String[]{Account.ACCOUNT_MERCHANT_ROLE});
			List<String> tags = null;
			List<Account> accounts = findAccounts(userName, fullName, referer, owners, roles, tags, fromTime, toTime, offset, limit);
			if(accounts!=null){
				List<String> rs = new ArrayList<String>();
				for(Account account:accounts){
					rs.add(account.getUsername());
				}
				java.util.Collections.sort(rs);
				return rs;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public List<Account> findByOwner(String owner, List<String> roles) {
		// TODO Auto-generated method stub
		try{
			Query query = new Query();
			query.addCriteria(Criteria.where(Account.ACCOUNT_OWNER_FIELD_NAME).is(owner));
			if(roles!=null && roles.size()>0){
				if(roles.size()>1)
					query.addCriteria(Criteria.where(Account.ACCOUNT_ROLES_FIELD_NAME).in(roles));
				else
					query.addCriteria(Criteria.where(Account.ACCOUNT_ROLES_FIELD_NAME).is(roles.get(0)));
			}
			query.with(new Sort(Sort.Direction.ASC, Account.ACCOUNT_USER_NAME_FIELD_NAME));
//			System.out.println("findByOwner: "+query.toString());
			List<Account> accounts = mongoTemplate.find(query, Account.class);
			return accounts;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public void findByOwner(List<Account> rs, String owner, List<String> roles, boolean isRecursive, List<String> ownerRolesToRecursive){
		// TODO Auto-generated method stub
		List<Account> accounts = findByOwner(owner, roles);
		if(accounts!=null && accounts.size()>0){
			rs.addAll(accounts);
			if(isRecursive){
				for(Account account: accounts){
					if(ownerRolesToRecursive!=null && account.checkRoles(ownerRolesToRecursive.toArray(new String[0])))
						findByOwner(rs, account.getUsername(), roles, isRecursive, ownerRolesToRecursive);
				}
			}
		}
	}
	//-----
	@Override
	public List<Account> findByTag(String tag) {
		// TODO Auto-generated method stub
		try{
			Query query = new Query();
			query.addCriteria(Criteria.where(Account.ACCOUNT_TAGS_FIELD_NAME).is(StringUtils.trimToEmpty(tag)));
			query.with(new Sort(Sort.Direction.ASC, Account.ACCOUNT_USER_NAME_FIELD_NAME));
//			System.out.println(query.toString());
			List<Account> accounts = mongoTemplate.find(query, Account.class);
			for(Account account:accounts){
				if(account.getCreated_time()!=null)
					account.setCreated_time(Utils.mongoDbTimeToDisplayTime(account.getCreated_time()));
				if(account.getUpdated_time()!=null)
					account.setUpdated_time(Utils.mongoDbTimeToDisplayTime(account.getUpdated_time()));
			}
			return accounts;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public Account findOwner(String username) {
		// TODO Auto-generated method stub
		Account account = find(username);
		if(account!=null && StringUtils.isNotEmpty(account.getOwner()))
			return find(account.getOwner());
		return null;
	}
	
	//-------
	@Override
	public List<Account> findAllAccount() {
		// TODO Auto-generated method stub
		try{
			Query query = new Query();
			query.with(new Sort(Sort.Direction.ASC, Account.ACCOUNT_USER_NAME_FIELD_NAME));
			
//			System.out.println(query.toString());
			List<Account> accounts = mongoTemplate.find(query, Account.class);
			for(Account account:accounts){
				if(account.getCreated_time()!=null)
					account.setCreated_time(Utils.mongoDbTimeToDisplayTime(account.getCreated_time()));
				if(account.getUpdated_time()!=null)
					account.setUpdated_time(Utils.mongoDbTimeToDisplayTime(account.getUpdated_time()));
			}
			return accounts;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public int findTotalAccounts(String userName, String fullName,
			String referer, List<String> owners, List<String> roles,
			List<String> tags, Date fromTime, Date toTime) {
		// TODO Auto-generated method stub
		try{
			Query query = getQuery(userName, fullName, referer, owners, roles, tags, fromTime, toTime);
			if(query!=null){
				return (int) mongoTemplate.count(query, Account.class);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return 0;
	}
	@Override
	public List<Account> findAccounts(String userName, String fullName,
			String referer, List<String> owners, List<String> roles,
			List<String> tags, Date fromTime, Date toTime, int offset, int limit) {
		// TODO Auto-generated method stub
		try{
			Query query = getQuery(userName, fullName, referer, owners, roles, tags, fromTime, toTime);
			query.with(new Sort(Sort.Direction.DESC, Account.ACCOUNT_CREATED_TIME_FIELD_NAME));
			query.skip(offset);
			if(limit > 0)
				query.limit(limit);
			List<Account> accounts = mongoTemplate.find(query, Account.class);
			if(accounts!=null && accounts.size()>0){
				for(Account account:accounts){
					if(account.getCreated_time()!=null)
						account.setCreated_time(Utils.mongoDbTimeToDisplayTime(account.getCreated_time()));
					if(account.getUpdated_time()!=null)
						account.setUpdated_time(Utils.mongoDbTimeToDisplayTime(account.getUpdated_time()));
				}
			}
			return accounts;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	private Query getQuery(String userName, String fullName, String referer, List<String> owners, List<String> roles, List<String> tags, Date fromTime, Date toTime){
		Query query = new Query();
		List<Criteria> searchCriterias = new ArrayList<Criteria>();
		if(StringUtils.isNotEmpty(userName))
			searchCriterias.add(Criteria.where(Account.ACCOUNT_USER_NAME_FIELD_NAME).is(StringUtils.trimToEmpty(userName)));
		if(StringUtils.isNotEmpty(fullName))
			searchCriterias.add(Criteria.where(Account.ACCOUNT_FULL_NAME_FIELD_NAME).regex(Pattern.compile("*"+fullName+"*", Pattern.DOTALL | Pattern.CASE_INSENSITIVE)));
		if(StringUtils.isNotEmpty(referer))
			searchCriterias.add(Criteria.where(Account.ACCOUNT_REFERER_FIELD_NAME).regex(Pattern.compile("*"+referer+"*", Pattern.DOTALL | Pattern.CASE_INSENSITIVE)));
		if(owners!=null && owners.size()>0){
			if(owners.size()>1)
				searchCriterias.add(Criteria.where(Account.ACCOUNT_OWNER_FIELD_NAME).in(owners));
			else
				searchCriterias.add(Criteria.where(Account.ACCOUNT_OWNER_FIELD_NAME).is(owners.get(0)));
		}
		if(roles!=null && roles.size()>0){
			if(roles.size()>1)
				searchCriterias.add(Criteria.where(Account.ACCOUNT_ROLES_FIELD_NAME).in(roles));
			else
				searchCriterias.add(Criteria.where(Account.ACCOUNT_ROLES_FIELD_NAME).is(roles.get(0)));
		}
		if(tags!=null && tags.size()>0){
			if(tags.size()>1)
				searchCriterias.add(Criteria.where(Account.ACCOUNT_TAGS_FIELD_NAME).in(tags));
			else
				searchCriterias.add(Criteria.where(Account.ACCOUNT_TAGS_FIELD_NAME).is(tags.get(0)));
		}
		if(fromTime!=null)
			searchCriterias.add(Criteria.where(Account.ACCOUNT_CREATED_TIME_FIELD_NAME).gte(Utils.convertToMongoDBTime(fromTime)));
		if(toTime!=null)
			searchCriterias.add(Criteria.where(Account.ACCOUNT_CREATED_TIME_FIELD_NAME).lte(Utils.convertToMongoDBTime(toTime)));
		if(searchCriterias!=null && searchCriterias.size()>0){
			if(searchCriterias.size()>1)
				query.addCriteria(new Criteria().andOperator(searchCriterias.toArray(new Criteria[0])));
			else
				query.addCriteria(searchCriterias.get(0));
		}
		System.out.println(query);
		return query;
	}
	
}
