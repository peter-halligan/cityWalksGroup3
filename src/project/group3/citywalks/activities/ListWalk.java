package project.group3.citywalks.activities;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import org.json.JSONException;

import project.group3.citywalks.R;
import project.group3.citywalks.dataInterface.JsonWalkParser;
import project.group3.citywalks.dataInterface.WalkHttpClient;
import project.group3.citywalks.objects.Walk;
import project.group3.citywalks.util.SystemUiHider;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 * @see SystemUiHider
 */
public class ListWalk extends Activity 
{
    ArrayList<Walk> walks = new ArrayList<Walk>();
   
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
	private class SendHttpRequestTask extends AsyncTask<String, Void, ArrayList<Walk>>{

		  @Override
		  protected ArrayList<Walk> doInBackground(String... params) 
		  {
		  
		      String cityId  = params[0];
		   
		      WalkHttpClient a = new WalkHttpClient();
		      String walk =  a.getWalk(cityId);
		      String coordinateString = a.getCordinates(cityId);
		      String poi = a.getPoi(cityId);
			   try 
			   {
					walks = JsonWalkParser.getWalk(walk, coordinateString, poi);
					FileOutputStream fos;
					try {
						fos = ListWalk.this.openFileOutput("walks", Context.MODE_PRIVATE);
						ObjectOutputStream os = new ObjectOutputStream(fos);
						os.writeObject(walks);
						os.close();
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			   } catch (JSONException e) {				
					e.printStackTrace();
			   }
		  return walks;
		  }

		
		  protected void onPostExecute(ArrayList<Walk> result) 
		  {
			  ArrayList<Button> buttons = new ArrayList<Button>();
			  LinearLayout listWalks = (LinearLayout) findViewById(R.id.listWalksLayout);
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
					    Log.w("WALK ID", String.valueOf(arg.getId()));
						startActivity(i);
					}
					 
				 });
			  }
			  
		  }
	}
}