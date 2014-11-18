package com.wt.smtp.receive;

import com.sun.org.apache.xml.internal.security.utils.Base64;
import com.wt.smtp.SMTPServer;
import com.wt.smtp.ServiceThread;
import com.wt.utils.User;
import com.wt.utils.UserManager;

public class AuthState extends State {
    private String[] sta = {"auth", "username", "password"};
    private String cur;
    private User user;
    
    public AuthState() {
        this.cur = sta[0];
        user = new User();
    }

    @Override
    public void handle(ServiceThread service, String com, String arg) {}

    public void handle(ServiceThread service, String inStr) {
        if (this.cur == sta[0]) {
            if (!"auth login".equals(inStr.toLowerCase())) {
                service.writeToClient("503 Error: Auth Login first");
                return ;
            }
            this.cur = sta[1];
            service.writeToClient("334 " + Base64.encode(cur.getBytes()));
        }
        else if (this.cur == sta[1]) {
            try {
                this.user.setUsername(new String(Base64.decode(inStr)));
            }
            catch (Exception e) {
                return ;
            }
            this.cur = sta[2];
            service.writeToClient("334 " + Base64.encode(cur.getBytes()));
        }
        else {
            try {
                this.user.setPassword(new String(Base64.decode(inStr)));
            }
            catch (Exception e) {
                return;
            }
            if (UserManager.authUser(this.user)) {
                service.writeToClient("235 OK, go ahead");
                service.getReceiver().setState(new MailState());
            }
            else {
                service.writeToClient("535 authentication failed");
                
                SMTPServer.logger.info("User " + this.user.getUsername() + 
                        " authentication failed");
                
                service.closeConnection();
            }
        }
    }
}
