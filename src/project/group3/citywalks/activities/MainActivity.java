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
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

public class MainActivity extends FragmentActivity implements LocationListener {

    private GoogleMap aMap = null;
    private ArrayList<Walk> walks = new  ArrayList<Walk>();
    private ArrayList<Coordinate> coordinates = new  ArrayList<Coordinate>();
    private ArrayList<POI> points = new ArrayList<POI>();
    ArrayList<LatLng> waypoints = new ArrayList<LatLng>();
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
			fis = MainActivity.this.openFileInput("walks");
	
			ObjectInputStream is = new ObjectInputStream(fis);
			walks = (ArrayList<Walk>) is.readObject();
			is.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
    		Log.w("mapStatus", "map loaded");
    	}
    	 aMap.setMyLocationEnabled(true);
    }
   
    private void addMarker()
    {
    	for(Walk walk : walks)
    	{
    		
    		if(walk.getWalkId() == walkId)
    		{
    			coordinates.addAll(walk.getCoordinates());
    			
    			points.addAll(walk.getPoiList());
    			
    		}
    		
    	}
 
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
    			.title("this is invivsible")
    			.visible(true)
    			);
    		}
    		
    	}
    	
    	route = new PolylineOptions().addAll(waypoints);
    	
    	if(route != null)
    	{
    	     Polyline displayRoute = aMap.addPolyline(route);
    	}
    }
    
    private void setUpListeners()
    {
    	aMap.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {

            @Override
            public void onInfoWindowClick(Marker marker) {
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
    private void setUpSpeechToTextListener() 
    {
    	textToSpeechObj = new TextToSpeech(getApplicationContext(), 
    		      new TextToSpeech.OnInitListener() {
    		      @Override
    		      public void onInit(int status) {
    		         if(status != TextToSpeech.ERROR){
    		        	 textToSpeechObj.setLanguage(Locale.UK);
    		            }
    		         }
    		      });
 		
 	}


	@Override
	public void onLocationChanged(Location location) {
		 mapCenter = new LatLng(location.getLatitude(),location.getLongitude());
		 aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mapCenter, 13));


		
	}



}
