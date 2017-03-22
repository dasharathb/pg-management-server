package com.bas.pgm.dao;

//import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.project;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.Fields;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
//import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.bas.pgm.model.Guest;
import com.bas.pgm.model.HostelGuests;
import com.bas.pgm.model.Person;
import com.bas.pgm.model.PersonInfo;
import com.bas.pgm.util.CustomAggregationOperation;
import com.mongodb.BasicDBObject;

@Component(value="payInGuestDao")
public class PayInGuestDaoImpl implements PayInGuestDao {

	@Autowired
	MongoTemplate mongoTemplate;
	
	@Override
	public Guest generateGuestId() {
		Guest guest = mongoTemplate.findById(1, Guest.class);
		if(guest == null){
			mongoTemplate.save(new Guest(1, 001));
			return new Guest(1, 001);
		}else{
			mongoTemplate.save(new Guest(1, guest.getGuestId()+1));		
		}
		return guest;
	}

	/*@Override
	public void savePerson(Person person) {
		
		mongoTemplate.save(person);
	}*/
	
	@Override
	public void pushMethodGuest(String objectId, Person person) {
		mongoTemplate.updateFirst(
            Query.query(Criteria.where("_id").is(objectId)), 
            new Update().push("guests", person), HostelGuests.class);
    }

	@Override
	public Person getGuestInfo(String hostelNum, String guestId) {
		Aggregation aggregations = newAggregation(
				PGMDaoQueryUtil.unWindAggrBuilderGuest(),
				match(Criteria.where("hostelNum").is(hostelNum).and("guests.guestId").is(guestId)),
				getProjectionBuilder()
				);
		Person result = getQueryAggrgationResults(aggregations, "hostel_guests");
		
		return result;
	}
	private Person getQueryAggrgationResults(Aggregation aggregations,String collectionNameToFetchRecords) {
		
		PersonInfo result = new PersonInfo();
		try{			
			AggregationResults<PersonInfo> groupResults 
			= mongoTemplate.aggregate(aggregations, collectionNameToFetchRecords, PersonInfo.class);
			result = groupResults.getUniqueMappedResult();
		}catch(Exception e){
		}
		System.out.println(""+result);
		return result.getGuests();
	}

	/*public static CustomAggregationOperation unWindAggrBuilderGuest(){
		CustomAggregationOperation aggregationOperation = new CustomAggregationOperation(
				new BasicDBObject("$unwind", "$guests")
				);				
		return aggregationOperation;
	}*/
	private ProjectionOperation getProjectionBuilder() {
		ProjectionOperation operation = project(Fields.fields("_id"))
				.andInclude("guests");
		return operation;
	}
	//@Override
	public void updateMethod(String objectId, Object metrics, int objId) {
		try{
			Query query = Query.query(Criteria.where("cluster").is(objectId).and("metrics.metricId").is(objId));
			Update update = new Update().update("metrics.$", metrics);
			mongoTemplate.upsert(query, update, Object.class);
		}catch(Exception e){
			Query query = Query.query(Criteria.where("cluster").is(objectId));
			mongoTemplate.upsert(query, new Update().push("metrics", metrics), Object.class);
		}
    }
}
