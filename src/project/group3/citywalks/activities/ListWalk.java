package project.group3.citywalks.activities;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import org.json.JSONException;

import project.group3.citywalks.dataInterface.JsonWalkParser;
import project.group3.citywalks.dataInterface.WalkHttpClient;
import project.group3.citywalks.objects.Walk;
import project.group3.citywalks.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
/**
 * 
 * @author peter halligan
 * this is an activity which will display all the walks the from a chosen city
 * or the walks of a chosen id
 *
 */
public class ListWalk extends Activity 
{
	/**
	 * declare shared preferences and an array list to contain the walk data
	 */
	SharedPreferences preferences;
    ArrayList<Walk> walks = new ArrayList<Walk>();
   
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_walk);
		/**
		 * get user city choice
		 */
    	Intent i = getIntent();
		String cityNumber = i.getStringExtra("city");
		preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		cityNumber = String.valueOf(preferences.getInt("cityId",-1));
		/**
		 * begin new async task passing parameter int as a string and the type of parameter
		 * either city or user id
		 */
		SendHttpRequestTask task = new SendHttpRequestTask();
		task.execute(new String[]{cityNumber, "city"});
		
		userWalks();
	}
	/**
	 * Function to set the text in a button dynamically if the user is logged in the 
	 * allow the user to download custom walks else display a login button and add to layout
	 */
	private void userWalks()
	{
		LinearLayout userWalks = (LinearLayout) findViewById(R.id.userButtonLayout);
		if(preferences.contains("userId"))
		{
			  Button myWalks = new Button(getApplicationContext());
			  myWalks.setText("My Walks");
			  userWalks.addView(myWalks);
			  myWalks.setOnClickListener(new OnClickListener()
			  {
					@Override
					public void onClick(View arg)
					{
						getUserWalks();
					}	 
			  });
	    }
		else
		{
			  Button login = new Button(getApplicationContext());
			  login.setText("Login");
			  userWalks.addView(login);
			  login.setOnClickListener(new OnClickListener()
			  {
					@Override
					public void onClick(View arg)
					{
						startActivity(new Intent(ListWalk.this,LoginActivity.class));
					}	 
			  });	  
		}
	}
	/**
	 * 
	 * @author peter halligan based on code by Declan Murphy
	 * private class for running an async task to retreive data from the remote server
	 * 
	 */
	private class SendHttpRequestTask extends AsyncTask<String, Void, ArrayList<Walk>>{

		/**
		 * do in backgroud method to allow processeing while letting the UI remain responsive
		 * the method saves the data to a file too 
		 * @param cityId a number passed in as a string to search the Database for
		 * @param type a string that states the type of number passed in this is appnded to the 
		 * url to make the queery
		 * @return ArrayList<Walk> a complete array list of walks that are found with stated criteria in the database
		 */
		  protected ArrayList<Walk> doInBackground(String... params) 
		  {
              //retrieve parameters from the array
		      String cityId  = params[0];
		      String type = params[1];
		      //create new client for retieveing data
		      WalkHttpClient a = new WalkHttpClient();
		      String walk =  a.getWalk(cityId, type);
		      String coordinateString = a.getCordinates(cityId, type);
		      String poi = a.getPoi(cityId, type);
		      
			   try 
			   {
				   //call to jsonWalkParser which will return a walk
					walks = JsonWalkParser.getWalk(walk, coordinateString, poi);
					FileOutputStream fos;
					try {
						fos = ListWalk.this.openFileOutput("walks", Context.MODE_PRIVATE);
						ObjectOutputStream os = new ObjectOutputStream(fos);
						os.writeObject(walks);
						os.close();
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
			   } catch (JSONException e) {				
					e.printStackTrace();
			   }
		  return walks;
		  }
		  /**
		   * once the async task has compete we will take the new list of walks, clear previous walks and pouplate the 
		   * Layout with buttons and attaches action listeners
		   */
		  protected void onPostExecute(ArrayList<Walk> result) 
		  {
			  ArrayList<Button> buttons = new ArrayList<Button>();
			  LinearLayout listWalks = (LinearLayout) findViewById(R.id.listWalksLayout);
			  listWalks.removeAllViews();
			  for(int i = 0; i < walks.size(); i++)
			  {
				  Button button = new Button(getApplicationContext());
				  button.setText(walks.get(i).getWalkName());
				  button.setId(walks.get(i).getWalkId());
				  buttons.add(i,button);
			  }
			  for(Button b: buttons)
			  {
				 listWalks.addView(b);
				 b.setOnClickListener(new OnClickListener()
				 {
					@Override
					public void onClick(View arg)
					{
						
						Intent i = new Intent(ListWalk.this, MainActivity.class);
					    i.putExtra("walkId", arg.getId());
						startActivity(i);
					}
					 
				 });
			  }
			  
		  }
	}
	/**
	 * if the user presses the get user defined walks this method runs the async task passing in 
	 * the user id from th shared preferences
	 */
	private void getUserWalks() {
		
		String userId = String.valueOf(preferences.getInt("userId", 0));
		userId = "0";
		SendHttpRequestTask task = new SendHttpRequestTask();
		task.execute(new String[]{userId,"user"});
		Log.d("done", "getUSerWAlks");
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.list_walk, menu);
		return true;
	}
	/**
	 * menu for changing system options and storing them 
	 */
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