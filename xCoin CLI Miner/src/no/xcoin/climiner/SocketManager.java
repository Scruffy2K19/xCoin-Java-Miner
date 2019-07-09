package no.xcoin.climiner;

import java.net.*;
import java.io.*;

public class SocketManager {
	ConsoleManager console = new ConsoleManager();
	
	private Socket xcn_socket;
	private PrintWriter xcn_out;
	private BufferedReader xcn_in;
	
	private long conn_timeout;
	private String conn_type;
	private String xcn_host;
	private int xcn_port;
	
	public void xcn_setTimeout(int ms) {
		this.conn_timeout = ms;
	}
	
	public long xcn_getTimeout() {
		return this.conn_timeout;
	}
	
	public void xcn_setConnType(String type) {
		this.conn_type = type;
	}
	
	public String xcn_getConnType() {
		return this.conn_type;
	}
	
	public void xcn_setHost(String host) {
		this.xcn_host = host;
	}
	
	public String xcn_getHost() {
		return this.xcn_host;
	}
	
	public void xcn_setPort(int port) {
		this.xcn_port = port;
	}
	
	public int xcn_getPort() {
		return this.xcn_port;
	}
	
	public Boolean xcn_connect(String host, int port) {
		if (host != null && port != 0) {
			try {
				xcn_socket = new Socket(host, port);
				xcn_out = new PrintWriter(xcn_socket.getOutputStream(), true);
				xcn_in = new BufferedReader(new InputStreamReader(xcn_socket.getInputStream()));
				return true;
			} catch (UnknownHostException e) {
				e.printStackTrace();
				return false;
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		} else {
			console.xcn_err("Invalid host or port specified");
			return false;
		}
	}
	
	public String xcn_send(String data) {
		xcn_out.println(data);
		String returnData = null;
		try {
			returnData = xcn_in.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return returnData;
	}
	
	public void xcn_closeConnection() {
		try {
			xcn_socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
