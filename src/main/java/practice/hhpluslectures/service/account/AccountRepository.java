package practice.hhpluslectures.service.account;

import practice.hhpluslectures.service.account.domain.Account;

public interface AccountRepository {

  Account getAccount(Long accountId);
}
