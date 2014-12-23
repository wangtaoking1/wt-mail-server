package com.wt.pop3.state;

import java.util.ArrayList;

import com.wt.pop3.PopServiceThread;
import com.wt.utils.Manager;
import com.wt.utils.User;

public class ApplyState extends State {
    private User user = null;
    private ArrayList<Integer> delQue = null;
    
    public ApplyState(User user) {
        this.user = user;
        this.delQue = new ArrayList<Integer>();
    }
    
    @Override
    public void handle(PopServiceThread service, String[] args) {
        String cmd = args[0].toLowerCase();
        switch (cmd) {
        case "stat":
            this.applySTAT(service, args);
            break;
        case "list":
            this.applyLIST(service, args);
            break;
        case "retr":
            this.applyRETR(service, args);
            break;
        case "dele":
            this.applyDELE(service, args);
            break;
        case "top":
            this.applyTOP(service, args);
            break;
        default:
            service.writeToClient("-ERR Unknown command");
            break;
        }
    }
    
    @Override
    public void synWithDB() {
        // TODO: syn operation with db
    }
    
    /**
     * To apply for "stat" command
     * @param service
     * @param args
     */
    private void applySTAT(PopServiceThread service, String[] args) {
        if (args.length != 1) {
            service.writeToClient("-ERR Syntax error");
            return ;
        }
        
        String ret = Manager.getMailStatus(Manager.MailRole.RECEIVER);
        service.writeToClient("+OK " + ret);
        return ;
    }
    
    /**
     * To apply for "list" command
     * @param service
     * @param args
     */
    private void applyLIST(PopServiceThread service, String[] args) {
        
    }
    
    /**
     * To apply for "retr" command
     * @param service
     * @param args
     */
    private void applyRETR(PopServiceThread service, String[] args) {
        
    }
    
    /**
     * To apply for "dele" command
     * @param service
     * @param args
     */
    private void applyDELE(PopServiceThread service, String[] args) {
        
    }
    
    /**
     * To apply for "top" command
     * @param service
     * @param args
     */
    private void applyTOP(PopServiceThread service, String[] args) {
        
    }
}
