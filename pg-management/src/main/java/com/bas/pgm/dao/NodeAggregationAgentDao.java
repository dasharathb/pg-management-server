package com.bas.pgm.dao;

import java.util.Date;
import java.util.List;

public interface NodeAggregationAgentDao {
	public List getNodeDailyDataWithAggregation(Date startDate, Date endDate);
	
	
}
