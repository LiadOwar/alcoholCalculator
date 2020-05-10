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
        showBeverageIconMenu(v);
        Object tag = v.getTag();
        currentSelectedDrink = tag.toString();
        return true;
    }


    private void showBeverageIconMenu(View v) {
        PopupMenu beverageMenu = new PopupMenu(v.getContext(), v);
        beverageMenu.setOnMenuItemClickListener(this);
        beverageMenu.inflate(R.menu.beverage_icon_menu);
        beverageMenu.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (currentSelectedDrink.contains("beer")) {

        }
        uiController.addDrinkToSession(currentSelectedDrink);

        return false;
    }

}
