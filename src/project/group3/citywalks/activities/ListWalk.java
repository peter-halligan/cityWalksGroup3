package project.group3.citywalks.activities;

import java.util.ArrayList;

import project.group3.citywalks.R;
import project.group3.citywalks.R.id;
import project.group3.citywalks.R.layout;
import project.group3.citywalks.util.SystemUiHider;
import android.app.Activity;
import android.content.Intent;
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
		ArrayList<String> walks = new ArrayList<String>();
		walks.add("Shandon");
		walks.add("south parish walk");
		walks.add("city centre walk");

		setContentView(R.layout.activity_list_walk);
		Intent i = getIntent();
		String cityNumber = i.getStringExtra("city");
		int cityNo = Integer.parseInt(cityNumber);
		
		((TextView) findViewById(R.id.listWalks)).setText("Getting walks");
		
		StringBuilder sb = new StringBuilder();
		for(String c: walks)
		{   
			sb.append(c);
			sb.append("\n");
		}
		sb.toString();
		
		((TextView) findViewById(R.id.listWalks)).setText(sb);
		
	}
}