package graduation.raitrest.web.menu;

import graduation.raitrest.model.entities.MenuDetails;
import graduation.raitrest.model.to.MenuDetailTo;
import graduation.raitrest.service.MenuDetailsService;
import graduation.raitrest.web.AbstractControllerTest;
import graduation.raitrest.web.SecurityUtil;
import graduation.raitrest.web.json.JsonUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static graduation.raitrest.MenuDetailsTestData.*;
import static graduation.raitrest.RestoranTestData.RESTAURANT_ID;
import static graduation.raitrest.RestoranTestData.RESTAURANT_STAR;
import static graduation.raitrest.TestUtil.*;
import static graduation.raitrest.UserTestData.*;
import static graduation.raitrest.util.Util.menuDetail_2_MenuDetailTo;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ManagerMenuDetailRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = ManagerMenuDetailRestController.REST_URL + '/';

    @Autowired
    private MenuDetailsService service;

    @Test
    void get() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + MENU_DETAILS_ID).with(userHttpBasic(MANAGER)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(result -> assertMatchTo(readFromJsonMvcResult(result, MenuDetailTo.class), menuDetail_2_MenuDetailTo(MENU_DETAILS_STAR_TODAY_1)));
    }

    @Test
    void getNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + MENU_DETAILS_ID)
                .with(userHttpBasic(MANAGER_1)))
                .andExpect(status().isUnprocessableEntity());
    }


    @Test
    void getUnauth() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + MENU_DETAILS_ID))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void delete() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.delete(REST_URL + MENU_DETAILS_ID).with(userHttpBasic(MANAGER)))
                .andExpect(status().isNoContent());

        List<MenuDetails> menuAllManager = service.getFilterByDateByRestaurant(LocalDate.now(), LocalDate.now().plusDays(1L), RESTAURANT_ID);
        assertMatch(menuAllManager, MENU_DETAILS_STAR_TODAY_2, MENU_DETAILS_STAR_TODAY_3, MENU_DETAILS_STAR_TODAY_4);
    }

    @Test
    void deleteNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(REST_URL + MENU_DETAILS_ID)
                .with(userHttpBasic(MANAGER_2)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }


 /*   @Test
    void getAll() throws Exception {

        List<MenuDetailTo> allMenu = service.getAll();

        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + "all").with(userHttpBasic(USER)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJsonTo(allMenu));

    }

    @Test
    void getAllCurrentDay() throws Exception {
        List<MenuDetails> allMenu = service.getFilterByDate(LocalDate.now(), LocalDate.now().plusDays(1L));

        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL).with(userHttpBasic(USER)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(allMenu));

    }*/

    @Test
    void create() throws Exception {

        MenuDetailTo newMenu = menuDetail_2_MenuDetailTo(new MenuDetails(RESTAURANT_STAR,
                "Пятое блюдо", "Хлеб", "1 кусочек", new BigDecimal("0.10"), LocalDateTime.now()));

        ResultActions action = mockMvc.perform(MockMvcRequestBuilders.post(REST_URL).with(userHttpBasic(MANAGER))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newMenu)))
                .andDo(print());


        MenuDetailTo created = readFromJson(action, MenuDetailTo.class);
        newMenu.setId(created.getId());

        assertMatchTo(created, newMenu);
    }

    @Test
    void update() throws Exception {

        MenuDetailTo updated = menuDetail_2_MenuDetailTo(new MenuDetails(MENU_DETAILS_STAR_TODAY_4.id(), RESTAURANT_STAR,
                "Первое блюдо", "Супер Уха", "100 грамм", new BigDecimal("25.00"),
                LocalDateTime.now()));

        mockMvc.perform(MockMvcRequestBuilders.put(REST_URL/* + (MENU_DETAILS_ID + 3)*/).contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(MANAGER))
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNoContent())
                .andDo(print());

        MenuDetailTo actual = menuDetail_2_MenuDetailTo(service.get(MENU_DETAILS_STAR_TODAY_4.id(), MANAGER_ID));
        assertMatchTo(actual, updated);

    }
}