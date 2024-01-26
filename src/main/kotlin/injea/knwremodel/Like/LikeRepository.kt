package injea.knwremodel.Like;

import injea.knwremodel.notice.Notice;
import injea.knwremodel.User.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    Boolean existsByUserAndNotice(User user, Notice notice);
    Like findByUserAndNotice(User user, Notice notice);

    @Query("SELECT DISTINCT n FROM Notice n JOIN n.likes l WHERE l.user.id = :userId")
    List<Notice> findLikedNoticesByUser(@Param("userId") Long userId);

}