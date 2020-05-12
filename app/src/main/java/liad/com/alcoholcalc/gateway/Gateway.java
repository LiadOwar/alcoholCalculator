package liad.com.alcoholcalc.gateway;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by liad on 07/05/2020.
 */

public interface Gateway {

    void AddDrinkFromUIToServer(JSONObject drinkDetails);

    Double getAlcoholScore();

    Double getFutureAlcoholScore();

    List<JSONObject> getSessionDrinks();
}
