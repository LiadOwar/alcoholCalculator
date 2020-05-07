package liad.com.alcoholcalc.utils;

import org.joda.time.LocalDateTime;

import liad.com.alcoholcalc.server.beverage.Beverage;
import liad.com.alcoholcalc.server.beverage.BeverageFactory;
import liad.com.alcoholcalc.server.beverage.BeverageType;
import liad.com.alcoholcalc.server.session.DrinkingSession;
import liad.com.alcoholcalc.server.session.SessionDrinkItem;

/**
 * Created by liad on 22/04/2020.
 */

public class TestFactory {

    private BeverageFactory beverageFactory;

    public TestFactory() {
        this.beverageFactory = new BeverageFactory();
    }

    public DrinkingSession createDrinkingSessionStrongBeer500ml() {
        LocalDateTime dateTime = new LocalDateTime(2020, 0, 0, 0, 0, 0);
        Beverage beverage = beverageFactory.getBeverage(BeverageType.STRONG_BEER);
        SessionDrinkItem drinkItem = new SessionDrinkItem(beverage, 500D, dateTime);
        DrinkingSession mockDrinkingSession = createMockDrinkingSession(drinkItem);

        return mockDrinkingSession;
    }

    private DrinkingSession createMockDrinkingSession(SessionDrinkItem sessionDrinkItem) {
        DrinkingSession drinkingSession = DrinkingSession.getSession();
        drinkingSession.addSessionDrinkItem(sessionDrinkItem);

        return drinkingSession;
    }


}
