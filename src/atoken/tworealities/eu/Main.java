package atoken.tworealities.eu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import atoken.tworealities.eu.R;

public class Main extends Activity {
	private static final int ACTIVITY_NEW_TOKEN=0;


	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
		case R.id.about:
			startActivity(new Intent(this,About.class));
			return true;
		case R.id.preferences:
			startActivity(new Intent(this,Preferences.class));
			return true;
		case R.id.new_token:
			startActivityForResult(new Intent(this,New_token.class),ACTIVITY_NEW_TOKEN);
			return true;
		default:
			return true;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		switch(requestCode){
		case ACTIVITY_NEW_TOKEN:
			if(resultCode==RESULT_OK)
				Toast.makeText(this,getString(R.string.new_token_toast_created), Toast.LENGTH_SHORT).show();
		}
	}
}