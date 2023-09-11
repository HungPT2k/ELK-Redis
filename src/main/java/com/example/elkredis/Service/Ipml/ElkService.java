package com.example.elkredis.Service.Ipml;

import com.example.elkredis.Service.IelkService;
import com.example.elkredis.model.Product;
import com.example.elkredis.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
@Component
public class ElkService implements IelkService {
    @Autowired
    private ProductRepository productRepository;


    @Cacheable(value = "findById",key = "#id",cacheManager = "cacheManager1")
    @Override
    public Optional<Product> findById(Long id){
        return   productRepository.findById(id);
    }

    @CacheEvict(value = "findAll", allEntries = true)
    @CachePut(value = "findById",key = "#id")
    @Override
    public Product UpdateProduct(Long id, Product p){
        Optional<Product> product1 =productRepository.findById(id);
        product1.get().setName(p.getName());
        return productRepository.save(product1.get());

    }
    @Cacheable(value = "findAll" , cacheManager = "cacheManager")
    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }
}
