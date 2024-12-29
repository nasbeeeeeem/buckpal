package io.reflectoring.buckpal.application.domain.service;

import io.reflectoring.buckpal.application.domain.model.Money;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MoneyTransferProperties {
  
  private Money maximumTransferThreshold = Money.of(1_000_000L);

}
