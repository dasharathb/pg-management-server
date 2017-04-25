package com.bas.pgm.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bas.pgm.dao.PayInGuestDao;
import com.bas.pgm.model.Guest;
import com.bas.pgm.model.HostelGuests;
import com.bas.pgm.model.Person;
import com.bas.pgm.model.Reason;
import com.bas.pgm.mongo.repo.HostelGuestRepo;
import com.bas.pgm.util.PgmDateUtil;

@Service(value="payInGuestService")
public class PayInGuestServiceImpl implements PayInGuestService {

	@Autowired
	PayInGuestDao payInGuestDao;
	
	@Autowired
	HostelGuestRepo hostelGuestRepo;
	
	@Autowired
	PgmDateUtil pgmDateUtil;
	
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
	public void updateFeePaidDtls(String phone, String guestId, String amount) {
		Person person = payInGuestDao.getGuestInfo(phone, guestId);
		
		LocalDate date = person.getPayDueDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		Date feeDueDate = pgmDateUtil.getStartOfDay(date.plusDays(30));
		Integer due = 0;
		if(person.getDueAmount() != null )
			due = (person.getAmount()+person.getDueAmount())-Integer.parseInt(amount);
		else
			due = person.getAmount()-Integer.parseInt(amount);
		payInGuestDao.updateFeePaidDtls(phone, guestId, due, feeDueDate);
		
	}

	@Override
	public void updateGuestInOutInfo(String phone, String guestId, Reason reason) {
		payInGuestDao.updateGuestInOutInfo(phone, guestId, reason);
	}

	
}
