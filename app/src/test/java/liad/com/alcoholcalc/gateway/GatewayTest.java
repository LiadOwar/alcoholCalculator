package liad.com.alcoholcalc.gateway;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.util.List;

import liad.com.alcoholcalc.BaseTest;
import liad.com.alcoholcalc.server.session.SessionDrinkItem;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by liad on 07/05/2020.
 */

public class GatewayTest extends BaseTest {
    private Gateway gateway = new GatewayImpl();

    @Test
    public void addDrinkFromUIToSession_Test() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("type", "STRONG_BEER");
            jsonObject.put("amount", "500");
            gateway.AddDrinkFromUIToServer(jsonObject);
            List<SessionDrinkItem> sessionDrinkingItems = drinkingSession.getSessionDrinkingItems();
            SessionDrinkItem drinkItem = sessionDrinkingItems.get(0);

            assertThat(drinkItem, is(notNullValue()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void calculateFutureAlcoholScore_Test() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("type", "STRONGBEER");
            jsonObject.put("amount", "500");
            gateway.AddDrinkFromUIToServer(jsonObject);
            List<SessionDrinkItem> sessionDrinkingItems = drinkingSession.getSessionDrinkingItems();
            Double score = gateway.getFutureAlcoholScore();
            assertThat(score, is(5.16));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void clearSessionGateway_Test() {
        JSONObject jsonObject1 = new JSONObject();
        JSONObject jsonObject2 = new JSONObject();
        try {
            jsonObject1.put("type", "STRONGBEER");
            jsonObject1.put("amount", "500");
            jsonObject2.put("type", "STRONGBEER");
            jsonObject2.put("amount", "500");
            gateway.AddDrinkFromUIToServer(jsonObject1);
            gateway.AddDrinkFromUIToServer(jsonObject2);
            List<SessionDrinkItem> sessionDrinkingItems = drinkingSession.getSessionDrinkingItems();
            Double score = gateway.getAlcoholScore();
            assertThat(score, not(0D));
            gateway.cleatSession();
            score = gateway.getAlcoholScore();
            assertThat(sessionDrinkingItems.size(), is(0));
            assertThat(score, is(0D));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
