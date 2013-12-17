package com.xgw.slideshow;

import android.provider.BaseColumns;

public  final class PicTableMetaData implements BaseColumns {
	
	public static String DATABASE_NAME = "picText.db";
	public static int DATABASE_VERSION = 1;
	public static String TABLE_NAME = "picText";
	
    public static String IMAGE_ID = "imageId";
    public static String IMAGE_PATH = "imagePath";
    public static String IMAGE_TEXT = "imageText";
}
