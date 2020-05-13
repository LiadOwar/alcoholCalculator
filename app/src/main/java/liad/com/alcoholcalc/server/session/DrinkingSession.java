package liad.com.alcoholcalc.server.session;

import com.google.common.collect.Lists;

import org.joda.time.LocalDateTime;

import java.util.List;

import liad.com.alcoholcalc.server.user.GENDER;
import liad.com.alcoholcalc.server.user.SessionUser;

/**
 * Created by liad on 22/04/2020.
 */

public class DrinkingSession {

    private List<SessionDrinkItem> drinkItems;
    private boolean isActive;
    private SessionStatus sessionStatus;
    private volatile LocalDateTime currentDateTime;
    private static Object mutex = new Object();

    public Integer getFastForwardClickCounter() {
        return fastForwardClickCounter;
    }

    public void setFastForwardClickCounter(Integer fastForwardClickCounter) {
        this.fastForwardClickCounter = fastForwardClickCounter;
    }

    private Integer fastForwardClickCounter;

    public LocalDateTime getStartAssumedEtOHProcessTime() {
        return assumedStartEtOHProcessTime;
    }

    private LocalDateTime assumedStartEtOHProcessTime;
    private SessionUser sessionUser;
    private static volatile DrinkingSession drinkingSession;

    private DrinkingSession() {
        drinkItems = Lists.newArrayList();
        this.sessionStatus = new SessionStatus();
        currentDateTime = LocalDateTime.now();
        fastForwardClickCounter = 0;
    }

    public static DrinkingSession getSession() {
        DrinkingSession result = drinkingSession;
        if (result == null) {
            synchronized (mutex) {
                result = drinkingSession;
                if(result == null) {
                    result = drinkingSession = new DrinkingSession();
                }
            }
        }
        return result;
    }

    public void addSessionDrinkItem(SessionDrinkItem drinkItem) {
        if (this.drinkItems.isEmpty()) {
            assumedStartEtOHProcessTime = drinkItem.getStartDateTime();
        }
        drinkItems.add(drinkItem);
    }

    public List<SessionDrinkItem> getSessionDrinkingItems() {
        return this.drinkItems;
    }

    public SessionStatus getSessionStatus() {
        return this.sessionStatus;
    }

    public boolean isActive() {
        return isActive;
    }

    public LocalDateTime getCurrentDateTime() {
        synchronized (mutex) {
            return currentDateTime;
        }
    }

    public void setCurrentDateTime(LocalDateTime currentDateTime) {
        synchronized (mutex) {
            this.currentDateTime = currentDateTime;
        }
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public static void destroy() {
        drinkingSession = null;
    }


    public SessionUser getSessionUser() {
        if (sessionUser == null) {
            sessionUser = new SessionUser(70D, GENDER.MALE);
        }
        return sessionUser;
    }

    public void setSessionUser(SessionUser sessionUser) {
        this.sessionUser = sessionUser;
    }
}
