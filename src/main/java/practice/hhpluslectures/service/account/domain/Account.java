package practice.hhpluslectures.service.account.domain;

import lombok.Builder;

public record Account(
    Long id,
    String name
) {

  @Builder
  public Account {
  }

  public static Account createAccount(Long id, String name) {
    return Account
        .builder()
        .id(id)
        .name(name)
        .build();
  }
}
