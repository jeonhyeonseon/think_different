package com.think_different.think_different.transaction.domain;

import lombok.Getter;

@Getter
public enum TransactionCategory {

    FOOD(TransactionType.EXPENSE, "식비"),
    CAFE(TransactionType.EXPENSE, "카페"),
    TRAFFIC(TransactionType.EXPENSE, "교통"),
    TELECOM(TransactionType.EXPENSE, "통신"),
    MEDICAL(TransactionType.EXPENSE, "의료"),
    SUBSCRIPTION(TransactionType.EXPENSE, "구독"),
    SHOPPING(TransactionType.EXPENSE, "쇼핑"),
    ETC_EXPENSE(TransactionType.EXPENSE, "기타 지출"),

    SALARY(TransactionType.INCOME, "월급"),
    ALLOWANCE(TransactionType.INCOME, "용돈"),
    BONUS(TransactionType.INCOME, "보너스"),
    SIDE_INCOME(TransactionType.INCOME, "부수입"),
    REFUND(TransactionType.INCOME, "환급"),
    ETC_INCOME(TransactionType.INCOME, "기타 수입");

    private final TransactionType type;
    private final String displayName;

    TransactionCategory(TransactionType type, String displayName) {
        this.type = type;
        this.displayName = displayName;
    }
}
