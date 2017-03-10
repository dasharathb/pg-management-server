package com.bas.pgm.model;

import org.springframework.data.annotation.Id;

public class Person {
	@Id
	private String id;
	private String base64Image;
	private String name;
	private String fName;
	private String gender;
	private String address;
	private String pin;
	private String aadharNo;
	private String phone;
	private String fphone;
	private String occupation;
	private String occName;
	
	public Person() {
		super();
	}

	public Person(String id, String base64Image, String name, String fName, String gender, String address, String pin,
			String aadharNo, String phone, String fphone, String occupation, String occName) {
		super();
		this.id = id;
		this.base64Image = base64Image;
		this.name = name;
		this.fName = fName;
		this.gender = gender;
		this.address = address;
		this.pin = pin;
		this.aadharNo = aadharNo;
		this.phone = phone;
		this.fphone = fphone;
		this.occupation = occupation;
		this.occName = occName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBase64Image() {
		return base64Image;
	}

	public void setBase64Image(String base64Image) {
		this.base64Image = base64Image;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getfName() {
		return fName;
	}

	public void setfName(String fName) {
		this.fName = fName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public String getAadharNo() {
		return aadharNo;
	}

	public void setAadharNo(String aadharNo) {
		this.aadharNo = aadharNo;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getFphone() {
		return fphone;
	}

	public void setFphone(String fphone) {
		this.fphone = fphone;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public String getOccName() {
		return occName;
	}

	public void setOccName(String occName) {
		this.occName = occName;
	}

	@Override
	public String toString() {
		return "Person [id=" + id + ", base64Image=" + base64Image + ", name=" + name + ", fName=" + fName + ", gender="
				+ gender + ", address=" + address + ", pin=" + pin + ", aadharNo=" + aadharNo + ", phone=" + phone
				+ ", fphone=" + fphone + ", occupation=" + occupation + ", occName=" + occName + "]";
	}

	
}
