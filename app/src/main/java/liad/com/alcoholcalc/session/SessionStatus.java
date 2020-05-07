package liad.com.alcoholcalc.session;

import org.joda.time.LocalDateTime;

/**
 * Created by liad on 22/04/2020.
 */

public class SessionStatus {

    private LocalDateTime lastUpdateTime;
    private Double alcoholScore;

    public SessionStatus() {
        this.alcoholScore = 0D;
    }

    public LocalDateTime getLastUpdateTime() {
        return this.lastUpdateTime;
    }

    public void setLastUpdateTime(LocalDateTime lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public Double getAlcoholScore() {
        return alcoholScore;
    }

    public void setAlcoholScore(Double alcoholScore) {
        this.alcoholScore = alcoholScore;
    }
}
