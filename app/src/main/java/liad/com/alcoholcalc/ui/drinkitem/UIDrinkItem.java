package liad.com.alcoholcalc.ui.drinkitem;

public class UIDrinkItem {

    private String drinkType;

    private String drinkingDateTime;

    private String amount;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public UIDrinkItem(String drinkType, String amount, String drinkingDateTime) {
        this.drinkType = drinkType;
        this.amount = amount;
        this.drinkingDateTime = drinkingDateTime;
    }

    public String getDrinkType() {
        return drinkType;
    }

    public void setDrinkType(String drinkType) {
        this.drinkType = drinkType;
    }

    public String getDrinkingDateTime() {
        return drinkingDateTime;
    }

    public void setDrinkingDateTime(String drinkingDateTime) {
        this.drinkingDateTime = drinkingDateTime;
    }
}
