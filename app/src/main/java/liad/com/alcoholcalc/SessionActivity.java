package liad.com.alcoholcalc;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.github.anastr.speedviewlib.SpeedView;
import com.github.anastr.speedviewlib.components.Section;
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
        configureScoreGauge();


    }

    private void configureScoreGauge() {
        SpeedView speedometer = (SpeedView)findViewById(R.id.speedView);
        speedometer.setSpeedAt(0f);
        speedometer.setMinMaxSpeed(0.0f, 50f);
        speedometer.setUnit("Score");
        speedometer.setWithTremble(false);
        speedometer.clearSections();
        Section greenSection = new Section(0f, 0.1f, Color.GREEN, 20);
        Section yellowSection = new Section(0.1f, 0.2f, Color.YELLOW, 20);
        Section orangeSection = new Section(0.2f, 0.4f, Color.rgb(247, 138, 5), 20);
        Section redSection = new Section(0.4f, 0.8f, Color.RED,20);
        Section blackSection = new Section(0.8f, 1f, Color.BLACK,20);
        speedometer.addSections(greenSection, yellowSection, orangeSection, redSection, blackSection);
        speedometer.setTickNumber(7);

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
