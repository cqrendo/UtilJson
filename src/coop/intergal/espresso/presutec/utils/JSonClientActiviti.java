package coop.intergal.espresso.presutec.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * A very simple, self-contained demo REST client that exercises an Espresso Logic API.<br><br>
 * 
 * You will need the following jars:
 * <ol>
 * <li>httpclient-4.x.x.jar</li>
 * <li>httpcore-4.x.x.jar</li>
 * <li>commons-logging-1.1.1.jar</li>
 * <li>jackson-core-2.x.x.jar</li>
 * <li>jackson-databind-2.x.x.jar</li>
 * <li>jackson-annotations-2.x.x.jar</li>
 * </ol>
 * 
 * @see <a href="http://docs.espressologic.com/docs/rest-apis/using-ssl">Using https</a> 
 */
public class JSonClientActiviti {

//	private final static HttpClient client = new DefaultHttpClient();
	private static CloseableHttpClient client = HttpClientBuilder.create().build();
	private final static JsonNodeFactory nodeFactory = JsonNodeFactory.instance;

//	protected static String LOCAL_BASE_URL = "http://localhost:8080/KahunaService/rest/abl/demo/demo1/";   // for internal testing (ignore)
	protected static String SERVER = "http://activiti.intergal.coop/activiti-rest/service/";
	static String BASE_URL = SERVER ; // LOCAL_BASE_URL;
	// Result: https://eval.espressologic.com/rest/valJune18/demo/v1/
//	protected static String MY_API_KEY = "ykBPQq7tBBGLl3xV7gay";  // TODO edit for your project
	protected static String API_KEY =  "Basic Y3FyOnAxMjNzYWRv";//"Basic YWRtaW5pc3RyYWRvcjpwMTIzc2Fkbw==";
//	private static JsonNode keepJson = null;
	private final static Header API_KEY_HEADER = new BasicHeader("Authorization", API_KEY );
	private static Hashtable<String,JsonNode> jsonCaches=new Hashtable<String,JsonNode>();
	public static void main(String[] args) throws Exception {
		
		// {@link <a href="http://docs.espressologic.com/docs/rest-apis/using-ssl">more info</>
		System.setProperty("javax.net.ssl.trustStore", "src/main/resources/mykeystore");  

		// Retrieve all customer objects 
		System.out.println("\n\n------ Retrieve all customers using BASE_URL: " + BASE_URL);  
		JsonNode taskList = get("tasks","candidate-group=espressoLogic", true).get("data");
		System.out.println("taskList " + taskList) ;
		for (JsonNode eachTask : taskList) {
			System.out.println("createTime: " + eachTask.get("createTime").asText());
			System.out.println("owner " + eachTask.get("owner").asText());
			System.out.println("   description: " + eachTask.get("description").asText());
		}

	System.out.println("\n------ Test is complete");
	}

	public static JsonNode get(String resource, String filter, boolean useCache ) throws Exception {
//		client = HttpClientBuilder.create().build();
	
		String url = BASE_URL + resource;
		if (filter != null)
		{
			url = url +filter;
		}
		System.out.println("tengo en url : " + url);
		HttpGet get = new HttpGet(url);
		get.addHeader(API_KEY_HEADER);
		HttpResponse response = client.execute(get);
		
//		System.out.println("hace el close : ");
		JsonNode parResponse = parseResponse(response);
		System.out.println("tengo en response : " + parResponse);
//		client.close();
//		client.getConnectionManager().shutdown();
		return parResponse.get("data"); //activiti returns rows inside "data"
	}
	private static JsonNode getJsonCache(String resource) {
		if (jsonCaches.get(resource) != null)
		{
			return jsonCaches.get(resource);
		}
		return null;
	}

	public static JsonNode get(String url) throws Exception {
	
	//	client = HttpClientBuilder.create().build();
		System.out.println("tengo en url : " + url);
		HttpGet get = new HttpGet(url);
//		get.addHeader(API_KEY_HEADER);
		HttpResponse response = client.execute(get);
		JsonNode parResponse = parseResponse(response);
	//	client.close();
		return parResponse;
	}
	public static JsonNode put(String resource, JsonNode object) throws Exception {
//		client = HttpClientBuilder.create().build();
		HttpPut put = new HttpPut(BASE_URL + resource);
		put.addHeader(API_KEY_HEADER);
		StringEntity entity = new StringEntity(object.toString(),"UTF-8");
		entity.setContentType("application/json");
		put.setEntity(entity);
		HttpResponse response = client.execute(put);
		JsonNode parResponse = parseResponse(response);
//		client.close();
		return parResponse;
	}

	public static JsonNode post(String resource, JsonNode object) throws Exception {
//		client = HttpClientBuilder.create().build();
		HttpPost post = new HttpPost(BASE_URL + resource);
		System.err.println(" URL para POST "+ BASE_URL + resource );
		post.addHeader(API_KEY_HEADER);
		StringEntity entity = new StringEntity(object.toString(),"UTF-8");//, HTTP.UTF_8);
//		entity.setContentEncoding("UTF-8");
		entity.setContentType("application/json");
		post.setEntity(entity);
		HttpResponse response = client.execute(post);
		JsonNode parResponse = null;
		if (response.getEntity() != null)
			{	
			parResponse = parseResponse(response);
			}
//		client.close();
		return parResponse;
	}

	public static JsonNode delete(JsonNode object) throws Exception {
//		client = HttpClientBuilder.create().build();
		String objectUrl = object.get("@metadata").get("href").asText();
		String checksum = object.get("@metadata").get("checksum").asText();
		HttpDelete delete = new HttpDelete(objectUrl + "?checksum=" + checksum);
		delete.addHeader(API_KEY_HEADER);
		HttpResponse response = client.execute(delete);
		JsonNode parResponse = parseResponse(response);
//		client.close();
		return parResponse;
	}

	/**
	 * Read a Response object and parse it into a JsonNode
	 */
	private static JsonNode parseResponse(HttpResponse response) throws Exception {
		InputStreamReader inStr = new InputStreamReader(response.getEntity().getContent());
		BufferedReader rd = new BufferedReader(inStr);
		StringBuffer sb = new StringBuffer();
		String line = null;
		while ((line = rd.readLine()) != null) {
			sb.append(line);
		}

		ObjectMapper mapper = new ObjectMapper();
		JsonNode inData = mapper.readTree(sb.toString());
		return inData;
	}

//	public static String getJSON() {
//		// TODO Auto-generated method stub
//		try {
//			return get("Entity").toString();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return null;
//	}


	private static String getFieldLocale(String field) {
		String clave =field; 
		ResourceBundle labels = ResourceBundle.getBundle("ResourceBundle", new Locale("es", "ES"));
		Enumeration bundleKeys = labels.getKeys();
		clave = labels.getString(field);

	    return clave;
	}

}

	


