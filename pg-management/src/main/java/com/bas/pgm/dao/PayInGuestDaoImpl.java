package com.bas.pgm.dao;

//import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;

//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
//import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.bas.pgm.model.Guest;
import com.bas.pgm.model.HostelGuests;
import com.bas.pgm.model.Person;
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

	@Override
	public void savePerson(Person person) {
		
		mongoTemplate.save(person);
	}
	
	@Override
	public void pushMethodGuest(String objectId, Person person) {
		mongoTemplate.updateFirst(
            Query.query(Criteria.where("_id").is(objectId)), 
            new Update().push("guests", person), HostelGuests.class);
    }

	@Override
	public HostelGuests getGuestInfo(String hostelNum, String guestId) {
		Aggregation aggregations = newAggregation(
				unWindAggrBuilderGuest(),
				match(Criteria.where("hostelNum").is(hostelNum).and("guests._id").is(guestId))				
				);
		HostelGuests result = getQueryAggrgationResults(aggregations, "hostel_guests");
		
		return result;
	}
	private HostelGuests getQueryAggrgationResults(Aggregation aggregations,String collectionNameToFetchRecords) {
		
		HostelGuests result = new HostelGuests();
		try{			
			AggregationResults<HostelGuests> groupResults 
			= mongoTemplate.aggregate(aggregations, collectionNameToFetchRecords, HostelGuests.class);
			result = groupResults.getUniqueMappedResult();
		}catch(Exception e){
		}
		return result;
	}

	public static CustomAggregationOperation unWindAggrBuilderGuest(){
		CustomAggregationOperation aggregationOperation = new CustomAggregationOperation(
				new BasicDBObject("$unwind", "$guests")
				);				
		return aggregationOperation;
	}
}
