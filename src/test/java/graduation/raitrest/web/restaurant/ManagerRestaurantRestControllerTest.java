package graduation.raitrest.web.restaurant;

import graduation.raitrest.model.entities.Restaurant;
import graduation.raitrest.service.RestaurantService;
import graduation.raitrest.web.AbstractControllerTest;
import graduation.raitrest.web.json.JsonUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static graduation.raitrest.RestoranTestData.*;
import static graduation.raitrest.TestUtil.*;
import static graduation.raitrest.UserTestData.*;
import static graduation.raitrest.util.Util.restaurant_2_RestaurantTo;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ManagerRestaurantRestControllerTest extends AbstractControllerTest {
    private static final String REST_URL = ManagerRestaurantRestController.REST_URL + '/';

    @Autowired
    private RestaurantService service;



    @Test
    void getAllByManager() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + "manager/" + MANAGER_ID).with(userHttpBasic(MANAGER)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(service.getAll(MANAGER_ID)));

    }

    @Test
    void get() throws Exception {
         mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + RESTAURANT_ID).with(userHttpBasic(MANAGER)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(result -> assertMatch(readFromJsonMvcResult(result, Restaurant.class), RESTAURANT_STAR));

    }
    @Test
    void getNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + RESTAURANT_ID)
                .with(userHttpBasic(MANAGER_1)))
                .andExpect(status().isUnprocessableEntity());
    }


    @Test
    void getUnauth() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + RESTAURANT_ID))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void delete() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.delete(REST_URL + RESTAURANT_ID).with(userHttpBasic(MANAGER)))
                .andDo(print())
                .andExpect(status().isNoContent());

        assertMatchTo(service.getAll(), restaurant_2_RestaurantTo(RESTAURANT_PEARL),
                restaurant_2_RestaurantTo(RESTAURANT_SUPER_STAR),
                restaurant_2_RestaurantTo(RESTAURANT_BLACK_PEARL));
    }
    @Test
    void deleteNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(REST_URL + RESTAURANT_ID)
                .with(userHttpBasic(MANAGER_2)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }


    @Test
    void update() throws Exception {

        Restaurant updated = getUpdated();
        mockMvc.perform(MockMvcRequestBuilders.put(REST_URL + RESTAURANT_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(MANAGER))
                .content(JsonUtil.writeValue(restaurant_2_RestaurantTo(updated))))
                .andExpect(status().isNoContent())
                .andDo(print());


        assertMatch(service.get(RESTAURANT_ID, MANAGER_ID), updated);
    }

    @Test
    void createWithLocation() throws Exception {

        Restaurant newRestaurant = getCreated();
        ResultActions action = mockMvc.perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(MANAGER))
                .content(JsonUtil.writeValue(restaurant_2_RestaurantTo(newRestaurant))))
                .andDo(print());



        Restaurant returned = readFromJson(action, Restaurant.class);
        newRestaurant.setId(returned.getId());

        assertMatch(returned, newRestaurant);
        assertMatch(service.getAll(MANAGER_ID), RESTAURANT_STAR, RESTAURANT_SUPER_STAR,newRestaurant);

    }


}