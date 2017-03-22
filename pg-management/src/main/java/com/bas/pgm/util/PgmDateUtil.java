package com.bas.pgm.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class PgmDateUtil {
 
	String zoneId = "UTC";
	
	public Date getEndOfDay(LocalDate date) {
		LocalDateTime endOfDay = date.atTime(23, 59, 59);
		Instant endDayInstant = endOfDay.atZone(ZoneId.of(zoneId)).toInstant();
		return Date.from(endDayInstant);
	}

	public Date getStartOfDay(LocalDate date) {
		LocalDateTime startOfDay = date.atStartOfDay();
		Instant startDayInstant = startOfDay.atZone(ZoneId.of(zoneId)).toInstant();
		return Date.from(startDayInstant);
	}
}
