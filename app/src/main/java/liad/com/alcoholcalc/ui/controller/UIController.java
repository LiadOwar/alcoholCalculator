package liad.com.alcoholcalc.ui.controller;

import java.io.Serializable;
import java.util.List;

import liad.com.alcoholcalc.ui.drinkitem.UIDrinkItem;

/**
 * Created by liad on 07/05/2020.
 */

public interface UIController extends Serializable {

    void addDrinkToSession(String drinkDetails);

    double getAlcoholScore();

    Double getFutureAlcoholScore();

    List<UIDrinkItem> getSessionDrinks();

    void addTimeToCurrentTime(int min);

    void cleatSession();

    void removeDrink(UIDrinkItem drinkItem);
}
