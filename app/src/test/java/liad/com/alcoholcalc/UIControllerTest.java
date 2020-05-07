package liad.com.alcoholcalc;

import android.widget.ImageView;

import org.junit.Test;

import liad.com.alcoholcalc.gateway.Gateway;
import liad.com.alcoholcalc.gateway.GatewayImpl;

/**
 * Created by liad on 07/05/2020.
 */

public class UIControllerTest extends BaseTest{

    private Gateway gateway = new GatewayImpl();
    private MockActivity mockActivity = new MockActivity();
    @Test
    public void AddDrinkFromUIToServer_Test() {
        final ImageView strongBeerView= (ImageView)mockActivity.findViewById(R.id.mock_strongbeer_icon);

    }
}
