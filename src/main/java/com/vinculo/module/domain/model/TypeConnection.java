package com.vinculo.module.domain.model;

public enum TypeConnection {
    PARTNER(1),
    FAMILY(1),

    FRIEND(2),
    BUSINESS_PARTNER(2),

    MENTOR(3),
    REFERRAL(3),

    COLLEAGUE(4),
    BUDDY(4),

    ACQUAINTANCE(5);

    private final int weight;

    TypeConnection(int weight) {
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }
}