package practice.hhpluslectures.infrastructure.lectures.repository;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import practice.hhpluslectures.infrastructure.lectures.entity.LecturesScheduleEntity;

@Repository
public interface LecturesScheduleJpaRepository extends JpaRepository<LecturesScheduleEntity, Long> {

  List<LecturesScheduleEntity> findAllByOpenAtLessThan(LocalDateTime now);

  @Modifying
  @Query("UPDATE LecturesScheduleEntity SET currentCapacity = currentCapacity + 1 where id = :lecturesScheduleId")
  Integer updateCurrentCapacity(Long lecturesScheduleId);

//  @Lock(LockModeType.PESSIMISTIC_WRITE)
//  Optional<LecturesScheduleEntity> findByIdWithPessimisticLock(Long lecturesScheduleId);
}
