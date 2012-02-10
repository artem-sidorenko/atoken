package atoken.tworealities.eu;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.view.View;
import android.view.View.OnClickListener;

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
			setResult(RESULT_OK);
			finish();
		}

	};

}
