package liad.com.alcoholcalc.ui;

import android.os.Vibrator;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import liad.com.alcoholcalc.R;
import liad.com.alcoholcalc.ui.controller.UIController;
import liad.com.alcoholcalc.ui.drinkitem.UIDrinkItem;

public class DrinkListItemOnClickListener implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {

    private UIDrinkItem drinkItem;

    private UIController uiController;

    private Vibrator vibe;


    public DrinkListItemOnClickListener(UIDrinkItem drinkItem, UIController uiController, Vibrator vibe) {
        this.drinkItem = drinkItem;
        this.uiController = uiController;
        this.vibe = vibe;
    }

    @Override
    public void onClick(View v) {
        showDrinkListItemMenu(v);

    }

    private void showDrinkListItemMenu(View v) {
        PopupMenu beverageMenu = new PopupMenu(v.getContext(), v);
        beverageMenu.setOnMenuItemClickListener(this);
        beverageMenu.inflate(R.menu.drink_list_item_menu);
        setDrinkDescriptionInMenue(beverageMenu, drinkItem);
        beverageMenu.show();
    }

    private void setDrinkDescriptionInMenue(PopupMenu beverageMenu, UIDrinkItem drinkItem) {
        String drinkType = drinkItem.getDrinkType();
        String amount = drinkItem.getAmount();
        String etOHConc = drinkItem.getEtOHConc();
        String drinkingDateTime = drinkItem.getDrinkingDateTime();

        beverageMenu.getMenu().getItem(0).setTitle(drinkType);
        beverageMenu.getMenu().getItem(1).setTitle((amount + " [ml]" + etOHConc + " [%]"));
        beverageMenu.getMenu().getItem(2).setTitle(drinkingDateTime);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {

        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.remove_drink : uiController.removeDrink(drinkItem);
                break;
            default: return false;
        }
        return false;
    }
}
