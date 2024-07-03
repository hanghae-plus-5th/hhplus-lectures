package practice.hhpluslectures.infrastructure.lectures.entity.enums;

import lombok.Getter;

@Getter
public enum LecturesScheduleHistoryState {
  SUCCESS("수강 신청 성공", true),
  FAIL("수강 신청 실패", false);

  private final String label;
  private final Boolean isSuccess;

  LecturesScheduleHistoryState(String label, Boolean isSuccess) {
    this.label = label;
    this.isSuccess = isSuccess;
  }

  public static LecturesScheduleHistoryState toLecturesScheduleHistoryState(Boolean isSuccess) {
    return isSuccess ? SUCCESS : FAIL;
  }
}
