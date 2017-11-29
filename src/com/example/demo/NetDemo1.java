package com.example.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class NetDemo1 {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		
		URL url = new URL("http://fb.163.com");
		
		URLConnection connection = url.openConnection();
		
		connection.setRequestProperty("Accept-Charset", "UTF-8");
		
		BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(),"UTF-8"));
		
		String line = "";
		
		StringBuilder sb = new StringBuilder();
		
		while(null != (line = br.readLine())) {
			sb.append(line+"\n");
		}
		System.out.println(sb.toString());
		System.out.println("111");
	}

}
