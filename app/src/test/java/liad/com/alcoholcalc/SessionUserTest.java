package liad.com.alcoholcalc;

import org.junit.Test;

import liad.com.alcoholcalc.server.user.GENDER;
import liad.com.alcoholcalc.server.user.SessionUser;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by liad on 27/04/2020.
 */

public class SessionUserTest extends BaseTest{

    @Test
    public void createNewSessionUser_Test(){
        SessionUser sessionUser = new SessionUser(70D, GENDER.MALE);
        assertThat(sessionUser, is(notNullValue()));
    }

    @Test
    public void assignUserToSession_Test() {
        SessionUser sessionUser = new SessionUser(75D, GENDER.MALE);
        sessionRunner.assignUserToSession(sessionUser);

        assertThat(drinkingSession.getSessionUser(), is(notNullValue()));
        assertThat(drinkingSession.getSessionUser().getWeightKg(), is(75D));
    }

    @Test
    public void getDefaultUserToSessionWhenUserNotAssigned_Test() {

        assertThat(drinkingSession.getSessionUser(), is(notNullValue()));
    }
}
