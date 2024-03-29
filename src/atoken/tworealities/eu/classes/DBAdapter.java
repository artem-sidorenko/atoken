package atoken.tworealities.eu.classes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBAdapter extends SQLiteOpenHelper{
	private static final String TAG = DBAdapter.class.getSimpleName();
	/**some constants for main table*/
	public static final String MAIN_TABLE = "main";
	public static final String KEY_MAIN_ID = "_id";
	public static final String KEY_MAIN_NAME = "name";
	public static final String KEY_MAIN_SERIAL = "serial";
	public static final String KEY_MAIN_SEED = "seed";

	/**some constants for table with event counters, id is the same as in the main table*/
	public static final String EVENT_TABLE = "event";
	public static final String KEY_EVENT_ID = "_id";
	public static final String KEY_EVENT_COUNTER = "counter";

	/**some constants for table with time token informations, id is the same as in the main table*/
	public static final String TIME_TABLE = "time";
	public static final String KEY_TIME_ID = "_id";
	public static final String KEY_TIME_TYPE = "type";

	/**other constants*/
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "atoken";

	/**SQL Queries*/
	private static final String DATABASE_CREATE_MAIN =
			"CREATE TABLE "+MAIN_TABLE+" ("+KEY_MAIN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+KEY_MAIN_NAME+" TEXT NOT NULL, "+KEY_MAIN_SERIAL+" TEXT, "+KEY_MAIN_SEED+" TEXT NOT NULL);";
	private static final String DATABASE_CREATE_EVENT =
			"CREATE TABLE "+EVENT_TABLE+" ("+KEY_EVENT_ID+" INTEGER PRIMARY KEY, "+KEY_EVENT_COUNTER+" INTEGER);";
	private static final String DATABASE_CREATE_TIME =
			"CREATE TABLE "+TIME_TABLE+" ("+KEY_TIME_ID+" INTEGER PRIMARY KEY, "+KEY_TIME_TYPE+" INTEGER);";
	private static final String QUERY_ALL_TOKENS =
			"SELECT "+MAIN_TABLE+".*, "+EVENT_TABLE+"."+KEY_EVENT_COUNTER+", "+TIME_TABLE+"."+KEY_TIME_TYPE
			+" FROM "+MAIN_TABLE+" LEFT JOIN "+EVENT_TABLE+" ON "+MAIN_TABLE+"."+KEY_MAIN_ID+"="+EVENT_TABLE+"."+KEY_EVENT_ID
			+" LEFT JOIN "+TIME_TABLE+ " ON "+MAIN_TABLE+"."+KEY_MAIN_ID+"="+TIME_TABLE+"."+KEY_TIME_ID;
	private static final String QUERY_TOKEN=
			"SELECT "+MAIN_TABLE+".*, "+EVENT_TABLE+"."+KEY_EVENT_COUNTER+", "+TIME_TABLE+"."+KEY_TIME_TYPE
			+" FROM "+MAIN_TABLE+" LEFT JOIN "+EVENT_TABLE+" ON "+MAIN_TABLE+"."+KEY_MAIN_ID+"="+EVENT_TABLE+"."+KEY_EVENT_ID
			+" LEFT JOIN "+TIME_TABLE+ " ON "+MAIN_TABLE+"."+KEY_MAIN_ID+"="+TIME_TABLE+"."+KEY_TIME_ID
			+" WHERE "+MAIN_TABLE+"."+KEY_MAIN_ID+"==?";

	public DBAdapter(Context ctx) {
		super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
	}

	public void createToken(Token token){
		SQLiteDatabase db = getWritableDatabase();
		ContentValues main_values = new ContentValues();
		main_values.put(KEY_MAIN_NAME, token.getName());
		main_values.put(KEY_MAIN_SERIAL, token.getSerial());
		main_values.put(KEY_MAIN_SEED, token.getSeed());
		long token_id = db.insert(MAIN_TABLE, null, main_values);

		
		if(token instanceof TimeToken){
			TimeToken time_token = (TimeToken) token;
			ContentValues time_values = new ContentValues();
			time_values.put(KEY_TIME_ID,token_id);
			time_values.put(KEY_TIME_TYPE,time_token.getType());
			db.insert(TIME_TABLE, null, time_values);
		}
		
		else if(token instanceof EventToken){
			ContentValues event_values = new ContentValues();
			event_values.put(KEY_EVENT_ID,token_id);
			event_values.put(KEY_EVENT_COUNTER,0);
			db.insert(EVENT_TABLE, null, event_values);
		}
	}
	
	public void updateToken(Token token){
		Log.d("DB:","updating");
		SQLiteDatabase db = getWritableDatabase();
		int token_id = token.getId();
		ContentValues main_values = new ContentValues();
		main_values.put(KEY_MAIN_NAME, token.getName());
		main_values.put(KEY_MAIN_SERIAL, token.getSerial());
		main_values.put(KEY_MAIN_SEED, token.getSeed());
		Log.d(TAG,"values: "+main_values.toString());
		db.update(MAIN_TABLE, main_values, KEY_MAIN_ID+"="+token_id, null);

		
		if(token instanceof TimeToken){
			TimeToken time_token = (TimeToken) token;
			ContentValues time_values = new ContentValues();
			time_values.put(KEY_TIME_ID,token_id);
			time_values.put(KEY_TIME_TYPE,time_token.getType());
			Log.d(TAG,"values: "+time_values.toString());
			db.update(TIME_TABLE, time_values, KEY_TIME_ID+"="+token_id, null);
		}
		
		else if(token instanceof EventToken){
			ContentValues event_values = new ContentValues();
			event_values.put(KEY_EVENT_ID,token_id);
			event_values.put(KEY_EVENT_COUNTER,((EventToken) token).getCounter());
			Log.d(TAG,"values: "+event_values.toString());
			db.update(EVENT_TABLE, event_values, KEY_EVENT_ID+"="+token_id, null);
		}
	}

	public Cursor getTokens(){
		return getReadableDatabase().rawQuery(QUERY_ALL_TOKENS, null);
	}
	
	public Token getToken(int id){
		Token token;
		String [] params = {Integer.toString(id)};
		Cursor c = getReadableDatabase().rawQuery(QUERY_TOKEN, params);
		c.moveToFirst();
		if(c.isNull(c.getColumnIndex(KEY_TIME_TYPE)))
			token = new EventToken(c);
		else
			token = new TimeToken(c);
		return token;
	}
	
	public void deleteToken(Token token){
		SQLiteDatabase db = getWritableDatabase();
		db.delete(MAIN_TABLE, KEY_MAIN_ID+"="+token.getId(),null);
		
		if(token instanceof TimeToken){
			db.delete(TIME_TABLE, KEY_TIME_ID+"="+token.getId(), null);
		}
		
		else if(token instanceof EventToken){
			db.delete(EVENT_TABLE, KEY_EVENT_ID+"="+token.getId(), null);
		}
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.i("AToken", "Creating DB");
		db.execSQL(DATABASE_CREATE_MAIN);
		db.execSQL(DATABASE_CREATE_EVENT);
		db.execSQL(DATABASE_CREATE_TIME);		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}
}
