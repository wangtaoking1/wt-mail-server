package com.wt.smtp.receive;

import java.io.IOException;

import com.wt.smtp.ServiceThread;
import com.wt.utils.MailManager;
import com.wt.utils.MailMessage;

public class SendState extends State {

    private StringBuffer buffer = new StringBuffer();
    
    @Override
    public void handle(ServiceThread service, String com, String arg) {}
    
    public void handle(ServiceThread service, String inStr) {
        if (".".equals(inStr)) {
            service.getReceiver().getMessage().setContent(buffer.toString());
            MailManager.handleMail(service.getReceiver().getMessage());
            
            service.writeToClient("250 ok");
            
            service.getReceiver().setMessage(new MailMessage());
            service.getReceiver().setState(new MailState());
            return ;
        }
        
        buffer.append(inStr + "\n");
    }
}
