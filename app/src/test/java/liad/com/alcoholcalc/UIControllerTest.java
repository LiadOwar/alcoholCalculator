package liad.com.alcoholcalc;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.util.List;

import liad.com.alcoholcalc.converter.UIConverter;
import liad.com.alcoholcalc.converter.UIConverterImpl;
import liad.com.alcoholcalc.gateway.Gateway;
import liad.com.alcoholcalc.gateway.GatewayImpl;
import liad.com.alcoholcalc.server.beverage.StrongBeer;
import liad.com.alcoholcalc.server.session.SessionDrinkItem;
import liad.com.alcoholcalc.ui.controller.UIController;
import liad.com.alcoholcalc.ui.controller.UIControllerImpl;
import liad.com.alcoholcalc.ui.drinkitem.UIDrinkItem;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by liad on 07/05/2020.
 */

public class UIControllerTest extends BaseTest{


    private Gateway gateway = new GatewayImpl();

    private UIController uiController = new UIControllerImpl();

    private UIConverter uiConverter = new UIConverterImpl();


    @Test
    public void AddDrinkFromUIToServer_Test() {


        uiController.addDrinkToSession("strongbeer_img_500");
        SessionDrinkItem sessionDrinkItem = drinkingSession.getSessionDrinkingItems().get(0);
        assertThat(sessionDrinkItem, is(notNullValue()));
        assertThat(sessionDrinkItem.getBeverage(), is(notNullValue()));
    }

    @Test
    public void getAlcoholScore1StrongBeer_10min_test() {

        drinkingSession.setCurrentDateTime(MOCK_DATE_TIME);
        uiController.addDrinkToSession("strongbeer_img_500");
        SessionDrinkItem sessionDrinkItem = drinkingSession.getSessionDrinkingItems().get(0);
        sessionDrinkItem.setStartDateTime(MOCK_DATE_TIME);
        drinkingSession.setCurrentDateTime(MOCK_DATE_TIME.plusMinutes(10));

        double score = uiController.getAlcoholScore();

        assertThat(score, not(0d));
    }

    @Test
    public void getAlcoholScore1StrongBeer_1min_test() {

        drinkingSession.setCurrentDateTime(MOCK_DATE_TIME);
        uiController.addDrinkToSession("strongchaser_img_30");
        SessionDrinkItem sessionDrinkItem = drinkingSession.getSessionDrinkingItems().get(0);
        sessionDrinkItem.setStartDateTime(MOCK_DATE_TIME);
        drinkingSession.setCurrentDateTime(MOCK_DATE_TIME.plusMinutes(1));

        double score = uiController.getAlcoholScore();

        assertThat(score, is(1.96));
    }

    @Test
    public void convertJSONToUIDrinkItem_test() {

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("type", new StrongBeer().getType().toString());
            jsonObject.put("amount",100d);
            jsonObject.put("drinkTime",MOCK_DATE_TIME);
            UIDrinkItem uiDrinkItem = uiConverter.convertJSONToUIDrinkItem(jsonObject);
            assertThat(uiDrinkItem.getDrinkType(), is("STRONG_BEER"));
            assertThat(uiDrinkItem.getDrinkingDateTime().toString(), is(MOCK_DATE_TIME.toString()));
            assertThat(uiDrinkItem.getAmount(), is("100.0"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getDrinkListsFromServer_1_Item_test() {

        drinkingSession.setCurrentDateTime(MOCK_DATE_TIME);
        uiController.addDrinkToSession("strongchaser_img_30");
        SessionDrinkItem sessionDrinkItem = drinkingSession.getSessionDrinkingItems().get(0);
        sessionDrinkItem.setStartDateTime(MOCK_DATE_TIME);
        drinkingSession.setCurrentDateTime(MOCK_DATE_TIME.plusMinutes(1));

        List<UIDrinkItem> drinks = uiController.getSessionDrinks();

        assertThat(drinks.size(), is(1));
    }

    @Test
    public void getFutureAlcoholScore1StrongBeer_Test() {

        drinkingSession.setCurrentDateTime(MOCK_DATE_TIME);
        uiController.addDrinkToSession("strongbeer_img_500");
        SessionDrinkItem sessionDrinkItem = drinkingSession.getSessionDrinkingItems().get(0);
        sessionDrinkItem.setStartDateTime(MOCK_DATE_TIME);


        Double score = uiController.getFutureAlcoholScore();

        assertThat(score, is(5.13d));
    }

    @Test
    public void clearSessionUI_test() {

        drinkingSession.setCurrentDateTime(MOCK_DATE_TIME);
        uiController.addDrinkToSession("strongchaser_img_30");
        uiController.addDrinkToSession("strongchaser_img_30");
        SessionDrinkItem sessionDrinkItem1 = drinkingSession.getSessionDrinkingItems().get(0);
        SessionDrinkItem sessionDrinkItem2 = drinkingSession.getSessionDrinkingItems().get(1);
        sessionDrinkItem1.setStartDateTime(MOCK_DATE_TIME);
        sessionDrinkItem2.setStartDateTime(MOCK_DATE_TIME);
        drinkingSession.setCurrentDateTime(MOCK_DATE_TIME.plusMinutes(1));
        List<UIDrinkItem> drinks = uiController.getSessionDrinks();
        assertThat(drinks.size(), is(2));
        uiController.cleatSession();
        List<UIDrinkItem> drinksAfterClear = uiController.getSessionDrinks();
        assertThat(drinksAfterClear.size(), is(0));
    }



}
