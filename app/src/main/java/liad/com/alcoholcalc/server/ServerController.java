package liad.com.alcoholcalc.server;

import liad.com.alcoholcalc.server.session.SessionDrinkItem;

/**
 * Created by liad on 08/05/2020.
 */

public interface ServerController {

    void addDrinkToSession(SessionDrinkItem drinkItem);

    Double getAlcoholScore();
}
