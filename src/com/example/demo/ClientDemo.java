package com.example.demo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ClientDemo {

	public static void main(String[] args) throws UnknownHostException, IOException {
		// TODO Auto-generated method stub

		Scanner scan = new Scanner(System.in);
		System.out.println("�ͻ���������!");
		while(true) {
			Socket s = new Socket("127.0.0.1",1000);
			OutputStream os = s.getOutputStream();
			InputStream is = s.getInputStream();
			System.out.println("����Ҫ���͵�����:");
			os.write(scan.next().getBytes());
			byte[] buffer = new byte[1024];
			int length = is.read(buffer);
			System.out.println("�յ�����˷��͵�����:\n"+new String(buffer,0,length));
			os.close();
			is.close();
		}
	}

}
