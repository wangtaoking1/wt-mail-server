package com.wt.smtp.receive;

import java.util.regex.Pattern;

import com.wt.smtp.SMTPServiceThread;

public class DataState extends State {
    
    @Override
    public void handle(SMTPServiceThread service, String com, String arg) {
        if (!"data".equals(com))
        {
            service.writeToClient("503 Error: need Data command");
            return ;
        }
        if (!this.checkEmpty(arg))
        {
            service.writeToClient("501 Invalid argument");
            return ;
        }
        
        service.writeToClient("354 End data with <CR><LF>.<CR><LF>");
        
        service.getReceiver().setState(new SendState());
    }

    /**
     * Check the string whether or not is blank
     * @param arg
     * @return
     */
    public boolean checkEmpty(String arg) {
        return Pattern.matches("\\s*", arg);
    }
}
