
package project.group3.citywalks.dataInterface;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import project.group3.citywalks.objects.Coordinate;
import project.group3.citywalks.objects.POI;
import project.group3.citywalks.objects.Walk;

public class JsonWalkParser {
	
	public static ArrayList<Walk> getWalk(String aWalk, String coords, String points) throws JSONException  {
		ArrayList<Walk> walks = new ArrayList<Walk>();
		ArrayList<Coordinate> coordinates = new ArrayList<Coordinate>();
		ArrayList<POI> pois = new ArrayList<POI>();
		
		JSONObject jObjWalk = new JSONObject(aWalk);
		JSONArray jArrWalk = jObjWalk.getJSONArray("walks");
		
		JSONObject jObjCord = new JSONObject(coords);
		JSONArray jArrCord = jObjCord.getJSONArray("coordinates");
		
		JSONObject jObjPoint = new JSONObject(points);
		JSONArray jArrPoint = jObjPoint.getJSONArray("pois");
		
		for(int i = 0; i < jArrCord.length(); i ++)
		{
			Coordinate co = new Coordinate(0,0,0);
			co.setCoordinateId(getInt("cid", jArrCord.getJSONObject(i)));
			co.setWalkId(getInt("walkId",jArrCord.getJSONObject(i)));
			co.setLatitude(getFloat("latitude", jArrCord.getJSONObject(i)));
			co.setLongitude(getFloat("longitude", jArrCord.getJSONObject(i)));
			co.setVisible(getInt("visible", jArrCord.getJSONObject(i)));
			coordinates.add(co);
			
		}	

		for(int i = 0; i < jArrWalk.length(); i ++)
		{
			Walk walk = new Walk();
	
			walk.setWalkId(getInt("walkId",jArrWalk.getJSONObject(i)));
			walk.setCityId(getInt("cityId", jArrWalk.getJSONObject(i)));
			walk.setWalkName(getString("name", jArrWalk.getJSONObject(i)));
			walk.setDescription(getString("description", jArrWalk.getJSONObject(i)));
			walks.add(walk);
			
		}
		for(int i = 0; i < jArrPoint.length(); i ++)
		{
			POI poi = new POI();
	
			poi.setPoiid(getInt("poiid",jArrPoint.getJSONObject(i)));
			poi.setName(getString("name",jArrPoint.getJSONObject(i)));
			poi.setWalkId(getInt("walkId", jArrPoint.getJSONObject(i)));
			poi.setCid(getInt("cid", jArrPoint.getJSONObject(i)));
			poi.setDescription(getString("description", jArrPoint.getJSONObject(i)));
			pois.add(poi);
			
		}
		for(Walk w: walks)
		{
			for(POI p: pois)
			{
				if(w.getWalkId()==p.getWalkId())
				{
					w.getPoiList().add(p);
				}
			}
			for(Coordinate c: coordinates)
			{
				if(w.getWalkId()==c.getWalkId())
				{
					w.getCoordinates().add(c);
				}
			}
		}
		
		return walks;	

	}

	private static float  getFloat(String tagName, JSONObject jObj) throws JSONException {
		return (float) jObj.getDouble(tagName);
	}

	private static int  getInt(String tagName, JSONObject jObj) throws JSONException {
		return (int) jObj.getInt(tagName);
	}
	private static String getString(String tagName, JSONObject jObj) throws JSONException {
		return (String) jObj.getString(tagName);
	}
}
