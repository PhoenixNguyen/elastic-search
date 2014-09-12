package org.springframework.data.elasticsearch.repositories;

import org.elasticsearch.index.query.FilterBuilders;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.FacetedPage;
import org.springframework.data.elasticsearch.core.facet.request.TermFacetRequestBuilder;
import org.springframework.data.elasticsearch.core.facet.result.Term;
import org.springframework.data.elasticsearch.core.facet.result.TermResult;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.data.elasticsearch.entities.Article;
import org.springframework.data.elasticsearch.entities.Book;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration("classpath:/springContext-test.xml")
@Service
public class SampleArticleRepositoryTest {

//	@Resource
//    private SampleArticleRepository sampleArticleRepository;
//
//    @Before
//    public void emptyData(){
//        sampleArticleRepository.deleteAll();
//    }

    @Test
    public void shouldIndexSingleBookEntity(){
    	
    	ApplicationContext context = 
                new ClassPathXmlApplicationContext("/spring-article.xml");
    	ArticleService articleService = (ArticleService)context.getBean("articleService");
            
        Article article = new Article();
        article.setId("123455");
        article.setTitle("Spring Data Elasticsearch Test Article");
        
        List<String> authors = new ArrayList<String>();
        authors.add("Author1");
        authors.add("Author2");
        
        article.setAuthors(authors);
        
        List<String> tags = new ArrayList<String>();
        tags.add("tag1");
        tags.add("tag2");
        tags.add("tag3");
        
        article.setTags(tags);
        
        
        //Article 2
        Article article2 = new Article();
        article2.setId("22123455");
        article2.setTitle("Spring Data Elasticsearch Test Article");
        
        List<String> authors2 = new ArrayList<String>();
        authors2.add("Author2");
        
        article2.setAuthors(authors2);
        
        List<String> tags2 = new ArrayList<String>();
        tags2.add("tag1");
        tags2.add("tag2");
        tags2.add("tag3");
        
        article2.setTags(tags2);
        
        //Indexing using sampleArticleRepository
        articleService.save(article);
        articleService.save(article2);
        
        //lets try to search same record in elasticsearch
        Article indexedArticle = articleService.findOne(article.getId());
        if(indexedArticle != null){
        	System.out.println(indexedArticle.getId());
        }
        
        // given
        String facetName = "fauthors";//fauthors
        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(matchAllQuery())
        		.withFacet(new TermFacetRequestBuilder(facetName).fields("authors.untouched").ascCount().build()).build();//.untouched
        // when
        Page<Article> articles = articleService.search(searchQuery);
        
        FacetedPage<Article> result = articleService.search(searchQuery);
        TermResult facet = (TermResult) result.getFacet(facetName);
        
        List<Term> terms = facet.getTerms();
        for(int i = 0; i < terms.size(); i++){
        	System.out.println(terms.get(i).getTerm()+" | " + terms.get(i).getCount());
        	
        	for(Article art : articles){
        		if(art.getAuthors().contains(terms.get(i).getTerm())){
        			System.out.println("	"+art.getId());
        		}
        	}
        	
        	
        }
        
//        assertThat(indexedArticle,is(notNullValue()));
//        assertThat(indexedArticle.getId(),is(article.getId()));
//        assertThat(indexedArticle.getAuthors().size(),is(authors.size()));
//        assertThat(indexedArticle.getTags().size(),is(tags.size()));
    }
    
    public static void main(String[] a){
	    SampleArticleRepositoryTest test = new SampleArticleRepositoryTest();
	    test.shouldIndexSingleBookEntity();
	    
//	    JUnitCore junit = new JUnitCore();
//	    Result result = junit.run(SampleArticleRepositoryTest.class);
	    
    }
}
