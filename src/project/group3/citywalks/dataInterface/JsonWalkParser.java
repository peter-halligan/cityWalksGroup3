
package project.group3.citywalks.dataInterface;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import project.group3.citywalks.objects.Coordinate;
import project.group3.citywalks.objects.POI;
import project.group3.citywalks.objects.Walk;

public class JsonWalkParser {
	
	public static ArrayList<Walk> getWalks(String data) throws JSONException  {
		ArrayList<Walk> walks = new ArrayList<Walk>();
		

		JSONObject jObj = new JSONObject(data);
		JSONArray jArr = jObj.getJSONArray("names");

		for(int i = 0; i < jArr.length(); i ++)
		{
			Coordinate co = new Cordinate();
			POI poi = new POI();
			Walk walk = new Walk();
			
			walks.add(getString("name", jArr.getJSONObject(i)));		
			
		}		
		return walks;
	}

	private static String  getString(String tagName, JSONObject jObj) throws JSONException {
		return jObj.getString(tagName);
	}
}
