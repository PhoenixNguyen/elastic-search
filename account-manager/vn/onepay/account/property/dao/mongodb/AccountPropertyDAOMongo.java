package vn.onepay.account.property.dao.mongodb;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import vn.onepay.account.model.AccountProperty;
import vn.onepay.account.property.dao.AccountPropertyDAO;
import vn.onepay.utils.Utils;

public class AccountPropertyDAOMongo implements AccountPropertyDAO{
	MongoTemplate mongoTemplate;
	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}
	//---------------
	
	@Override
	public boolean save(AccountProperty accountProperty) {
		// TODO Auto-generated method stub
		try{
			if(accountProperty.getCreated_time()!=null)
				accountProperty.setCreated_time(Utils.convertToMongoDBTime(accountProperty.getCreated_time()));
			mongoTemplate.save(accountProperty);
			return true;
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public void remove(AccountProperty accountProfile) {
		// TODO Auto-generated method stub
		mongoTemplate.remove(accountProfile);
	}
	
	@Override
	public List<AccountProperty> findAllAccountProperties() {
		try{
			Query query = new Query();
			query.with(new Sort(Sort.Direction.ASC, AccountProperty.USER_NAME_FIELD_NAME));
			List<AccountProperty> accountProperties = mongoTemplate.find(query, AccountProperty.class);
			for(AccountProperty accountProperty:accountProperties){
				if(accountProperty.getCreated_time()!=null)
					accountProperty.setCreated_time(Utils.mongoDbTimeToDisplayTime(accountProperty.getCreated_time()));
			}
			return accountProperties;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public List<AccountProperty> getAccountProperties(String username) {
		// TODO Auto-generated method stub
		Query query = new Query();
		query.addCriteria(Criteria.where(AccountProperty.USER_NAME_FIELD_NAME).is(StringUtils.trimToEmpty(username)));
		return mongoTemplate.find(query, AccountProperty.class);
	}
	
	@Override
	public AccountProperty getAccountProperty(String username,
			String accountProfileParam) {
		// TODO Auto-generated method stub
		Query query = new Query();
		query.addCriteria(Criteria.where(AccountProperty.USER_NAME_FIELD_NAME).is(StringUtils.trimToEmpty(username)));
		query.addCriteria(Criteria.where(AccountProperty.PARAM_FIELD_NAME).is(StringUtils.trimToEmpty(accountProfileParam)));
		return mongoTemplate.findOne(query, AccountProperty.class);
	}
}
