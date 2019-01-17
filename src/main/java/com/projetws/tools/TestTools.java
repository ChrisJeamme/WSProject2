package com.projetws.tools;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class TestTools
{
	public static String putRequest(String urlString, String json)
	{
		try
		{
			URL url = new URL(urlString);
			HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
			httpCon.setDoOutput(true);
			httpCon.setRequestMethod("PUT");
			httpCon.addRequestProperty("content-type", "application/json");
			OutputStream os = httpCon.getOutputStream();
			OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");    
			osw.write(json);
			osw.flush();
			osw.close();
			os.close();  //don't forget to close the OutputStream
			httpCon.connect();
	
			//read the inputstream and print it
			String result;
			BufferedInputStream bis;
			bis = new BufferedInputStream(httpCon.getInputStream());
			ByteArrayOutputStream buf = new ByteArrayOutputStream();
			int result2 = bis.read();
			while(result2 != -1)
			{
			    buf.write((byte) result2);
			    result2 = bis.read();
			}
			result = buf.toString();
			return result;

		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static String getRequest(String urlString)
	{
		try
		{
			
			URL url = new URL(urlString);
			HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
			httpCon.setDoOutput(true);
			httpCon.setRequestMethod("GET");
			httpCon.connect();
	
			String result;
			BufferedInputStream bis;
			bis = new BufferedInputStream(httpCon.getInputStream());
			ByteArrayOutputStream buf = new ByteArrayOutputStream();
			int result2 = bis.read();
			while(result2 != -1)
			{
			    buf.write((byte) result2);
			    result2 = bis.read();
			}
			result = buf.toString();
			return result;

		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args)
	{
		String result = getRequest("http://localhost:8080/job/list");
		System.out.println(result);

		String result2 = putRequest("http://localhost:8080/job/update/AC_ACCOUNT",
				"{\r\n" + 
				"	\"jobTitle\": \"Public Accountant de son père\"\r\n" + 
				"}");
		System.out.println(result2);
	}
	
}
