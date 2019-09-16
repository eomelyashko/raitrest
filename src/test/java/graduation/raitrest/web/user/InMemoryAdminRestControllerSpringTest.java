package graduation.raitrest.web.user;

import graduation.raitrest.UserTestData;
import graduation.raitrest.model.entities.User;
import graduation.raitrest.repository.inmemory.InMemoryUserRepository;
import graduation.raitrest.util.exception.NotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Collection;

import static graduation.raitrest.UserTestData.ADMIN;
import static org.junit.jupiter.api.Assertions.assertThrows;

//Чтобы использовать спринг апп, нужно добавить в инмемори - репозитоории соответсвующие сервисам. сервисы сканиру
//ются через спринг апп
//@ContextConfiguration({"classpath:spring/spring-app.xml", "classpath:spring/inmemory.xml"})
//@SpringJUnitConfig(locations = {"classpath:spring/spring-app.xml", "classpath:spring/inmemory.xml"})
@SpringJUnitConfig(locations = {"classpath:spring/spring-security.xml","classpath:spring/inmemory.xml"})
 class InMemoryAdminRestControllerSpringTest {

    @Autowired
    private AdminRestController controller;

    @Autowired
    private InMemoryUserRepository repository;

    @BeforeEach
     void setUp() {
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
     void deleteNotFound()  {
        assertThrows(NotFoundException.class, () ->
                controller.delete(10));
    }
}