package liad.com.alcoholcalc.converter;

import org.json.JSONObject;

import liad.com.alcoholcalc.server.user.GENDER;
import liad.com.alcoholcalc.ui.drinkitem.UIDrinkItem;

/**
 * Created by liad on 01/05/2020.
 */

public interface UIConverter {

    Double convertUIWieght(Integer weightInt);

    GENDER convertUIGender (String genderString);

    UIDrinkItem convertJSONToUIDrinkItem(JSONObject jsonObject);

}
