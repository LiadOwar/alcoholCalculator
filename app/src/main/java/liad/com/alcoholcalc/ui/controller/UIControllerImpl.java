package liad.com.alcoholcalc.ui.controller;

import com.google.common.collect.Lists;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import liad.com.alcoholcalc.converter.UIConverter;
import liad.com.alcoholcalc.converter.UIConverterImpl;
import liad.com.alcoholcalc.gateway.Gateway;
import liad.com.alcoholcalc.gateway.GatewayImpl;
import liad.com.alcoholcalc.ui.drinkitem.UIDrinkItem;

/**
 * Created by liad on 07/05/2020.
 */

public class UIControllerImpl implements UIController  {

    private Gateway gateway = new GatewayImpl();

    private UIConverter uiConverter = new UIConverterImpl();


    @Override
    public void addDrinkToSession(String drinkDetails) {
        JSONObject drinkJsonObject = new JSONObject();
        String[] split = drinkDetails.split("_");
        String drinkType = split[0];
        String drinkAmount = split[2];
        try {
            drinkJsonObject.put("type", drinkType);
            drinkJsonObject.put("amount", drinkAmount);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        gateway.AddDrinkFromUIToServer(drinkJsonObject);
    }

    @Override
    public double getAlcoholScore() {
        Double score = gateway.getAlcoholScore();
        return score;
    }

    @Override
    public Double getFutureAlcoholScore() {
        Double score = gateway.getFutureAlcoholScore();
        return score;
    }

    @Override
    public List<UIDrinkItem> getSessionDrinks() {
        List<UIDrinkItem> ret = Lists.newArrayList();
        List<JSONObject> JSONObjects = gateway.getSessionDrinks();

        for (JSONObject jsonObject : JSONObjects){
            UIDrinkItem uiDrinkItem = uiConverter.convertJSONToUIDrinkItem(jsonObject);
            ret.add(uiDrinkItem);
        }
        return ret;
    }
}
