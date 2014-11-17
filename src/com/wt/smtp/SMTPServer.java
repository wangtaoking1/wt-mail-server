package com.wt.smtp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

public class SMTPServer {
	private static int port = 25;
	private static ServerSocket server = null;
	private static Executor service = null;
	
	public static Logger logger = Logger.getLogger(SMTPServer.class);
	
	public static void start() throws IOException {
		server = new ServerSocket(port);
		service = Executors.newCachedThreadPool();
		
		while(true) {
			Socket client = server.accept();
			
			logger.info("A connection from " + client.getInetAddress().getHostAddress());
			
			//Create a new thread if there is no idle thread;
			//
			service.execute(new ServiceThread(client));
		}
	}
	
	public static void close() {
		if (server != null) {
			try {
				server.close();
			}
			catch (Exception e) {
				SMTPServer.logger.error(e);
			}
		}
	}
}
