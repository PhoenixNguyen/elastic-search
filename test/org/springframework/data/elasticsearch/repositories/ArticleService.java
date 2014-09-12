package org.springframework.data.elasticsearch.repositories;
import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.FacetedPage;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.data.elasticsearch.entities.Article;
import org.springframework.stereotype.Service;

@Service
public class ArticleService {
	@SuppressWarnings("restriction")
	@Resource
	SampleArticleRepository sampleArticleRepository;

	public SampleArticleRepository getSampleArticleRepository() {
		return sampleArticleRepository;
	}

	public void setSampleArticleRepository(
			SampleArticleRepository sampleArticleRepository) {
		this.sampleArticleRepository = sampleArticleRepository;
	}
	
	 public void save(Article art) {
		 sampleArticleRepository.save(art);
     }
	 
	 public Article findOne(String id){
		 return sampleArticleRepository.findOne(id);
		 
	 }
	 
	 public FacetedPage<Article> search(SearchQuery searchQuery){
		 return sampleArticleRepository.search(searchQuery);
	 }
}
