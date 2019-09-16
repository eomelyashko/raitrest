package graduation.raitrest.web.menu;

import graduation.raitrest.model.entities.MenuDetails;
import graduation.raitrest.model.to.MenuDetailTo;
import graduation.raitrest.service.MenuDetailsService;
import graduation.raitrest.web.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.List;

import static graduation.raitrest.MenuDetailsTestData.contentJson;
import static graduation.raitrest.MenuDetailsTestData.contentJsonTo;
import static graduation.raitrest.RestoranTestData.RESTAURANT_STAR;
import static graduation.raitrest.TestUtil.userHttpBasic;
import static graduation.raitrest.UserTestData.USER;
import static graduation.raitrest.util.Util.menuDetail_2_MenuDetailTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProfileMenuDetailRestControllerTest extends AbstractControllerTest {
    private static final String REST_URL = ProfileMenuDetailRestController.REST_URL + '/';

    @Autowired
    private MenuDetailsService service;


    @Test
    void getAll() throws Exception {

        List<MenuDetailTo> allMenu = service.getAll();

        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + "all").with(userHttpBasic(USER)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJsonTo(allMenu));
    }

    @Test
    void getAllCurrentDayTo() throws Exception {
        List<MenuDetails> allMenu = service.getFilterByDate(LocalDate.now(), LocalDate.now());

        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL).with(userHttpBasic(USER)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJsonTo(menuDetail_2_MenuDetailTo(allMenu)));

    }
    @Test
    void getTodayByRestaurantID() throws Exception {
        List<MenuDetails> allMenu = service.getFilterByDateByRestaurant(LocalDate.now(), LocalDate.now(),RESTAURANT_STAR.id());
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + "restaurant/" + RESTAURANT_STAR.id() ).with(userHttpBasic(USER)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJsonTo(menuDetail_2_MenuDetailTo(allMenu)));


    }
}