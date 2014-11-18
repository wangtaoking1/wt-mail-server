package com.wt.smtp.receive;


import com.wt.smtp.ServiceThread;

public abstract class State {
    //Handle the input string
    public abstract void handle(ServiceThread service, String com, String arg);

    public void handle(ServiceThread service, String inStr) {}
}
