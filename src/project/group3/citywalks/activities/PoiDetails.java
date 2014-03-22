package project.group3.citywalks.activities;

import java.util.Locale;

import project.group3.citywalks.R;
import project.group3.citywalks.objects.POI;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

public class PoiDetails extends Activity {
	private TextView name;
	private TextView description;
	private TextToSpeech textToSpeechObj;
	private POI poi;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_poi_details);
		Intent i = getIntent();
	    poi = (POI)i.getSerializableExtra("POI");

		name=(TextView)findViewById(R.id.PoiName);
		description = (TextView)findViewById(R.id.description);
		name.setText(poi.getName());
		description.setText(poi.getDescription());
		
		
		 textToSpeechObj = new TextToSpeech(getApplicationContext(), 
	      new TextToSpeech.OnInitListener() {
	      @Override
	      public void onInit(int status) {
	         if(status != TextToSpeech.ERROR){
	        	 textToSpeechObj.setLanguage(Locale.UK);
	            }
	         textToSpeechObj.speak(poi.getDescription(), TextToSpeech.QUEUE_FLUSH, null);
	         }
	      });
	   }
	   @Override
	   public void onPause()
	   {
	      if(textToSpeechObj !=null){
	    	  textToSpeechObj.stop();
	    	  textToSpeechObj.shutdown();
	      }
	      super.onPause();
	   }


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.poi_details, menu);
		return true;
	}

}
