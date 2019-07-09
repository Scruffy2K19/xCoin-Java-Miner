package no.xcoin.climiner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

public class AuthenticationManager {
	ConsoleManager console = new ConsoleManager();
	
	String auth_args;
	String auth_handle;
	String token;
	String status_response;
	String auth_token;
	
	public void xcn_setToken(String token) {
		this.auth_token = token;
	}
	
	public String xcn_getToken() {
		return this.auth_token;
	}
	
	public String xcn_auth(String handler, String args) throws IOException {
		this.auth_args = args;
		this.auth_handle = handler;
		
		if (auth_args != null) {
			URL url;
			try {
				url = new URL("https://xcoin.no/m_api/auth.php?token="+token);
				HttpURLConnection xcn_con = (HttpURLConnection) url.openConnection();
				xcn_con.setRequestMethod("GET");
				xcn_con.setConnectTimeout(5000);
				xcn_con.setReadTimeout(5000);
				
				BufferedReader in = new BufferedReader(
				  new InputStreamReader(xcn_con.getInputStream()));
				String inputLine;
				StringBuffer content_response = new StringBuffer();
				while ((inputLine = in.readLine()) != null) {
					content_response.append(inputLine);
				    status_response = content_response.toString();
				    return status_response;
				}
				in.close();
				xcn_con.disconnect();
			} catch (MalformedURLException e) {
				e.printStackTrace();
				console.xcn_err("Could not reach API Endpoint.");
			}
		} else {
			console.xcn_err("Please enter valid auth statements");
		}
		
		return null;
	}
}
