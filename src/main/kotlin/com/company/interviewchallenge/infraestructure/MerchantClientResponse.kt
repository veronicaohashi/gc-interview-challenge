package com.company.interviewchallenge.infraestructure

import com.fasterxml.jackson.annotation.JsonProperty
import java.math.BigDecimal

data class MerchantClientResponse(
    val iban: String,
    val discount: TransactionDiscount,
    val transactions: List<Transaction>
) {
}

data class TransactionDiscount(
    @JsonProperty("minimum_transaction_count")
    val minimumTransactionCount: Int,
    @JsonProperty("fees_discount")
    val feesDiscount: BigDecimal
)

data class Transaction(
    val amount: BigDecimal,
    val fee: BigDecimal
)