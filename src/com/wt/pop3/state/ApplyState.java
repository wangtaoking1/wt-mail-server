package com.wt.pop3.state;

import java.util.ArrayList;

import com.wt.pop3.PopServer;
import com.wt.pop3.PopServiceThread;
import com.wt.utils.Manager;
import com.wt.utils.Manager.MailRole;
import com.wt.utils.User;

/**
 * The second state of pop3 service
 * @author wangtao
 * @time 2014/12/23
 */
public class ApplyState extends State {
    private User user = null;
    private ArrayList<Integer> delReceQue = null;
    private ArrayList<Integer> delSendQue = null;
    
    
    public ApplyState(User user) {
        this.user = user;
        this.delReceQue = new ArrayList<Integer>();
        this.delSendQue = new ArrayList<Integer>();
    }
    
    
    @Override
    public void handle(PopServiceThread service, String[] args) {
        String cmd = args[0].toLowerCase();
        switch (cmd) {
        case "stat":
            this.applySTAT(service, args);
            break;
        case "sstat":
            this.applySSTAT(service, args);
            break;
        case "list":
            this.applyLIST(service, args);
            break;
        case "slist":
            this.applySLIST(service, args);
            break;
        case "retr":
            this.applyRETR(service, args);
            break;
        case "sretr":
            this.applySRETR(service, args);
            break;
        case "dele":
            this.applyDELE(service, args);
            break;
        case "sdele":
            this.applySDELE(service, args);
            break;
        case "top":
            this.applyTOP(service, args);
            break;
        case "stop":
            this.applySTOP(service, args);
            break;
        case "isr":
            this.applyISR(service, args);
            break;
        case "read":
            this.applyREAD(service, args);
            break;
        default:
            service.writeToClient("-ERR Unknown command");
            break;
        }
    }
    
    
    /**
     * Syn the delete operation with DB when quit the connection
     */
    @Override
    public void synWithDB() {
        //del the receive mail
        ArrayList<Integer> del_rece_ids = Manager.getMailIDs(
                user.getUsername(), MailRole.RECEIVER, delReceQue);
        for (int id : del_rece_ids) {
            Manager.delMail(id);
        }
        
        //del the send mail
        ArrayList<Integer> del_send_ids = Manager.getMailIDs(
                user.getUsername(), MailRole.SENDER, delSendQue);
        for (int id : del_send_ids) {
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
        
        String ret = Manager.getMailStatus(this.user.getUsername(), 
                Manager.MailRole.RECEIVER);
        service.writeToClient("+OK " + ret);
        return ;
    }
    
    
    /**
     * To apply for "sstat" command
     * @param service
     * @param args
     */
    private void applySSTAT(PopServiceThread service, String[] args) {
        if (args.length != 1) {
            service.writeToClient("-ERR Syntax error");
            return ;
        }
        
        String ret = Manager.getMailStatus(this.user.getUsername(), 
                Manager.MailRole.SENDER);
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
            if (!this.checkMailNum(user.getUsername(), MailRole.RECEIVER, 
                    args[1])) {
                service.writeToClient("-ERR error arguments");
                return ;
            }
            
            int num = Integer.parseInt(args[1]);
            int bytes = Manager.getBytes(user.getUsername(), 
                    MailRole.RECEIVER, num);
            service.writeToClient("+OK " + num + " " + bytes);
        }
        else {
            service.writeToClient("+OK " + Manager.getMailStatus(
                    user.getUsername(), Manager.MailRole.RECEIVER));
            service.writeToClient(Manager.getMailStatusList(user.getUsername(),
                    MailRole.RECEIVER) + ".");
        }
    }
    
    
    /**
     * To apply for "slist" command
     * @param service
     * @param args
     */
    private void applySLIST(PopServiceThread service, String[] args) {
        if (args.length != 1 && args.length != 2) {
            service.writeToClient("-ERR Syntax error");
            return ;
        }
        if (args.length == 2) {
            if (!this.checkMailNum(user.getUsername(), MailRole.SENDER, 
                    args[1])) {
                service.writeToClient("-ERR error arguments");
                return ;
            }
            
            int num = Integer.parseInt(args[1]);
            
            int bytes = Manager.getBytes(user.getUsername(), 
                    MailRole.SENDER, num);
            service.writeToClient("+OK " + num + " " + bytes);
        }
        else {
            service.writeToClient("+OK " + Manager.getMailStatus(
                    user.getUsername(), Manager.MailRole.SENDER));
            
            service.writeToClient(Manager.getMailStatusList(user.getUsername(),
                    MailRole.SENDER) + ".");
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
        
        if (!this.checkMailNum(user.getUsername(), MailRole.RECEIVER, 
                args[1])) {
            service.writeToClient("-ERR error arguments");
            return ;
        }
        
        int num = Integer.parseInt(args[1]);
        
        service.writeToClient(Manager.getMailMessage(user.getUsername(), 
                MailRole.RECEIVER, num) + "\n.");
    }
    
    
    /**
     * To apply for "sretr" command
     * @param service
     * @param args
     */
    private void applySRETR(PopServiceThread service, String[] args) {
        if (args.length != 2) {
            service.writeToClient("-ERR Syntax error");
            return ;
        }
        
        if (!this.checkMailNum(user.getUsername(), MailRole.SENDER, 
                args[1])) {
            service.writeToClient("-ERR error arguments");
            return ;
        }
        
        int num = Integer.parseInt(args[1]);
        
        service.writeToClient(Manager.getMailMessage(user.getUsername(), 
                MailRole.SENDER, num) + "\n.");
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
        
        if (!this.checkMailNum(user.getUsername(), MailRole.RECEIVER, 
                args[1])) {
            service.writeToClient("-ERR error arguments");
            return ;
        }
        
        int num = Integer.parseInt(args[1]);
        this.delReceQue.add(num);
    }
    
    
    /**
     * To apply for "sdele" command
     * @param service
     * @param args
     */
    private void applySDELE(PopServiceThread service, String[] args) {
        if (args.length != 2) {
            service.writeToClient("-ERR Syntax error");
            return ;
        }
        
        if (!this.checkMailNum(user.getUsername(), MailRole.SENDER, 
                args[1])) {
            service.writeToClient("-ERR error arguments");
            return ;
        }
        
        int num = Integer.parseInt(args[1]);
        this.delSendQue.add(num);
    }
    
    
    /**
     * To apply for "top" command
     * @param service
     * @param args
     */
    private void applyTOP(PopServiceThread service, String[] args) {
        if (args.length != 3) {
            service.writeToClient("-ERR Syntax error");
            return ;
        }
        
        if (!this.checkMailNum(user.getUsername(), MailRole.RECEIVER, 
                args[1])) {
            service.writeToClient("-ERR error arguments");
            return ;
        }
        
        int num = 0;
        try {
            num = Integer.parseInt(args[2]);
        }
        catch (Exception e) {
            service.writeToClient("-ERR error arguments");
            return ;
        }
        
//        PopServer.logger.debug(args[0] + " " + args[1] + " " + args[2]);
        
        //Get mail id
        int id = Manager.getMailID(user.getUsername(), MailRole.RECEIVER, Integer.parseInt(
                args[1]));
        
//        PopServer.logger.debug(id);
        
        //Get header and top num lines content
        String content = Manager.getTopContend(id, num);
        service.writeToClient(content + ".");
    }
    
    
    /**
     * To apply for "stop" command
     * @param service
     * @param args
     */
    private void applySTOP(PopServiceThread service, String[] args) {
        if (args.length != 3) {
            service.writeToClient("-ERR Syntax error");
            return ;
        }
        
        if (!this.checkMailNum(user.getUsername(), MailRole.SENDER, 
                args[1])) {
            service.writeToClient("-ERR error arguments");
            return ;
        }
        
        int num = 0;
        try {
            num = Integer.parseInt(args[2]);
        }
        catch (Exception e) {
            service.writeToClient("-ERR error arguments");
            return ;
        }
        
        PopServer.logger.debug(args[0] + " " + args[1] + " " + args[2]);
        
        //Get mail id
        int id = Manager.getMailID(user.getUsername(), MailRole.SENDER, 
                Integer.parseInt(args[1]));
        
        PopServer.logger.debug(id);
        
        //Get header and top num lines content
        String content = Manager.getTopContend(id, num);
        service.writeToClient(content + ".");
    }
    
    
    /**
     * To apply for "isr" command
     * @param service
     * @param args
     */
    private void applyISR(PopServiceThread service, String[] args) {
        if (args.length != 2) {
            service.writeToClient("-ERR Syntax error");
            return ;
        }
        
        if (!this.checkMailNum(user.getUsername(), MailRole.RECEIVER, 
                args[1])) {
            service.writeToClient("-ERR error arguments");
            return ;
        }
        
        int num = Integer.parseInt(args[1]);
        
        if (Manager.getReadStatus(user.getUsername(), MailRole.RECEIVER, num))
            service.writeToClient("1");
        else
            service.writeToClient("0");
    }
    
    
    /**
     * To apply for "read" command
     * @param service
     * @param args
     */
    private void applyREAD(PopServiceThread service, String[] args) {
        if (args.length != 2) {
            service.writeToClient("-ERR Syntax error");
            return ;
        }
        
        if (!this.checkMailNum(user.getUsername(), MailRole.RECEIVER, 
                args[1])) {
            service.writeToClient("-ERR error arguments");
            return ;
        }
        
        int num = Integer.parseInt(args[1]);
        
        if (Manager.readMail(user.getUsername(), MailRole.RECEIVER, num))
            service.writeToClient("+OK");
        else
            service.writeToClient("-ERR");
    }
    
    
    /**
     * To check the validation of mail number
     * @param role
     * @param sNum
     * @return
     */
    private boolean checkMailNum(String username, MailRole role, String sNum) {
        int num = 0;
        try {
            num = Integer.parseInt(sNum);
        }
        catch (Exception e) {
            return false;
        }
        
        int cnt = Manager.getMailCount(username, role);
        if (num == 0 || num > cnt) {
            return false;
        }
        return true;
    }
}
