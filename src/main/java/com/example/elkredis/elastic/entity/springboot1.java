package com.example.elkredis.elastic.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(indexName = "springboot1*")
public class springboot1 implements Serializable {
    @Id
    private String id;
    @Field(type = FieldType.Auto,name = "_index")
    private String index;
    @Field(type = FieldType.Text,name = "message")
    private String message;
    @Field(type = FieldType.Text,name = "host.name")
    private String hostName;
}
