package liad.com.alcoholcalc.ui;

import android.view.MotionEvent;
import android.view.View;

public class BeverageIconOnTouchListener implements View.OnTouchListener {
    private float bigScale = 2;
    private float smallScale = 1;
    private static final String TAG = "SessionActivity";

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (v.getScaleX() == 1) {
            changeIconSize(v, bigScale);
        }else if (event.getAction() == MotionEvent.ACTION_UP) {
            changeIconSize(v, smallScale);
        }
        return false;
    }

    private void changeIconSize(View v, float scale) {
        v.setScaleX(scale);
        v.setScaleY(scale);
    }
}
