package com.zx.pinke.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import com.zx.pinke.util.Log;
import com.zx.pinke.util.LogFactory;

public class IVProvider extends ContentProvider{

	private static final Log logger = LogFactory.getLog(IVProvider.class);
	private IVDatabaseHelper mHelper;
	
	private static final int URI_PUBLIC_SERVICE					= 1;
	private static final int URI_PUBLIC_SERVICE_ITEM			= 2;
	
	private static final UriMatcher mMatcher;
	static {
        mMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        mMatcher.addURI(IVDatas.AUTHORITY, "public_service", URI_PUBLIC_SERVICE);
        mMatcher.addURI(IVDatas.AUTHORITY, "public_service/#", URI_PUBLIC_SERVICE_ITEM);
	}
	
	@Override
	public boolean onCreate() {
		mHelper = IVDatabaseHelper.getInstance(getContext());
		return true;
	}
	
    private String parseSelection(String selection) {
        return (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : "");
    }

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		switch(mMatcher.match(uri)){
			case URI_PUBLIC_SERVICE:{
				qb.setTables(IVDatas.PUBLIC_SERVICE);
			}break;
			case URI_PUBLIC_SERVICE_ITEM:{
				qb.setTables(IVDatas.PUBLIC_SERVICE);
				qb.appendWhere(IVDatas.PublicServiceColumn.ID + "=" + uri.getPathSegments().get(1));
			}break;
			default:
	           throw new IllegalArgumentException("Unknown URI " + uri);
		}
		
		String orderBy = null;
		if(TextUtils.isEmpty(sortOrder)){
			orderBy = IVDatas.DEFAULT_SORT_ORDER;
		}else{
			orderBy = sortOrder;
		}
		
		SQLiteDatabase db = mHelper.getReadableDatabase();
		
		Cursor c = qb.query(db, projection, selection, selectionArgs, null, null, orderBy);
		if(c != null)
			c.setNotificationUri(getContext().getContentResolver(), uri);
		
		return c;
	}

	@Override
	public String getType(Uri uri) {
		switch(mMatcher.match(uri)){
			case URI_PUBLIC_SERVICE:{
				return IVDatas.CONTENT_TYPE_DIR;
			}
			case URI_PUBLIC_SERVICE_ITEM:{
				return IVDatas.CONTENT_TYPE_ITEM;
			}
		}
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		SQLiteDatabase db = mHelper.getWritableDatabase();
		long rowId = 0;
		switch(mMatcher.match(uri)){
			case URI_PUBLIC_SERVICE :{
				rowId = db.insert(IVDatas.PUBLIC_SERVICE, null, values);
			}break;
			default:
                throw new IllegalArgumentException("Unknown URI " + uri);
		}
		
		if(rowId > 0){
			Uri insertedUri = null;
			switch(mMatcher.match(uri)){
				case URI_PUBLIC_SERVICE:{
					insertedUri = ContentUris.withAppendedId(IVDatas.CONTENT_PUBLIC_SERVICE_URI, rowId);
				}break;
			}
		
			getContext().getContentResolver().notifyChange(insertedUri, null);
			logger.debug("Insert record success uri:"+insertedUri);
			
			return insertedUri;
		}
		
		throw new SQLException("Fail to insert row into " + uri);
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		
		int count = 0;
		String rowId = null;
		SQLiteDatabase db = mHelper.getWritableDatabase();
		switch(mMatcher.match(uri)){
			case URI_PUBLIC_SERVICE:{
				count = db.delete(IVDatas.PUBLIC_SERVICE, selection, selectionArgs);
			}break;
			case URI_PUBLIC_SERVICE_ITEM:{
				rowId = uri.getPathSegments().get(1);
				long id = Long.parseLong(rowId);
				count = db.delete(IVDatas.PUBLIC_SERVICE,
						IVDatas.PublicServiceColumn.ID + "=" + id + parseSelection(selection), selectionArgs);
			}break;
			default:
	           throw new IllegalArgumentException("Unknown URI " + uri);
			
		}
		if (count > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
            logger.debug("Delete record success uri:"+uri+",count:"+count);
        }
		return count;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		int count = 0;
		SQLiteDatabase db = mHelper.getWritableDatabase();
		switch(mMatcher.match(uri)){
			case URI_PUBLIC_SERVICE:{
				count = db.update(IVDatas.PUBLIC_SERVICE, values, selection, selectionArgs);
			}break;
			case URI_PUBLIC_SERVICE_ITEM:{
				String rowId = uri.getPathSegments().get(1);
	            count = db.update(IVDatas.PUBLIC_SERVICE, values, IVDatas.PublicServiceColumn.ID + "=" + rowId
	                    + parseSelection(selection), selectionArgs);
			}break;
			default:
	            throw new IllegalArgumentException("Unknown URI " + uri);
		}
		
		if (count > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
            logger.debug("Update record success uri:"+uri+",count="+count);
        }
		return count;
	}

}
