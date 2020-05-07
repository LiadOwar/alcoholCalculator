package liad.com.alcoholcalc.converter;

import liad.com.alcoholcalc.user.GENDER;

/**
 * Created by liad on 01/05/2020.
 */

public interface UIConverter {

    Double convertUIWieght(Integer weightInt);

    GENDER convertUIGender (String genderString);

}
