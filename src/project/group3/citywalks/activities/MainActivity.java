package project.group3.citywalks.activities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Locale;

import project.group3.citywalks.R;
import project.group3.citywalks.objects.Coordinate;
import project.group3.citywalks.objects.POI;
import project.group3.citywalks.objects.Walk;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
/**
 * 
 * @author peter halligan
 * Main map activity displays map and walks that are passed in
 *
 */
public class MainActivity extends FragmentActivity implements LocationListener {

    private GoogleMap aMap = null;
    private ArrayList<Walk> walks = new  ArrayList<Walk>();
    private ArrayList<Coordinate> coordinates = new  ArrayList<Coordinate>();
    private ArrayList<POI> points = new ArrayList<POI>();
    ArrayList<LatLng> waypoints = new ArrayList<LatLng>();
    private LatLng centre;
    private int walkId;
    private PolylineOptions route;
    private TextToSpeech textToSpeechObj;
    private LatLng mapCenter;
 
    @SuppressWarnings("unchecked")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent i = getIntent();
		walkId = i.getIntExtra("walkId", 0);
		FileInputStream fis;
		try {
			//open the file where the walk is stored and place in a Walk arraylist
			fis = MainActivity.this.openFileInput("walks");
	
			ObjectInputStream is = new ObjectInputStream(fis);
			walks = (ArrayList<Walk>) is.readObject();
			is.close();
			for(Walk walk : walks)
	    	{
	    		if(walk.getWalkId() == walkId)
	    		{
	    			coordinates.addAll(walk.getCoordinates());
	    			
	    			points.addAll(walk.getPoiList());
	    			
	    		}
	    		
	    	}
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		}
		// centre the map on the first coordinate
		centre = new LatLng(coordinates.get(0).getLatitude(),coordinates.get(0).getLongitude());
        setUpMap();
        addMarker();
        setUpSpeechToTextListener();
        setUpListeners();
    
        
    }
    

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setUpMap()
    {
    	if (aMap == null)
    	{
    	     aMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
    	     
    	} 
    	else
    	{
    	      //do nothing 
    	}
    	//mpve camera to new latLong and zoom level
    	aMap.setMyLocationEnabled(true);
    	aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(centre, 17));
    	
    }
    /**
     * function to add markers to the map and add the route
     */
    private void addMarker()
    {
 
    	for(Coordinate c: coordinates)
    	{
    		waypoints.add(new LatLng(c.getLatitude(),c.getLongitude()));
    		if(c.isVisible())
    		{
    			for(POI p: points)
    			{
    				if(p.getCid() == c.getCoordinateId())
    				{
		    			aMap.addMarker(new MarkerOptions()
							.position(new LatLng(c.getLatitude(),c.getLongitude()))
							.title(p.getName())
							.snippet(p.getDescription())
							.draggable(false)
							.visible(true)
							);	
    				}
    			}
    		}
    		else
    		{
    			aMap.addMarker(new MarkerOptions()
    			.position(new LatLng(c.getLatitude(),c.getLongitude()))
    			.visible(false)
    			);
    		}
    		
    	}
    	// add the walk route
    	route = new PolylineOptions().addAll(waypoints);
    	
    	if(route != null)
    	{
    	     Polyline displayRoute = aMap.addPolyline(route);
    	}
    }
    /**
     * function to set up the listeners that opens a poi details activity when the info window is clicked
     */
    private void setUpListeners()
    {
    	aMap.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {

            @Override
            public void onInfoWindowClick(Marker marker) {
            	if(textToSpeechObj.isSpeaking())
            	{
            		textToSpeechObj.stop();
            		textToSpeechObj.shutdown();
            	}
            	for(POI p : points)
            	{
                    if(marker.getTitle().equals(p.getName()))
                    {
                        Intent i = new Intent(MainActivity.this,PoiDetails.class);
                        i.putExtra("POI", p);
                        startActivity(i);
                    }
            	}
            }
        });
    	/**
    	 * create marker click listener which will dispaly an info window when the marker is pressed
    	 */
    	aMap.setOnMarkerClickListener(new OnMarkerClickListener(){

			@Override
			public boolean onMarkerClick(Marker marker) 
			{
				marker.getTitle();
				for(POI p:points)
				{
					if(p.getName().equals(marker.getTitle()))
					{
				        textToSpeechObj.speak(p.getDescription(), TextToSpeech.QUEUE_FLUSH, null);
				        
					}
				}
				 return false;
			}
    		
    	});
    }
    /**
     * speech to text listener which will read the object 
     */
    private void setUpSpeechToTextListener() 
    {
    	textToSpeechObj = new TextToSpeech(getApplicationContext(), 
    		  new TextToSpeech.OnInitListener() {
    		      @Override
    		      public void onInit(int status) 
    		      {
    		         if(status != TextToSpeech.ERROR)
    		         {
    		        	 textToSpeechObj.setLanguage(Locale.UK);
    		         }
    		      }
    		  });
 	}

	@Override
	public void onLocationChanged(Location location) 
	{
		 mapCenter = new LatLng(location.getLatitude(),location.getLongitude());
		 aMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mapCenter, 15));
		 
	}
		
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		switch (item.getItemId()) 
		{
	        case R.id.change_city:
	            startActivity(new Intent(this, ChooseCity.class));
	            return true;
	        case R.id.toggle_location:
		        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
		        return true;
	    default:
	    return super.onOptionsItemSelected(item);
	    }
	}

}
