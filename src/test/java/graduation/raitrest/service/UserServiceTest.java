package graduation.raitrest.service;

import graduation.raitrest.model.entities.Role;
import graduation.raitrest.model.entities.User;
import graduation.raitrest.util.JpaUtil;

import graduation.raitrest.util.exception.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.dao.DataAccessException;

import javax.validation.ConstraintViolationException;
import java.util.*;
import java.util.stream.Collectors;

import static graduation.raitrest.UserTestData.*;
import static org.junit.jupiter.api.Assertions.*;


public class UserServiceTest extends AbstractServiceTest {

    @Autowired
    protected UserService service;
    @Autowired
    protected JpaUtil jpaUtil;
    @Autowired
    private CacheManager cacheManager;

    @BeforeEach
     void setUp() throws Exception {
        cacheManager.getCache("users").clear();
        jpaUtil.clear2ndLevelHibernateCache();
    }

    @Test
     void create() throws Exception {
        User newUser = new User(null, "New", "new@gmail.com", "newPass", false, new Date(), Collections.singleton(Role.ROLE_USER));
        User created = service.create(newUser);
        newUser.setId(created.getId());
        assertMatch(created, newUser);

        List<User> collect = listUsers.stream().collect(Collectors.toList());
        collect.add(newUser);
        collect.sort(Comparator.comparing(User::getName));
        assertMatch(service.getAll(), collect);
    }

    @Test
     void duplicateMailCreate() throws Exception {
        assertThrows(DataAccessException.class, () ->
        service.create(new User(null, "Duplicate", "user@yandex.ru", "newPass", Role.ROLE_USER)));
    }

    @Test
     void delete() throws Exception {
        service.delete(USER_ID);
        List<User> collect = listUsers.stream()
                .filter(user -> user.getId() != USER_ID)
                .sorted(Comparator.comparing(User::getName)).collect(Collectors.toList());
        assertMatch(service.getAll(), collect);
    }

    @Test
     void deletedNotFound() throws Exception {
        assertThrows(NotFoundException.class, () ->
        service.delete(1));
    }

    @Test
     void get() throws Exception {
        User user = service.get(USER_ID);
        assertMatch(user, USER);
        User admin = service.get(ADMIN_ID);
        assertMatch(admin, ADMIN);
        User manager = service.get(MANAGER_ID);
        assertMatch(manager, MANAGER);
        User user1 = service.get(MANAGER_3_ID);
        assertMatch(user1, MANAGER_3);

    }

    @Test
     void getNotFound() throws Exception {
        assertThrows(NotFoundException.class, () ->
        service.get(1));
    }

    @Test
     void getByEmail() throws Exception {
        User user = service.getByEmail("user@yandex.ru");
        assertMatch(user, USER);
    }

    @Test
     void update() throws Exception {
        User updated = new User(USER);
        updated.setName("UpdatedName");

        service.update(updated);
        assertMatch(service.get(USER_ID), updated);
        // меняем роль
        updated.setRoles(EnumSet.of(Role.ROLE_ADMIN));
        service.update(updated);
        assertMatch(service.get(USER_ID), updated);
        // две роли
        updated.setRoles(EnumSet.of(Role.ROLE_USER, Role.ROLE_ADMIN));
        service.update(updated);
        assertMatch(service.get(USER_ID), updated);
        // и снова одна роль
        updated.setRoles(EnumSet.of(Role.ROLE_USER));
        service.update(updated);
        assertMatch(service.get(USER_ID), updated);
    }

    @Test
     void getAll()  {
        List<User> all = service.getAll();
        List<User> collect = listUsers.stream().sorted(Comparator.comparing(User::getName)).collect(Collectors.toList());
        assertMatch(all, collect);
    }

    @Test
    void enable() {
        service.enable(USER_ID, false);
        assertFalse(service.get(USER_ID).isEnabled());
        service.enable(USER_ID, true);
        assertTrue(service.get(USER_ID).isEnabled());
    }


    @Test

    public void testValidation() throws Exception {
        validateRootCause(() -> service.create(new User(null, "  ", "mail@yandex.ru", "password", Role.ROLE_USER)), ConstraintViolationException.class);
        validateRootCause(() -> service.create(new User(null, "User", "  ", "password", Role.ROLE_USER)), ConstraintViolationException.class);
        validateRootCause(() -> service.create(new User(null, "User", "mail@yandex.ru", "  ", Role.ROLE_USER)), ConstraintViolationException.class);
        validateRootCause(() -> service.create(new User(null, "User", "mail@yandex.ru", "password", true, null, Collections.emptySet())), ConstraintViolationException.class);
      //     validateRootCause(() -> service.create(new User(null, "User", "mail@yandex.ru", "password",  true, new Date(), null)), ConstraintViolationException.class);
    }
}