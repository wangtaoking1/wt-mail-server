package com.wt.smtp.receive;

import java.util.regex.Pattern;

import com.wt.smtp.SMTPServer.ServerType;
import com.wt.smtp.SMTPServiceThread;
import com.wt.utils.Manager;
import com.wt.smtp.SMTPServer;

public class MailState extends State {

    @Override
    public void handle(SMTPServiceThread service, String com, String arg) {
        if (!"mail".equals(com)) {
            service.writeToClient("503 Error: need Mail command");
            return ;
        }
        if (!this.checkArgument(arg)) {
            service.writeToClient("501 Invalid argument");
            return ;
        }

        //check the mail whether is sent from local or not
        if (service.getType() == ServerType.FORCLIENT) {
            if (!Manager.isLocalServer(Manager.getMailServer(
                    this.getMail(arg)))) {
                service.writeToClient("550 Wrong mail address");
                return ;
            }

            // SMTPServer.logger.debug(this.getUsername(this.getMail(arg)));
            // SMTPServer.logger.debug(service.getReceiver().getMessage()
            //     .getUser().getUsername());

            if (!Manager.getMailUser(this.getMail(arg)).equals(
                        service.getReceiver().getMessage().getUser().getUsername())
               ) {
                service.writeToClient("553 You are not authorized to send " +
                                      "mail, authentication is required");
                return ;
            }
        }

        service.getReceiver().getMessage().setFrom(this.getMail(arg));

        service.writeToClient("250 Mail OK");

        service.getReceiver().setState(new RcptState());
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
        if (!"from".equals(ahead))
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

}
