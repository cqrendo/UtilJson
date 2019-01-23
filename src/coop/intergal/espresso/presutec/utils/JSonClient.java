 package coop.intergal.espresso.presutec.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.Hashtable;
import java.util.Properties;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
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
public class JSonClient {

//	private final static HttpClient client = new DefaultHttpClient();
	private static CloseableHttpClient client = HttpClientBuilder.create().build();
	private final static JsonNodeFactory nodeFactory = JsonNodeFactory.instance;
	private static Boolean SHOW_LOGS = false;

//	protected static String LOCAL_BASE_URL = "http://localhost:8080/KahunaService/rest/abl/demo/demo1/";   // for internal testing (ignore)
	// @@ Adebate 		protected static String SERVER = "http://presutec.my.espressologic.com/rest/";
	// @@ Adebate 	protected static String ACCOUNT = "presutec/";  // TODO edit these for your account
	// @@ Adebate 	protected static String PROJECT = "adebate/";   // the Url Name of the pre-installed Espresso Logic demo project
	// @@ Adebate 	protected static String API_VERSION ="v1/"; 
	// @@ Adebate static String BASE_URL = SERVER + ACCOUNT + PROJECT + API_VERSION; // LOCAL_BASE_URL;
	static String BASE_URL ="http://localhost:8090/call-center-rest-api/rest/default/ccAF/v1/";
	// Result: https://eval.espressologic.com/rest/valJune18/demo/v1/
// @@ Adebate 	protected static String MY_API_KEY = "QxMJqyyzAXhfkou7ITRT:1 ";  // TODO edit for your project
// @@ Adebate	protected static String API_KEY = "Espresso " + MY_API_KEY + ":1";///23";
	protected static String MY_API_KEY = "xJUmSI6bZvxpgjfGc5Hf";  // TODO edit for your project
    protected static String API_KEY = "Espresso " + MY_API_KEY + ":1";///23";

//	private static JsonNode keepJson = null;
	private final static Header API_KEY_HEADER = new BasicHeader("Authorization", API_KEY );
	private static Hashtable<String,JsonNode> jsonCaches=new Hashtable<String,JsonNode>();
	private static Header apiKeyHeader;
	private static String baseURL;
	private static String kPreConfParam;
	private static String tableNameRoot;
	static Hashtable<String, String> ht = new Hashtable<String, String>();
//	static Hashtable<String, String> htPK = new Hashtable<String, String>();
	static Hashtable<String, Hashtable<String, String>> resourceHtPK  = new Hashtable<String, Hashtable<String, String>>();
	static Hashtable<String, JsonNode> parents = new Hashtable<String, JsonNode>();
	static Hashtable<String, Hashtable<String, JsonNode>> parentsResource = new Hashtable<String, Hashtable<String, JsonNode>>();
	private static String resourceTableName;
	private static String kbaseURL = "";
	private JsonNode keepJson ;
	public static void main(String[] args) throws Exception {
		setConfigEspreso(null);
		if (baseURL == null)
			{
			baseURL= BASE_URL;
			apiKeyHeader = API_KEY_HEADER;
			}
	
		
		
		// {@link <a href="http://docs.espressologic.com/docs/rest-apis/using-ssl">more info</>
		System.setProperty("javax.net.ssl.trustStore", "src/main/resources/mykeystore");  

		// Retrieve all customer objects 
		printLog("\n\n------ Retrieve all customers using BASE_URL: " + baseURL);  
		JsonNode customerList = get("Region",null, true, null);
		for (JsonNode eachCustomer : customerList) {
			printLog("nome:" + eachCustomer.get("nameEntity").asText());
			printLog("   IdPersoa:" + eachCustomer.get("idEntity").asText());
		}

		// Insert a brand new customer object
		printLog("\n\n------ Insert a new customer");
		ObjectNode newCustomerinfo = new ObjectNode(nodeFactory);
		newCustomerinfo.put("nameEntity", "NewCustomer");  // simple name (spaces in url need replacement)
//		newCustomerinfo.put("credit_limit", 0);
		JsonNode postResult = post("Entity", newCustomerinfo, null);
		if (postResult.get("statusCode").intValue() != 201)
			throw new RuntimeException("Unable to insert: " + postResult);
		JsonNode postTxSummary = postResult.get("txsummary");
		for (JsonNode node : postTxSummary) {
			printLog("Transaction summary for post: " + node);
		}
		ObjectNode insertedNode = (ObjectNode)postTxSummary.get(0);

		// Now update the new customer object
		printLog("\n\n------ Update the new customer");
		insertedNode.put("keyEntity", "modificado");
		JsonNode putResult = put("Entity", insertedNode, null);
		if (putResult.get("statusCode").intValue() != 200)
			throw new RuntimeException("Unable to update: " + putResult);
		JsonNode putTxSummary = putResult.get("txsummary");
		for (JsonNode node : putTxSummary) {
			printLog("Transaction summary for put: " + node);
		}
		ObjectNode updatedNode = (ObjectNode)putTxSummary.get(0);

		// And delete the object we just inserted
		printLog("\n\n------ Delete the new customer");
		JsonNode deleteResult = delete(updatedNode, null);
		if (deleteResult.get("statusCode").intValue() != 200)
			throw new RuntimeException("Unable to delete: " + deleteResult);
		JsonNode deleteTxSummary = deleteResult.get("txsummary");
		for (JsonNode node : deleteTxSummary) {
			printLog("Transaction summary for delete: " + node);
		}

		printLog("\n------ Test is complete");
	}
	private static void printLog(String string) {
		if (SHOW_LOGS)
			System.out.println( new Date().toString() +" JSonClient.printLog()-->" + string);
		
	}
//	public static JsonNode get(String resource, String filter, boolean useCache) throws Exception {
//		return get(resource, filter, useCache, null, null);
//	}
	public static JsonNode get(String resource, String filter, boolean useCache,String preConfParam ) throws Exception {
		return get(resource, filter, useCache, preConfParam, null);
	}
	public static JsonNode get(String resource, String filter, boolean useCache, String preConfParam, String pagesize ) throws Exception {
//		client = HttpClientBuilder.create().build();
//		String nlPcP = preConfParam;
//		String knlPcP = kPreConfParam;
//		if (preConfParam == null)
//			nlPcP = "null";
//		if (preConfParam == null)
//			knlPcP ="null";
//
//		if (baseURL == null |! !nlPcP.equals(knlPcP))
//			{
		
		if (pagesize == null)
			pagesize = "200";
		calculateBaseURL(preConfParam);
		printLog(" preConfParam "+preConfParam  + " kPreConfParam "+ kPreConfParam);
		if (useCache)
		{
			JsonNode keepJson = getJsonCache(resource+filter);
			if (keepJson != null)
			{
				printLog("  the resource "+ resource+ " filter " + filter + " is return form cache ");
			//	printLog(" cache content " +keepJson);
				return keepJson;
			
			}
		}
		String url = baseURL;
		if (filter != null && filter.startsWith("#PK#")) // you send a direct GET by PK value
										// instead of a filter then not pagesize
										// or other params are need
		{
			url = url + resource + "/" + filter.substring(4);
		} else {
			url = url + resource + "?pagesize=" + pagesize;
			if (resource.indexOf("?") > 0)
				url = baseURL + resource + "&pagesize=" + pagesize;
			if (filter != null && filter.startsWith("order="))
				url = url + "&" + filter;
			else if (filter != null && filter.length() > 0) {
				url = url + "&filter=" + filter;
			}
		}
		//	if (order != null)
//		{
//			url = url + "&order="+order;
//		}
		printLog("tengo en url : (JsonNode get(String resource...)" + url);
		HttpGet get = new HttpGet(url);
		get.addHeader(apiKeyHeader);
		HttpResponse response = client.execute(get);
//		printLog("hace el close : ");
		JsonNode parResponse = parseResponse(response);
//		client.close();
//		client.getConnectionManager().shutdown();
		if (useCache)
			{
			jsonCaches.put(resource + filter, parResponse);
			}
		int max = parResponse.asText().length();
		if (max >50)
			max =50;
			
		printLog("Response : " + parResponse.asText().substring(0,max)+".............");
		return parResponse;//parseResponse(response);
	}
	private static void setConfigEspreso(String preConfParam) {
		 kPreConfParam = preConfParam;
		 Properties prop = new Properties();
		   try
		   {
		//	   String baseUrl = null;
		//	   InputStream url = getClass().getClassLoader().getResourceAsStream("asambleas.properties");
			   InputStream is = JSonClient.class.getResourceAsStream("/espresso.properties");
			   if (preConfParam == null)
			   {
				   preConfParam = "";
			   }
			   else
				   preConfParam = preConfParam + "_";
			   if (is !=null)
			   	   {
				   prop.load(is);
		 	   	   baseURL = prop.getProperty(preConfParam+"BASE_URL");
		 	   	   apiKeyHeader=  new BasicHeader("Authorization", prop.getProperty(preConfParam+"API_KEY")); 
		 	   	   
		 	   	   /**Imprimimos los valores*/
		 	   	   printLog("baseUrl: "+baseURL);
			   	   }
		  } catch (FileNotFoundException e) {
			  printLog("Error, El archivo no exite");
		  } catch (IOException e) {
			  printLog("Error, No se puede leer el archivo");
		  }	
		
	}

	public static String getBaseURL() {
		return baseURL;
	}
	private static JsonNode getJsonCache(String resource) {
		if (jsonCaches.get(resource) != null)
		{
			return jsonCaches.get(resource);
		}
		return null;
	}
//	public static JsonNode get(String url) throws Exception {
//		return get(url, null);
//		}
	public static JsonNode get(String url, String preConfParam) throws Exception {
		printLog(" preConfParam "+preConfParam  + " kPreConfParam "+ kPreConfParam);
		if (preConfParam == null )
		{	
			if (baseURL == null)
				setConfigEspreso(null);	
		}		
		else if (baseURL == null |! !preConfParam.equals(kPreConfParam))
		{
			setConfigEspreso(preConfParam);
			if (baseURL == null)
			{
				baseURL= BASE_URL;
				apiKeyHeader = API_KEY_HEADER;
			}
		}
		else if (!preConfParam.equals(kPreConfParam))
		{
			setConfigEspreso(preConfParam);
			if (baseURL == null)
			{
				baseURL= BASE_URL;
				apiKeyHeader = API_KEY_HEADER;
			}
		}
		if (!url.startsWith("http"))
			url = baseURL + url;	
	//	client = HttpClientBuilder.create().build();
		printLog("tengo en url  (JsonNode get(String url,.....) : " + url);
		HttpGet get = new HttpGet(url);
		get.addHeader(apiKeyHeader);
		HttpResponse response = client.execute(get);
		JsonNode parResponse = parseResponse(response);
	//	client.close();
		printLog("Response : " + parResponse);
		return parResponse;
	}
//	public static JsonNode put(String resource, JsonNode object) throws Exception {
//		return put(resource, object, null);
//	}
	public static JsonNode put(String resource, JsonNode object, String preConfParam) throws Exception {
//		client = HttpClientBuilder.create().build();
		calculateBaseURL(preConfParam);

		HttpPut put = new HttpPut(baseURL + resource);
		put.addHeader(apiKeyHeader);
		StringEntity entity = new StringEntity(object.toString(),"UTF-8");
		entity.setContentType("application/json;charset=UTF-8");
		put.setEntity(entity);
		HttpResponse response = client.execute(put);
		JsonNode parResponse = parseResponse(response);
//		client.close();
		return parResponse;
	}
//	public static JsonNode post(String resource, JsonNode object) throws Exception {
//		return post(resource, object, null);
//	}
	
	private static void calculateBaseURL(String preConfParam) {
		if (preConfParam == null )
		{	
			if (baseURL == null)
				setConfigEspreso(null);	
		}		
		else  
			if ((baseURL == null ) || (kPreConfParam.equals(preConfParam) == false) || (baseURL.equals(kbaseURL) == false)) // Attention using ! for not doesn't work
			{
				setConfigEspreso(preConfParam);
				if (baseURL == null)
				{
					baseURL= BASE_URL;
					apiKeyHeader = API_KEY_HEADER;
				}
				else
				{
					kbaseURL = baseURL;
				}
			}		

	}
	public static JsonNode post(String resource, JsonNode object, String preConfParam) throws Exception {
//		client = HttpClientBuilder.create().build();
		calculateBaseURL(preConfParam);
		HttpPost post = new HttpPost(baseURL + resource);
		post.addHeader(apiKeyHeader);
		if (object !=null)
			{
			StringEntity entity = new StringEntity(object.toString(),"UTF-8");//, HTTP.UTF_8);
//		entity.setContentEncoding("UTF-8");
			entity.setContentType("application/json;charset=UTF-8");
			post.setEntity(entity);
			printLog(post + "POST..... " + post.toString());
			}
		HttpResponse response = client.execute(post);
		JsonNode parResponse = parseResponse(response);
//		client.close();
		return parResponse;
	}
//	public static JsonNode delete(JsonNode object) throws Exception {
//		return delete(object,null);
//	}
	public static JsonNode delete(JsonNode object, String preConfParam) throws Exception {
//		client = HttpClientBuilder.create().build();
		calculateBaseURL(preConfParam);
		String objectUrl = object.get("@metadata").get("href").asText();
		String checksum = object.get("@metadata").get("checksum").asText();
		HttpDelete delete = new HttpDelete(objectUrl + "?checksum=" + checksum);
		delete.addHeader(apiKeyHeader);
		HttpResponse response = client.execute(delete);
		JsonNode parResponse = parseResponse(response);
//		client.close();
		return parResponse;
	}
	public static JsonNode delete(String resource, String preConfParam) throws Exception { // use for ApiREST to delete with DROPTable
//		client = HttpClientBuilder.create().build();
		calculateBaseURL(preConfParam);	
		HttpDelete delete = new HttpDelete(baseURL + resource);
		delete.addHeader(apiKeyHeader);
		printLog(delete + "POST..... " + delete.toString());
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
			printLog("line "+ line);
			sb.append(line);
		}

		ObjectMapper mapper = new ObjectMapper();
		JsonNode inData = mapper.readTree(sb.toString());
		return inData;
	}
//******** METADATA *******
	
	public static String getPKTable(String table, String preConfParam) {
		JsonNode resource = null;
		
		try {
			resource = get("@tables/"+cleanNoDBPrefix(table),null,true,preConfParam);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (resource.get("errorCode") == null) // is get a errorCode it means a error -> probably "No Such entity" that is because you are using a resource not a table
			{
			JsonNode pKeyColumns = resource.get("primaryKeyColumns");
			String keys ="";
			int i=0;
			for (JsonNode eachRow : pKeyColumns) {
				String keyName = eachRow.asText();				
				i++;
				if (i==1)
					keys = keyName;
				else
					keys = keys + ";" + keyName;	
				}
			return keys;
			}
		return null;
	}
	private static String cleanNoDBPrefix(String table) {
		if (table != null && table.startsWith("CHAIN:")) // CHAIN is not the realname of the table, is to handle table in a way that follows the next form path
			return table.substring(6);
		return table;
	}
	public static String getFKFromTable(String table, String childTableName, String preConfParam) {
		JsonNode resource = null;
		try {
			resource = get("@tables/"+cleanNoDBPrefix(table),null,true, preConfParam);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (resource.get("errorCode") == null) // is get a errorCode it means a error -> probably "No Such entity" that is because you are using a resource not a table
			{
			JsonNode pKeyColumns = resource.get("children");
			String keys ="";
			int i=0;
			for (JsonNode eachRow : pKeyColumns) {
				String childTable = eachRow.get("child_table").asText();
				if (childTable.equals("main:"+childTableName)) //@@CQR be careful with "main:" 
					{
					JsonNode childKeyColumns = eachRow.get("child_columns");
					for (JsonNode eachChlidKey : childKeyColumns) {
						String keyName = eachChlidKey.asText();
						i++;
						if (i==1)
							keys = keyName;
						else
							keys = keys + ";" + keyName;		
					}
					}

				}
			return keys;
			}
		return null;
	}
	public static String getDataType(String resourceName, String field, String childName, boolean cache, String preConfParam) throws Exception {
		String type = "";
		if (resourceName==null && childName.startsWith("List-"))
			resourceName=childName.substring(5);
		if (field.startsWith("FK-") )  // the format is FK-tableName-fieldName
			{
			String tName = field.substring(3);
			while (true) // searchs last FK  by example a Granparent has 2 and last is the good one 
				{
				if (tName.indexOf("FK-") == -1)
					break;				
				tName = tName.substring(tName.indexOf("FK-") + 3);	
				}
			
			int line2 = tName.indexOf("-");
			String fName = tName.substring(line2+1);		
			tName=tName.substring(0, line2);
	//		String fName = field.substring(line2+4);			
			type = getDataTypeFromTable(tName, fName, cache,preConfParam);
			if (type == null) // can happens that resource name is new then must be re-load with out cache
				type = getDataTypeFromTable(tName, fName, false,preConfParam);
			}
		else
			type = getDataTypeFromTable(cleanNoDBPrefix(resourceName), field, cache,preConfParam);
		if (type != null)
			return type;
		else
			return getDataTypeFromResource(resourceName, field, childName, cache, preConfParam); 
		 
	}
	private static String getDataTypeFromResource(String resourceName, String field, String childName, Boolean cache, String preConfParam) throws Exception {

		JsonNode resource = null;
		String ident = null;
		String tableName = null;
//		String type = null;
		
		//********** STEP 1 ******** // get all the resources and scans to get the ident
		int posPointForSubR = resourceName.indexOf(".");
		String subResourceName = null;
		if (posPointForSubR > -1)
		{
			subResourceName = resourceName.substring(posPointForSubR+1 );
			resourceName = resourceName.substring(0, posPointForSubR);
		}
			
		ident = getIdentOfResuorce(resourceName, cache, preConfParam);
	
		///*******  STEP 2 ***** gets a concret resource using ident 
		try {
			if (ident != null)
				resource = JSonClient.get("@resources/"+ident,null,cache,preConfParam);   
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (resource != null && resource.size() > 0 && (childName == null || childName.isEmpty()) ) // is the root table
			{
			tableName = resource.get("table_name").asText();
			tableNameRoot = tableName;
			}
		else if (resource != null && resource.size() > 0)
			{
			JsonNode subResourceTable = getSubResourceTable(resource, childName);
			if (subResourceTable != null)		
				tableName = subResourceTable.get("table_name").asText();
			else //null means probably a new sub-resource the cache is refill (cache = false) 
				{
				resource = JSonClient.get("@resources/"+ident,null,false,preConfParam); 
				subResourceTable = getSubResourceTable(resource, childName);
				if (subResourceTable != null)		
					tableName = subResourceTable.get("table_name").asText();
				}
				
			}
		///******* STEP 3 ****** gets the data type from the table
			return getDataTypeFromTable(tableName, field, cache,preConfParam);
			}
	
	public static String getIdentOfResuorce(String resourceName, boolean cache, String preConfParam) {
		String ident = null;
		JsonNode resource = null;
		try {
			
			resource = JSonClient.get("@resources",null,cache, preConfParam);   
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (resource == null ||resource.get("statusCode") != null) 
			return null;
		for (JsonNode eachRow : resource) {            // scan the list to found ident
			String fieldName = eachRow.get("name").asText();
			if (fieldName.equals(resourceName))
				{
				ident = eachRow.get("ident").asText(); 
				return ident;
				}
			}
		return null;
	}

	private static JsonNode getSubResourceTable(JsonNode resource, String childName) { // SCAN all sub-resources to get the table name
		JsonNode subResources = resource.get("subresources");
		if (subResources != null)
			{
			for (JsonNode eachRow : subResources) {
				String resourceName = eachRow.get("name").asText();
				if (ht.get(childName) == null)
					ht.put(childName, eachRow.get("join_condition").asText()); // keeps 

				if (resourceName.equals(childName))
					{
					return eachRow; 
					}
				JsonNode subResources1 = eachRow.get("subresources");
				if (subResources1 != null)
					{
					if (ht.get(childName) == null)
						ht.put(childName, eachRow.get("join_condition").asText()); // keeps 

					for (JsonNode eachRow1 : subResources1) {
						String resourceName1 = eachRow1.get("name").asText();
						if (resourceName1.equals(childName))
							{
							return eachRow1; 
							}
						JsonNode subResources2 = eachRow1.get("subresources");
						if (subResources2 != null)
							{
							if (ht.get(childName) == null)
								ht.put(childName, eachRow.get("join_condition").asText()); // keeps 

							for (JsonNode eachRow2 : subResources2) {
								String resourceName2 = eachRow2.get("name").asText();
								if (resourceName2.equals(childName))
								{
									return eachRow2; 
								}
							}
						}
					}
				}
			}
		}		
		return null;
	}
//	@SuppressWarnings("null")
	public static String getDataTypeFromTable(String resourceName, String field, boolean cache, String preConfParam) {
		int idxSepar = field.indexOf("--"); // TODO doc this in "how define resources" ,  when using special names (different from fieldname in table) in columns the syntax is fieldName--ident  then the --ident is cut to be able to get metadata from table field
		if (idxSepar > -1)
			field = field.substring(0, idxSepar );
		
		JsonNode resource = null;
		try {
			resource = JSonClient.get("@tables/"+ cleanNoDBPrefix(resourceName),null,cache,preConfParam);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (resource.get("errorCode") == null) // is get a errorCode it means a error -> probably "No Such entity" that is because you are using a resource not a table
			{
			Hashtable<String, String> htPkForThisReource = resourceHtPK.get(resourceName);
			if (htPkForThisReource == null)
				{
				htPkForThisReource = new Hashtable<String, String>();
				JsonNode pkFieldList;
				boolean is31 = false;
				if (resource.get("primaryKey") != null) // depens in LAC release (2.1)
					pkFieldList = resource.get("primaryKey").get("columns"); // TODO Adadapt to SPRING API Call center Rest API
				else // depens in LAC release (3.1)
					{
					pkFieldList = resource.get("primaryKeyColumns");
					is31=true;
					}
				int i = 0;
				for (JsonNode eachRow : pkFieldList) { // @@ CQR revisar a forma de cargar e usar HT
					String fieldName;
					if 	(is31)
						fieldName = eachRow.asText();	
					else
						fieldName = eachRow.get("name").asText();
			//	String dataType = eachRow.get("generic_type").asText();
					htPkForThisReource.put("pkField" + i,fieldName);
					resourceHtPK.put(resourceName, htPkForThisReource);
					
					i++;
					}
				}
			JsonNode fieldList = resource.get("columns");
		
			for (JsonNode eachRow : fieldList) {
				String fieldName = eachRow.get("name").asText();
				if (fieldName.equals(field))
					{
					String genericType = eachRow.get("generic_type").asText();  // TODO handle fields type TIME
					String type = eachRow.get("type").asText(); 
					if (genericType.equals("text"))
					{
						
					if  ((eachRow.get("size") != null && eachRow.get("size").asInt()  > 512) || eachRow.get("db_column_type").equals("longtext"))
						return ("memo");
					else
						return ("text"); 
					}
					if (type.equals("DECIMAL"))
						return ("DECIMAL");
					return genericType; 
		//		break;
					}
				}
			}
		return null;
	}
	public static Hashtable<String, Hashtable<String, String>> getResourceHtPK() {
		return resourceHtPK;
	}
//	public static Hashtable<String, String> getHtPK() {
//		return htPK;
//	}
	public static JsonNode getColumnsFromTable(String resourceName, String subResourceName, boolean cache, String preConfParam) {  
		ObjectNode listFields = new ObjectNode(nodeFactory);
		ArrayNode rowFields = new ArrayNode(nodeFactory);
		String ident = "";
		if (resourceName == null)
		{
			resourceName = subResourceName.substring(5);   // when a List- (subresource) is sent the name of the table is after List-  
		}
		JsonNode resource = null;
		// TODO revisar , al activar no carga bien el rootTableName 
//		if (resourceName.startsWith("CR-") == false) // CR- means custom 
//														// resource, then is not
//														// a table not sense to
//														// get @tables
//		{
			try {
				resource = JSonClient.get("@tables/"
						+ cleanNoDBPrefix(resourceName), null, cache,
						preConfParam);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println("is probably a reource not a table");
	//			e.printStackTrace();
			}
			if (resource.get("errorCode") == null) // is get a errorCode it
													// means a error -> probably
													// "No Such entity" that is
													// because you are using a
													// resource not a table
			{
				JsonNode fieldList = resource.get("columns");

				for (JsonNode eachRow : fieldList) {
					String fieldName = eachRow.get("name").asText();
					String dataType = eachRow.get("generic_type").asText();
					listFields.put(fieldName, dataType);
				}
				JsonNode parentList = resource.get("parents");

				for (JsonNode eachRow : parentList) {
					JsonNode parentNode = eachRow;
					String fieldName = eachRow.get("child_columns").get(0)
							.asText(); // @@ CQR pending FK multi key, for now
										// just the first of the list determines
										// the attribute that will have pick
					parents.put(fieldName, parentNode);
					parentsResource.put(resourceName, parents);
				}

			}
//		}
		else
		{
			int posPointForSubR = resourceName.indexOf(".");
			//String subResourceName = null;
			if (posPointForSubR > -1)
			{
				subResourceName = resourceName.substring(posPointForSubR+1 );
				resourceName = resourceName.substring(0, posPointForSubR);
			} 
			ident = getIdentOfResuorce(resourceName, cache,preConfParam);
			try {
				resource = JSonClient.get("@resources/"+ident,null,cache,preConfParam);  
				keepJoinConditionSubResources(resource); // is used later for childFilters
				if (subResourceName != null && subResourceName.length() > 1) 
					{
					resource = getSubResourceTable(resource, subResourceName);
					}
				JsonNode fieldList = resource.get("attributes");
				String tableName = resource.get("table_name").asText();
				setResourceTableName(tableName);
				for (JsonNode eachRow : fieldList) {
					String fieldName = eachRow.get("column_name").asText();
					String dataType = getDataTypeFromTable(tableName, fieldName, cache,preConfParam);
					listFields.put(fieldName,dataType);
					}
				// *** get Atributes for subresources
				JsonNode subresources = resource.get("subresources");
//				tableName = resource.get("table_name").asText();
//				setResourceTableName(tableName);
//				if (subResourceName != null) // only when you send a subResourceName you want his attributes names in the list
//				{
				for (JsonNode eachRow : subresources) {
					JsonNode fieldListSR = eachRow.get("attributes");
					tableName = eachRow.get("table_name").asText();
					String name = eachRow.get("name").asText();
					if (name.equals(subResourceName) || name.startsWith("FK-")) // only when you are getting a subresourceList or is  FK you want add the fields to the list
					{
						for (JsonNode eachRowSR : fieldListSR) {
							String fieldName = eachRowSR.get("column_name").asText();
							String dataType = getDataTypeFromTable(tableName, fieldName, cache,preConfParam);
							listFields.put(fieldName,dataType);
						}
					}	
					}
				//}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
		rowFields.add(listFields);
		return rowFields;
	}
	public static void keepJoinConditionSubResources(JsonNode resource) { // Keeps Join Condition for all resources 3 levels deep
		String parentResource = resource.get("name").asText(); // is add to sub-resource to avoid conflicts when a child have more than one parent
		JsonNode subResources = resource.get("subresources");
		if (subResources != null && subResources.size() > 0)
			{
			for (JsonNode eachRow : subResources) {
				String childResource = eachRow.get("name").asText();
				String resourceName = getResourceName(parentResource,childResource);
				if (ht.get(resourceName) == null)
					ht.put(resourceName, eachRow.get("join_condition").asText()); // keeps 

				JsonNode subResources1 = eachRow.get("subresources");
				if (subResources1 != null)
					{
					for (JsonNode eachRow1 : subResources1) {
						String childResource1 = eachRow1.get("name").asText();
						String resourceName1 = getResourceName(childResource,childResource1);
						if (ht.get(resourceName1) == null)
							ht.put(resourceName1, eachRow1.get("join_condition").asText()); // keeps 
						JsonNode subResources2 = eachRow1.get("subresources");
						if (subResources2 != null)
							{
							for (JsonNode eachRow2 : subResources2) {
								String childResource2 = eachRow2.get("name").asText();
								String resourceName2 = getResourceName(childResource1,childResource2);
								if (ht.get(resourceName2) == null)
									ht.put(resourceName2, eachRow1.get("join_condition").asText()); // keeps 
							}
						}
					}
				}
			}
		}		
	}
		
	private static String getResourceName(String parentResource, String childResource) {
		String resourceName = parentResource+"."+childResource;
//		int idxEndOfTableName = childResource.indexOf("--"); // anulado por ahora afecta a las bus
//		if (idxEndOfTableName > 3)
//			resourceName = parentResource+"."+childResource.substring(0,idxEndOfTableName );
		return resourceName;
	}
	public static void putParentsInCache(String resourceName, boolean cache, String preConfParam) {   /// TODO @@@ PENDENT TO ADPAT TO RESOURCE
//		ObjectNode listFields = new ObjectNode(nodeFactory);
//		ArrayNode rowFields = new ArrayNode(nodeFactory);
//		String ident = "";
		
		JsonNode resource = null;
		try {
			resource = JSonClient.get("@tables/"+cleanNoDBPrefix(resourceName),null,cache,preConfParam);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (resource.get("errorCode") == null) // is get a errorCode it means a error -> probably "No Such entity" that is because you are using a resource not a table
			{
			JsonNode parentList = resource.get("parents");
			
			for (JsonNode eachRow : parentList) {				
				JsonNode parentNode = eachRow;
				String fieldName = eachRow.get("child_columns").get(0).asText(); // @@ CQR pending FK multi key, for now just the first of the list determines the attribute that will have pick				
				parents.put(fieldName,parentNode);
				parentsResource.put(resourceName, parents);
				}
			
			}
	}
	public static Hashtable<String, JsonNode> getParents() {
		return parents;
	}
	public static Hashtable<String, Hashtable<String, JsonNode>> getParentsResource() {
		return parentsResource;
	}

	public static Hashtable<String, String> getHt() {
		return ht;
	}
//	private static String getFieldLocale(String field) {
//		String clave =field; 
//		ResourceBundle labels = ResourceBundle.getBundle("ResourceBundle", new Locale("es", "ES"));
//		Enumeration bundleKeys = labels.getKeys();
//		clave = labels.getString(field);
//
//	    return clave;
//	}
	public static String getTableNameRoot() {
		return tableNameRoot;
	}
	public static void setTableNameRoot(String tableNameRoot) {
		JSonClient.tableNameRoot = tableNameRoot;
	}
	public void fillKeepJson (String resource, String filter, boolean useCache, String preConfParam ) {
		try {
			keepJson= get(resource, filter, useCache, preConfParam );
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public int getRowCount()
	{
		if (keepJson !=null)
			return keepJson.size();
		return 0;
	}
	public String getValueFromRow(String field, int rowNumber)
	{
		return keepJson.get(rowNumber).get(field).asText();
		
	}
	public String getValueFromRow(String node, String field, int rowNumber)
	{
		if (keepJson.get(rowNumber).get(node) != null)
			return keepJson.get(rowNumber).get(node).get(field).asText();
		return "null";
		
	}
	public String getValueFromRow(String node1, String node2, String field, int rowNumber)
	{
		if (keepJson.get(rowNumber).get(node1) == null)
			return "null";
		if (keepJson.get(rowNumber).get(node1).get(node2) == null)
			return "null";
		return keepJson.get(rowNumber).get(node1).get(node2).get(field).asText();
		
	}
	public String getResult()
	{
		return keepJson.toString();
		
	}
	public static void setApiKeyHeader(String apiKey) {
		
		JSonClient.apiKeyHeader = new BasicHeader("Authorization", apiKey );;
	}
	public static void setBaseURL(String baseURL) {
		JSonClient.baseURL = baseURL;
	}
	public static String getResourceTableName() {
		return resourceTableName;
	}
	public static void setResourceTableName(String resourceTableName) {
		JSonClient.resourceTableName = resourceTableName;
	}
}

	


