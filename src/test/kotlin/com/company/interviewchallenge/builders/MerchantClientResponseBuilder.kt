package com.company.interviewchallenge.builders

import com.company.interviewchallenge.infraestructure.clients.DiscountClientResponse
import com.company.interviewchallenge.infraestructure.clients.MerchantClientResponse
import com.company.interviewchallenge.infraestructure.clients.TransactionClientResponse
import java.math.BigDecimal

class MerchantClientResponseBuilder {

    private var iban = "123"
    private var discount = DiscountClientResponse(
        minimumTransactionCount = 50,
        feesDiscount = BigDecimal(7)
    )
    private var transactions = mutableListOf(
        TransactionClientResponse(
            amount = BigDecimal(1000),
            fee = BigDecimal(20)
        )
    )

    fun withIban(value: String): MerchantClientResponseBuilder {
        this.iban = value
        return this
    }

    fun withDiscount(minimumCount: Int, discount: BigDecimal): MerchantClientResponseBuilder{
        this.discount = DiscountClientResponse(
            minimumTransactionCount = minimumCount,
            feesDiscount = discount
        )
        return this
    }

    fun withTransaction(amount: BigDecimal, fee: BigDecimal): MerchantClientResponseBuilder {
        this.transactions.add(TransactionClientResponse(amount, fee))
        return this
    }

    fun build() = MerchantClientResponse(
        iban = iban,
        discount = discount,
        transactions = transactions
    )
}