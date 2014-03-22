package project.group3.citywalks.dataInterface;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class WalkHttpClient {

	
	private static String base_url = "http://www.peterhalligan.com/";
	
	public String getCordinates(String cityId){
		String coordinates = base_url + "get_coordinates.php?" +"cityId=" + cityId;
		try {
			HttpURLConnection con = (HttpURLConnection) ( new URL(coordinates)).openConnection();
			
			con.setRequestMethod("GET");
			con.setDoInput(true);
			con.setDoOutput(true);
			con.connect();
			
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			StringBuffer buffer = new StringBuffer();
			String line = null;
			while (  (line = in.readLine()) != null )
				buffer.append(line + "\r\n");

			in.close();
            con.disconnect();
			return buffer.toString();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public String getWalk(String cityId)
	{
		String walk = base_url + "get_walk.php?" +"cityId=" + cityId;
		try {
			HttpURLConnection con = (HttpURLConnection) ( new URL(walk)).openConnection();
			
			con.setRequestMethod("GET");
			con.setDoInput(true);
			con.setDoOutput(true);
			con.connect();
			
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			StringBuffer buffer = new StringBuffer();
			String line = null;
			while (  (line = in.readLine()) != null )
				buffer.append(line + "\r\n");

			in.close();
            con.disconnect();
			return buffer.toString();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	
	public String getPoi(String cityId)
	{
		String poi = base_url + "get_poi.php?" +"cityId=" + cityId;
		try {
			HttpURLConnection con = (HttpURLConnection) ( new URL(poi)).openConnection();
			
			con.setRequestMethod("GET");
			con.setDoInput(true);
			con.setDoOutput(true);
			con.connect();
			
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			StringBuffer buffer = new StringBuffer();
			String line = null;
			while (  (line = in.readLine()) != null )
				buffer.append(line + "\r\n");

			in.close();
            con.disconnect();
			return buffer.toString();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
}
