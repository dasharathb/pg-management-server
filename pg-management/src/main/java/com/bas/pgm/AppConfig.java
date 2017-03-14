package com.bas.pgm;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

@PropertySource(value={"classpath:db-conf.properties",
		"file:${external.config.dir.base}/conf/db-conf.properties"
		}, 
		ignoreResourceNotFound=true)
@EnableMongoRepositories(basePackages={"com.bas.pgm.mongo.repo*"}, mongoTemplateRef="mongoTemplate")
@ComponentScan("com.bas.pgm*")
@EnableAsync
@EnableScheduling
@Configuration
public class AppConfig {

	@Value("${mongo.db.user}")
	String mongoUser;
	@Value("${mongo.db.pwd}")
	String mongoPwd;
	@Value("${mongo.db.database}")
	String mongoDB;

	@Value("${mongo.db.host}")
	String mongoHost;
	@Value( "${mongo.db.port:27017}" )
	private int mongodbPort;
	
	/*MongoDB configuration */
    @Bean
    public MongoDbFactory mongoDBFactory() throws Exception {

	    // Set credentials      
	    MongoCredential credential = MongoCredential.createCredential(mongoUser, mongoDB, mongoPwd.toCharArray());
	    ServerAddress serverAddress = new ServerAddress(mongoHost, mongodbPort);

	    // Mongo Client
	    MongoClient mongoClient = new MongoClient(serverAddress,Arrays.asList(credential)); 

	    // Mongo DB Factory
	    SimpleMongoDbFactory simpleMongoDbFactory = new SimpleMongoDbFactory(
	            mongoClient, mongoDB);
	    System.out.println("mongoDB::::"+mongoDB+" :::: mongoUser:::::::"+mongoUser+" ::::::::mongoPwd::::::::::::::"+mongoPwd+" :::::::host::::"+mongoHost);
	    return simpleMongoDbFactory;
	}	

    @Bean(name = "mongoTemplate")
    public MongoTemplate mongoTemplateLocal() throws Exception{
    	System.out.println("mongoDBFactory() ******************* "+mongoDBFactory());
        return new MongoTemplate(mongoDBFactory());
    }
	
}
