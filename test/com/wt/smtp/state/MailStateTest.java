package com.wt.smtp.receive;

import static org.junit.Assert.*;
import junit.framework.Assert;

import org.junit.Test;

public class MailStateTest {

    @Test
    public void testCheckArgument() {
        MailState state = new MailState();
        Assert.assertTrue(state.checkArgument("from:<asdfsaf@qq.com>"));
        //Assert.assertTrue(state.checkArgument("abc"));
    }

    @Test
    public void testGetMail() {
        MailState state = new MailState();
        Assert.assertEquals("abc@qq.com", state.getMail("from:<abc@qq.com>"));
    }
}
