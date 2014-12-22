package com.wt.smtp.state;

import com.wt.smtp.SMTPServer;
import com.wt.smtp.SMTPServiceThread;
import com.wt.utils.MailMessage;

public class SmtpReceiver {
    private String[] commands = {"helo", "auth", "mail", "rcpt", "data", "quit"};
    private State state;
    private MailMessage message;

    public SmtpReceiver() {
        this.message = new MailMessage();
    }
    public SmtpReceiver(State state) {
        this.state = state;
        this.message = new MailMessage();
    }

    public State getState() {
        return this.state;
    }
    public void setState(State state) {
        this.state = state;
    }

    public MailMessage getMessage() {
        return this.message;
    }
    public void setMessage(MailMessage message) {
        this.message = message;
    }

    /**
     * Handle the string from the client in connection
     * @param service "the service thread"
     * @param inStr
     */
    public void handleInput(SMTPServiceThread service, String inStr) {
        if ((state instanceof SendState) || (state instanceof LoginState)) {
            this.state.handle(service, inStr);
            return ;
        }

        if (!checkCommand(inStr)) {
            service.writeToClient("500 Invalid command");
            return ;
        }
        String com = this.getCommand(inStr);
        String arg = this.getArgument(inStr);

        if ("quit".equals(com)) {
            service.closeConnection();
            return;
        }

        state.handle(service, com, arg);
    }

    /**
     * Check the validation of the input string
     * @param inStr
     * @return
     */
    private boolean checkCommand(String inStr) {
        if ("".equals(inStr))
            return false;

        String com = getCommand(inStr);

        SMTPServer.logger.debug("Command: " + inStr);

        for (int i = 0; i < commands.length; i++)
            if (commands[i].endsWith(com))
                return true;
        return false;
    }

    /**
     * Get the command from the input string
     * @param inStr
     * @return
     */
    private String getCommand(String inStr) {
        int bPos = inStr.indexOf(" ");
        if (bPos == -1)
            return inStr.toLowerCase();
        return inStr.substring(0, bPos).toLowerCase();
    }

    /**
     * Get the argument from the input string
     * @param inStr
     * @return
     */
    private String getArgument(String inStr) {
        int bPos = inStr.indexOf(" ");
        if (bPos == -1)
            return "";
        return inStr.substring(bPos + 1, inStr.length());
    }

}
