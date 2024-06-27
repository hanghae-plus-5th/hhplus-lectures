package practice.hhpluslectures.infrastructure.account;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import practice.hhpluslectures.service.account.AccountRepository;
import practice.hhpluslectures.service.account.domain.Account;

@Repository
@RequiredArgsConstructor
public class AccountRepositoryImpl implements AccountRepository {

  private final AccountJpaRepository accountJpaRepository;

  @Override
  public Account getAccount(Long accountId) {
    return accountJpaRepository.findById(accountId)
        .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."))
        .toDomain();
  }
}
