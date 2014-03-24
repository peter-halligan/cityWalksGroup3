package project.group3.citywalks.dataInterface;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class JsonUserParse {
    static InputStream is = null;
    static JSONObject jObj = null;
    static String jsonLogin = "";
    private static String base_url = "http://www.peterhalligan.com/mobilelogin.php";
    private Integer userId;
 
    // constructor
    public JsonUserParse() {
 
    }
	    public Integer loginUser(List<NameValuePair> params)
	    {
		    try 
		    {
		        // defaultHttpClient
		        DefaultHttpClient httpClient = new DefaultHttpClient();
		        HttpPost httpPost = new HttpPost(base_url);
		        httpPost.setEntity(new UrlEncodedFormEntity(params));
		
		        HttpResponse httpResponse = httpClient.execute(httpPost);
		        HttpEntity httpEntity = httpResponse.getEntity();
		        is = httpEntity.getContent();
		
		    } catch (UnsupportedEncodingException e) {
		        e.printStackTrace();
		    } catch (ClientProtocolException e) {
		        e.printStackTrace();
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
		    try {
	            BufferedReader reader = new BufferedReader(new InputStreamReader(
	                    is, "iso-8859-1"), 8);
	            StringBuilder sb = new StringBuilder();
	            String line = null;
	            while ((line = reader.readLine()) != null) {
	                sb.append(line + "n");
	            }
	            is.close();
	            jsonLogin = sb.toString();
	            Log.e("JSON", jsonLogin);
	        } catch (Exception e) {
	            Log.e("Buffer Error", "Error converting result " + e.toString());
	        }
	 
	        // try parse the string to a JSON object
	        try {
	            jObj = new JSONObject(jsonLogin);           
	        } catch (JSONException e) {
	            Log.e("JSON Parser", "Error parsing data " + e.toString());
	            return userId = -1;
	        }
	 
	        // return JSON String
			try {
				if(jObj !=null)
				{
					JSONArray jArr = jObj.getJSONArray("login");
					if(jArr != null)
					{
						userId = jArr.getJSONObject(0).getInt("userId");
					}
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return userId;
	    }
	 
 }
