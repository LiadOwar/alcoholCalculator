package liad.com.alcoholcalc.server;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.util.List;

import liad.com.alcoholcalc.BaseTest;
import liad.com.alcoholcalc.gateway.Gateway;
import liad.com.alcoholcalc.gateway.GatewayImpl;
import liad.com.alcoholcalc.server.session.SessionDrinkItem;
import liad.com.alcoholcalc.server.user.ServerControllerImpl;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

/**
 * Created by liad on 08/05/2020.
 */

public class ServerControllerTest extends BaseTest {

    private Gateway gateway = new GatewayImpl();

    private ServerController serverController = new ServerControllerImpl();

    @Test
    public void addDrinkFromGatewayToServer_Test() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("type", "STRONG_BEER");
            jsonObject.put("amount", "500");
            serverController.addDrinkToSession(jsonObject.toString());
            List<SessionDrinkItem> sessionDrinkingItems = drinkingSession.getSessionDrinkingItems();
            SessionDrinkItem drinkItem = sessionDrinkingItems.get(0);
            assertThat(drinkItem, is(notNullValue()));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
