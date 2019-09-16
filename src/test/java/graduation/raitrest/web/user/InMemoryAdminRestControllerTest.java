package graduation.raitrest.web.user;

import graduation.raitrest.UserTestData;
import graduation.raitrest.model.entities.User;
import graduation.raitrest.repository.inmemory.InMemoryUserRepository;
import graduation.raitrest.util.exception.NotFoundException;
import org.junit.jupiter.api.*;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Arrays;
import java.util.Collection;

import static graduation.raitrest.UserTestData.ADMIN;
import static org.junit.jupiter.api.Assertions.assertThrows;

class InMemoryAdminRestControllerTest {
    private static ConfigurableApplicationContext appCtx;
    private static AdminRestController controller;

    @BeforeAll
     static void beforeClass() {
       // appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml", "spring/inmemory.xml");
        appCtx = new ClassPathXmlApplicationContext( "classpath:spring/spring-security.xml","spring/inmemory.xml");
        System.out.println("\n" + Arrays.toString(appCtx.getBeanDefinitionNames()) + "\n");
        controller = appCtx.getBean(AdminRestController.class);
    }

    @AfterAll
     static void afterClass() {
        appCtx.close();
    }

    @BeforeEach
     void setUp() {
        // re-initialize
        InMemoryUserRepository repository = appCtx.getBean(InMemoryUserRepository.class);
        repository.init();
    }

    @Test
     void delete() {
        controller.delete(UserTestData.USER_ID);
        Collection<User> users = controller.getAll();
        Assertions.assertEquals(1, users.size());
        Assertions.assertEquals(ADMIN, users.iterator().next());
    }

    @Test
     void deleteNotFound() {
        assertThrows(NotFoundException.class, () ->
                controller.delete(10));
    }
}