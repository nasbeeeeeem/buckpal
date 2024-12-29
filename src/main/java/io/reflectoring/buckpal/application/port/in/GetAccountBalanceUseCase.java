package io.reflectoring.buckpal.application.port.in;

import io.reflectoring.buckpal.application.domain.model.Money;
import io.reflectoring.buckpal.application.domain.model.Account.AccountId;

public interface GetAccountBalanceUseCase {
  
  Money getAccountBalance(GetAccountBalanceQuery query);

  record GetAccountBalanceQuery(AccountId accountId) {

  }
}
