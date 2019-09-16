package graduation.raitrest.model.to;

import java.util.Objects;

public class Rating {
    private Integer restaurantId;
    private String restaurantName;
    private Long countVotes;

    public Rating(Integer restaurantId, String restaurantName, Long countVotes) {
        this.restaurantId = restaurantId;
        this.restaurantName = restaurantName;
        this.countVotes = countVotes;
    }

    public Rating() {

    }

    public Integer getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Integer restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public Long getCountVotes() {
        return countVotes;
    }

    public void setCountVotes(Long countVotes) {
        this.countVotes = countVotes;
    }

    @Override
    public String toString() {
        return "Rating{" +
                "restaurantId=" + restaurantId +
                ", restaurantName='" + restaurantName + '\'' +
                ", countVotes=" + countVotes +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rating rating = (Rating) o;
        return Objects.equals(restaurantId, rating.restaurantId) &&
                Objects.equals(restaurantName, rating.restaurantName) &&
                Objects.equals(countVotes, rating.countVotes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(restaurantId, restaurantName, countVotes);
    }
}
