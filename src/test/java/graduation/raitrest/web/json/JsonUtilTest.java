package graduation.raitrest.web.json;

import graduation.raitrest.UserTestData;
import graduation.raitrest.model.entities.Restaurant;
import graduation.raitrest.model.entities.User;
import org.junit.jupiter.api.Test;

import java.util.List;

import static graduation.raitrest.RestoranTestData.*;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class JsonUtilTest {
    @Test
    void readWriteValue() throws Exception {
        String json = JsonUtil.writeValue(RESTAURANT_STAR);
        System.out.println(json);
        Restaurant restaurant = JsonUtil.readValue(json, Restaurant.class);
        assertMatch(restaurant, RESTAURANT_STAR);
    }

    @Test
    void readWriteValues() throws Exception {
        String json = JsonUtil.writeValue(restaurantList);
        System.out.println(json);
        List<Restaurant> meals = JsonUtil.readValues(json, Restaurant.class);
        assertMatch(meals, restaurantList);
    }
    @Test
    void writeOnlyAccess() throws Exception {
        String json = JsonUtil.writeValue(UserTestData.USER);
        System.out.println(json);
        assertThat(json, not(containsString("password")));
        String jsonWithPass = UserTestData.jsonWithPassword(UserTestData.USER, "newPass");
        System.out.println(jsonWithPass);
        User user = JsonUtil.readValue(jsonWithPass, User.class);
        assertEquals(user.getPassword(), "newPass");
    }
}