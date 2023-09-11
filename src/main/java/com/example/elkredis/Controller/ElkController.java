package com.example.elkredis.Controller;

import com.example.elkredis.Config.Receiver3;
import com.example.elkredis.model.Product;
import com.example.elkredis.model.ResponseCommon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping(value = "/api")
public class ElkController {
    private static final Logger log = LoggerFactory.getLogger(ElkController.class);
    @Autowired
    private Receiver3 re;


    @GetMapping(value = "/product/{id}")
    public ResponseCommon getById(@PathVariable(value = "id") Long id) throws InterruptedException {
        log.info("Inside Hello World Function");
        String response = "Hello World! " + new Date();
        log.info("Response => {}", response);
        //   ResponseCommon responseCommon=
        return re.findById(id);
    }

    @GetMapping(value = "/product/all")
    public ResponseCommon findAllProduct() throws InterruptedException {
        System.out.println(re.findAllProduct().getObject());
        return re.findAllProduct();
    }

    @PutMapping(value = "/update/{id}")
    public ResponseCommon UpDateItem(@PathVariable(value = "id") Long id, @RequestBody Product product) throws InterruptedException {
//        log.info("Inside Hello World Function");
//        String response = "Hello World! " + new Date();
//        log.info("Response => {}",response);
        return re.updateProduct(id, product);
    }

}
