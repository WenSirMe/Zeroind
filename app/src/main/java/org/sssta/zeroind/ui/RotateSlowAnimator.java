package org.sssta.zeroind.ui;

import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

/**
 * Created by Heaven on 2015/10/31.
 */
public class RotateSlowAnimator {
    private ObjectAnimator objectAnimator = new ObjectAnimator();
    public RotateSlowAnimator(View mView,float strength,float beginRotation) {
        objectAnimator =
                ObjectAnimator.ofFloat(mView, "Rotation", beginRotation, beginRotation + strength)
                .setDuration((int)Math.abs(strength*2));
        objectAnimator
                .setInterpolator(new DecelerateInterpolator());
    }
    public void start(){
        objectAnimator.start();
    }
    public void end(){
        objectAnimator.cancel();
    }
    public boolean isRunning(){
        return objectAnimator.isRunning();
    }

}
