package com.wt.pop3.state;

import java.util.ArrayList;

import com.wt.pop3.PopServer;
import com.wt.pop3.PopServiceThread;
import com.wt.utils.Manager;
import com.wt.utils.Manager.MailRole;
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
        ArrayList<Integer> del_ids = Manager.getMailIDs(MailRole.RECEIVER, delQue);
        for (int id : del_ids) {
            Manager.delMail(id);
        }
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
        if (args.length != 1 && args.length != 2) {
            service.writeToClient("-ERR Syntax error");
            return ;
        }
        if (args.length == 2) {
            if (!this.checkMailNum(MailRole.RECEIVER, args[1])) {
                service.writeToClient("-ERR error arguments");
                return ;
            }
            
            int num = Integer.parseInt(args[1]);
            int bytes = Manager.getBytes(num);
            service.writeToClient("+OK " + num + " " + bytes);
        }
        else {
            service.writeToClient(Manager.getMailStatus(
                    Manager.MailRole.RECEIVER));
            service.writeToClient(Manager.getMailStatusList() + ".");
        }
    }
    
    
    /**
     * To apply for "retr" command
     * @param service
     * @param args
     */
    private void applyRETR(PopServiceThread service, String[] args) {
        if (args.length != 2) {
            service.writeToClient("-ERR Syntax error");
            return ;
        }
        
        if (!this.checkMailNum(MailRole.RECEIVER, args[1])) {
            service.writeToClient("-ERR error arguments");
            return ;
        }
        
        int num = Integer.parseInt(args[1]);
        
        service.writeToClient(Manager.getMailMessage(MailRole.RECEIVER, num));
    }
    
    
    /**
     * To apply for "dele" command
     * @param service
     * @param args
     */
    private void applyDELE(PopServiceThread service, String[] args) {
        if (args.length != 2) {
            service.writeToClient("-ERR Syntax error");
            return ;
        }
        
        if (!this.checkMailNum(MailRole.RECEIVER, args[1])) {
            service.writeToClient("-ERR error arguments");
            return ;
        }
        
        int num = Integer.parseInt(args[1]);
        this.delQue.add(num);
    }
    
    
    /**
     * To apply for "top" command
     * @param service
     * @param args
     */
    private void applyTOP(PopServiceThread service, String[] args) {
        
    }
    
    
    /**
     * To check the validation of mail number
     * @param role
     * @param sNum
     * @return
     */
    private boolean checkMailNum(MailRole role, String sNum) {
        int num = 0;
        try {
            num = Integer.parseInt(sNum);
        }
        catch (Exception e) {
            return false;
        }
        
        int cnt = Manager.getMailCount(role);
        if (num == 0 || num > cnt) {
            return false;
        }
        return true;
    }
}
