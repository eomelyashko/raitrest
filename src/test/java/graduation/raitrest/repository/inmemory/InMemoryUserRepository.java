package graduation.raitrest.repository.inmemory;

import graduation.raitrest.UserTestData;
import graduation.raitrest.model.entities.User;
import graduation.raitrest.repository.UserRepository;
import org.springframework.stereotype.Repository;


import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static graduation.raitrest.UserTestData.ADMIN;
import static graduation.raitrest.UserTestData.USER;


@Repository
public class InMemoryUserRepository extends InMemoryBaseRepository<User> implements UserRepository {

    public void init() {
        entryMap.clear();
        entryMap.put(UserTestData.USER_ID, USER);
        entryMap.put(UserTestData.ADMIN_ID, ADMIN);
    }

    @Override
    public List<User> getAll() {
        return getCollection().stream()
                .sorted(Comparator.comparing(User::getName).thenComparing(User::getEmail))
                .collect(Collectors.toList());
    }

    @Override
    public User getByEmail(String email) {
        Objects.requireNonNull(email, "email must not be null");
        return getCollection().stream()
                .filter(u -> email.equals(u.getEmail()))
                .findFirst()
                .orElse(null);
    }
}