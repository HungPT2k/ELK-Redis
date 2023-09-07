package com.example.elkredis.elastic.ReposElastic;

import com.example.elkredis.elastic.entity.springboot1;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ElasticRepo extends ElasticsearchRepository<springboot1,String> {
//    List<springboot1> find(String contains);
}
