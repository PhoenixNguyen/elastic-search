package vn.onepay.web.secure.controllers;

import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import vn.onepay.account.model.Account;
import vn.onepay.billing.model.ProviderProfile;
import vn.onepay.billing.service.ProviderProfileManager;
import vn.onepay.card.dao.CardCdrDAO;
import vn.onepay.card.model.CardCdr;
import vn.onepay.common.SharedConstants;
import vn.onepay.search.CardCdrElasticSearch;
import vn.onepay.service.ServiceFinder;
import vn.onepay.utils.Utils;

public class CardCdrReportController extends AbstractProtectedController
{
  private CardCdrDAO cardCdrDAO;
  private int limit;
  private static DateFormat dtFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    CardCdrElasticSearch cardCdrElasticSearch;
	
	public CardCdrElasticSearch getCardCdrElasticSearch() {
		return cardCdrElasticSearch;
	}

	public void setCardCdrElasticSearch(CardCdrElasticSearch cardCdrElasticSearch) {
		this.cardCdrElasticSearch = cardCdrElasticSearch;
	}

public void setCardCdrDAO(CardCdrDAO cardCdrDAO)
  {
    this.cardCdrDAO = cardCdrDAO;
  }

  public void setLimit(int limit) {
    this.limit = limit;
  }

  protected ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response, ModelMap model)
    throws Exception
  {
    try
    {
      String chargingService = "CARD";
      Account account = (Account)request.getSession().getAttribute("account_logined");
      if (account.checkRole("staff"));
      boolean isStaff = account.checkRoles(new String[] { "admin", "operation_manager", "reporter", "share_holder", "merchant_manager", "biz_supporter", "customer_care" });
      List<String> myOwnMerchants = findMyOwnMerchants(account);

      if ((account.isAdmin()) && (SharedConstants.MBIZ) && (SharedConstants.MBIZ_MERCHANTS != null) && (SharedConstants.MBIZ_MERCHANTS.length > 0) && 
        (myOwnMerchants != null)) {
        List<String> myMerchants = new ArrayList<String>();
        for (String mc : myOwnMerchants) {
          for (String activedMerchant : SharedConstants.MBIZ_MERCHANTS) {
            if (activedMerchant.equalsIgnoreCase(mc)) {
              myMerchants.add(mc);
              break;
            }
          }
        }
        myOwnMerchants = myMerchants;
      }

      if ((myOwnMerchants != null) && (myOwnMerchants.size() > 1)) {
        model.put("merchants", myOwnMerchants);
      }
      if (isStaff) {
        if (account.checkRoles(new String[] { "admin", "operation_manager", "reporter", "share_holder" }))
        {
          ProviderProfileManager providerProfileManager = (ProviderProfileManager)ServiceFinder.getContext(request).getBean("providerProfileManager", ProviderProfileManager.class);
          List<ProviderProfile> providerProfiles = providerProfileManager.findProviderProfiles("CARD");
          if ((providerProfiles != null) && (providerProfiles.size() > 0)) {
            Object providers = new ArrayList();
            for (ProviderProfile profile : providerProfiles) {
              ((List)providers).add(profile.getProviderCode());
            }
            Collections.sort((List)providers);
            model.put("providers", providers);
          }
        }
      }

      model.put("isStaff", Boolean.valueOf(isStaff));
      model.put("types", this.cardCdrDAO.findAllCardTypes());
      model.put("status", this.cardCdrDAO.findAllCardStatus());
      model.put("amounts", getAmounts());
      String pin = StringUtils.trimToEmpty(request.getParameter("pin"));
      String serial = StringUtils.trimToEmpty(request.getParameter("serial"));
      String status = StringUtils.trimToEmpty(request.getParameter("status"));
      String searchMerchant = StringUtils.trimToEmpty(request.getParameter("searchMerchant"));

      Object amounts = null;
      List<String> arrAmounts = getValues(request, "amount");
      if ((arrAmounts != null) && (arrAmounts.size() > 0)) {
        amounts = new ArrayList();
        for (String strAmount : arrAmounts) {
          try {
            ((List)amounts).add(Integer.valueOf(Integer.parseInt(strAmount)));
          } catch (Exception e) {
            e.printStackTrace();
          }
        }
      }
      List merchants = null;
      if (isStaff) {
        merchants = getValues(request, "merchant");
        if ((merchants == null) && 
          (account.checkRole("merchant_manager")))
          merchants = myOwnMerchants;
      }
      else {
        merchants = Arrays.asList(new String[] { account.getUsername() });
      }

      List providers = isStaff ? getValues(request, "provider") : null;
      List cardTypes = getValues(request, "type");
      Date reportFromDay = getDate(request, "startDate");
      Date reportToDay = getDate(request, "endDate");
      SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
      try {
        String[] dates = request.getParameter("reservation").split("-");
        reportFromDay = sdf.parse(dates[0].trim());
        reportToDay = sdf.parse(dates[1].trim());
      }
      catch (Exception e) {
        Date today = new Date();
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        reportFromDay = sdf2.parse(sdf.format(today) + " 00:00:00");
        reportToDay = sdf2.parse(sdf.format(today) + " 00:00:00");
        String todaySt = sdf.format(today);
        model.put("today", todaySt + " - " + todaySt);
      }
      Date fromTime = Utils.getStartOfDay(reportFromDay);
      Date toTime = Utils.getEndOfDay(reportToDay);

      int offset = 0;
      if (StringUtils.isNumeric(request.getParameter("d-49520-p"))) {
        offset = Integer.parseInt(request.getParameter("d-49520-p"));
        if (offset > 0) {
          offset = (offset - 1) * this.limit;
        }
      }
      Double[] sumary = this.cardCdrDAO.countAndAmountCdr(account, myOwnMerchants, pin, serial, status, (List)amounts, cardTypes, searchMerchant, merchants, providers, fromTime, toTime);

      model.put("pagesize", Integer.valueOf(this.limit));
      model.put("offset", Integer.valueOf(offset));
      model.put("list", this.cardCdrDAO.findCardCdr(account, myOwnMerchants, pin, serial, status, (List)amounts, cardTypes, searchMerchant, merchants, providers, fromTime, toTime, offset, this.limit));
      model.put("total", new Integer(sumary[0].intValue()));

      model.put("sumary", sumary); System.out.println(sumary);
      
      //INDEX
      if(!cardCdrElasticSearch.checkExist() ){
    	  System.out.println("Dang danh chi muc ...");
    	  List<CardCdr> cardCdrList = cardCdrDAO.findCardCdr(account, null, "", "", "", null, null, "", null, null, null, null, 0, 0);
    	  cardCdrElasticSearch.index(cardCdrList);
      }
      else{
    	  System.out.println("Da ton tai chi muc");
      }
      
      
      
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return (ModelAndView)(ModelAndView)new ModelAndView(getWebView(), "model", model);
  }

  private List<String> getValues(HttpServletRequest request, String name)
  {
    try {
      String[] arr = request.getParameterValues(name);
      if ((arr != null) && (arr.length > 0))
        return Arrays.asList(arr);
    }
    catch (Exception localException) {
    }
    return null;
  }
  private List<String> getAmounts() {
    String[] arrAmounts = { "10000", "20000", "50000", "100000", "200000", "300000", "500000", "1000000", "5000000" };
    return Arrays.asList(arrAmounts);
  }
  private Date getDate(HttpServletRequest request, String name) {
    try {
      return dtFormat.parse(request.getParameter(name) + " 00:00:00");
    }
    catch (Exception localException) {
    }
    return new Date();
  }
}