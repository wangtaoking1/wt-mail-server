package com.wt.utils;

/**
 * @author wangtao
 * @time 2014/11/12
 */

public class MailMessage {
	private String from;
	private String to;
	private String dataFrom;
	private String dataTo;
	private String subject;
	private String content;
	private String date;
	
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	
	public String getDataFrom() {
		return dataFrom;
	}
	public void setDataFrom(String dataFrom) {
		this.dataFrom = dataFrom;
	}
	
	public String getDataTo() {
		return dataTo;
	}
	public void setDataTo(String dataTo) {
		this.dataTo = dataTo;
	}
	
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	public String getContent() {
		return this.content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getDate() {
		return this.date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	
}
