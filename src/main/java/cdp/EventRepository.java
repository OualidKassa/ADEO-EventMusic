package cdp;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface EventRepository extends Repository<Event, Long> {

    void deleteById(Long eventId);

    List<Event> findAllBy();

    @Modifying
    @Query(value ="UPDATE Event SET nb_stars= :nbStars, comment= :comment WHERE id= :id", nativeQuery = true)
    void updateNbStarsAndComment (@Param("id") Long id, @Param("nbStars") Integer nbStars,@Param("comment") String comment);
}
