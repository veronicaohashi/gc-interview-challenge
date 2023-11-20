package com.company.interviewchallenge.domain

import java.math.BigDecimal

data class TransactionDiscount(
    val discountRatio: BigDecimal,
    val minimumTransaction: Int
)