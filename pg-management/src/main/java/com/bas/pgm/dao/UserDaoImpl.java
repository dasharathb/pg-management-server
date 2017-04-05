package com.bas.pgm.dao;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.project;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.Fields;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.bas.pgm.model.GuestInfo;
import com.bas.pgm.model.HostelGuests;
import com.bas.pgm.model.Person;
import com.bas.pgm.model.PersonInfo;
import com.bas.pgm.model.User;
import com.bas.pgm.mongo.repo.UserRepo;

@Component(value="userDao")
public class UserDaoImpl implements UserDao {

	@Autowired
	MongoTemplate mongoTemplate;
	
	@Autowired
	UserRepo userRepo;
	
	@Override
	public String savePerson(User user) {
		User exeUser = userRepo.findByPhone(user.getPhone());
		if(exeUser == null)
			mongoTemplate.save(user);
		return "user created";
	}

	@Override
	public User getUser(String phone, String password) {
		return userRepo.findByPhoneAndPassword(phone, password);
	}

	@Override
	public GuestInfo getTotalGuests(String phone) {
		Aggregation aggregations = newAggregation(
				match(Criteria.where("hostelNum").is(phone)),
				PGMDaoQueryUtil.unWindAggrBuilderGuest(),
				getGroupOperationTotalGuest()
				);
		
		GuestInfo result = getQueryAggrgationTotalGuests(aggregations, "hostel_guests");
		return result;
	}

	private GroupOperation getGroupOperationTotalGuest() {
		GroupOperation groupOperationBuilder = group("hostelNum")
				.count().as("totalGuests");		
		return groupOperationBuilder;
	}
	private GuestInfo getQueryAggrgationTotalGuests(Aggregation aggregations,String collectionNameToFetchRecords) {
		
		GuestInfo result = new GuestInfo();
		try{			
			AggregationResults<GuestInfo> groupResults 
			= mongoTemplate.aggregate(aggregations, collectionNameToFetchRecords, GuestInfo.class);
			result = groupResults.getUniqueMappedResult();
		}catch(Exception e){
		}
		System.out.println("GuestInfo ::::::::::: "+result);
		return result;
	}

	@Override
	public GuestInfo getPresentGuests(String phone) {
		Aggregation aggregations = newAggregation(
				match(Criteria.where("hostelNum").is(phone)),
				PGMDaoQueryUtil.unWindAggrBuilderGuest(),
				match(Criteria.where("guests.status").is("P")),
				getGroupOperationPresentGuest()
				);
		
		GuestInfo result = getQueryAggrgationTotalGuests(aggregations, "hostel_guests");
		return result;
	}
	private GroupOperation getGroupOperationPresentGuest() {
		GroupOperation groupOperationBuilder = group("hostelNum")
				.count().as("presentGuests");		
		return groupOperationBuilder;
	}

	@Override
	public List<PersonInfo> getFeeDueInfo(String phone) {
		Aggregation aggregations = newAggregation(
				match(Criteria.where("hostelNum").is(phone)),
				PGMDaoQueryUtil.unWindAggrBuilderGuest(),
				match(Criteria.where("guests.joinDate").lte(new Date()))
				);
		
		List<PersonInfo> result = getQueryAggrgationFeeDueInfo(aggregations, "hostel_guests");
		return result;
	}

	private ProjectionOperation getProjectionBuilder() {
		ProjectionOperation operation = project(Fields.fields("_id"))
				.andInclude("guests");
		return operation;
	}
	
	private List<PersonInfo> getQueryAggrgationFeeDueInfo(Aggregation aggregations,String collectionNameToFetchRecords) {
		
		List<PersonInfo> result = new ArrayList<PersonInfo>();
		//HostelGuests guests = new HostelGuests();
		try{			
			AggregationResults<PersonInfo> groupResults 
			= mongoTemplate.aggregate(aggregations, collectionNameToFetchRecords, PersonInfo.class);
			result = groupResults.getMappedResults();
		}catch(Exception e){
		}
		System.out.println("GuestInfo ::::::::::: "+result.toString());
		return result;
	}

	@Override
	public User getUserWithDeviceId(String deviceId) {
		
		return userRepo.findByDeviceId(deviceId);
	}
	
	@Override
	public void updateMethod(String phone, String deviceId) {
		try{
			Query query = Query.query(Criteria.where("phone").is(phone));
			Update update = new Update().update("deviceId.$", deviceId);
			mongoTemplate.upsert(query, update, User.class);
		}catch(Exception e){
			Query query = Query.query(Criteria.where("phone").is(phone));
			mongoTemplate.upsert(query, new Update().update("deviceId", deviceId), User.class);
		}
    }
}
