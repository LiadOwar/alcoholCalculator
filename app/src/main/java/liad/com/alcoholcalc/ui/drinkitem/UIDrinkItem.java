package liad.com.alcoholcalc.ui.drinkitem;

public class UIDrinkItem {

    private String drinkType;

    private String drinkingDateTime;

    private String amount;

    private String etOHConc;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getEtOHConc() {
        return etOHConc;
    }

    public UIDrinkItem(String drinkType, String etOHConc, String amount, String drinkingDateTime) {
        this.drinkType = drinkType;
        this.etOHConc = etOHConc;
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
