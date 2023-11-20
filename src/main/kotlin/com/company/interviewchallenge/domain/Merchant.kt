package com.company.interviewchallenge.domain

import java.math.BigDecimal
import java.math.RoundingMode

data class Merchant(
    val iban: String,
    val discount: TransactionDiscount,
    val transactions: List<Transaction>
) {
    fun calculateTotalValue(): BigDecimal {
        val totalFee = transactions.sumOf { it.fee }
            .let {
                if (shouldApplyFeeDiscount()) (it.minus(
                    it * discount.discountRatio.setScale(
                        2,
                        RoundingMode.HALF_UP
                    )
                )) else it
            }

        return transactions.sumOf { it.amount }.minus(totalFee)
    }

    private fun shouldApplyFeeDiscount() = transactions.size >= discount.minimumTransaction
}