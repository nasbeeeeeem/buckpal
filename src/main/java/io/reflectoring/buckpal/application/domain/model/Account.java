package io.reflectoring.buckpal.application.domain.model;

import java.time.LocalDateTime;
import java.util.Optional;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

@AllArgsConstructor(access =  AccessLevel.PRIVATE)
public class Account {

  private final AccountId id;

  @Getter private final Money baselineBlance;

  @Getter private final ActivityWindow activityWindow;

  public static Account withoutId(
    Money baselineBalance,
    ActivityWindow activityWindow
  ) {
    return new Account(null, baselineBalance, activityWindow);
  }
  
  public static Account withId(
    AccountId accountId,
    Money baselienBalance,
    ActivityWindow activityWindow
  ) {
    return new Account(accountId, baselienBalance, activityWindow);
  }

  public Optional<AccountId> getId() {
    return Optional.ofNullable(this.id);
  }

  // 残高を計算する
  public Money calculateBalance() {
    return Money.add(this.baselineBlance, this.activityWindow.calculateBalance(this.id));
  }

  // 自身の口座から引き出す
  // ※自身の口座から送金先の口座へ送金した、という取引を取引歴に追加する
  public boolean withdraw(Money money, AccountId targetAccountId) {
    if(!mayWithdraw(money)) {
      return false;
    }

    Activity withDrawal = new Activity(
      this.id, // 口座所有者の口座ID
      this.id, // 送金元の口座ID
      targetAccountId, // 送金先の口座ID
      LocalDateTime.now(),
      money   
    );

    this.getActivityWindow().addActivity(withDrawal);
    return true;
  }

  // 引き出せる金額か？
  private boolean mayWithdraw(Money money) {
    return Money
      .add(this.calculateBalance(), money.negate())
      .isPositive();
  }

  // 自身の口座に預け入れる
  // ※送金元の口座から自身の口座へ送金した、という取引を取引履歴に追加する
  public boolean deposit(Money money, AccountId sourceAccountId) {
    Activity deposit = new Activity(
      this.id, //口座所有者の口座ID 
      sourceAccountId, // 送金元の口座ID
      this.id, //送金先の口座ID
      LocalDateTime.now(), 
      money
    );

    this.getActivityWindow().addActivity(deposit);
    return true;
  }

  @Value
  public static class AccountId {
    private Long value;
  }
}
