package com.tt.jiaoyou.xml;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.Principal;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import com.tt.jiaoyou.ui.OPlayerApplication;
import com.tt.jiaoyou.util.StringUtils;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.Log;

public class XMLUtil {
	public static  String readJsonFromUrl(String path) { 
		String result=null;
		try{
			URL url = new URL(path);
			Log.i("tag", "--------url = "+url);
			HttpURLConnection  conn = (HttpURLConnection )url.openConnection();
			conn.connect();
			if(conn.getResponseCode()==200){
				InputStream input = conn.getInputStream();
				result = toString(input);
				input.close();
			}
			conn.disconnect();
			}catch(Exception e){
				e.printStackTrace();
			}
		Log.i("tag", "--------result = "+result);
		  return result;
	      } 
	
	private static String toString(InputStream input){
		
		String content = null;
		try{
		InputStreamReader ir = new InputStreamReader(input);
		BufferedReader br = new BufferedReader(ir);
		
		StringBuilder sbuff = new StringBuilder();
		while(null != br){
			String temp = br.readLine();
			if(null == temp)break;
			sbuff.append(temp).append(System.getProperty("line.separator"));
		}
		
		content = sbuff.toString();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return content;
	}	
	
}
