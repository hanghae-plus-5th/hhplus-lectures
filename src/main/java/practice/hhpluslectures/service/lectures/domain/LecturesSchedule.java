package practice.hhpluslectures.service.lectures.domain;

import java.time.LocalDateTime;
import lombok.Builder;

public record LecturesSchedule(
    Long id,
    LocalDateTime openAt,
    String title,
    Integer currentCapacity,
    Long lecturesId
) {

  @Builder
  public LecturesSchedule {
  }

  public static LecturesSchedule createLecturesSchedule(
      Long id,
      LocalDateTime openAt,
      String title,
      Integer currentCapacity,
      Long lecturesId) {
    return LecturesSchedule.builder()
        .id(id)
        .openAt(openAt)
        .title(title)
        .currentCapacity(currentCapacity)
        .lecturesId(lecturesId)
        .build();
  }

  public static LecturesSchedule empty() {
    return LecturesSchedule.builder().build();
  }

  public Boolean isMaxCapacity() {
    return currentCapacity == 30;
  }
}
