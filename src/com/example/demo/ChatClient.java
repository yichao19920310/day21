/**  
 * @Title: ChatClient.java  
 * @Package com.example.demo  
 * @Description: TODO(用一句话描述该文件做什么)  
 * @author Administrator  
 * @date 2017年11月28日  
 * @version V1.0  
 */  
package com.example.demo;

/**  
 * @ClassName: ChatClient  
 * @Description: TODO(这里用一句话描述这个类的作用)  
 * @author Administrator  
 * @date 2017年11月28日  
 *    
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {

	private Socket clientSocket;

	/**
	 * 有参构造来完成 Host和Port的初始化操作
	 * 
	 * @param host
	 *            主机地址
	 * @param port
	 *            端口号
	 */
	public ChatClient(String host, int port) {
		try {
			clientSocket = new Socket(host, port);
		} catch (IOException e) {
			System.out.println("开启Socket失败!");
		}
	}

	/**
	 * 客户端从这里启动.
	 */
	private void start() {

		/*
		 * 提供Scanner对象用于供客户端输入用户名
		 */
		Scanner scan = new Scanner(System.in);
		try {
			// --注册用户名
			inputUserName(scan);
			// --下面就应该是服务端和客户端开始进行聊天

			// --专门用来服务端消息的. 线程中是输入流
			Runnable run = new GetServiceMsgHandler();
			Thread t = new Thread(run);
			t.start();

			// --开启输出流向服务端发消息.
			OutputStream os = clientSocket.getOutputStream();
			OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
			PrintWriter pw = new PrintWriter(osw);

			// --卡死循环用于向服务端发消息
			while (true) {
				pw.println(scan.nextLine());
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// --关闭客户端
			if (clientSocket != null) {
				try {
					clientSocket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 录入用户名
	 * 
	 * @param scan
	 * @throws IOException
	 */
	private void inputUserName(Scanner scan) throws IOException {
		// --1.参数 检查
		if (scan == null) {
			throw new IllegalArgumentException("Scanner为null");
		}

		// --2.提供一个String变量用于保存从控制台录入的用户名
		String name = "";

		// --3.通过Socket对象获取输入和输出流
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream(), "UTF-8"), true);
		BufferedReader br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), "UTF-8"));

		// --4.卡死循环.在客户端录入用户名,发送到服务端.并等待响应结果
		while (true) {
			System.out.println("请创建你的昵称:");
			// --nextLine() 是读取一行.
			name = scan.nextLine();
			// --防止用户名是空格等空字符
			if (name.trim().length() == 0) {
				System.out.println("昵称不可以为空");
			} else {
				// --把用户名发送给服务端
				pw.println(name);
				/*
				 * 接受服务端返回的结果 因为io是阻塞的.客户端会一直停留在读这里,直到读取到内容.
				 */
				String answer = br.readLine();
				// --对返回值做验证 OK 代表用户名是唯一的.通过注册了.
				if (answer != null && !answer.equals("OK")) {
					System.out.println("用户名已经被占用,请重新输入");
				} else {
					System.out.println("欢迎" + name + "来到车友社区!");
					break;
				}
			}
		}
	}

	/**
	 * 开启子线程用于接收服务端消息.
	 * 
	 * @author Administrator
	 *
	 */
	class GetServiceMsgHandler implements Runnable {

		@Override
		public void run() {

			try {
				// --获取输入流对象
				InputStream is = clientSocket.getInputStream();
				InputStreamReader isr = new InputStreamReader(is, "UTF-8");
				BufferedReader br = new BufferedReader(isr);

				// --接受从客户端发过来的消息

				// --保存从客户端发过来的消息
				String msg = "";
				// --为了保证线程不结束,写死循环
				while ((msg = br.readLine()) != null) {
					System.out.println("接受服务端信息:" + msg);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/*
	 * 端对端 : 只和某一个人聊天 端对All : 所有人聊天
	 */
	public static void main(String[] args) {

		ChatClient client = new ChatClient("127.0.0.1", 1100);
		client.start();
	}

}

