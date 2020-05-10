package liad.com.alcoholcalc;

import org.junit.Test;

import liad.com.alcoholcalc.gateway.Gateway;
import liad.com.alcoholcalc.gateway.GatewayImpl;
import liad.com.alcoholcalc.server.session.SessionDrinkItem;
import liad.com.alcoholcalc.ui.controller.UIController;
import liad.com.alcoholcalc.ui.controller.UIControllerImpl;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by liad on 07/05/2020.
 */

public class UIControllerTest extends BaseTest{


    private Gateway gateway = new GatewayImpl();

    private UIController uiController = new UIControllerImpl();


    @Test
    public void AddDrinkFromUIToServer_Test() {


        uiController.addDrinkToSession("strongbeer_img_500");
        SessionDrinkItem sessionDrinkItem = drinkingSession.getSessionDrinkingItems().get(0);
        assertThat(sessionDrinkItem, is(notNullValue()));
        assertThat(sessionDrinkItem.getBeverage(), is(notNullValue()));
    }

    @Test
    public void getAlcoholScore1StrongBeer_10min_test() {

        drinkingSession.setCurrentDateTime(MOCK_DATE_TIME);
        uiController.addDrinkToSession("strongbeer_img_500");
        SessionDrinkItem sessionDrinkItem = drinkingSession.getSessionDrinkingItems().get(0);
        sessionDrinkItem.setStartDateTime(MOCK_DATE_TIME);
        drinkingSession.setCurrentDateTime(MOCK_DATE_TIME.plusMinutes(10));

        double score = uiController.getAlcoholScore();

        assertThat(score, not(0d));
    }

    @Test
    public void getAlcoholScore1StrongBeer_1min_test() {

        drinkingSession.setCurrentDateTime(MOCK_DATE_TIME);
        uiController.addDrinkToSession("strongchaser_img_30");
        SessionDrinkItem sessionDrinkItem = drinkingSession.getSessionDrinkingItems().get(0);
        sessionDrinkItem.setStartDateTime(MOCK_DATE_TIME);
        drinkingSession.setCurrentDateTime(MOCK_DATE_TIME.plusMinutes(1));

        double score = uiController.getAlcoholScore();

        assertThat(score, is(1.96));
    }
}
