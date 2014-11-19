package com.wt.smtp.receive;

import com.sun.org.apache.xml.internal.security.utils.Base64;
import com.wt.smtp.SMTPServer;
import com.wt.smtp.SMTPServiceThread;
import com.wt.utils.User;
import com.wt.utils.UserManager;

public class AuthState extends State {
    @Override
    public void handle(SMTPServiceThread service, String com, String arg) {
        //auth login
        if (!("auth".equals(com) && "login".equals(arg.toLowerCase()))) {
            service.writeToClient("500 Error: need Auth login");
            return ;
        }
        
        service.writeToClient("334 " + Base64.encode("username".getBytes()));
        service.getReceiver().setState(new LoginState());
    }

    
}
