package liad.com.alcoholcalc;

import org.joda.time.LocalDateTime;
import org.junit.Test;

import java.util.List;

import liad.com.alcoholcalc.beverage.Beverage;
import liad.com.alcoholcalc.beverage.BeverageType;
import liad.com.alcoholcalc.session.DrinkingSession;
import liad.com.alcoholcalc.session.SessionDrinkItem;
import liad.com.alcoholcalc.session.SessionStatus;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;

/**
 * Created by liad on 22/04/2020.
 */

public class DrinkingSessionTest extends BaseTest {


    @Test
    public void createDrinkingSession() {

        assertThat(drinkingSession, is(notNullValue()));
    }

    @Test
    public void startDrinkingSession(){
        sessionRunner.startSession();
        DrinkingSession session = DrinkingSession.getSession();
        assertThat(session.isActive(), is(true));
    }

    @Test
    public void addStrongBeer500mlToSession() {
        sessionRunner.startSession();
        SessionDrinkItem sessionDrinkItem = new SessionDrinkItem(beverageFactory.getBeverage(BeverageType.STRONG_BEER), 500D, MOCK_DATE_TIME);
        sessionRunner.addDrinkItemToSession(sessionDrinkItem);

        assertThat(drinkingSession.getSessionDrinkingItems().get(0).getAmount(), is(500D));
    }

    @Test
    public void add2StrongBeer500ml_and330mkToSession() {
        sessionRunner.startSession();
        SessionDrinkItem sessionDrinkItem1 = new SessionDrinkItem(beverageFactory.getBeverage(BeverageType.STRONG_BEER), 500D, MOCK_DATE_TIME);
        SessionDrinkItem sessionDrinkItem2 = new SessionDrinkItem(beverageFactory.getBeverage(BeverageType.STRONG_BEER), 330D, MOCK_DATE_TIME.plusMinutes(30));
        sessionRunner.addDrinkItemToSession(sessionDrinkItem1);
        sessionRunner.addDrinkItemToSession(sessionDrinkItem2);

        assertThat(drinkingSession.getSessionDrinkingItems().get(0).getAmount(), is(500D));
        assertThat(drinkingSession.getSessionDrinkingItems().get(1).getAmount(), is(330D));
    }

    @Test
    public void removeDrinkItem() {
        sessionRunner.startSession();
        SessionDrinkItem sessionDrinkItem1 = new SessionDrinkItem(beverageFactory.getBeverage(BeverageType.STRONG_BEER), 500D, MOCK_DATE_TIME);
        SessionDrinkItem sessionDrinkItem2 = new SessionDrinkItem(beverageFactory.getBeverage(BeverageType.STRONG_BEER), 330D, MOCK_DATE_TIME.plusMinutes(30));
        sessionRunner.addDrinkItemToSession(sessionDrinkItem1);
        sessionRunner.addDrinkItemToSession(sessionDrinkItem2);
        sessionRunner.removeDrinkItemFromSession(0);

        assertThat(drinkingSession.getSessionDrinkingItems().get(0).getAmount(), is(330D));
        assertThat(drinkingSession.getSessionDrinkingItems().size(), is(1));
    }

    @Test
    public void getStatus_Test() {
        sessionRunner.startSession();
        assertThat(drinkingSession.getSessionStatus().getAlcoholScore(), is(0D));
    }

    @Test
    public void calculateStatusBasic_Test() {
        sessionRunner.startSession();
        SessionDrinkItem sessionDrinkItem1 = new SessionDrinkItem(beverageFactory.getBeverage(BeverageType.STRONG_BEER), 500D, MOCK_DATE_TIME);
        sessionRunner.addDrinkItemToSession(sessionDrinkItem1);
        drinkingSession.setCurrentDateTime(MOCK_DATE_TIME.plusMinutes(10));
        sessionRunner.calculateSessionStatus();
        SessionStatus sessionStatus = drinkingSession.getSessionStatus();

        assertThat(sessionStatus.getAlcoholScore(), not(0D));
    }

    @Test
    public void calculateConsumedAmountOfBeerAfter10Min_Test() {
        sessionRunner.startSession();
        SessionDrinkItem sessionDrinkItem1 = new SessionDrinkItem(beverageFactory.getBeverage(BeverageType.STRONG_BEER), 500D, MOCK_DATE_TIME);
        sessionRunner.addDrinkItemToSession(sessionDrinkItem1);
        SessionDrinkItem sessionDrinkItem = drinkingSession.getSessionDrinkingItems().get(0);
        drinkingSession.setCurrentDateTime(MOCK_DATE_TIME.plusMinutes(10));
        sessionRunner.calculateSessionStatus();

        assertThat(sessionDrinkItem.getConsumedAmount(), is(225D));
    }

    @Test
    public void stopConsumingIfDrinkIsFullyConsumed_Test() {
        sessionRunner.startSession();
        SessionDrinkItem sessionDrinkItem1 = new SessionDrinkItem(beverageFactory.getBeverage(BeverageType.STRONG_BEER), 500D, MOCK_DATE_TIME);
        sessionRunner.addDrinkItemToSession(sessionDrinkItem1);
        SessionDrinkItem sessionDrinkItem = drinkingSession.getSessionDrinkingItems().get(0);
        drinkingSession.setCurrentDateTime(MOCK_DATE_TIME.plusMinutes(60));
        sessionRunner.calculateSessionStatus();

        assertThat(sessionDrinkItem.getConsumedAmount(), is(500D));
    }

    @Test
    public void calculateConsumedAmountOfBeerAfter0Min_Test() {
        sessionRunner.startSession();
        SessionDrinkItem sessionDrinkItem1 = new SessionDrinkItem(beverageFactory.getBeverage(BeverageType.STRONG_BEER), 500D, MOCK_DATE_TIME);
        sessionRunner.addDrinkItemToSession(sessionDrinkItem1);
        SessionDrinkItem sessionDrinkItem = drinkingSession.getSessionDrinkingItems().get(0);

        assertThat(sessionDrinkItem.getConsumedAmount(), is(0D));
    }



    @Test
    public void createDrinkingSessionStatus_Test() {


        assertThat(drinkingSession.getSessionStatus(), is(notNullValue()));
    }

    @Test
    public void drinkingSessionStatus_Test() {

        SessionStatus sessionStatus = drinkingSession.getSessionStatus();
        sessionStatus.setLastUpdateTime(MOCK_DATE_TIME);

        assertThat(sessionStatus.getLastUpdateTime(), is(MOCK_DATE_TIME));
    }

    @Test
    public void addBeerToDrinkingSession_Test() {
        DrinkingSession drinkingSession = createDefaultDrinkingSession();
        Beverage strongBeerBeverage = beverageFactory.getBeverage(BeverageType.STRONG_BEER);
        List sessionDrinkItems = drinkingSession.getSessionDrinkingItems();
        assertThat(sessionDrinkItems.size(), is(1));
        SessionDrinkItem item = (SessionDrinkItem)sessionDrinkItems.get(0);
        assertThat(item.getBeverage(), is(strongBeerBeverage));
    }

    @Test
    public void add2BeersToDrinkingSession_Test() {
        DrinkingSession drinkingSession = createDefaultDrinkingSession();
        SessionDrinkItem sessionDrinkItem2 = createSessionDrinkItem(BeverageType.STRONG_BEER, 330D, MOCK_DATE_TIME.plusMinutes(30));
        drinkingSession.addSessionDrinkItem(sessionDrinkItem2);

        List sessionDrinkItems = drinkingSession.getSessionDrinkingItems();
        assertThat(sessionDrinkItems.size(), is(2));
        SessionDrinkItem item1 = (SessionDrinkItem)sessionDrinkItems.get(0);
        SessionDrinkItem item2 = (SessionDrinkItem)sessionDrinkItems.get(1);
        assertThat(item1.getBeverage(), is(beverageFactory.getBeverage(BeverageType.STRONG_BEER)));
        assertThat(item2.getBeverage(), is(beverageFactory.getBeverage(BeverageType.STRONG_BEER)));
    }

    private DrinkingSession createDefaultDrinkingSession(){

        SessionDrinkItem sessionDrinkItem = createSessionDrinkItem(BeverageType.STRONG_BEER, 500D, MOCK_DATE_TIME);
        drinkingSession.addSessionDrinkItem(sessionDrinkItem);

        return drinkingSession;
    }

    private SessionDrinkItem createSessionDrinkItem(BeverageType beverageType, Double amount, LocalDateTime dateTime) {
        Beverage beverage = beverageFactory.getBeverage(beverageType);
        SessionDrinkItem sessionDrinkItem = new SessionDrinkItem(beverage, amount, dateTime);

        return sessionDrinkItem;
    }


}
