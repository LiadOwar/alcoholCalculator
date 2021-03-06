package liad.com.alcoholcalc.server.session;

import org.joda.time.LocalDateTime;

import liad.com.alcoholcalc.server.beverage.Beverage;

/**
 * Created by liad on 22/04/2020.
 */

public class SessionDrinkItem {

    private Beverage beverage;
    private LocalDateTime startDateTime;
    private Double amount;
    private Double consumedAmount;
    private Boolean isEtOHConsumed;

    public SessionDrinkItem(Beverage beverage, Double amount, LocalDateTime startDateTime) {
        this.beverage = beverage;
        setDrinkAmount(amount);
        this.startDateTime = startDateTime;
        this.consumedAmount = 0D;
        this.isEtOHConsumed = false;
    }

    private void setDrinkAmount(Double amount) {
        if (amount == 0d) {
            this.amount = beverage.getAmount();
        }else {
            this.amount = amount;
        }
    }

    public Boolean getEtOHConsumed() {
        return isEtOHConsumed;
    }

    public void setEtOHConsumed(Boolean etOHConsumed) {
        isEtOHConsumed = etOHConsumed;
    }

    public Beverage getBeverage() {
        return beverage;
    }

    public void setBeverage(Beverage beverage) {
        this.beverage = beverage;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public Double getConsumedAmount() {
        return consumedAmount;
    }

    public void setConsumedAmount(Double consumedAmount) {
        this.consumedAmount = consumedAmount;
    }
}
