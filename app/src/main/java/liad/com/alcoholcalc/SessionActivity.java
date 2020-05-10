package liad.com.alcoholcalc;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.anastr.speedviewlib.SpeedView;
import com.github.anastr.speedviewlib.components.Section;
import com.google.common.collect.Lists;

import org.joda.time.LocalDateTime;

import java.io.Serializable;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import liad.com.alcoholcalc.ui.BeverageIconLongClickListener;
import liad.com.alcoholcalc.ui.BeverageIconOnTouchListener;
import liad.com.alcoholcalc.ui.controller.UIController;
import liad.com.alcoholcalc.ui.controller.UIControllerImpl;

public class SessionActivity extends AppCompatActivity implements Serializable {

    private List<ImageView> imageViews;

    private UIController uiController;

    private Timer timer;

    private TimerTask timerTask;

    private SpeedView gauge;

    private TextView timerView;

    private final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session);
        this.uiController = new UIControllerImpl();
        imageViews = Lists.newArrayList();
        configBeverageImageViews();
        this.gauge = configureScoreGauge();
        this.timerView = (TextView)findViewById(R.id.timerView);
        updateTimerView();
    }

    private void updateTimerView() {
        LocalDateTime localDateTime = LocalDateTime.now();
        int hourOfDay = localDateTime.getHourOfDay();
        int minuteOfHour = localDateTime.getMinuteOfHour();
        int secondOfMinute = localDateTime.getSecondOfMinute();
        String formattedTime = String.format("%s:%s:%s", hourOfDay, minuteOfHour, secondOfMinute);
        timerView.setText(formattedTime);
    }

    @Override
    protected void onResume() {
        super.onResume();
        startTimer();
    }

    private void startTimer() {
        timer = new Timer();
        initTimerTask();
        timer.schedule(timerTask, 1000, 1000);
    }

    private void initTimerTask() {
        timerTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        updateTimerView();
                        Double alcoholScore = uiController.getAlcoholScore();
                        gauge.setSpeedAt(alcoholScore.floatValue());
                    }
                });
            }
        };
    }

    public SessionActivity() {
    }

    private SpeedView configureScoreGauge() {
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

        return speedometer;
    }

    private void configBeverageImageViews() {
        final ImageView strongBeerImgView = (ImageView)findViewById(R.id.StrongBeer_Img);
        final ImageView normalBeerImgView = (ImageView)findViewById(R.id.NormalBeer_Img);
        final ImageView strongChaserImgView = (ImageView)findViewById(R.id.StrongChaser_Img);
        strongBeerImgView.setTag("StrongBeer_Img");
        normalBeerImgView.setTag("NormalBeer_Img");
        strongChaserImgView.setTag("StrongChaser_Img");

        imageViews.add(strongBeerImgView);
        imageViews.add(normalBeerImgView);
        imageViews.add(strongChaserImgView);

        for (ImageView imageView : imageViews) {
            imageView.setOnTouchListener(new BeverageIconOnTouchListener());
            imageView.setOnLongClickListener(new BeverageIconLongClickListener(uiController));
        }
    }

}
