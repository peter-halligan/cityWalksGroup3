package project.group3.citywalks;

import java.util.Timer;
import java.util.TimerTask;

import project.group3.citywalks.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class Splash extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
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
	                      startActivity(new Intent(Splash.this, ChooseCity.class));
	                  }
	              });
	         }
	    }, 2000);;
	}
	
	
	
}
