package com.example.elkredis.Controller;
import com.example.elkredis.Config.receiver3;
import com.example.elkredis.elastic.Service.ElasticService;
import com.example.elkredis.elastic.entity.springboot1;
import com.example.elkredis.model.Product;

import com.example.elkredis.model.ResponseCommon;
import com.example.elkredis.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api")
public class ElkController {
    private static final Logger log = LoggerFactory.getLogger(ElkController.class);
    @Autowired
    private receiver3 re;
    @Autowired
    private ElasticService elasticService;


    @GetMapping(value = "/product/{id}")
    public ResponseCommon getById(@PathVariable(value = "id") Long id) throws InterruptedException {
        log.info("Inside Hello World Function");
        String response = "Hello World! " + new Date();
        log.info("Response => {}",response);
    //   ResponseCommon responseCommon=


        return  re.findById(id);
    }
    @PutMapping(value = "/update/{id}")
    public ResponseCommon UpDateItem (@PathVariable(value = "id") Long id, @RequestBody Product product) throws InterruptedException {
//        log.info("Inside Hello World Function");
//        String response = "Hello World! " + new Date();
//        log.info("Response => {}",response);
        return re.updateProduct(id,product);
    }
    @GetMapping(value="/elastic/all")
    public List<springboot1> getAll(){
      return  elasticService.findAll();
    }

//    @GetMapping(value = "/{id}")
//    public Optional<Product> getByIdTest(@PathVariable(value = "id") Long id) throws InterruptedException {
//
//
//
//        return productRepository.findById(id);
//    }

}
