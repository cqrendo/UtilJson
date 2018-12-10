package coop.intergal.espresso.presutec.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;

public class Journal {
	private final static Header API_KEY_HEADER = new BasicHeader("Authorization", "Basic Y3FyQGludGVyZ2FsLmNvb3A6cDEyM3NhZG8=" );


	public static void main(String[] args) {

		getJournal("173293");//JA_ASSET222");
	}
	
	public static void getJournal(String journalId){

		HttpHost targetHost = new HttpHost("presutec.intergal.coop", 8090, "http");
		DefaultHttpClient httpclient = new DefaultHttpClient();
//		httpclient.getCredentialsProvider().setCredentials(
//				new AuthScope(targetHost.getHostName(), targetHost.getPort()),
//				new UsernamePasswordCredentials("test@liferay.com", "test"));
		
//		AuthCache authCache = new BasicAuthCache();
//		BasicScheme basicAuth = new BasicScheme();
//		authCache.put(targetHost, basicAuth);

		BasicHttpContext ctx = new BasicHttpContext();
//		ctx.setAttribute(
//				org.apache.http.client.protocol.ClientContext.AUTH_CACHE,
//				authCache);

//		HttpHost targetHost = new HttpHost("localhost", 8080, "http");
//		DefaultHttpClient httpclient = new DefaultHttpClient();
//		httpclient.getCredentialsProvider().setCredentials(
//				new AuthScope(targetHost.getHostName(), targetHost.getPort()),
//				new UsernamePasswordCredentials("test@liferay.com", "test"));

		AuthCache authCache = new BasicAuthCache();
		BasicScheme basicAuth = new BasicScheme();
		authCache.put(targetHost, basicAuth);

//		BasicHttpContext ctx = new BasicHttpContext();
//		ctx.setAttribute(
//				org.apache.http.client.protocol.ClientContext.AUTH_CACHE,
//				authCache);

		//HttpGet get = new HttpGet("/api/jsonws/journalarticle/get-article/group-id/10194/article-id/ja_pepe");
		HttpPost post = new HttpPost("/api/jsonws/journalarticle/get-article");
		post.addHeader(API_KEY_HEADER);

		List<NameValuePair> params = new ArrayList<NameValuePair>();
		
		//params.add(new BasicNameValuePair("cmd", ""));
		params.add(new BasicNameValuePair("groupId", "10194"));
		params.add(new BasicNameValuePair("articleId", journalId));
		try {
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, "UTF-8");
			post.setEntity(entity);
			HttpResponse resp = httpclient.execute(targetHost, post, ctx);
			System.out.println(resp.getStatusLine());
			resp.getEntity().writeTo(System.out);
			httpclient.getConnectionManager().shutdown();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
