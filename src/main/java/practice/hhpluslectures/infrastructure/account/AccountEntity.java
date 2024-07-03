package practice.hhpluslectures.infrastructure.account;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import practice.hhpluslectures.infrastructure.common.BaseTimeEntity;
import practice.hhpluslectures.service.account.domain.Account;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccountEntity extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Comment("유저 고유번호")
  private Long id;

  @NotNull
  @Comment("유저명")
  private String name;

  public AccountEntity( String name) {
    this.name = name;
  }

  public Account toDomain() {
    return Account.createAccount(this.id, this.name);
  }
}
