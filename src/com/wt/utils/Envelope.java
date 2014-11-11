package com.wt.utils;

public class Envelope {
	private String fromAddr;
	private String toAddr;
	
	public Envelope(String fromAddr, String toAddr) {
		this.fromAddr = fromAddr;
		this.toAddr = toAddr;
	}
	
	public void setFromAddr(String fromAddr) {
		this.fromAddr = fromAddr;
	}
	public String getFromAddr() {
		return fromAddr;
	}
	
	public void setToAddr(String toAddr) {
		this.toAddr = toAddr;
	}
	public String getToAddr() {
		return toAddr;
	}
	
	/**
	 * 
	 * @return The encapsulation of the mail envelope
	 * 
	 */
	public String showEnvelope() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("MAIL From: <");
		buffer.append(fromAddr);
		buffer.append(">\nRCPT To: <");
		buffer.append(toAddr);
		buffer.append(">\n");
		return buffer.toString();
	}
}
