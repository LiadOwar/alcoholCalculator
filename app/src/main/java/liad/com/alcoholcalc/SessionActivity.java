package liad.com.alcoholcalc;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.anastr.speedviewlib.SpeedView;
import com.github.anastr.speedviewlib.components.Section;
import com.github.anastr.speedviewlib.components.indicators.Indicator;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import org.joda.time.LocalDateTime;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import liad.com.alcoholcalc.server.session.DrinkingSession;
import liad.com.alcoholcalc.ui.BeverageIconLongClickListener;
import liad.com.alcoholcalc.ui.BeverageIconOnTouchListener;
import liad.com.alcoholcalc.ui.DrinkListItemOnClickListener;
import liad.com.alcoholcalc.ui.GaugeTickMapper;
import liad.com.alcoholcalc.ui.VibrateOnOnTouchListener;
import liad.com.alcoholcalc.ui.controller.UIController;
import liad.com.alcoholcalc.ui.controller.UIControllerImpl;
import liad.com.alcoholcalc.ui.drinkitem.UIDrinkItem;

public class SessionActivity extends AppCompatActivity implements Serializable {

    private List<ImageView> imageViews;

    private UIController uiController;

    private Timer timer;

    private TimerTask timerTask;

    private SpeedView gauge;

    private SpeedView futureGauge;

    private TextView timerView;

    private List<UIDrinkItem> currentActiveDrinks;

    private final Handler handler = new Handler();

    private Map<String, Integer> imageMap;

    private Integer fastForwardClickCounter;

    private Vibrator vibe;

    private VibrateOnOnTouchListener vibrateOnOnTouchListener;

    public final int LAYOUT_MAX_SIZE = 7;

    public SessionActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session);
        this.uiController = new UIControllerImpl();
        imageViews = Lists.newArrayList();
        configBeverageImageViews();
        this.gauge = configureScoreGauge();
        this.futureGauge = configureFutureGauge();
        this.timerView = (TextView)findViewById(R.id.timerView);
        this.vibe = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
        vibrateOnOnTouchListener = (new VibrateOnOnTouchListener(vibe));
        currentActiveDrinks = Lists.newArrayList();
        initMapImageIconsPaths();
        updateTimerView();
        initFastForward();
        initClearSessionBtn();
        initResetTimeSessionBtn();
//        pupulate8drinks();
    }

    private void pupulate8drinks() {

        for( int i = 0 ; i < 7; ++i) {
            uiController.addDrinkToSession("STRONGBEER_img_500");
            try {
                Thread.sleep(600);
            } catch (InterruptedException e) {


            }
        }
    }

    public UIController getUiController() {
        return uiController;
    }
    private void initClearSessionBtn() {
        Button clearSessionButton = (Button)findViewById(R.id.clearSessionBtn);
        final Dialog dialog = initAlertBeforeClearSession();
        clearSessionButton.setOnTouchListener(vibrateOnOnTouchListener);
        clearSessionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();

            }
        });
    }

    private void initResetTimeSessionBtn() {
        Button resetTimeButton = (Button)findViewById(R.id.resetTimeBtn);
        resetTimeButton.setOnTouchListener(vibrateOnOnTouchListener);
        resetTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fastForwardClickCounter = 0;
                uiController.addTimeToCurrentTime(fastForwardClickCounter);
            }
        });
    }

    private Dialog initAlertBeforeClearSession() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("ARE YOU SURE?")
                .setTitle("Clear Session");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                uiController.cleatSession();
                currentActiveDrinks.clear();
                fastForwardClickCounter = 0;
            }
        });
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });

        AlertDialog dialog = builder.create();
        return dialog;
    }

    private void initFastForward() {
        ImageView ffView = (ImageView)findViewById(R.id.fastForwardView);
        fastForwardClickCounter = 0;
        ffView.setOnTouchListener(vibrateOnOnTouchListener);
        ffView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ++fastForwardClickCounter;
                uiController.addTimeToCurrentTime(fastForwardClickCounter);
            }
        });
    }

    private void initMapImageIconsPaths() {
        this.imageMap = Maps.newHashMap();
        imageMap.put("strongbeer", R.drawable.strongbeer);
        imageMap.put("normalbeer", R.drawable.beer);
        imageMap.put("strongchaser", R.drawable.strongchaser);
    }

    private void updateTimerView() {
        LocalDateTime localDateTime = DrinkingSession.getSession().getCurrentDateTime();
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
                        updateGauge();
                        updateFutureGauge();
                        updateDrinksList();
                    }
                });
            }
        };
    }


    private void updateDrinksList() {
        List<UIDrinkItem> sessionDrinks = uiController.getSessionDrinks();
        sessionDrinks.sort(new Comparator<UIDrinkItem>() {
            @Override
            public int compare(UIDrinkItem o1, UIDrinkItem o2) {
                String o1DrinkingDateTime = o1.getDrinkingDateTime();
                LocalDateTime o1LocalDateTime = new LocalDateTime(o1DrinkingDateTime);
                String o2DrinkingDateTime = o2.getDrinkingDateTime();
                LocalDateTime o2LocalDateTime = new LocalDateTime(o2DrinkingDateTime);
                return o1LocalDateTime.compareTo(o2LocalDateTime);
            }
        });

        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.linear_layout);
        LinearLayout linearLayoutDesc = (LinearLayout)findViewById(R.id.linear_layout_desc);
        LinearLayout linearLayout2 = (LinearLayout)findViewById(R.id.linear_layout2);
        LinearLayout linearLayoutDesc2 = (LinearLayout)findViewById(R.id.linear_layout_desc2);

        for(UIDrinkItem drinkItem : sessionDrinks ) {
            ImageView imageView = createDrinkImage(drinkItem);
            if (sessionDrinks.indexOf(drinkItem) < 7) {
                configureViewAndPopulateLayouts(linearLayout, linearLayoutDesc, drinkItem, imageView);
            }
            else {
                configureViewAndPopulateLayouts(linearLayout2, linearLayoutDesc2, drinkItem, imageView);
            }
        }
        handleImageViewToRemove(linearLayout, sessionDrinks);
        handleImageViewToRemove(linearLayoutDesc, sessionDrinks);
        handleImageViewToRemove(linearLayout2, sessionDrinks);
        handleImageViewToRemove(linearLayoutDesc2, sessionDrinks);
        manageLayouts(linearLayout, linearLayoutDesc, linearLayout2, linearLayoutDesc2);
    }

    private void manageLayouts(LinearLayout linearLayout1, LinearLayout linearLayoutDesc1,
                               LinearLayout linearLayout2, LinearLayout linearLayoutDesc2) {
        int childCount1 = linearLayout1.getChildCount();
        int childCount2 = linearLayout2.getChildCount();

        if (childCount1 < LAYOUT_MAX_SIZE && childCount2 > 0) {
            int delta = LAYOUT_MAX_SIZE - childCount1;
            for (int i = 0 ; i < delta ; ++i) {
                View image = linearLayout2.getChildAt(i);
                View description = linearLayoutDesc2.getChildAt(i);
                linearLayout2.removeView(image);
                linearLayoutDesc2.removeView(description);
                linearLayout1.addView(image);
                linearLayoutDesc1.addView(description);
            }
        }
    }

    private void configureViewAndPopulateLayouts(LinearLayout linearLayout, LinearLayout linearLayoutDesc, UIDrinkItem drinkItem, ImageView imageView) {
        if (imageView != null) {
            imageView.setPadding(20, 0 ,0 , 0);
            linearLayout.addView(imageView);
            TextView detailsView = new TextView(this);
            String drinkDetailsText = createDrinkDetailsText(drinkItem);
            detailsView.setTag(imageView.getTag());
            detailsView.setText(drinkDetailsText);
            detailsView.setTextSize(10F);
            detailsView.setPadding(28,0,0,0);
            linearLayoutDesc.addView(detailsView);
        }
    }

    private String createDrinkDetailsText(UIDrinkItem drinkItem) {
        StringBuilder sb = new StringBuilder();
        String drinkingDateTime = drinkItem.getDrinkingDateTime();
        String formattedDrinkDateTime =  formatDrinkDateTime(drinkingDateTime);
        sb.append(formattedDrinkDateTime);

        return sb.toString();
    }

    private String formatDrinkDateTime(String drinkingDateTime) {
       String ret = "";
       String[] split = drinkingDateTime.split("\\.");
       ret = split[0].split("T")[1];


        return ret;
    }

    private void handleImageViewToRemove(LinearLayout linearLayout, List<UIDrinkItem> sessionDrinks) {
        int childCount = linearLayout.getChildCount();

        for (int i = childCount ; i >= 0; i--) {
            View view = linearLayout.getChildAt(i);
            if (view != null){
                boolean imageInDrinksList = isImageInDrinksList(view, sessionDrinks);
                if (imageInDrinksList == false) {
                    linearLayout.removeView(view);
                }
            }
        }
    }

    private boolean isImageInDrinksList(View view, List<UIDrinkItem> sessionDrinks) {

        String tag = (String)view.getTag();
        String[] split = tag.split("_");
        for (UIDrinkItem uiDrinkItem : sessionDrinks) {
            if (split[1].equals(uiDrinkItem.getDrinkingDateTime())) {
                return true;
            }
        }
        return false;
    }

    private ImageView createDrinkImage(UIDrinkItem drinkItem) {
        if (isNewDrink(drinkItem)) {
            String drinkType = drinkItem.getDrinkType();
            String formattedDrinkType = drinkType.replace("_", "").toLowerCase();
            String drinkingDateTime = drinkItem.getDrinkingDateTime();
            String tag = formattedDrinkType.concat("_" + drinkingDateTime);
            ImageView drinkView = new ImageView(this);
            drinkView.setOnClickListener(new DrinkListItemOnClickListener(drinkItem, uiController, vibe, currentActiveDrinks));
            drinkView.setOnTouchListener(vibrateOnOnTouchListener);
            drinkView.setTag(tag);
            drinkView.setImageResource(imageMap.get(formattedDrinkType));
            currentActiveDrinks.add(drinkItem);
            return drinkView;
        }

        return null;
    }


    private boolean isNewDrink(UIDrinkItem drinkItem) {
        String drinkingDateTime = drinkItem.getDrinkingDateTime();

        for (UIDrinkItem activeDrinkItem : currentActiveDrinks) {
            String drinkingDateTimeFromList = activeDrinkItem.getDrinkingDateTime();
            if (drinkingDateTimeFromList.equals(drinkingDateTime)) {
                return false;
            }

        }
        return true;
    }

    private void updateGauge() {
        Double alcoholScore = uiController.getAlcoholScore();
        gauge.setSpeedAt(alcoholScore.floatValue());
    }

    private void updateFutureGauge() {
        Double alcoholScore = uiController.getFutureAlcoholScore();
        futureGauge.setSpeedAt(alcoholScore.floatValue());
    }



    private SpeedView configureScoreGauge() {
        final SpeedView speedometer = (SpeedView)findViewById(R.id.speedView);
        speedometer.setSpeedAt(0f);
        speedometer.setMinMaxSpeed(0.0f, 50f);
        speedometer.setUnit("Score");
        speedometer.setWithTremble(false);
        speedometer.clearSections();
        Section greenSection = new Section(0f, 0.1f, Color.GREEN, 80);
        Section yellowSection = new Section(0.1f, 0.2f, Color.YELLOW, 80);
        Section orangeSection = new Section(0.2f, 0.4f, Color.rgb(247, 138, 5), 80);
        Section redSection = new Section(0.4f, 0.8f, Color.RED,80);
        Section blackSection = new Section(0.8f, 1f, Color.BLACK,80);
        speedometer.addSections(greenSection, yellowSection, orangeSection, redSection, blackSection);
        speedometer.setTickNumber(20);
        speedometer.setOnPrintTickLabel(new GaugeTickMapper());



        return speedometer;
    }

    private SpeedView configureFutureGauge() {
        SpeedView speedometer = (SpeedView)findViewById(R.id.futureSpeedView);
        speedometer.setSpeedAt(0f);
        speedometer.setMinMaxSpeed(0.0f, 50f);
        speedometer.setWithTremble(false);
        speedometer.setUnitTextColor(android.R.color.transparent);
        speedometer.setSpeedTextColor(android.R.color.transparent);
        ArrayList<Section> sections = speedometer.getSections();
        for (Section section : sections) {
            section.setColor(android.R.color.transparent);
        }
        speedometer.setIndicator(Indicator.Indicators.NeedleIndicator);
        speedometer.getIndicator().setColor(Color.rgb(48, 8, 36));

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
            imageView.setOnLongClickListener(new BeverageIconLongClickListener(this));
        }
    }

}
