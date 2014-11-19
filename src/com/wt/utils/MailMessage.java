package com.wt.utils;

/**
 * @author wangtao
 * @time 2014/11/12
 */

public class MailMessage {
    private User user;
    private String from;
    private String to;
    private String content;
    
    public MailMessage() {
        this.user = new User();
    }
    public MailMessage(User user) {
        this.user = user;
    }
    
    public User getUser() {
        return this.user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    
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
    
    public String getContent() {
        return this.content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    
}
