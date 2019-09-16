package graduation.raitrest.repository.datajpa;

import graduation.raitrest.model.entities.Restaurant;
import graduation.raitrest.model.entities.Vote;
import graduation.raitrest.model.to.Rating;
import graduation.raitrest.repository.VoteRepository;
import graduation.raitrest.repository.datajpa.grud.CrudRestaurantRepository;
import graduation.raitrest.repository.datajpa.grud.CrudUserRepository;
import graduation.raitrest.repository.datajpa.grud.CrudVoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class DataJpaVoteRepository implements VoteRepository {

    @Autowired
    private CrudVoteRepository crudVoteRepository;
    @Autowired
    private  CrudRestaurantRepository crudRestaurantRepository;
    @Autowired
    private CrudUserRepository crudUserRepository;

    @Override
    public Vote save(Vote vote, int userId) {
        if (!vote.isNew() && get(vote.getId(), userId) == null) {
            return null;
        }

      if (vote.getRestaurant() == null) return null;

        vote.setUser(crudUserRepository.getOne(userId));
        return crudVoteRepository.save(vote);
    }

    @Override
    public Vote save(Vote vote, int restaurantID, int userId) {
        if (!vote.isNew() && get(vote.getId(), userId) == null) {
            return null;
        }

        Restaurant restaurant = crudRestaurantRepository.getOne(restaurantID);
        if (restaurant == null) {
            return  null;
        }
        vote.setRestaurant(restaurant);
        vote.setUser(crudUserRepository.getOne(userId));
        return crudVoteRepository.save(vote);
    }

    @Override
    public boolean delete(int id) {
        return crudVoteRepository.delete(id) != 0;
    }

    @Override
    public boolean delete(int id, int userId) {
        return crudVoteRepository.delete(id,userId) != 0;
    }

    @Override
    public Vote get(int id) {
        return crudVoteRepository.findById(id).orElse(null);
    }

    @Override
    public Vote get(int id, int userId) {
//        return crudVoteRepository.findById(id).filter(vote -> vote.getUser().getId()==userId).orElse(null);
        return crudVoteRepository.getWithRestaurantAndUser(id,userId);
    }

    @Override
    public List<Vote> getAll() {
        return crudVoteRepository.findAll(Sort.by(Sort.Direction.DESC,"dateTime"));
    }

    @Override
    public List<Vote> getAll(int userId) {
        return crudVoteRepository.getAll(userId) ;
    }

    @Override
    public List<Vote> filterByDateTime(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return crudVoteRepository.getFilterByDateTime(startDate,endDate,userId);
    }

    @Override
    public List<Vote> filterByDateTime(LocalDateTime startDate, LocalDateTime endDate) {
        return crudVoteRepository.getFilterByDateTime(startDate,endDate);
    }

    @Override
    public List<Rating> getRating(LocalDateTime startDate, LocalDateTime endDate) {
        return crudVoteRepository.getCountVotes(startDate,endDate);
    }
}
