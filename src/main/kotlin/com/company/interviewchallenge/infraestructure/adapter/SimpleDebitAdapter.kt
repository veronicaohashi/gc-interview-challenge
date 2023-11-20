package com.company.interviewchallenge.infraestructure.adapter

import com.company.interviewchallenge.application.SimpleDebitPort
import com.company.interviewchallenge.domain.Merchant
import com.company.interviewchallenge.infraestructure.clients.SimpleDebitClient
import org.springframework.stereotype.Component

@Component
class SimpleDebitAdapter(
    private val client: SimpleDebitClient
): SimpleDebitPort {
    override fun getTransactions(): List<Merchant> {
        return client.getMerchants()
            .map { client.getMerchantsById(it) }
            .map { it.toDomain() }
    }
}