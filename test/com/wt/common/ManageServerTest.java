package com.wt.common;

import static org.junit.Assert.*;

import org.junit.Test;

public class ManageServerTest {

    @Test
    public void test() {
        Thread manageServer = new Thread(new ManageServer(5055));
        manageServer.start();
        try {
            manageServer.join();
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }

}
