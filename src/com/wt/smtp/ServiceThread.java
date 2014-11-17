package com.wt.smtp;

import java.net.Socket;

/**
 * This is a thread class to accept the message from the client
 * @author wangtao
 * @time 2014/11/17
 */
public class ServiceThread implements Runnable {
	
	private Socket client = null;
	private static int num = 0;
	
	public ServiceThread(Socket client) {
		this.client = client;
	}
	
	@Override
	public void run() {
		System.out.println(num++);
		try {
			Thread.sleep(2000);
		}
		catch (Exception e) {
			SMTPServer.logger.error(e);
		}
	}
	
}
