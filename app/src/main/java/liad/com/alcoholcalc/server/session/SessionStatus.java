package liad.com.alcoholcalc.server.session;

import org.joda.time.LocalDateTime;

/**
 * Created by liad on 22/04/2020.
 */

public class SessionStatus {

    private LocalDateTime lastUpdateTime;
    private Double alcoholScore;
    private Double futureAlcoholScore;

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

    public Double getFutureAlcoholScore() {
        return futureAlcoholScore;
    }

    public void setAlcoholScore(Double alcoholScore) {
        this.alcoholScore = alcoholScore;
    }

    public void setFutureAlcoholScore(Double futureAlcoholScore) {
        this.futureAlcoholScore = futureAlcoholScore;
    }
}
