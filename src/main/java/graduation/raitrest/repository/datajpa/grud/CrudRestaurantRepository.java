package graduation.raitrest.repository.datajpa.grud;

import graduation.raitrest.model.entities.Restaurant;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface CrudRestaurantRepository extends JpaRepository<Restaurant, Integer> {
    @Transactional
    @Modifying
    @Query("DELETE FROM Restaurant r WHERE r.id=:id")
    int delete(@Param("id") int id);

    @Modifying
    @Transactional
    @Query("DELETE FROM Restaurant r WHERE r.id=:id AND r.manager.id=:userId")
    int delete(@Param("id") int id, @Param("userId") int userId);


    @Override
    @Query("SELECT distinct  r FROM Restaurant r left join FETCH r.manager")
    List<Restaurant> findAll();


    @Query("SELECT distinct  r FROM Restaurant r left join FETCH r.manager where r.manager.id = :user_id")
    List<Restaurant> findAll(@Param("user_id") int user_id);

    @EntityGraph(attributePaths = {"manager"},type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT u FROM Restaurant u WHERE u.id=?1")
    Restaurant getWithUser(int id);
}
