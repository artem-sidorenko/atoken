package atoken._2realities.com;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBAdapter {
	/**some constants for main table*/
	private static final String MAIN_TABLE = "main";
	private static final String KEY_MAIN_ID = "_id";
	private static final String KEY_MAIN_NAME = "name";
	private static final String KEY_MAIN_SERIAL = "serial";
	private static final String KEY_MAIN_TYPE = "type";
	private static final String KEY_MAIN_SEED = "seed";
	
	/**some constants for table with event counters, id is the same as in the mail table*/
	private static final String EVENT_TABLE = "event";
	private static final String KEY_EVENT_ID = "_id";
	private static final String KEY_EVENT_COUNTER = "counter";
	
	/**other constants*/
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "atoken";
	
	/**SQL Queries*/
    private static final String DATABASE_CREATE =
            "CREATE TABLE "+MAIN_TABLE+" ("+KEY_MAIN_ID+" PRIMARY KEY AUTOINCREMENT, "+KEY_MAIN_NAME+" NOT NULL, "+KEY_MAIN_SERIAL+", "+KEY_MAIN_TYPE+", "+KEY_MAIN_SEED+" NOT NULL;"
            + "CREATE TABLE "+EVENT_TABLE+" ("+KEY_EVENT_ID+" PRIMARY KEY, "+KEY_EVENT_COUNTER+";";
	
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
			db.execSQL(DATABASE_CREATE);
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


}
