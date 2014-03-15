
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
		ArrayList<Coordinate> coordinates = new ArrayList<Coordinate>();

		JSONObject jObj = new JSONObject(data);
		JSONArray jArr = jObj.getJSONArray("names");

		for(int i = 0; i < jArr.length(); i ++)
		{
			Coordinate co = new Coordinate(0,0,0);
			co.setCoordinateId(getInt("cid", jArr.getJSONObject(i)));
			co.setWalkId(getInt("walkId",jArr.getJSONObject(i)));
			co.setLatitude(getFloat("latitude", jArr.getJSONObject(i)));
			co.setLongitude(getFloat("longitude", jArr.getJSONObject(i)));
			co.setVisible(getInt("visible", jArr.getJSONObject(i)));
			coordinates.add(co);
			
		}	
		for(Coordinate o: coordinates)
		{
			System.out.printf("cid: %i \n lat %d \n lon %d", o.getCoordinateId(), o.getLatitude(), o.getLongitude());
		}
		return walks;
	}

	private static float  getFloat(String tagName, JSONObject jObj) throws JSONException {
		return (float) jObj.getDouble(tagName);
	}

	private static int  getInt(String tagName, JSONObject jObj) throws JSONException {
		return jObj.getInt(tagName);
	}
}
