package graduation.raitrest.web.restaurant;

import graduation.raitrest.model.entities.Restaurant;
import graduation.raitrest.model.to.RestaurantTo;
import graduation.raitrest.service.RestaurantService;
import graduation.raitrest.web.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static graduation.raitrest.RestoranTestData.*;
import static graduation.raitrest.RestoranTestData.RESTAURANT_STAR;
import static graduation.raitrest.TestUtil.readFromJsonMvcResult;
import static graduation.raitrest.TestUtil.userHttpBasic;
import static graduation.raitrest.UserTestData.MANAGER;
import static graduation.raitrest.UserTestData.USER;
import static graduation.raitrest.util.Util.restaurant_2_RestaurantTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProfileRestaurantRestControllerTest extends AbstractControllerTest {
    private static final String REST_URL = ProfileRestaurantRestController.REST_URL + '/';
    @Autowired
    private RestaurantService service;

    @Test
    void getAll() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL).with(userHttpBasic(USER)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJsonTo(service.getAll()));

    }
    @Test
    void get() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + RESTAURANT_ID).with(userHttpBasic(USER)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(result -> assertMatchTo(readFromJsonMvcResult(result, RestaurantTo.class), restaurant_2_RestaurantTo( RESTAURANT_STAR)));

    }
}