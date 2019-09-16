package graduation.raitrest;

import graduation.raitrest.model.entities.MenuDetails;
import graduation.raitrest.model.to.MenuDetailTo;
import org.springframework.test.web.servlet.ResultMatcher;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import static graduation.raitrest.RestoranTestData.*;
import static graduation.raitrest.TestUtil.readListFromJsonMvcResult;
import static org.assertj.core.api.Assertions.assertThat;

public class MenuDetailsTestData {
    // TODAY
    public static final int MENU_DETAILS_ID = 100;
    // Star
    
    public static final MenuDetails MENU_DETAILS_STAR_TODAY_1 = new MenuDetails(MENU_DETAILS_ID, RESTAURANT_STAR,
            "Первое блюдо", "Борщ", "250 грамм", new BigDecimal("10.00"), LocalDateTime.now());
    public static final MenuDetails MENU_DETAILS_STAR_TODAY_2 = new MenuDetails(MENU_DETAILS_ID + 1, RESTAURANT_STAR,
            "Второе блюдо", "Картошка с мясом", "200 грамм", new BigDecimal("25.00"),
            LocalDateTime.now());
    public static final MenuDetails MENU_DETAILS_STAR_TODAY_3 = new MenuDetails(MENU_DETAILS_ID + 2, RESTAURANT_STAR,
            "Третье блюдо", "Салат овощной", "100 грамм", new BigDecimal("5.50"),
            LocalDateTime.now());
    public static final MenuDetails MENU_DETAILS_STAR_TODAY_4 = new MenuDetails(MENU_DETAILS_ID + 3, RESTAURANT_STAR,
            "Четвертое блюдо", "Компот", "250 грамм", new BigDecimal("4.00"),
            LocalDateTime.now());
    // Pearl
    public static final MenuDetails MENU_DETAILS_PEARL_TODAY_1 = new MenuDetails(MENU_DETAILS_ID + 4, RESTAURANT_PEARL,
            "Первое блюдо", "Уха", "250 грамм", new BigDecimal("12.00"),
            LocalDateTime.now());
    public static final MenuDetails MENU_DETAILS_PEARL_TODAY_2 = new MenuDetails(MENU_DETAILS_ID + 5, RESTAURANT_PEARL,
            "Второе блюдо", "рис  с рыбой", "200 грамм", new BigDecimal("18.00"),
            LocalDateTime.now());
    public static final MenuDetails MENU_DETAILS_PEARL_TODAY_3 = new MenuDetails(MENU_DETAILS_ID + 6, RESTAURANT_PEARL,
            "Третье блюдо", "Салат крабовый", "100 грамм", new BigDecimal("7.50"),
            LocalDateTime.now());

    public static final MenuDetails MENU_DETAILS_PEARL_TODAY_4 = new MenuDetails(MENU_DETAILS_ID + 7, RESTAURANT_PEARL,
            "Четвертое блюдо", "Сок", "250 грамм", new BigDecimal("4.00"),
            LocalDateTime.now());

    // Super Star
    public static final MenuDetails MENU_DETAILS_SUPER_STAR_TODAY_1 = new MenuDetails(MENU_DETAILS_ID + 8, RESTAURANT_SUPER_STAR,
            "Первое блюдо", "Грибной суб", "250 грамм", new BigDecimal("12.00"),
            LocalDateTime.now());

    public static final MenuDetails MENU_DETAILS_SUPER_STAR_TODAY_2 = new MenuDetails(MENU_DETAILS_ID + 9, RESTAURANT_SUPER_STAR,
            "Второе блюдо", "Мясное рагу", "200 грамм", new BigDecimal("18.00"),
            LocalDateTime.now());

    public static final MenuDetails MENU_DETAILS_SUPER_STAR_TODAY_3 = new MenuDetails(MENU_DETAILS_ID + 10, RESTAURANT_SUPER_STAR,
            "Третье блюдо", "Кокот", "100 грамм", new BigDecimal("7.50"),
            LocalDateTime.now());
    public static final MenuDetails MENU_DETAILS_SUPER_STAR_TODAY_4 = new MenuDetails(MENU_DETAILS_ID + 11, RESTAURANT_SUPER_STAR,
            "Четвертое блюдо", "Чай", "250 грамм", new BigDecimal("4.00"),
            LocalDateTime.now());

    // Black Pearl
    public static final MenuDetails MENU_DETAILS_BLACK_PEARL_TODAY_1 = new MenuDetails(MENU_DETAILS_ID + 12, RESTAURANT_BLACK_PEARL,
            "Первое блюдо", "Солянка", "250 грамм", new BigDecimal("12.00"),
            LocalDateTime.now());

    public static final MenuDetails MENU_DETAILS_BLACK_PEARL_TODAY_2 = new MenuDetails(MENU_DETAILS_ID + 13, RESTAURANT_BLACK_PEARL,
            "Второе блюдо", "Баранина", "200 грамм", new BigDecimal("18.00"),
            LocalDateTime.now());

    public static final MenuDetails MENU_DETAILS_BLACK_PEARL_TODAY_3 = new MenuDetails(MENU_DETAILS_ID + 14, RESTAURANT_BLACK_PEARL,
            "Третье блюдо", "Кокот", "100 грамм", new BigDecimal("8.50"),
            LocalDateTime.now());
    public static final MenuDetails MENU_DETAILS_BLACK_PEARL_TODAY_4 = new MenuDetails(MENU_DETAILS_ID + 15, RESTAURANT_BLACK_PEARL,
            "Четвертое блюдо", "Чай", "250 грамм", new BigDecimal("2.00"),
            LocalDateTime.now());

    // yesterday
    public static final MenuDetails MENU_DETAILS_STAR_YESTERDAY_1 = new MenuDetails(MENU_DETAILS_ID + 16, RESTAURANT_STAR,
            "Первое блюдо", "Гороховый суп", "250 грамм", new BigDecimal("6.00"), LocalDateTime.now());
    public static final MenuDetails MENU_DETAILS_STAR_YESTERDAY_2 = new MenuDetails(MENU_DETAILS_ID + 17, RESTAURANT_STAR,
            "Второе блюдо", "шашлык", "200 грамм", new BigDecimal("28.00"),
            LocalDateTime.now());
    public static final MenuDetails MENU_DETAILS_STAR_YESTERDAY_3 = new MenuDetails(MENU_DETAILS_ID + 18, RESTAURANT_STAR,
            "Третье блюдо", "Вино красное", "100 грамм", new BigDecimal("9.50"),
            LocalDateTime.now());

    public static final MenuDetails MENU_DETAILS_PEARL_YESTERDAY_1 = new MenuDetails(MENU_DETAILS_ID + 19, RESTAURANT_PEARL,
            "Первое блюдо", "Суп с тефтелями", "250 грамм", new BigDecimal("10.00"),
            LocalDateTime.now());
    public static final MenuDetails MENU_DETAILS_PEARL_YESTERDAY_2 = new MenuDetails(MENU_DETAILS_ID + 20, RESTAURANT_PEARL,
            "Второе блюдо", "Говядина", "200 грамм", new BigDecimal("12.00"),
            LocalDateTime.now());
    public static final MenuDetails MENU_DETAILS_PEARL_YESTERDAY_3 = new MenuDetails(MENU_DETAILS_ID + 21, RESTAURANT_PEARL,
            "Третье блюдо", "Чай", "100 грамм", new BigDecimal("1.50"),
            LocalDateTime.now());

    public static final MenuDetails MENU_DETAILS_SUPER_STAR_YESTERDAY_1 = new MenuDetails(MENU_DETAILS_ID + 22, RESTAURANT_SUPER_STAR,
            "Первое блюдо", "Лапша", "250 грамм", new BigDecimal("7.00"),
            LocalDateTime.now());

    public static final MenuDetails MENU_DETAILS_SUPER_STAR_YESTERDAY_2 = new MenuDetails(MENU_DETAILS_ID + 23, RESTAURANT_SUPER_STAR,
            "Второе блюдо", "Пельмени", "200 грамм", new BigDecimal("10.00"),
            LocalDateTime.now());

    public static final MenuDetails MENU_DETAILS_SUPER_STAR_YESTERDAY_3 = new MenuDetails(MENU_DETAILS_ID + 24, RESTAURANT_SUPER_STAR,
            "Третье блюдо", "Сок", "100 грамм", new BigDecimal("3.50"),
            LocalDateTime.now());
    // Black Pearl
    public static final MenuDetails MENU_DETAILS_BLACK_PEARL_YESTERDAY_1 = new MenuDetails(MENU_DETAILS_ID + 25, RESTAURANT_BLACK_PEARL,
            "Первое блюдо", "Суп", "250 грамм", new BigDecimal("6.00"),
            LocalDateTime.now());

    public static final MenuDetails MENU_DETAILS_BLACK_PEARL_YESTERDAY_2 = new MenuDetails(MENU_DETAILS_ID + 26, RESTAURANT_BLACK_PEARL,
            "Второе блюдо", "Макароны", "200 грамм", new BigDecimal("8.00"),
            LocalDateTime.now());

    public static final MenuDetails MENU_DETAILS_BLACK_PEARL_YESTERDAY_3 = new MenuDetails(MENU_DETAILS_ID + 27, RESTAURANT_BLACK_PEARL,
            "Третье блюдо", "Мороженое", "100 грамм", new BigDecimal("6.50"), LocalDateTime.now());




       public static final List<MenuDetails> MENU_DETAILS_LIST = List.of(MENU_DETAILS_STAR_TODAY_1,
               MENU_DETAILS_STAR_TODAY_2,MENU_DETAILS_STAR_TODAY_3,MENU_DETAILS_STAR_TODAY_4,

               MENU_DETAILS_PEARL_TODAY_1,MENU_DETAILS_PEARL_TODAY_2,MENU_DETAILS_PEARL_TODAY_3, MENU_DETAILS_PEARL_TODAY_4,

               MENU_DETAILS_SUPER_STAR_TODAY_1,MENU_DETAILS_SUPER_STAR_TODAY_2,MENU_DETAILS_SUPER_STAR_TODAY_3,MENU_DETAILS_SUPER_STAR_TODAY_4,

               MENU_DETAILS_BLACK_PEARL_TODAY_1,MENU_DETAILS_BLACK_PEARL_TODAY_2,MENU_DETAILS_BLACK_PEARL_TODAY_3,MENU_DETAILS_BLACK_PEARL_TODAY_4,

               MENU_DETAILS_STAR_YESTERDAY_1,MENU_DETAILS_STAR_YESTERDAY_2,MENU_DETAILS_STAR_YESTERDAY_3,

               MENU_DETAILS_PEARL_YESTERDAY_1,MENU_DETAILS_PEARL_YESTERDAY_2,MENU_DETAILS_PEARL_YESTERDAY_3,

               MENU_DETAILS_SUPER_STAR_YESTERDAY_1,MENU_DETAILS_SUPER_STAR_YESTERDAY_2,MENU_DETAILS_SUPER_STAR_YESTERDAY_3,

               MENU_DETAILS_BLACK_PEARL_YESTERDAY_1,MENU_DETAILS_BLACK_PEARL_YESTERDAY_2,MENU_DETAILS_BLACK_PEARL_YESTERDAY_3


              );

        public static void assertMatch(MenuDetails actual, MenuDetails expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "restaurant", "dateTime");
    }

    public static void assertMatch(Iterable<MenuDetails> actual, MenuDetails... expected) {
        assertMatch(actual, List.of(expected));
    }


    public static void assertMatch(Iterable<MenuDetails> actual, Iterable<MenuDetails> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("restaurant", "dateTime").isEqualTo(expected);
    }


    public static void assertMatchTo(MenuDetailTo actual, MenuDetailTo expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "restaurant", "dateTime");
    }

    public static void assertMatchTo(Iterable<MenuDetailTo> actual, Iterable<MenuDetailTo> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("restaurant", "dateTime").isEqualTo(expected);
    }
    public static ResultMatcher contentJson(MenuDetails... expected) {
        return contentJson(List.of(expected));
    }

    public static ResultMatcher contentJson(Iterable<MenuDetails> expected) {
        return result -> assertThat(readListFromJsonMvcResult(result, MenuDetails.class)).isEqualTo(expected);
    }

    public static ResultMatcher contentJsonTo(Iterable<MenuDetailTo> expected) {
        return result -> assertThat(readListFromJsonMvcResult(result, MenuDetailTo.class)).isEqualTo(expected);
    }



}
