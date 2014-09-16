package vn.onepay.search;

import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.elasticsearch.index.query.FilterBuilders;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.FacetedPage;
import org.springframework.data.elasticsearch.core.facet.request.TermFacetRequestBuilder;
import org.springframework.data.elasticsearch.core.facet.result.Term;
import org.springframework.data.elasticsearch.core.facet.result.TermResult;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import vn.onepay.card.model.CardCdr;
import vn.onepay.search.service.CardCdrService;

@Service
public class CardCdrElasticSearch {
	CardCdrService cardCdrService;
	public CardCdrService getCardCdrService() {
		return cardCdrService;
	}

	public void setCardCdrService(CardCdrService cardCdrService) {
		this.cardCdrService = cardCdrService;
	}

	public ElasticsearchTemplate getElasticsearchTemplate() {
		return elasticsearchTemplate;
	}

	public void setElasticsearchTemplate(ElasticsearchTemplate elasticsearchTemplate) {
		this.elasticsearchTemplate = elasticsearchTemplate;
	}

	ElasticsearchTemplate elasticsearchTemplate;
	
//	public CardCdrElasticSearch(){
//		@SuppressWarnings("resource")
//		ApplicationContext context = 
//                new ClassPathXmlApplicationContext("/vn/onepay/search/resources/card-cdr-service.xml");
//		cardCdrService = (CardCdrService)context.getBean("cardCdrService");
//		
//		elasticsearchTemplate = (ElasticsearchTemplate)context.getBean("elasticsearchTemplate");
//
//	}
	
	public static void main(String []args){
		CardCdrElasticSearch elasticSearch = new CardCdrElasticSearch();
		CardCdr cardCdr = new CardCdr();
		cardCdr.setId("1");
		cardCdr.setApp_code("999");
		
		elasticSearch.index(Arrays.asList(cardCdr));
		
	}
	
	public boolean deleteIndex(){
			
			if(elasticsearchTemplate.indexExists(vn.onepay.search.entities.CardCdr.class))
				return elasticsearchTemplate.deleteIndex(vn.onepay.search.entities.CardCdr.class);
			return false;
	}

	public boolean checkExist(){
		
		return elasticsearchTemplate.indexExists(vn.onepay.search.entities.CardCdr.class);
	}
	
	public void index(List<CardCdr> cardCdrList){
		if(cardCdrList == null)
			return;
		
		System.out.println("COUNT: " + cardCdrList.size());
		
		List<vn.onepay.search.entities.CardCdr> cardCdrRepo = new ArrayList<vn.onepay.search.entities.CardCdr>();
		for(CardCdr card : cardCdrList){
			vn.onepay.search.entities.CardCdr cardRepo = new vn.onepay.search.entities.CardCdr(
					card.getId(), card.getAmount(), card.getMerchant(), 
					card.getPaymentProvider(), card.getApp_code(), card.getPin(), 
					card.getSerial(), card.getType(), card.getStatus(), 
					card.getMessage(), card.getTimestamp(), card.getExtractStatus());
			
			cardCdrRepo.add(cardRepo);
			
		}
		
		cardCdrService.save(cardCdrRepo);
	}
	
	public List<vn.onepay.search.entities.CardCdr> searchAll(){
		
		Iterable<vn.onepay.search.entities.CardCdr> cardCdrs = cardCdrService.findAll();
		
		List<vn.onepay.search.entities.CardCdr> cardCdrList = new ArrayList<vn.onepay.search.entities.CardCdr>();
		
		CollectionUtils.addAll(cardCdrList, cardCdrs.iterator());
		
		return cardCdrList;
		
	}
	
	public List<vn.onepay.search.entities.CardCdr> search(String paymentProvider, String cardType, String amount, String status, String merchant, int page, int size){
		
		Iterable<vn.onepay.search.entities.CardCdr> cardCdrs = cardCdrService.search(queryForPage(paymentProvider, cardType, amount, status, merchant, page, size));
		
		List<vn.onepay.search.entities.CardCdr> cardCdrList = new ArrayList<vn.onepay.search.entities.CardCdr>();
		
		CollectionUtils.addAll(cardCdrList, cardCdrs.iterator());
		
		return cardCdrList;
		
	}
	
	private SearchQuery queryForPage(String paymentProvider, String cardType,
			String amount, String status, String merchant, int page, int size) {
		
		return new NativeSearchQueryBuilder().withQuery(matchAllQuery())
				.withFilter(FilterBuilders.andFilter(
						paymentProvider.equalsIgnoreCase("")? FilterBuilders.matchAllFilter():FilterBuilders.termFilter("paymentProvider", paymentProvider),
						cardType.equalsIgnoreCase("")? FilterBuilders.matchAllFilter():FilterBuilders.termFilter("type", cardType),
						amount.equalsIgnoreCase("")? FilterBuilders.matchAllFilter():FilterBuilders.termFilter("amount", amount),
						status.equalsIgnoreCase("")? FilterBuilders.matchAllFilter():FilterBuilders.termFilter("status", status),
						merchant.equalsIgnoreCase("")? FilterBuilders.matchAllFilter():FilterBuilders.prefixFilter("merchant", merchant)
						))
				
				.withPageable(new PageRequest(page, size))
	    		.build();
		
	}

	public List<Term> getFacets(String facetName){
		FacetedPage<vn.onepay.search.entities.CardCdr> result = cardCdrService.search(queryString(facetName));
        TermResult facet = (TermResult) result.getFacet(facetName);
        
        return facet.getTerms();
	}
	
	public int getFacets(String paymentProvider, String cardType, String amount, String status, String merchant,
  		  List<Term> providerFacets, List<Term> typeFacets, List<Term> amountFacets, List<Term> statusFacets){
		
		if(!paymentProvider.equalsIgnoreCase("")){
			providerFacets.add(new Term(paymentProvider , 0));
			
		}
		else{
			providerFacets.addAll(getFacets("paymentProvider", "", cardType, amount, status, merchant));
		}
		
		if(!cardType.equalsIgnoreCase("")){
			typeFacets.add(new Term(cardType , 0));
			
		}
		else{
			typeFacets.addAll(getFacets("type", paymentProvider, "", amount, status, merchant));
		}
		
		if(!amount.equalsIgnoreCase("")){
			amountFacets.add(new Term(amount , 0));
			
		}
		else{
			amountFacets.addAll(getFacets("amount", paymentProvider, cardType, "", status, merchant));
		}
		
		if(!status.equalsIgnoreCase("")){
			statusFacets.add(new Term(status , 0));
			
		}
		else{
			statusFacets.addAll(getFacets("status", paymentProvider, cardType, amount, "", merchant));
		}
		
		int count = getTotalRecord(paymentProvider, cardType, amount, status, merchant);
		System.out.println(count);
		return count;
		
	}
	
	private int getTotalRecord(String paymentProvider, String cardType, String amount, String status, String merchant){
		
		List<Term> statusFacets = getFacets("status", paymentProvider, cardType, amount, "", merchant);
		List<Term> amountFacets = getFacets("amount", paymentProvider, cardType, "", status, merchant);
		List<Term> typeFacets = getFacets("type", paymentProvider, "", amount, status, merchant);
		List<Term> providerFacets = getFacets("paymentProvider", "", cardType, amount, status, merchant);
		
		
		int count = 0;
		
		if(!paymentProvider.equalsIgnoreCase("") && !cardType.equalsIgnoreCase("") && !amount.equalsIgnoreCase("") && !status.equalsIgnoreCase("")){
			List<Term> rest = getFacets("status", paymentProvider, cardType, amount, status, merchant);
			for(Term term : rest){
				count += term.getCount();
			}
			
			return count;
		}
		
		
		if(paymentProvider.equalsIgnoreCase("") && providerFacets.size() > 0){
			for(Term term : providerFacets){
				count += term.getCount();
			}
			
			return count;
		}
		else
			if(cardType.equalsIgnoreCase("") && typeFacets.size() > 0){
				for(Term term : typeFacets){
					count += term.getCount();
				}
				
				return count;
			}
		
			else
				if(amount.equalsIgnoreCase("") && amountFacets.size() > 0){
					for(Term term : amountFacets){
						count += term.getCount();
					}
					
					return count;
				}
				else
					if(status.equalsIgnoreCase("") && statusFacets.size() > 0){
						for(Term term : statusFacets){
							count += term.getCount();
						}
						
						return count;
					}
					
						
		return 0;
	}
	
	private List<Term> getFacets(String field, String paymentProvider, String cardType,
			String amount, String status, String merchant) {
		
		FacetedPage<vn.onepay.search.entities.CardCdr> result = cardCdrService.search(queryString(field, paymentProvider, cardType, amount, status, merchant));
        TermResult facet = (TermResult) result.getFacet(field);
        
        return facet.getTerms();
	}

	private SearchQuery queryString(String field, String paymentProvider, String cardType,
			String amount, String status, String merchant) {
		
		return new NativeSearchQueryBuilder().withQuery(matchAllQuery())
				.withFilter(FilterBuilders.andFilter(
						paymentProvider.equalsIgnoreCase("")? FilterBuilders.matchAllFilter():FilterBuilders.termFilter("paymentProvider", paymentProvider),
						cardType.equalsIgnoreCase("")? FilterBuilders.matchAllFilter():FilterBuilders.termFilter("type", cardType),
						amount.equalsIgnoreCase("")? FilterBuilders.matchAllFilter():FilterBuilders.termFilter("amount", amount),
						status.equalsIgnoreCase("")? FilterBuilders.matchAllFilter():FilterBuilders.termFilter("status", status),
						merchant.equalsIgnoreCase("")? FilterBuilders.matchAllFilter():FilterBuilders.prefixFilter("merchant", merchant)
						))
				
        		.withFacet(new TermFacetRequestBuilder(field).applyQueryFilter().fields(field).ascCount().build()).build();
		
	}

	public SearchQuery queryString(String field){
		
		return new NativeSearchQueryBuilder().withQuery(matchAllQuery())
				.withFilter(FilterBuilders.notFilter(FilterBuilders.termFilter("type", "vcoin")))
        		.withFacet(new TermFacetRequestBuilder(field).applyQueryFilter().fields(field).ascCount().build()).build();
		
	}
	
}
