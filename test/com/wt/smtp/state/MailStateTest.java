package com.wt.smtp.state;

import static org.junit.Assert.*;
import junit.framework.Assert;

import org.junit.Test;

import com.wt.smtp.state.MailState;

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
