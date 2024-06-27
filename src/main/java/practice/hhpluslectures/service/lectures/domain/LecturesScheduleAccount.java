package practice.hhpluslectures.service.lectures.domain;

import lombok.Builder;

public record LecturesScheduleAccount(
    Long id,
    Long lecturesScheduleId,
    Long accountId
) {

  @Builder
  public LecturesScheduleAccount {
  }

  public static LecturesScheduleAccount createLecturesScheduleAccount(
      Long id,
      Long lecturesScheduleId,
      Long accountId
  ) {
    return LecturesScheduleAccount
        .builder()
        .id(id)
        .accountId(accountId)
        .lecturesScheduleId(lecturesScheduleId)
        .build();
  }
}
