package liad.com.alcoholcalc.converter;

import org.json.JSONException;
import org.json.JSONObject;

import liad.com.alcoholcalc.server.user.GENDER;
import liad.com.alcoholcalc.ui.drinkitem.UIDrinkItem;

/**
 * Created by liad on 01/05/2020.
 */

public class UIConverterImpl implements UIConverter {

    @Override
    public Double convertUIWieght(Integer weightInt) {
        return Double.valueOf(weightInt);
    }

    @Override
    public GENDER convertUIGender(String genderString) {
        switch (genderString) {
            case "MALE" : return GENDER.MALE;
            case "FEMALE" : return GENDER.FEMALE;
            default: return GENDER.MALE;
        }
    }

    @Override
    public UIDrinkItem convertJSONToUIDrinkItem(JSONObject jsonObject) {

        UIDrinkItem ret = null;
        String type;
        String amount;
        String drinkTime;

        try {
            type = (String)jsonObject.get("type");
            amount = jsonObject.get("amount").toString();
            drinkTime = (jsonObject.get("drinkTime")).toString();
            ret = new UIDrinkItem(type, amount, drinkTime);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ret;

    }
}
