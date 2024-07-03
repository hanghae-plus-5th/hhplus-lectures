package practice.hhpluslectures.infrastructure.lectures.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import practice.hhpluslectures.infrastructure.common.BaseTimeEntity;
import practice.hhpluslectures.service.lectures.domain.LecturesScheduleAccount;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LecturesScheduleAccountEntity extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Comment("특강 수강생 고유 번호")
  private Long id;

  @NotNull
  @Comment("특강 스케줄 고유 번호")
  private Long lecturesScheduleId;

  @NotNull
  @Comment("회원 고유 번호")
  private Long accountId;

  public LecturesScheduleAccountEntity(Long lecturesSchedulerId, Long accountId) {
    this.lecturesScheduleId = lecturesSchedulerId;
    this.accountId = accountId;
  }

  public LecturesScheduleAccount toDomain() {
    return LecturesScheduleAccount.createLecturesScheduleAccount(
        this.id,
        this.lecturesScheduleId,
        this.accountId
    );
  }
}
