package com.bas.pgm.dao;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Component;

@Component(value ="dmAggregationAgentDao")
public class NodeAggregationAgentDaoImpl implements NodeAggregationAgentDao {
	private final Logger logger = Logger.getLogger(NodeAggregationAgentDao.class);

	@Qualifier(value="collectorMongoTemplate")
	@Autowired
	MongoTemplate collectorMongoTemplate;
		
	@Qualifier(value="dashbMongoTemplate")
	@Autowired
	MongoTemplate dashbMongoTemplate;
	
	@Override
	public List getNodeDailyDataWithAggregation(Date startDate, Date endDate) {
		logger.debug("get Node Daily Data with Aggregation...");
		
		Aggregation aggregations = newAggregation(
				match(Criteria.where("date").is(startDate)),
				getGroupOperationBuilder()
				);
		
		List result = getQueryAggrgationResults(aggregations,"NODE_INTERVAL");

		return result;

	}

	private GroupOperation getGroupOperationBuilder() {
		GroupOperation groupOperationBuilder = group("cluster")
				.avg("$interval.totalNodes").as("totalNodes")
				.avg("$interval.activeNodes").as("activeNodes")
				.avg("$interval.downNodes").as("downNodes");		
		return groupOperationBuilder;
	}
	
	private List getQueryAggrgationResults(Aggregation aggregations,String collectionNameToFetchRecords){
		//Convert the aggregation result into a List
		List result = new ArrayList();
		try{
			AggregationResults groupResults 
			= dashbMongoTemplate.aggregate(aggregations, collectionNameToFetchRecords, Object.class);
			result = groupResults.getMappedResults();
		}catch(Exception e){
			//throw new AggregationException(e.getMessage());
		}
		return result;
	}
}
