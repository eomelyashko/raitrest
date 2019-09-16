package graduation.raitrest.repository.datajpa;

import graduation.raitrest.model.entities.Restaurant;
import graduation.raitrest.repository.RestaurantRepository;
import graduation.raitrest.repository.datajpa.grud.CrudRestaurantRepository;
import graduation.raitrest.repository.datajpa.grud.CrudUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DataJpaRestaurantRepository implements RestaurantRepository {

    @Autowired
    private CrudRestaurantRepository crudRestaurantRepository;

    @Autowired
    private CrudUserRepository crudUserRepository;

//    @Override
//    public Restaurant save(Restaurant restaurant) {
//        return crudRestaurantRepository.save(restaurant);
//    }

    @Override
    public Restaurant save(Restaurant restaurant, int managerId) {
        if (!restaurant.isNew() && get(restaurant.getId(), managerId) == null) {
            return null;
        }
        restaurant.setManager(crudUserRepository.getOne(managerId));
        return crudRestaurantRepository.save(restaurant);
    }

    @Override
    public boolean delete(int id) {
        return crudRestaurantRepository.delete(id) != 0;
    }

    @Override
    public boolean delete(int id, int managerId) {
        return crudRestaurantRepository.delete(id, managerId) != 0;
    }

    @Override
    public Restaurant get(int id) {
        return crudRestaurantRepository.findById(id).orElse(null);
    }

    @Override
    public Restaurant get(int id, int userId) {

        return crudRestaurantRepository.findById(id).filter(r -> r.getManager().getId() ==  userId).orElse(null);
    }

    @Override
    public Restaurant getWithUser(int id) {
        return crudRestaurantRepository.getWithUser(id);
    }

    @Override
    public List<Restaurant> getAll() {
        return crudRestaurantRepository.findAll();
    }

    @Override
    public List<Restaurant> getAll(int userId) {
        return crudRestaurantRepository.findAll(userId);
    }
}
