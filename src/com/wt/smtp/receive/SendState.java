package com.wt.smtp.receive;

import com.wt.smtp.SMTPServiceThread;
import com.wt.utils.MailManager;
import com.wt.utils.MailMessage;

public class SendState extends State {

    private StringBuffer buffer = new StringBuffer();
    
    @Override
    public void handle(SMTPServiceThread service, String com, String arg) {}
    
    public void handle(SMTPServiceThread service, String inStr) {
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
