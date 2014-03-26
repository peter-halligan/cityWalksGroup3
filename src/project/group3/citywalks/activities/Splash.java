package project.group3.citywalks.activities;

import java.util.Timer;
import java.util.TimerTask;

import project.group3.citywalks.R;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;


public class Splash extends Activity {
	SharedPreferences sharedPreferences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		startApplication();
	
	}

	private void startApplication() 
	{
		new Timer().schedule(new TimerTask()
		{
	        public void run() 
	        { 
	             Splash.this.runOnUiThread(new Runnable() 
	             {
	                  public void run() 
	                  {
	                	
	                	  if (sharedPreferences.contains("cityId"))
	                	  {
	                		  Intent i = new Intent(Splash.this, ListWalk.class);
	                		  i.putExtra("city", String.valueOf(sharedPreferences.getInt("cityId", 1)));
	                          startActivity(i);
	                         Splash.this.finish();
	                           
	                	  }
	                	  else
	                	  {
	                          startActivity(new Intent(Splash.this, ChooseCity.class));
	                          Splash.this.finish();
	                	  }
	                  }
	              });
	         }
	    }, 2000);;
	}
	
	
	
}
