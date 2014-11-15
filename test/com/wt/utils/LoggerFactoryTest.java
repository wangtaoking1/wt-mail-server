package com.wt.utils;

import static org.junit.Assert.*;
import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.wt.main.MailServer;

public class LoggerFactoryTest {

	@Test
	public void testGetLogger() {
		Logger logger = LoggerFactory.getLogger(MailServer.class);
		logger.debug("this is debug");
		logger.info("this is info");
		logger.error("this is error");
		
		Assert.assertTrue(true);
	}

}
