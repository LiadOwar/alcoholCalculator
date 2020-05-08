package liad.com.alcoholcalc;

import org.junit.Test;

import liad.com.alcoholcalc.gateway.Gateway;
import liad.com.alcoholcalc.gateway.GatewayImpl;
import liad.com.alcoholcalc.server.session.SessionDrinkItem;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by liad on 07/05/2020.
 */

public class UIControllerTest extends BaseTest{


    private Gateway gateway = new GatewayImpl();


    @Test
    public void AddDrinkFromUIToServer_Test() {

        gateway.AddDrinkFromUIToServer("strongbeer_img");
        SessionDrinkItem sessionDrinkItem = drinkingSession.getSessionDrinkingItems().get(0);
        assertThat(sessionDrinkItem, is(notNullValue()));


    }
}
