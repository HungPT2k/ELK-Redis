package com.example.elkredis.elastic.Service;

import com.example.elkredis.elastic.ReposElastic.ElasticRepo;
import com.example.elkredis.elastic.entity.springboot1;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ElasticService {

    private final ElasticRepo elasticRepo;
    private ElasticsearchOperations elasticsearchOperations;

    public List<springboot1> findAll(){
        return (List<springboot1>)  elasticRepo.findAll();
    }

    public void findByProductPrice(final String productPrice) {
        Criteria criteria = new Criteria("message");
        Query searchQuery = new CriteriaQuery(criteria);
        SearchHits<springboot1> products = elasticsearchOperations
                .search(searchQuery,
                        springboot1.class);
    }

}
