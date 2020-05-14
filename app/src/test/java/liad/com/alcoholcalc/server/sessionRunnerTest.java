package liad.com.alcoholcalc.server;

import org.junit.Test;

import java.util.List;

import liad.com.alcoholcalc.BaseTest;
import liad.com.alcoholcalc.server.beverage.BeverageType;
import liad.com.alcoholcalc.server.session.DrinkingSession;
import liad.com.alcoholcalc.server.session.SessionDrinkItem;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class sessionRunnerTest extends BaseTest {

    @Test
    public void clearDrinkingSession_Test() {
        DrinkingSession drinkingSession = createDefaultDrinkingSession();
        SessionDrinkItem sessionDrinkItem2 = createSessionDrinkItem(BeverageType.STRONG_BEER, 330D, MOCK_DATE_TIME.plusMinutes(30));
        drinkingSession.addSessionDrinkItem(sessionDrinkItem2);

        List sessionDrinkItems = drinkingSession.getSessionDrinkingItems();
        assertThat(sessionDrinkItems.size(), is(2));
        SessionDrinkItem item1 = (SessionDrinkItem)sessionDrinkItems.get(0);
        SessionDrinkItem item2 = (SessionDrinkItem)sessionDrinkItems.get(1);
        assertThat(item1.getBeverage(), is(beverageFactory.getBeverage(BeverageType.STRONG_BEER)));
        assertThat(item2.getBeverage(), is(beverageFactory.getBeverage(BeverageType.STRONG_BEER)));
        sessionRunner.clearSession();

        assertThat(drinkingSession.getSessionStatus().getAlcoholScore(), is(0D));
        assertThat(drinkingSession.getSessionDrinkingItems().size(), is(0));
    }


}
