/**  
 * @Title: ChatClient.java  
 * @Package com.example.demo  
 * @Description: TODO(��һ�仰�������ļ���ʲô)  
 * @author Administrator  
 * @date 2017��11��28��  
 * @version V1.0  
 */  
package com.example.demo;

/**  
 * @ClassName: ChatClient  
 * @Description: TODO(������һ�仰��������������)  
 * @author Administrator  
 * @date 2017��11��28��  
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
	 * �вι�������� Host��Port�ĳ�ʼ������
	 * 
	 * @param host
	 *            ������ַ
	 * @param port
	 *            �˿ں�
	 */
	public ChatClient(String host, int port) {
		try {
			clientSocket = new Socket(host, port);
		} catch (IOException e) {
			System.out.println("����Socketʧ��!");
		}
	}

	/**
	 * �ͻ��˴���������.
	 */
	private void start() {

		/*
		 * �ṩScanner�������ڹ��ͻ��������û���
		 */
		Scanner scan = new Scanner(System.in);
		try {
			// --ע���û���
			inputUserName(scan);
			// --�����Ӧ���Ƿ���˺Ϳͻ��˿�ʼ��������

			// --ר�������������Ϣ��. �߳�����������
			Runnable run = new GetServiceMsgHandler();
			Thread t = new Thread(run);
			t.start();

			// --��������������˷���Ϣ.
			OutputStream os = clientSocket.getOutputStream();
			OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
			PrintWriter pw = new PrintWriter(osw);

			// --����ѭ�����������˷���Ϣ
			while (true) {
				pw.println(scan.nextLine());
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// --�رտͻ���
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
	 * ¼���û���
	 * 
	 * @param scan
	 * @throws IOException
	 */
	private void inputUserName(Scanner scan) throws IOException {
		// --1.���� ���
		if (scan == null) {
			throw new IllegalArgumentException("ScannerΪnull");
		}

		// --2.�ṩһ��String�������ڱ���ӿ���̨¼����û���
		String name = "";

		// --3.ͨ��Socket�����ȡ����������
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream(), "UTF-8"), true);
		BufferedReader br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), "UTF-8"));

		// --4.����ѭ��.�ڿͻ���¼���û���,���͵������.���ȴ���Ӧ���
		while (true) {
			System.out.println("�봴������ǳ�:");
			// --nextLine() �Ƕ�ȡһ��.
			name = scan.nextLine();
			// --��ֹ�û����ǿո�ȿ��ַ�
			if (name.trim().length() == 0) {
				System.out.println("�ǳƲ�����Ϊ��");
			} else {
				// --���û������͸������
				pw.println(name);
				/*
				 * ���ܷ���˷��صĽ�� ��Ϊio��������.�ͻ��˻�һֱͣ���ڶ�����,ֱ����ȡ������.
				 */
				String answer = br.readLine();
				// --�Է���ֵ����֤ OK �����û�����Ψһ��.ͨ��ע����.
				if (answer != null && !answer.equals("OK")) {
					System.out.println("�û����Ѿ���ռ��,����������");
				} else {
					System.out.println("��ӭ" + name + "������������!");
					break;
				}
			}
		}
	}

	/**
	 * �������߳����ڽ��շ������Ϣ.
	 * 
	 * @author Administrator
	 *
	 */
	class GetServiceMsgHandler implements Runnable {

		@Override
		public void run() {

			try {
				// --��ȡ����������
				InputStream is = clientSocket.getInputStream();
				InputStreamReader isr = new InputStreamReader(is, "UTF-8");
				BufferedReader br = new BufferedReader(isr);

				// --���ܴӿͻ��˷���������Ϣ

				// --����ӿͻ��˷���������Ϣ
				String msg = "";
				// --Ϊ�˱�֤�̲߳�����,д��ѭ��
				while ((msg = br.readLine()) != null) {
					System.out.println("���ܷ������Ϣ:" + msg);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/*
	 * �˶Զ� : ֻ��ĳһ�������� �˶�All : ����������
	 */
	public static void main(String[] args) {

		ChatClient client = new ChatClient("127.0.0.1", 1100);
		client.start();
	}

}

