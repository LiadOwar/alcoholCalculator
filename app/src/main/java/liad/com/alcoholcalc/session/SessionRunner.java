package liad.com.alcoholcalc.session;

import liad.com.alcoholcalc.user.SessionUser;

/**
 * Created by liad on 25/04/2020.
 */

public interface SessionRunner {

    void startSession();

    void addDrinkItemToSession(SessionDrinkItem drinkItem);

    void removeDrinkItemFromSession(int itemIdx);

    void calculateSessionStatus();

    void assignUserToSession(SessionUser sessionUser);
}
