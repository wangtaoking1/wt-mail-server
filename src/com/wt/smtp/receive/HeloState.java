package com.wt.smtp.receive;

import java.util.regex.Pattern;

import com.wt.smtp.SMTPServer.ServerType;
import com.wt.smtp.SMTPServiceThread;

public class HeloState extends State {

    @Override
    public void handle(SMTPServiceThread service, String com, String arg) {
        if (!"helo".equals(com))
        {
            service.writeToClient("503 Error: need HELO first");
            return ;
        }
        if (this.checkEmpty(arg))
        {
            service.writeToClient("501 Invalid argument");
            return ;
        }
        
        service.writeToClient("220 ok");
        
        //State transition according to the server type 
        if (service.getType() == ServerType.FORCLIENT)
            service.getReceiver().setState(new AuthState());
        else if (service.getType() == ServerType.FORSERVER)
            service.getReceiver().setState(new MailState());
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
