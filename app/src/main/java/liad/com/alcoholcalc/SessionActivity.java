package liad.com.alcoholcalc;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.github.anastr.speedviewlib.SpeedView;
import com.google.common.collect.Lists;

import java.util.List;

import liad.com.alcoholcalc.ui.BeverageIconLongClickListener;

public class SessionActivity extends AppCompatActivity {

    private List<ImageView> imageViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session);
        imageViews = Lists.newArrayList();
        configBeverageImageViews();
        SpeedView speedometer = (SpeedView)findViewById(R.id.speedView);
        speedometer.setSpeedAt(1f);
        speedometer.setMinMaxSpeed(0.0f, 50f);
        speedometer.setUnit("AlcoScore");
    }

    private void configBeverageImageViews() {
        final ImageView StrongBeerImgView = (ImageView)findViewById(R.id.StrongBeer_Img);
        final ImageView NormalBeerImgView= (ImageView)findViewById(R.id.NormalBeer_Img);
        final ImageView StrongChaserImgView= (ImageView)findViewById(R.id.StrongChaser_Img);
        imageViews.add(StrongBeerImgView);
        imageViews.add(NormalBeerImgView);
        imageViews.add(StrongChaserImgView);

        for (ImageView imageView : imageViews) {
            imageView.setOnLongClickListener(new BeverageIconLongClickListener());
        }
    }

}
