package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class Main {

	public static void main(String[] args) throws MalformedURLException, UnsupportedEncodingException{
		String aa = "locationName=嘉義縣";  
		String encodedURL = URLEncoder.encode(aa, "UTF-8");

//		URL  url = new URL("http://odsvr3.cwb.gov.tw:8084/api/v1/rest/datastore/F-C0032-001?" + encodedURL);
		URL  url = new URL("http://odsvr2.cwb.gov.tw:8084/api/v1/rest/datastore/F-C0032-001?locationName=嘉義縣&Authorization=CWB-F188F669-15F0-445C-B8AA-4C1C5EA1AEEA");
		try (BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()))) {
			
			                String inputLine;
			 
			 
			
			                // Read the "gpl.txt" text file from its URL representation
			
			                System.out.println("/***** File content (URL4) *****/n");
			
			                while((inputLine = in.readLine()) != null) {
			
			                    System.out.println(inputLine);
			
			                }
			
			            } catch (IOException ioe) {
			
			                ioe.printStackTrace(System.err);
			
			            }

	}
}
