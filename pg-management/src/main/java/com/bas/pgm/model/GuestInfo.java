package com.bas.pgm.model;

public class GuestInfo {
	private Integer totalGuests;
	private Integer presentGuests;
	public GuestInfo() {
		super();
	}
	public GuestInfo(Integer totalGuests, Integer presentGuests) {
		super();
		this.totalGuests = totalGuests;
		this.presentGuests = presentGuests;
	}
	public Integer getTotalGuests() {
		return totalGuests;
	}
	public void setTotalGuests(Integer totalGuests) {
		this.totalGuests = totalGuests;
	}
	public Integer getPresentGuests() {
		return presentGuests;
	}
	public void setPresentGuests(Integer presentGuests) {
		this.presentGuests = presentGuests;
	}
	@Override
	public String toString() {
		return "GuestInfo [totalGuests=" + totalGuests + ", presentGuests=" + presentGuests + "]";
	}
	
	
}
