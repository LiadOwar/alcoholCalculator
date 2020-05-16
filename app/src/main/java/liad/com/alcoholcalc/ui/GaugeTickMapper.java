package liad.com.alcoholcalc.ui;

import kotlin.jvm.functions.Function2;

public class GaugeTickMapper implements Function2 {
    @Override
    public Object invoke(Object o, Object o2) {
        Integer tickPosition = (Integer)o;
        switch (tickPosition) {
            case 1 : return "Drive";
            case 3 : return "Good Mood";
            case 6 : return "Pace Yourself";
            case 12 : return "Shitfaced";
            case 18 : return "Your Dead";
            default: return "";
        }
    }
}
