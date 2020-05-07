package liad.com.alcoholcalc;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import liad.com.alcoholcalc.converter.UIConverter;
import liad.com.alcoholcalc.converter.UIConverterImpl;
import liad.com.alcoholcalc.server.session.DrinkingSession;
import liad.com.alcoholcalc.server.user.GENDER;
import liad.com.alcoholcalc.server.user.SessionUser;

import static liad.com.alcoholcalc.R.id.submitUserBtn;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MyActivity";

    private DrinkingSession drinkingSession;

    private UIConverter uiConverter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        drinkingSession = DrinkingSession.getSession();
        uiConverter = new UIConverterImpl();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "starting alcohol calculator app");
        final Spinner genderSpinner = (Spinner) findViewById(R.id.genderSpinner);
        final String gender = (String)genderSpinner.getSelectedItem();
        final EditText weightText = (EditText)findViewById(R.id.weightInput);
        final Button submitUserButton = (Button) findViewById(submitUserBtn);
        submitUserButton.setClickable(false);
        handleWeightTextButtonRelation(weightText, gender, submitUserButton);


    }

    private void handleWeightTextButtonRelation(final EditText weightText, final String gender, final Button submitUserButton) {
        weightText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (weightText.getText().toString().isEmpty() == false){
                    weightText.getText().toString();
                    submitUserButton.setClickable(true);
                    submitUserButton.setBackgroundColor(Color.parseColor("#00FF00"));
                    final Integer weight = Integer.parseInt(weightText.getText().toString());
                    submitUserButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            submitUser(gender, weight);
                        }
                    });
                } else {
                    submitUserButton.setClickable(false);
                    submitUserButton.setBackgroundColor(Color.parseColor("#b3cccc"));

                }
            }
        });
    }


    private void submitUser(String genderString, Integer weightInt) {
        GENDER gender = uiConverter.convertUIGender(genderString);
        Double weight = uiConverter.convertUIWieght(weightInt);
        SessionUser user = new SessionUser(weight, gender);
        drinkingSession.setSessionUser(user);
        if (validateUser()) {
            Log.d(TAG, "send button pressed");
            goToSessionActivity();
        }
    }

    private void goToSessionActivity() {
        startActivity(new Intent(MainActivity.this, SessionActivity.class));
    }

    private boolean validateUser() {
        // todo implement
        return true;
    }
}
