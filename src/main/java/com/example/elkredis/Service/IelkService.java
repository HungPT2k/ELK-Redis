package com.example.elkredis.Service;

import com.example.elkredis.model.MessageDTO;
import com.example.elkredis.model.MessageDTO1;
import com.example.elkredis.model.Product;

import java.util.List;
import java.util.Optional;

public interface IelkService {
     Optional<Product> findById(Long id );
     Product UpdateProduct(Long id,Product p);
     List<Product> findAll();
}
