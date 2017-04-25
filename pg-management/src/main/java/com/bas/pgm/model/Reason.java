package com.bas.pgm.model;

public class Reason {
	private String inout;
	private String reason;
	public Reason() {
		super();
	}
	public Reason(String inout, String reason) {
		super();
		this.inout = inout;
		this.reason = reason;
	}
	public String getInout() {
		return inout;
	}
	public void setInout(String inout) {
		this.inout = inout;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	@Override
	public String toString() {
		return "Reason [inout=" + inout + ", reason=" + reason + "]";
	}
	
	
}
