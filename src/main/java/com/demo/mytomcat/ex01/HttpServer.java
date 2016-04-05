package com.demo.mytomcat.ex01;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;



public class HttpServer {
	/**
	 * web资源根路径
	 */
	public static final String WEB_ROOT = "target" + File.separator + "classes"
			+ File.separator + "webroot";
	
	
	/**
	 * shutdown command
	 */
	protected static final String SHUTDOWN_COMMAND = "/SHUTDOWN";
	
	/**
	 * shut down command received
	 */
	protected boolean shutdown = false;
	
	public static void main(String[] args) {
		HttpServer server = new HttpServer();
		server.await();
	}
	
	public void await(){
		ServerSocket serverSocket = null;
		int port = 8080;
		try {
			serverSocket = new ServerSocket(port,1,InetAddress.getByName("127.0.0.1"));
		} catch ( IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		// loop waiting for a request
		while (!this.shutdown) {
			Socket socket = null;
			InputStream input = null;
			OutputStream output = null;
			try {
				socket = serverSocket.accept();
				input = socket.getInputStream();
				output = socket.getOutputStream();
				// create a request object
				Request request = new Request(input);
				request.parse();

				// create a response object
				Response response = new Response(output);
				response.setRequest(request);
				response.sendStaticResource();
				// close socket
				socket.close();
				// check if the previouus uri is shutdown command
				shutdown = request.getUri().equals(SHUTDOWN_COMMAND);
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
			
		}
		
		
		
		
		
		
	}
	
	
	
}
