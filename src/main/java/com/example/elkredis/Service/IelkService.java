package com.example.elkredis.Service;

import com.example.elkredis.model.MessageDTO;
import com.example.elkredis.model.MessageDTO1;
import com.example.elkredis.model.Product;

import java.util.Optional;

public interface IelkService {
    public Optional<Product> findById(Long id );
    public Product createProduct(Long id,Product p);

}
