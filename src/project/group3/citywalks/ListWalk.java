package project.group3.citywalks;

import project.group3.citywalks.util.SystemUiHider;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 * @see SystemUiHider
 */
public class ListWalk extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_list_walk);
		Intent i = getIntent();
		String cityNumber = i.getStringExtra("city");
		int cityNo = Integer.parseInt(cityNumber);
		Log.w("cityNumber :", cityNumber);

	}
}