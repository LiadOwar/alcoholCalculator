package liad.com.alcoholcalc.user;

/**
 * Created by liad on 27/04/2020.
 */

public class SessionUser {

    private Double weightKg;
    private GENDER gender;

    public SessionUser(Double weightKg, GENDER gender) {
        this.weightKg = weightKg;
        this.gender = gender;
    }

    public Double getWeightKg() {
        return weightKg;
    }

    public void setWeightKg(Double weightKg) {
        this.weightKg = weightKg;
    }

    public GENDER getGender() {
        return gender;
    }
}
