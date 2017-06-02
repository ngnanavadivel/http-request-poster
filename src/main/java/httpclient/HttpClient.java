package httpclient;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

public class HttpClient {

	private static final String USER_AGENT = "Mozilla/5.0";

	public static void main(String[] args) throws Exception {
		String url = args[0];
		
		URL obj = new URL(url);
		HttpURLConnection con = null;
		
		if(url.startsWith("https")) {
			con = (HttpsURLConnection) obj.openConnection();
		}
		else {
			con = (HttpURLConnection) obj.openConnection();
		}

		// add reuqest header
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", USER_AGENT);

		// Send post request
		con.setDoOutput(true);

		Scanner scanner = new Scanner(new File(args[1]));
		String content = scanner.useDelimiter("\\Z").next();
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(content);
		wr.flush();
		wr.close();
		scanner.close();
		
		int responseCode = con.getResponseCode();
		
		System.out.println("\nSending 'POST' request to URL : " + url);
		System.out.println("Request Body : \n" + content);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		// print result
		System.out.println("Response :\n" + response.toString());

	}
}
