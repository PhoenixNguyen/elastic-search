package vn.onepay.search.elastic;

import java.util.List;
import java.util.Map;

import org.elasticsearch.search.sort.SortOrder;
import org.springframework.data.elasticsearch.core.facet.result.Term;

public interface ElasticSearch {
	
	public <T> boolean checkIndex(Class<T> clazz);
	public <T> boolean deleteIndex(Class<T> clazz);
	
	public <T> void index(String id, T object);
	public <T> void bulkIndex(List<String> idList, List<T> objectList);
	
	public <T> List<List<Term>> getFacets(List<String> fields, List<String> terms, Map<String , String> keywords, Class<T> clazz);
	
	public <T> List<T> search(List<String> fields, List<String> terms, Map<String , String> keywords, Map<String , SortOrder> sorts,int page, int size, Class<T> clazz);
	public <T> int count(List<String> fields, List<String> terms, Map<String , String> keywords, Class<T> clazz);
}
