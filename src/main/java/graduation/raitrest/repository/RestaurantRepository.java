package graduation.raitrest.repository;


import graduation.raitrest.model.entities.Restaurant;

import java.util.List;

public interface RestaurantRepository {
    // null if not found, when updated
   Restaurant save(Restaurant restaurant, int userId);

    // false if not found
    boolean delete(int id);

    boolean delete(int id, int userId);

    // null if not found
    Restaurant get(int id);

    Restaurant get(int id, int userId);

    Restaurant getWithUser(int id);

    List<Restaurant> getAll();

    List<Restaurant> getAll(int userId);
}