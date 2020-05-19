package liad.com.alcoholcalc.ui;

import android.app.Dialog;
import android.support.v7.app.AlertDialog;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import liad.com.alcoholcalc.R;
import liad.com.alcoholcalc.SessionActivity;
import liad.com.alcoholcalc.ui.controller.UIController;

/**
 * Created by liad on 07/05/2020.
 */

public class BeverageIconLongClickListener implements android.view.View.OnLongClickListener, PopupMenu.OnMenuItemClickListener{

    private String currentSelectedDrink = null;

    private UIController uiController;

    private SessionActivity sessionActivity;

    private Dialog fullCapacityDialog;

    public BeverageIconLongClickListener( SessionActivity sessionActivity) {
        this.sessionActivity = sessionActivity;
        this.uiController = sessionActivity.getUiController();
        fullCapacityDialog = initAlertOnFullDrinksCapacityDialog();
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

    private Dialog initAlertOnFullDrinksCapacityDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(sessionActivity);

        String msg = String.format("Cannot add more then [%s] drinks", sessionActivity.LAYOUT_MAX_SIZE * 2);
        builder.setMessage(msg)
                .setTitle("Full Capacity");

        AlertDialog dialog = builder.create();
        return dialog;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (isMaxDrinksCapacity()) {
            fullCapacityDialog.show();
            return false;
        }
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

    private boolean isMaxDrinksCapacity() {
        if (uiController.getSessionDrinks().size() >= sessionActivity.LAYOUT_MAX_SIZE * 2) {
            return true;
        }
        return false;
    }

}
