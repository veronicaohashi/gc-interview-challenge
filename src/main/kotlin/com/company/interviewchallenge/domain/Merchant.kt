package com.company.interviewchallenge.domain

data class Merchant(
    val iban: String,
    val discount: TransactionDiscount,
    val transactions: List<Transaction>
)