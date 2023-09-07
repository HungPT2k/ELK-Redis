package com.example.elkredis.Service;

import com.example.elkredis.model.MessageDTO;
import com.example.elkredis.model.MessageDTO1;
import com.example.elkredis.model.Product;
import com.example.elkredis.model.ProductDTO;
import com.example.elkredis.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Component
public class ElkService implements IelkService{
    @Autowired
    private ProductRepository productRepository;

    public Optional<Product> findById(Long id){
      //  Long id= Long.valueOf(m.getParameters().get("id"));
        return   productRepository.findById(id);
    }
    public Product createProduct(Long id,Product p){
        Optional<Product> product1 =productRepository.findById(id);
        product1.get().setName(p.getName());
        return productRepository.save(product1.get());

    }
//    public Product updateProduct(MessageDTO m){
//        Long id= Long.valueOf(m.getParameters().get("id"));
//        ProductDTO product = (ProductDTO) m.getObject();
//        Optional<Product> productOptional =productRepository.findById(id);
//        if(productOptional.isPresent()){
//            productOptional.get().setName(product.getName());
//            return productRepository.save(productOptional.get());
//        }
//        return null;
//    }
}
