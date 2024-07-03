package practice.hhpluslectures.infrastructure.lectures.repository;

import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import practice.hhpluslectures.infrastructure.lectures.entity.LecturesEntity;
import org.springframework.transaction.annotation.Transactional;
import practice.hhpluslectures.infrastructure.lectures.entity.LecturesScheduleAccountEntity;
import practice.hhpluslectures.infrastructure.lectures.entity.LecturesScheduleAccountHistoryEntity;
import practice.hhpluslectures.infrastructure.lectures.entity.LecturesScheduleEntity;
import practice.hhpluslectures.service.lectures.LecturesRepository;
import practice.hhpluslectures.service.lectures.domain.Lectures;
import practice.hhpluslectures.service.lectures.domain.LecturesSchedule;
import practice.hhpluslectures.service.lectures.domain.LecturesScheduleAccount;
import practice.hhpluslectures.service.lectures.domain.LecturesScheduleAccountHistory;

@Repository
@RequiredArgsConstructor
public class LecturesRepositoryImpl implements LecturesRepository {

  private final LecturesScheduleJpaRepository lecturesScheduleJpaRepository;
  private final LecturesJpaRepository lecturesJpaRepository;
  private final LecturesScheduleAccountHistoryJpaRepository lecturesScheduleAccountHistoryJpaRepository;
  private final LecturesScheduleAccountJpaRepository lecturesScheduleAccountJpaRepository;


  @Override
  public List<LecturesSchedule> getLecturesList() {
    return lecturesScheduleJpaRepository.findAllByOpenAtLessThan(LocalDateTime.now())
        .stream()
        .map(LecturesScheduleEntity::toDomain)
        .toList();
  }

  @Override
  public List<LecturesScheduleAccountHistory> getLectureSchedulerAccountHistoryList(Long accountId) {
    return lecturesScheduleAccountHistoryJpaRepository.findAllByAccountId(accountId)
        .stream()
        .map(LecturesScheduleAccountHistoryEntity::toDomain)
        .toList();
  }

  @Override
  @Transactional
  public LecturesSchedule getLectureSchedulerByLectureSchedulerId(Long lecturesScheduleId) {
    return lecturesScheduleJpaRepository.findByIdWithPessimisticLock(lecturesScheduleId)
        .orElseThrow(() -> new IllegalArgumentException("특강을 찾을 수 없습니다."))
        .toDomain();
  }

  @Override
  public LecturesScheduleAccount registerLecture(Long accountId, Long lecturesScheduleId) {
    return lecturesScheduleAccountJpaRepository.save(new LecturesScheduleAccountEntity(lecturesScheduleId, accountId)).toDomain();
  }

  @Override
  public LecturesScheduleAccountHistory saveLecturesScheduleAccountHistory(LecturesScheduleAccountHistory lecturesScheduleAccountHistory) {
    return lecturesScheduleAccountHistoryJpaRepository.save(
        new LecturesScheduleAccountHistoryEntity(
            lecturesScheduleAccountHistory.isSuccess(),
            lecturesScheduleAccountHistory.lecturesScheduleId(),
            lecturesScheduleAccountHistory.accountId()
        )
    ).toDomain();
  }

  @Override
  public Integer updateLectureSchedulerByLectureSchedulerCurrentCapacity(Long lecturesScheduleId) {
    return lecturesScheduleJpaRepository.updateCurrentCapacity(lecturesScheduleId);
  }

  @Override
  public Lectures saveLectures(String url) {
    return lecturesJpaRepository.save(new LecturesEntity(url)).toDomain();
  }

  @Override
  public LecturesSchedule saveLecturesSchedule(LecturesSchedule lecturesSchedule) {
   return lecturesScheduleJpaRepository.save(
        new LecturesScheduleEntity(
            lecturesSchedule.openAt(),
            lecturesSchedule.title(),
            lecturesSchedule.currentCapacity(),
            lecturesSchedule.lecturesId()
        )
    ).toDomain();
  }

  @Override
  public boolean existsLectureSchedulerAccountByAccountIdAndLecturesScheduleId(Long accountId, Long lecturesScheduleId) {
    return lecturesScheduleAccountJpaRepository.existsByAccountIdAndLecturesScheduleId(accountId, lecturesScheduleId);
  }
}
