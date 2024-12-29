package io.reflectoring.buckpal.adapter.in.web;

import org.springframework.web.bind.annotation.RestController;

import io.reflectoring.buckpal.application.domain.model.Account.AccountId;
import io.reflectoring.buckpal.application.domain.model.Money;
import io.reflectoring.buckpal.application.port.in.SendMoneyCommand;
import io.reflectoring.buckpal.application.port.in.SendMoneyUseCase;
import io.reflectoring.buckpal.common.WebAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@WebAdapter
@RestController
@RequiredArgsConstructor
public class SendMoneyController {
  
  private final SendMoneyUseCase sendMoneyUseCase;

  @PostMapping(path = "/accounts/send/{sourceAccountId}/{targetAccountId}/{amount}")
  public void sendMoney(
    @PathVariable("sourceAccountId") Long sourceAccountId,
    @PathVariable("targetAccountId") Long targetAccountId,
    @PathVariable("amount") Long amount
  ){
      SendMoneyCommand command = new SendMoneyCommand(
        new AccountId(sourceAccountId), 
        new AccountId(targetAccountId), 
        Money.of(amount));

      sendMoneyUseCase.sendMoney(command);
  }
  
}
