package project.group3.citywalks.dataInterface;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import android.util.Log;

public class WalkHttpClient {

	private static String BASE_URL = "http://peterhalligan.com/sayhi.php";

	public String getCoordinateData() {
		try {
			URL readFrom = new URL(BASE_URL);
			BufferedReader in = new BufferedReader(new InputStreamReader(readFrom.openStream()));
			StringBuffer buffer = new StringBuffer();
			String line = null;
			while (  (line = in.readLine()) != null )
				buffer.append(line + "\r\n");

			in.close();
			
			return buffer.toString();
	    }
		catch(Throwable t) {
			Log.e("TAG", "Error1", t);			
		}
		return null;
	}
}
