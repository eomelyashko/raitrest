package graduation.raitrest.service;

import graduation.raitrest.RestoranTestData;
import graduation.raitrest.model.entities.MenuDetails;
import graduation.raitrest.model.to.MenuDetailTo;
import graduation.raitrest.util.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static graduation.raitrest.MenuDetailsTestData.assertMatch;
import static graduation.raitrest.MenuDetailsTestData.*;
import static graduation.raitrest.RestoranTestData.RESTAURANT_ID;
import static graduation.raitrest.RestoranTestData.RESTAURANT_STAR;
import static graduation.raitrest.UserTestData.*;
import static graduation.raitrest.util.Util.menuDetail_2_MenuDetailTo;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class MenuDetailsServiceTest extends AbstractServiceTest {

    @Autowired
    protected MenuDetailsService service;


    @Test
     void get() {
        // просто по  id
        MenuDetails menuDetails = service.get(MENU_DETAILS_STAR_TODAY_3.getId());
        assertMatch(menuDetails, MENU_DETAILS_STAR_TODAY_3);
        //---
        menuDetails = service.get(MENU_DETAILS_STAR_TODAY_2.getId());
        assertMatch(menuDetails, MENU_DETAILS_STAR_TODAY_2);
        menuDetails = service.get(MENU_DETAILS_STAR_TODAY_1.getId());
        assertMatch(menuDetails, MENU_DETAILS_STAR_TODAY_1);
        menuDetails = service.get(MENU_DETAILS_STAR_TODAY_4.getId());
        assertMatch(menuDetails, MENU_DETAILS_STAR_TODAY_4);


        menuDetails = service.get(MENU_DETAILS_PEARL_TODAY_1.getId());
        assertMatch(menuDetails, MENU_DETAILS_PEARL_TODAY_1);
        menuDetails = service.get(MENU_DETAILS_PEARL_TODAY_2.getId());
        assertMatch(menuDetails, MENU_DETAILS_PEARL_TODAY_2);
        menuDetails = service.get(MENU_DETAILS_PEARL_TODAY_3.getId());
        assertMatch(menuDetails, MENU_DETAILS_PEARL_TODAY_3);
        menuDetails = service.get(MENU_DETAILS_PEARL_TODAY_4.getId());
        assertMatch(menuDetails, MENU_DETAILS_PEARL_TODAY_4);

        menuDetails = service.get(MENU_DETAILS_SUPER_STAR_TODAY_1.getId());
        assertMatch(menuDetails, MENU_DETAILS_SUPER_STAR_TODAY_1);
        menuDetails = service.get(MENU_DETAILS_SUPER_STAR_TODAY_2.getId());
        assertMatch(menuDetails, MENU_DETAILS_SUPER_STAR_TODAY_2);
        menuDetails = service.get(MENU_DETAILS_SUPER_STAR_TODAY_3.getId());
        assertMatch(menuDetails, MENU_DETAILS_SUPER_STAR_TODAY_3);
        menuDetails = service.get(MENU_DETAILS_SUPER_STAR_TODAY_4.getId());
        assertMatch(menuDetails, MENU_DETAILS_SUPER_STAR_TODAY_4);


        // по id и manager_id
        menuDetails = service.get(MENU_DETAILS_STAR_TODAY_4.getId(), MANAGER_ID);
        assertMatch(menuDetails, MENU_DETAILS_STAR_TODAY_4);
    }

    @Test
     void getAll() {
        //all

        List<MenuDetailTo> menuDetailToList = service.getAll();
      //  assertThat(menuDetailToList).usingElementComparatorIgnoringFields("dateTime").isEqualTo(menuDetail_2_MenuDetailTo(MENU_DETAILS_LIST));
        assertMatchTo(menuDetailToList,menuDetail_2_MenuDetailTo(MENU_DETAILS_LIST));
        // all by manager_id
        List<MenuDetails> allMenu= service.getAll(MANAGER_1_ID);
        assertMatch(List.of(MENU_DETAILS_PEARL_TODAY_1, MENU_DETAILS_PEARL_TODAY_2, MENU_DETAILS_PEARL_TODAY_3, MENU_DETAILS_PEARL_TODAY_4, MENU_DETAILS_PEARL_YESTERDAY_1, MENU_DETAILS_PEARL_YESTERDAY_2, MENU_DETAILS_PEARL_YESTERDAY_3), allMenu);

        // all by date
        List<MenuDetails> menuDetailsToday = List.of(MENU_DETAILS_STAR_TODAY_1,
                MENU_DETAILS_STAR_TODAY_2, MENU_DETAILS_STAR_TODAY_3, MENU_DETAILS_STAR_TODAY_4,

                MENU_DETAILS_PEARL_TODAY_1, MENU_DETAILS_PEARL_TODAY_2, MENU_DETAILS_PEARL_TODAY_3, MENU_DETAILS_PEARL_TODAY_4,

                MENU_DETAILS_SUPER_STAR_TODAY_1, MENU_DETAILS_SUPER_STAR_TODAY_2, MENU_DETAILS_SUPER_STAR_TODAY_3, MENU_DETAILS_SUPER_STAR_TODAY_4,

                MENU_DETAILS_BLACK_PEARL_TODAY_1, MENU_DETAILS_BLACK_PEARL_TODAY_2, MENU_DETAILS_BLACK_PEARL_TODAY_3, MENU_DETAILS_BLACK_PEARL_TODAY_4);

        allMenu = service.getFilterByDate(LocalDate.now(), LocalDate.now().plusDays(1L));
        assertMatch(menuDetailsToday, allMenu);

        allMenu = service.getFilterByDateByRestaurant(LocalDate.now(), LocalDate.now().plusDays(1L),RESTAURANT_ID);
        assertMatch(List.of(MENU_DETAILS_STAR_TODAY_1,
                MENU_DETAILS_STAR_TODAY_2, MENU_DETAILS_STAR_TODAY_3, MENU_DETAILS_STAR_TODAY_4), allMenu);
    }

    @Test
     void getNotFound() {
         assertThrows(NotFoundException.class, () ->   service.get(1, MANAGER_ID));
    }

    @Test
     void getNotOwn()  {
        assertThrows(NotFoundException.class, () -> service.get(MENU_DETAILS_ID, MANAGER_1_ID));
    }

    @Test
     void create() {

        MenuDetails newMenu = new MenuDetails(RESTAURANT_STAR,
                "Пятое блюдо", "Хлеб", "1 кусочек", new BigDecimal("0.10"), LocalDateTime.now());

        MenuDetails created = service.create(newMenu, RestoranTestData.RESTAURANT_ID, MANAGER_ID);
        newMenu.setId(created.getId());
        newMenu.setRestaurant(created.getRestaurant());
        assertMatch(created, newMenu);
        MenuDetails menuDetailsGet = service.get(created.getId());
        assertMatch(menuDetailsGet, newMenu);


    }

    @Test
     void update()  {
        MenuDetails updated =  new MenuDetails(MENU_DETAILS_ID + 3, RESTAURANT_STAR,
                "Первое блюдо", "Супер Уха", "100 грамм", new BigDecimal("25.00"),
                LocalDateTime.now());


        service.update(updated, MANAGER_ID);
        MenuDetails actual = service.get(MENU_DETAILS_ID + 3, MANAGER_ID);
        assertMatch(actual, updated);


    }
    @Test
    public void updateNotFound() throws Exception {
        NotFoundException e =  assertThrows(NotFoundException.class, () -> service.update(MENU_DETAILS_STAR_TODAY_4, ADMIN_ID));
        assertEquals(e.getMessage(), "Not found entity with id=" + (MENU_DETAILS_STAR_TODAY_4.getId()));
    }


    @Test
     void delete() {
        service.delete(MENU_DETAILS_ID);
        List<MenuDetails> menuAllManager  = service.getFilterByDateByRestaurant(LocalDate.now(), LocalDate.now().plusDays(1L),RESTAURANT_ID);
        assertMatch(menuAllManager,  MENU_DETAILS_STAR_TODAY_2, MENU_DETAILS_STAR_TODAY_3, MENU_DETAILS_STAR_TODAY_4);
    }

    @Test
    public void deleteWithUserID() {
        service.delete(MENU_DETAILS_ID+4,MANAGER_1_ID);
        List<MenuDetails> menuAllManager  = service.getFilterByDateByRestaurant(LocalDate.now(), LocalDate.now().plusDays(1L),RESTAURANT_ID +1);
        assertMatch(menuAllManager, MENU_DETAILS_PEARL_TODAY_2,MENU_DETAILS_PEARL_TODAY_3,MENU_DETAILS_PEARL_TODAY_4);
    }

    @Test
    public void deleteNotFound()  {

        assertThrows(NotFoundException.class, () ->   service.delete(1, MANAGER_ID));
    }

    @Test
    public void deleteNotOwn() {
         assertThrows(NotFoundException.class, () ->  service.delete(MENU_DETAILS_ID, MANAGER_1_ID));
    }


}