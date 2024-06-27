package practice.hhpluslectures.infrastructure.lectures.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import practice.hhpluslectures.infrastructure.lectures.entity.LecturesScheduleAccountHistoryEntity;

@Repository
public interface LecturesScheduleAccountHistoryJpaRepository extends JpaRepository<LecturesScheduleAccountHistoryEntity, Long> {

  List<LecturesScheduleAccountHistoryEntity> findAllByAccountId(Long accountId);
}
