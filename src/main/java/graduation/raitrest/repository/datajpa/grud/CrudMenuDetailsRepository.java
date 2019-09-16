package graduation.raitrest.repository.datajpa.grud;

import graduation.raitrest.model.entities.MenuDetails;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;


import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Transactional(readOnly = true)
public interface CrudMenuDetailsRepository extends JpaRepository<MenuDetails, Integer> {

    @Transactional
    @Modifying
    @Query("DELETE FROM MenuDetails md WHERE md.id=:id")
    int delete(@Param("id") int id);

    @Transactional
    @Modifying
    @Query("DELETE FROM MenuDetails md WHERE md.id=?1 and md.restaurant.id in" +
            " (select r.id from Restaurant r  where  r.id=md.restaurant.id and r.manager.id = ?2)")
    int delete(int id, int managerId);

    @Query("SELECT md FROM MenuDetails md left join fetch md.restaurant where md.restaurant.manager.id=?2 and md.id=?1")
    MenuDetails getWithManager(int id, int managerId);

//    @Query("SELECT md FROM MenuDetails md left join fetch md.restaurant where md.restaurant.manager.id=?1")
//    List<MenuDetails> getAllWithManager(int managerId);

//    @Query("SELECT md FROM MenuDetails md left join fetch md.restaurant where md.restaurant.id=?1")
//    List<MenuDetails> getAllWithRestaurant(int restaurantId);

    @SuppressWarnings("JpaQlInspection")
    @EntityGraph(attributePaths = {"restaurant"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT md FROM MenuDetails md WHERE  md.dateTime BETWEEN :startDate AND :endDate")
    List<MenuDetails> getAllByDateTime(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @SuppressWarnings("JpaQlInspection")
    @EntityGraph(attributePaths = {"restaurant"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT md FROM MenuDetails md WHERE md.restaurant.id=:restaurantID and md.dateTime BETWEEN :startDate AND :endDate ")
    List<MenuDetails> getAllByDateTimeByRestaurantId(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate, @Param("restaurantID") int restaurantID);

    @SuppressWarnings("JpaQlInspection")
    @EntityGraph(attributePaths = {"restaurant"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT md FROM MenuDetails md WHERE md.restaurant.manager.id=:managerId and md.dateTime BETWEEN :startDate AND :endDate ")
    List<MenuDetails> getAllByDateTimeByManagerId(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate, @Param("managerId") int managerId);

    @SuppressWarnings("JpaQlInspection")
    @EntityGraph(attributePaths = {"restaurant"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT md FROM MenuDetails md WHERE md.restaurant.id=:restaurantID and md.restaurant.manager.id=:managerId  and md.dateTime BETWEEN :startDate AND :endDate ")
    List<MenuDetails> getAllByDateTimeByRestaurantIdAndManagerId(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate, @Param("restaurantID") int restaurantID, @Param("managerId") int managerId);
}
