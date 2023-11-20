package com.company.interviewchallenge.domain

import java.math.BigDecimal

data class TransactionDiscount(
    val discount: BigDecimal,
    val minimumTransaction: Int
)