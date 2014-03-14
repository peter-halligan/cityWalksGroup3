package project.group3.citywalks.activities;

import java.util.ArrayList;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import project.group3.citywalks.R;
import project.group3.citywalks.R.id;
import project.group3.citywalks.R.layout;
import project.group3.citywalks.objects.Coordinate;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends FragmentActivity {
    private GoogleMap aMap = null;
    private ArrayList<Coordinate> coordinatesList = new  ArrayList<Coordinate>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
		     coordinatesList.add(new Coordinate(1,51.9005,-8.46618));
		     coordinatesList.add(new Coordinate(2,51.89434,-8.48138));
		     coordinatesList.add(new Coordinate(3,51.89823,-8.4656));
		     coordinatesList.add(new Coordinate(4,51.90126,-8.46524));
        setUpMap();
        addMarker();
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
    }
    
    private void addMarker()
    {
    	for(Coordinate c : coordinatesList)
    	{
    		aMap.addMarker(new MarkerOptions()
    				.position(new LatLng(c.getLatitude(),c.getLongitude()))
    				.title("this is marker number: " + c.getCoordinateId())
    				);			
    	}
    }
    private void setUpListeners()
    {
    	aMap.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {

            @Override
            public void onInfoWindowClick(Marker marker) {

                Log.d("Hello", marker.getTitle());   
            }
        });
    }
}
