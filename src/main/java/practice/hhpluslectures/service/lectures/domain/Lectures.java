package practice.hhpluslectures.service.lectures.domain;

import lombok.Builder;

public record Lectures(
    Long id,
    String lecturesVideoUrl
) {

  @Builder
  public Lectures {
  }

  public static Lectures createLectures(
      Long id,
      String lecturesVideoUrl
  ) {
    return Lectures.builder()
        .id(id)
        .lecturesVideoUrl(lecturesVideoUrl)
        .build();
  }
}
