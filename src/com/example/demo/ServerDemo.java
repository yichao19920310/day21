package com.example.demo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ServerDemo {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		Scanner scan = new Scanner(System.in);
		ServerSocket ss = new ServerSocket(1000);
		System.out.println("服务端已启动!");
		while(true) {
			System.out.println("等待客户端发送消息...");
			Socket s = ss.accept();
			
			OutputStream os = s.getOutputStream();
			InputStream is = s.getInputStream();
			byte[] buffer = new byte[1024];
			int length = is.read(buffer);
			System.out.println("收到客户端发送的消息:\n"+new String(buffer,0,length));
			System.out.println("输入回复的内容:");
			String str = scan.next();
			os.write(str.getBytes());
			os.close();
			is.close();
			
		}
		
	}

}
