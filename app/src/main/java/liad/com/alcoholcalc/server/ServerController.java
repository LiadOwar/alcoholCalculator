package liad.com.alcoholcalc.server;

import java.util.List;

import liad.com.alcoholcalc.server.session.SessionDrinkItem;

/**
 * Created by liad on 08/05/2020.
 */

public interface ServerController {

    void addDrinkToSession(SessionDrinkItem drinkItem);

    Double getAlcoholScore();

    List<SessionDrinkItem> getSessionDrinks();

    Double getFutureAlcoholScore();
}
