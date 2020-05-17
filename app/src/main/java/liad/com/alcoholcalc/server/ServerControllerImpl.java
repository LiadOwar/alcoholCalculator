package liad.com.alcoholcalc.server;

import org.joda.time.LocalDateTime;

import java.util.List;

import liad.com.alcoholcalc.server.session.SessionDrinkItem;
import liad.com.alcoholcalc.server.session.SessionRunner;
import liad.com.alcoholcalc.server.session.SessionRunnerImpl;

/**
 * Created by liad on 08/05/2020.
 */

public class ServerControllerImpl implements ServerController {

    private SessionRunner sessionRunner = new SessionRunnerImpl();

    @Override
    public void addDrinkToSession(SessionDrinkItem drinkItem) {
        sessionRunner.addDrinkItemToSession(drinkItem);
    }

    @Override
    public Double getAlcoholScore() {
        sessionRunner.calculateSessionStatus();
        Double score  = sessionRunner.getAlcoholScore();
        return score * 100;
    }

    @Override
    public List<SessionDrinkItem> getSessionDrinks() {
        return sessionRunner.getSessionDrinkItems();
    }

    @Override
    public Double getFutureAlcoholScore() {
        sessionRunner.calculateFutureSessionStatus();
        Double score  = sessionRunner.getFutureAlcoholScore();
        return score * 100;
    }

    @Override
    public void addTimeToCurrentTime(int clickCount) {
        sessionRunner.addTimeToCurrentTime(clickCount);
    }

    @Override
    public void clearSession() {
        sessionRunner.clearSession();
    }

    @Override
    public void removeDrink(LocalDateTime drinkingDateTime) {
        List<SessionDrinkItem> sessionDrinkItems = sessionRunner.getSessionDrinkItems();
        for (int i = 0 ; i < sessionDrinkItems.size() ; ++i) {
            SessionDrinkItem sessionDrinkItem = sessionDrinkItems.get(i);
            if (sessionDrinkItem.getStartDateTime().isEqual(drinkingDateTime)) {
                sessionRunner.removeDrinkItemFromSession(i);
                break;
            }
        }
    }
}
