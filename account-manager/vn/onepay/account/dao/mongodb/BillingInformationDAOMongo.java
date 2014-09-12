package vn.onepay.account.dao.mongodb;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import vn.onepay.account.dao.BillingInformationDAO;
import vn.onepay.account.model.Account;
import vn.onepay.account.model.BillingInformation;
import vn.onepay.utils.Utils;

public class BillingInformationDAOMongo implements BillingInformationDAO{
	private MongoTemplate mongoTemplate;
	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}
	//---------
	@Override
	public boolean save(Account account, BillingInformation billingInformation) {
		if(account==null) return false;
		boolean isStaff = (account.checkRole(Account.ACCOUNT_STAFF_ROLE)&& account.checkRoles(new String[]{Account.ACCOUNT_OPERATION_MANAGER_ROLE, Account.ACCOUNT_MERCHANT_MANAGER_ROLE, Account.ACCOUNT_BIZ_SUPPORTER_ROLE}));
		if(isStaff || account.getUsername().equalsIgnoreCase(billingInformation.getUsername())){
			try{
				if(billingInformation.getCreated_time()!=null)
					billingInformation.setCreated_time(Utils.convertToMongoDBTime(billingInformation.getCreated_time()));
				if(billingInformation.getUpdated_time()!=null)
					billingInformation.setUpdated_time(Utils.convertToMongoDBTime(billingInformation.getUpdated_time()));
				mongoTemplate.save(billingInformation);
				return true;
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return false;
	}
	
	@Override
	public List<BillingInformation> findAll() {
		try{
			Query query = new Query();
			query.with(new Sort(Sort.Direction.ASC, BillingInformation.BILLINGINFO_USERNAME_FIELD_NAME));
			List<BillingInformation> billInfos = mongoTemplate.find(query, BillingInformation.class);
			for(BillingInformation bill:billInfos){
				bill.setCreated_time(Utils.mongoDbTimeToDisplayTime(bill.getCreated_time()));
				bill.setUpdated_time(Utils.mongoDbTimeToDisplayTime(bill.getUpdated_time()));
			}
			return billInfos;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public BillingInformation findById(String id) {
		Query query = new Query();
		query.addCriteria(Criteria.where("id").is(StringUtils.trimToEmpty(id)));
		BillingInformation billingInformation =  mongoTemplate.findOne(query, BillingInformation.class);
		if(billingInformation!=null){
			if(billingInformation.getCreated_time()!=null)
				billingInformation.setCreated_time(Utils.mongoDbTimeToDisplayTime(billingInformation.getCreated_time()));
			if(billingInformation.getUpdated_time()!=null)
				billingInformation.setUpdated_time(Utils.mongoDbTimeToDisplayTime(billingInformation.getUpdated_time()));
		}
		return billingInformation;
	}

	@Override
	public BillingInformation findByMerchant(String username) {
		Query query = new Query();
		query.addCriteria(Criteria.where("username").is(StringUtils.trimToEmpty(username)));
		BillingInformation billingInformation =  mongoTemplate.findOne(query, BillingInformation.class);
		if(billingInformation!=null){
			if(billingInformation.getCreated_time()!=null)
				billingInformation.setCreated_time(Utils.mongoDbTimeToDisplayTime(billingInformation.getCreated_time()));
			//billingInformation.setModified_time(Utils.mongoDbTimeToDisplayTime(billingInformation.getModified_time()));
		}
		return billingInformation;
	}

	@Override
	public void update(String username, String field, Object value) {
		try{
			Query query = new Query();
			query.addCriteria(Criteria.where(BillingInformation.BILLINGINFO_USERNAME_FIELD_NAME).is(username));
			Update update =  new Update();
			update.set(field, value);
			mongoTemplate.updateFirst(query, update, BillingInformation.class);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
