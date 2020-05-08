package liad.com.alcoholcalc;

import org.junit.Test;

import liad.com.alcoholcalc.gateway.Gateway;
import liad.com.alcoholcalc.gateway.GatewayImpl;

/**
 * Created by liad on 07/05/2020.
 */

public class UIControllerTest extends BaseTest{


    private Gateway gateway = new GatewayImpl();


    @Test
    public void AddDrinkFromUIToServer_Test() {

        gateway.AddDrinkFromUIToServer("strongbeer_img");


    }
}
