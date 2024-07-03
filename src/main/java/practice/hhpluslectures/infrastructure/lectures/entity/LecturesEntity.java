package practice.hhpluslectures.infrastructure.lectures.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import practice.hhpluslectures.infrastructure.common.BaseTimeEntity;
import practice.hhpluslectures.service.lectures.domain.Lectures;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LecturesEntity extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Comment("특강 고유번호")
  private Long id;

  @NotNull
  @Comment("특강 영상 url")
  private String lecturesVideoUrl;

  public LecturesEntity(String lecturesVideoUrl) {
    this.lecturesVideoUrl = lecturesVideoUrl;
  }

  public Lectures toDomain() {
    return Lectures.createLectures(this.id, this.lecturesVideoUrl);
  }

}
