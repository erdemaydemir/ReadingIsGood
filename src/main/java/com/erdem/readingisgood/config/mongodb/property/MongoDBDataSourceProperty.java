package com.erdem.readingisgood.config.mongodb.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "mongodb")
public class MongoDBDataSourceProperty {

    private String connectionString;
    private String databaseName;
}
