package graduation.raitrest.repository.datajpa.grud;


import graduation.raitrest.model.entities.User;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Transactional(readOnly = true)
public interface CrudUserRepository extends JpaRepository<User, Integer> {
    @Transactional
    @Modifying
    //    @Query(name = User.DELETE)
    @Query("DELETE FROM User u WHERE u.id=:id")
    int delete(@Param("id") int id);

    User getByEmail(String email);

    @EntityGraph(attributePaths = {"roles"},type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT u FROM User u  WHERE u.id=?1")
    User getWithRole(int id);

    @Override
    @Query("SELECT distinct  u FROM User u left join FETCH u.roles")
    List<User> findAll(Sort sort);


}
