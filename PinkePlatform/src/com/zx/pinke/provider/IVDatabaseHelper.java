package com.zx.pinke.provider;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.zx.pinke.provider.IVDatas.PublicServiceColumn;
import com.zx.pinke.util.Log;
import com.zx.pinke.util.LogFactory;

public class IVDatabaseHelper extends SQLiteOpenHelper{

	private static Log logger = LogFactory.getLog(IVDatabaseHelper.class);
	private static final String DB_NAME = "iv.db";
	private static final int DB_VERSION = 4;
	private Context mContext;
	private static IVDatabaseHelper mInstance;
	
	public IVDatabaseHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
		mContext = context;
	}
	
	public static synchronized IVDatabaseHelper getInstance(Context context) {
		if (mInstance == null) {
			mInstance = new IVDatabaseHelper(context);
		}
		return mInstance;
	}

	private static final String CREATE_PUBLIC_SERVICE_TABLE_SQL = 
			"CREATE TABLE " + IVDatas.PUBLIC_SERVICE +"(" +
					PublicServiceColumn.ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
					PublicServiceColumn.KEY + " TEXT NOT NULL DEFAULT '',"+
					PublicServiceColumn.TITLE + " TEXT NOT NULL DEFAULT ''," +
					PublicServiceColumn.CONTENT + " TEXT NOT NULL DEFAULT ''," +
					PublicServiceColumn.STATE + " TEXT NOT NULL DEFAULT ''," +
					PublicServiceColumn.START_DATE + " TEXT NOT NULL DEFAULT ''," +
					PublicServiceColumn.END_DATE + " TEXT NOT NULL DEFAULT ''," +
					PublicServiceColumn.PLACE + " TEXT NOT NULL DEFAULT ''," +
					PublicServiceColumn.CATEGORY + " TEXT NOT NULL DEFAULT ''," +
					PublicServiceColumn.JOIN_NUM + " INTEGER NOT NULL DEFAULT 0," +
					PublicServiceColumn.NEED_NUM + " INTEGER NOT NULL DEFAULT 0," +
					PublicServiceColumn.ICON_URL + " TEXT NOT NULL DEFAULT ''," +
					PublicServiceColumn.ICON + " BLOB," +
					PublicServiceColumn.CONTACTS + " TEXT NOT NULL DEFAULT ''" +
			")";
	
	
	private void createPublicServiceTable(SQLiteDatabase db){
		db.execSQL(CREATE_PUBLIC_SERVICE_TABLE_SQL);
		logger.debug("public_service table created.");
//		createTestPublicService(db);
	}
	
	private void createTestPublicService(SQLiteDatabase db){
		for(int i=0;i<20;i++){
			ContentValues values = new ContentValues();
			values.put(PublicServiceColumn.TITLE, "标题标题标题标题标题标题标题标题标题标题标题标题标题标题标题标题标题标题标题"+i);
			values.put(PublicServiceColumn.CONTENT, "内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容"+i);
			values.put(PublicServiceColumn.START_DATE, "2013-12-12");
			values.put(PublicServiceColumn.END_DATE, "2013-12-12");
			values.put(PublicServiceColumn.PLACE, "地点地点地点地点地点地点地点地点地点地点地点"+i);
			values.put(PublicServiceColumn.CATEGORY, "类别类别类别类别"+i);
			values.put(PublicServiceColumn.JOIN_NUM, 200);
			values.put(PublicServiceColumn.ICON_URL, "http://h.hiphotos.baidu.com/album/w%3D210%3Bq%3D75/sign=0a547691912397ddd6799f0569b9c38a/0eb30f2442a7d933a7f9ab05ad4bd11373f00176.jpg");
			values.put(PublicServiceColumn.CONTACTS, "联系人");
			db.insert(IVDatas.PUBLIC_SERVICE, null, values);
		}
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		createPublicServiceTable(db);
		
		//version 2
		db.execSQL("ALTER TABLE " + IVDatas.PUBLIC_SERVICE +" ADD "+
				PublicServiceColumn.LAUNCH + " TEXT NOT NULL DEFAULT ''");
		
		//version 3
		db.execSQL("ALTER TABLE " + IVDatas.PUBLIC_SERVICE +" ADD "+
				PublicServiceColumn.USERLIST + " TEXT NOT NULL DEFAULT ''");
		
		//version 4
		db.execSQL("ALTER TABLE " + IVDatas.PUBLIC_SERVICE +" ADD "+
				PublicServiceColumn.LAUNCHERINFO + " TEXT NOT NULL DEFAULT ''");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		if (oldVersion<2) {
			db.execSQL("ALTER TABLE " + IVDatas.PUBLIC_SERVICE +" ADD "+
					PublicServiceColumn.LAUNCH + " TEXT NOT NULL DEFAULT ''");
			oldVersion=2;
		}
		
		if (oldVersion<3) {
			db.execSQL("ALTER TABLE " + IVDatas.PUBLIC_SERVICE +" ADD "+
					PublicServiceColumn.USERLIST + " TEXT NOT NULL DEFAULT ''");
			oldVersion=3;
		}
		
		if (oldVersion<4) {
			db.execSQL("ALTER TABLE " + IVDatas.PUBLIC_SERVICE +" ADD "+
					PublicServiceColumn.LAUNCHERINFO + " TEXT NOT NULL DEFAULT ''");
			oldVersion=4;
		}
		
	}
	
}
