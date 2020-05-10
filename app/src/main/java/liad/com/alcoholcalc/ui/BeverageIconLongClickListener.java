package liad.com.alcoholcalc.ui;

import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import liad.com.alcoholcalc.R;
import liad.com.alcoholcalc.ui.controller.UIController;

/**
 * Created by liad on 07/05/2020.
 */

public class BeverageIconLongClickListener implements android.view.View.OnLongClickListener, PopupMenu.OnMenuItemClickListener{

    private String currentSelectedDrink = null;

    private UIController uiController;

    public BeverageIconLongClickListener(UIController uiController) {
        this.uiController = uiController;
    }

    @Override
    public boolean onLongClick(View v) {
        Object tag = v.getTag();
        currentSelectedDrink = tag.toString();
        if (currentSelectedDrink.toLowerCase().contains("beer")) {
            showBeerSettingsMenu(v);
        } else {
            showBeverageIconMenu(v);
        }

        return true;
    }


    private void showBeverageIconMenu(View v) {
        PopupMenu beverageMenu = new PopupMenu(v.getContext(), v);
        beverageMenu.setOnMenuItemClickListener(this);
        beverageMenu.inflate(R.menu.beverage_icon_menu);
        beverageMenu.show();
    }

    private void showBeerSettingsMenu(View v) {
        PopupMenu beerSettingsMenu = new PopupMenu(v.getContext(), v);
        beerSettingsMenu.setOnMenuItemClickListener(this);
        beerSettingsMenu.inflate(R.menu.beer_settings_menu);
        beerSettingsMenu.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        int itemId = item.getItemId();
        Double amount = null;
        switch (itemId) {
            case R.id.beer_500 : amount = 500d;
            break;
            case R.id.beer_473 : amount = 473d;
            break;
            case R.id.beer_330 : amount = 330d;
            break;
            default: amount = null;
        }
        uiController.addDrinkToSession(currentSelectedDrink+ "_" + amount);

        return false;
    }

}
