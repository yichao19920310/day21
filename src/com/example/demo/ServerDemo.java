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
		System.out.println("�����������!");
		while(true) {
			System.out.println("�ȴ��ͻ��˷�����Ϣ...");
			Socket s = ss.accept();
			
			OutputStream os = s.getOutputStream();
			InputStream is = s.getInputStream();
			byte[] buffer = new byte[1024];
			int length = is.read(buffer);
			System.out.println("�յ��ͻ��˷��͵���Ϣ:\n"+new String(buffer,0,length));
			System.out.println("����ظ�������:");
			String str = scan.next();
			os.write(str.getBytes());
			os.close();
			is.close();
			
		}
		
	}

}
