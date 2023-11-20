package com.company.interviewchallenge.infraestructure.clients

import com.company.interviewchallenge.domain.Merchant
import com.company.interviewchallenge.domain.Transaction
import com.company.interviewchallenge.domain.TransactionDiscount
import com.fasterxml.jackson.annotation.JsonProperty
import java.math.BigDecimal

data class MerchantClientResponse(
    val iban: String,
    val discount: DiscountClientResponse,
    val transactions: List<TransactionClientResponse>
) {
    fun toDomain() = Merchant(
        iban = this.iban,
        discount = TransactionDiscount(
            discountRatio = this.discount.feesDiscount,
            minimumTransaction = this.discount.minimumTransactionCount
        ),
        transactions = this.transactions.map {
            Transaction(
                amount = it.amount,
                fee = it.fee
            )
        }
    )

}

data class DiscountClientResponse(
    @JsonProperty("minimum_transaction_count")
    val minimumTransactionCount: Int,
    @JsonProperty("fees_discount")
    val feesDiscount: BigDecimal
)

data class TransactionClientResponse(
    val amount: BigDecimal,
    val fee: BigDecimal
)