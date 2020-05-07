package liad.com.alcoholcalc.gateway;

import com.google.common.collect.Maps;

import org.joda.time.LocalDateTime;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import liad.com.alcoholcalc.server.beverage.Beverage;
import liad.com.alcoholcalc.server.beverage.StrongBeer;
import liad.com.alcoholcalc.server.session.SessionDrinkItem;
import liad.com.alcoholcalc.server.session.SessionRunner;
import liad.com.alcoholcalc.server.session.SessionRunnerImpl;

/**
 * Created by liad on 07/05/2020.
 */

public class GatewayImpl implements Gateway {

    private SessionRunner sessionRunner = new SessionRunnerImpl();
    private Map<String, Beverage> beverageConverterMap = Maps.newHashMap();

    public GatewayImpl() {
        loadConverterMap();
    }

    @Override
    public void AddDrinkFromUIToServer(String drinkDetails) {

        try {
            JSONObject drinkDetailsJsonObject = new JSONObject(drinkDetails);
            String type = drinkDetailsJsonObject.getString("type");
            String amount = drinkDetailsJsonObject.getString("amount");
            Beverage beverage = convertStringToBeverage(type);
            SessionDrinkItem drinkItem = new SessionDrinkItem(beverage, Double.parseDouble(amount), LocalDateTime.now());
            sessionRunner.addDrinkItemToSession(drinkItem);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private Beverage convertStringToBeverage(String type) {
        return beverageConverterMap.get(type);
    }

    private void loadConverterMap() {
        beverageConverterMap.put("STRONG_BEER", new StrongBeer());
        beverageConverterMap.put("NORMAL_BEER", new StrongBeer());
        beverageConverterMap.put("STRONG_CHASER", new StrongBeer());
    }
}
