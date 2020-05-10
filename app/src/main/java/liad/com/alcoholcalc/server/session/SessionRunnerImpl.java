package liad.com.alcoholcalc.server.session;

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

        for (SessionDrinkItem drinkItem : sessionDrinkingItems) {
            if (BAC == 0D) {
                tempEtOHProcessStartTime = drinkItem.getStartDateTime();
            }
            LocalDateTime drinkItemStartDateTime = drinkItem.getStartDateTime();
            LocalDateTime currentDateTime = drinkingSession.getCurrentDateTime();
            Period deltaDateTime = Period.fieldDifference(drinkItemStartDateTime, currentDateTime);
            int deltaTimeSeconds = deltaDateTime.toStandardSeconds().getSeconds();
            Beverage beverage = drinkItem.getBeverage();
            Double assumedConsumptionRate = beverage.getAssumedConsumptionRate();
            Double initialDrinkItemAmount = drinkItem.getAmount();
            Double hypotheticalConsumedAmount = assumedConsumptionRate * deltaTimeSeconds / 60d;
            if (hypotheticalConsumedAmount > initialDrinkItemAmount){
                hypotheticalConsumedAmount = initialDrinkItemAmount;
            }
            Double EtOhConcentration = beverage.getAlcoholConcentration() / 100;
                    drinkItem.setConsumedAmount(hypotheticalConsumedAmount);
            Double rawGrossBAC = (hypotheticalConsumedAmount * EtOhConcentration * Consts.ETOH_DENSITY * 100) / (userWeightGr * EtOhProcessCoefficient);

            Period deltaDateTimeEtOHProcess = Period.fieldDifference(tempEtOHProcessStartTime, currentDateTime);
            int deltaDateTimeEtOHProcessMinutes = deltaDateTimeEtOHProcess.toStandardMinutes().getMinutes();
            Double rawBAC = rawGrossBAC;
            if (BAC == 0) {
                rawBAC = rawGrossBAC - (deltaDateTimeEtOHProcessMinutes * Consts.ETOH_PROCESS_RATE);
            }

            if (rawBAC <= 0D) {
                rawBAC = 0D;
            }

            NumberFormat formatter = new DecimalFormat("#0.0000");
            String formattedBACString = formatter.format(rawBAC);
            Double formattedBAC = Double.parseDouble(formattedBACString);
            BAC += formattedBAC;
        }
        SessionStatus sessionStatus = drinkingSession.getSessionStatus();
        sessionStatus.setLastUpdateTime(drinkingSession.getCurrentDateTime());
        sessionStatus.setAlcoholScore(BAC);
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
        drinkingSession.setCurrentDateTime(LocalDateTime.now());
        SessionStatus sessionStatus = drinkingSession.getSessionStatus();
        Double alcoholScore = sessionStatus.getAlcoholScore();
        return alcoholScore;
    }
}
