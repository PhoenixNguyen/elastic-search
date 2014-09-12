package vn.onepay.account.dao.mongodb;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import vn.onepay.account.dao.IdentityDAO;
import vn.onepay.account.model.Account;
import vn.onepay.account.model.Identity;
import vn.onepay.utils.Utils;

public class IdentityDAOMongo implements IdentityDAO{
	private MongoTemplate mongoTemplate;
	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}
	//-----------------------------------
	@Override
	public boolean save(Account account, Identity identity) {
		if(account==null) return false;
		boolean isStaff = (account.checkRole(Account.ACCOUNT_STAFF_ROLE)&& account.checkRoles(new String[]{Account.ACCOUNT_OPERATION_MANAGER_ROLE, Account.ACCOUNT_MERCHANT_MANAGER_ROLE, Account.ACCOUNT_BIZ_SUPPORTER_ROLE}));
		if(isStaff || account.getUsername().equalsIgnoreCase(identity.getUsername())){
			try{
				if(identity.getCreated_time()!=null)
					identity.setCreated_time(Utils.convertToMongoDBTime(identity.getCreated_time()));
				if(identity.getUpdated_time()!=null)
					identity.setUpdated_time(Utils.convertToMongoDBTime(identity.getUpdated_time()));
				if(identity.getVerify_time()!=null)
					identity.setVerify_time(Utils.convertToMongoDBTime(identity.getVerify_time()));
				if(identity.getDateOfIssue()!=null)
					identity.setDateOfIssue(Utils.convertToMongoDBTime(identity.getDateOfIssue()));
				mongoTemplate.save(identity);
				return true;
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return false;
	}

	@Override
	public Identity findById(String id) {
		Query query = new Query();
		query.addCriteria(Criteria.where("id").is(StringUtils.trimToEmpty(id)));
		Identity identity =  mongoTemplate.findOne(query, Identity.class);
		if(identity!=null){
			identity.setCreated_time(Utils.mongoDbTimeToDisplayTime(identity.getCreated_time()));
		}
		return identity;
	}

	@Override
	public Identity findByMerchant(String username) {
		Query query = new Query();
		query.addCriteria(Criteria.where("username").is(StringUtils.trimToEmpty(username)));
		Identity identity =  mongoTemplate.findOne(query, Identity.class);
		if(identity!=null){
			if(identity.getCreated_time()!=null)
				identity.setCreated_time(Utils.mongoDbTimeToDisplayTime(identity.getCreated_time()));
			if(identity.getUpdated_time()!=null)
				identity.setUpdated_time(Utils.mongoDbTimeToDisplayTime(identity.getUpdated_time()));
			if(identity.getVerify_time()!=null)
				identity.setVerify_time(Utils.mongoDbTimeToDisplayTime(identity.getVerify_time()));
			if(identity.getDateOfIssue()!=null)
				identity.setDateOfIssue(Utils.mongoDbTimeToDisplayTime(identity.getDateOfIssue()));
		}
		return identity;
	}
}
