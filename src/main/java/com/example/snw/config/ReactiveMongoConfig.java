package com.example.snw.config;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;

@Configuration
public class ReactiveMongoConfig extends AbstractReactiveMongoConfiguration {

  @Value("${spring.data.mongodb.uri}")
  private String mongodbUri;

  @Value("${spring.data.mongodb.database}")
  private String database;

  @Override
  public MongoClient reactiveMongoClient() {
    return MongoClients.create(mongodbUri);
  }

  @Override
  protected String getDatabaseName() {
    return database;
  }
}