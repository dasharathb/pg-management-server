package com.bas.pgm.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bas.pgm.dao.PayInGuestDao;
import com.bas.pgm.model.Guest;
import com.bas.pgm.model.HostelGuests;
import com.bas.pgm.model.Person;
import com.bas.pgm.model.PersonInfo;
import com.bas.pgm.model.Reason;
import com.bas.pgm.model.User;
import com.bas.pgm.mongo.repo.HostelGuestRepo;
import com.bas.pgm.mongo.repo.UserRepo;
import com.bas.pgm.util.PgmDateUtil;

@Service(value="payInGuestService")
public class PayInGuestServiceImpl implements PayInGuestService {

	@Autowired
	PayInGuestDao payInGuestDao;
	
	@Autowired
	HostelGuestRepo hostelGuestRepo;
	
	@Autowired
	PgmDateUtil pgmDateUtil;
	
	@Autowired
	UserRepo userRepo;
	
	private static Map<String, Integer> month = setMapManth();
	
	private static Map<String, Integer> setMapManth(){
		Map<String, Integer> month = new HashMap<String, Integer>();
		month.put("JANUARY", 31);
		month.put("FEBRUARY", 28);
		month.put("MARCH", 31);
		month.put("APRIL", 30);
		month.put("MAY", 31);
		month.put("JUNE", 30);
		month.put("JULY", 31);
		month.put("AUGUST", 31 );
		month.put("SEPTEMBER", 30);
		month.put("OCTOBER", 31);
		month.put("NOVEMBER", 30);
		month.put("DECEMBER", 31);
		
		return month;
	}
	
	@Override
	public Guest generateGuestId() {
		
		return payInGuestDao.generateGuestId();
	}

	@Override
	public String savePerson(Person person, String hostelNum) {
		Guest guest = generateGuestId();
		String[] name = person.getName().split(" ");
		String guestId ;
		
		if(name.length > 1){
			guestId = name[0].charAt(0)+""+name[1].charAt(0)+""+guest.getGuestId();
		}else{
			guestId = person.getName().charAt(0)+""+guest.getGuestId();
		}
		person.setGuestId(guestId);
		person.setJoinDate(pgmDateUtil.getStartOfDay(LocalDate.now()));
		person.setPayDueDate(pgmDateUtil.getStartOfDay(LocalDate.now().plusDays(30)));
		person.setStatus("P");
		
		List<Person> persons = new ArrayList<Person>();
		HostelGuests guests = hostelGuestRepo.findOne(hostelNum);
		if(guests == null){			
			guests = new HostelGuests();
			guests.setId(hostelNum);
			guests.setHostelNum(hostelNum);
			guests.setGuests(persons);
			hostelGuestRepo.save(guests);
		}
			//payInGuestDao.savePerson(person);
			payInGuestDao.pushMethodGuest(hostelNum, person);
		//}
		
		return guestId;
	}

	@Override
	public HostelGuests getAllGuests(String hostelNum) {
		HostelGuests guests = hostelGuestRepo.findOne(hostelNum);
		return guests;
	}

	@Override
	public Person getGuestInfo(String hostelNum, String guestId) {
		return payInGuestDao.getGuestInfo(hostelNum, guestId);
	}

	@Override
	public void updateFeePaidDtls(String phone, String guestId, Integer amount) {
		Person person = payInGuestDao.getGuestInfo(phone, guestId);
		User user = userRepo.findByPhone(phone);
		
		LocalDate date = person.getPayDueDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		
		Date feeDueDate = pgmDateUtil.getStartOfDay(date.plusDays(month.get(date.getMonth().name())));
		Integer due = 0;
		if(person.getDueAmount() != null )
			due = (user.gethFee()+person.getDueAmount())-amount;
		else
			due = user.gethFee()-amount;
		payInGuestDao.updateFeePaidDtls(phone, guestId, due, feeDueDate);
		
	}

	@Override
	public void updateGuestInOutInfo(String phone, String guestId, Reason reason) {
		payInGuestDao.updateGuestInOutInfo(phone, guestId, reason);
	}

	@Override
	public HostelGuests getSearchGuests(String hostelNum, String name) {
		
		HostelGuests hostelGuest = new HostelGuests();
		List<PersonInfo> personInfos = payInGuestDao.getSearchGuests(hostelNum, name);
		List<Person> persons = new ArrayList<Person>();
		for(PersonInfo info : personInfos){
			persons.add(info.getGuests());
		}
		hostelGuest.setGuests(persons);
		return hostelGuest;
	}

	
}
