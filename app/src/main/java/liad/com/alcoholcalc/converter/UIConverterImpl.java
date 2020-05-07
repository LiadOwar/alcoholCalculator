package liad.com.alcoholcalc.converter;

import liad.com.alcoholcalc.user.GENDER;

/**
 * Created by liad on 01/05/2020.
 */

public class UIConverterImpl implements UIConverter {
    @Override
    public Double convertUIWieght(Integer weightInt) {
        return Double.valueOf(weightInt);
    }

    @Override
    public GENDER convertUIGender(String genderString) {
        switch (genderString) {
            case "MALE" : return GENDER.MALE;
            case "FEMALE" : return GENDER.FEMALE;
            default: return GENDER.MALE;
        }
    }
}
