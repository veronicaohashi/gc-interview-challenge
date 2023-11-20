package com.company.interviewchallenge.application

import com.company.interviewchallenge.domain.Merchant

interface SimpleDebitPort {
    fun getTransactions(): List<Merchant>

    fun generateCsv(merchants: List<Merchant>)
}