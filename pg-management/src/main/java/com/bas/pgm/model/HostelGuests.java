package com.bas.pgm.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
@Document(collection="hostel_guests")
public class HostelGuests {
	@Id
	private String id;
	private String hostelNum;
	private List<Person> guests;
	
	public HostelGuests() {
		super();
	}

	public HostelGuests(String id, String hostelNum, List<Person> guests) {
		super();
		this.id = id;
		this.hostelNum = hostelNum;
		this.guests = guests;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getHostelNum() {
		return hostelNum;
	}

	public void setHostelNum(String hostelNum) {
		this.hostelNum = hostelNum;
	}

	public List<Person> getGuests() {
		return guests;
	}

	public void setGuests(List<Person> guests) {
		this.guests = guests;
	}

	@Override
	public String toString() {
		return "HostelGuestDtls [id=" + id + ", hostelNum=" + hostelNum + ", guests=" + guests + "]";
	}
	
	
}
