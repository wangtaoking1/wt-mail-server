package com.wt.pop3.state;

import com.wt.pop3.PopServiceThread;
import com.wt.utils.User;

/**
 * The state receiver of pop3 service
 * @author wangtao
 * @time 2014/12/23
 */
public class PopReceiver {
    //Apply for these command servive
    private String[] commands = {"user", "pass", "stat", "sstat", "list", 
            "slist", "retr", "sretr", "dele", "sdele", "top", "stop", "noop",
            "isr", "read", "quit"};
    private State state = null;
    
    public PopReceiver(State state) {
        this.state = state;
    }
    
    public void setState(State state) {
        this.state = state;
    }
    
    
    /**
     * To handle the inStr from the client
     * @param service
     * @param inStr
     * @return
     */
    public void handleInput(PopServiceThread service, String inStr) {
        String[] args = inStr.split("\\s+");
        String cmd = args[0].toLowerCase();
        if (!checkCommand(cmd)) {
            service.writeToClient("-ERR Unknown command");
            return ;
        }
        
        if ("quit".equals(cmd)) {
            this.state.synWithDB();
            service.writeToClient("+OK Bye");
            service.closeConnection();
            return;
        }
        
        if ("noop".equals(cmd)) {
            service.writeToClient("+OK");
            return ;
        }
        
        state.handle(service, args);
    }
    
    
    /**
     * To check validation of input command
     * @param command
     * @return
     */
    public boolean checkCommand(String cmd) {
        boolean flag = false;
        for (String s : commands) {
            if (s.equals(cmd))
                flag = true;
        }
        return flag;
    }
}
