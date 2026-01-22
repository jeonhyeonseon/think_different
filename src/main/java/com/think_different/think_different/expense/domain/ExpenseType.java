package com.think_different.think_different.expense.domain;

import lombok.Getter;

@Getter
public enum ExpenseType {

    VARIABLE("VARIABLE", "변동"), // 변동
    FIX("FIX", "고정"); // 고정

    private String name;
    private String displayName;

    ExpenseType(String name, String displayName) {
        this.name = name;
        this.displayName = displayName;
    }
}
