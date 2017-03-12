package com.bas.pgm.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="guest")
public class Guest {
	@Id
	private int id;
	private int guestId;

	public Guest() {
		super();
	}

	public Guest(int id, int guestId) {
		super();
		this.id = id;
		this.guestId = guestId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getGuestId() {
		return guestId;
	}

	public void setGuestId(int guestId) {
		this.guestId = guestId;
	}

	@Override
	public String toString() {
		return "Guest [id=" + id + ", guestId=" + guestId + "]";
	}

}
