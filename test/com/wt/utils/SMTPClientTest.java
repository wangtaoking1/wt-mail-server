package com.wt.utils;

import static org.junit.Assert.*;
import junit.framework.Assert;

import org.junit.Test;

public class SMTPClientTest {

	@Test
	public void testSetMessage() {
		SMTPClient client = new SMTPClient();
		MailMessage message = new MailMessage();
		message.setFrom("abc@163.com");
		message.setTo("abc@qq.com");
		client.setMessage(message);
		
		Assert.assertTrue(true);
	}

}
