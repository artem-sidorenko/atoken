package atoken.tworealities.eu;

import java.util.ArrayList;
import android.app.AlertDialog.Builder;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import atoken.tworealities.eu.R;
import atoken.tworealities.eu.classes.DBAdapter;
import atoken.tworealities.eu.classes.EventToken;
import atoken.tworealities.eu.classes.TimeToken;
import atoken.tworealities.eu.classes.Token;

public class Main extends ListActivity {
	private static final int ACTIVITY_NEW_TOKEN=0;
	private static final int ACTIVITY_EDIT_TOKEN=1;
	private ArrayList<Token> token_list;
	private DBAdapter db;


	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		fillTokens();
		registerForContextMenu(getListView());
		db = new DBAdapter(this);
	}
	
	

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		db.close();
	}



	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_menu, menu);
		return true;
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_list_context_menu, menu);
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
	public boolean onContextItemSelected(final MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		final Token token = token_list.get((int) info.position);
		
		switch(item.getItemId()) {
		case R.id.main_list_context_menu_edit:
			Intent i = new Intent(this,New_token.class);
			i.putExtra("token", token);
			startActivityForResult(i, ACTIVITY_EDIT_TOKEN);
			return true;
		case R.id.main_list_context_menu_delete:
			Builder builder = new Builder(this);
			builder.setMessage(getString(R.string.main_dialog_text_delete_token));
			builder.setCancelable(true);
			builder.setPositiveButton(getString(R.string.main_dialog_button_delete), new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					DBAdapter db = new DBAdapter(getApplicationContext());
					db.deleteToken(token);
					fillTokens();
				}
			});
			builder.setNegativeButton(getString(R.string.main_dialog_button_cancel), null);
			builder.create().show();
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
			if(resultCode==RESULT_OK){
				Token token = (Token) data.getSerializableExtra("token");
				db.createToken(token);
				Toast.makeText(this,getString(R.string.new_token_toast_created), Toast.LENGTH_SHORT).show();
				fillTokens();
			}
			break;
		case ACTIVITY_EDIT_TOKEN:
			if(resultCode==RESULT_OK){
				// TODO also change of types!
				Token token = (Token) data.getSerializableExtra("token");
				db.updateToken(token);
				Toast.makeText(this,getString(R.string.new_token_toast_updated), Toast.LENGTH_SHORT).show();
				fillTokens();
			}
			break;
		}
	}
	
	public void fillTokens(){
		ListAdapter list_adapter = new ListAdapter(this);
		
		if(list_adapter.getCount()!=0)
			findViewById(R.id.no_tokens).setVisibility(View.GONE);
		else
			findViewById(R.id.no_tokens).setVisibility(View.VISIBLE);
		
		setListAdapter(list_adapter);
	}
	
	private class ListAdapter extends BaseAdapter{
		
		
		
		public ListAdapter(Context context) {
			super();
			token_list = new ArrayList<Token>();
			DBAdapter db = new DBAdapter(context);
			Cursor c = db.getTokens();
			startManagingCursor(c);
			
			c.moveToFirst();
			while(!c.isAfterLast()){
				if(c.isNull(c.getColumnIndex(DBAdapter.KEY_TIME_TYPE)))
					token_list.add(new EventToken(c));
				else
					token_list.add(new TimeToken(c));
				c.moveToNext();
			}
			
			db.close();
		}

		public int getCount() {
			// TODO Auto-generated method stub
			return token_list.size();
		}

		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return token_list.get(position);
		}

		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return token_list.get(position).getId();
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			LayoutInflater inflater = getLayoutInflater();
			View token_item = inflater.inflate(R.layout.main_token_item, null);
			
			TextView name = (TextView) token_item.findViewById(R.id.token_item_name);
			TextView serial = (TextView) token_item.findViewById(R.id.token_item_serial);
			ProgressBar time = (ProgressBar) token_item.findViewById(R.id.token_item_time_bar);
			
			Token token = (Token) getItem(position);
			
			name.setText(token.getName());
			serial.setText(token.getSerial());
			time.setProgress(50);
			
			if(token instanceof EventToken){
				token_item.findViewById(R.id.token_item_time_panel).setVisibility(View.GONE);
				token_item.findViewById(R.id.token_item_event_pic).setVisibility(View.VISIBLE);
			}
			
			return token_item;
		}
		
	}
}