package com.wt.smtp.receive;

import com.sun.org.apache.xml.internal.security.utils.Base64;
import com.wt.smtp.SMTPServer;
import com.wt.smtp.SMTPServiceThread;
import com.wt.utils.User;
import com.wt.utils.UserManager;

public class LoginState extends State {
    private String[] sta = {"username", "password"};
    private String cur;
    private User user;
    
    public LoginState() {
        this.cur = sta[0];
        user = new User();
    }
    
    @Override
    public void handle(SMTPServiceThread service, String com, String arg) { }

    public void handle(SMTPServiceThread service, String inStr) {
        if (this.cur == sta[0]) {
            //username
            try {
                this.user.setUsername(new String(Base64.decode(inStr)));
            }
            catch (Exception e) {
                this.user.setUsername("");
            }
            this.cur = sta[1];
            service.writeToClient("334 " + Base64.encode("password".getBytes()));
        }
        else {
            //password
            try {
                this.user.setPassword(new String(Base64.decode(inStr)));
            }
            catch (Exception e) {
                this.user.setPassword("");
            }
            
            if (UserManager.authUser(this.user)) {
                service.writeToClient("235 OK, go ahead");
                service.getReceiver().setState(new MailState());
            }
            else {
                service.writeToClient("535 authentication failed");
                
                service.closeConnection();
            }
        }
    }
}
