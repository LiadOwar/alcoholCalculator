package liad.com.alcoholcalc.gateway;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.util.List;

import liad.com.alcoholcalc.BaseTest;
import liad.com.alcoholcalc.server.session.SessionDrinkItem;

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
}
