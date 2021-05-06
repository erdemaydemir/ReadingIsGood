package com.erdem.readingisgood.config.mongodb;

import com.erdem.readingisgood.config.mongodb.property.MongoDBDataSourceProperty;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@ComponentScan("com.erdem")
@EnableMongoRepositories(basePackages = "com.erdem")
@EnableConfigurationProperties(MongoDBDataSourceProperty.class)
public class MongoDBAutoConfigurer extends AbstractMongoClientConfiguration {

    private final MongoDBDataSourceProperty mongoDBDataSourceProperty;

    public MongoDBAutoConfigurer(MongoDBDataSourceProperty mongoDBDataSourceProperty) {
        this.mongoDBDataSourceProperty = mongoDBDataSourceProperty;
    }

    @Bean
    MongoTransactionManager transactionManager(MongoDatabaseFactory dbFactory) {
        return new MongoTransactionManager(dbFactory);
    }

    @Override
    public MongoClient mongoClient() {
        ConnectionString connectionString = new ConnectionString(mongoDBDataSourceProperty.getConnectionString());
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();

        return MongoClients.create(mongoClientSettings);
    }

    @Override
    protected String getDatabaseName() {
        return mongoDBDataSourceProperty.getDatabaseName();
    }

}
