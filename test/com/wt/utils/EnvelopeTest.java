package com.wt.utils;

import static org.junit.Assert.*;
import junit.framework.Assert;

import org.junit.Test;

public class EnvelopeTest {

	@Test
	public void testShowEnvelope() {
		Envelope env = new Envelope("abc@qq.com", "123@sina.com");
		String expected = "MAIL From: <abc@qq.com>\nRCPT To: <123@sina.com>\n";
		String actual = env.showEnvelope();
		Assert.assertEquals(expected, actual);
	}

}
