package liad.com.alcoholcalc;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

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

    }

    private void configBeverageImageViews() {
        final ImageView StrongBeerImgView = (ImageView)findViewById(R.id.StrongBeerImg);
        final ImageView NormslBeerImgView= (ImageView)findViewById(R.id.NormalBeerImg);
        final ImageView StrongChaserImgView= (ImageView)findViewById(R.id.StrongChaserImg);
        imageViews.add(StrongBeerImgView);
        imageViews.add(NormslBeerImgView);
        imageViews.add(StrongChaserImgView);

        for (ImageView imageView : imageViews) {
            imageView.setOnLongClickListener(new BeverageIconLongClickListener());
        }
    }

}
