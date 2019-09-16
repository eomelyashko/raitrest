package graduation.raitrest.repository.datajpa.grud;

import graduation.raitrest.model.entities.Vote;
import graduation.raitrest.model.to.Rating;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Transactional(readOnly = true)
public interface CrudVoteRepository extends JpaRepository<Vote, Integer> {
    @Transactional
    @Modifying
    @Query("DELETE FROM Vote v WHERE v.id=:id")
    int delete(@Param("id") int id);

    @Modifying
    @Transactional
    @Query("DELETE FROM Vote v WHERE v.id=:id AND v.user.id=:userId")
    int delete(@Param("id") int id, @Param("userId") int userId);

    @EntityGraph(attributePaths = {"user","restaurant"},type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT v FROM Vote v  WHERE v.id=?1 and v.user.id=?2")
    Vote getWithRestaurantAndUser(int id,int userId);

    @Query("SELECT v FROM Vote v WHERE v.user.id=:userId ORDER BY v.dateTime DESC")
    List<Vote> getAll(@Param("userId") int userId);

    @SuppressWarnings("JpaQlInspection")
    @Query("SELECT v from Vote v WHERE v.user.id=:userId AND v.dateTime BETWEEN :startDate AND :endDate ORDER BY v.dateTime DESC")
    List<Vote> getFilterByDateTime(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate, @Param("userId") int userId);

    @SuppressWarnings("JpaQlInspection")
    @Query("SELECT v from Vote v WHERE  v.dateTime BETWEEN :startDate AND :endDate ORDER BY v.dateTime DESC")
    List<Vote> getFilterByDateTime(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

   // https://stackoverflow.com/questions/36328063/how-to-return-a-custom-object-from-a-spring-data-jpa-group-by-query
     @SuppressWarnings("JpaQlInspection")
    @Query("SELECT new graduation.raitrest.model.to.Rating( v.restaurant.id, v.restaurant.name, count(v) )" +
            "from Vote v WHERE  v.dateTime BETWEEN :startDate AND :endDate GROUP BY v.restaurant.id, v.restaurant.name ORDER BY count(v) DESC")
    List<Rating> getCountVotes(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);


}
