package practice.hhpluslectures.infrastructure.lectures.repository;

import jakarta.persistence.LockModeType;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import practice.hhpluslectures.infrastructure.lectures.entity.LecturesScheduleEntity;

@Repository
public interface LecturesScheduleJpaRepository extends JpaRepository<LecturesScheduleEntity, Long> {

  List<LecturesScheduleEntity> findAllByOpenAtLessThan(LocalDateTime now);

  @Modifying
  @Query("UPDATE LecturesScheduleEntity SET currentCapacity = currentCapacity + 1 where id = :id")
  Integer updateCurrentCapacity(Long id);

  @Lock(LockModeType.PESSIMISTIC_WRITE)
  @Query("SELECT L from LecturesScheduleEntity L where L.id = :id")
  Optional<LecturesScheduleEntity> findByIdWithPessimisticLock(Long id);
}
