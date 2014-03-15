package project.group3.citywalks.activities;

import java.util.ArrayList;


import project.group3.citywalks.R;
import project.group3.citywalks.dataInterface.WalkHttpClient;
import project.group3.citywalks.objects.Coordinate;
import project.group3.citywalks.util.SystemUiHider;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 * @see SystemUiHider
 */
public class ListWalk extends Activity 
{
    ArrayList<String> walks = new ArrayList<String>();
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_walk);
		Intent i = getIntent();
		String cityNumber = i.getStringExtra("city");
		
		
		SendHttpRequestTask task = new SendHttpRequestTask();
		task.execute(new String[]{cityNumber});
		
	}
	private class SendHttpRequestTask extends AsyncTask<String, Void, String>{

		  @Override
		  protected String doInBackground(String... params) {
		  
		   String cityId  = params[0];
		   
		   WalkHttpClient a = new WalkHttpClient();
		   String data =  a.getWalks(cityId);
		   return data;
		  }

		
		  protected void onPostExecute(String result) {
			   ((TextView) findViewById(R.id.listWalks)).setText(result);
			  }
		}
}