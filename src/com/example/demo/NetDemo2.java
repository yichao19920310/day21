package com.example.demo;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class NetDemo2 {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		URL url = new URL("http://heroes.nos.netease.com/2/cms/wp/wp54/Alexstrasza_1920x1080.jpg");
		URLConnection conn = url.openConnection();
		HttpURLConnection hcon = (HttpURLConnection) conn;
		if(hcon.getResponseCode() != 200) {
			return;
		}
		InputStream is = hcon.getInputStream();
		OutputStream os = new FileOutputStream("F:\\Alexstrasza_1920x1080.jpg");
		int size = hcon.getContentLength();
		System.out.println("大小:"+size);
		int length = 0;
		double currentLength = 0;
		byte [] buffer = new byte[1024];
		while(-1 != (length = is.read(buffer))) {
			currentLength += length;
			System.out.println("已读取:"+(int)(currentLength/size*100)+"%");
			os.write(buffer, 0, length);
		}
		os.close();
		is.close();
		os = null;
		is = null;
	}

}
