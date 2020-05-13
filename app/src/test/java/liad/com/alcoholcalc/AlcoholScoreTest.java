package liad.com.alcoholcalc;

import org.joda.time.LocalDateTime;
import org.junit.Test;

import liad.com.alcoholcalc.server.beverage.BeverageType;
import liad.com.alcoholcalc.server.session.SessionDrinkItem;
import liad.com.alcoholcalc.server.session.SessionStatus;
import liad.com.alcoholcalc.server.user.GENDER;
import liad.com.alcoholcalc.server.user.SessionUser;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Created by liad on 28/04/2020.
 */

public class AlcoholScoreTest extends BaseTest {

    private SessionUser defaultManUser = new SessionUser(70D, GENDER.MALE);

    @Test
    public void calculateScore0_Test() {
        drinkingSession.setSessionUser(defaultManUser);
        sessionRunner.startSession();
        drinkingSession.setCurrentDateTime(MOCK_DATE_TIME.plusMinutes(10));
        sessionRunner.calculateSessionStatus();
        SessionStatus sessionStatus = drinkingSession.getSessionStatus();
        LocalDateTime lastUpdateTime = sessionStatus.getLastUpdateTime();
        Double alcoholScore = sessionStatus.getAlcoholScore();
        assertThat(alcoholScore, is(0D));
        assertThat(lastUpdateTime, is(MOCK_DATE_TIME.plusMinutes(10)));
    }

    @Test
    public void calculateScoreStrongBeer10min_Test() {
        drinkingSession.setSessionUser(defaultManUser);
        sessionRunner.startSession();
        SessionDrinkItem sessionDrinkItem1 = new SessionDrinkItem(beverageFactory.getBeverage(BeverageType.STRONG_BEER), 500D, MOCK_DATE_TIME);
        sessionRunner.addDrinkItemToSession(sessionDrinkItem1);
        drinkingSession.setCurrentDateTime(MOCK_DATE_TIME.plusMinutes(10));
        sessionRunner.calculateSessionStatus();
        SessionStatus sessionStatus = drinkingSession.getSessionStatus();
        Double alcoholScore = sessionStatus.getAlcoholScore();

        assertThat(alcoholScore, is(0.0273));
    }

    @Test
    public void calculateScoreStrongBeer30min_Test() {
        drinkingSession.setSessionUser(defaultManUser);
        sessionRunner.startSession();
        SessionDrinkItem sessionDrinkItem1 = new SessionDrinkItem(beverageFactory.getBeverage(BeverageType.STRONG_BEER), 500D, MOCK_DATE_TIME);
        sessionRunner.addDrinkItemToSession(sessionDrinkItem1);
        drinkingSession.setCurrentDateTime(MOCK_DATE_TIME.plusMinutes(30));
        sessionRunner.calculateSessionStatus();
        SessionStatus sessionStatus = drinkingSession.getSessionStatus();
        Double alcoholScore = sessionStatus.getAlcoholScore();

        assertThat(alcoholScore, is(0.0588));
    }

    @Test
    public void calculateScoreStrongBeer300min_Test() {
        drinkingSession.setSessionUser(defaultManUser);
        sessionRunner.startSession();
        SessionDrinkItem sessionDrinkItem1 = new SessionDrinkItem(beverageFactory.getBeverage(BeverageType.STRONG_BEER), 500D, MOCK_DATE_TIME);
        sessionRunner.addDrinkItemToSession(sessionDrinkItem1);
        drinkingSession.setCurrentDateTime(MOCK_DATE_TIME.plusMinutes(300));
        sessionRunner.calculateSessionStatus();
        SessionStatus sessionStatus = drinkingSession.getSessionStatus();
        Double alcoholScore = sessionStatus.getAlcoholScore();

        assertThat(alcoholScore, is(0D));
    }

    @Test
    public void calculateScore2StrongBeers22and44min_Test() {
        drinkingSession.setSessionUser(defaultManUser);
        sessionRunner.startSession();
        SessionDrinkItem sessionDrinkItem1 = new SessionDrinkItem(beverageFactory.getBeverage(BeverageType.STRONG_BEER), 500D, MOCK_DATE_TIME);
        SessionDrinkItem sessionDrinkItem2 = new SessionDrinkItem(beverageFactory.getBeverage(BeverageType.STRONG_BEER), 500D, MOCK_DATE_TIME);
        sessionRunner.addDrinkItemToSession(sessionDrinkItem1);
        drinkingSession.setCurrentDateTime(MOCK_DATE_TIME.plusMillis(1333200));
        sessionRunner.calculateSessionStatus();
        sessionRunner.addDrinkItemToSession(sessionDrinkItem2);
        drinkingSession.setCurrentDateTime(MOCK_DATE_TIME.plusMillis(1333200 * 2));
        sessionRunner.calculateSessionStatus();
        SessionStatus sessionStatus = drinkingSession.getSessionStatus();
        Double alcoholScore = sessionStatus.getAlcoholScore();

        assertThat(alcoholScore, is(0D));
    }

    @Test
    public void calculateScore2StrongBeers300and300min_Test() {
        drinkingSession.setSessionUser(defaultManUser);
        sessionRunner.startSession();
        SessionDrinkItem sessionDrinkItem1 = new SessionDrinkItem(beverageFactory.getBeverage(BeverageType.STRONG_BEER), 500D, MOCK_DATE_TIME);
        SessionDrinkItem sessionDrinkItem2 = new SessionDrinkItem(beverageFactory.getBeverage(BeverageType.STRONG_BEER), 500D, MOCK_DATE_TIME);
        sessionRunner.addDrinkItemToSession(sessionDrinkItem1);
        drinkingSession.setCurrentDateTime(MOCK_DATE_TIME.plusMinutes(300));
        sessionRunner.addDrinkItemToSession(sessionDrinkItem2);
        drinkingSession.setCurrentDateTime(MOCK_DATE_TIME.plusMinutes(600));
        sessionRunner.calculateSessionStatus();
        SessionStatus sessionStatus = drinkingSession.getSessionStatus();
        Double alcoholScore = sessionStatus.getAlcoholScore();

        assertThat(alcoholScore, is(0D));
    }

    @Test
    public void calculateScore2StrongBeers30and300min_Test() {
        drinkingSession.setSessionUser(defaultManUser);
        sessionRunner.startSession();
        SessionDrinkItem sessionDrinkItem1 = new SessionDrinkItem(beverageFactory.getBeverage(BeverageType.STRONG_BEER), 500D, MOCK_DATE_TIME);
        SessionDrinkItem sessionDrinkItem2 = new SessionDrinkItem(beverageFactory.getBeverage(BeverageType.STRONG_BEER), 500D, MOCK_DATE_TIME);
        sessionRunner.addDrinkItemToSession(sessionDrinkItem1);
        drinkingSession.setCurrentDateTime(MOCK_DATE_TIME.plusMinutes(30));
        sessionRunner.addDrinkItemToSession(sessionDrinkItem2);
        drinkingSession.setCurrentDateTime(MOCK_DATE_TIME.plusMinutes(330));
        sessionRunner.calculateSessionStatus();
        SessionStatus sessionStatus = drinkingSession.getSessionStatus();
        Double alcoholScore = sessionStatus.getAlcoholScore();

        assertThat(alcoholScore, is(0D));
    }

    @Test
    public void calculateScore10StrongChasers_5hours_Test() {
        drinkingSession.setSessionUser(defaultManUser);
        sessionRunner.startSession();
        for (int i = 0 ; i < 10 ; i++) {
            SessionDrinkItem sessionDrinkItem = new SessionDrinkItem(beverageFactory.getBeverage(BeverageType.STRONG_CHASER), 30D, MOCK_DATE_TIME);
            sessionRunner.addDrinkItemToSession(sessionDrinkItem);
        }


        drinkingSession.setCurrentDateTime(MOCK_DATE_TIME.plusMinutes(300));
        sessionRunner.calculateSessionStatus();
        SessionStatus sessionStatus = drinkingSession.getSessionStatus();
        Double alcoholScore = sessionStatus.getAlcoholScore();

        assertThat(alcoholScore, is(0.1239));
    }
}
