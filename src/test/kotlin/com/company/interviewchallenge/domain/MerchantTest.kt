package com.company.interviewchallenge.domain

import org.amshove.kluent.`should be equal to`
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class MerchantTest {

    @Test
    fun `should calculate total value with discount when the minimum transaction count is equal to transaction size`() {
        val expectedTotalValue = BigDecimal("173.00")
        val merchant = Merchant(
            "123",
            TransactionDiscount(BigDecimal(0.1), 2),
            listOf(
                Transaction(BigDecimal(100), BigDecimal(15)),
                Transaction(BigDecimal(100), BigDecimal(15))
            )
        )

        val totalValue = merchant.calculateTotalValue()

        totalValue `should be equal to` expectedTotalValue
    }

    @Test
    fun `should calculate total value with discount when the minimum transaction count is bigger than the transaction size`() {
        val expectedTotalValue = BigDecimal("173.00")
        val merchant = Merchant(
            "123",
            TransactionDiscount(BigDecimal(0.1), 1),
            listOf(
                Transaction(BigDecimal(100), BigDecimal(15)),
                Transaction(BigDecimal(100), BigDecimal(15))
            )
        )

        val totalValue = merchant.calculateTotalValue()

        totalValue `should be equal to` expectedTotalValue
    }

    @Test
    fun `should calculate total value without discount`() {
        val expectedTotalValue = BigDecimal(170)
        val merchant = Merchant(
            "123",
            TransactionDiscount(BigDecimal(0.1), 3),
            listOf(
                Transaction(BigDecimal(100), BigDecimal(15)),
                Transaction(BigDecimal(100), BigDecimal(15))
            )
        )

        val totalValue = merchant.calculateTotalValue()

        totalValue `should be equal to` expectedTotalValue
    }
}