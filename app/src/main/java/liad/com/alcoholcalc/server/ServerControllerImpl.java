package liad.com.alcoholcalc.server;

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
}
