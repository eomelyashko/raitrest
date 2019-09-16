package graduation.raitrest.model.to;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;

public class VoteTo extends BaseTo {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime dateTime = LocalDateTime.now();

    @NotNull
    private Integer restaurantID;

    private Integer userID;

    public VoteTo() {
    }

    public VoteTo(Integer id, Integer restaurantID) {
        super(id);
        this.restaurantID = restaurantID;
    }
    public VoteTo(Integer id, Integer restaurantID,LocalDateTime dateTime,Integer userID) {
        super(id);
        this.restaurantID = restaurantID;
        this.dateTime = dateTime;
        this.userID = userID;
    }
    public VoteTo( Integer restaurantID) {
       this(null,restaurantID);
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Integer getRestaurantID() {
        return restaurantID;
    }

    public void setRestaurantID(Integer restaurantID) {
        this.restaurantID = restaurantID;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VoteTo)) return false;
        VoteTo voteTo = (VoteTo) o;
        return Objects.equals(dateTime, voteTo.dateTime) &&
                Objects.equals(restaurantID, voteTo.restaurantID) &&
                Objects.equals(userID, voteTo.userID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dateTime, restaurantID, userID);
    }
}
