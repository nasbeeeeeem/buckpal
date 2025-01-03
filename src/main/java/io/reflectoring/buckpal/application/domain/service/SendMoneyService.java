package io.reflectoring.buckpal.application.domain.service;

import java.time.LocalDateTime;

import io.reflectoring.buckpal.application.domain.model.Account;
import io.reflectoring.buckpal.application.domain.model.Account.AccountId;
import io.reflectoring.buckpal.application.port.in.SendMoneyCommand;
import io.reflectoring.buckpal.application.port.in.SendMoneyUseCase;
import io.reflectoring.buckpal.application.port.out.AccountLock;
import io.reflectoring.buckpal.application.port.out.LoadAccountPort;
import io.reflectoring.buckpal.application.port.out.UpdateAccountStatePort;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
class SendMoneyService implements SendMoneyUseCase {
  private final LoadAccountPort loadAccountPort;
  private final AccountLock accountLock;
  private final UpdateAccountStatePort updateAccountStatePort;
  private final MoneyTransferProperties moneyTransferProperties;


  @Override
  public boolean sendMoney(SendMoneyCommand command) {
    // TODO: ビジネスルールに関する妥当性確認を行なう
    // TODO: モデルの状態を変える
    // TODO: 処理結果を返す
    checkThreshold(command);

    LocalDateTime baseLineDate = LocalDateTime.now().minusDays(10);

    Account sourceAccount = loadAccountPort.loadAccount(command.sourceAccountId(), baseLineDate);

    Account targetAccount = loadAccountPort.loadAccount(command.targetAccountId(), baseLineDate);

    AccountId sourceAccountId = sourceAccount.getId()
      .orElseThrow(() -> new IllegalStateException("expected source account ID not to be empty"));

    AccountId targetAccountId = targetAccount.getId()
      .orElseThrow(() -> new IllegalStateException("expected source account ID not to be empty"));

      accountLock.lockAccount(sourceAccountId);
      if (!sourceAccount.withdraw(command.money(), targetAccountId)) {
        accountLock.releaseAccount(sourceAccountId);
        return false;
      }
  
      accountLock.lockAccount(targetAccountId);
      if (!targetAccount.deposit(command.money(), sourceAccountId)) {
        accountLock.releaseAccount(sourceAccountId);
        accountLock.releaseAccount(targetAccountId);
        return false;
      }

      updateAccountStatePort.updateActivites(sourceAccount);
      updateAccountStatePort.updateActivites(targetAccount);

      accountLock.releaseAccount(sourceAccountId);
      accountLock.releaseAccount(targetAccountId);

      return true;
  }

  private void checkThreshold(SendMoneyCommand command) {
    if(command.money().isGreaterThan(moneyTransferProperties.getMaximumTransferThreshold())) {
      throw new ThresholdExceededException(moneyTransferProperties.getMaximumTransferThreshold(), command.money());
    }
  }

}
