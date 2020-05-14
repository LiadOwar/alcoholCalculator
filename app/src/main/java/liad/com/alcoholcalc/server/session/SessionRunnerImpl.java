package liad.com.alcoholcalc.server.session;

import android.support.annotation.NonNull;

import org.joda.time.LocalDateTime;
import org.joda.time.Period;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

import liad.com.alcoholcalc.server.beverage.Beverage;
import liad.com.alcoholcalc.server.consts.Consts;
import liad.com.alcoholcalc.server.user.GENDER;
import liad.com.alcoholcalc.server.user.SessionUser;

/**
 * Created by liad on 25/04/2020.
 */

public class SessionRunnerImpl implements SessionRunner {

    private DrinkingSession drinkingSession = DrinkingSession.getSession();

    @Override
    public void startSession() {
        drinkingSession.setActive(true);

    }

    @Override
    public void addDrinkItemToSession(SessionDrinkItem drinkItem) {
        drinkingSession.addSessionDrinkItem(drinkItem);
    }

    @Override
    public void removeDrinkItemFromSession(int itemIdx) {
        List<SessionDrinkItem> sessionDrinkingItems = drinkingSession.getSessionDrinkingItems();
        sessionDrinkingItems.remove(itemIdx);
    }

    @Override
    public void calculateSessionStatus() {

        List<SessionDrinkItem> sessionDrinkingItems = this.drinkingSession.getSessionDrinkingItems();
        LocalDateTime tempEtOHProcessStartTime = this.drinkingSession.getStartAssumedEtOHProcessTime();
        Double userWeightGr = drinkingSession.getSessionUser().getWeightKg()*1000;
        Double EtOhProcessCoefficient = getEtOHProcessCoefficient();
        Double BAC = 0D;
        Double accumulatedHypotheticalBAC = 0D;
        Double potentialEtOHProcess = 0D;

        for (int i = 0 ; i < sessionDrinkingItems.size() ; ++i) {

            SessionDrinkItem currentDrinkItem = sessionDrinkingItems.get(i);
            SessionDrinkItem nextDrinkItem = getNextDrinkItem(i, sessionDrinkingItems);

            LocalDateTime drinkItemStartDateTime = currentDrinkItem.getStartDateTime();
            LocalDateTime nextTimePoint = drinkingSession.getCurrentDateTime();
            if (nextDrinkItem != null) {
                nextTimePoint = nextDrinkItem.getStartDateTime();
            }
            Period deltaTime =  Period.fieldDifference(drinkItemStartDateTime, nextTimePoint);
            int deltaTimeMinutes = deltaTime.toStandardMinutes().getMinutes();
            Double hypotheticalGrossBAC = getHypotheticalGrossBAC(userWeightGr,
                                    EtOhProcessCoefficient, currentDrinkItem);
            Double potentialEtOHConsumed = deltaTimeMinutes * Consts.ETOH_PROCESS_RATE;
            accumulatedHypotheticalBAC += (hypotheticalGrossBAC - potentialEtOHConsumed) ;

            if (accumulatedHypotheticalBAC < 0D) {
                accumulatedHypotheticalBAC = 0D;
            }

//
//
//
//            if (BAC == 0D) {
//                tempEtOHProcessStartTime = currentDrinkItem.getStartDateTime();
//            }
//
//
//            int deltaTimeSeconds = getDeltaTimeSeconds(drinkItemStartDateTime, nextTimePoint);
//            Beverage beverage = currentDrinkItem.getBeverage();
//            Double assumedConsumptionRate = beverage.getAssumedConsumptionRate();
//            Double initialDrinkItemAmount = currentDrinkItem.getAmount();
//            Double hypotheticalConsumedAmount = getConsumedAmount(deltaTimeSeconds, assumedConsumptionRate, initialDrinkItemAmount);
//
//
//            Period deltaDateTimeEtOHProcess = Period.fieldDifference(tempEtOHProcessStartTime, nextTimePoint);
//            accumulatedGrossBAC += rawGrossBAC;
//            Double potentialEtOHProcessAmount = getPotentialEtOHConsumedAmount(rawGrossBAC, deltaDateTimeEtOHProcess);
//
//            int deltaDateTimeEtOHProcessMinutes = deltaDateTimeEtOHProcess.toStandardMinutes().getMinutes();
//            Double rawBAC = rawGrossBAC;
//            if (BAC == 0) {
//                rawBAC = rawGrossBAC - (deltaDateTimeEtOHProcessMinutes * Consts.ETOH_PROCESS_RATE);
//            }
//
//            if (rawBAC <= 0D) {
//                rawBAC = 0D;
//                currentDrinkItem.setEtOHConsumed(true);
//            }
//
//
//            Double formattedBAC = formatBAC(rawBAC);
//            BAC += formattedBAC;
        }
        Double formattedBAC = formatBAC(accumulatedHypotheticalBAC);
        SessionStatus sessionStatus = drinkingSession.getSessionStatus();
//        sessionStatus.setLastUpdateTime(drinkingSession.getCurrentDateTime());
        sessionStatus.setAlcoholScore(formattedBAC);
    }

    private SessionDrinkItem getNextDrinkItem(int i, List<SessionDrinkItem> sessionDrinkingItems) {
        if (i >= sessionDrinkingItems.size() - 1) {
            return null;
        }
        SessionDrinkItem nextDrinkItem = sessionDrinkingItems.get(i + 1);
        return nextDrinkItem;
    }

    private Double getPotentialEtOHConsumedAmount(int deltaTimeMinutes) {

        Double potentialEtOHConsumed = deltaTimeMinutes * Consts.ETOH_PROCESS_RATE;
        return potentialEtOHConsumed;
    }

    private int getDeltaTimeSeconds(LocalDateTime drinkItemStartDateTime, LocalDateTime currentDateTime) {
        Period deltaDateTime = Period.fieldDifference(drinkItemStartDateTime, currentDateTime);
        return deltaDateTime.toStandardSeconds().getSeconds();
    }

    @NonNull
    private Double getConsumedAmount(int deltaTimeSeconds, Double assumedConsumptionRate, Double initialDrinkItemAmount) {
        Double hypotheticalConsumedAmount = assumedConsumptionRate * deltaTimeSeconds / 60d;
        if (hypotheticalConsumedAmount > initialDrinkItemAmount) {
            hypotheticalConsumedAmount = initialDrinkItemAmount;
        }
        return hypotheticalConsumedAmount;
    }

    @NonNull
    private Double formatBAC(Double rawBAC) {
        NumberFormat formatter = new DecimalFormat("#0.0000");
        String formattedBACString = formatter.format(rawBAC);
        return Double.parseDouble(formattedBACString);
    }

    @Override
    public void calculateFutureSessionStatus() {
        List<SessionDrinkItem> sessionDrinkingItems = this.drinkingSession.getSessionDrinkingItems();
        LocalDateTime tempEtOHProcessStartTime = this.drinkingSession.getStartAssumedEtOHProcessTime();
        Double userWeightGr = drinkingSession.getSessionUser().getWeightKg()*1000;
        Double EtOhProcessCoefficient = getEtOHProcessCoefficient();
        Double BAC = 0D;
        Double accumulatedHypotheticalBAC = 0D;
        Double potentialEtOHProcess = 0D;

        for (int i = 0 ; i < sessionDrinkingItems.size() ; ++i) {

            SessionDrinkItem currentDrinkItem = sessionDrinkingItems.get(i);
            SessionDrinkItem nextDrinkItem = getNextDrinkItem(i, sessionDrinkingItems);

            LocalDateTime drinkItemStartDateTime = currentDrinkItem.getStartDateTime();
            LocalDateTime nextTimePoint = drinkingSession.getCurrentDateTime().plusHours(1);
            if (nextDrinkItem != null) {
                nextTimePoint = nextDrinkItem.getStartDateTime();
            }
            Period deltaTime =  Period.fieldDifference(drinkItemStartDateTime, nextTimePoint);
            int deltaTimeMinutes = deltaTime.toStandardMinutes().getMinutes();
            Double hypotheticalGrossBAC = getHypotheticalGrossBAC(userWeightGr,
                    EtOhProcessCoefficient, currentDrinkItem);
            Double potentialEtOHConsumed = deltaTimeMinutes * Consts.ETOH_PROCESS_RATE;
            accumulatedHypotheticalBAC += (hypotheticalGrossBAC - potentialEtOHConsumed) ;

            if (accumulatedHypotheticalBAC < 0D) {
                accumulatedHypotheticalBAC = 0D;
            }

//
//
//
//            if (BAC == 0D) {
//                tempEtOHProcessStartTime = currentDrinkItem.getStartDateTime();
//            }
//
//
//            int deltaTimeSeconds = getDeltaTimeSeconds(drinkItemStartDateTime, nextTimePoint);
//            Beverage beverage = currentDrinkItem.getBeverage();
//            Double assumedConsumptionRate = beverage.getAssumedConsumptionRate();
//            Double initialDrinkItemAmount = currentDrinkItem.getAmount();
//            Double hypotheticalConsumedAmount = getConsumedAmount(deltaTimeSeconds, assumedConsumptionRate, initialDrinkItemAmount);
//
//
//            Period deltaDateTimeEtOHProcess = Period.fieldDifference(tempEtOHProcessStartTime, nextTimePoint);
//            accumulatedGrossBAC += rawGrossBAC;
//            Double potentialEtOHProcessAmount = getPotentialEtOHConsumedAmount(rawGrossBAC, deltaDateTimeEtOHProcess);
//
//            int deltaDateTimeEtOHProcessMinutes = deltaDateTimeEtOHProcess.toStandardMinutes().getMinutes();
//            Double rawBAC = rawGrossBAC;
//            if (BAC == 0) {
//                rawBAC = rawGrossBAC - (deltaDateTimeEtOHProcessMinutes * Consts.ETOH_PROCESS_RATE);
//            }
//
//            if (rawBAC <= 0D) {
//                rawBAC = 0D;
//                currentDrinkItem.setEtOHConsumed(true);
//            }
//
//
//            Double formattedBAC = formatBAC(rawBAC);
//            BAC += formattedBAC;
        }
        Double formattedBAC = formatBAC(accumulatedHypotheticalBAC);
        SessionStatus sessionStatus = drinkingSession.getSessionStatus();
        sessionStatus.setLastUpdateTime(drinkingSession.getCurrentDateTime());
        sessionStatus.setFutureAlcoholScore(formattedBAC);
    }

    private Double getHypotheticalGrossBAC(Double userWeightGr, Double etOhProcessCoefficient, SessionDrinkItem drinkItem) {
        Beverage beverage = drinkItem.getBeverage();
        Double amount = drinkItem.getAmount();
        Double EtOhConcentration = beverage.getAlcoholConcentration() / 100;

        return (amount * EtOhConcentration * Consts.ETOH_DENSITY * 100) / (userWeightGr * etOhProcessCoefficient);
    }

    private Double getEtOHProcessCoefficient() {
        SessionUser sessionUser = drinkingSession.getSessionUser();
        GENDER gender = sessionUser.getGender();
        switch (gender) {
            case MALE: return 0.68;
            case FEMALE: return 0.55;
            default:return 0.68;
        }
    }

    @Override
    public void assignUserToSession(SessionUser sessionUser) {
        drinkingSession.setSessionUser(sessionUser);
    }

    @Override
    public Double getAlcoholScore() {
        LocalDateTime now = LocalDateTime.now();
        drinkingSession.setCurrentDateTime(now.plusMinutes(drinkingSession.getFastForwardClickCounter() * 5));
        SessionStatus sessionStatus = drinkingSession.getSessionStatus();
        Double alcoholScore = sessionStatus.getAlcoholScore();
        return alcoholScore;
    }

    @Override
    public Double getFutureAlcoholScore() {
        SessionStatus sessionStatus = drinkingSession.getSessionStatus();
        Double alcoholScore = sessionStatus.getFutureAlcoholScore();
        return alcoholScore;
    }

    @Override
    public List<SessionDrinkItem> getSessionDrinkItems() {
        return drinkingSession.getSessionDrinkingItems();
    }

    @Override
    public void addTimeToCurrentTime(int clickCount) {
        drinkingSession.setFastForwardClickCounter(clickCount);
    }

    @Override
    public void clearSession() {
        drinkingSession.getSessionDrinkingItems().clear();

    }
}
