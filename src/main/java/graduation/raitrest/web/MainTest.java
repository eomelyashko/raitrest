package graduation.raitrest.web;

import graduation.raitrest.model.entities.Role;
import graduation.raitrest.model.entities.User;
import graduation.raitrest.web.user.AdminRestController;
import org.slf4j.Logger;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import java.util.Arrays;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;


public class MainTest {
     private static final Logger log = getLogger(MainTest.class);
    public static void main(String[] args) {
        try (ConfigurableApplicationContext appCtx = new GenericXmlApplicationContext();) {
            log.info("HELLO");
          //  appCtx.getEnvironment().setActiveProfiles(Profiles.DATAJPA,Profiles.POSTGRES_DB);
            ((GenericXmlApplicationContext) appCtx).load("spring/spring-app.xml", "spring/spring-db.xml");
            appCtx.refresh();

            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
//            adminUserController.create(new User(null, "userName", "email@mail.ru", "password", Role.ROLE_ADMIN));
            adminUserController.getAll();
            System.out.println();
            List<User> all = adminUserController.getAll();
            System.out.println(all);

//            MealRestController mealController = appCtx.getBean(MealRestController.class);
//            List<MealTo> filteredMealsWithExcess =
//                    mealController.getBetween(
//                            LocalDate.of(2015, Month.MAY, 30), LocalTime.of(7, 0),
//                            LocalDate.of(2015, Month.MAY, 31), LocalTime.of(11, 0));
//            filteredMealsWithExcess.forEach(System.out::println);
        }
    }
}
