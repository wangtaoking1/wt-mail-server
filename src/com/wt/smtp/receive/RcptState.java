package com.wt.smtp.receive;

import java.util.regex.Pattern;

import com.wt.smtp.ServiceThread;

public class RcptState extends State {

    @Override
    public void handle(ServiceThread service, String com, String arg) {
        if (!"rcpt".equals(com))
        {
            service.writeToClient("503 Error: need rcpt command");
        }
        if (this.checkArgument(arg))
        {
            service.writeToClient("501 Invalid argument");
        }
        
        service.getReceiver().getMessage().setTo(this.getMail(arg));
        
        service.writeToClient("220 ok");
        
        service.getReceiver().setState(new DataState());
    }

    public boolean checkArgument(String arg) {
        return Pattern.matches("^to:<\\w+@\\w+(\\.\\w+)*>$", arg);
    }
    
    public String getMail(String arg) {
        return arg.substring(arg.indexOf("<") + 1, arg.indexOf(">"));
    }
}
