package com.wt.pop3.state;

import com.wt.pop3.PopServer;
import com.wt.pop3.PopServiceThread;
import com.wt.utils.Manager;
import com.wt.utils.User;

/**
 * The auth state of pop3 service
 * @author wangtao
 * @time 2014/12/23
 */
public class AuthState extends State {
    private String cur = null;
    private User user = null;
    
    public AuthState() {
        this.cur = "user";
        this.user = new User();
    }
    
    @Override
    public void handle(PopServiceThread service, String[] args) {
        String cmd = args[0];
        if (!cmd.equalsIgnoreCase("user") && !cmd.equalsIgnoreCase("pass")) {
            service.writeToClient("-ERR Auth first");
            return ;
        }
        if (!cur.equalsIgnoreCase(cmd)) {
            service.writeToClient("-ERR " + cur + " need");
            return ;
        }
        if (args.length != 2) {
            service.writeToClient("-ERR Syntax error");
            return ;
        }
        
        if (cur.equals("user")) {
            if (!Manager.isLocalUser(args[1])) {
                service.writeToClient("-ERR User not found");
                return ;
            }
            service.writeToClient("+OK");
            this.user.setUsername(args[1]);
            this.cur = "pass";
        }
        else {
            this.user.setPassword(args[1]);
            if (!Manager.authUser(user)) {
                service.writeToClient("-ERR INCORRECT PASSWORD");
                PopServer.logger.info("User " + user.getUsername() + 
                        " login failed");
                service.closeConnection();
                return ;
            }
            service.writeToClient("+OK Authentication succeeded");
            PopServer.logger.debug("User " + user.getUsername() + 
                    " login successfully");
            service.getReceiver().setState(new ApplyState(this.user));
            this.cur = "quit";
        }
    }
    
}
