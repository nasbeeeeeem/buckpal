package io.reflectoring.buckpal.adapter.out.presistence;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Component;

import io.reflectoring.buckpal.application.domain.model.Account;
import io.reflectoring.buckpal.application.domain.model.Activity;
import io.reflectoring.buckpal.application.domain.model.Account.AccountId;
import io.reflectoring.buckpal.application.port.out.LoadAccountPort;
import io.reflectoring.buckpal.application.port.out.UpdateAccountStatePort;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class AccountPersistenceAdapter implements LoadAccountPort, UpdateAccountStatePort{
  private final AccountRepository accountRepository;
  private final ActivityRepository activityRepository;
  private final AccountMapper accountMapper;

  @Override
  public Account loadAccount(AccountId accountId, LocalDateTime baselineDate) {
    AccountJpaEntity account = accountRepository
      .findById(accountId.getValue())
      .orElseThrow(EntityNotFoundException::new);

    List<ActivityJpaEntity> activities = activityRepository
      .findByOwnerSince(accountId.getValue(), baselineDate);

    Long withdrawalBalance = activityRepository
      .getWithdrawalBalanceUntil(accountId.getValue(), baselineDate)
      .orElse(0L);

    Long depositBalance = activityRepository
      .getDepositBalanceUntil(accountId.getValue(), baselineDate)
      .orElse(0L);

    return accountMapper.mapToDomainEntity(account, activities, withdrawalBalance, depositBalance);
  }

  @Override
  public void updateActivites(Account account) {
    for (Activity activity : account.getActivityWindow().getActivities()) {
      if (activity.getId() == null) {
        activityRepository.save(accountMapper.mapToJpaEntity(activity));
      }
    }
  }
}
