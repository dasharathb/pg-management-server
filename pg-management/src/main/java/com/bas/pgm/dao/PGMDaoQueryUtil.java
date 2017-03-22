package com.bas.pgm.dao;

import com.bas.pgm.util.CustomAggregationOperation;
import com.mongodb.BasicDBObject;

public class PGMDaoQueryUtil {

	public static CustomAggregationOperation unWindAggrBuilderGuest(){
		CustomAggregationOperation aggregationOperation = new CustomAggregationOperation(
				new BasicDBObject("$unwind", "$guests")
				);				
		return aggregationOperation;
	}
}
