package com.wt.utils;

/**
 * @author wangtao
 * @time 2014/11/12
 */

public class MailMessage {
	private String from;
	private String to;
	private String subject;
	private String content;
	
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
	
	
	/**
	 * 
	 * @return "The body of the message to send"
	 */
	public String getMessageBody() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("From:<" + this.from + ">\n");
		buffer.append("To:<" + this.to + ">\n");
		buffer.append("Subject:" + this.subject + "\n");
		buffer.append(this.content + "\n.");
		
		return buffer.toString();
	}
	
}
