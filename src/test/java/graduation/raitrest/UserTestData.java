package graduation.raitrest;

import graduation.raitrest.model.entities.Role;
import graduation.raitrest.model.entities.User;
import graduation.raitrest.web.json.JsonUtil;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static graduation.raitrest.TestUtil.readFromJsonMvcResult;
import static graduation.raitrest.TestUtil.readListFromJsonMvcResult;
import static graduation.raitrest.model.AbstractBaseEntity.START_SEQ;
import static org.assertj.core.api.Assertions.assertThat;

public class UserTestData {
    public static final int USER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ + 1;
    public static final int MANAGER_ID = START_SEQ + 2;
    public static final int USER_1_ID = START_SEQ + 3;
    public static final int USER_2_ID = START_SEQ + 4;
    public static final int USER_3_ID = START_SEQ + 5;
//    public static final int USER_4_ID = START_SEQ + 6;
//    public static final int USER_5_ID = START_SEQ + 7;
//    public static final int USER_6_ID = START_SEQ + 8;
//    public static final int USER_7_ID = START_SEQ + 9;
//    public static final int USER_8_ID = START_SEQ + 10;
//    public static final int USER_9_ID = START_SEQ + 11;
    public static final int MANAGER_1_ID = START_SEQ + 6; // !!!
    public static final int MANAGER_2_ID = START_SEQ + 7;
    public static final int MANAGER_3_ID = START_SEQ + 8;
//    public static final int MANAGER_3_ID = START_SEQ + 1;

    public static final User USER = new User(USER_ID, "User", "user@yandex.ru", "password", Role.ROLE_USER);
    public static final User ADMIN = new User(ADMIN_ID, "Admin", "admin@gmail.com", "admin", Role.ROLE_ADMIN);
    public static final User MANAGER = new User(MANAGER_ID, "Manager", "manager@yandex.ru", "manager", Role.ROLE_MANAGER);
    public static final User USER_1 = new User(USER_1_ID, "User_1", "user_1@yandex.ru", "password1", Role.ROLE_USER);
    public static final User USER_2 = new User(USER_2_ID, "User_2", "user_2@yandex.ru", "password2", Role.ROLE_USER);
    public static final User USER_3 = new User(USER_3_ID, "User_3", "user_3@yandex.ru", "password3", Role.ROLE_USER);
//    public static final User USER_4 = new User(USER_4_ID, "User_4", "user_4@yandex.ru", "password4", Role.ROLE_USER);
//    public static final User USER_5 = new User(USER_5_ID, "User_5", "user_5@yandex.ru", "password5", Role.ROLE_USER);
//    public static final User USER_6 = new User(USER_6_ID, "User_6", "user_6@yandex.ru", "password6", Role.ROLE_USER);
//    public static final User USER_7 = new User(USER_7_ID, "User_7", "user_7@yandex.ru", "password7", Role.ROLE_USER);
//    public static final User USER_8 = new User(USER_8_ID, "User_8", "user_8@yandex.ru", "password8", Role.ROLE_USER);
//    public static final User USER_9 = new User(USER_9_ID, "User_9", "user_9@yandex.ru", "password9", Role.ROLE_USER);
    public static final User MANAGER_1 = new User(MANAGER_1_ID, "Manager_1", "manager_1@yandex.ru", "1password", Role.ROLE_MANAGER);
    public static final User MANAGER_2 = new User(MANAGER_2_ID, "Manager_2", "manager_2@yandex.ru", "2password", Role.ROLE_MANAGER);
    public static final User MANAGER_3 = new User(MANAGER_3_ID, "Manager_3", "manager_3@yandex.ru", "3password", Role.ROLE_MANAGER);

 //   public static  List<User> listUsers = Stream.of(USER, ADMIN, MANAGER, USER_1, USER_2, USER_3, USER_4, USER_5, USER_6, USER_7, USER_8, USER_9, MANAGER_1, MANAGER_2, MANAGER_3).collect(Collectors.toList());
  //  public static  final List<User> listUsers = List.of(USER, ADMIN, MANAGER, USER_1, USER_2, USER_3, USER_4, USER_5, USER_6, USER_7, USER_8, USER_9, MANAGER_1, MANAGER_2, MANAGER_3);
    public static  final List<User> listUsers = List.of(USER, ADMIN, MANAGER, USER_1, USER_2, USER_3,  MANAGER_1, MANAGER_2, MANAGER_3);


    public static void assertMatch(User actual, User expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "registered","restaurants","password");
    }

    public static void assertMatch(Iterable<User> actual, User... expected) {
        assertMatch(actual, List.of(expected));
    }

    public static void assertMatch(Iterable<User> actual, Iterable<User> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("registered","restaurants","password").isEqualTo(expected);
    }
    public static ResultMatcher contentJson(User... expected) {
        return result -> assertMatch(readListFromJsonMvcResult(result, User.class), List.of(expected));
    }

    public static ResultMatcher contentJson(Iterable<User> expected) {
        return result -> assertMatch(readListFromJsonMvcResult(result, User.class),expected);
    }


    public static ResultMatcher contentJson(User expected) {
        return result -> assertMatch(readFromJsonMvcResult(result, User.class), expected);
    }
    public static String jsonWithPassword(User user, String passw) {
        return JsonUtil.writeAdditionProps(user, "password", passw);
    }
}
