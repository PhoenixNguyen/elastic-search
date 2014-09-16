package vn.onepay.search.elastic;

import java.util.List;
import java.util.Map;

import org.springframework.data.elasticsearch.core.facet.result.Term;

public interface ElasticSearch {
	
	public <T> boolean checkIndex(Class<T> clazz);
	public <T> boolean deleteIndex(Class<T> clazz);
	public <T> void index(List<String> idList, List<T> objectList);
	
	public <T> int getFacets(List<String> fields, List<String> terms, List<List<Term>> termLists, Map<String , String> keywords, Class<T> clazz);
	
	public <T> List<T> search(List<String> fields, List<String> terms, Map<String , String> keywords,int page, int size, Class<T> clazz);
	public <T> long count(List<String> fields, List<String> terms, Map<String , String> keywords,Class<T> clazz);
}
