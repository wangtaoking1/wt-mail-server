package com.wt.main;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @author wangtao
 * @time 2014/11/12
 */
public class MailServer {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		Socket socket = new Socket("smtp.163.com", 25);
		
		BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		PrintWriter output = new PrintWriter(socket.getOutputStream());
		
		String line = input.readLine();
		System.out.println(line);
		
		output.println("HELO smtp.163.com");
		output.flush();
		
		line = input.readLine();
		System.out.println(line);
		
		socket.close();
		input.close();
		output.close();
		System.out.println("done.");
	}
	
	
}
