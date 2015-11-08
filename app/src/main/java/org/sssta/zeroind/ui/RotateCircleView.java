package org.sssta.zeroind.ui;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;

import org.sssta.zeroind.util.MathUtil;

import java.lang.ref.WeakReference;

/**
 * Created by Heaven on 2015/10/28.
 */
public class RotateCircleView extends View{
    private float lastRotation;
    private boolean isSetPivot = false;
    float nrot=0,drot=0;
    RotationChangeListener rotationChangeListener;
    RotateSlowAnimator rotateSlowAnimator;

    @Override
    public void setRotation(float rotation) {
        float mRemixedRotation = MathUtil.remixRotation(rotation);
        super.setRotation(mRemixedRotation);
        if (rotationChangeListener!=null){
            rotationChangeListener.rotationChange((int)mRemixedRotation);
        }
        //Log.i("rotation",String.valueOf(rotation));
    }

    public RotateCircleView(Context context) {
        super(context);
    }

    public RotateCircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RotateCircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public RotateCircleView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float touchX,touchY;
        touchX = event.getX()-getWidth()/2;
        touchY = event.getY()-getHeight();
        if (!isSetPivot){
            setPivotX(getWidth()/2);
            setPivotY(getHeight());
            isSetPivot = true;
        }
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                lastRotation = (float)Math.toDegrees(Math.atan2(touchY, touchX));
                if (rotateSlowAnimator != null && rotateSlowAnimator.isRunning()){
                    rotateSlowAnimator.end();
                }
                break;
            case MotionEvent.ACTION_MOVE:

                nrot = (float)Math.toDegrees(Math.atan2(touchY, touchX));
                drot = nrot - lastRotation;
                setRotation(getRotation() + drot);


                break;
            case MotionEvent.ACTION_UP:
                rotateSlowAnimator = new
                        RotateSlowAnimator(this,drot*100,(int)getRotation());
                rotateSlowAnimator.start();
                break;
        }
        return true;
    }

    public interface RotationChangeListener{
        void rotationChange(int rotation);
    }
    public void setOnWindFlagChangeListener(RotationChangeListener rotationChangeListener){
        this.rotationChangeListener = rotationChangeListener;
    }
    public void setRotateAnimatorEnd(){
        if (rotateSlowAnimator != null && rotateSlowAnimator.isRunning()){
            rotateSlowAnimator.end();
        }
    }
}
