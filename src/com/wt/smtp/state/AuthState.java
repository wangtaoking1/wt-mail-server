package com.wt.smtp.state;

import org.apache.commons.codec.binary.Base64;
import com.wt.smtp.SMTPServer;
import com.wt.smtp.SMTPServiceThread;
import com.wt.utils.User;

public class AuthState extends State {
    @Override
    public void handle(SMTPServiceThread service, String com, String arg) {
        //auth login
        if (!("auth".equals(com) && "login".equals(arg.toLowerCase()))) {
            service.writeToClient("500 Error: need Auth login");
            return ;
        }

        service.writeToClient("334 " + Base64.encodeBase64String(
            "username".getBytes()));
        service.getReceiver().setState(new LoginState());
    }

    
}
