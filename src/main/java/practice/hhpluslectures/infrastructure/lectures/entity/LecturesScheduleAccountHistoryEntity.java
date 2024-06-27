package practice.hhpluslectures.infrastructure.lectures.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.hibernate.annotations.Comment;
import practice.hhpluslectures.infrastructure.common.BaseTimeEntity;
import practice.hhpluslectures.infrastructure.lectures.entity.enums.LecturesScheduleHistoryState;
import practice.hhpluslectures.service.lectures.domain.LecturesScheduleAccountHistory;

@Getter
@Entity
public class LecturesScheduleAccountHistoryEntity extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Comment("히스토리 고유번호")
  private Long id;

  @NotNull
  @Comment("수강신청 상태")
  @Enumerated(EnumType.STRING)
  private LecturesScheduleHistoryState state;

  @NotNull
  @Comment("특강 스케줄 고유 번호")
  private Long lecturesScheduleId;

  @NotNull
  @Comment("회원 고유 번호")
  private Long accountId;

  public LecturesScheduleAccountHistoryEntity(Boolean isSuccess, Long lecturesSchedulerId, Long accountId) {
    this.state = LecturesScheduleHistoryState.toLecturesScheduleHistoryState(isSuccess);
    this.lecturesScheduleId = lecturesSchedulerId;
    this.accountId = accountId;
  }

  public LecturesScheduleAccountHistory toDomain() {
    return LecturesScheduleAccountHistory.createLecturesScheduleAccountHistory(
        this.id,
        this.state.getIsSuccess(),
        this.lecturesScheduleId,
        this.accountId
    );
  }
}
