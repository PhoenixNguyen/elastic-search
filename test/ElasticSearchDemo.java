
import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;

import java.util.List;

import org.elasticsearch.index.query.FilterBuilders;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.FacetedPage;
import org.springframework.data.elasticsearch.core.facet.request.RangeFacetRequestBuilder;
import org.springframework.data.elasticsearch.core.facet.request.TermFacetRequestBuilder;
import org.springframework.data.elasticsearch.core.facet.result.Range;
import org.springframework.data.elasticsearch.core.facet.result.RangeResult;
import org.springframework.data.elasticsearch.core.facet.result.Term;
import org.springframework.data.elasticsearch.core.facet.result.TermResult;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.data.elasticsearch.entities.Article;
import org.springframework.data.elasticsearch.entities.ArticleBuilder;

public class ElasticSearchDemo {
	public static final String RIZWAN_IDREES = "Rizwan Idrees";
    public static final String MOHSIN_HUSEN = "Mohsin Husen";
    public static final String JONATHAN_YAN = "Jonathan Yan";
    public static final String ARTUR_KONCZAK = "Artur Konczak";
    public static final int YEAR_2002 = 2002;
    public static final int YEAR_2001 = 2001;
    public static final int YEAR_2000 = 2000;
    
	public static void main(String[] args) {
		
		try{
			ApplicationContext ctx = new ClassPathXmlApplicationContext("/springContext-test.xml");
			ElasticsearchTemplate elasticsearchTemplate = (ElasticsearchTemplate) ctx.getBean("elasticsearchTemplate");
			
			elasticsearchTemplate.deleteIndex(Article.class);
	        elasticsearchTemplate.createIndex(Article.class);
	        elasticsearchTemplate.putMapping(Article.class);
	        elasticsearchTemplate.refresh(Article.class, true);

	        IndexQuery article1 = new ArticleBuilder("1").title("article four").addAuthor(RIZWAN_IDREES).addAuthor(ARTUR_KONCZAK).addAuthor(MOHSIN_HUSEN).addAuthor(JONATHAN_YAN).score(10).buildIndex();
	        IndexQuery article2 = new ArticleBuilder("2").title("article three four").addAuthor(RIZWAN_IDREES).addAuthor(ARTUR_KONCZAK).addAuthor(MOHSIN_HUSEN).addPublishedYear(YEAR_2000).score(20).buildIndex();
	        IndexQuery article3 = new ArticleBuilder("3").title("article two four").addAuthor(RIZWAN_IDREES).addAuthor(ARTUR_KONCZAK).addPublishedYear(YEAR_2001).addPublishedYear(YEAR_2000).score(30).buildIndex();
	        IndexQuery article4 = new ArticleBuilder("4").title("article one").addAuthor(RIZWAN_IDREES).addPublishedYear(YEAR_2002).addPublishedYear(YEAR_2001).addPublishedYear(YEAR_2000).score(40).buildIndex();

	        elasticsearchTemplate.index(article1);
	        elasticsearchTemplate.index(article2);
	        elasticsearchTemplate.index(article3);
	        elasticsearchTemplate.index(article4);
	        elasticsearchTemplate.refresh(Article.class, true);
	        
	        System.out.println("Indexing ...");
	        // test
	        shouldReturnFacetedAuthorsForGivenQueryWithDefaultOrder(elasticsearchTemplate);
	        
	        //filter with query
	        //shouldReturnFacetedAuthorsForGivenFilteredQuery(elasticsearchTemplate);
	        
	        //exclude terms in facet
	        //shouldExcludeTermsFromFacetedAuthorsForGivenQuery(elasticsearchTemplate);
	        
	        //Order by facet
	        //shouldReturnFacetedAuthorsForGivenQueryOrderedByTerm(elasticsearchTemplate);
	        
	        //Order by count
	        //shouldReturnFacetedAuthorsForGivenQueryOrderedByCountAsc(elasticsearchTemplate);
	        
	        //Facet for year
	        //shouldReturnFacetedYearsForGivenQuery(elasticsearchTemplate);
	        
	        //Facet author and year
	        //shouldReturnSingleFacetOverYearsAndAuthorsForGivenQuery(elasticsearchTemplate);
	        
	        //two facet
	        //shouldReturnFacetedYearsAndFacetedAuthorsForGivenQuery(elasticsearchTemplate);
	        
	        //regex
	        //shouldFilterResultByRegexForGivenQuery(elasticsearchTemplate);
	        
	        //Range facet
	        //shouldReturnKeyValueRangeFacetForGivenQuery(elasticsearchTemplate);
	        
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
//	public static void elasticRepositoryTest(){
////		ApplicationContext ctx = new ClassPathXmlApplicationContext("/vn/onepay/search/elasticsearch-context.xml");
////		ArticleRepository articleRepository = (ArticleRepository) ctx.getBean("articleRepository");
//		
//		Article article1 = new Article();
//		article1.setId("1");
//		article1.setAuthors(Arrays.asList(new String[]{RIZWAN_IDREES, ARTUR_KONCZAK, MOHSIN_HUSEN, JONATHAN_YAN}));
//		article1.setScore(10);
//		article1.setTitle("article four");
//		
//		Article article2 = new Article();
//		article2.setId("1");
//		article2.setAuthors(Arrays.asList(new String[]{RIZWAN_IDREES, ARTUR_KONCZAK, MOHSIN_HUSEN}));
//		article2.setScore(10);
//		article2.setTitle("article three");
//		
//		articleRepository.save(Arrays.asList(article1, article2));
//		
//		Article article = articleRepository.findOne(article1.getId());
//		System.out.println(article.getTitle());
//		
//		
//	}
	
	public static void shouldReturnFacetedAuthorsForGivenQueryWithDefaultOrder(ElasticsearchTemplate elasticsearchTemplate) {
		try{
			// given
	        String facetName = "fauthors";//fauthors
	        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(matchAllQuery())
	        		//.withFilter(FilterBuilders.andFilter(FilterBuilders.termFilter("title", "four"), FilterBuilders.termFilter("publishedYears", YEAR_2000)))
	        		.withFilter(FilterBuilders.regexpFilter("title*", "four 45 ghh"))
	        		.withFacet(new TermFacetRequestBuilder(facetName).applyQueryFilter().fields("authors.untouched").ascCount().build()).build();//.untouched
	        // when
	        
	        
//	        List<Article> articleList = elasticsearchTemplate.queryForList(searchQuery, Article.class);
//	        
//	        for(Article article : articleList){
//	        	System.out.println(article.getId() + " " + article.getTitle());
//	        }
	        
	        FacetedPage<Article> result = elasticsearchTemplate.queryForPage(searchQuery, Article.class);
	        // then
	        TermResult facet = (TermResult) result.getFacet(facetName);
	        List<Article> articleList = result.getContent();
	        
//			for(Article term : result.getContent()){
//				        	
//	        	System.out.println(term.getId()+" | " + term.getTitle());
//	        }
	        for(Term term : facet.getTerms()){
	        	
	        	System.out.println(term.getTerm()+" | " + term.getCount());
	        	for(Article art : articleList){
	        		if(art.getAuthors().contains(term.getTerm())){
	        			System.out.println("	"+art.getId() + " " + art.getTitle());
	        		}
	        	}
	        }
	        
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void shouldReturnFacetedAuthorsForGivenFilteredQuery(ElasticsearchTemplate elasticsearchTemplate) {

        // given
        String facetName = "fauthors";
        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(matchAllQuery())
                .withFilter(FilterBuilders.andFilter(FilterBuilders.termFilter("title", "four")))
                .withFacet(new TermFacetRequestBuilder(facetName).applyQueryFilter().fields("authors.untouched").build()).build();
        // when
        FacetedPage<Article> result = elasticsearchTemplate.queryForPage(searchQuery, Article.class);
        // then
        TermResult facet = (TermResult) result.getFacet(facetName);
        for(Term term : facet.getTerms()){
        	System.out.println(term.getTerm()+" | " + term.getCount());
        }
    }
	
	public static void shouldExcludeTermsFromFacetedAuthorsForGivenQuery(ElasticsearchTemplate elasticsearchTemplate) {
        // given
        String facetName = "fauthors";
        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(matchAllQuery())
                //.withFilter(FilterBuilders.notFilter(FilterBuilders.termFilter("title", "four")))
                .withFacet(new TermFacetRequestBuilder(facetName).applyQueryFilter().fields("authors.untouched").excludeTerms(RIZWAN_IDREES).build()).build();// ARTUR_KONCZAK RIZWAN_IDREES JONATHAN_YAN MOHSIN_HUSEN
        // when
        FacetedPage<Article> result = elasticsearchTemplate.queryForPage(searchQuery, Article.class);
        // then
        TermResult facet = (TermResult) result.getFacet(facetName);
        for(Term term : facet.getTerms()){
        	System.out.println(term.getTerm()+" | " + term.getCount());
        }

    }
	
	public static void shouldReturnFacetedAuthorsForGivenQueryOrderedByTerm(ElasticsearchTemplate elasticsearchTemplate) {

        // given
        String facetName = "fauthors";
        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(matchAllQuery())
                .withFacet(new TermFacetRequestBuilder(facetName).fields("authors.untouched").ascTerm().build()).build();
        // when
        FacetedPage<Article> result = elasticsearchTemplate.queryForPage(searchQuery, Article.class);
        // then
        TermResult facet = (TermResult) result.getFacet(facetName);
        for(Term term : facet.getTerms()){
        	System.out.println(term.getTerm()+" | " + term.getCount());
        }

    }
	
	public static void shouldReturnFacetedAuthorsForGivenQueryOrderedByCountAsc(ElasticsearchTemplate elasticsearchTemplate) {

        // given
        String facetName = "fauthors";
        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(matchAllQuery())
                .withFacet(new TermFacetRequestBuilder(facetName).fields("authors.untouched").ascCount().build()).build();
        // when
        List<Article> articleList = elasticsearchTemplate.queryForList(searchQuery, Article.class);
        
        for(Article article : articleList){
        	System.out.println(article.getId() + " " + article.getTitle());
        }
        
        FacetedPage<Article> result = elasticsearchTemplate.queryForPage(searchQuery, Article.class);
        // then
        TermResult facet = (TermResult) result.getFacet(facetName);
        for(Term term : facet.getTerms()){
        	System.out.println(term.getTerm()+" | " + term.getCount());
        }
    }
	
	public static void shouldReturnFacetedYearsForGivenQuery(ElasticsearchTemplate elasticsearchTemplate) {

        // given
        String facetName = "fyears";
        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(matchAllQuery())
                .withFacet(new TermFacetRequestBuilder(facetName).fields("publishedYears").descCount().build()).build();
        // when
        FacetedPage<Article> result = elasticsearchTemplate.queryForPage(searchQuery, Article.class);
        // then
        TermResult facet = (TermResult) result.getFacet(facetName);
        for(Term term : facet.getTerms()){
        	System.out.println(term.getTerm()+" | " + term.getCount());
        }
    }
	
	public static void shouldReturnSingleFacetOverYearsAndAuthorsForGivenQuery(ElasticsearchTemplate elasticsearchTemplate) {

        // given
        String facetName = "fyears";
        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(matchAllQuery())
                .withFacet(new TermFacetRequestBuilder(facetName).fields("publishedYears", "authors.untouched").ascTerm().build()).build();
        // when
        FacetedPage<Article> result = elasticsearchTemplate.queryForPage(searchQuery, Article.class);
        // then
        TermResult facet = (TermResult) result.getFacet(facetName);
        for(Term term : facet.getTerms()){
        	System.out.println(term.getTerm()+" | " + term.getCount());
        }

    }
	
	public static void shouldReturnFacetedYearsAndFacetedAuthorsForGivenQuery(ElasticsearchTemplate elasticsearchTemplate) {

        // given
        String numberFacetName = "fAuthors";
        String stringFacetName = "fyears";
        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(matchAllQuery())
                .withFacet(new TermFacetRequestBuilder(numberFacetName).fields("publishedYears").ascTerm().build())
                .withFacet(new TermFacetRequestBuilder(stringFacetName).fields("authors.untouched").ascTerm().build())
                .build();
        // when
        FacetedPage<Article> result = elasticsearchTemplate.queryForPage(searchQuery, Article.class);
        // then
        TermResult numberFacet = (TermResult) result.getFacet(numberFacetName);
        for(Term term : numberFacet.getTerms()){
        	System.out.println(term.getTerm()+" | " + term.getCount());
        }
        
        System.out.println("-");
        		
        TermResult stringFacet = (TermResult) result.getFacet(stringFacetName);
        for(Term term : stringFacet.getTerms()){
        	System.out.println(term.getTerm()+" | " + term.getCount());
        }
    }
	
	public static void shouldFilterResultByRegexForGivenQuery(ElasticsearchTemplate elasticsearchTemplate) {
        // given
        String facetName = "regex_authors";
        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(matchAllQuery())
                .withFilter(FilterBuilders.notFilter(FilterBuilders.termFilter("title", "four")))
                .withFacet(new TermFacetRequestBuilder(facetName).applyQueryFilter().fields("authors.untouched").regex("Art.*").build()).build();
        // when
        FacetedPage<Article> result = elasticsearchTemplate.queryForPage(searchQuery, Article.class);
        // then
        TermResult facet = (TermResult) result.getFacet(facetName);
        for(Term term : facet.getTerms()){
        	System.out.println(term.getTerm()+" | " + term.getCount());
        }

    }
	
	public static void shouldReturnKeyValueRangeFacetForGivenQuery(ElasticsearchTemplate elasticsearchTemplate) {
        // given
        String facetName = "rangeScoreOverYears";
        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(matchAllQuery())
                .withFacet(
                        new RangeFacetRequestBuilder(facetName).fields("publishedYears", "score")
                                .to(YEAR_2000).range(YEAR_2000, YEAR_2002).from(YEAR_2002).build()
                ).build();
        // when
        FacetedPage<Article> result = elasticsearchTemplate.queryForPage(searchQuery, Article.class);
        // then
        RangeResult facet = (RangeResult) result.getFacet(facetName);
        for(Range range : facet.getRanges()){
        	System.out.println(range.getFrom() +" | " + range.getTo() +" | " + range.getCount() + " | " + range.getTotal());
        }
    }
}
