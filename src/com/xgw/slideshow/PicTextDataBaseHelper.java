package com.xgw.slideshow;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class PicTextDataBaseHelper extends SQLiteOpenHelper {

	
	public PicTextDataBaseHelper(Context context) {
		super(context, PicTableMetaData.DATABASE_NAME, null, PicTableMetaData.DATABASE_VERSION);
		
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE " + PicTableMetaData.TABLE_NAME + " ("
				+ PicTableMetaData._ID + " INTERGER PRIMARY KEY, "
				+ PicTableMetaData.IMAGE_ID + " INTERGER,"
				+ PicTableMetaData.IMAGE_PATH + " TEXT,"
				+ PicTableMetaData.IMAGE_TEXT + " TEXT"
				+ ");");
        
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}
	
	public ImageText getImage(int imageId){
		ImageText it = new ImageText();
		SQLiteDatabase db = this.getReadableDatabase();
		String[] columns = new String[]{PicTableMetaData._ID, PicTableMetaData.IMAGE_ID,PicTableMetaData.IMAGE_PATH
				,PicTableMetaData.IMAGE_TEXT};
		String[] selectionArgs = new String[]{""+imageId};
		Cursor cur = db.query(PicTableMetaData.TABLE_NAME, columns, PicTableMetaData.IMAGE_ID + "=?", selectionArgs, 
				null, null, null, null);
		if(cur.moveToFirst()){
			
			int idColumn = cur.getColumnIndex(PicTableMetaData._ID);
			int imgIdColumn = cur.getColumnIndex(PicTableMetaData.IMAGE_ID);
			int imgPathColumn = cur.getColumnIndex(PicTableMetaData.IMAGE_PATH);
			int imgTextColumn = cur.getColumnIndex(PicTableMetaData.IMAGE_TEXT);
			it.id = cur.getInt(idColumn);
			it.imageId = cur.getInt(imgIdColumn);
			it.imagePath = cur.getString(imgPathColumn);
			it.imageText = cur.getString(imgTextColumn);
		}
		cur.close();
		db.close();
		return it;
	}
	
	public void insertImage(ImageText it){
		Log.d("xgw", "insert image.");
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(PicTableMetaData.IMAGE_ID, it.imageId);
		values.put(PicTableMetaData.IMAGE_PATH, it.imagePath);
		values.put(PicTableMetaData.IMAGE_TEXT, it.imageText);
		
		long rowId = db.insert(PicTableMetaData.TABLE_NAME, null, values);
		it.id = (int)rowId;
		db.close();
	}
	
	public void updateImage(ImageText it){
		Log.d("xgw", "update image.");
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(PicTableMetaData.IMAGE_ID, it.imageId);
		values.put(PicTableMetaData.IMAGE_PATH, it.imagePath);
		values.put(PicTableMetaData.IMAGE_TEXT, it.imageText);
		db.update(PicTableMetaData.TABLE_NAME, values, PicTableMetaData.IMAGE_ID + "=?"
				, new String[]{""+it.imageId});
		db.close();
	}

	public void insertOrUpdateImage(ImageText it){
		ImageText oIt = this.getImage(it.imageId);
		if(oIt.id < 0){
			this.insertImage(it);
		}else{
			it.id = oIt.id;
			this.updateImage(it);
		}
		
	}
}
