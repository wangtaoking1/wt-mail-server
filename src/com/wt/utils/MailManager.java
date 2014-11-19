package com.wt.utils;

public class MailManager {
    
    public static void handleMail(MailMessage message) {
        System.out.println("Message send successfully");
    }
    
    public static boolean isLocalServer(String server) {
        return true;
    }
    
    public static boolean isMyServer(String server) {
        return true;
    }
}
