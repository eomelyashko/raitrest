package graduation.raitrest;

import graduation.raitrest.model.entities.Role;
import graduation.raitrest.model.entities.User;
import graduation.raitrest.web.user.AdminRestController;

import org.springframework.context.support.GenericXmlApplicationContext;


import java.util.Arrays;

import static graduation.raitrest.TestUtil.mockAuthorize;
import static graduation.raitrest.UserTestData.USER;

public class SpringMain {
    public static void main(String[] args) {
        // java 7 automatic resource management
        try (GenericXmlApplicationContext appCtx = new GenericXmlApplicationContext()) {
         //   appCtx.getEnvironment().setActiveProfiles(Profiles.getActiveDbProfile(), Profiles.REPOSITORY_IMPLEMENTATION);
         //   appCtx.load("classpath:spring/inmemory.xml","classpath:spring/spring-app.xml");
            appCtx.load("classpath:spring/inmemory.xml");
            appCtx.refresh();
            mockAuthorize(USER);
            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            System.out.println(adminUserController.getAll());
//            adminUserController.create(new User(null, "userName", "email@mail.ru", "password", Role.ROLE_ADMIN));

            System.out.println(adminUserController.getAll());

//            MealRestController mealController = appCtx.getBean(MealRestController.class);
//            List<MealTo> filteredMealsWithExcess =
//                    mealController.getBetween(
//                            LocalDate.of(2015, Month.MAY, 30), LocalTime.of(7, 0),
//                            LocalDate.of(2015, Month.MAY, 31), LocalTime.of(11, 0));
//            filteredMealsWithExcess.forEach(System.out::println);
        }
    }
}
