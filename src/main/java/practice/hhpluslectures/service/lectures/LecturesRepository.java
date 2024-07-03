package practice.hhpluslectures.service.lectures;

import java.util.List;
import practice.hhpluslectures.service.lectures.domain.LecturesSchedule;
import practice.hhpluslectures.service.lectures.domain.LecturesScheduleAccount;
import practice.hhpluslectures.service.lectures.domain.LecturesScheduleAccountHistory;

public interface LecturesRepository {

  List<LecturesSchedule> getLecturesList();

  List<LecturesScheduleAccountHistory> getLectureSchedulerAccountHistoryList(Long accountId);

  LecturesSchedule getLectureSchedulerByLectureSchedulerId(Long lecturesScheduleId);

  LecturesScheduleAccount registerLecture(Long accountId, Long lecturesScheduleId);

  LecturesScheduleAccountHistory saveLecturesScheduleAccountHistory(LecturesScheduleAccountHistory lecturesScheduleAccountHistory);

  Integer updateLectureSchedulerByLectureSchedulerCurrentCapacity(Long lecturesScheduleId);

  boolean existsLectureSchedulerAccountByAccountIdAndLecturesScheduleId(Long accountId, Long lecturesScheduleId);
}
