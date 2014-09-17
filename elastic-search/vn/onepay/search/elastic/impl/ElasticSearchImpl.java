package vn.onepay.search.elastic.impl;

import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.index.query.FilterBuilder;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.facet.request.TermFacetRequestBuilder;
import org.springframework.data.elasticsearch.core.facet.result.Term;
import org.springframework.data.elasticsearch.core.facet.result.TermResult;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;

import vn.onepay.search.elastic.ElasticSearch;

public class ElasticSearchImpl implements ElasticSearch{
	ElasticsearchTemplate elasticsearchTemplate;
	
	public ElasticsearchTemplate getElasticsearchTemplate() {
		return elasticsearchTemplate;
	}

	public void setElasticsearchTemplate(ElasticsearchTemplate elasticsearchTemplate) {
		this.elasticsearchTemplate = elasticsearchTemplate;
	}

	@Override
	public <T> List<List<Term>> getFacets(List<String> fields, List<String> terms, Map<String , String> keywords, Class<T> clazz) {
		
		List<List<Term>> termLists = new ArrayList<List<Term>>();
		
		if(fields == null)
			return termLists;
		
		int i = 0;
		for(String f : fields){
			if(!terms.get(i).equals("")){
				
				termLists.add(i, Arrays.asList(new Term(terms.get(i), 0)));
				//termLists.get(i).add(new Term(terms.get(i), 0));
				
			}
			else{
				List<String> termTemps = new ArrayList<String>();
				termTemps.addAll(terms);
				termTemps.set(i, "");
				
				termLists.add(i , getFacets(f, fields, termTemps, keywords, clazz));
				
				//test
//				if(termLists.get(i).size() > 0)
//					System.out.println(termLists.get(i).get(0).getTerm() + " " +termLists.get(i).get(0).getCount());
				
			}
				
			i++;
		}
		
		return termLists;
	}
	
	
	private <T> List<Term> getFacets(String field, List<String> fields, List<String> terms,  Map<String , String> keywords, Class<T> clazz){
		
        TermResult facet = (TermResult) elasticsearchTemplate.queryForPage(queryString(field, fields, terms, keywords, null, 0, 0),  clazz).getFacet(field);
        
        return facet.getTerms();
        
	}
	
	public <T> List<T> search(List<String> fields, List<String> terms, Map<String , String> keywords, Map<String , SortOrder> sorts, int page, int size, Class<T> clazz){
		
		Iterable<T> resultIterable = elasticsearchTemplate.queryForPage(queryString("", fields, terms, keywords, sorts, page, size),  clazz);
		
		List<T> resultList = new ArrayList<T>();
		
		CollectionUtils.addAll(resultList, resultIterable.iterator());
		
		return resultList;
		
	}
	
	public <T> int count(List<String> fields, List<String> terms, Map<String , String> keywords, Class<T> clazz){
		//return elasticsearchTemplate.count(queryString("", fields, terms, keywords, sorts, 0, 0),  clazz);
		
		return getTotalRecord(fields, terms, keywords, clazz);
		
	}
	
	private SearchQuery queryString(String field, List<String> fields, List<String> terms, Map<String , String> keywords, Map<String , SortOrder> sorts, int page, int size) {
		
		NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder().withQuery(matchAllQuery());
		
		//And
		List<FilterBuilder> filterBuildersAnd = new ArrayList<FilterBuilder>();
		int i = 0;
		for(String f : fields){
			if(StringUtils.isNotBlank(terms.get(i))){
				filterBuildersAnd.add(FilterBuilders.termFilter(f, terms.get(i)) );
				
			}
			
			i++;
		}
		
		//Or
		List<FilterBuilder> filterBuildersOr = new ArrayList<FilterBuilder>();
		Set<String> keys = keywords.keySet();
		if(keys.size() > 0)
			for(String key : keys){
				System.out.println(key + " " + keywords.get(key));
				
				filterBuildersOr.add(FilterBuilders.prefixFilter(key, keywords.get(key)));
				filterBuildersOr.add(FilterBuilders.termFilter(key, keywords.get(key).split(" ")));
				filterBuildersOr.add(FilterBuilders.inFilter(key, keywords.get(key).split(" ")));
				
			}
		
		filterBuildersAnd.add(FilterBuilders.orFilter(filterBuildersOr.toArray(new FilterBuilder[filterBuildersOr.size()])));
		
		queryBuilder.withFilter(FilterBuilders.andFilter(filterBuildersAnd.toArray(new FilterBuilder[filterBuildersAnd.size()]) ));
		
		//Sorts
		if(sorts != null)
			for(String fie : sorts.keySet()){
				queryBuilder.withSort(new FieldSortBuilder(fie).ignoreUnmapped(true).order(sorts.get(fie)));
			}
		
		if(size != 0)
			queryBuilder.withPageable(new PageRequest(page, size));
		
		if(!field.equals(""))
			queryBuilder.withFacet(new TermFacetRequestBuilder(field).applyQueryFilter().fields(field).ascCount().build());
		
		return queryBuilder.build();
	}
	
	@Override
	public <T> boolean checkIndex(Class<T> clazz) {
		
		return elasticsearchTemplate.indexExists(clazz);
	}
	
	public <T> boolean deleteIndex(Class<T> clazz){
		
		if(elasticsearchTemplate.indexExists(clazz))
			return elasticsearchTemplate.deleteIndex(clazz);
		return false;
	}
	
	public <T> void bulkIndex(List<String> idList, List<T> objectList){
		if(objectList == null)
			return;
		
		List<IndexQuery> indexQuerys = new ArrayList<IndexQuery>();
		
		int i = 0;
		for(T value : objectList){
			IndexQuery  query = new IndexQueryBuilder().withId(idList.get(i)).withObject(value).build();
			indexQuerys.add(query);
			
			i ++;
		}
		
		elasticsearchTemplate.bulkIndex(indexQuerys);
	}
	
	public <T> void index(String id, T object){
		if(object == null)
			return;
		
		elasticsearchTemplate.index(new IndexQueryBuilder().withId(id).withObject(object).build());
	}
	
	private <T> int getTotalRecord(List<String> fields, List<String> terms, Map<String , String> keywords,Class<T> clazz){
		
		if(fields.size() == 0)
			return 0;
		
		int count = 0;
		int i = 0;
		for(String field : fields){
			List<String> termTemps = new ArrayList<String>();
			termTemps.addAll(terms);
			termTemps.set(i, "");
			
			List<Term> termForFields = getFacets(field, fields, termTemps, keywords, clazz);
			
			//get count
			if(terms.get(i).equalsIgnoreCase("") && termForFields.size() > 0){
				for(Term term : termForFields){
					count += term.getCount();
				}
				
				return count;
			}
			
			i++;
		}
		
		//Else
		List<Term> rest = getFacets(fields.get(0), fields, terms, keywords, clazz);
		for(Term term : rest){
			count += term.getCount();
		}
		
		return count;
	}

}
