package com.wt.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class SMTPClient {
	private Socket socket = null;
	private BufferedReader input = null;
	private BufferedWriter output = null;
	private String server = null;
	private int port;
	private MailMessage message;
	
	public SMTPClient() {
	}
	
	public SMTPClient(MailMessage message) {
		this.message = message;
	}
	
	public MailMessage getMessage() {
		return message;
	}
	public void setMessage(MailMessage message) {
		this.message = message;
		this.setServerInfo();
	}
	
	private void setServerInfo() {
		int pos = this.message.getTo().indexOf("@");
		server = this.message.getTo().substring(pos + 1);
		if ("yahoo.com".equals(server) || "gmail.com".equals(server))
		{
			port = 465;
		}
		else
			port = 25;
		server = "smtp." + server;
	}
	
	
	public void setServerInfo(String server, int port) {
		this.server = server;
		this.port = port;
	}
	
	public boolean sendMail() {
		boolean flag = true;
		try {
			init();
			regist();
			
			quit();
		}
		catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}

	private void init() throws Exception {
		socket = new Socket(server, port);
		input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
	}

	/**
	 * 
	 */
	private void regist() throws IOException {
		
	}

	private void quit() throws IOException {
		
		
	}
	
	private String getResult() {
		String line = "";
		try {
			line = this.input.readLine();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return line;
	}
}
