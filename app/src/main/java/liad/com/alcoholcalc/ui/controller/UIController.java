package liad.com.alcoholcalc.ui.controller;

import java.io.Serializable;

/**
 * Created by liad on 07/05/2020.
 */

public interface UIController extends Serializable {

    void addDrinkToSession(String drinkDetails);

    double getAlcoholScore();
}
