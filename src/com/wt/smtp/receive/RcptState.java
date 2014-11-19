package com.wt.smtp.receive;

import java.util.regex.Pattern;

import com.wt.smtp.SMTPServiceThread;
import com.wt.smtp.SMTPServer.ServerType;
import com.wt.utils.MailManager;

public class RcptState extends State {

    @Override
    public void handle(SMTPServiceThread service, String com, String arg) {
        if (!"rcpt".equals(com))
        {
            service.writeToClient("503 Error: need Rcpt command");
            return ;
        }
        if (this.checkArgument(arg))
        {
            service.writeToClient("501 Invalid argument");
            return ;
        }
        
        //check the mail whether is sent to local or not
        if (service.getType() == ServerType.FORSERVER) {
            if (!MailManager.isLocalServer(this.getMailAddress(
                    this.getMail(arg)))) {
                service.writeToClient("550 Wrong mail address");
                return ;
            }
            if (!this.getUsername(this.getMail(arg)).equals(
                    service.getReceiver().getMessage().getUser().getUsername())
                    ) {
                service.writeToClient("550 User not found");
                return ;
            }
        }
        
        service.getReceiver().getMessage().setTo(this.getMail(arg));
        
        service.writeToClient("220 ok");
        
        service.getReceiver().setState(new DataState());
    }

    /**
     * Check the validation of the input string
     * @param arg
     * @return
     */
    public boolean checkArgument(String arg) {
        boolean flag =  Pattern.matches("^\\w+:<\\w+@\\w+(\\.\\w+)*>$", arg);
        if (!flag)
            return false;
        
        int pos = arg.indexOf(":");
        String ahead = arg.substring(0, pos).toLowerCase();
        if (!"to".equals(ahead))
            return false;
        
        return true;
    }
    
    /**
     * Get the mail from the input string
     * @param arg
     * @return
     */
    public String getMail(String arg) {
        return arg.substring(arg.indexOf("<") + 1, arg.indexOf(">"));
    }
    
    /**
     * Get username from the 'to mail'
     * @param mail
     * @return
     */
    public String getUsername(String mail) {
        int pos = mail.indexOf("@");
        return mail.substring(0, pos);
    }
    
    /**
     * Get mail address from the 'to mail'
     * @param mail
     * @return
     */
    public String getMailAddress(String mail) {
        int pos = mail.indexOf("@");
        return mail.substring(pos + 1);
    }
    
}
