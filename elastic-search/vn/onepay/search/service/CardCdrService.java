package vn.onepay.search.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.elasticsearch.core.FacetedPage;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import vn.onepay.search.entities.CardCdr;
import vn.onepay.search.repositories.CardCdrRepository;

@Service
public class CardCdrService {

	@SuppressWarnings("restriction")
	@Resource
	CardCdrRepository cardCdrRepository;
	
	public CardCdrRepository getCardCdrRepository() {
		return cardCdrRepository;
	}

	public void setCardCdrRepository(CardCdrRepository cardCdrRepository) {
		this.cardCdrRepository = cardCdrRepository;
	}
	
	public void save(CardCdr card) {
		cardCdrRepository.save(card);
    }
	
	public void save(List<CardCdr> cards) {
		cardCdrRepository.save(cards);
    }
	
	public CardCdr findOne(String id){
	    return cardCdrRepository.findOne(id);
		 
	}
	
	public Iterable<CardCdr> findAll(){
	    return cardCdrRepository.findAll();
		 
	}
	
	public FacetedPage<CardCdr> search(SearchQuery searchQuery){
		 return cardCdrRepository.search(searchQuery);
	 }
	
}
