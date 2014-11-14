package com.example.bclib;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	public Socket s = null;
	public DataOutputStream dos = null;
	public DataInputStream dis  = null;
	
	public String games;
	
	public Client(){
		
		try {
			s = new Socket(InetAddress.getLocalHost(), 8096);
			dos = new DataOutputStream(s.getOutputStream());
			dis = new DataInputStream(s.getInputStream());
			
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
	}
}
