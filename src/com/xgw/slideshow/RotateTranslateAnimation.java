package com.xgw.slideshow;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;

public class RotateTranslateAnimation extends Animation {
    private int centerX, centerY;
    Camera camera = new Camera();
    private float fromX,toX,fromDegree,toDegree;
    public RotateTranslateAnimation(float fromX, float toX, float fromDegree, float toDegree){
    	this.fromX = fromX;
    	this.toX = toX;
    	this.fromDegree = fromDegree;
    	this.toDegree = toDegree;
    }
	@Override
	protected void applyTransformation(float interpolatedTime, Transformation t) {
		Matrix matrix = t.getMatrix();
		/*matrix.setRotate(interpolatedTime*(-180));
		matrix.setTranslate(-interpolatedTime*centerX*2, 0);*/
		camera.save();
		float tran = 0;
		if(Math.abs(fromX) < Math.abs(toX)){
			tran = fromX + (toX -centerX*2)* interpolatedTime;
		}else{
			tran = fromX *(1- interpolatedTime) + toX ;
		}
		
		camera.translate(tran, 0, 0);
		float rot = 0;
		if(Math.abs(fromDegree)< Math.abs(toDegree)){
			rot = fromDegree + toDegree*interpolatedTime;
		}else{
			rot = fromDegree*(1-interpolatedTime) + toDegree;
		}
		
		camera.rotateZ(-rot);
		camera.getMatrix(matrix);
		matrix.preTranslate(-centerX*2/3*2, -centerY);
		
		matrix.postTranslate(centerX*2/3*2, centerY);
		
		camera.restore();
	}

	@Override
	public void initialize(int width, int height, int parentWidth,
			int parentHeight) {
		// TODO Auto-generated method stub
		super.initialize(width, height, parentWidth, parentHeight);
		centerX = width/2;
		centerY = height/2;
		setDuration(1000);
		setFillAfter(true);
		//this.setInterpolator(new LinearInterpolator());
	}

}
