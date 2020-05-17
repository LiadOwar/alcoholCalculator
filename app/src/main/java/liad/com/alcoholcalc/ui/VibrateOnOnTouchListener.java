package liad.com.alcoholcalc.ui;

import android.os.Vibrator;
import android.view.MotionEvent;
import android.view.View;

public class VibrateOnOnTouchListener implements View.OnTouchListener {

    Vibrator vibe;

    public VibrateOnOnTouchListener(Vibrator vibe) {
        this.vibe = vibe;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            vibe.vibrate(50);
        }
        return false;
    }
}
