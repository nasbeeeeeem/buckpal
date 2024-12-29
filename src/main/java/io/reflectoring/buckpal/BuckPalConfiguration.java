package io.reflectoring.buckpal;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.reflectoring.buckpal.application.domain.model.Money;
import io.reflectoring.buckpal.application.domain.service.MoneyTransferProperties;

@Configuration
@EnableConfigurationProperties(BuckPalConfigurationProperties.class)
public class BuckPalConfiguration {

  @Bean
  public MoneyTransferProperties moneyTransferProperties(BuckPalConfigurationProperties backBuckPalConfigurationProperties) {
    return new MoneyTransferProperties(Money.of(backBuckPalConfigurationProperties.getTransferThreshold()));
  }
}
