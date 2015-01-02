package com.wt.smtp.state;

import org.apache.commons.codec.binary.Base64;

import com.wt.pop3.PopServer;
import com.wt.smtp.SMTPServer;
import com.wt.smtp.SMTPServiceThread;
import com.wt.utils.User;
import com.wt.utils.Manager;

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
                this.user.setUsername(new String(Base64.decodeBase64(inStr)));
            }
            catch (Exception e) {
                this.user.setUsername("");
            }
            this.cur = sta[1];
            service.writeToClient("334 " + Base64.encodeBase64(
                "password".getBytes()));
        }
        else {
            //password
            try {
                this.user.setPassword(new String(Base64.decodeBase64(inStr)));
            }
            catch (Exception e) {
                this.user.setPassword("");
            }
            
            if (Manager.authUser(this.user)) {
                service.getReceiver().getMessage().setUser(this.user);
                service.writeToClient("235 OK, go ahead");
                SMTPServer.logger.debug("User " + user.getUsername() + 
                        " login successfully");
                service.getReceiver().setState(new MailState());
            }
            else {
                service.writeToClient("535 authentication failed");
                SMTPServer.logger.info("User " + user.getUsername() + 
                        " login failed");
                service.closeConnection();
            }
        }
    }
}
