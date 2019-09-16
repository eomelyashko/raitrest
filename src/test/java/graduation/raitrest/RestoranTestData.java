package graduation.raitrest;

import graduation.raitrest.model.entities.Restaurant;
import graduation.raitrest.model.to.RestaurantTo;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.Date;
import java.util.List;

import static graduation.raitrest.TestUtil.readListFromJsonMvcResult;
import static graduation.raitrest.UserTestData.*;
import static graduation.raitrest.model.AbstractBaseEntity.START_SEQ;
import static org.assertj.core.api.Assertions.assertThat;

public class RestoranTestData {
    public static final int RESTAURANT_ID = START_SEQ + 9;
    // У   манаджера два ресторана star и super star
    public static final Restaurant RESTAURANT_STAR = new Restaurant(RESTAURANT_ID, "Star",
            "Адресс 1, тел 111-111-111", "Директор 1", "Звезда", MANAGER);
    public static final Restaurant RESTAURANT_PEARL = new Restaurant(RESTAURANT_ID + 1, "Pearl",
            "Адресс 2, тел 222-222-222", "Директор 2", "Жемчужина", MANAGER_1);
    public static final Restaurant RESTAURANT_SUPER_STAR = new Restaurant(RESTAURANT_ID + 2, "Super Star",
            "Адресс 3, тел 333-333-333", "Директор 3", "Супер Звезда", MANAGER);
    //  У черной жемчужины два хозяина - манаджер и манажер_1
    public static final Restaurant RESTAURANT_BLACK_PEARL = new Restaurant(RESTAURANT_ID + 3, "Black Pearl",
             "Адресс 4, тел 444-444-444", "Директор 4", "Черная Жемчужина", MANAGER_2);


    public static List<Restaurant> restaurantList = List.of(RESTAURANT_STAR, RESTAURANT_PEARL, RESTAURANT_SUPER_STAR,
            RESTAURANT_BLACK_PEARL);

    public static Restaurant getCreated() {
        return new Restaurant( "Новый ресторан",
                "Новый Адресс , тел 111-111-111", "Новый директор",  "Новый ресторан", MANAGER);
    }

    public static Restaurant getUpdated() {
        return new Restaurant(RESTAURANT_ID, "Super Super Star",
                "Адресс 1, тел 121-121-121", "Директор 1", "Звезда", MANAGER);
    }

    public static void assertMatch(Restaurant actual, Restaurant expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "registered","manager");
        //  assertThat(actual).isEqualToComparingFieldByField(expected);
    }

    public static void assertMatch(Iterable<Restaurant> actual, Restaurant... expected) {
        assertMatch(actual, List.of(expected));
    }

    public static void assertMatch(Iterable<Restaurant> actual, Iterable<Restaurant> expected) {
          assertThat(actual).usingElementComparatorIgnoringFields("registered","manager").isEqualTo(expected);
       // assertThat(actual).usingDefaultElementComparator().isEqualTo(expected);
    }
    public static ResultMatcher contentJson(Restaurant... expected) {
        return contentJson(List.of(expected));
    }
    public static ResultMatcher contentJsonTo(RestaurantTo... expected) {
        return contentJsonTo(List.of(expected));
    }


    public static ResultMatcher contentJson(Iterable<Restaurant> expected) {
        return result -> assertThat(readListFromJsonMvcResult(result, Restaurant.class)).isEqualTo(expected);
    }
    public static ResultMatcher contentJsonTo(Iterable<RestaurantTo> expected) {
        return result -> assertThat(readListFromJsonMvcResult(result, RestaurantTo.class)).isEqualTo(expected);
    }


    public static void assertMatchTo(RestaurantTo actual, RestaurantTo expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "registered");
        //  assertThat(actual).isEqualToComparingFieldByField(expected);
    }

    public static void assertMatchTo(Iterable<RestaurantTo> actual, RestaurantTo... expected) {
        assertMatchTo(actual, List.of(expected));
    }
    public static void assertMatchTo(Iterable<RestaurantTo> actual, Iterable<RestaurantTo> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("registered").isEqualTo(expected);
        // assertThat(actual).usingDefaultElementComparator().isEqualTo(expected);
    }
}
