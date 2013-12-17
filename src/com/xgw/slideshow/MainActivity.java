package com.xgw.slideshow;

import java.util.ArrayList;
import java.util.List;

import com.umeng.analytics.MobclickAgent;

import net.youmi.android.AdManager;
import net.youmi.android.smart.SmartBannerManager;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.provider.MediaStore.Images.Thumbnails;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends Activity implements OnTouchListener {
	private List<ImageText> picList = new ArrayList<ImageText>();
    private int index = 0;
    private MyHandler handler;
    ImageView mIv;
    ImageView mIv2;
    Bitmap mBmCurrent;
    Bitmap mBmNext;
    
    RelativeLayout frame1 ;
    RelativeLayout frame2;
    
    TextView mTv1;
    TextView mTv2;
    TextView currentTV;
    String mCurrentText;
    String mNextText;
    
    private boolean hasOpenMenu;
    private int mSwitchDelay = 3000;
    private int mEffect;
    int count = 0;
    
    PicTextDataBaseHelper dbHelper = new PicTextDataBaseHelper(this);
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		RelativeLayout rl =(RelativeLayout) this.findViewById(R.id.root);
		rl.setOnTouchListener(this);
		PreferenceManager.setDefaultValues(this, R.xml.options, false);
		
		AdManager.getInstance(this).init("9d27a89794e0a8d0","c025ee197c26fd4d", false); 
		handler = new MyHandler(){

			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				
				if(msg.what == 1){
					Log.d("", "close menu");
					//if(hasOpenMenu)
						MainActivity.this.closeOptionsMenu();
				}
				else if(msg.what == 0){
					
					if(picList.size()>1){
						ImageView next = null;
						RelativeLayout currentFrame = null;
						RelativeLayout nextFrame = null;
						
						TextView nexTxt = null;
						int m = index%2;
						if(m == 0){
							next = mIv2;
							currentFrame = frame1;
							nextFrame = frame2;
							nexTxt = mTv2;
							currentTV = mTv2;
						}else{
							next = mIv;
							currentFrame = frame2;
							nextFrame = frame1;
							nexTxt = mTv1;
							currentTV = mTv1;
						}
						Animation animIn = null;
						Animation animOut = null;			
						int r = mEffect;
						if(r == 1000){
							r = (int)(Math.random()*5.0f);
						}
						if(r == 4){
							final ImageView n = next;
							final RelativeLayout nFrame = nextFrame;
							final RelativeLayout cFrame = currentFrame;
							nFrame.setVisibility(View.INVISIBLE);
							n.setImageBitmap(mBmNext);
							nexTxt.setText(mNextText);
							cFrame.setVisibility(View.VISIBLE);
							index++;
							animOut = AnimationManager.getFlipBackAnim(currentFrame);
							animOut.setAnimationListener(new AnimationListener() {
						         @Override
					             public void onAnimationStart(Animation animation) {    }        
					             @Override
					             public void onAnimationRepeat(Animation animation) {}
					             @Override
					             public void onAnimationEnd(Animation animation) {
					            	 nFrame.setVisibility(View.VISIBLE);
					            	 cFrame.setVisibility(View.INVISIBLE);
					            	 Animation anim = AnimationManager.getFlipFrontAnim(n);
					            	 anim.setAnimationListener(new AnimListener());
					            	 nFrame.startAnimation(anim);
					            	 
					             }
							});
							cFrame.startAnimation(animOut);
						}else {
							if(r == 0){
								animOut = AnimationManager.getLeftTranslateOutAnim(currentFrame);
								animIn = AnimationManager.getRightTranslateInAnim(currentFrame);
							}else if(r ==1){
								 animOut = new RotateTranslateAnimation(0, -currentFrame.getWidth(), 0, -180);
								 animIn = new RotateTranslateAnimation(currentFrame.getWidth(),0 , 180, 0);
							}else if(r == 2){
								animOut = AnimationManager.getFadeOutAnim();
								animIn = AnimationManager.getFadeInAnim();
							}else if(r == 3){
								animOut = AnimationManager.getBottomTranslateOutAnim(currentFrame);
								animIn = AnimationManager.getTopTranslateInAnim(next);
							}
							animOut.setAnimationListener(new AnimListener());
							currentFrame.startAnimation(animOut);
							nextFrame.setVisibility(View.VISIBLE);
							index++;
							next.setImageBitmap(mBmNext);
							nexTxt.setText(mNextText);
		                    nextFrame.startAnimation(animIn);
						}
		                    
						if(!handler.hasMessages(0))
						    handler.sendEmptyMessageDelayed(0, mSwitchDelay);
					}
				}
			}
		};
		ContentResolver cr = getContentResolver();
		String[] projection = { Thumbnails._ID, Thumbnails.IMAGE_ID,
           Thumbnails.DATA };
        Cursor cur = cr.query(Thumbnails.EXTERNAL_CONTENT_URI, projection,
           null, null, null);
        if(cur.moveToFirst())
        {
        	 int _id;
             int image_id;
             String image_path;
             int _idColumn = cur.getColumnIndex(Thumbnails._ID);
             int image_idColumn =cur.getColumnIndex(Thumbnails.IMAGE_ID);
             int dataColumn = cur.getColumnIndex(Thumbnails.DATA);
             do
             {
                // Get the field values
                _id = cur.getInt(_idColumn);
                image_id =cur.getInt(image_idColumn);
                image_path =cur.getString(dataColumn);
                ImageText it = new ImageText();
                it.imageId = image_id;
                it.imagePath = image_path;
                picList.add(it);
      
             }
             while (cur.moveToNext());
        }
        cur.close();
        count = picList.size();
        if(count>0){
        	ImageText it0 = picList.get(0);
        	ImageText it = null;
        	String path = it0.imagePath;
        	mIv = (ImageView)this.findViewById(R.id.pic);
        	mBmCurrent = BitmapFactory.decodeFile(path);
        	

        	mIv.setImageBitmap(mBmCurrent);
        	mIv2 = (ImageView)this.findViewById(R.id.pic2);
        	
        	frame1 = (RelativeLayout) this.findViewById(R.id.frame1);
        	frame2 = (RelativeLayout) this.findViewById(R.id.frame2);
        	
        	mTv1 = (TextView)this.findViewById(R.id.text1);
        	mTv2 =  (TextView)this.findViewById(R.id.text2);
        	currentTV = mTv1;
        	it = dbHelper.getImage(it0.imageId);
        	if(it.id >=0){
        		mCurrentText = it0.imageText = it.imageText;
        	    mTv1.setText(mCurrentText);
        	    
        	}
        	if(count >1){
        		ImageText it1 = picList.get(1);
    			mBmNext = BitmapFactory.decodeFile(it1.imagePath);
    			it = dbHelper.getImage(it1.imageId);
    			if(it.id >= 0){
    				this.mNextText = it1.imageText = it.imageText;
    			}
        	}
        }
        
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	}
	
	

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		handler.removeMessages(0);
		MobclickAgent.onPause(this);
	}



	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	    SharedPreferences pre = PreferenceManager.getDefaultSharedPreferences(this);
	    Resources resource = this.getResources();
	    mSwitchDelay = Integer.parseInt(pre.getString(resource.getString(R.string.interval_options), null))*1000;
	    mEffect = Integer.parseInt(pre.getString(resource.getString(R.string.switching_effect), null));
	    if(!handler.hasMessages(0))
	        handler.sendEmptyMessageDelayed(0, mSwitchDelay);
	}



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		
		Log.d("xgw", "oncreate menu.");
		
		return true;
	}
	
	@Override
	public void onOptionsMenuClosed(Menu menu) {
		// TODO Auto-generated method stub
		super.onOptionsMenuClosed(menu);
		if(!handler.hasMessages(0))
		    handler.sendEmptyMessageDelayed(0, mSwitchDelay);
		hasOpenMenu = false;
		
	}
	
	

	@Override
	public void openOptionsMenu() {
		// TODO Auto-generated method stub
		super.openOptionsMenu();
		Log.d("xgw", "open menu");
		hasOpenMenu = true;
		handler.removeMessages(0);
		SmartBannerManager.show(this);
	}



	private static class MyHandler extends Handler{
		
	}
	
	private class AnimListener implements AnimationListener{

		@Override
		public void onAnimationEnd(Animation animation) {
			/*if(mBmCurrent != null && !mBmCurrent.isRecycled()&&count > 2){
				mBmCurrent.recycle();
				mBmCurrent = null;
				System.gc();
			}*/
			ImageText it = picList.get((index+1)%picList.size());
			mBmCurrent = mBmNext;
			mBmNext = BitmapFactory.decodeFile(it.imagePath);
			mCurrentText = mNextText;
			
			mNextText = it.imageText = dbHelper.getImage(it.imageId).imageText;
			//mIv.setVisibility(View.VISIBLE);
			//mIv2.setVisibility(View.VISIBLE);
		}

		@Override
		public void onAnimationRepeat(Animation animation) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onAnimationStart(Animation animation) {
			// TODO Auto-generated method stub
			
		}
		
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		int id = item.getItemId();
		if(id == R.id.action_settings){
			Intent it = new Intent(this, SlidePreferenceActivity.class);
			this.startActivity(it);
		}else if(id == R.id.action_add_text){
			Intent it = new Intent(this, AddTextActivity.class);
			ImageText imgTxt = picList.get(index%picList.size());
			Bundle bundle = new Bundle();
			bundle.putSerializable(ImageText.key, imgTxt);
			it.putExtras(bundle);
			this.startActivityForResult(it, 100);
		}
		return true;
	}



	@Override
	public boolean onTouch(View v, MotionEvent event) {
		
		if(hasOpenMenu){
			this.closeOptionsMenu();
		}else{
		    this.openOptionsMenu();
		    handler.sendEmptyMessageDelayed(1, 3000);
		}
		return false;
	}
	
	 @Override  
	    protected void onActivityResult(int requestCode, int resultCode, Intent data)  
	    {  
	        //可以根据多个请求代码来作相应的操作  
	        if(100==resultCode)  
	        {  
	        	String text = data.getExtras().getString("image_text");
	        	currentTV.setText(text);
	        	ImageText it = picList.get((index+1)%picList.size());
	        	it.imageText = text;
	        }  
	        super.onActivityResult(requestCode, resultCode, data);  
	    }  

}
