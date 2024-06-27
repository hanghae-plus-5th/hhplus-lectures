package practice.hhpluslectures.controller.lectures;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import practice.hhpluslectures.service.lectures.LecturesService;
import practice.hhpluslectures.service.lectures.domain.LecturesSchedule;
import practice.hhpluslectures.service.lectures.domain.LecturesScheduleAccountHistory;

@RestController
@RequiredArgsConstructor
@RequestMapping("/lectures")
public class LecturesController {

  private final LecturesService lectureRegistered;

  @PostMapping("/schedule/{lectures-schedule-id}/account/{account-id}")
  public Boolean registerLecture(@PathVariable("account-id") Long accountId, @PathVariable("lectures-schedule-id") Long lecturesScheduleId) {
    return lectureRegistered.registerLecture(accountId, lecturesScheduleId);
  }

  @GetMapping("/schedule/history/account/{account-id}")
  public List<LecturesScheduleAccountHistory> getLectureSchedulerAccountHistoryList(@PathVariable("account-id") Long accountId) {
    return lectureRegistered.getLectureSchedulerAccountHistoryList(accountId);
  }

  @GetMapping("/schedule")
  public List<LecturesSchedule> getLecturesScheduleList() {
    return lectureRegistered.getLecturesList();
  }
}
