package liad.com.alcoholcalc.server;

import org.junit.Test;

import java.util.List;

import liad.com.alcoholcalc.BaseTest;
import liad.com.alcoholcalc.server.beverage.Beverage;
import liad.com.alcoholcalc.server.beverage.BeverageType;
import liad.com.alcoholcalc.server.session.SessionDrinkItem;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

/**
 * Created by liad on 08/05/2020.
 */

public class ServerControllerTest extends BaseTest {

    private ServerController serverController = new ServerControllerImpl();

    @Test
    public void addDrinkFromGatewayToServer_Test() {

        Beverage beverage = beverageFactory.getBeverage(BeverageType.STRONG_BEER);
        SessionDrinkItem sessionDrinkItem = new SessionDrinkItem(beverage, 500D ,MOCK_DATE_TIME);

        serverController.addDrinkToSession(sessionDrinkItem);
        List<SessionDrinkItem> sessionDrinkingItems = drinkingSession.getSessionDrinkingItems();
        SessionDrinkItem drinkItem = sessionDrinkingItems.get(0);

        assertThat(drinkItem, is(notNullValue()));
    }

    @Test
    public void calculateFutureScore_Test(){
        Beverage beverage = beverageFactory.getBeverage(BeverageType.STRONG_BEER);
        SessionDrinkItem sessionDrinkItem = new SessionDrinkItem(beverage, 500D ,MOCK_DATE_TIME);

        serverController.addDrinkToSession(sessionDrinkItem);
        drinkingSession.setCurrentDateTime(MOCK_DATE_TIME);
        Double futureAlcoholScore = serverController.getFutureAlcoholScore();

        assertThat(futureAlcoholScore, is(5.13));
    }
}
