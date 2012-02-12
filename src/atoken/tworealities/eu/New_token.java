package atoken.tworealities.eu;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.view.View;
import android.view.View.OnClickListener;
import atoken.tworealities.eu.classes.DBAdapter;
import atoken.tworealities.eu.classes.EventToken;
import atoken.tworealities.eu.classes.TimeToken;
import atoken.tworealities.eu.classes.Token;

public class New_token extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_token);

		//filling spinner with token types
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.new_token_text_token_types, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		((Spinner) findViewById(R.id.token_type)).setAdapter(adapter);


		//listener for radio buttons
		findViewById(R.id.event_token).setOnClickListener(this.radio_listener);
		findViewById(R.id.time_token).setOnClickListener(this.radio_listener);

		//listener for create button
		findViewById(R.id.button_create).setOnClickListener(this.button_listener);
		
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			int token_id = extras.getInt(DBAdapter.KEY_MAIN_ID);
			DBAdapter db = new DBAdapter(this);
			db.open();
			Token token = db.getToken(token_id);
			((EditText) findViewById(R.id.token_name)).setText(token.getName());
			((EditText) findViewById(R.id.token_serial)).setText(token.getSerial());
		}
	}

	private OnClickListener radio_listener = new OnClickListener() {

		public void onClick(View v) {
			switch(v.getId()){
			case R.id.event_token:
				findViewById(R.id.time_token_details).setVisibility(View.GONE);
				break;
			case R.id.time_token:
				findViewById(R.id.time_token_details).setVisibility(View.VISIBLE);
				break;
			}
		}

	};

	private OnClickListener button_listener = new OnClickListener() {

		public void onClick(View v) {
			String name = ((EditText) findViewById(R.id.token_name)).getText().toString();
			String serial = ((EditText) findViewById(R.id.token_serial)).getText().toString();
			String seed = ((EditText) findViewById(R.id.token_seed)).getText().toString();
			DBAdapter db = new DBAdapter(v.getContext());
			db.open();
			
			if(((RadioButton) findViewById(R.id.event_token)).isChecked()){
				EventToken token = new EventToken(name,serial,seed);
				db.createToken(token);
			}else{
				TimeToken token = new TimeToken(name, serial, seed, 0);
				db.createToken(token);
			}

			db.close();
			setResult(RESULT_OK);
			finish();
		}

	};

}
