package com.company.interviewchallenge.infraestructure.adapter

import com.company.interviewchallenge.application.SimpleDebitPort
import com.company.interviewchallenge.domain.Merchant
import com.company.interviewchallenge.infraestructure.clients.SimpleDebitClient
import org.apache.poi.xssf.streaming.SXSSFWorkbook
import org.springframework.stereotype.Component
import java.io.File
import java.io.FileOutputStream


@Component
class SimpleDebitAdapter(
    private val client: SimpleDebitClient
) : SimpleDebitPort {
    override fun getTransactions(): List<Merchant> {
        return client.getMerchants()
            .map { client.getMerchantsById(it) }
            .map { it.toDomain() }
    }

    override fun generateCsv(merchants: List<Merchant>) {
        val headers = listOf("IBAN", "Payments")
        val workbook = SXSSFWorkbook()
        val spreadsheet = workbook.createSheet("output")

        val headerRow = spreadsheet.createRow(0)
        headers.forEachIndexed { index, it ->  headerRow.createCell(index).setCellValue(it) }

        merchants
            .associate { it.iban to it.calculateTotalValue().toString() }
            .onEachIndexed { index, entry ->
                val row = spreadsheet.createRow(index + 1)
                row.createCell(0).setCellValue(entry.key)
                row.createCell(1).setCellValue(entry.value)
            }

        val file = File("src/main/resources/", "output.xlsx")
        val outputStream = FileOutputStream(file)
        workbook.write(outputStream)

        outputStream.close()
        workbook.close()
    }
}