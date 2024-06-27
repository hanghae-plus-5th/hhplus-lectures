package practice.hhpluslectures.infrastructure.lectures.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import practice.hhpluslectures.infrastructure.lectures.entity.LecturesScheduleAccountEntity;

@Repository
public interface LecturesScheduleAccountJpaRepository extends JpaRepository<LecturesScheduleAccountEntity, Long> {

  boolean existsByAccountIdAndLecturesScheduleId(Long accountId, Long lecturesScheduleId);
}
