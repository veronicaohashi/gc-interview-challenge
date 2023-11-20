package com.company.interviewchallenge.builders

import com.company.interviewchallenge.domain.Merchant
import com.company.interviewchallenge.domain.Transaction
import com.company.interviewchallenge.domain.TransactionDiscount
import java.math.BigDecimal

class MerchantBuilder {

    private var iban = "123"
    private var discount = TransactionDiscount(
        minimumTransaction = 50,
        discountRatio = BigDecimal(7)
    )
    private var transactions = mutableListOf<Transaction>()

    fun withIban(value: String): MerchantBuilder {
        this.iban = value
        return this
    }

    fun withDiscount(minimumCount: Int, discount: BigDecimal): MerchantBuilder{
        this.discount = TransactionDiscount(
            minimumTransaction = minimumCount,
            discountRatio = discount
        )
        return this
    }

    fun withTransaction(amount: BigDecimal, fee: BigDecimal): MerchantBuilder {
        this.transactions.add(Transaction(amount, fee))
        return this
    }

    fun build() = Merchant(
        iban = iban,
        discount = discount,
        transactions = transactions
    )
}