package no.xcoin.climiner;

import java.io.IOException;
import java.net.Socket;

public class CLIMiner {
	static ConsoleManager console = new ConsoleManager();
	static AuthenticationManager auth = new AuthenticationManager();
	static SocketManager socketManager = new SocketManager();
	
	public int nThreads;
	public long hSec;
	public long upTime;
	public long lastReply;
	
	public static String xcn_host = "pool-eu.xcoin.no";
	public static int xcn_port = 8738;
	
	public static String token;
	public static String xcn_auth_string;
	public static String[] xcn_auth_array;
	
	public static String auth_username;
	
	public volatile boolean running = false;

	public static void main(String[] args) {
		console.xcn_print("Starting XCN Java Miner 1.0.0");
		console.xcn_print("Authenticating.");
		try {
			xcn_auth_string = auth.xcn_auth(null, token);
			xcn_auth_array = xcn_auth_string.split(",");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		if (xcn_auth_array[0] == "xe.01") {
			console.xcn_err("Could not authenticate, account banned.");
		} else if (xcn_auth_array[0] == "xe.02") {
			console.xcn_err("Could not authenticate, account is not licensed");
		} else if (xcn_auth_array[0] == "xe.03") {
			console.xcn_err("Could not authenticate, invalid credentials");
		} else if (xcn_auth_array[0] == "success") {
			auth.xcn_setToken(xcn_auth_array[1]);
			auth_username = xcn_auth_array[2];
			console.xcn_print("Authenticated successfully. Connected to: " + auth_username);
			console.xcn_print("Connecting to xCoin Network");
			
			if (socketManager.xcn_connect(xcn_host, xcn_port)) {
				console.xcn_print("Connected to xCoin Network");
			} else {
				console.xcn_err("Could not connect to xCoin Network");
			}
			
		} else {
			console.xcn_err("Could not authenticate, unknown error occured");
		}
	}
}
