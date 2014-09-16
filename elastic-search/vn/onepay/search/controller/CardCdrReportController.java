package vn.onepay.search.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.ehcache.util.TimeUtil;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.elasticsearch.core.facet.result.Term;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import vn.onepay.account.model.Account;
import vn.onepay.common.SharedConstants;
import vn.onepay.search.CardCdrElasticSearch;
import vn.onepay.web.secure.controllers.AbstractProtectedController;

public class CardCdrReportController extends AbstractProtectedController{
	
	CardCdrElasticSearch cardCdrElasticSearch;
	
	public CardCdrElasticSearch getCardCdrElasticSearch() {
		return cardCdrElasticSearch;
	}

	public void setCardCdrElasticSearch(CardCdrElasticSearch cardCdrElasticSearch) {
		this.cardCdrElasticSearch = cardCdrElasticSearch;
	}

	private int limit;
	public void setLimit(int limit) {
	    this.limit = limit;
	  }
	
	@Override
	protected ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) throws Exception {
		
		Date start = new Date();
		
		Account account = (Account)request.getSession().getAttribute("account_logined");
		
		String paymentProvider = StringUtils.trimToEmpty(request.getParameter("provider"));
		String cardType = StringUtils.trimToEmpty(request.getParameter("type"));
		String amount = StringUtils.trimToEmpty(request.getParameter("amount"));
		String status = StringUtils.trimToEmpty(request.getParameter("status"));
		
		String merchant = StringUtils.trimToEmpty(request.getParameter("merchant"));
		
		System.out.println(paymentProvider + " " + cardType + " " + amount + " " + status);
		
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
	      
	      
		int offset = 0;
		int page = 0;
	      if (StringUtils.isNumeric(request.getParameter("d-49520-p"))) {
	    	  offset = Integer.parseInt(request.getParameter("d-49520-p"));
	    	  page = Integer.parseInt(request.getParameter("d-49520-p")) - 1;
	        if (offset > 0) {
	          offset = (offset - 1) * this.limit;
	        }
	      }
	      
	      List<vn.onepay.search.entities.CardCdr> cardCdrList = new ArrayList<vn.onepay.search.entities.CardCdr>();
	      
	      if(cardCdrElasticSearch.checkExist())
	    	  cardCdrList = cardCdrElasticSearch.search(paymentProvider, cardType, amount, status, merchant, page, limit);
	      
		  model.put("pagesize", Integer.valueOf(this.limit));
	      model.put("offset", Integer.valueOf(offset));
	      model.put("list", cardCdrList);
	      
	 
	      //FACET
	      List<Term> providerFacets = new ArrayList<Term>();//elasticSearch.getFacets("paymentProvider"); //
	      List<Term> typeFacets = new ArrayList<Term>();//elasticSearch.getFacets("type");
	      List<Term> amountFacets = new ArrayList<Term>();//elasticSearch.getFacets("amount");
	      List<Term> statusFacets = new ArrayList<Term>();//elasticSearch.getFacets("status");
	      
	      int count = 0;
	      if(cardCdrElasticSearch.checkExist())
	    	  count = cardCdrElasticSearch.getFacets(paymentProvider, cardType, amount, status, merchant, 
	    		  providerFacets, typeFacets, amountFacets, statusFacets);
	      
	      model.put("providerFacets", providerFacets);
	      model.put("typeFacets", typeFacets);
	      model.put("amountFacets", amountFacets);
	      model.put("statusFacets", statusFacets);
	      
	      model.put("total", count);
	      
	      Date end = new Date();
	      Long duration = end.getTime() - start.getTime();
	      Long timeHandleTotal = TimeUnit.MILLISECONDS.toMillis(duration);
	      
	      model.put("timeHandleTotal", timeHandleTotal);
	      
	      return new ModelAndView(getWebView(), "model", model);
	}
	
}
