package project.group3.citywalks.activities;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import project.group3.citywalks.R;
import project.group3.citywalks.dataInterface.JsonUserParse;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Activity which displays a login screen to the user, 
 */
public class LoginActivity extends Activity {
	Integer userId;
	SharedPreferences preferences;
	/*
	 * Keep track of the login task to ensure we can cancel it if requested.
	 */
	private UserLoginTask mAuthTask = null;

	// Values for username and password at the time of the login attempt.
	private String userName;
	private String mPassword;

	// UI references.
	private EditText mUserNameView;
	private EditText mPasswordView;
	private View mLoginFormView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_login);

		// Set up the login form.
		mUserNameView = (EditText) findViewById(R.id.email);

		mPasswordView = (EditText) findViewById(R.id.password);
		mPasswordView
				.setOnEditorActionListener(new TextView.OnEditorActionListener() {
					@Override
					public boolean onEditorAction(TextView textView, int id,
							KeyEvent keyEvent) {
						if (id == R.id.login || id == EditorInfo.IME_NULL) {
							attemptLogin();
							return true;
						}
						return false;
					}
				});

		mLoginFormView = findViewById(R.id.login_form);
	//	mLoginStatusView = findViewById(R.id.login_status);
	//	mLoginStatusMessageView = (TextView) findViewById(R.id.login_status_message);

		findViewById(R.id.sign_in_button).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						attemptLogin();
					}
				});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	/**
	 * Attempts to sign in
	 * If there are form errors (invalid userName, missing fields, etc.), the
	 * errors are presented and no actual login attempt is made.
	 */
	public void attemptLogin() {
		if (mAuthTask != null) {
			return;
		}

		// Reset errors.
		mUserNameView.setError(null);
		mPasswordView.setError(null);

		// Store values at the time of the login attempt.
		userName = mUserNameView.getText().toString();
		mPassword = mPasswordView.getText().toString();

		boolean cancel = false;
		View focusView = null;

		// Check for a valid password.
		if (TextUtils.isEmpty(mPassword)) {
			mPasswordView.setError(getString(R.string.error_field_required));
			focusView = mPasswordView;
			cancel = true;
		} else if (mPassword.length() < 4) {
			mPasswordView.setError(getString(R.string.error_invalid_password));
			focusView = mPasswordView;
			cancel = true;
		}

		// Check for a valid email address.
		if (TextUtils.isEmpty(userName)) {
			mUserNameView.setError(getString(R.string.error_field_required));
			focusView = mUserNameView;
			cancel = true;
		} 

		if (cancel) {
			// There was an error; don't attempt login and focus the first
			// form field with an error.
			focusView.requestFocus();
		} else {
			// Show a progress spinner, and kick off a background task to
			// perform the user login attempt.

			mAuthTask = new UserLoginTask();
			mAuthTask.execute(new String[]{userName,mPassword});
		}
	}

	/**
	 *  asynchronous login  task used to authenticate the user.
	 */
	public class UserLoginTask extends AsyncTask<String, Void, Integer> {
		/**
		 * method to check details of user
		 * @param params String array containing username and password
		 * @return Integer userid
		 */
		protected Integer doInBackground(String... params) {
			String userName = params[0];
			String password = params[1];
			JsonUserParse parseUserLogin = new JsonUserParse();
			List<NameValuePair> parameters = new ArrayList<NameValuePair>();
	        parameters.add(new BasicNameValuePair("userName", userName));
	        parameters.add(new BasicNameValuePair("password", password));
	        Integer login = parseUserLogin.loginUser(parameters);
	        return login;
		}

		@Override
		/**
		 * after executing the login task either save userid or post message stating not able to login
		 * the end of the activity begins a new intent
		 * @param userId integer retrieved from async return
		 */
		protected void onPostExecute(final Integer userId) {
			if(userId !=null)
			{
				preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
				SharedPreferences.Editor editor = preferences.edit();
		        editor.putInt("userId", userId);
		        editor.commit();
		        startActivity(new Intent(LoginActivity.this,ListWalk.class));
			}
			else
			{
				Toast toast = Toast.makeText(LoginActivity.this, "unable to Log in", Toast.LENGTH_SHORT);
				toast.show();
				preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
				SharedPreferences.Editor editor = preferences.edit();
		        editor.remove("userId");
		        editor.commit();
				startActivity(new Intent(LoginActivity.this,ListWalk.class));
			}
		}

		@Override
		protected void onCancelled() {
			mAuthTask = null;
		}
	}
	/**
	 * function dealing with menu clicks
	 * @param item than was clicks
	 * @return boolean 
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