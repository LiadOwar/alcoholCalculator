package liad.com.alcoholcalc.ui;

import android.view.MotionEvent;
import android.view.View;

public class BeverageIconOnTouchListener implements View.OnTouchListener {
    private float scale = 2;

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (v.getScaleX() == 1) {
            v.setScaleX(scale);
            v.setScaleY(scale);
        } else {
            v.setScaleX(1);
            v.setScaleY(1);
        }
        return false;
    }
}
