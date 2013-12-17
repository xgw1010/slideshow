package com.xgw.slideshow;

import android.animation.AnimatorSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;

public class AnimationManager {
	private static int inAnimaCount = 5;
	
    public static Animation getFadeInAnim(){
    	Animation anim = new AlphaAnimation(0.1f, 1f);
    	anim.setDuration(900);
    	anim.setFillAfter(true);
    	
    	return anim;
    }
    
    public static Animation getFadeOutAnim(){
    	Animation anim = new AlphaAnimation(1f, 0);
    	anim.setDuration(900);
    	anim.setFillAfter(true);
    	
    	return anim;
    }
    
    public static Animation getRightTranslateInAnim(View v){
    	Animation anim = new TranslateAnimation(v.getWidth(), 0, 0, 0);
    	anim.setDuration(1000);
    	anim.setFillAfter(true);
    	anim.setDetachWallpaper(true); 
    	anim.setInterpolator(new AccelerateDecelerateInterpolator());
    	return anim;
    	
    }
    public static Animation getLeftTranslateOutAnim(View v){
    	Animation anim = new TranslateAnimation(0,-v.getWidth(), 0, 0);
    	anim.setDuration(1000);
    	anim.setFillAfter(true);
    	anim.setDetachWallpaper(true); 
    	return anim;
    	
    }
    
    
    public static Animation getLeftTranslateInAnim(View v){
    	
    	Animation anim = new TranslateAnimation(-v.getWidth(), 0, 0, 0);
    	anim.setDuration(1000);
    	anim.setFillAfter(true);
    	anim.setDetachWallpaper(true); 
    	anim.setInterpolator(new AccelerateDecelerateInterpolator());
    	return anim;
    	
    }
    
    public static Animation getTopTranslateInAnim(View v){
    	AnimationSet animSet = new AnimationSet(false);
    	
    	Animation anim1 = new TranslateAnimation(0, 0, -v.getHeight(), 0);
    	anim1.setDuration(400);
    	anim1.setFillAfter(true);
    	animSet.addAnimation(anim1);
    	
    	Animation anim2 = new TranslateAnimation(0, 0, 0, -v.getHeight()/6);
    	anim2.setStartOffset(400);
    	anim2.setDuration(300);
    	anim2.setFillAfter(true);
    	animSet.addAnimation(anim2);
    	
    	Animation anim3 = new TranslateAnimation(0, 0, 0, v.getHeight()/6);
    	anim3.setStartOffset(700);
    	anim3.setDuration(200);
    	anim3.setFillAfter(true);
    	animSet.addAnimation(anim3);
       
    	return animSet;
    	
    }
    
    public static Animation getBottomTranslateOutAnim(View v){
    	Animation anim = new TranslateAnimation(0, 0, 0 , v.getHeight());
    	anim.setDuration(400);
    	anim.setFillAfter(true);
    	anim.setDetachWallpaper(true); 
    	return anim;
    }
    
    public static Animation getBottomTranslateInAnim(View v){
    	Animation anim = new TranslateAnimation(0, 0, v.getHeight(), 0);
    	anim.setDuration(1000);
    	anim.setFillAfter(true);
    	anim.setDetachWallpaper(true); 
    	return anim;
    	
    }
    
    public static Animation getFlipBackAnim(View v){
    	Animation anim = new FlipAnimation(v.getWidth()/2, v.getHeight()/2, 0, 90, 0, FlipAnimation.AXIS_Y);
    	anim.setDuration(1000);
    	anim.setFillAfter(true);
    	return anim;
    }
    
    public static Animation getFlipFrontAnim(View v){
    	Animation anim = new FlipAnimation(v.getWidth()/2, v.getHeight()/2, -90, 0, 0, FlipAnimation.AXIS_Y);
    	anim.setDuration(1000);
    	anim.setFillAfter(true);
    	return anim;
    }
    
    
    public static Animation getRandomInAnim(View v){
    	Animation anim = null;
    	int type =Math.round( (float)Math.random() * (inAnimaCount-1));
    	switch(type){
    	case 0:
    		anim = getFadeInAnim();
    		break;
    	case 1:
    		anim = getRightTranslateInAnim(v);
    		break;
    	case 2:
    		anim = getLeftTranslateInAnim(v);
    	    break;
    	case 3:
    		anim = getTopTranslateInAnim(v);
    		break;
    	case 4:
    		anim = getBottomTranslateInAnim(v);
    		break;
    	}
    	return anim;
    }
}
