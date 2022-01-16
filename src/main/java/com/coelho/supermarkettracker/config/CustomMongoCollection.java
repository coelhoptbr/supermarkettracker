package com.coelho.supermarkettracker.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:application.properties")
public class CustomMongoCollection {

    private static String dataTablePrefix;

    @Value("${datatable.prefix}")
    public void setDataTablePrefix(String dataTablePrefixVar) {
        dataTablePrefix = dataTablePrefixVar;
    }

    public static String getCollectionNameWithPrefix(String collectionName) {
        if (dataTablePrefix == null || dataTablePrefix.trim().length() == 0) {
            return collectionName;
        } else {
            return dataTablePrefix.trim().concat(collectionName.trim());
        }
    }

}
