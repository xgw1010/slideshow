package com.xgw.slideshow;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.Transformation;

public class FlipAnimation extends Animation {
	    public static final int AXIS_Y = 0;
	    public static final int AXIS_X = 1;

	    float mCenterX, mCenterY;
	    float mFromDegree, mToDegree, mDistanceZ;
	    int mAxis = AXIS_Y;

	    public FlipAnimation(
	            float centerX,
	            float centerY,
	            float fromDegree,
	            float toDegree,
	            float distanceZ,
	            int axis) {
	        mCenterX = centerX;
	        mCenterY = centerY;
	        mFromDegree = fromDegree;
	        mToDegree = toDegree;
	        mDistanceZ = distanceZ;
	        mAxis = axis;
	    }

	    @Override
	    protected void applyTransformation(float interpolatedTime, Transformation t) {
	        float degree = mFromDegree + (mToDegree - mFromDegree) * interpolatedTime;
	        float distanceZ = mDistanceZ * interpolatedTime;
	        
	        Matrix matrix = t.getMatrix();
	        Camera camera = new Camera();
	        camera.save();
	        if (mDistanceZ != 0) {
	            camera.translate(0.0f, 0.0f, distanceZ);
	        }
	        if (mAxis == AXIS_X) {
	            camera.rotateX(degree);
	        } else if (mAxis == AXIS_Y) {
	            camera.rotateY(degree);
	        }
	        camera.getMatrix(matrix);
	        camera.restore();
	        
	        //设置旋转中心
	        matrix.preTranslate(-mCenterX, -mCenterY);
	        matrix.postTranslate(mCenterX, mCenterY);
	        
	        super.applyTransformation(interpolatedTime, t);
	    }
	
}
