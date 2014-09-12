package vn.onepay.search.repositories;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import vn.onepay.search.entities.CardCdr;

public interface CardCdrRepository extends ElasticsearchRepository<CardCdr, String>{

}
