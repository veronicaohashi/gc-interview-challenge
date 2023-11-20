package com.company.interviewchallenge.infraestructure.clients

import com.company.interviewchallenge.IntegrationTest
import com.company.interviewchallenge.infraestructure.exceptions.ClientException
import org.amshove.kluent.invoking
import org.amshove.kluent.`should be equal to`
import org.amshove.kluent.`should not be null`
import org.amshove.kluent.`should throw`
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.util.*

class SimpleDebitClientTest : IntegrationTest() {

    private val host = "http://localhost:9981"

    private val simpleDebitClient = SimpleDebitClient(host)

    @Test
    fun `should return the list of merchant ids when the request execute successfully`() {
        val expectedResponse = arrayOf("123","456","789")
        val expectedListSize = 3
        stub.simpleDebit.stubGetMerchants("[123,456,789]")

        val response = simpleDebitClient.getMerchants()

        response.`should not be null`()
        response.size `should be equal to` expectedListSize
        response `should be equal to` expectedResponse
    }

    @Test
    fun `should throw ClientException when the request to get merchants has an error`() {
        stub.simpleDebit.stubGerMerchantsWithError()

        invoking {
            simpleDebitClient.getMerchants()
        } `should throw` ClientException::class
    }

    @Test
    fun `should return the merchant transactions when the request execute successfully`() {
        val expectedMerchantId = "M28A9"
        val expectedIban = "GB2756386333762976"
        val expectedFeesDiscount = BigDecimal(7)
        val expectedMinimumTransactionCount = 49
        val expectedFirstTransaction = Transaction(
            amount = BigDecimal(54869),
            fee = BigDecimal(290)
        )
        val expectedSecondTransaction = Transaction(
            amount = BigDecimal(50033),
            fee = BigDecimal(297)
        )
        val expectedTransactionsCount = 2
        val expectedResponse = """
            {
                "id": "$expectedMerchantId",
                "iban": "$expectedIban",
                "discount": {
                    "minimum_transaction_count": $expectedMinimumTransactionCount,
                    "fees_discount": $expectedFeesDiscount
                },
                "transactions": [
                    {
                        "amount": ${expectedFirstTransaction.amount},
                        "fee":  ${expectedFirstTransaction.fee}
                    },
                    {
                        "amount": ${expectedSecondTransaction.amount},
                        "fee":  ${expectedSecondTransaction.fee}
                    }
                ]
            }
        """.trimIndent()
        stub.simpleDebit.stubGetMerchantById(expectedResponse)

        val response = simpleDebitClient.getMerchantsById(expectedMerchantId)

        response.`should not be null`()
        response.iban `should be equal to` expectedIban
        response.discount.feesDiscount `should be equal to` expectedFeesDiscount
        response.discount.minimumTransactionCount `should be equal to` expectedMinimumTransactionCount
        response.transactions.size `should be equal to` expectedTransactionsCount
        response.transactions[0].amount `should be equal to` expectedFirstTransaction.amount
        response.transactions[0].fee `should be equal to` expectedFirstTransaction.fee
        response.transactions[1].amount `should be equal to` expectedSecondTransaction.amount
        response.transactions[1].fee `should be equal to` expectedSecondTransaction.fee
    }

    @Test
    fun `should throw ClientException when the request to get transactions has an error`() {
        stub.simpleDebit.stubGetMerchantByIdWithError()

        invoking {
            simpleDebitClient.getMerchantsById(UUID.randomUUID().toString())
        } `should throw` ClientException::class
    }
}