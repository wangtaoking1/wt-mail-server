package com.wt.pop3.state;

import com.wt.pop3.PopServiceThread;

/**
 * @author wangtao
 * @time 2014/12/22
 */
public abstract class State {
    public abstract void handle(PopServiceThread service, String[] args);
    public void synWithDB() {}
}
