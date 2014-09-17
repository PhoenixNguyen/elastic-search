package vn.onepay.search.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.map.LinkedMap;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.data.elasticsearch.core.facet.result.Term;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import vn.onepay.search.elastic.ElasticSearch;
import vn.onepay.web.secure.controllers.AbstractProtectedController;

public class CardCdrController extends AbstractProtectedController{

	public ElasticSearch elasticSearch;
	public ElasticSearch getElasticSearch() {
		return elasticSearch;
	}

	public void setElasticSearch(ElasticSearch elasticSearch) {
		this.elasticSearch = elasticSearch;
	}

	private int limit;
	public void setLimit(int limit) {
	    this.limit = limit;
	}
	
	@Override
	protected ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) throws Exception {
		Date start = new Date();
		
		String key = StringUtils.trimToEmpty(request.getParameter("key"));
		
		//(*)
		@SuppressWarnings("unchecked")
		Map<String , String> fieldMaps = new LinkedMap();
		fieldMaps.put("paymentProvider", "Nhà cung cấp");
		fieldMaps.put("type", "Loại thẻ");
		fieldMaps.put("amount", "Mệnh giá");
		fieldMaps.put("status", "Trạng thái");
		model.put("fieldMaps", fieldMaps);
		
		//(*)
		List<String> fields = new ArrayList<String>();
		fields.add("paymentProvider");
		fields.add("type");
		fields.add("amount");
		fields.add("status");
		
		List<String> terms = new ArrayList<String>();
		//Display
		for(String field : fieldMaps.keySet()){
			
			String param = StringUtils.trimToEmpty(request.getParameter(field));
			terms.add(param);
		}
		
		//(*)
		List<List<Term>> termLists = new ArrayList<List<Term>>();
	    
	    //(*)
	    @SuppressWarnings("unchecked")
		Map<String , String> keywords = new LinkedMap();
	    if(!key.equals("")){
	    	keywords.put("merchant", key);
	    	keywords.put("type", key);
	    }
	    
	    //(*)
	    @SuppressWarnings("unchecked")
		Map<String , SortOrder> sorts = new LinkedMap();
	    sorts.put("timestamp", SortOrder.DESC);
	    sorts.put("amount", SortOrder.ASC);
	    
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
	      int count = 0;
	      
	      if(elasticSearch.checkIndex(vn.onepay.search.entities.CardCdr.class)){
	    	    count = elasticSearch.count(fields, terms, keywords, vn.onepay.search.entities.CardCdr.class);
	    	    termLists = elasticSearch.getFacets(fields, terms, keywords, vn.onepay.search.entities.CardCdr.class);
				cardCdrList = elasticSearch.search(fields, terms, keywords, sorts, page, limit, vn.onepay.search.entities.CardCdr.class);
				
				
	      }
	    
	    System.out.println("COUNT: "+count);
	    
		model.put("total", count);
		
		//(*)
		//push to layout
		@SuppressWarnings("unchecked")
		Map<String , List<Term>> facetsMap = new LinkedMap();
		int k = 0;
		for(String field : fieldMaps.keySet()){
			
			if(termLists.size() > k)
				facetsMap.put(field, termLists.get(k));
			
			k++;
		}

		model.put("facetsMap", facetsMap);
			
		model.put("pagesize", Integer.valueOf(this.limit));
	    model.put("offset", Integer.valueOf(offset));
	    model.put("list", cardCdrList);
	      
	    Date end = new Date();
	    Long duration = end.getTime() - start.getTime();
	    Long timeHandleTotal = TimeUnit.MILLISECONDS.toMillis(duration);
	      
        model.put("timeHandleTotal", timeHandleTotal);
	      
		return new ModelAndView(getWebView(), "model", model);
	}

}
