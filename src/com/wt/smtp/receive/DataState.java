package com.wt.smtp.receive;

import java.util.regex.Pattern;

import com.wt.smtp.ServiceThread;

public class DataState extends State {
    
    @Override
    public void handle(ServiceThread service, String com, String arg) {
        if (!"data".equals(com))
        {
            service.writeToClient("503 Error: need data command");
        }
        if (!this.checkEmpty(arg))
        {
            service.writeToClient("501 Invalid argument");
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
