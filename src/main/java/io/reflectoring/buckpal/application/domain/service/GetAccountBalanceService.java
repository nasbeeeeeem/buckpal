package io.reflectoring.buckpal.application.domain.service;

import java.time.LocalDateTime;

import io.reflectoring.buckpal.application.domain.model.Money;
import io.reflectoring.buckpal.application.port.in.GetAccountBalanceUseCase;
import io.reflectoring.buckpal.application.port.out.LoadAccountPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetAccountBalanceService implements GetAccountBalanceUseCase {
  
  private final LoadAccountPort loadAccountPort;

  @Override
  public Money getAccountBalance(GetAccountBalanceQuery query) {
    return loadAccountPort.loadAccount(query.accountId(), LocalDateTime.now()).calculateBalance();
  }
}
