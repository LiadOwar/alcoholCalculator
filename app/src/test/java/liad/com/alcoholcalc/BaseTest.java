package liad.com.alcoholcalc;

import org.joda.time.LocalDateTime;
import org.junit.After;

import liad.com.alcoholcalc.server.beverage.Beverage;
import liad.com.alcoholcalc.server.beverage.BeverageFactory;
import liad.com.alcoholcalc.server.beverage.BeverageType;
import liad.com.alcoholcalc.server.session.DrinkingSession;
import liad.com.alcoholcalc.server.session.SessionDrinkItem;
import liad.com.alcoholcalc.server.session.SessionRunner;
import liad.com.alcoholcalc.server.session.SessionRunnerImpl;
import liad.com.alcoholcalc.utils.TestFactory;

/**
 * Created by liad on 25/04/2020.
 */

public class BaseTest {

    protected BeverageFactory beverageFactory;

    protected DrinkingSession drinkingSession;

    protected SessionRunner sessionRunner;

    protected TestFactory testFactory;

    protected LocalDateTime MOCK_DATE_TIME = new LocalDateTime(2020, 1, 1 ,0 ,0, 0, 0);

    @After
    public void destroy() {
        DrinkingSession.destroy();
    }

    public BaseTest() {
        this.beverageFactory = new BeverageFactory();
        this.drinkingSession = DrinkingSession.getSession();
        this.sessionRunner = new SessionRunnerImpl();
        this.testFactory = new TestFactory();
    }

    protected DrinkingSession createDefaultDrinkingSession(){

        SessionDrinkItem sessionDrinkItem = createSessionDrinkItem(BeverageType.STRONG_BEER, 500D, MOCK_DATE_TIME);
        drinkingSession.addSessionDrinkItem(sessionDrinkItem);

        return drinkingSession;
    }

    protected SessionDrinkItem createSessionDrinkItem(BeverageType beverageType, Double amount, LocalDateTime dateTime) {
        Beverage beverage = beverageFactory.getBeverage(beverageType);
        SessionDrinkItem sessionDrinkItem = new SessionDrinkItem(beverage, amount, dateTime);

        return sessionDrinkItem;
    }

}
