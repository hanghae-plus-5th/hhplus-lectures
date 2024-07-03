package practice.hhpluslectures.service.lectures;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import practice.hhpluslectures.service.account.AccountRepository;
import practice.hhpluslectures.service.account.domain.Account;
import practice.hhpluslectures.service.lectures.domain.LecturesSchedule;
import practice.hhpluslectures.service.lectures.domain.LecturesScheduleAccountHistory;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LecturesService {

  private final LecturesRepository lecturesRepository;
  private final AccountRepository accountRepository;

  /**
   * 강의 목록
   */
  public List<LecturesSchedule> getLecturesList() {
    return lecturesRepository.getLecturesList();
  }

  /**
   * 수강 신청
   */
  @Transactional
  public Boolean registerLecture(Long accountId, Long lecturesScheduleId) {
    Account account = accountRepository.getAccount(accountId);
    LecturesSchedule lecturesSchedule = lecturesRepository.getLectureSchedulerByLectureSchedulerId(lecturesScheduleId);

    if (lecturesRepository.existsLectureSchedulerAccountByAccountIdAndLecturesScheduleId(accountId, lecturesScheduleId)) {
      throw new IllegalArgumentException("이미 신청한 특강입니다.");
    } else if (lecturesSchedule.isMaxCapacity()) {
      saveLecturesScheduleAccountHistory(lecturesSchedule.id(), account.id(), false);
      return false;
    }

    lecturesRepository.registerLecture(accountId, lecturesScheduleId);
    lecturesRepository.updateLectureSchedulerByLectureSchedulerCurrentCapacity(lecturesScheduleId);
    saveLecturesScheduleAccountHistory(lecturesSchedule.id(), account.id(), true);
    return true;
  }

  /**
   * 수강 신청 성공 여부 목록 조회
   */
  public List<LecturesScheduleAccountHistory> getLectureSchedulerAccountHistoryList(Long accountId) {
    accountRepository.getAccount(accountId);
    return lecturesRepository.getLectureSchedulerAccountHistoryList(accountId);
  }

  /**
   * 히스토리 저장
   */
  public LecturesScheduleAccountHistory saveLecturesScheduleAccountHistory(Long lecturesScheduleId, Long accountId, Boolean isSuccess) {
    return lecturesRepository.saveLecturesScheduleAccountHistory(
        LecturesScheduleAccountHistory.createLecturesScheduleAccountHistory(
            null,
            isSuccess,
            lecturesScheduleId,
            accountId)
    );
  }
}
