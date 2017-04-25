package com.bas.pgm.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
@Document(collection="hostel_guests")
public class PersonInfo {
	@Id
	private String id;
	private String hostelNum;
	private Person guests;
	
	public PersonInfo() {
		super();
	}

	public PersonInfo(String id, String hostelNum, Person guests) {
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

	public Person getGuests() {
		return guests;
	}

	public void setGuests(Person guests) {
		this.guests = guests;
	}

	@Override
	public String toString() {
		return "HostelGuestDtls [id=" + id + ", hostelNum=" + hostelNum + ", guests=" + guests + "]";
	}
	
	
}
