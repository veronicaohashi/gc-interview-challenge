package com.company.interviewchallenge.domain

import java.math.BigDecimal

data class Transaction(
    val amount: BigDecimal,
    val fee: BigDecimal
)