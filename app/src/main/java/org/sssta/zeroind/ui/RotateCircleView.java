package org.sssta.zeroind.ui;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;

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
                setRotation(remixRotation(getRotation() + drot));
                Log.i("rot rot", String.valueOf(nrot) + " " + String.valueOf(lastRotation));
                if (rotationChangeListener!=null){
                    //There will do something at Activity.
                }
                break;
            case MotionEvent.ACTION_UP:
              rotateSlowAnimator = new
                        RotateSlowAnimator(this,drot*100,(int)getRotation());
                rotateSlowAnimator.start();
                break;
        }
        return true;
    }
    private float remixRotation(float rot){
        rot = rot % 360;
        if (rot<0) rot+=360;
        return rot;
    }
    public interface RotationChangeListener{
        void rotationChange(String str);
    }
    public void setWindFlagChangeListener(RotationChangeListener rotationChangeListener){
        this.rotationChangeListener = rotationChangeListener;
    }
}
