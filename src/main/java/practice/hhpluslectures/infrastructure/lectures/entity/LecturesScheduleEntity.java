package practice.hhpluslectures.infrastructure.lectures.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;
import practice.hhpluslectures.infrastructure.common.BaseTimeEntity;
import practice.hhpluslectures.service.lectures.domain.LecturesSchedule;

@Getter
@Entity
public class LecturesScheduleEntity extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Comment("특강 스케줄 고유번호")
  private Long id;

  @NotNull
  @Comment("특강 오픈일")
  private LocalDateTime openAt;

  @NotNull
  @Comment("특강 제목")
  private String title;

  @NotNull
  @Comment("현재 수강 인원")
  @ColumnDefault("0")
  private Integer currentCapacity;

  @NotNull
  @Comment("특강 고유 번호")
  private Long lecturesId;

  public LecturesSchedule toDomain() {
    return LecturesSchedule.createLecturesSchedule(
        this.id,
        this.openAt,
        this.title,
        this.currentCapacity,
        this.lecturesId
    );
  }
}
