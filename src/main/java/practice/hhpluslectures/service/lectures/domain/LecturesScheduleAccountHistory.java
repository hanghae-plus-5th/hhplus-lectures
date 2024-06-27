package practice.hhpluslectures.service.lectures.domain;

import lombok.Builder;

public record LecturesScheduleAccountHistory(
    Long id,
    Boolean isSuccess,
    Long lecturesScheduleId,
    Long accountId
) {

  @Builder
  public LecturesScheduleAccountHistory {
  }

  public static LecturesScheduleAccountHistory createLecturesScheduleAccountHistory(
      Long id,
      Boolean isSuccess,
      Long lecturesScheduleId,
      Long accountId
  ) {
    return LecturesScheduleAccountHistory.builder()
        .id(id)
        .isSuccess(isSuccess)
        .lecturesScheduleId(lecturesScheduleId)
        .accountId(accountId)
        .build();
  }
}
