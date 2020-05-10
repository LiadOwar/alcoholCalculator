package liad.com.alcoholcalc.gateway;

import org.json.JSONObject;

/**
 * Created by liad on 07/05/2020.
 */

public interface Gateway {
    void AddDrinkFromUIToServer(JSONObject drinkDetails);

    Double getAlcoholScore();
}
