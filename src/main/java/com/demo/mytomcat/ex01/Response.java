package com.demo.mytomcat.ex01;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

public class Response {

	private static final int BUFFER_SIZE = 1024;
	
	private Request request;
	
	private OutputStream output;
	
	public Response(OutputStream output){
		this.output = output;
	}
	
	public void setRequest(Request request){
		this.request = request;
	}
	
	public void sendStaticResource() throws IOException{
		byte[] bytes = new byte[BUFFER_SIZE];
		FileInputStream fis = null;
		try {
			System.out.println(HttpServer.WEB_ROOT);
			System.out.println(this.request.getUri());
			File file = new File(HttpServer.WEB_ROOT, this.request.getUri());

			if (file.exists()) {
				fis = new FileInputStream(file);
				int ch = fis.read(bytes, 0, BUFFER_SIZE);
				while (ch != -1) {
					output.write(bytes, 0, ch);
					ch = fis.read(bytes, 0, BUFFER_SIZE);
				}
			} else {
				// file not found
				String errorMsg = "HTTP/1.1 404 File Not Found\r\n"
						+ "Content-Type: text/html\r\n"
						+ "Content-Length: 23\r\n" + "\r\n"
						+ "<h1>File Not Found</h1>";
				output.write(errorMsg.getBytes());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if (fis != null)
				fis.close();
		}
	}
	
	
	
}
