package vn.onepay.card.dao.mongodb;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.GroupCommand;
import com.mongodb.util.JSON;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;
import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import vn.onepay.account.model.Account;
import vn.onepay.card.dao.CardCdrDAO;
import vn.onepay.card.model.CardCdr;
import vn.onepay.common.SharedConstants;
import vn.onepay.utils.Utils;

public class CardCdrDAOMongo
  implements CardCdrDAO
{
  private MongoTemplate mongoTemplate;
  private String mongoCollection;

  public void setMongoTemplate(MongoTemplate mongoTemplate)
  {
    this.mongoTemplate = mongoTemplate;
  }

  public void setMongoCollection(String mongoCollection)
  {
    this.mongoCollection = mongoCollection;
  }

  public List<String> findAllCardTypes()
  {
    String[] types = { "mobifone", "vinaphone", "viettel", "gate" };
    return Arrays.asList(types);
  }

  public List<String> findAllCardStatus()
  {
    String[] status = { "00", "02", "13" };
    return Arrays.asList(status);
  }

  public List<CardCdr> findCardCdr(Account account, List<String> restrictedMerchants, String pin, String serial, String status, List<Integer> amounts, List<String> cardTypes, String searchMerchant, List<String> merchants, List<String> providers, Date fromTime, Date toTime, int offset, int limit)
  {
    Query query = getQuery(account, restrictedMerchants, pin, serial, status, amounts, cardTypes, searchMerchant, merchants, providers, fromTime, toTime);
    if (query == null)
      return null;
    Sort orderBy = new Sort(Sort.Direction.DESC, new String[] { "timestamp" });
    query.with(orderBy);
    if (offset > 0)
      query.skip(offset);
    if (limit > 0) {
      query.limit(limit);
    }
    List<CardCdr> cdrs = this.mongoTemplate.find(query, CardCdr.class);
    if ((cdrs != null) && (cdrs.size() > 0)) {
      for (CardCdr cardCdr : cdrs) {
        cardCdr.setTimestamp(Utils.mongoDbTimeToDisplayTime(cardCdr.getTimestamp()));
      }
    }
    return cdrs;
  }

  public Double[] countAndAmountCdr(Account account, List<String> restrictedMerchants, String pin, String serial, String status, List<Integer> amounts, List<String> cardTypes, String searchMerchant, List<String> merchants, List<String> providers, Date fromTime, Date toTime)
  {
    if (account == null)
      return null;
    if (account.checkRole("staff"));
    boolean isAdmin = account.checkRoles(new String[] { "admin", "operation_manager", "biz_supporter", "reporter", "share_holder", "customer_care" });

    Double count = Double.valueOf(0.0D);
    Double amount = Double.valueOf(0.0D);
    DBCollection collection = this.mongoTemplate.getCollection(this.mongoCollection);
    Query query = getQuery(account, restrictedMerchants, pin, serial, status, amounts, cardTypes, searchMerchant, merchants, providers, fromTime, toTime);
    if (query == null)
      return new Double[] { count, amount };
    try
    {
      BasicDBObject initial = new BasicDBObject("count", Integer.valueOf(0));
      initial.put("amount", Integer.valueOf(0));
      GroupCommand cmd = new GroupCommand(collection, 
        null, 
        query.getQueryObject(), 
        initial, 
        "function(obj,prev) {prev.count++; prev.amount += obj.amount;}", 
        null);
      DBObject obj = collection.group(cmd);
      JSONObject jsonObject = null;
      jsonObject = new JSONObject(JSON.serialize(obj.toMap()));
      System.out.println("RESULT:" + jsonObject.toString());
      count = Double.valueOf(((JSONObject)jsonObject.get("0")).getDouble("count"));
      amount = Double.valueOf(((JSONObject)jsonObject.get("0")).getDouble("amount"));

      if ((isAdmin) && (SharedConstants.MBIZ_RATE > 0.0D) && (SharedConstants.MBIZ_RATE < 1.0D)) {
        count = Double.valueOf(count.doubleValue() * SharedConstants.MBIZ_RATE);
        amount = Double.valueOf(amount.doubleValue() * SharedConstants.MBIZ_RATE);
      }
    }
    catch (Exception localException) {
    }
    return new Double[] { count, amount };
  }

  private Query getQuery(Account account, List<String> restrictedMerchants, String pin, String serial, String status, List<Integer> amounts, List<String> cardTypes, String searchMerchant, List<String> merchants, List<String> providers, Date fromTime, Date toTime)
  {
    if (account == null)
      return null;
    if (!account.isAdmin()) if (!account.isStaff()); 
    boolean isAdmin = account.checkRoles(new String[] { "biz_supporter", "customer_care" });
    Query query = new Query();

    Criteria searchMerchantCriteria = null;
    if (StringUtils.isNotEmpty(searchMerchant))
    {
      String[] arrayOfString = null;
      int j = 0;
      int i = 0;
      if ((isAdmin) && (SharedConstants.MBIZ) && (SharedConstants.MBIZ_MERCHANTS != null) && (SharedConstants.MBIZ_MERCHANTS.length > 0)) {
        j = (arrayOfString = SharedConstants.MBIZ_MERCHANTS).length; i = 0; 
        } 
      while (true) 
      { 
    	  String mbizMerchant = arrayOfString[i];
        if (mbizMerchant.indexOf(searchMerchant) != -1) {
          searchMerchantCriteria = Criteria.where("merchant").regex(searchMerchant, "i");
        }
        else
        {
          i++; 
          if (i < j) 
        	  continue; 
          break;

          //searchMerchantCriteria = Criteria.where("merchant").regex(searchMerchant, "i");
        }
      }
    }
    if (!isAdmin) {
      if ((restrictedMerchants == null) || (restrictedMerchants.isEmpty()))
        return null;
      List filterMerchants = new ArrayList();
      if ((merchants != null) && (merchants.size() > 0)) {
        for (String merchant : merchants) {
          if (restrictedMerchants.contains(merchant))
            filterMerchants.add(merchant);
        }
        if (filterMerchants.size() == 0)
          return null;
      }
      if (filterMerchants.size() > 0) {
        if (filterMerchants.size() > 1) {
          if (searchMerchantCriteria != null)
            searchMerchantCriteria = searchMerchantCriteria.andOperator(new Criteria[] { Criteria.where("merchant").in(filterMerchants) });
          else
            searchMerchantCriteria = Criteria.where("merchant").in(filterMerchants);
        }
        else if (searchMerchantCriteria != null)
          searchMerchantCriteria = searchMerchantCriteria.andOperator(new Criteria[] { Criteria.where("merchant").is(filterMerchants.get(0)) });
        else {
          searchMerchantCriteria = Criteria.where("merchant").is(filterMerchants.get(0));
        }
      }
      else if (restrictedMerchants.size() > 1) {
        if (searchMerchantCriteria != null)
          searchMerchantCriteria = searchMerchantCriteria.andOperator(new Criteria[] { Criteria.where("merchant").in(restrictedMerchants) });
        else
          searchMerchantCriteria = Criteria.where("merchant").in(restrictedMerchants);
      }
      else if (searchMerchantCriteria != null)
        searchMerchantCriteria = searchMerchantCriteria.andOperator(new Criteria[] { Criteria.where("merchant").is(restrictedMerchants.get(0)) });
      else {
        searchMerchantCriteria = Criteria.where("merchant").is(restrictedMerchants.get(0));
      }

    }
    else if ((merchants != null) && (merchants.size() > 0)) {
      if (merchants.size() > 1) {
        if (searchMerchantCriteria != null)
          searchMerchantCriteria = searchMerchantCriteria.andOperator(new Criteria[] { Criteria.where("merchant").in(merchants) });
        else
          searchMerchantCriteria = Criteria.where("merchant").in(merchants);
      }
      else if (searchMerchantCriteria != null)
        searchMerchantCriteria = searchMerchantCriteria.andOperator(new Criteria[] { Criteria.where("merchant").is(merchants.get(0)) });
      else {
        searchMerchantCriteria = Criteria.where("merchant").is(merchants.get(0));
      }
    }

    if (searchMerchantCriteria != null) {
      if ((isAdmin) && (SharedConstants.MBIZ) && (SharedConstants.MBIZ_MERCHANTS != null) && (SharedConstants.MBIZ_MERCHANTS.length > 0) && 
        (!account.checkRole("customer_care")) && (!account.checkRole("biz_supporter")))
      {
        searchMerchantCriteria = new Criteria().andOperator(new Criteria[] { searchMerchantCriteria, Criteria.where("merchant").in(Arrays.asList(SharedConstants.MBIZ_MERCHANTS)) });
      }

      query.addCriteria(searchMerchantCriteria);
    }

    if (StringUtils.isNotEmpty(pin)) {
      query.addCriteria(Criteria.where("pin").regex(Pattern.compile(pin, 34)));
    }
    if (StringUtils.isNotEmpty(serial)) {
      query.addCriteria(Criteria.where("serial").regex(Pattern.compile(serial, 34)));
    }
    if (StringUtils.isNotEmpty(status)) {
      query.addCriteria(Criteria.where("status").is(status));
    }
    if ((amounts != null) && (amounts.size() > 0)) {
      if (amounts.size() > 1)
        query.addCriteria(Criteria.where("amount").in(amounts));
      else
        query.addCriteria(Criteria.where("amount").is(amounts.get(0)));
    }
    if ((cardTypes != null) && (cardTypes.size() > 0)) {
      if (cardTypes.size() > 1)
        query.addCriteria(Criteria.where("type").in(cardTypes));
      else
        query.addCriteria(Criteria.where("type").is(cardTypes.get(0)));
    }
    if ((providers != null) && (providers.size() > 0)) {
      if (providers.size() > 1)
        query.addCriteria(Criteria.where("paymentProvider").in(providers));
      else {
        query.addCriteria(Criteria.where("paymentProvider").is(providers.get(0)));
      }
    }
    if ((fromTime != null) || (toTime != null)) {
      if (fromTime == null)
        fromTime = new Date(0L);
      if (toTime == null)
        toTime = new Date();
      if (fromTime.getTime() <= toTime.getTime()) {
        Criteria dateCriteria = Criteria.where("timestamp").gte(Utils.convertToMongoDBTime(fromTime)).andOperator(new Criteria[] { Criteria.where("timestamp").lte(Utils.convertToMongoDBTime(toTime)) });
        query.addCriteria(dateCriteria);
      }
    }

    System.out.println(query.toString());
    return query;
  }
}