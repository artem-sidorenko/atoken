package atoken.tworealities.eu.classes;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBAdapter {
	/**some constants for main table*/
	private static final String MAIN_TABLE = "main";
	private static final String KEY_MAIN_ID = "_id";
	private static final String KEY_MAIN_NAME = "name";
	private static final String KEY_MAIN_SERIAL = "serial";
	private static final String KEY_MAIN_SEED = "seed";

	/**some constants for table with event counters, id is the same as in the main table*/
	private static final String EVENT_TABLE = "event";
	private static final String KEY_EVENT_ID = "_id";
	private static final String KEY_EVENT_COUNTER = "counter";

	/**some constants for table with time token informations, id is the same as in the main table*/
	private static final String TIME_TABLE = "time";
	private static final String KEY_TIME_ID = "_id";
	private static final String KEY_TIME_TYPE = "type";

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

	/**some internals*/
	private final Context mCtx;
	private DBHelper mDBHelper;
	private SQLiteDatabase mDB;

	/**private class for SQLite*/
	private class DBHelper extends SQLiteOpenHelper{

		public DBHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
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

	public DBAdapter(Context ctx) {
		this.mCtx = ctx;
	}

	public void open(){
		mDBHelper = new DBHelper(this.mCtx);
		mDB = mDBHelper.getWritableDatabase();
	}

	public void close() {
		mDBHelper.close();
	}

	public void createToken(Token token){
		ContentValues main_values = new ContentValues();
		main_values.put(KEY_MAIN_NAME, token.getName());
		main_values.put(KEY_MAIN_SERIAL, token.getSerial());
		main_values.put(KEY_MAIN_SEED, token.getSeed());
		long token_id = mDB.insert(MAIN_TABLE, null, main_values);

		
		if(token instanceof TimeToken){
			TimeToken time_token = (TimeToken) token;
			ContentValues time_values = new ContentValues();
			time_values.put(KEY_TIME_ID,token_id);
			time_values.put(KEY_TIME_TYPE,time_token.getType());
			mDB.insert(TIME_TABLE, null, time_values);
		}
		
		else if(token instanceof EventToken){
			ContentValues event_values = new ContentValues();
			event_values.put(KEY_EVENT_ID,token_id);
			event_values.put(KEY_EVENT_COUNTER,0);
			mDB.insert(EVENT_TABLE, null, event_values);
		}
	}

}
