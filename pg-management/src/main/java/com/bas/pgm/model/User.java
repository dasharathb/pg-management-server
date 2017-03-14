package com.bas.pgm.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
@Document(collection="user")
public class User {
	@Id
	private String id;
	private String name;
	private Integer phone;
	private String email;
	private String password;
	private Integer hFee;
	private Date date;
	
	public User() {
		super();
	}

	public User(String id, String name, Integer phone, String email, String password, Integer hFee, Date date) {
		super();
		this.id = id;
		this.name = name;
		this.phone = phone;
		this.email = email;
		this.password = password;
		this.hFee = hFee;
		this.date = date;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getPhone() {
		return phone;
	}

	public void setPhone(Integer phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer gethFee() {
		return hFee;
	}

	public void sethFee(Integer hFee) {
		this.hFee = hFee;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", phone=" + phone + ", email=" + email + ", password=" + password
				+ ", hFee=" + hFee + ", date=" + date + "]";
	}	
	
}
