package com.example.saurabhgoyal.buyhatkeproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class SQLiteHandler extends SQLiteOpenHelper {

	private static final String TAG = SQLiteHandler.class.getSimpleName();

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "android_api";
	private static SQLiteHandler mInstance = null;


	// Login table name
	private static final String TABLE_USER = "user";
	private static final String TABLE_MESSAGE = "message";

	public static final String KEY_EMAIL = "email";
	public static final String KEY_USEREMAIL = "useremail";
	public static final String KEY_RECIEVEREMAIL = "recieveremail";

	public static final String KEY_RECIEVERNAME = "recievername";

	public static final String KEY_USERNAME = "username";
	public static final String KEY_MESSAGE = "message";

	public static final String KEY_ID = "id";

	public static final String KEY_TOKEN = "token";



	public SQLiteHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	public static SQLiteHandler getInstance(Context ctx) {
		/**
		 * use the application context as suggested by CommonsWare.
		 * this will ensure that you dont accidentally leak an Activitys
		 * context (see this article for more information:
		 * http://android-developers.blogspot.nl/2009/01/avoiding-memory-leaks.html)
		 */
		if (mInstance == null) {
			mInstance = new SQLiteHandler(ctx.getApplicationContext());
		}
		return mInstance;
	}


	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {


		String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
				+ KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_EMAIL + " TEXT,"+ KEY_TOKEN+" TEXT,"+ KEY_USERNAME+" TEXT" +")";
		db.execSQL(CREATE_USER_TABLE);
		String CREATE_MESSAGE_TABLE = "CREATE TABLE " + TABLE_MESSAGE + "("
				+ KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_MESSAGE + " TEXT,"+ KEY_USEREMAIL+" TEXT,"
				+ KEY_USERNAME+" TEXT,"+KEY_RECIEVEREMAIL+" TEXT,"+KEY_RECIEVERNAME+" TEXT,"+KEY_TOKEN+" TEXT"


				+")";
		db.execSQL(CREATE_MESSAGE_TABLE);





	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);

		// Create tables again
		onCreate(db);
	}
	public void insertUser(ArrayList<Users> categories)
	{
		try {
			SQLiteDatabase db = this.getWritableDatabase();
			for (Users category : categories) {
				Cursor cursor = db.rawQuery(
						"Select count(*) from user where  id='" + category.getId() + "'", null);
				// Cursor cursor=db.rawQuery("Select * from newss", null);

				cursor.moveToFirst();
				if (cursor.getInt(0) <= 0) {
					ContentValues companyValues = new ContentValues();
					companyValues.put(KEY_ID, category.getId());
					companyValues.put(KEY_EMAIL, category.getEmail());
					companyValues.put(KEY_TOKEN, category.getToken());
					companyValues.put(KEY_USERNAME, category.getUsername());

					long id1 = db.insert("user", null, companyValues);
					Log.d(TAG, "New category category into sqlite: " + id1);


				}
			}
		} catch (Exception ex) {
			Log.e("Exception in adding product", ex.getMessage());
			File sdcard = Environment.getExternalStorageDirectory();
			File file = new File(sdcard, "ErrorLog.txt");
			try {
				BufferedWriter bw = new BufferedWriter(new FileWriter(file));
				bw.append(ex.getMessage());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


		}

	}
	public void insertMessage(MessageEntity category)
	{
		try {
			SQLiteDatabase db = this.getWritableDatabase();


				Cursor cursor = db.rawQuery(
						"Select count(*) from message where  id='" + category.getId() + "'", null);
				// Cursor cursor=db.rawQuery("Select * from newss", null);

				cursor.moveToFirst();
				if (cursor.getInt(0) <= 0) {
					ContentValues companyValues = new ContentValues();
					companyValues.put(KEY_ID, category.getId());
					companyValues.put(KEY_MESSAGE, category.getMessage());
					companyValues.put(KEY_USEREMAIL, category.getUseremail());
					companyValues.put(KEY_USERNAME, category.getUsername());
					companyValues.put(KEY_RECIEVEREMAIL, category.getRecieveremail());
					companyValues.put(KEY_RECIEVERNAME, category.getRecievername());
					companyValues.put(KEY_TOKEN, category.getToken());
					long id1 = db.insert("message", null, companyValues);
					Log.d(TAG, "New category category into sqlite: " + id1);



			}
		} catch (Exception ex) {
			Log.e("Exception in adding product", ex.getMessage());
			File sdcard = Environment.getExternalStorageDirectory();
			File file = new File(sdcard, "ErrorLog.txt");
			try {
				BufferedWriter bw = new BufferedWriter(new FileWriter(file));
				bw.append(ex.getMessage());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


		}

	}

	public ArrayList<Users> fetchUser()
	{

		ArrayList<Users> userList = new ArrayList<Users>();
		SQLiteDatabase db = this.getWritableDatabase();

		Cursor cursor = db
				.rawQuery(
						"select * from user  ",
						null);

		if (cursor.getCount() > 0) {
			cursor.moveToFirst();
			do {
				Users product = new Users();
				product.setId(cursor.getString(0));
				product.setEmail(cursor.getString(1));
				product.setToken(cursor.getString(2));
				product.setUsername(cursor.getString(3));

				userList.add(product);
			} while (cursor.moveToNext());
		}
		return userList;

	}
	public ArrayList<MessageEntity> fetchMessage( String email,String recieverEmail)
	{

		ArrayList<MessageEntity> messgaeList = new ArrayList<MessageEntity>();
		SQLiteDatabase db = this.getWritableDatabase();

		Cursor cursor = db
				.rawQuery(
						"select * from message where useremail='"+email+"' AND recieveremail='"+recieverEmail+"'", null);

		if (cursor.getCount() > 0) {
			cursor.moveToFirst();
			do {
				MessageEntity product = new MessageEntity();
				product.setId(cursor.getString(0));
				product.setMessage(cursor.getString(1));
				product.setUseremail(cursor.getString(2));
				product.setUsername(cursor.getString(3));
				product.setRecieveremail(cursor.getString(4));

				product.setRecievername(cursor.getString(5));
				product.setToken(cursor.getString(6));

				messgaeList.add(product);
			} while (cursor.moveToNext());
		}
		return messgaeList;

	}

	public ArrayList<MessageEntity> fetchUserMessage( String email) {

		ArrayList<MessageEntity> messgaeList = new ArrayList<MessageEntity>();
		SQLiteDatabase db = this.getWritableDatabase();

		Cursor cursor = db
				.rawQuery(
						"select * from message where useremail='" + email + "' OR recieveremail='" + email + "'", null);

		if (cursor.getCount() > 0) {
			cursor.moveToFirst();
			do {
				MessageEntity product = new MessageEntity();
				product.setId(cursor.getString(0));
				product.setMessage(cursor.getString(1));
				product.setUseremail(cursor.getString(2));
				product.setUsername(cursor.getString(3));
				product.setRecieveremail(cursor.getString(4));

				product.setRecievername(cursor.getString(5));
				product.setToken(cursor.getString(6));

				messgaeList.add(product);
			} while (cursor.moveToNext());
		}
		return messgaeList;
	}

	public ArrayList<MessageEntity> fetchSearchMessage( String message) {

		ArrayList<MessageEntity> messgaeList = new ArrayList<MessageEntity>();
		SQLiteDatabase db = this.getWritableDatabase();

		Cursor cursor = db
				.rawQuery(
						"select * from message where message like '%"+message+"%'", null);

		if (cursor.getCount() > 0) {
			cursor.moveToFirst();
			do {
				MessageEntity product = new MessageEntity();
				product.setId(cursor.getString(0));
				product.setMessage(cursor.getString(1));
				product.setUseremail(cursor.getString(2));
				product.setUsername(cursor.getString(3));
				product.setRecieveremail(cursor.getString(4));

				product.setRecievername(cursor.getString(5));
				product.setToken(cursor.getString(6));

				messgaeList.add(product);
			} while (cursor.moveToNext());
		}
		return messgaeList;
	}








}
