package com.company.interviewchallenge.infraestructure.adapter

import com.company.interviewchallenge.builders.MerchantBuilder
import com.company.interviewchallenge.builders.MerchantClientResponseBuilder
import com.company.interviewchallenge.infraestructure.clients.SimpleDebitClient
import com.company.interviewchallenge.infraestructure.exceptions.ClientException
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.amshove.kluent.invoking
import org.amshove.kluent.`should be equal to`
import org.amshove.kluent.`should be true`
import org.amshove.kluent.`should throw`
import org.apache.poi.ss.usermodel.WorkbookFactory
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.io.File
import java.math.BigDecimal

@ExtendWith(MockKExtension::class)
class SimpleDebitAdapterTest {

    @MockK
    private lateinit var simpleDebitClient: SimpleDebitClient

    @InjectMockKs
    private lateinit var simpleDebitAdapter: SimpleDebitAdapter

    @Test
    fun `should get all transactions per merchant`() {
        val merchantIds = arrayOf("1", "2")
        val firstMerchant = MerchantClientResponseBuilder()
            .withIban("321")
            .withDiscount(discount = BigDecimal(7), minimumCount = 2)
            .withTransaction(amount = BigDecimal(200), fee = BigDecimal(12))
            .build()
        val secondMerchant = MerchantClientResponseBuilder()
            .withIban("123")
            .withDiscount(discount = BigDecimal(2), minimumCount = 3)
            .withTransaction(amount = BigDecimal(100), fee = BigDecimal(10))
            .withTransaction(amount = BigDecimal(150), fee = BigDecimal(20))
            .build()
        val expectedMerchantCount = 2
        every { simpleDebitClient.getMerchants() } returns merchantIds
        every { simpleDebitClient.getMerchantsById(any()) } returns firstMerchant andThen secondMerchant

        val response = simpleDebitAdapter.getTransactions()

        response.size `should be equal to` expectedMerchantCount
        response[0].iban `should be equal to` firstMerchant.iban
        response[0].discount.minimumTransaction `should be equal to` firstMerchant.discount.minimumTransactionCount
        response[0].discount.discountRatio `should be equal to` firstMerchant.discount.feesDiscount
        response[0].transactions.size `should be equal to` firstMerchant.transactions.size
        response[0].transactions[0].amount `should be equal to` firstMerchant.transactions[0].amount
        response[0].transactions[0].fee `should be equal to` firstMerchant.transactions[0].fee
        response[1].iban `should be equal to` secondMerchant.iban
        response[1].discount.minimumTransaction `should be equal to` secondMerchant.discount.minimumTransactionCount
        response[1].discount.discountRatio `should be equal to` secondMerchant.discount.feesDiscount
        response[1].transactions.size `should be equal to` secondMerchant.transactions.size
        response[1].transactions[0].amount `should be equal to` secondMerchant.transactions[0].amount
        response[1].transactions[0].fee `should be equal to` secondMerchant.transactions[0].fee
        response[1].transactions[1].amount `should be equal to` secondMerchant.transactions[1].amount
        response[1].transactions[1].fee `should be equal to` secondMerchant.transactions[1].fee
        verify { simpleDebitClient.getMerchants() }
        verify(exactly = 2) { simpleDebitClient.getMerchantsById(any()) }
    }

    @Test
    fun `should throw an exception when client throw an exception`() {
        every { simpleDebitClient.getMerchants() } throws ClientException(400)

        invoking {
            simpleDebitAdapter.getTransactions()
        } `should throw` ClientException::class
    }

    @Test
    fun `should generate a csv file`() {
        val merchants = listOf(
            MerchantBuilder()
                .withIban("123")
                .withDiscount(1, BigDecimal(0.1))
                .withTransaction(BigDecimal(100), BigDecimal(15))
                .withTransaction(BigDecimal(100), BigDecimal(15))
                .build(),
            MerchantBuilder()
                .withIban("321")
                .withDiscount(3, BigDecimal(0.1))
                .withTransaction(BigDecimal(100), BigDecimal(15))
                .withTransaction(BigDecimal(100), BigDecimal(15))
                .build()
        )

        simpleDebitAdapter.generateCsv(merchants)

        val outputFile = File("src/main/resources/output.xlsx")

        outputFile.exists().`should be true`()
        val workbook = WorkbookFactory.create(outputFile)
        val sheet = workbook.getSheetAt(0)

        sheet.physicalNumberOfRows `should be equal to` 3
        sheet.getRow(0).getCell(0).toString() `should be equal to` "IBAN"
        sheet.getRow(0).getCell(1).toString() `should be equal to` "Payments"
        sheet.getRow(1).getCell(0).toString() `should be equal to` "123"
        sheet.getRow(1).getCell(1).toString() `should be equal to` "173.00"
        sheet.getRow(2).getCell(0).toString() `should be equal to` "321"
        sheet.getRow(2).getCell(1).toString() `should be equal to` "170"
    }
}